package it.algos.wiki24.backend.liste;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.packages.nome.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Fri, 03-Jun-2022
 * Time: 16:08
 * <p>
 * Classe specializzata nella creazione di liste. <br>
 * La lista è una mappa di WrapLista suddivisa in paragrafi, che contiene tutte le informazioni per scrivere le righe della pagina <br>
 * <p>
 * Liste cronologiche (in namespace principale) di nati e morti nel giorno o nell'anno <br>
 * Liste di nomi e cognomi (in namespace principale) <br>
 * Liste di attività e nazionalità (in Progetto:Biografie) <br>
 * Sovrascritta nelle sottoclassi concrete <br>
 */
public abstract class Lista implements AlgosCheckCostruttore, AlgosBuilderPattern {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ArrayService arrayService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ElaboraService elaboraService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public DidascaliaService didascaliaService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AttPluraleBackend attPluraleBackend;

    @Autowired
    public AttSingolareBackend attSingolareBackend;

    @Autowired
    public LogService logService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public NazPluraleBackend nazPluraleBackend;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public BioService bioService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public BioBackend bioBackend;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public WikiUtility wikiUtility;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AnnoWikiBackend annoWikiBackend;

    @Autowired
    public GiornoWikiBackend giornoWikiBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public GiornoBackend giornoBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AnnoBackend annoBackend;

    @Autowired
    public NomeBackend nomeBackend;

    @Autowired
    public CognomeBackend cognomeBackend;


    //--property
    public List<String> listaNomiSingoli;

    /**
     * Lista ordinata (per cognome) delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     * La lista è ordinata per cognome <br>
     */
    protected List<Bio> listaBio;

    /**
     * Lista ordinata dei wrapper (WrapDidascalia) per gestire i dati necessari ad una didascalia <br>
     */
    protected List<WrapDidascalia> listaWrapDidascalie;

    /**
     * Mappa ordinata dei wrapper (WrapDidascalia) per gestire i dati necessari ad una didascalia <br>
     */
    protected LinkedHashMap<String, LinkedHashMap<String, List<WrapDidascalia>>> mappaWrapDidascalie;

    /**
     * Mappa ordinata delle didascalie che hanno una valore valido per la pagina specifica <br>
     * La mappa è composta da una chiave (ordinata) che corrisponde al futuro titolo del paragrafo <br>
     * Ogni valore della mappa è costituito da una lista di didascalie per ogni paragrafo <br>
     * La visualizzazione dei paragrafi può anche essere esclusa, ma questi sono comunque presenti <br>
     */
    protected LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaDidascalieOld;

    /**
     * Mappa dei paragrafi delle didascalie che hanno una valore valido per la pagina specifica <br>
     * La mappa è composta da una chiave (ordinata) che è il titolo visibile del paragrafo <br>
     * Ogni valore della mappa è costituito da una lista di didascalie per ogni paragrafo <br>
     * La visualizzazione dei paragrafi può anche essere esclusa, ma questi sono comunque presenti <br>
     */
    protected LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaParagrafi;

    /**
     * Mappa dei paragrafi delle didascalie che hanno una valore valido per la pagina specifica <br>
     * La mappa è composta da una chiave (ordinata) che è il titolo visibile del paragrafo <br>
     * Nel titolo visibile del paragrafo viene riportato il numero di voci biografiche presenti <br>
     * Ogni valore della mappa è costituito da una lista di didascalie per ogni paragrafo <br>
     * La visualizzazione dei paragrafi può anche essere esclusa, ma questi sono comunque presenti <br>
     */
    protected LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaParagrafiDimensionati;

    /**
     * Lista ordinata dei wrapper (WrapLista) per gestire i dati necessari ad una didascalia <br>
     */
    protected List<WrapLista> listaWrap;

    protected LinkedHashMap<String, List<WrapLista>> mappaWrap;

    protected LinkedHashMap<String, List<String>> mappaDidascalia;

    public static Function<WrapDidascalia, String> funCognome = wrap -> wrap.getCognome() != null ? wrap.getCognome() : VUOTA;

    public static Function<WrapDidascalia, String> funWikiTitle = wrap -> wrap.getWikiTitle() != null ? wrap.getWikiTitle() : VUOTA;

    public static Function<WrapDidascalia, String> funNazionalita = wrap -> wrap.getNazionalitaSingola() != null ? wrap.getNazionalitaSingola() : VUOTA;

    public static Function<WrapDidascalia, String> funAttivita = wrap -> wrap.getAttivitaSingola() != null ? wrap.getAttivitaSingola() : VUOTA;

    public static Function<WrapDidascalia, String> funAnnoNato = wrap -> wrap.getAnnoNato() != null ? wrap.getAnnoNato() : VUOTA;

    public static Function<WrapDidascalia, String> funAnnoMorto = wrap -> wrap.getAnnoMorto() != null ? wrap.getAnnoMorto() : VUOTA;

    public static Function<WrapDidascalia, String> funGiornoNato = wrap -> wrap.getGiornoNato() != null ? wrap.getGiornoNato() : VUOTA;

    public static Function<WrapDidascalia, String> funGiornoMorto = wrap -> wrap.getGiornoMorto() != null ? wrap.getGiornoMorto() : VUOTA;

    public String nomeLista;

    public String titoloPagina;

    public AETypeLista typeLista;

    public AETypeLink typeLinkParagrafi;

    public AETypeLink typeLinkCrono;

    public String paragrafoAltre = VUOTA;

    public boolean usaIcona;

    protected boolean costruttoreValido = false;

    protected boolean istanzaValida = false;

    protected boolean accettaCostruttoreSenzaParametri = false;

    protected CrudBackend backend;
    //    protected boolean costruttoreNecessitaUnParametro = false;
    //
    //    protected boolean costruttoreNonAccettaParametri = false;


    /**
     * Costruttore base <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(ListaGiorni.class, nomeGiorno) <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public Lista(String nomeLista) {
        this.nomeLista = nomeLista;
    }// end of constructor


    @PostConstruct
    protected void postConstruct() {
        this.fixPreferenze();
        this.checkValiditaCostruttore();
    }


    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.usaIcona = WPref.usaSimboliCrono.is();
        this.typeLinkCrono = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
    }

    protected void checkValiditaCostruttore() {
        if (backend != null) {
            this.costruttoreValido = backend.isExistByKey(textService.primaMaiuscola(nomeLista)) || backend.isExistByKey(textService.primaMinuscola(nomeLista));
        }
        else {
            String message = String.format("Manca il backend in fixPreferenze() di %s", this.getClass().getSimpleName());
            logService.error(new WrapLog().message(message));
            this.costruttoreValido = false;
        }
    }


    /**
     * Pattern Builder <br>
     */
    public Lista typeLista(AETypeLista typeLista) {
        this.typeLista = typeLista;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Lista typeLinkParagrafi(AETypeLink typeLinkParagrafi) {
        this.typeLinkParagrafi = typeLinkParagrafi;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Lista typeLinkCrono(AETypeLink typeLinkCrono) {
        this.typeLinkCrono = typeLinkCrono;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Lista icona(boolean usaIcona) {
        this.usaIcona = usaIcona;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Lista icona() {
        this.icona(true);
        return this;
    }

    @Override
    public boolean isValida() {
        return this.istanzaValida;
    }

    public boolean isCostruttoreValido() {
        return this.costruttoreValido;
    }

    /**
     * Lista ordinata delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     */
    public List<Bio> listaBio() {
        String message;

        if (textService.isEmpty(nomeLista)) {
            message = String.format("Un'istanza della classe [%s] SENZA parametri non funziona e non può essere utilizzata.", this.getClass().getSimpleName());
            logger.warn(new WrapLog().message(message));
            return null;
        }

        if (typeLista == null) {
            message = String.format("Manca il '%s' della classe [%s] e il metodo '%s' NON può funzionare.", "typeLista", this.getClass().getSimpleName(), "listaBio");
            logger.warn(new WrapLog().message(message));
            return null;
        }

        try {
            listaBio = bioService.fetchListe(typeLista, nomeLista);
        } catch (Exception unErrore) {
            return null;
        }

        return listaBio;
    }

    /**
     * Numero di biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     */
    public int getSize() {
        return listaBio() != null ? listaBio.size() : 0;
    }


    /**
     * Lista ordinata di tutti i wrapLista che hanno una valore valido per la pagina specifica <br>
     */
    public List<WrapLista> listaWrap() {
        listaWrap = new ArrayList<>();
        WrapLista wrap;

        if (listaBio == null || listaBio.size() == 0) {
            listaBio();
        }

        if (listaBio != null && listaBio.size() > 0) {
            if (typeLinkParagrafi == null) {
                logger.error(new WrapLog().exception(new AlgosException("Manca typeLinkParagrafi")));
                return null;
            }
            if (typeLinkCrono == null) {
                logger.error(new WrapLog().exception(new AlgosException("Manca typeLinkCrono")));
                return null;
            }

            for (Bio bio : listaBio) {
                wrap = didascaliaService.getWrap(titoloPagina, bio, typeLista, typeLinkParagrafi, typeLinkCrono, usaIcona);

                if (wrap != null) {
                    listaWrap.add(wrap);
                }
            }
        }

        return listaWrap;
    }


    /**
     * Mappa ordinata di tutti i wrapLista che hanno una valore valido per la pagina specifica <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public LinkedHashMap<String, List<WrapLista>> mappaWrap() {
        mappaWrap = new LinkedHashMap<>();
        String paragrafo;
        List<WrapLista> lista;

        if (listaWrap == null || listaWrap.size() == 0) {
            this.listaWrap();
        }

        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapLista wrap : listaWrap) {
                paragrafo = wrap.titoloParagrafo;

                if (mappaWrap.containsKey(paragrafo)) {
                    lista = mappaWrap.get(paragrafo);
                }
                else {
                    lista = new ArrayList();
                }
                lista.add(wrap);
                mappaWrap.put(paragrafo, lista);
            }
        }

        mappaWrap = sortMap(mappaWrap);
        mappaWrap = fixAltreInCoda(mappaWrap);
        return mappaWrap;
    }

    public List<WrapLista> getWrapLista(String keyParagrafo) {
        if (mappaWrap == null) {
            mappaWrap();
        }

        return getWrapLista(mappaWrap, keyParagrafo);
    }

    public List<WrapLista> getWrapLista(LinkedHashMap<String, List<WrapLista>> mappa, String keyParagrafo) {
        List<WrapLista> listaWrap = null;

        if (mappa != null) {
            if (mappa.containsKey(textService.primaMaiuscola(keyParagrafo))) {
                listaWrap = mappa.get(textService.primaMaiuscola(keyParagrafo));
            }
        }

        return listaWrap;
    }

    /**
     * Ordina la mappa <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    public LinkedHashMap<String, List<WrapLista>> sortMap(LinkedHashMap<String, List<WrapLista>> mappa) {
        return wikiUtility.sort(mappa);
    }

    /**
     * Sposta in coda alla mappa il paragrafo 'Altre...' (eventuale) <br>
     */
    public LinkedHashMap<String, List<WrapLista>> fixAltreInCoda(LinkedHashMap<String, List<WrapLista>> mappa) {
        List<WrapLista> listaAltre;

        if (mappa.containsKey(paragrafoAltre)) {
            listaAltre = mappa.get(paragrafoAltre);
            mappa.remove(paragrafoAltre);
            mappa.put(paragrafoAltre, listaAltre);
        }

        return mappa;
    }

    /**
     * Mappa ordinata di tutti le didascalie che hanno una valore valido per la pagina specifica <br>
     * Le didascalie usano SPAZIO_NON_BREAKING al posto di SPAZIO (se previsto) <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Deprecated
    public LinkedHashMap<String, List<String>> mappaDidascalia() {
        mappaDidascalia = new LinkedHashMap<>();
        String didascalia;
        List<WrapLista> listaWrap;
        List<String> listaDidascalie;

        if (mappaWrap == null || mappaWrap.size() == 0) {
            this.mappaWrap();
        }

        if (mappaWrap != null && mappaWrap.size() > 0) {
            for (String paragrafo : mappaWrap.keySet()) {
                listaWrap = mappaWrap.get(paragrafo);
                if (listaWrap != null) {
                    listaDidascalie = new ArrayList<>();
                    for (WrapLista wrap : listaWrap) {
                        didascalia = switch (typeLista) {
                            case giornoNascita, giornoMorte, annoNascita, annoMorte -> wrap.didascalia;
                            case nazionalitaSingolare, nazionalitaPlurale -> wrap.didascaliaBreve;
                            default -> VUOTA;
                        };
                        if (Pref.usaNonBreaking.is()) {
                            didascalia = didascalia.replaceAll(SPAZIO, SPAZIO_NON_BREAKING);
                        }
                        listaDidascalie.add(didascalia);
                    }
                    mappaDidascalia.put(paragrafo, listaDidascalie);
                }
            }
        }

        return mappaDidascalia;
    }


    /**
     * Testo del body di upload con paragrafi e righe <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public WResult testoBody() {
        String testoBody = VUOTA;

        if (mappaDidascalia == null || mappaDidascalia.size() > 0) {
            this.mappaDidascalia();
        }
        return WResult.errato();
    }


}
