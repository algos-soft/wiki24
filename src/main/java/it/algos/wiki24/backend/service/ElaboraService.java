package it.algos.wiki24.backend.service;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
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
    BioMongoModulo bioMongoModulo;

    @Inject
    MongoService mongoService;

    @Inject
    TextService textService;

    @Inject
    RegexService regexService;

    @Inject
    AnnoModulo annoModulo;

    private String message;


    /**
     * Elabora tutte le entities di BioMongo <br>
     * Ricava i dati prendendoli da BioServer <br>
     */
    public void elaboraAll() {
        List<BioServerEntity> lista = mongoService.findAll(BioServerEntity.class);

        for (BioServerEntity bioServerBean : lista) {
            creaBeanMongo(bioServerBean);
        }
    }

    /**
     * Elabora la singola entity <br>
     */
    public BioMongoEntity creaBeanMongo(BioServerEntity bioServerBean) {
        BioMongoEntity bioMongoEntity = null;
        Map<String, String> mappa;

        if (bioServerBean == null) {
            return null;
        }

        bioMongoEntity = bioMongoModulo.newEntity(bioServerBean);
        mappa = estraeMappa(bioServerBean);

        bioMongoEntity = elaboraBean(bioMongoEntity, mappa);
        return (BioMongoEntity) bioMongoModulo.insertSave(bioMongoEntity);
    }


    /**
     * Elabora la singola entity <br>
     */
    public Map<String, String> estraeMappa(BioServerEntity bioServerBean) {
        String tmplBio;

        if (bioServerBean == null) {
            return null;
        }

        tmplBio = bioServerBean.getTmplBio();
        return estraeMappa(tmplBio);
    }

    /**
     * Elabora la singola entity <br>
     */
    public BioMongoEntity elaboraBean(BioMongoEntity bioMongoEntity, Map<String, String> mappa) {
        String wikiTitle;
        if (bioMongoEntity == null || mappa == null) {
            return null;
        }

        wikiTitle = bioMongoEntity.wikiTitle;
        bioMongoEntity.nome = fixNome(mappa.get(KEY_MAPPA_NOME));
        bioMongoEntity.cognome = fixCognome(mappa.get(KEY_MAPPA_COGNOME));
        bioMongoEntity.ordinamento = fixOrdinamento(wikiTitle, mappa.get(KEY_MAPPA_ORDINAMENTO), mappa.get(KEY_MAPPA_NOME), mappa.get(KEY_MAPPA_COGNOME));
        bioMongoEntity.sesso = fixSesso(mappa.get(KEY_MAPPA_SESSO));
        bioMongoEntity.luogoNato = fixLuogo(wikiTitle, mappa.get(KEY_MAPPA_LUOGO_NASCITA), mappa.get(KEY_MAPPA_LUOGO_NASCITA_LINK));
        bioMongoEntity.giornoNato = fixGiorno(wikiTitle, mappa.get(KEY_MAPPA_GIORNO_NASCITA));
        bioMongoEntity.giornoNatoOrd = fixGiornoOrd(wikiTitle, bioMongoEntity.giornoNato);
        bioMongoEntity.annoNato = fixAnno(wikiTitle, mappa.get(KEY_MAPPA_ANNO_NASCITA));
        bioMongoEntity.annoNatoOrd = fixAnnoOrd(wikiTitle, bioMongoEntity.annoNato);
        bioMongoEntity.luogoMorto = fixLuogo(wikiTitle, mappa.get(KEY_MAPPA_LUOGO_MORTE), mappa.get(KEY_MAPPA_LUOGO_MORTE_LINK));
        bioMongoEntity.giornoMorto = fixGiorno(wikiTitle, mappa.get(KEY_MAPPA_GIORNO_MORTE));
        bioMongoEntity.giornoMortoOrd = fixGiornoOrd(wikiTitle, bioMongoEntity.giornoMorto);
        bioMongoEntity.annoMorto = fixAnno(wikiTitle, mappa.get(KEY_MAPPA_ANNO_MORTE));
        bioMongoEntity.annoMortoOrd = fixAnnoOrd(wikiTitle, bioMongoEntity.annoMorto);
        bioMongoEntity.attivita = fixAttivita(wikiTitle, mappa.get(KEY_MAPPA_ATTIVITA));
        bioMongoEntity.attivita2 = fixAttivita(wikiTitle, mappa.get(KEY_MAPPA_ATTIVITA_DUE));
        bioMongoEntity.attivita3 = fixAttivita(wikiTitle, mappa.get(KEY_MAPPA_ATTIVITA_TRE));
        bioMongoEntity.nazionalita = fixNazionalita(wikiTitle, mappa.get(KEY_MAPPA_NAZIONALITA));

        return bioMongoEntity;
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

        if (mappa != null && mappa.size() < 8) {
            message = String.format("Parametri insufficienti (%d) nella bio %s", mappa.size(), mappa.get(KEY_MAPPA_TITLE));
            if (mappa.size() < 6) {
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

    public String fixNome(String grezzo) {
        String elaborato = grezzo;

        if (textService.isValid(grezzo)) {
            elaborato = fixElimina(elaborato);
            elaborato = fixDopo(elaborato);
            elaborato = fixNomeSingolo(elaborato);
        }

        return elaborato;
    }


    public String fixCognome(String grezzo) {
        String elaborato = grezzo;

        if (textService.isValid(grezzo)) {
            elaborato = fixElimina(elaborato);
            elaborato = fixDopo(elaborato);
        }

        return elaborato;
    }


    public String fixOrdinamento(String wikiTitle, String ordinamento, String nome, String cognome) {
        String elaborato = VUOTA;

        if (textService.isValid(ordinamento)) {
            return ordinamento;
        }

        elaborato = cognome;
        if (textService.isValid(nome)) {
            elaborato += SPAZIO;
            elaborato += nome;
        }

        if (textService.isEmpty(elaborato)) {
            elaborato = wikiTitle;
        }

        return textService.isValid(elaborato) ? elaborato : VUOTA;
    }


    public String fixSesso(String grezzo) {
        String elaborato = grezzo;

        if (textService.isValid(grezzo)) {
            elaborato = fixElimina(elaborato);
            elaborato = fixDopo(elaborato);
        }

        return elaborato;
    }

    public String fixLuogo(String wikiTitle, String grezzo, String linkLuogo) {
        String elaborato = grezzo != null ? textService.trim(grezzo) : VUOTA;

        if (textService.isValid(elaborato)) {
            for (TypeElimina type : TypeElimina.values()) {
                elaborato = type.get(elaborato);
            }
        }

        if (textService.isValid(elaborato)) {
            elaborato = fixDopo(elaborato);
        }
        else {
            return VUOTA;
        }

        if (textService.isValid(linkLuogo)) {
            elaborato = String.format("%s%s%s", linkLuogo, PIPE, elaborato);
        }

        return textService.isValid(elaborato) ? textService.setDoppieQuadre(elaborato) : VUOTA;
    }

    public String fixGiorno(String wikiTitle, String grezzo) {
        String elaboratoUno = grezzo != null ? textService.trim(grezzo) : VUOTA;
        String elaboratoDue = VUOTA;
        String pattern = "^(1 |1º |1° |[2-9] |[1-2][0-9] |30 |31 )(gennaio|febbraio|marzo|aprile|maggio|giugno|luglio|agosto|settembre|ottobre|novembre|dicembre)$";
        String patternUno = "^(1 |1º |1° )";
        String patternUnoSpazio = "^1 ";
        String patternZero = "^0";
        String patternMaiuscola = "^(1 |1º |1° |[2-9] |[1-2][0-9] |30 |31 )(Gennaio|Febbraio|Marzo|Aprile|Maggio|Giugno|Luglio|Agosto|Settembre|Ottobre|Novembre|Dicembre)$";
        String patternApici = "^'.+'$";
        String patternVirgola = "^,[1-9]";

        elaboratoUno = fixElimina(elaboratoUno);
        if (textService.isEmpty(elaboratoUno)) {
            return VUOTA;
        }

        elaboratoUno = fixDopo(elaboratoUno);
        if (textService.isEmpty(elaboratoUno)) {
            return VUOTA;
        }

        elaboratoUno = textService.setNoDoppieQuadre(elaboratoUno);

        if (regexService.isEsiste(elaboratoUno, patternZero)) {
            elaboratoUno = elaboratoUno.substring(1);
            message = String.format("Zero iniziale da levare: [%s]%s%s", wikiTitle, FORWARD, elaboratoUno);
            logger.warn(new WrapLog().message(message));
        }

        if (regexService.isEsiste(elaboratoUno, patternUno)) {
            if (regexService.isEsiste(elaboratoUno, patternUnoSpazio)) {
                elaboratoUno = elaboratoUno.replaceAll("1" + SPAZIO, "1" + PRIMO_WIN + SPAZIO);
                message = String.format("Un primo del mese da convertire: [%s]%s%s", wikiTitle, FORWARD, elaboratoUno);
                logger.warn(new WrapLog().message(message));
            }
            else {
                if (elaboratoUno.contains(PRIMO_MENO_OTTANTA)) {
                    elaboratoUno = elaboratoUno.replace(PRIMO_MENO_OTTANTA, PRIMO_MENO_SETTANTA);
                    message = String.format("Un primo del mese da convertire: [%s]%s%s", wikiTitle, FORWARD, elaboratoUno);
                    logger.warn(new WrapLog().message(message));
                }
            }
        }

        elaboratoDue = regexService.getReal(elaboratoUno, pattern);
        if (textService.isValid(elaboratoDue)) {
            return elaboratoDue;
        }

        if (regexService.isEsiste(elaboratoUno, patternMaiuscola)) {
            elaboratoDue = elaboratoUno.toLowerCase();
            message = String.format("Nome del mese maiuscolo da convertire: [%s]%s%s", wikiTitle, FORWARD, elaboratoUno);
            logger.warn(new WrapLog().message(message));
            return elaboratoDue;
        }

        if (regexService.isEsiste(elaboratoUno, patternApici)) {
            elaboratoDue = elaboratoUno.replace("'", "");
            message = String.format("Apici inizio-fine da levare: [%s]%s%s", wikiTitle, FORWARD, elaboratoUno);
            logger.warn(new WrapLog().message(message));
            return elaboratoDue;
        }

        if (regexService.isEsiste(elaboratoUno, patternVirgola)) {
            elaboratoDue = elaboratoUno.substring(1);
            message = String.format("Virgola iniziale da levare: [%s]%s%s", wikiTitle, FORWARD, elaboratoUno);
            logger.warn(new WrapLog().message(message));
            return elaboratoDue;
        }

        if (elaboratoUno.contains(SPAZIO_NON_BREAKING)) {
            elaboratoDue = elaboratoUno.replace(SPAZIO_NON_BREAKING, SPAZIO);
            message = String.format("Spazio non-breaking da levare: [%s]%s%s", wikiTitle, FORWARD, elaboratoUno);
            logger.warn(new WrapLog().message(message));
            return elaboratoDue;
        }

        return elaboratoDue;
    }

    public String fixAnno(String wikiTitle, String grezzo) {
        String elaboratoUno = grezzo != null ? textService.trim(grezzo) : VUOTA;
        String elaboratoDue = VUOTA;
        String pattern = "^[1-9][0-9]?[0-9]?[0-9]?$";
        String patternAnteCristo = "^[1-9][0-9]?[0-9]?[0-9]? a.C.$";

        for (TypeElimina type : TypeElimina.values()) {
            elaboratoUno = type.get(elaboratoUno);
        }

        if (textService.isValid(elaboratoUno)) {
            elaboratoUno = fixDopo(elaboratoUno);
            elaboratoUno = textService.setNoDoppieQuadre(elaboratoUno);
            elaboratoDue = regexService.getReal(elaboratoUno, pattern);
        }
        else {
            return VUOTA;
        }

        if (textService.isValid(elaboratoDue)) {
            return elaboratoDue;
        }
        elaboratoDue = regexService.getReal(elaboratoUno, patternAnteCristo);

        return elaboratoDue;
    }


    public int fixGiornoOrd(String wikiTitle, String testoGiorno) {
        int giornoOrdine = MAX_ORDINE_ANNI;
        AnnoEntity annoWiki;

        if (textService.isEmpty(testoAnno)) {
            return annoOrdine;
        }

        annoWiki = (AnnoEntity) annoModulo.findByKey(testoAnno);
        if (annoWiki != null) {
            annoOrdine = annoWiki.ordine;
        }

        return annoOrdine;
    }

    public int fixAnnoOrd(String wikiTitle, String testoAnno) {
        int annoOrdine = MAX_ORDINE_ANNI;
        AnnoEntity annoWiki;

        if (textService.isEmpty(testoAnno)) {
            return annoOrdine;
        }

        annoWiki = (AnnoEntity) annoModulo.findByKey(testoAnno);
        if (annoWiki != null) {
            annoOrdine = annoWiki.ordine;
        }

        return annoOrdine;
    }


    public String fixAttivita(String wikiTitle, String grezzo) {
        String elaborato = grezzo != null ? textService.trim(grezzo) : VUOTA;

        if (textService.isValid(elaborato)) {
            for (TypeElimina type : TypeElimina.values()) {
                elaborato = type.get(elaborato);
            }
        }

        if (textService.isValid(elaborato)) {
            elaborato = fixDopo(elaborato);
        }
        else {
            return VUOTA;
        }

        return textService.isValid(elaborato) ? elaborato : VUOTA;
    }

    public String fixNazionalita(String wikiTitle, String grezzo) {
        String elaborato = grezzo != null ? textService.trim(grezzo) : VUOTA;

        if (textService.isValid(elaborato)) {
            for (TypeElimina type : TypeElimina.values()) {
                elaborato = type.get(elaborato);
            }
        }

        if (textService.isValid(elaborato)) {
            elaborato = fixDopo(elaborato);
        }
        else {
            return VUOTA;
        }

        return textService.isValid(elaborato) ? elaborato : VUOTA;
    }

    public String fixNomeSingolo(String elaboratoForseDoppio) {
        String elaboratoSingolo = elaboratoForseDoppio;

        if (textService.isValid(elaboratoForseDoppio)) {
            if (elaboratoForseDoppio.contains(SPAZIO)) {
                elaboratoSingolo = elaboratoForseDoppio.substring(0, elaboratoForseDoppio.indexOf(SPAZIO));
                //                elaboratoSingolo = textService.levaCodaDaPrimo(elaboratoForseDoppio, SPAZIO);
            }
        }

        return elaboratoSingolo.trim();
    }

    /**
     * Elimina gli eventuali contenuti IN CODA che non devono essere presi in considerazione <br>
     * Restituisce un valore GREZZO che deve essere ancora elaborato <br>
     * <p>
     * Tag chiave di contenuti che invalidano il valore:
     * UGUALE = "="
     * INTERROGATIVO = "?"
     * ECC = "ecc."
     * dubbio -> " o ", -> " oppure "
     *
     * @param valorePropertyTmplBioServer testo originale proveniente dalla property tmplBioServer della entity Bio
     *
     * @return valore grezzo ammesso
     */
    public String fixElimina(String valorePropertyTmplBioServer) {
        String valoreGrezzo = valorePropertyTmplBioServer.trim();

        if (textService.isEmpty(valorePropertyTmplBioServer)) {
            return VUOTA;
        }

        for (TypeElimina type : TypeElimina.values()) {
            valoreGrezzo = type.get(valoreGrezzo);
        }

        return valoreGrezzo.trim();
    }


    /**
     * Elimina gli eventuali contenuti IN CODA che non devono essere presi in considerazione <br>
     * Restituisce un valore GREZZO che deve essere ancora elaborato <br>
     * <p>
     * Tag chiave di troncature sempre valide:
     * REF = "<ref"
     * REF = ""{{#tag:ref""
     * NOTE = "<!--"
     * GRAFFE = "{{"
     * NO WIKI = "<nowiki>"
     *
     * @param valorePropertyTmplBioServer testo originale proveniente dalla property tmplBioServer della entity Bio
     *
     * @return valore grezzo troncato dopo alcuni tag chiave (<ref>, {{, ecc.) <br>
     */
    public String fixDopo(String valorePropertyTmplBioServer) {
        String valoreGrezzo = valorePropertyTmplBioServer.trim();

        if (textService.isEmpty(valorePropertyTmplBioServer)) {
            return VUOTA;
        }

        valoreGrezzo = textService.setNoDoppieQuadre(valoreGrezzo);
        valoreGrezzo = textService.setNoQuadre(valoreGrezzo);

        for (TypeDopo type : TypeDopo.values()) {
            valoreGrezzo = type.get(valoreGrezzo);
        }

        if (valoreGrezzo.startsWith(PARENTESI_TONDA_INI)) {
            valoreGrezzo = valoreGrezzo.replaceAll(PARENTESI_TONDA_INI_REGEX, VUOTA);
            valoreGrezzo = valoreGrezzo.replaceAll(PARENTESI_TONDA_END_REGEX, VUOTA);

        }
        else {
            valoreGrezzo = textService.levaCodaDaPrimo(valoreGrezzo, PARENTESI_TONDA_INI);
        }

        return valoreGrezzo.trim();
    }

}// end of Service class