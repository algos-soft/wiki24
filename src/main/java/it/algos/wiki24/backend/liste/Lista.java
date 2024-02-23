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
import it.algos.wiki24.backend.packages.tabelle.attplurale.*;
import it.algos.wiki24.backend.packages.tabelle.attsingolare.*;
import it.algos.wiki24.backend.packages.tabelle.nazplurale.*;
import it.algos.wiki24.backend.packages.tabelle.nazsingolare.*;
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
    protected GiornoModulo giornoModulo;

    @Inject
    protected AnnoModulo annoModulo;

    @Inject
    protected AttSingolareModulo attSingolareModulo;

    @Inject
    protected AttPluraleModulo attPluraleModulo;

    @Inject
    protected NazSingolareModulo nazSingolareModulo;

    @Inject
    protected NazPluraleModulo nazPluraleModulo;

    @Inject
    protected ArrayService arrayService;

    @Inject
    DidascaliaService didascaliaService;

    protected String titoloPagina;

    protected String nomeLista;

    protected TypeLista typeLista;

    protected List<WrapDidascalia> listaWrapDidascalie = new ArrayList<>();

    protected boolean usaParagrafiLista;

    protected boolean usaDimensioneParagrafi;

    protected boolean usaIncludeParagrafi;

    protected boolean usaParagrafiSottoPagina;

    protected boolean usaSottoPagineLista;

    protected boolean usaSottoSottoPagineLista;

    protected int sogliaDiv;

    protected int sogliaParagrafi;

    protected int sogliaIncludeAll;

    protected int sogliaSottoPagina;

    protected int sogliaVociTotaliPaginaPerSottopagine;

    protected int numBio = INT_ERROR;

    protected String bodyText = VUOTA;


    /**
     * Lista ordinata (per cognome) delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     * La lista è ordinata per cognome <br>
     */
    protected List<BioMongoEntity> listaBio = new ArrayList<>();

    protected Map<String, List<WrapDidascalia>> mappaWrapDidascalie = new LinkedHashMap<>();

    // titolo del paragrafo e numero di voci del paragrafo stesso
    protected Map<String, Integer> mappaParagrafi = new LinkedHashMap<>();

    // titolo della sottoPagina
    protected List<String> listaSottoPagine = new ArrayList<>();


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
        this.mappaWrapDidascalie();
        this.bodyText();
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
            case giornoNascita, giornoMorte -> WPref.sogliaPaginaGiorniAnni.getInt();
            case annoNascita, annoMorte -> WPref.sogliaPaginaGiorniAnni.getInt();
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
     * Mappa ordinata di WrapDidascalie per tutti i paragrafi <br>
     *
     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
     */
    protected Map<String, List<WrapDidascalia>> mappaWrapDidascalie() {
        String key;
        List<WrapDidascalia> lista;

        if (listaWrapDidascalie == null || listaWrapDidascalie.size() == 0) {
            listaWrapDidascalie = listaWrapDidascalie();
        }

        for (WrapDidascalia wrap : listaWrapDidascalie) {

            //--primo livello - paragrafi
            key = wrap.getPrimoLivello();
            if (!mappaWrapDidascalie.containsKey(key)) {
                lista = new ArrayList<>();
                mappaWrapDidascalie.put(key, lista);
            }

            //--didascalia
            lista = mappaWrapDidascalie.get(key);
            lista.add(wrap);
        }

        this.fixMappaWrapDidascalie();
        return mappaWrapDidascalie;
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

    public void fixMappaWrapDidascalie() {
        int size;
        String tagAltre;
        List<WrapDidascalia> lista;

        for (String keyParagrafo : mappaWrapDidascalie.keySet()) {
            size = mappaWrapDidascalie.get(keyParagrafo).size();
            mappaParagrafi.put(keyParagrafo, size);
        }

        mappaWrapDidascalie = switch (typeLista) {
            case attivitaSingolare, attivitaPlurale, nazionalitaSingolare, nazionalitaPlurale -> arrayService.sort(mappaWrapDidascalie);
            default -> mappaWrapDidascalie;
        };

        mappaParagrafi = switch (typeLista) {
            case attivitaSingolare, attivitaPlurale, nazionalitaSingolare, nazionalitaPlurale -> arrayService.sort(mappaParagrafi);
            default -> mappaParagrafi;
        };

        for (String key : mappaWrapDidascalie.keySet()) {
            lista = mappaWrapDidascalie.get(key);
            lista = switch (typeLista) {
                case giornoNascita, giornoMorte, annoNascita, annoMorte -> lista;
                case attivitaSingolare, attivitaPlurale, nazionalitaSingolare, nazionalitaPlurale -> didascaliaService.ordinamentoAlfabetico(lista);
                default -> lista;
            };
            mappaWrapDidascalie.put(key, lista);
        }

        usaParagrafiLista = numBio >= sogliaSottoPagina && mappaWrapDidascalie.size() >= sogliaParagrafi;

        tagAltre = switch (typeLista) {
            case giornoNascita, giornoMorte -> TypeInesistente.giorno.getTag();
            case annoNascita, annoMorte -> TypeInesistente.anno.getTag();
            case attivitaSingolare, attivitaPlurale -> TypeInesistente.nazionalita.getTag();
            case nazionalitaSingolare, nazionalitaPlurale -> TypeInesistente.attivita.getTag();
            default -> VUOTA;
        };
        mappaWrapDidascalie = fixAltreInCodaWrap(mappaWrapDidascalie, tagAltre);
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
    protected String bodyText() {
        StringBuffer buffer = new StringBuffer();

        if (usaParagrafiLista) {
            buffer.append(bodyTextConParagrafi());
        }
        else {
            buffer.append(bodyTextSenzaParagrafi());
        }

        bodyText = buffer.toString().trim();
        return bodyText;
    }

    public String bodyTextConParagrafi() {
        StringBuffer buffer = new StringBuffer();

        for (String key : mappaWrapDidascalie.keySet()) {
            buffer.append(singoloParagrafo(key));
        }

        return buffer.toString().trim();
    }


    public String singoloParagrafo(String keyParagrafo) {
        StringBuffer buffer = new StringBuffer();
        int numVociParagrafo = mappaParagrafi.get(keyParagrafo);
        boolean usaDiv = numVociParagrafo > sogliaDiv;
        String sottoPagina;
        String vedi;

        buffer.append(getTitoloParagrafo(keyParagrafo, numVociParagrafo));

        //corpo con/senza sottopagine
        if (usaSottoPagineLista && numBio > sogliaVociTotaliPaginaPerSottopagine && numVociParagrafo > sogliaSottoPagina) {
            sottoPagina = String.format("%s%s%s", textService.primaMaiuscola(titoloPagina), SLASH, keyParagrafo);

            vedi = String.format("{{Vedi anche|%s}}", sottoPagina);
            buffer.append(vedi);
            buffer.append(CAPO);

            listaSottoPagine.add(keyParagrafo);
        }
        else {
            if (usaDiv) {
                buffer.append(DIV_INI_CAPO);
            }

            for (WrapDidascalia wrap : mappaWrapDidascalie.get(keyParagrafo)) {
                buffer.append(ASTERISCO);
                buffer.append(wrap.getDidascalia());
                buffer.append(CAPO);
            }

            if (usaDiv) {
                buffer.append(DIV_END_CAPO);
            }
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

    /**
     * Testo della pagina <br>
     * Corpo unico senza paragrafi e senza sottopagine <br>
     */
    public String bodyTextSenzaParagrafi() {
        StringBuffer buffer = new StringBuffer();
        boolean usaDiv = numBio > sogliaDiv;

        if (usaDiv) {
            buffer.append(DIV_INI_CAPO);
        }

        for (String keyParagrafo : mappaWrapDidascalie.keySet()) {
            for (WrapDidascalia wrap : mappaWrapDidascalie.get(keyParagrafo)) {
                buffer.append(ASTERISCO);
                buffer.append(wrap.getDidascalia());
                buffer.append(CAPO);
            }
        }

        if (usaDiv) {
            buffer.append(DIV_END_CAPO);
        }

        return buffer.toString();
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

    public Map<String, List<WrapDidascalia>> getMappaWrapDidascalie() {
        return mappaWrapDidascalie;
    }

    public Map<String, Integer> getMappaParagrafi() {
        return mappaParagrafi;
    }


    public List<String> getKeyParagrafi() {
        return mappaWrapDidascalie.keySet().stream().toList();
    }

    public String getBodyText() {
        return bodyText;
    }

    public List<String> getListaSottoPagine() {
        return listaSottoPagine;
    }

    public String getBodySottoPagina(String keySottoPagina) {
        List<WrapDidascalia> listaSottoPagina;

        if (!usaSottoPagineLista) {
            return VUOTA;
        }
        if (!listaSottoPagine.contains(keySottoPagina)) {
            return VUOTA;
        }
        if (!mappaWrapDidascalie.keySet().contains(keySottoPagina)) {
            return VUOTA;
        }
        listaSottoPagina = mappaWrapDidascalie.get(keySottoPagina);

        if (usaParagrafiSottoPagina) {
            return bodyTextSottoPaginaConParagrafi(listaSottoPagina);
        }
        else {
            return bodyTextSottoPaginaSenzaParagrafi(listaSottoPagina);
        }
    }

    public String bodyTextSottoPaginaConParagrafi(List<WrapDidascalia> listaSottoPagina) {
        StringBuffer buffer = new StringBuffer();
        Map<String, List<WrapDidascalia>> mappaSotto = mappaWrapDidascalieSotto(listaSottoPagina);
        boolean usaDiv;
        int dimensioneParagrafo = 0;

        if (usaSottoSottoPagineLista) {
        }

        for (String keyParagrafo : mappaSotto.keySet()) {
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
        List<WrapDidascalia> listaSottoPagina = mappaWrapDidascalie.get(keySottoPagina);
        return listaSottoPagina != null ? listaSottoPagina.size() : 0;
    }

}
