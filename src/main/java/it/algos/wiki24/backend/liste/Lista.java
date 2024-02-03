package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import static it.algos.base24.backend.enumeration.TypeFiltro.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.packages.crono.secolo.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.tabelle.attplurale.*;
import it.algos.wiki24.backend.packages.tabelle.attsingolare.*;
import it.algos.wiki24.backend.packages.tabelle.nazplurale.*;
import it.algos.wiki24.backend.packages.tabelle.nazsingolare.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import jakarta.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.query.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 05-Jan-2024
 * Time: 07:40
 */
@SpringComponent
@Primary()
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Lista implements AlgosBuilderPattern {

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

    @Inject
    protected GiornoModulo giornoModulo;

    @Inject
    protected AnnoModulo annoModulo;

    @Inject
    ArrayService arrayService;

    @Inject
    MathService mathService;

    @Inject
    SecoloModulo secoloModulo;

    @Inject
    protected AttSingolareModulo attSingolareModulo;

    @Inject
    protected AttPluraleModulo attPluraleModulo;

    @Inject
    protected NazSingolareModulo nazSingolareModulo;

    @Inject
    protected NazPluraleModulo nazPluraleModulo;

    protected TypeLista type;


    /**
     * Lista ordinata (per cognome) delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     * La lista è ordinata per cognome <br>
     */
    protected List<BioMongoEntity> listaBio;


    protected List<WrapDidascalia> listaWrapDidascalie;

    protected LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaCompleta;

    protected String bodyText;

    protected List<String> listaSottopagine;

    protected boolean costruttoreValido = false;

    protected boolean patternCompleto = false;

    protected String nomeLista;

    protected String titoloPagina;

    protected CrudModulo moduloCorrente;


    protected String collectionName;

    protected String message;

    boolean usaDimensioneParagrafi;

    boolean usaIncludeSottoMax;

    boolean usaSottopagine;

    boolean isSottopagina;

    private int sogliaDiv;

    private int sogliaParagrafi;

    private int sogliaIncludeAll;

    private int sogliaSottoPagina;

    private int sogliaPaginaGiorniAnni;

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
        this.patternCompleto = type != null && type != TypeLista.nessunaLista;
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
        this.sogliaDiv = WPref.sogliaDiv.getInt();
        this.sogliaParagrafi = WPref.sogliaParagrafi.getInt();
        this.sogliaIncludeAll = WPref.sogliaIncludeAll.getInt();
        this.sogliaSottoPagina = WPref.sogliaSottoPagina.getInt();
        this.sogliaPaginaGiorniAnni = WPref.sogliaPaginaGiorniAnni.getInt();

    }

    /**
     * Pattern Builder <br>
     */
    public Lista type(TypeLista type) {
        this.type = type;

        if (type == null) {
            return this;
        }

        this.moduloCorrente = switch (type) {
            case giornoNascita, giornoMorte -> giornoModulo;
            case annoNascita, annoMorte -> annoModulo;
            case attivitaSingolare -> attSingolareModulo;
            case attivitaPlurale -> attPluraleModulo;
            case nazionalitaSingolare -> nazSingolareModulo;
            case nazionalitaPlurale -> nazPluraleModulo;
            default -> null;
        };

        this.titoloPagina = switch (type) {
            case giornoNascita -> wikiUtilityService.wikiTitleNatiGiorno(nomeLista);
            case giornoMorte -> wikiUtilityService.wikiTitleMortiGiorno(nomeLista);
            case annoNascita -> wikiUtilityService.wikiTitleNatiAnno(nomeLista);
            case annoMorte -> wikiUtilityService.wikiTitleMortiAnno(nomeLista);
            case attivitaSingolare -> VUOTA;
            case attivitaPlurale -> wikiUtilityService.wikiTitleAttivita(nomeLista);
            case nazionalitaSingolare -> VUOTA;
            case nazionalitaPlurale -> wikiUtilityService.wikiTitleNazionalita(nomeLista);
            default -> null;
        };

        this.usaSottopagine = switch (type) {
            case giornoNascita, giornoMorte -> WPref.usaSottopagineGiorni.is();
            case annoNascita, annoMorte -> WPref.usaSottopagineAnni.is();
            default -> false;
        };

        return this;
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
    public Lista nonUsaIncludeNeiParagrafi() {
        this.usaIncludeSottoMax = false;
        return this;
    }

    protected void checkValiditaCostruttore() {
        costruttoreValido = textService.isValid(nomeLista);
    }

    protected boolean checkValiditaPattern() {
        if (costruttoreValido && patternCompleto) {
            return true;
        }
        if (type == null || type == TypeLista.nessunaLista) {
            logger.error(new WrapLog().message("Manca il typeLista"));
            return false;
        }

        patternCompleto = moduloCorrente != null;
        patternCompleto = switch (type) {
            case giornoNascita, giornoMorte, annoNascita, annoMorte -> patternCompleto && textService.isValid(titoloPagina);
            case attivitaSingolare, nazionalitaSingolare -> patternCompleto;
            case attivitaPlurale, nazionalitaPlurale -> textService.isValid(titoloPagina);
            default -> patternCompleto;
        };

        if (!costruttoreValido) {
            message = String.format("Non è valido il costruttore di %s", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message));
            return false;
        }
        if (!patternCompleto) {
            message = String.format("Pattern non completo di %s", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message));
            return false;
        }

        return patternCompleto;
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
     *
     * @return -1 se il pattern della classe non è valido, zero se i dati sono validi ma non ci sono biografie <br>
     */
    public int numBio() {
        if (!checkValiditaPattern()) {
            return INT_ERROR;
        }

        return switch (type) {
            case giornoNascita -> bioMongoModulo.countAllByGiornoNato(nomeLista);
            case giornoMorte -> bioMongoModulo.countAllByGiornoMorto(nomeLista);
            case annoNascita -> bioMongoModulo.countAllByAnnoNato(nomeLista);
            case annoMorte -> bioMongoModulo.countAllByAnnoMorto(nomeLista);
            case attivitaSingolare -> bioMongoModulo.countAllByAttivitaSingolare(nomeLista);
            case attivitaPlurale -> bioMongoModulo.countAllByAnnoMorto(nomeLista);
            case nazionalitaSingolare -> bioMongoModulo.countAllByAnnoMorto(nomeLista);
            case nazionalitaPlurale -> bioMongoModulo.countAllByAnnoMorto(nomeLista);
            default -> 0;
        };
    }


    /**
     * Lista ordinata delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     *
     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
     */
    public List<BioMongoEntity> listaBio() {
        if (!checkValiditaPattern()) {
            return null;
        }

        return switch (type) {
            case giornoNascita -> bioMongoModulo.findAllByGiornoNato(nomeLista);
            case giornoMorte -> bioMongoModulo.findAllByGiornoMorto(nomeLista);
            case annoNascita -> bioMongoModulo.findAllByAnnoNato(nomeLista);
            case annoMorte -> bioMongoModulo.findAllByAnnoMorto(nomeLista);
            case attivitaSingolare -> bioMongoModulo.findAllByAttivitaSingolare(nomeLista);
            case attivitaPlurale -> bioMongoModulo.findAllByAttivitaSingolare(nomeLista);
            case nazionalitaSingolare -> bioMongoModulo.findAllByAttivitaSingolare(nomeLista);
            case nazionalitaPlurale -> bioMongoModulo.findAllByAttivitaSingolare(nomeLista);
            default -> null;
        };
    }


    /**
     * Lista ordinata di tutti i wrapLista che hanno una valore valido per la pagina specifica <br>
     *
     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
     */
    public List<WrapDidascalia> listaWrapDidascalie() {
        WrapDidascalia wrap;

        if (!checkValiditaPattern()) {
            return null;
        }

        listaWrapDidascalie = new ArrayList<>();
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
        List<String> listaTestoDidascalie;

        if (!checkValiditaPattern()) {
            return null;
        }

        listaTestoDidascalie = new ArrayList<>();
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
        LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaPrima;
        LinkedHashMap<String, List<String>> mappaSeconda;
        List<String> listaTerza;
        String keyUno;
        String keyDue;
        String keyTre;

        if (!checkValiditaPattern()) {
            return null;
        }

        mappaCompleta = new LinkedHashMap<>();
        if (listaWrapDidascalie == null || listaWrapDidascalie.size() == 0) {
            listaWrapDidascalie = listaWrapDidascalie();
        }
        if (listaWrapDidascalie != null) {
            for (WrapDidascalia wrap : listaWrapDidascalie) {
                //--primo livello - paragrafi
                keyUno = wrap.getPrimoLivello();
                if (!mappaCompleta.containsKey(keyUno)) {
                    mappaPrima = new LinkedHashMap<>();
                    mappaCompleta.put(keyUno, mappaPrima);
                }

                //--secondo livello - decade/lettera alfabetica iniziale
                keyDue = wrap.getSecondoLivello();
                mappaPrima = mappaCompleta.get(keyUno);
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
        }

        listaWrapDidascalie = null;
        return fixAltreInCoda(mappaCompleta);
    }

    /**
     * Sposta in coda alla mappa il paragrafo 'Altre...' (eventuale) <br>
     */
    public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> fixAltreInCoda(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappa) {
        LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaAltre;

        if (mappa == null) {
            return null;
        }

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
        List<String> keyList;

        if (!checkValiditaPattern()) {
            return null;
        }

        keyList = new ArrayList<>();
        if (mappaCompleta == null || mappaCompleta.size() == 0) {
            mappaCompleta = mappaDidascalie();
        }

        if (mappaCompleta != null && mappaCompleta.size() > 0) {
            for (String key : mappaCompleta.keySet()) {
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
        int numVociLista; //voci totali
        int numChiaviMappa; //paragrafi effettivi
        boolean usaParagrafi = false;
        int numVociParagrafo;
        String sottoPagina;
        String vedi;
        listaSottopagine = new ArrayList<>();

        if (!checkValiditaPattern()) {
            return STRING_ERROR;
        }

        if (mappaCompleta == null || mappaCompleta.size() == 0) {
            mappaCompleta = mappaDidascalie();
        }

        if (mappaCompleta != null && mappaCompleta.size() > 0) {
            numVociLista = wikiUtilityService.getSizeMappaMappa(mappaCompleta);
            numChiaviMappa = mappaCompleta.size();
            usaParagrafi = numVociLista > sogliaSottoPagina && numChiaviMappa >= sogliaParagrafi;

            if (usaParagrafi) {
                for (String keyParagrafo : mappaCompleta.keySet()) {
                    numVociParagrafo = wikiUtilityService.getSizeMappa(mappaCompleta.get(keyParagrafo));

                    //titolo con/senza dimensione
                    if (usaDimensioneParagrafi) {
                        //titolo con/senza includeOnly
                        if (usaIncludeSottoMax && numVociLista < sogliaIncludeAll) {
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
                    if (usaSottopagine && numVociLista > sogliaPaginaGiorniAnni && numVociParagrafo > sogliaSottoPagina) {
                        sottoPagina = String.format("%s%s%s", textService.primaMaiuscola(titoloPagina), SLASH, keyParagrafo);

                        vedi = String.format("{{Vedi anche|%s}}", sottoPagina);
                        buffer.append(vedi + CAPO);

                        listaSottopagine.add(keyParagrafo);
                    }
                    else {
                        buffer.append(bodyParagrafo(keyParagrafo, true));
                    }
                }
            }
            else {
                //corpo unico senza paragrafi e senza sottopagine
                buffer.append(bodyAll());
            }
        }

        bodyText = buffer.toString().trim();
        return bodyText;
    }


    /**
     * Testo della pagina <br>
     */
    public String bodyAll() {
        StringBuffer buffer = new StringBuffer();
        int numVociTotali = wikiUtilityService.getSizeMappaMappa(mappaCompleta);
        boolean usaDiv = numVociTotali > sogliaDiv;

        if (usaDiv) {
            buffer.append(DIV_INI_CAPO);
        }
        for (String keyParagrafo : mappaCompleta.keySet()) {
            buffer.append(bodyParagrafo(keyParagrafo, false));
        }
        if (usaDiv) {
            buffer.append(DIV_END_CAPO);
        }

        return buffer.toString();
    }

    /**
     * Testo del paragrafo <br>
     */
    public String bodyParagrafo(String keyParagrafo, boolean usaDivisori) {
        LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaParagrafo = mappaCompleta.get(keyParagrafo);
        StringBuffer buffer = new StringBuffer();
        int numVociParagrafo = wikiUtilityService.getSizeMappa(mappaParagrafo);
        boolean usaDiv = usaDivisori && numVociParagrafo > sogliaDiv;

        if (numVociParagrafo > 0) {
            if (usaDiv) {
                buffer.append(DIV_INI_CAPO);
            }
            for (String secondoLivello : mappaParagrafo.keySet()) {
                for (String terzoLivello : mappaParagrafo.get(secondoLivello).keySet()) {
                    for (String didascalia : mappaParagrafo.get(secondoLivello).get(terzoLivello)) {
                        buffer.append(ASTERISCO);
                        buffer.append(didascalia);
                        buffer.append(CAPO);
                    }
                }
            }
            if (usaDiv) {
                buffer.append(DIV_END_CAPO);
            }
        }

        return buffer.toString();
    }

    public TypeLista getType() {
        return type;
    }

    public List<String> listaSottopagine() {

        if (!checkValiditaPattern()) {
            return null;
        }

        if (textService.isEmpty(bodyText)) {
            bodyText();
        }
        return listaSottopagine;
    }


    public String getTestoSottopagina(String keySottopagina) {
        StringBuffer buffer = new StringBuffer();
        int numVociSottopagina;
        boolean usaDiv;

        if (!checkValiditaPattern()) {
            return STRING_ERROR;
        }

        if (textService.isEmpty(bodyText)) {
            bodyText();
        }
        numVociSottopagina = wikiUtilityService.getSizeMappa(mappaCompleta.get(keySottopagina));
        usaDiv = numVociSottopagina > sogliaDiv;

        if (usaDiv) {
            buffer.append(DIV_INI_CAPO);
        }
        buffer.append(bodyParagrafo(keySottopagina, false));
        if (usaDiv) {
            buffer.append(DIV_END_CAPO);
        }

        return buffer.toString();
    }

    public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getMappaSottopagina(String keySottopagina) {
        if (textService.isEmpty(bodyText)) {
            bodyText();
        }
        return mappaCompleta.get(keySottopagina);
    }

    /**
     * Numero delle biografie (Bio) che hanno una valore valido per il paragrafo (sottopagina) specifico <br>
     * Prima esegue una query diretta al database (più veloce)
     * Se non trova nulla controlla la mappaCompleta (creandola se manca) per vedere se esiste il paragrafo/sottopagina
     *
     * @return -1 se il pattern della classe non è valido o se nella mappa non esiste il paragrafo indicato come keySottopagina, zero se i dati sono validi ma non ci sono biografie <br>
     */
    public int numBio(String keySottopagina) {
        int numBioSottopagina = INT_ERROR;

        if (!checkValiditaPattern()) {
            return numBioSottopagina;
        }

        numBioSottopagina = switch (type) {
            case giornoNascita -> bioMongoModulo.countAllByGiornoNato(nomeLista);
            case giornoMorte -> bioMongoModulo.countAllByGiornoMorto(nomeLista);
            case annoNascita -> bioMongoModulo.countByAnnoNatoAndMese(nomeLista, keySottopagina);
            case annoMorte -> bioMongoModulo.countByAnnoMortoAndMese(nomeLista, keySottopagina);
            case attivitaSingolare -> bioMongoModulo.countAllByAttivitaSingolare(nomeLista);
            case attivitaPlurale -> bioMongoModulo.countAllByAnnoMorto(nomeLista);
            case nazionalitaSingolare -> bioMongoModulo.countAllByAnnoMorto(nomeLista);
            case nazionalitaPlurale -> bioMongoModulo.countAllByAnnoMorto(nomeLista);
            default -> INT_ERROR;
        };

        if (numBioSottopagina > 0) {
            return numBioSottopagina;
        }

        if (mappaCompleta == null || mappaCompleta.size() == 0) {
            mappaCompleta = mappaDidascalie();
        }
        if (!mappaCompleta.containsKey(keySottopagina)) {
            return INT_ERROR;
        }

        return wikiUtilityService.getSizeMappa(mappaCompleta.get(keySottopagina));
    }

    public LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaSottopagina(String keySottopagina) {
        if (textService.isEmpty(bodyText)) {
            bodyText();
        }

        return mappaCompleta.get(keySottopagina);
    }

}

