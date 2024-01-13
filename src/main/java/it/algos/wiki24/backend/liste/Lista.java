package it.algos.wiki24.backend.liste;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import jakarta.annotation.*;
import org.springframework.context.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 05-Jan-2024
 * Time: 07:40
 */
public abstract class Lista implements AlgosBuilderPattern {

    @Inject
    ApplicationContext appContext;

    @Inject
    TextService textService;

    @Inject
    LogService logger;

    @Inject
    BioMongoModulo bioMongoModulo;

    @Inject
    DidascaliaService didascaliaService;

    @Inject
    AnnotationService annotationService;

    @Inject
    WikiUtilityService wikiUtilityService;

    protected TypeLista type;


    /**
     * Lista ordinata (per cognome) delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     * La lista è ordinata per cognome <br>
     */
    protected List<BioMongoEntity> listaBio;


    protected List<WrapDidascalia> listaWrapDidascalie;

    protected LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaDidascalie;

    protected boolean costruttoreValido = false;

    protected boolean patternCompleto = false;

    protected String nomeLista;

    protected String titoloPagina;

    protected CrudModulo moduloCorrente;


    protected String collectionName;

    protected String message;

    boolean usaDimensioneParagrafi;

    boolean usaIncludeSottoMax;

    boolean usaSottopaginaOltreMax;

    /**
     * Costruttore base con 1 parametro (obbligatorio) <br>
     * Not annotated with @Autowired annotation, classe astratta <br>
     * La classe usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public Lista(String nomeLista) {
        this.nomeLista = nomeLista;
    }// end of constructor not @Autowired and used


    @PostConstruct
    protected void postConstruct() {
        this.fixPreferenze();
        this.patternCompleto = type != TypeLista.nessunaLista;
        this.checkValiditaCostruttore();
    }


    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.type = TypeLista.nessunaLista;
        this.usaDimensioneParagrafi = true;
        this.usaIncludeSottoMax = true;
        this.usaSottopaginaOltreMax = true;
    }

    /**
     * Pattern Builder <br>
     */
    public Lista nonUsaDimensioneParagrafi() {
        this.usaDimensioneParagrafi = false;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Lista nonUsaSottoPagina() {
        this.usaSottopaginaOltreMax = false;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Lista nonUsaIncludeNeiParagrafi() {
        this.usaIncludeSottoMax = false;
        return this;
    }

    protected void checkValiditaCostruttore() {
        if (moduloCorrente != null) {
            this.costruttoreValido = moduloCorrente.existByKey(textService.primaMaiuscola(nomeLista)) || moduloCorrente.existByKey(textService.primaMinuscola(nomeLista));
        }
        else {
            message = String.format("Manca il modulo in fixPreferenze() di %s", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message));
            this.costruttoreValido = false;
        }

        if (this.type == TypeLista.nessunaLista) {
            message = String.format("Manca il type della lista in fixPreferenze() di %s", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message));
            this.costruttoreValido = false;
        }

        this.collectionName = costruttoreValido ? annotationService.getCollectionName(BioMongoEntity.class) : VUOTA;
    }

    protected boolean checkValiditaPattern() {
        if (costruttoreValido && patternCompleto) {
            return true;
        }
        else {
            if (!costruttoreValido) {
                message = String.format("Non è valido il costruttore di %s", this.getClass().getSimpleName());
                logger.error(new WrapLog().message(message));
            }
            if (!patternCompleto) {
                message = String.format("Pattern non completo di %s", this.getClass().getSimpleName());
                logger.error(new WrapLog().message(message));
            }

            return false;
        }
    }


    @Override
    public boolean isCostruttoreValido() {
        return this.costruttoreValido;
    }

    @Override
    public boolean isPatternCompleto() {
        return this.patternCompleto;
    }

    @Override
    public String getNome() {
        return this.nomeLista;
    }

    /**
     * Numero delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     */
    public int numBio() {
        if (checkValiditaPattern()) {
            return switch (type) {
                case giornoNascita -> bioMongoModulo.countAllByGiornoNato(nomeLista);
                case giornoMorte -> bioMongoModulo.countAllByGiornoMorto(nomeLista);
                case annoNascita -> bioMongoModulo.countAllByAnnoNato(nomeLista);
                case annoMorte -> bioMongoModulo.countAllByAnnoMorto(nomeLista);
                default -> 0;
            };
        }
        else {
            return 0;
        }
    }

    /**
     * Lista ordinata delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     */
    public List<BioMongoEntity> listaBio() {
        if (checkValiditaPattern()) {
            return switch (type) {
                case giornoNascita -> bioMongoModulo.findAllByGiornoNato(nomeLista);
                case giornoMorte -> bioMongoModulo.findAllByGiornoMorto(nomeLista);
                case annoNascita -> bioMongoModulo.findAllByAnnoNato(nomeLista);
                case annoMorte -> bioMongoModulo.findAllByAnnoMorto(nomeLista);
                default -> null;
            };
        }
        else {
            return null;
        }
    }


    /**
     * Lista ordinata di tutti i wrapLista che hanno una valore valido per la pagina specifica <br>
     */
    public List<WrapDidascalia> listaWrapDidascalie() {
        listaWrapDidascalie = new ArrayList<>();
        WrapDidascalia wrap;

        if (listaBio == null || listaBio.size() == 0) {
            listaBio = listaBio();
        }

        if (listaBio != null && listaBio.size() > 0) {
            for (BioMongoEntity bio : listaBio) {
                wrap = appContext.getBean(WrapDidascalia.class).type(type).get(bio);
                if (wrap != null) {
                    listaWrapDidascalie.add(wrap);
                }
            }
        }

        listaBio = null;
        return listaWrapDidascalie;
    }


    /**
     * Lista ordinata di tutte le didascalie che hanno una valore valido per la pagina specifica <br>
     */
    public List<String> listaTestoDidascalie() {
        List<String> listaTestoDidascalie = new ArrayList<>();

        if (listaWrapDidascalie == null || listaWrapDidascalie.size() == 0) {
            listaWrapDidascalie = listaWrapDidascalie();
        }

        if (listaWrapDidascalie != null && listaWrapDidascalie.size() > 0) {
            for (WrapDidascalia wrap : listaWrapDidascalie) {
                listaTestoDidascalie.add(wrap.getDidascalia());
            }
        }

        listaWrapDidascalie = null;
        return listaTestoDidascalie;
    }


    public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaDidascalie() {
        mappaDidascalie = new LinkedHashMap<>();
        LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaPrima;
        LinkedHashMap<String, List<String>> mappaSeconda;
        List<String> listaTerza;
        String keyUno;
        String keyDue;
        String keyTre;

        if (listaWrapDidascalie == null || listaWrapDidascalie.size() == 0) {
            listaWrapDidascalie = listaWrapDidascalie();
        }
        for (WrapDidascalia wrap : listaWrapDidascalie) {
            //--primo livello - paragrafi
            keyUno = wrap.getPrimoLivello();
            if (!mappaDidascalie.containsKey(keyUno)) {
                mappaPrima = new LinkedHashMap<>();
                mappaDidascalie.put(keyUno, mappaPrima);
            }

            //--secondo livello - decade/lettera alfabetica iniziale
            keyDue = wrap.getSecondoLivello();
            mappaPrima = mappaDidascalie.get(keyUno);
            if (!mappaPrima.containsKey(keyDue)) {
                mappaSeconda = new LinkedHashMap<>();
                mappaPrima.put(keyDue, mappaSeconda);
            }

            //--terzo livello - giorno/anno/binomio alfabetico
            keyTre = wrap.getTerzoLivello();
            mappaSeconda = mappaPrima.get(keyDue);
            listaTerza = new ArrayList<>();
            if (!mappaSeconda.containsKey(keyTre)) {
                mappaSeconda.put(keyTre, listaTerza);
            }

            //--didascalia
            listaTerza = mappaSeconda.get(keyTre);
            listaTerza.add(wrap.getDidascalia());
        }

        listaWrapDidascalie = null;
        return fixAltreInCoda(mappaDidascalie);
    }

    /**
     * Sposta in coda alla mappa il paragrafo 'Altre...' (eventuale) <br>
     */
    public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> fixAltreInCoda(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappa) {
        LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaAltre;

        for (TypeInesistente type : TypeInesistente.values()) {
            if (mappa.containsKey(type.getTag())) {
                mappaAltre = mappa.get(type.getTag());
                mappa.remove(type.getTag());
                mappa.put(type.getTag(), mappaAltre);
            }
        }

        return mappa;
    }

    public List<String> keyMappa() {
        List<String> keyList = null;
        if (mappaDidascalie == null || mappaDidascalie.size() == 0) {
            mappaDidascalie = mappaDidascalie();
        }

        if (mappaDidascalie != null && mappaDidascalie.size() > 0) {
            keyList = new ArrayList<>();
            for (String key : mappaDidascalie.keySet()) {
                keyList.add(key);
            }
        }

        return keyList;
    }

    /**
     * Testo della pagina suddiviso in paragrafi <br>
     */
    public String bodyText() {
        StringBuffer buffer = new StringBuffer();
        int numMinParagrafi = 4; //@todo passare a preferenza
        int minVociInclude = 200; //@todo passare a preferenza
        int numVociLista; //voci totali
        int numChiaviMappa; //paragrafi effettivi
        boolean usaParagrafi = false;
        int numVociParagrafo;
        int maxVociPerUnaColonna = 5;
        boolean usaDiv;
        String sottoPagina;
        String vedi;
        int maxVociPerParagrafo = 50; //@todo passare a preferenza

        if (mappaDidascalie == null || mappaDidascalie.size() == 0) {
            mappaDidascalie = mappaDidascalie();
        }

        if (mappaDidascalie != null && mappaDidascalie.size() > 0) {
            numVociLista = wikiUtilityService.getSizeMappaMappa(mappaDidascalie);
            numChiaviMappa = mappaDidascalie.size();
            usaParagrafi = numChiaviMappa > numMinParagrafi && numVociLista > maxVociPerParagrafo;

            if (usaParagrafi) {
                for (String keyParagrafo : mappaDidascalie.keySet()) {
                    numVociParagrafo = wikiUtilityService.getSizeMappa(mappaDidascalie.get(keyParagrafo));
                    usaDiv = numVociParagrafo > maxVociPerUnaColonna;

                    //titolo con/senza dimensione
                    if (usaDimensioneParagrafi) {
                        //titolo con/senza includeOnly
                        if (usaIncludeSottoMax && numVociLista < minVociInclude) {
                            //per i titoli dei paragrafi che vengono 'inclusi', meglio non mettere le dimensioni
                            buffer.append(wikiUtilityService.setParagrafoIncludeOnly(keyParagrafo, numVociParagrafo));
                        }
                        else {
                            buffer.append(wikiUtilityService.setParagrafo(keyParagrafo, numVociParagrafo));
                        }
                    }
                    else {
                        buffer.append(wikiUtilityService.setParagrafo(keyParagrafo));
                    }

                    //corpo con/senza sottopagine
                    if (usaSottopaginaOltreMax && numVociParagrafo > maxVociPerParagrafo) {
                        sottoPagina = String.format("%s%s%s", textService.primaMaiuscola(titoloPagina), SLASH, keyParagrafo);

                        vedi = String.format("{{Vedi anche|%s}}", sottoPagina);
                        buffer.append(vedi + CAPO);
                    }
                    else {
                        if (usaDiv) {
                            buffer.append(DIV_INI_CAPO);
                        }
                        buffer.append(bodyParagrafo(mappaDidascalie.get(keyParagrafo)));
                        if (usaDiv) {
                            buffer.append(DIV_END_CAPO);
                        }
                    }
                }
            }
            else {
                //corpo unico senza paragrafi e senza sottopagine
                numVociParagrafo = wikiUtilityService.getSizeMappaMappa(mappaDidascalie);
                usaDiv = numVociParagrafo >= maxVociPerUnaColonna;
                if (usaDiv) {
                    buffer.append(DIV_INI_CAPO);
                }
                buffer.append(bodyAll(mappaDidascalie));
                if (usaDiv) {
                    buffer.append(DIV_END_CAPO);
                }
            }
        }

        mappaDidascalie = null;
        return buffer.toString();
    }


    /**
     * Testo della pagina <br>
     */
    public String bodyAll(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaParagrafo) {
        StringBuffer buffer = new StringBuffer();

        for (String paragrafo : mappaParagrafo.keySet()) {
            buffer.append(bodyParagrafo(mappaParagrafo.get(paragrafo)));
        }

        return buffer.toString();
    }

    /**
     * Testo del paragrafo <br>
     */
    public String bodyParagrafo(LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaParagrafo) {
        StringBuffer buffer = new StringBuffer();

        if (mappaParagrafo != null && mappaParagrafo.size() > 0) {
            for (String secondoLivello : mappaParagrafo.keySet()) {
                for (String terzoLivello : mappaParagrafo.get(secondoLivello).keySet()) {

                    for (String didascalia : mappaParagrafo.get(secondoLivello).get(terzoLivello)) {
                        buffer.append(ASTERISCO);
                        buffer.append(didascalia);
                        buffer.append(CAPO);
                    }
                }
            }
        }

        return buffer.toString();
    }

    public TypeLista getType() {
        return type;
    }

}

