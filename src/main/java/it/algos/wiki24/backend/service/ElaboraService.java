package it.algos.wiki24.backend.service;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.boot.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.packages.bioserver.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 26-Dec-2023
 * Time: 08:09
 * Classe di libreria. NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 *
 * @Inject public ElaboraService elaboraService; (iniziale minuscola) <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class ElaboraService {

    @Inject
    LogService logger;

    @Inject
    BioServerModulo bioServerModulo;

    @Inject
    MongoService mongoService;

    @Inject
    TextService textService;

    private String message;


    /**
     * Elabora tutte le entities di BioMongo <br>
     * Ricava i dati prendendoli da BioServer <br>
     */
    public void elaboraAll() {
        int tot = bioServerModulo.count();
        List<BioServerEntity> lista = mongoService.findSkip(BioServerEntity.class, 27, 12);

        for (BioServerEntity bioServerBean : lista) {
            elaboraBean(bioServerBean);
        }

        message = String.format("Fin qui ci sono");
        logger.info(new WrapLog().message(message));


    }

    /**
     * Elabora la singola entity <br>
     */
    public void elaboraBean(BioServerEntity bioServerBean) {
        String tmplBio = bioServerBean.getTmplBio();
        Map<String, String> mappa = estraeMappa(tmplBio);
        System.out.println(VUOTA);
        System.out.println(mappa);
    }


    /**
     * Estrae una mappa chiave-valore dal testo del template <br>
     * Presuppone che le righe siano separate da pipe e return
     * Controlla che non ci siano doppie graffe annidate nel valore dei parametri
     *
     * @param tmplBio da cui estrarre la mappa
     *
     * @return mappa chiave-valore
     */
    public Map<String, String> estraeMappa(String tmplBio) {
        String message;
        LinkedHashMap<String, String> mappa = null;
        LinkedHashMap mappaGraffe = null;
        boolean continua = true;
        String regexSeparatore = "\n *\\|";
        String[] righe = null;
        String chiave;
        String valore;
        int pos;

        if (textService.isEmpty(tmplBio)) {
            logger.warn(new WrapLog().message(String.format("Manca il tmplBio")));
            return null;
        }

        if (tmplBio.startsWith(DOPPIE_GRAFFE_INI) && tmplBio.endsWith(DOPPIE_GRAFFE_END)) {
            tmplBio = textService.setNoDoppieGraffe(tmplBio);
        }

        if (continua) {
            mappaGraffe = checkGraffe(tmplBio);
            if (mappaGraffe.containsKey(KEY_MAP_GRAFFE_ESISTONO)) {
                //                testoTemplate = (String) mappaGraffe.get(KEY_MAP_GRAFFE_TESTO); //@todo non chiaro
            }
        }

        if (continua) {
            if (tmplBio.startsWith(PIPE)) {
                tmplBio = tmplBio.substring(1).trim();
            }

            righe = tmplBio.split(regexSeparatore);
            if (righe.length == 1) {
                mappa = getMappaRigaUnica(tmplBio);
                continua = false;
            }
        }

        if (continua) {
            if (righe != null) {
                mappa = new LinkedHashMap();

                for (String stringa : righe) {
                    pos = stringa.indexOf(UGUALE_SEMPLICE);
                    if (pos != -1) {
                        chiave = stringa.substring(0, pos).trim();
                        valore = stringa.substring(pos + 1).trim();
                        if (!chiave.equals("")) {
                            mappa.put(chiave, valore);
                        }
                    }
                }
            }
        }

        // reinserisce il contenuto del parametro che eventualmente avesse avuto le doppie graffe
        if (continua) {
            if (mappaGraffe.containsKey(KEY_MAP_GRAFFE_ESISTONO)) {
                if ((Integer) mappaGraffe.get(KEY_MAP_GRAFFE_NUMERO) == 1) {
                    chiave = (String) mappaGraffe.get(KEY_MAP_GRAFFE_NOME_PARAMETRO);
                    valore = (String) mappaGraffe.get(KEY_MAP_GRAFFE_VALORE_PARAMETRO);
                    mappa.put(chiave, valore);
                }
                else {
                    for (int k = 0; k < (Integer) mappaGraffe.get(KEY_MAP_GRAFFE_NUMERO); k++) {
                        //                        chiave = mappaGraffe.nomeParGraffe[k];
                        //                        valore = mappaGraffe.valParGraffe[k];
                        //                        mappa.put(chiave, valore);
                    }
                }
            }
        }

        //        //--controllo e aggiunta di un parametro che verrà elaborato
        //        if (!mappa.containsKey("ForzaOrdinamento")) {
        //            mappa.put("ForzaOrdinamento", VUOTA);
        //        }

        if (mappa != null && mappa.size() < 9) {
            message = String.format("Parametri insufficienti (%d) nella bio %s", mappa.size(), mappa.get(KEY_MAPPA_TITLE));
            if (mappa.size() < 7) {
                logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            }
            else {
                logger.info(new WrapLog().exception(new AlgosException(message)));
            }
        }

        return mappa;
    }


    /**
     * Controlla le graffe interne al testo
     * <p>
     * Casi da controllare (all'interno delle graffe principali, già eliminate):
     * 1-...{{..}}...                   (singola)
     * 2-...{{}}...                     (vuota)
     * 3-{{..}}...                      (iniziale)
     * 3-...{{..}}                      (terminale)
     * 4-...{{..{{...}}...}}...         (interna)
     * 5-...{{..}}...{{...}}...         (doppie)
     * 6-...{{..}}..{{..}}..{{...}}...  (tre o più)
     * 7-...{{..}}..|..{{..}}...        (due in punti diversi)
     * 8-...{{...|...}}...              (pipe interno)
     * 8-...{{...|...|...}}...          (doppio pipe)
     * 7-...{{..|...}}..|..{{..}}...    (due pipe in due graffe)
     * 9-...{{....                      (singola apertura)
     * 10-...}}....                     (singola chiusura)
     * <p>
     * Se una o più graffe esistono, restituisce:
     * <p>
     * keyMapGraffeEsistono = se esistono                                       (boolean)
     * keyMapGraffeNumero = quante ce ne sono                                   (integer)
     * keyMapGraffeTestoPrecedente = testo che precede la prima graffa o testo originale se non ce ne sono
     * keyMapGraffeListaWrapper = lista di WrapTreStringhe coi seguenti dati:   (list<WrapTreStringhe>)
     * - prima = valore del contenuto delle graffe                              (stringa)
     * - seconda = nome del parametro interessato                               (stringa)
     * - terza = valore completo del parametro che le contiene                  (stringa)
     *
     * @param testoTemplate da analizzare
     *
     * @return mappa di risultati
     */
    public LinkedHashMap checkGraffe(String testoTemplate) {
        LinkedHashMap mappa = null;
        boolean continua = false;
        String tagIni = "{{";
        String tagEnd = "}}";
        int numIni = textService.getNumTag(testoTemplate, DOPPIE_GRAFFE_INI);
        int numEnd = textService.getNumTag(testoTemplate, DOPPIE_GRAFFE_END);

        mappa = new LinkedHashMap();
        mappa.put(KEY_MAP_GRAFFE_ESISTONO, false);
        mappa.put(KEY_MAP_GRAFFE_NUMERO, 0);
        mappa.put(KEY_MAP_GRAFFE_TESTO_PRECEDENTE, testoTemplate);
        mappa.put(KEY_MAP_GRAFFE_VALORE_CONTENUTO, VUOTA);
        mappa.put(KEY_MAP_GRAFFE_NOME_PARAMETRO, VUOTA);
        mappa.put(KEY_MAP_GRAFFE_VALORE_PARAMETRO, VUOTA);

        if (!testoTemplate.equals(VUOTA)) {
            continua = true;
        }

        // controllo di esistenza delle graffe
        if (continua) {
            if (testoTemplate.contains(tagIni) && testoTemplate.contains(tagEnd)) {
                mappa.put(KEY_MAP_GRAFFE_ESISTONO, true);
            }
            else {
                continua = false;
            }
        }

        // spazzola il testo per ogni coppia di graffe
        if (continua) {
            if (numIni == numEnd) {
                while (testoTemplate.contains(tagIni) && testoTemplate.contains(tagEnd)) {
                    testoTemplate = levaGraffa(mappa, testoTemplate);
                }
            }
            else {
            }
        }

        return mappa;
    }


    /**
     * Estrae una mappa chiave/valore dal testo contenuto tutto in una riga <br>
     * Presuppone che la riga sia unica e i parametri siano separati da pipe <br>
     *
     * @param testo
     *
     * @return mappa chiave/valore
     */
    public LinkedHashMap getMappaRigaUnica(String testo) {
        LinkedHashMap mappa = null;
        boolean continua = false;
        String sepRE = "\\|";
        String[] righe;
        String chiave;
        String valore;
        int pos;

        if (!testo.equals(VUOTA)) {
            continua = true;
        }

        if (continua) {
            righe = testo.split(sepRE);
            if (righe != null && righe.length > 0) {
                mappa = new LinkedHashMap();

                for (String stringa : righe) {
                    pos = stringa.indexOf(UGUALE_SEMPLICE);
                    if (pos != -1) {
                        chiave = stringa.substring(0, pos).trim();
                        valore = stringa.substring(pos + 1).trim();
                        if (!chiave.equals(VUOTA)) {
                            mappa.put(chiave, valore);
                        }
                    }
                }
            }
        }

        return mappa;
    }


    /**
     * Elabora ed elimina le prime graffe del testo
     * Regola la mappa
     * Restituisce il testo depurato delle prime graffe per ulteriore elaborazione
     *
     * @param testoTemplate testo completo del template
     */
    public String levaGraffa(HashMap mappa, String testoTemplate) {
        String testoElaborato = VUOTA;
        boolean continua = false;
        int posIni;
        int posEnd;
        String testoGraffa = VUOTA;

        if (mappa != null && !testoTemplate.equals("")) {
            testoElaborato = testoTemplate;
            continua = true;
        }

        // controllo di esistenza delle graffe
        if (continua) {
            if (testoTemplate.contains(DOPPIE_GRAFFE_INI) && testoTemplate.contains(DOPPIE_GRAFFE_END)) {
            }
            else {
                continua = false;
            }
        }

        // controllo (non si sa mai) che le graffe siano nell'ordine giusto
        if (continua) {
            posIni = testoTemplate.indexOf(DOPPIE_GRAFFE_INI);
            posEnd = testoTemplate.indexOf(DOPPIE_GRAFFE_END);
            if (posIni > posEnd) {
                continua = false;
            }
        }

        //spazzola il testo fino a pareggiare le graffe
        if (continua) {
            posIni = testoTemplate.indexOf(DOPPIE_GRAFFE_INI);
            posEnd = testoTemplate.indexOf(DOPPIE_GRAFFE_END, posIni);
            testoGraffa = testoTemplate.substring(posIni, posEnd + DOPPIE_GRAFFE_END.length());
            while (!textService.isPariGraffe(testoGraffa)) {
                posEnd = testoTemplate.indexOf(DOPPIE_GRAFFE_END, posEnd + DOPPIE_GRAFFE_END.length());
                if (posEnd != -1) {
                    testoGraffa = testoTemplate.substring(posIni, posEnd + DOPPIE_GRAFFE_END.length());
                }
                else {
                    mappa.put(KEY_MAP_GRAFFE_ESISTONO, false);
                    break;
                }
            }
        }

        //estrae i dati rilevanti per la mappa
        //inserisce i dati nella mappa
        if (continua) {
            testoElaborato = regolaMappa(mappa, testoTemplate, testoGraffa);
        }

        return testoElaborato;
    }


    /**
     * Elabora il testo della singola graffa
     * Regola la mappa
     */
    public String regolaMappa(HashMap mappa, String testoTemplate, String testoGraffa) {
        String testoElaborato = VUOTA;
        boolean continua = false;
        ArrayList arrayValGraffe;
        ArrayList arrayNomeParGraffe;
        ArrayList arrayvValParGraffe;
        String valParGraffe = VUOTA;
        String nomeParGraffe = VUOTA;
        String valRiga;
        int posIni = 0;
        int posEnd = 0;
        String sep2 = "\n|";
        int numGraffe;

        // controllo di congruità
        if (mappa != null && !testoTemplate.equals(VUOTA) && !testoGraffa.equals(VUOTA)) {
            testoElaborato = textService.sostituisce(testoTemplate, testoGraffa, VUOTA);
            continua = true;
        }

        //estrae i dati rilevanti per la mappa
        //inserisce i dati nella mappa
        if (continua) {
            posIni = testoTemplate.indexOf(testoGraffa);
            posIni = testoTemplate.lastIndexOf(sep2, posIni);
            posIni += sep2.length();
            posEnd = testoTemplate.indexOf(sep2, posIni + testoGraffa.length());
            if (posIni == -1) {
                continua = false;
            }
            if (posEnd == -1) {
                posEnd = testoTemplate.length();
            }
        }

        //estrae i dati rilevanti per la mappa
        //inserisce i dati nella mappa
        if (continua) {
            valRiga = testoTemplate.substring(posIni, posEnd);
            posIni = valRiga.indexOf(UGUALE_SEMPLICE);
            //nomeParGraffe = valRiga.substring(0, posIni).trim()
            //valParGraffe = valRiga.substring(posIni + sepParti.length()).trim()
            if (posIni != -1) {
                nomeParGraffe = valRiga.substring(0, posIni).trim();
                valParGraffe = valRiga.substring(posIni + UGUALE_SEMPLICE.length()).trim();
            }
            else {
                continua = false;
            }
        }

        numGraffe = mappa.get(KEY_MAP_GRAFFE_NUMERO) != null ? (Integer) mappa.get(KEY_MAP_GRAFFE_NUMERO) : 0;
        numGraffe++;
        switch (numGraffe) {
            case 1:
                mappa.put(KEY_MAP_GRAFFE_VALORE_CONTENUTO, testoGraffa);
                mappa.put(KEY_MAP_GRAFFE_NOME_PARAMETRO, nomeParGraffe);
                mappa.put(KEY_MAP_GRAFFE_VALORE_PARAMETRO, valParGraffe);
                break;
            case 2:
                arrayValGraffe = new ArrayList();
                String oldValGraffe;
                oldValGraffe = (String) mappa.get(KEY_MAP_GRAFFE_VALORE_CONTENUTO);
                arrayValGraffe.add(oldValGraffe);
                arrayValGraffe.add(testoGraffa);
                mappa.put(KEY_MAP_GRAFFE_VALORE_CONTENUTO, arrayValGraffe);

                arrayNomeParGraffe = new ArrayList();
                String oldNomeParGraffe;
                oldNomeParGraffe = (String) mappa.get(KEY_MAP_GRAFFE_VALORE_PARAMETRO);
                arrayNomeParGraffe.add(oldNomeParGraffe);
                arrayNomeParGraffe.add(nomeParGraffe);
                mappa.put(KEY_MAP_GRAFFE_VALORE_PARAMETRO, arrayNomeParGraffe);

                //                arrayvValParGraffe = new ArrayList();
                //                String oldValParGraffe;
                //                arrayValGraffe = (ArrayList) mappa.get(KEY_MAP_GRAFFE_VALORE_CONTENUTO);
                //                arrayvValParGraffe.add(oldValParGraffe);
                //                arrayvValParGraffe.add(valParGraffe);
                //                mappa.put(KEY_MAP_GRAFFE_VALORE_CONTENUTO, arrayvValParGraffe);
                break;
            default: // caso non definito
                arrayValGraffe = (ArrayList) mappa.get(KEY_MAP_GRAFFE_VALORE_CONTENUTO);
                arrayValGraffe.add(testoGraffa);
                mappa.put(KEY_MAP_GRAFFE_VALORE_CONTENUTO, arrayValGraffe);

                arrayNomeParGraffe = (ArrayList) mappa.get(KEY_MAP_GRAFFE_VALORE_PARAMETRO);
                arrayNomeParGraffe.add(nomeParGraffe);
                mappa.put(KEY_MAP_GRAFFE_VALORE_PARAMETRO, arrayNomeParGraffe);

                arrayvValParGraffe = (ArrayList) mappa.get(KEY_MAP_GRAFFE_VALORE_PARAMETRO);
                arrayvValParGraffe.add(valParGraffe);
                mappa.put(KEY_MAP_GRAFFE_VALORE_PARAMETRO, arrayvValParGraffe);
                break;
        }

        mappa.put(KEY_MAP_GRAFFE_NUMERO, numGraffe);
        mappa.put(KEY_MAP_GRAFFE_TESTO_PRECEDENTE, testoElaborato);

        return testoElaborato;
    }

}// end of Service class