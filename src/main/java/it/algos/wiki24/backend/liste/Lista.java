package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import jakarta.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;

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

    boolean usaSottopaginaOltreMax;

    boolean isSottopagina;

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
    }

    /**
     * Pattern Builder <br>
     */
    public Lista type(TypeLista type) {
        this.type = type;

        this.moduloCorrente = switch (type) {
            case giornoNascita, giornoMorte -> giornoModulo;
            case annoNascita, annoMorte -> annoModulo;
            default -> null;
        };

        this.titoloPagina = switch (type) {
            case giornoNascita -> wikiUtilityService.wikiTitleNatiGiorno(nomeLista);
            case giornoMorte -> wikiUtilityService.wikiTitleMortiGiorno(nomeLista);
            case annoNascita -> wikiUtilityService.wikiTitleNatiAnno(nomeLista);
            case annoMorte -> wikiUtilityService.wikiTitleMortiAnno(nomeLista);
            default -> null;
        };

        this.usaSottopaginaOltreMax = switch (type) {
            case giornoNascita, giornoMorte -> false;
            case annoNascita, annoMorte -> true;
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
    public Lista isSottopagina() {
        if (usaSottopaginaOltreMax) {
            this.isSottopagina = true;
        }

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
        //        if (moduloCorrente != null) {
        //            this.costruttoreValido = moduloCorrente.existByKey(textService.primaMaiuscola(nomeLista)) || moduloCorrente.existByKey(textService.primaMinuscola(nomeLista));
        //        }
        //        else {
        //            message = String.format("Manca il modulo in fixPreferenze() di %s", this.getClass().getSimpleName());
        //            logger.error(new WrapLog().message(message));
        //            this.costruttoreValido = false;
        //        }
        //
        //        if (this.type == TypeLista.nessunaLista) {
        //            message = String.format("Manca il type della lista in fixPreferenze() di %s", this.getClass().getSimpleName());
        //            logger.error(new WrapLog().message(message));
        //            this.costruttoreValido = false;
        //        }
        //
        //        this.collectionName = costruttoreValido ? annotationService.getCollectionName(BioMongoEntity.class) : VUOTA;
        costruttoreValido = textService.isValid(nomeLista);
    }

    protected boolean checkValiditaPattern() {
        if (costruttoreValido && patternCompleto) {
            return true;
        }
        if (type == TypeLista.nessunaLista) {
            logger.error(new WrapLog().message("Manca il typeLista"));
            return false;
        }

        patternCompleto = moduloCorrente != null;
        patternCompleto = patternCompleto && textService.isValid(titoloPagina);

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
        mappaCompleta = new LinkedHashMap<>();
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

        listaWrapDidascalie = null;
        return fixAltreInCoda(mappaCompleta);
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
        if (mappaCompleta == null || mappaCompleta.size() == 0) {
            mappaCompleta = mappaDidascalie();
        }

        if (mappaCompleta != null && mappaCompleta.size() > 0) {
            keyList = new ArrayList<>();
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
        int numMinParagrafi = 2; //@todo passare a preferenza
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
        listaSottopagine = new ArrayList<>();

        if (mappaCompleta == null || mappaCompleta.size() == 0) {
            mappaCompleta = mappaDidascalie();
        }

        if (mappaCompleta != null && mappaCompleta.size() > 0) {
            numVociLista = wikiUtilityService.getSizeMappaMappa(mappaCompleta);
            numChiaviMappa = mappaCompleta.size();
            usaParagrafi = numVociLista > maxVociPerParagrafo && numChiaviMappa > numMinParagrafi;

            if (usaParagrafi) {
                for (String keyParagrafo : mappaCompleta.keySet()) {
                    numVociParagrafo = wikiUtilityService.getSizeMappa(mappaCompleta.get(keyParagrafo));
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

                        listaSottopagine.add(keyParagrafo);
                    }
                    else {
                        if (usaDiv) {
                            buffer.append(DIV_INI_CAPO);
                        }
                        buffer.append(bodyParagrafo(keyParagrafo));
                        if (usaDiv) {
                            buffer.append(DIV_END_CAPO);
                        }
                    }
                }
            }
            else {
                //corpo unico senza paragrafi e senza sottopagine
                numVociParagrafo = wikiUtilityService.getSizeMappaMappa(mappaCompleta);
                usaDiv = numVociParagrafo >= maxVociPerUnaColonna;
                if (usaDiv) {
                    buffer.append(DIV_INI_CAPO);
                }
                buffer.append(bodyAll());
                if (usaDiv) {
                    buffer.append(DIV_END_CAPO);
                }
            }
        }

        mappaCompleta = null;
        bodyText = buffer.toString().trim();
        return bodyText;
    }


    /**
     * Testo della pagina <br>
     */
    public String bodyAll() {
        StringBuffer buffer = new StringBuffer();

        for (String keyParagrafo : mappaCompleta.keySet()) {
            buffer.append(bodyParagrafo(keyParagrafo));
        }

        return buffer.toString();
    }

    /**
     * Testo del paragrafo <br>
     */
    public String bodyParagrafo(String keyParagrafo) {
        LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaParagrafo = mappaCompleta.get(keyParagrafo);
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

    public List<String> listaSottopagine() {
        if (textService.isEmpty(bodyText)) {
            bodyText();
        }
        return listaSottopagine;
    }

    public LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaSottopagina(String keyParagrafo) {
        if (textService.isEmpty(bodyText)) {
            bodyText();
        }

        return mappaCompleta.get(keyParagrafo);
    }

}

