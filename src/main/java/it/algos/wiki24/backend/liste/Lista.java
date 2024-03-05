package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
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
import org.springframework.context.annotation.Scope;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 17-Feb-2024
 * Time: 06:31
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Lista {

    @Inject
    protected ApplicationContext appContext;

    @Inject
    protected TextService textService;

    @Inject
    protected LogService logger;

    @Inject
    protected BioMongoModulo bioMongoModulo;

    @Inject
    protected WikiUtilityService wikiUtilityService;

    @Inject
    protected ArrayService arrayService;

    @Inject
    DidascaliaService didascaliaService;

    protected String titoloPagina;

    protected String nomeLista;

    protected TypeLista typeLista;

    protected List<WrapDidascalia> listaWrapDidascalie = new ArrayList<>();

    //    protected boolean usaParagrafiLista;

    protected boolean usaDimensioneParagrafi;

    protected boolean usaIncludeParagrafi;

    protected boolean usaParagrafiSottoPagina;

    protected boolean usaSottoPagineLista;

    protected boolean usaSottoSottoPagineLista;

    protected int sogliaDiv;

    protected int sogliaParagrafi;

    protected int sogliaIncludeAll;

    protected int sogliaSottoPagina;

    protected int sogliaSottoSottoPagina;

    protected int sogliaVociTotaliPaginaPerSottopagine;

    protected int numBio = INT_ERROR;

    protected String bodyText = VUOTA;

    protected TypeLivello typeLivello;

    /**
     * Lista ordinata (per cognome) delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     * La lista è ordinata per cognome <br>
     */
    protected List<BioMongoEntity> listaBio = new ArrayList<>();

    protected Map<String, WrapLista> mappaGenerale = new LinkedHashMap<>();

    protected Map<String, Object> mappaChiavi = new LinkedHashMap<>();

    protected Map<String, List<WrapDidascalia>> mappaWrapDidascalie2 = new LinkedHashMap<>();

    // titolo del paragrafo e numero di voci del paragrafo stesso
    protected Map<String, Integer> mappaParagrafi = new LinkedHashMap<>();

    // titolo della sottoPagina
    protected List<String> listaSottoPagine = new ArrayList<>();

    // titolo della sottoPagina
    protected List<String> listaSottoSottoPagine = new ArrayList<>();

    protected Map<String, String> mappaBodySottoPagine = new LinkedHashMap<>();

    /**
     * Costruttore base con 2 parametri (obbligatori) <br>
     * Not annotated with @Autowired annotation, classe astratta <br>
     * La classe usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public Lista(final String nomeLista, TypeLista typeLista) {
        this.nomeLista = nomeLista;
        this.typeLista = typeLista;
    }// end of constructor not @Autowired and used


    @PostConstruct
    protected void postConstruct() {
        this.fixPreferenze();
        this.fixRegolazioni();
        this.numBio();
        this.listaBio();
        this.listaWrapDidascalie();
        this.mappaGenerale();
        //        this.mappaWrapDidascalie();
        this.bodyText();
        this.bodyTextSottoPagine();
    }

    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.usaDimensioneParagrafi = true;
        this.usaIncludeParagrafi = false;
        this.sogliaDiv = WPref.sogliaDiv.getInt();
        this.sogliaParagrafi = WPref.sogliaParagrafi.getInt();
        this.sogliaIncludeAll = WPref.sogliaIncludeAll.getInt();
        this.sogliaSottoPagina = WPref.sogliaSottoPagina.getInt();
        this.sogliaSottoSottoPagina = MAX_INT_VALUE;
    }


    public void fixRegolazioni() {

        this.titoloPagina = switch (typeLista) {
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

        this.usaIncludeParagrafi = switch (typeLista) {
            case giornoNascita, giornoMorte -> true;
            case annoNascita, annoMorte -> true;
            case attivitaSingolare, attivitaPlurale -> false;
            case nazionalitaSingolare, nazionalitaPlurale -> false;
            default -> false;
        };

        this.usaSottoPagineLista = switch (typeLista) {
            case giornoNascita, giornoMorte -> WPref.usaSottopagineGiorni.is();
            case annoNascita, annoMorte -> WPref.usaSottopagineAnni.is();
            case attivitaSingolare, attivitaPlurale -> WPref.usaSottopagineAttivita.is();
            case nazionalitaSingolare, nazionalitaPlurale -> WPref.usaSottopagineNazionalita.is();
            default -> false;
        };

        this.sogliaVociTotaliPaginaPerSottopagine = switch (typeLista) {
            case giornoNascita, giornoMorte -> WPref.sogliaSottoPaginaGiorniAnni.getInt();
            case annoNascita, annoMorte -> WPref.sogliaSottoPaginaGiorniAnni.getInt();
            case attivitaSingolare, attivitaPlurale -> WPref.sogliaSottoPagina.getInt();
            case nazionalitaSingolare, nazionalitaPlurale -> WPref.sogliaSottoPagina.getInt();
            default -> 0;
        };

        this.usaParagrafiSottoPagina = switch (typeLista) {
            case giornoNascita, giornoMorte -> false;
            case annoNascita, annoMorte -> false;
            case attivitaSingolare -> false;
            case attivitaPlurale -> true;
            case nazionalitaSingolare -> false;
            case nazionalitaPlurale -> true;
            default -> false;
        };

        this.usaSottoSottoPagineLista = switch (typeLista) {
            case giornoNascita, giornoMorte -> false;
            case annoNascita, annoMorte -> false;
            case attivitaSingolare -> false;
            case attivitaPlurale -> true;
            case nazionalitaSingolare -> false;
            case nazionalitaPlurale -> true;
            default -> false;
        };

        this.sogliaSottoSottoPagina = switch (typeLista) {
            case giornoNascita, giornoMorte -> MAX_INT_VALUE;
            case annoNascita, annoMorte -> MAX_INT_VALUE;
            case attivitaSingolare, attivitaPlurale -> WPref.sogliaSottoPaginaAttNaz.getInt();
            case nazionalitaSingolare, nazionalitaPlurale -> WPref.sogliaSottoPaginaAttNaz.getInt();
            default -> MAX_INT_VALUE;
        };

        this.typeLivello = typeLista != null ? typeLista.getTypeLivello() : TypeLivello.vuota;
    }

    /**
     * Numero delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     * Rimanda direttamente al metodo dedicato del service BioMongoModulo, SENZA nessuna elaborazione in questa classe <br>
     *
     * @return -1 se il pattern della classe non è valido, zero se i dati sono validi ma non ci sono biografie <br>
     */
    protected int numBio() {
        if (bioMongoModulo == null || textService.isEmpty(nomeLista)) {
            return INT_ERROR;
        }

        this.numBio = switch (typeLista) {
            case giornoNascita -> bioMongoModulo.countAllByGiornoNato(nomeLista);
            case giornoMorte -> bioMongoModulo.countAllByGiornoMorto(nomeLista);
            case annoNascita -> bioMongoModulo.countAllByAnnoNato(nomeLista);
            case annoMorte -> bioMongoModulo.countAllByAnnoMorto(nomeLista);
            case attivitaSingolare -> bioMongoModulo.countAllByAttivitaSingolare(nomeLista);
            case attivitaPlurale -> bioMongoModulo.countAllByAttivitaPlurale(nomeLista);
            case nazionalitaSingolare -> bioMongoModulo.countAllByNazionalitaSingolare(nomeLista);
            case nazionalitaPlurale -> bioMongoModulo.countAllByNazionalitaPlurale(nomeLista);
            default -> 0;
        };

        return numBio;
    }


    /**
     * Lista ordinata delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     * Rimanda direttamente al metodo dedicato del service BioMongoModulo, SENZA nessuna elaborazione in questa classe <br>
     *
     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
     */
    protected List<BioMongoEntity> listaBio() {
        if (bioMongoModulo == null || textService.isEmpty(nomeLista)) {
            return null;
        }

        this.listaBio = switch (typeLista) {
            case giornoNascita -> bioMongoModulo.findAllByGiornoNato(nomeLista);
            case giornoMorte -> bioMongoModulo.findAllByGiornoMorto(nomeLista);
            case annoNascita -> bioMongoModulo.findAllByAnnoNato(nomeLista);
            case annoMorte -> bioMongoModulo.findAllByAnnoMorto(nomeLista);
            case attivitaSingolare -> bioMongoModulo.findAllByAttivitaSingolare(nomeLista);
            case attivitaPlurale -> bioMongoModulo.findAllByAttivitaPlurale(nomeLista);
            case nazionalitaSingolare -> bioMongoModulo.findAllByNazionalitaSingolare(nomeLista);
            case nazionalitaPlurale -> bioMongoModulo.findAllByNazionalitaPlurale(nomeLista);
            default -> null;
        };

        return listaBio;
    }

    /**
     * Lista ordinata di tutti i wrapLista che hanno una valore valido per la pagina specifica <br>
     * Costruisce un wrap per ogni elemento della listaBio recuperata da BioMongoModulo <br>
     *
     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
     */
    protected List<WrapDidascalia> listaWrapDidascalie() {
        WrapDidascalia wrap;

        if (listaBio == null || listaBio.size() == 0) {
            listaBio = listaBio();
        }

        if (listaBio != null && listaBio.size() > 0) {
            for (BioMongoEntity bio : listaBio) {
                wrap = appContext.getBean(WrapDidascalia.class).type(typeLista).get(bio);
                if (wrap != null) {
                    listaWrapDidascalie.add(wrap);
                }
            }
        }

        return listaWrapDidascalie;
    }

    /**
     * Lista ordinata di tutte le didascalie che hanno una valore valido per la pagina specifica <br>
     * Estrae la didascalia da ogni elemento della listaWrapDidascalie di questa classe <br>
     * Lista esemplificativa fine a se stessa che NON viene utilizzata da bodyText che usa direttamente listaWrapDidascalie <br>
     *
     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
     */
    public List<String> listaTestoDidascalie() {
        List<String> listaTestoDidascalie;

        listaTestoDidascalie = new ArrayList<>();
        for (WrapDidascalia wrap : listaWrapDidascalie) {
            listaTestoDidascalie.add(wrap.getDidascalia());
        }

        return listaTestoDidascalie;
    }


    /**
     * Mappa generale ordinata per tutti i paragrafi <br>
     *
     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
     */
    protected Map<String, WrapLista> mappaGenerale() {
        String key;
        WrapLista wrapLista;
        int soglia = WPref.sogliaSottoPagina.getInt();

        if (listaWrapDidascalie == null || listaWrapDidascalie.size() == 0) {
            listaWrapDidascalie = listaWrapDidascalie();
        }

        //--primo livello paragrafi (sempre)
        if (typeLivello.getLivelloParagrafi() > 0) {
            for (WrapDidascalia wrap : listaWrapDidascalie) {
                key = wrap.getPrimoLivello();

                if (!mappaGenerale.containsKey(key)) {
                    wrapLista = appContext.getBean(WrapLista.class, titoloPagina, key, soglia);
                    mappaGenerale.put(key, wrapLista);
                }

                wrapLista = mappaGenerale.get(key);
                wrapLista.add(wrap);
            }
        }

        this.fixMappaGenerale();

        //--Fix eventuale lista -> mappa se paragrafo -> sottoPagina
        //--Solo per il 1° livello. Nei livelli successivi viene fatto insieme alla scansione
        fixListaMappaPrimoLivello();

        //--secondo livello paragrafi
        if (typeLivello.getLivelloParagrafi() >= 1) {
            mappaParagrafiAndSottoPagine();
        }

        //--terzo livello - paragrafi
        if (typeLivello.getLivelloParagrafi() >= 2) {
            mappaParagrafiAndSottoSottoPagine();
        }

        Collections.sort(listaSottoSottoPagine);

        return mappaGenerale;
    }

    /**
     * Fix eventuale lista -> mappa se paragrafo -> sottoPagina <br>
     * Solo per il 1° livello. Nei livelli successivi viene fatto insieme alla scansione <br>
     */
    protected void fixListaMappaPrimoLivello() {
        WrapLista wrapListaParagrafo;
        WrapLista wrapListaSub;
        String key;
        Map<String, WrapLista> mappa;
        List<WrapDidascalia> listaWrap;
        int numBio;
        boolean usaSottoPaginaSingoloParagrafo;
        boolean usaSottoPagineGeneraleDiQuestoLivello = typeLivello.getLivelloSottoPagine() > 0;

        if (usaSottoPagineGeneraleDiQuestoLivello) {
            for (String keyParagrafo : mappaGenerale.keySet()) {
                wrapListaParagrafo = mappaGenerale.get(keyParagrafo);
                numBio = wrapListaParagrafo.getNumBio();
                usaSottoPaginaSingoloParagrafo = numBio > wrapListaParagrafo.getSogliaSottoPagina();
                if (usaSottoPaginaSingoloParagrafo) {
                    listaWrap = wrapListaParagrafo.getLista();
                    wrapListaParagrafo.usaRinvio();
                    mappa = wrapListaParagrafo.getMappa();

                    if (listaWrap != null && listaWrap.size() > 0) {
                        for (WrapDidascalia wrap : listaWrap) {
                            key = wrap.getSecondoLivello();
                            if (!mappa.containsKey(key)) {
                                wrapListaSub = appContext.getBean(WrapLista.class, titoloPagina, key);
                                mappa.put(key, wrapListaSub);
                            }

                            wrapListaSub = mappa.get(key);
                            wrapListaSub.add(wrap);
                        }
                    }
                }
            }
        }
    }

    /**
     * Rielabora la mappa per le sottoPagine - 2° livello <br>
     */
    protected void mappaParagrafiAndSottoPagine() {
        WrapLista wrapListaParagrafo;
        WrapLista wrapListaSub;
        String key;
        Map<String, WrapLista> mappa;
        List<WrapDidascalia> listaWrap;
        int soglia;
        int numBioParagrafo;
        int sogliaMinimaPaginaPerCreareSottoPagine;
        Map<String, Object> mappaChiave;
        boolean usaSottoPagine;

        sogliaMinimaPaginaPerCreareSottoPagine = switch (typeLista) {
            case giornoNascita, giornoMorte -> WPref.sogliaSottoPaginaGiorniAnni.getInt();
            case annoNascita, annoMorte -> WPref.sogliaSottoPaginaGiorniAnni.getInt();
            case attivitaPlurale -> 50;
            case nazionalitaPlurale -> 50;
            default -> MAX_INT_VALUE;
        };

        usaSottoPagine = numBio > sogliaMinimaPaginaPerCreareSottoPagine;

        //--secondo livello
        if (typeLivello.getLivelloSottoPagine() >= 1) {
            for (String keyParagrafo : mappaGenerale.keySet()) {
                wrapListaParagrafo = mappaGenerale.get(keyParagrafo);

                if (wrapListaParagrafo != null) {
                    numBioParagrafo = wrapListaParagrafo.getNumBio();
                    soglia = wrapListaParagrafo.getSogliaSottoPagina();
                    listaWrap = wrapListaParagrafo.getLista();
                    mappa = wrapListaParagrafo.getMappa();

                    if (usaSottoPagine && numBioParagrafo > soglia) {
                        if (mappa.size() < 1) {
                            for (WrapDidascalia wrap : listaWrap) {
                                key = wrap.getSecondoLivello();
                                if (!mappa.containsKey(key)) {
                                    wrapListaSub = appContext.getBean(WrapLista.class, titoloPagina, key);
                                    mappa.put(key, wrapListaSub);
                                }

                                wrapListaSub = mappa.get(key);
                                wrapListaSub.add(wrap);
                            }
                        }

                        wrapListaParagrafo.usaRinvio(true);
                        listaSottoPagine.add(keyParagrafo);
                        mappaChiave = new HashMap<>();
                        for (String keyMappa : mappa.keySet()) {
                            if (mappa.get(keyMappa).getNumBio() > soglia) {
                                mappaChiave.put(keyMappa, keyMappa);
                            }
                        }
                        mappaChiavi.put(keyParagrafo, mappaChiave);
                    }
                    else {
                        wrapListaParagrafo.usaRinvio(false);
                    }
                }
            }
        }
    }


    /**
     * Rielabora la mappa per le sottoSottoPagine - 3° livello <br>
     */
    protected void mappaParagrafiAndSottoSottoPagine() {
        WrapLista wrapListaParagrafo;
        WrapLista wrapListaSub;
        //        WrapLista wrapListaSubSub;
        List<WrapDidascalia> listaWrap;
        Map<String, WrapLista> mappa;
        int sogliaMinimaPaginaPerCreareSottoSottoPagine;
        boolean usaSottoSottoPagine;
        boolean usaRinvio;

        sogliaMinimaPaginaPerCreareSottoSottoPagine = switch (typeLista) {
            case giornoNascita, giornoMorte -> WPref.sogliaSottoPaginaGiorniAnni.getInt();
            case annoNascita, annoMorte -> WPref.sogliaSottoPaginaGiorniAnni.getInt();
            case attivitaPlurale -> 50;
            case nazionalitaPlurale -> 50;
            default -> MAX_INT_VALUE;
        };

        usaSottoSottoPagine = numBio > sogliaMinimaPaginaPerCreareSottoSottoPagine;
        if (!usaSottoSottoPagine) {
            return;
        }

        //--terzo livello
        if (typeLivello.getLivelloSottoPagine() >= 2) {
            for (String keySottoPagina : getListaSottoPagine()) {
                wrapListaParagrafo = mappaGenerale.get(keySottoPagina);
                mappa = wrapListaParagrafo.getMappa();

                // devo spazzolare la mappa creare una lista
                if (mappa != null) {
                    for (String keyParagrafoSottoPagina : mappa.keySet()) {
                        wrapListaSub = mappa.get(keyParagrafoSottoPagina);
                        usaRinvio = wrapListaSub.getNumBio() > 50;//@todo controllare typeLista
                        wrapListaParagrafo.usaRinvio(usaRinvio);
                        if (usaRinvio) {
                            listaSottoSottoPagine.add(keySottoPagina+SLASH+keyParagrafoSottoPagina);
                        }
                    }
                }
            }
        }
    }


    /**
     * Mappa ordinata di WrapDidascalie per tutti i paragrafi <br>
     *
     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
     */
    public Map<String, List<WrapDidascalia>> mappaWrapDidascalieSotto(List<WrapDidascalia> listaWrap) {
        Map<String, List<WrapDidascalia>> mappaSotto = new LinkedHashMap<>();
        String key;
        List<WrapDidascalia> listaSotto;

        for (WrapDidascalia wrap : listaWrap) {

            //--primo livello - paragrafi
            key = wrap.getSecondoLivello();
            if (!mappaSotto.containsKey(key)) {
                listaSotto = new ArrayList<>();
                mappaSotto.put(key, listaSotto);
            }

            //--didascalia
            listaSotto = mappaSotto.get(key);
            listaSotto.add(wrap);
        }

        mappaSotto = switch (typeLista) {
            case attivitaSingolare, attivitaPlurale, nazionalitaSingolare, nazionalitaPlurale -> arrayService.sort(mappaSotto);
            default -> mappaSotto;
        };

        return mappaSotto;
    }

    public void fixMappaGenerale() {
        int size = 0;
        String tagAltre;
        WrapLista wrapLista;
        List<WrapDidascalia> wrapDidascalie;

        for (String keyParagrafo : mappaGenerale.keySet()) {
            size = mappaGenerale.get(keyParagrafo).getNumBio();
            mappaParagrafi.put(keyParagrafo, size);
            mappaChiavi.put(keyParagrafo, keyParagrafo);
        }

        mappaGenerale = switch (typeLista) {
            case attivitaSingolare, attivitaPlurale, nazionalitaSingolare, nazionalitaPlurale -> arrayService.sort(mappaGenerale);
            default -> mappaGenerale;
        };

        mappaParagrafi = switch (typeLista) {
            case attivitaSingolare, attivitaPlurale, nazionalitaSingolare, nazionalitaPlurale -> arrayService.sort(mappaParagrafi);
            default -> mappaParagrafi;
        };
        mappaChiavi = switch (typeLista) {
            case attivitaSingolare, attivitaPlurale, nazionalitaSingolare, nazionalitaPlurale -> arrayService.sort(mappaChiavi);
            default -> mappaChiavi;
        };

        for (String key : mappaGenerale.keySet()) {
            wrapLista = mappaGenerale.get(key);
            wrapDidascalie = switch (typeLista) {
                case giornoNascita, giornoMorte, annoNascita, annoMorte -> wrapLista.getLista();
                case attivitaSingolare, attivitaPlurale, nazionalitaSingolare, nazionalitaPlurale ->
                        didascaliaService.ordinamentoAlfabetico(wrapLista.getLista());
                default -> wrapLista.getLista();
            };
            wrapLista.setLista(wrapDidascalie);
            mappaGenerale.put(key, wrapLista);
        }

        tagAltre = switch (typeLista) {
            case giornoNascita, giornoMorte -> TypeInesistente.giorno.getTag();
            case annoNascita, annoMorte -> TypeInesistente.anno.getTag();
            case attivitaSingolare, attivitaPlurale -> TypeInesistente.nazionalita.getTag();
            case nazionalitaSingolare, nazionalitaPlurale -> TypeInesistente.attivita.getTag();
            default -> VUOTA;
        };
        mappaGenerale = fixAltreInCoda(mappaGenerale, tagAltre);
        mappaParagrafi = fixAltreInCodaInt(mappaParagrafi, tagAltre);
    }


    /**
     * Sposta in coda alla mappa il paragrafo 'Altre...' (eventuale) <br>
     */
    public Map<String, List<WrapDidascalia>> fixAltreInCodaWrap(final Map<String, List<WrapDidascalia>> mappaIn, String tag) {
        Map<String, List<WrapDidascalia>> mappaOut = new LinkedHashMap<>();

        if (mappaIn == null) {
            return null;
        }

        for (String key : mappaIn.keySet()) {
            if (!key.equals(tag)) {
                mappaOut.put(key, mappaIn.get(key));
            }
        }

        if (mappaIn.keySet().contains(tag)) {
            mappaOut.put(tag, mappaIn.get(tag));
        }

        return mappaOut;
    }


    /**
     * Sposta in coda alla mappa il paragrafo 'Altre...' (eventuale) <br>
     */
    public Map<String, WrapLista> fixAltreInCoda(final Map<String, WrapLista> mappaIn, String tag) {
        Map<String, WrapLista> mappaOut = new LinkedHashMap<>();

        if (mappaIn == null) {
            return null;
        }

        for (String key : mappaIn.keySet()) {
            if (!key.equals(tag)) {
                mappaOut.put(key, mappaIn.get(key));
            }
        }

        if (mappaIn.keySet().contains(tag)) {
            mappaOut.put(tag, mappaIn.get(tag));
        }

        return mappaOut;
    }


    /**
     * Sposta in coda alla mappa il paragrafo 'Altre...' (eventuale) <br>
     */
    public Map<String, Integer> fixAltreInCodaInt(final Map<String, Integer> mappaIn, String tag) {
        Map<String, Integer> mappaOut = new LinkedHashMap<>();

        if (mappaIn == null) {
            return null;
        }

        for (String key : mappaIn.keySet()) {
            if (!key.equals(tag)) {
                mappaOut.put(key, mappaIn.get(key));
            }
        }

        if (mappaIn.keySet().contains(tag)) {
            mappaOut.put(tag, mappaIn.get(tag));
        }

        return mappaOut;
    }


    /**
     * Testo body della pagina suddiviso (eventualmente) in paragrafi <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public String bodyText() {
        StringBuffer buffer = new StringBuffer();
        boolean usaParagrafi;
        WrapLista wrapLista;

        usaParagrafi = numBio >= sogliaSottoPagina && mappaGenerale.size() >= sogliaParagrafi;

        if (!usaParagrafi && numBio > sogliaDiv) {
            buffer.append(DIV_INI_CAPO);
        }
        for (String keyParagrafo : mappaGenerale.keySet()) {
            wrapLista = mappaGenerale.get(keyParagrafo);
            buffer.append(bodyParagrafo(usaParagrafi, keyParagrafo, wrapLista));
        }
        if (!usaParagrafi && numBio > sogliaDiv) {
            buffer.append(DIV_END_CAPO);
        }

        bodyText = buffer.toString().trim();
        return bodyText;
    }


    private String bodyParagrafo(boolean usaParagrafi, final String keyParagrafo, final WrapLista wrapLista) {
        StringBuffer buffer = new StringBuffer();
        String message;
        List<WrapDidascalia> lista;
        int dimensioneParagrafo;
        boolean usaDiv;

        lista = wrapLista.getLista();
        if (lista == null) {
            message = String.format("Manca la lista nel paragrafo [%s%s%s]", nomeLista, SLASH, keyParagrafo);
            logger.error(new WrapLog().exception(new Exception(message)));
            return VUOTA;
        }

        dimensioneParagrafo = wrapLista.getNumBio();
        usaDiv = dimensioneParagrafo > sogliaDiv;
        if (usaParagrafi) {
            buffer.append(getTitoloParagrafo(keyParagrafo, dimensioneParagrafo));
        }
        if (wrapLista.isUsaRinvio()) {
            buffer.append(wrapLista.getRinvio());
        }
        else {
            if (usaParagrafi && usaDiv) {
                buffer.append(DIV_INI_CAPO);
            }
            for (WrapDidascalia wrap : lista) {
                buffer.append(ASTERISCO);
                buffer.append(wrap.getDidascalia());
                buffer.append(CAPO);
            }
            if (usaParagrafi && usaDiv) {
                buffer.append(DIV_END_CAPO);
            }
        }

        return buffer.toString();
    }


    /**
     * Testo body delle sottoPagine <br>
     */
    private void bodyTextSottoPagine() {
        String bodySingolaSottoPagina;

        if (mappaBodySottoPagine != null && listaSottoPagine != null && listaSottoPagine.size() > 0) {
            for (String keySottoPagina : listaSottoPagine) {
                bodySingolaSottoPagina = bodyTextSottoPagina(keySottoPagina);
                mappaBodySottoPagine.put(keySottoPagina, bodySingolaSottoPagina);
            }
        }
    }


    /**
     * Testo body delle sottoPagine <br>
     */
    private String bodyTextSottoPagina(String keySottoPagina) {
        StringBuffer buffer = new StringBuffer();
        WrapLista wrapLista;
        boolean usaParagrafi = typeLista.getTypeLivello().getLivelloParagrafi() >= 2;
        Map<String, WrapLista> mappa;
        boolean usaDiv;

        wrapLista = mappaGenerale.get(keySottoPagina);
        mappa = wrapLista.getMappa();

        if (!usaParagrafi && numBio > sogliaDiv) {
            buffer.append(DIV_INI_CAPO);
        }

        if (mappa != null && mappa.size() > 0) {
            for (String keyParagrafoSottoPagina : mappa.keySet()) {
                wrapLista = mappa.get(keyParagrafoSottoPagina);
                buffer.append(bodyParagrafo(usaParagrafi, keyParagrafoSottoPagina, wrapLista));
            }
        }

        if (!usaParagrafi && numBio > sogliaDiv) {
            buffer.append(DIV_END_CAPO);
        }

        return buffer.toString();
    }


    public String getTitoloParagrafo(String keyParagrafo, int numVociParagrafo) {
        if (usaIncludeParagrafi && numBio < sogliaIncludeAll) {
            return wikiUtilityService.setParagrafoIncludeOnly(keyParagrafo, numVociParagrafo);
        }
        else {
            return wikiUtilityService.setParagrafo(keyParagrafo, numVociParagrafo);
        }
    }


    public int getNumBio() {
        return numBio;
    }

    public List<BioMongoEntity> getListaBio() {
        return listaBio;
    }

    public List<WrapDidascalia> getListaWrapDidascalie() {
        return listaWrapDidascalie;
    }


    public Map<String, Integer> getMappaParagrafi() {
        return mappaParagrafi;
    }


    public List<String> getKeyParagrafi() {
        return mappaGenerale.keySet().stream().toList();
    }

    public String getBodyText() {
        return bodyText;
    }

    public List<String> getListaSottoPagine() {
        return listaSottoPagine;
    }


    public String getBodySottoPagina2(final String keySottoPagina) {
        StringBuffer buffer = new StringBuffer();
        WrapLista wrapLista;
        boolean usaParagrafi = numBio >= sogliaSottoPagina && mappaGenerale.size() >= sogliaParagrafi;
        //        int livPagina = 2;
        //        int livParagrafi = 2;

        if (!usaSottoPagineLista) {
            return VUOTA;
        }
        if (!listaSottoPagine.contains(keySottoPagina)) {
            return VUOTA;
        }
        if (!mappaGenerale.keySet().contains(keySottoPagina)) {
            return VUOTA;
        }
        if (!usaParagrafi && numBio > sogliaDiv) {
            buffer.append(DIV_INI_CAPO);
        }

        wrapLista = mappaGenerale.get(keySottoPagina);
        //        wrapLista.usaRinvio(true);
        buffer.append(bodyParagrafo(false, keySottoPagina, wrapLista));

        if (!usaParagrafi && numBio > sogliaDiv) {
            buffer.append(DIV_END_CAPO);
        }
        return buffer.toString();
    }

    public String bodyTextSottoPaginaConParagrafi(List<WrapDidascalia> listaSottoPagina) {
        StringBuffer buffer = new StringBuffer();
        int numVociParagrafo = listaSottoPagina.size();
        Map<String, List<WrapDidascalia>> mappaSotto = mappaWrapDidascalieSotto(listaSottoPagina);
        boolean usaDiv;
        int dimensioneParagrafo = 0;

        //        //corpo con/senza sottosottopagine
        //        if (usaSottoSottoPagineLista && numVociParagrafo >  sogliaSottoSottoPagina) {
        //            listaSottoPagine.add(keyParagrafo);
        //        }

        for (String keyParagrafo : mappaSotto.keySet()) {
            //            if (usaSottoSottoPagineLista && numVociParagrafo > sogliaSottoSottoPagina) {
            listaSottoSottoPagine.add(listaSottoPagina + SLASH + keyParagrafo);
            //            }
            dimensioneParagrafo = mappaSotto.get(keyParagrafo).size();
            buffer.append(getTitoloParagrafo(keyParagrafo, dimensioneParagrafo));
            usaDiv = dimensioneParagrafo > sogliaDiv;

            if (usaDiv) {
                buffer.append(DIV_INI_CAPO);
            }

            for (WrapDidascalia wrap : mappaSotto.get(keyParagrafo)) {
                buffer.append(ASTERISCO);
                buffer.append(wrap.getDidascalia());
                buffer.append(CAPO);
            }

            if (usaDiv) {
                buffer.append(DIV_END_CAPO);
            }

        }

        return buffer.toString().trim();
    }


    public String bodyTextSottoPaginaSenzaParagrafi(List<WrapDidascalia> listaSottoPagina) {
        StringBuffer buffer = new StringBuffer();
        boolean usaDiv = listaSottoPagina.size() > sogliaDiv;

        if (usaDiv) {
            buffer.append(DIV_INI_CAPO);
        }

        for (WrapDidascalia wrap : listaSottoPagina) {
            buffer.append(ASTERISCO);
            buffer.append(wrap.getDidascalia());
            buffer.append(CAPO);
        }

        if (usaDiv) {
            buffer.append(DIV_END_CAPO);
        }

        return buffer.toString();
    }

    public TypeLista getTypeLista() {
        return typeLista;
    }

    public int getNumBioSottoPagina(String keySottoPagina) {
        WrapLista listaSottoPagina = mappaGenerale.get(keySottoPagina);
        return listaSottoPagina != null ? listaSottoPagina.getNumBio() : 0;
    }

    public List<String> getListaSottoSottoPagine() {
        return listaSottoSottoPagine;
    }

    public Map<String, WrapLista> getMappaGenerale() {
        return mappaGenerale;
    }

    public String getBodySottoPagina(String keySottoPagina) {
        return mappaBodySottoPagine != null ? mappaBodySottoPagine.get(keySottoPagina) : VUOTA;
    }

    public Map<String, Object> getMappaChiavi() {
        return mappaChiavi;
    }

}
