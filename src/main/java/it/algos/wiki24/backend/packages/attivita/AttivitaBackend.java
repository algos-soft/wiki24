package it.algos.wiki24.backend.packages.attivita;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.genere.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: lun, 18-apr-2022
 * Time: 21:25
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class AttivitaBackend extends WikiBackend {


    public AttivitaRepository repository;


    /**
     * Costruttore @Autowired (facoltativo) @Qualifier (obbligatorio) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per specificare la classe che incrementa l'interfaccia repository <br>
     * Si usa una costante statica, per essere sicuri di scriverla uguale a quella di xxxRepository <br>
     * Regola la classe di persistenza dei dati specifica e la passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questo service <br>
     *
     * @param crudRepository per la persistenza dei dati
     */
    public AttivitaBackend(@Autowired @Qualifier(TAG_ATTIVITA) final MongoRepository crudRepository) {
        super(crudRepository, Attivita.class);
        this.repository = (AttivitaRepository) crudRepository;
    }


    public Attivita creaIfNotExist(String singolare, String pluraleParagrafo, String pluraleLista, String linkPaginaAttivita, AETypeGenere typeGenere, boolean aggiunta) {
        return checkAndSave(newEntity(singolare, pluraleParagrafo, pluraleLista, linkPaginaAttivita, typeGenere, aggiunta));
    }

    public Attivita checkAndSave(final Attivita attivita) {
        return isExist(attivita.singolare) ? null : repository.insert(attivita);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Attivita newEntity() {
        return newEntity(VUOTA, VUOTA, VUOTA, VUOTA, null, false);
    }


    /**
     * Creazione in memoria di una nuova entityBean che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param singolare          di riferimento (obbligatorio, unico)
     * @param pluraleParagrafo   di riferimento (obbligatorio, non unico)
     * @param pluraleLista       di riferimento (obbligatorio, non unico)
     * @param linkPaginaAttivita di riferimento (obbligatorio, non unico)
     * @param typeGenere         (obbligatorio, non unico)
     * @param aggiunta           flag (facoltativo, di default false)
     *
     * @return la nuova entityBean appena creata (non salvata)
     */
    public Attivita newEntity(
            final String singolare,
            final String pluraleParagrafo,
            final String pluraleLista,
            final String linkPaginaAttivita,
            final AETypeGenere typeGenere,
            final boolean aggiunta) {
        return Attivita.builder()
                .singolare(textService.isValid(singolare) ? singolare : null)
                .pluraleParagrafo(textService.isValid(pluraleParagrafo) ? pluraleParagrafo : null)
                .pluraleLista(textService.isValid(pluraleLista) ? pluraleLista : null)
                .linkPaginaAttivita(textService.isValid(linkPaginaAttivita) ? linkPaginaAttivita : null)
                .type(typeGenere != null ? typeGenere : AETypeGenere.maschile)
                .aggiunta(aggiunta)
                .numBio(0)
                .superaSoglia(false)
                .esistePaginaLista(false)
                .build();
    }


    public List<Attivita> findAll() {
        return repository.findAll();
    }

    public List<Attivita> findAttivitaAllSenzaEx() {
        List<Attivita> lista = new ArrayList<>();
        Sort sortOrder = Sort.by(Sort.Direction.ASC, "pagina");
        List<Attivita> listaAll = repository.findAll(sortOrder);

        for (Attivita attivita : listaAll) {
            if (!attivita.aggiunta) {
                lista.add(attivita);
            }
        }

        return lista;
    }


    private List<Attivita> findAttivitaDistinctByPlurali(String property) {
        List<Attivita> lista = new ArrayList<>();
        Set<String> set = new HashSet();
        Sort sortOrder = Sort.by(Sort.Direction.ASC, property);
        List<Attivita> listaAll = repository.findAll(sortOrder);

        for (Attivita attivita : listaAll) {
            if (set.add(attivita.pluraleLista)) {
                lista.add(attivita);
            }
        }

        return lista;
    }

    public List<Attivita> findAttivitaDistinctByPluraliSortPagina() {
        return findAttivitaDistinctByPlurali("pagina");
    }

    public List<Attivita> findAttivitaDistinctByPluraliSortPlurali() {
        return findAttivitaDistinctByPlurali("pluraleLista");
    }

    /**
     * Pagine che esistono sul server wikipedia e che non superano la soglia prevista per le liste <br>
     * flag esistePagina=true <br>
     * flag superaSoglia=false <br>
     *
     * @return attività con liste da cancellare
     */
    public List<Attivita> findPagineDaCancellare() {
        List<Attivita> listaDaCancellare = new ArrayList<>();
        List<Attivita> listaPlurali = findAttivitaDistinctByPluraliSortPagina();

        for (Attivita attivita : listaPlurali) {
            if (attivita.esistePaginaLista && !attivita.superaSoglia) {
                listaDaCancellare.add(attivita);
            }
        }

        return listaDaCancellare;
    }


    public List<String> findAllPlurali() {
        List<String> lista = new ArrayList<>();
        List<Attivita> listaAll = findAttivitaDistinctByPluraliSortPlurali();

        for (Attivita attivita : listaAll) {
            lista.add(attivita.pluraleLista);
        }

        return lista;
    }

    public List<String> findAllPagine() {
        List<String> lista = new ArrayList<>();

        for (String wikiTitle : findAllPlurali()) {
            lista.add(PATH_ATTIVITA + SLASH + textService.primaMaiuscola(wikiTitle));
        }

        return lista;
    }

    public boolean isExist(final String attivitaSingolarePlurale) {
        return findFirstBySingolare(attivitaSingolarePlurale) != null || findFirstByPluraleLista(attivitaSingolarePlurale) != null;
    }

    public boolean isExistSingolare(final String attivitaSingolare) {
        return findFirstBySingolare(attivitaSingolare) != null;
    }

    public boolean isExistPlurale(final String attivitaPlurale) {
        return findFirstByPluraleLista(attivitaPlurale) != null;
    }

    /**
     * Retrieves the first entity by a 'singular' property.
     * Cerca una singola entity con una query. <br>
     * Restituisce un valore valido ANCHE se esistono diverse entities <br>
     *
     * @param attivitaSingolare per costruire la query
     *
     * @return the FIRST founded entity
     */
    public Attivita findFirstBySingolare(final String attivitaSingolare) {
        return repository.findFirstBySingolare(attivitaSingolare);
    }

    /**
     * Retrieves the first entity by a 'pluraleLista' property.
     * Cerca una singola entity con una query. <br>
     * Restituisce un valore valido ANCHE se esistono diverse entities <br>
     *
     * @param attivitaPlurale per costruire la query
     *
     * @return the FIRST founded entity
     */
    public Attivita findFirstByPluraleLista(final String attivitaPlurale) {
        return repository.findFirstByPluraleLista(attivitaPlurale);
    }

    /**
     * Retrieves the first entity by a 'singular' or 'pluraleLista' property.
     * Cerca una singola entity con una query. <br>
     * Restituisce un valore valido ANCHE se esistono diverse entities <br>
     *
     * @param attivitaSingolarePlurale per costruire la query
     *
     * @return the FIRST founded entity
     */
    public Attivita findFirst(final String attivitaSingolarePlurale) {
        Attivita attivita;

        attivita = repository.findFirstBySingolare(attivitaSingolarePlurale);
        if (attivita == null) {
            attivita = repository.findFirstByPluraleLista(attivitaSingolarePlurale);
        }

        return attivita;
    }

    public List<Attivita> findAllBySingolarePlurale(final String singolarePlurale) {
        Attivita attivita = findFirst(singolarePlurale);
        return attivita != null ? repository.findAllByPluraleListaOrderBySingolareAsc(attivita.pluraleLista) : null;
    }

    public List<Attivita> findAllBySingolare(final String singolare) {
        Attivita attivita = findFirstBySingolare(singolare);
        if (attivita != null) {
            return repository.findAllByPluraleListaOrderBySingolareAsc(attivita.pluraleLista);
        }
        else {
            return null;
        }
    }

    public List<Attivita> findAllByPlurale(final String plurale) {
        return repository.findAllByPluraleListaOrderBySingolareAsc(plurale);
    }

    public List<Attivita> findAllByParagrafo(final String paragrafo) {
        return repository.findAllByPluraleParagrafoOrderBySingolareAsc(paragrafo);
    }

    public String pluraleBySingolarePlurale(final String attivitaSingolarePlurale) {
        Attivita attivita;
        attivita = findFirstBySingolare(attivitaSingolarePlurale);

        if (attivita != null) {
            return attivita.pluraleLista;
        }
        else {
            attivita = findFirstByPluraleLista(attivitaSingolarePlurale);
            if (attivita != null) {
                return attivita.pluraleLista;
            }
            else {
                return VUOTA;
            }
        }
    }

    public List<String> findAllSingolari(final String singolarePlurale) {
        Attivita attivita = findFirst(singolarePlurale);
        return attivita != null ? findAllSingolariByPlurale(attivita.pluraleLista) : null;
    }

    public List<String> findAllSingolariBySingolare(final String attivitaSingolare) {
        String attivitaPlurale = pluraleBySingolarePlurale(attivitaSingolare);
        return findAllSingolariByPlurale(attivitaPlurale);
    }

    /**
     * Crea una lista di singolari che hanno lo stesso plurale. <br>
     *
     * @param attivitaPlurale da selezionare
     *
     * @return lista di singolari filtrati
     */
    public List<String> findAllSingolariByPlurale(final String attivitaPlurale) {
        List<String> listaNomi = new ArrayList<>();
        List<Attivita> listaAttivita = findAllByPlurale(attivitaPlurale);
        Attivita attivitaSingola;

        if (listaAttivita.size() == 0) {
            attivitaSingola = findFirstBySingolare(attivitaPlurale);
            if (attivitaSingola != null) {
                listaAttivita.add(attivitaSingola);
            }
        }

        for (Attivita attivita : listaAttivita) {
            listaNomi.add(attivita.singolare);
        }

        return listaNomi;
    }


    public LinkedHashMap<String, List<String>> findMappaSingolariByPluraleLista() {
        LinkedHashMap<String, List<String>> mappa = new LinkedHashMap<>();
        List<String> lista;
        List<Attivita> listaAll = repository.findAll();
        String plurale;
        String singolare;

        for (Attivita attivita : listaAll) {
            plurale = attivita.pluraleLista;
            singolare = attivita.singolare;

            if (mappa.get(plurale) == null) {
                lista = new ArrayList<>();
                lista.add(singolare);
                mappa.put(plurale, lista);
            }
            else {
                lista = mappa.get(plurale);
                lista.add(singolare);
                mappa.put(plurale, lista);
            }
        }

        return mappa;
    }


    public LinkedHashMap<String, List<String>> findMappaSingolariByPluraleParagrafo() {
        LinkedHashMap<String, List<String>> mappa = new LinkedHashMap<>();
        List<String> lista;
        List<Attivita> listaAll = repository.findAll();
        String plurale;
        String singolare;

        for (Attivita attivita : listaAll) {
            plurale = attivita.pluraleParagrafo;
            singolare = attivita.singolare;

            if (mappa.get(plurale) == null) {
                lista = new ArrayList<>();
                lista.add(singolare);
                mappa.put(plurale, lista);
            }
            else {
                lista = mappa.get(plurale);
                lista.add(singolare);
                mappa.put(plurale, lista);
            }
        }

        return mappa;
    }


    public LinkedHashMap<String, List<String>> findMappaSingolariByLinkPagina() {
        LinkedHashMap<String, List<String>> mappa = new LinkedHashMap<>();
        List<String> lista;
        List<Attivita> listaAll = repository.findAll();
        String plurale;
        String singolare;

        for (Attivita attivita : listaAll) {
            plurale = attivita.linkPaginaAttivita;
            singolare = attivita.singolare;

            if (mappa.get(plurale) == null) {
                lista = new ArrayList<>();
                lista.add(singolare);
                mappa.put(plurale, lista);
            }
            else {
                lista = mappa.get(plurale);
                lista.add(singolare);
                mappa.put(plurale, lista);
            }
        }

        return mappa;
    }

    //    /**
    //     * Conta il totale delle voci bio per tutte le attività associate a quella indicata. <br>
    //     * Recupera l'attività plurale e quindi tutte le attività singole associate <br>
    //     *
    //     * @param attivitaSingolare selezionata
    //     *
    //     * @return totale di voci biografiche interessate
    //     */
    //    public int contBio(final Attivita attivitaSingolare) {
    //        int numBio = 0;
    //        List<Attivita> lista = this.findAllByPagina(attivitaSingolare.pluraleParagrafo);
    //
    //        for (Attivita attivita : lista) {
    //            numBio += attivita.numBio;
    //        }
    //
    //        return numBio;
    //    }

    /**
     * Legge le mappa di valori dai moduli di wiki: <br>
     * Modulo:Bio/Plurale attività
     * Modulo:Bio/Ex attività
     * Modulo:Bio/Link attività
     * <p>
     * Cancella la (eventuale) precedente lista di attività <br>
     */
    public void download() {
        long inizio = System.currentTimeMillis();
        String moduloPlurale = PATH_MODULO + PATH_PLURALE + ATT_LOWER;
        String moduloEx = PATH_MODULO + PATH_EX + ATT_LOWER;
        String moduloLink = PATH_MODULO + PATH_LINK + ATT_LOWER;

        // genere?????????

        downloadAttivitaPlurali(moduloPlurale);
        downloadAttivitaExtra(moduloEx);
        downloadAttivitaLink(moduloLink);

        super.fixDownload(inizio,"attività");
    }


    /**
     * Legge le mappa dal Modulo:Bio/Plurale attività <br>
     * Crea le attività <br>
     *
     * @param moduloPlurale della pagina su wikipedia
     *
     * @return entities create
     */
    public int downloadAttivitaPlurali(String moduloPlurale) {
        int size = 0;
        String singolare;
        String pluraleParagrafo;
        String pluraleLista = VUOTA;
        AETypeGenere typeGenere = null;
        Genere genere;
        Map<String, String> mappaPlurale = wikiApiService.leggeMappaModulo(moduloPlurale);

        if (mappaPlurale != null && mappaPlurale.size() > 0) {
            deleteAll();
            for (Map.Entry<String, String> entry : mappaPlurale.entrySet()) {
                singolare = entry.getKey();
                pluraleLista = entry.getValue();
                genere = genereBackend.findFirstBySingolare(singolare);
                typeGenere = genere != null ? genere.getType() : AETypeGenere.nessuno;
                pluraleParagrafo = getParagrafo(genere, pluraleLista);

                if (creaIfNotExist(singolare, pluraleParagrafo, pluraleLista, VUOTA, typeGenere, false) != null) {
                    size++;
                }
            }
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il %s", moduloPlurale);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return 0;
        }

        return size;
    }

    /**
     * Legge le mappa dal Modulo:Bio/Ex attività <br>
     * Crea le attività aggiuntive <br>
     *
     * @param moduloEx della pagina su wikipedia
     *
     * @return entities create
     */
    public int downloadAttivitaExtra(String moduloEx) {
        int size = 0;
        String singolareEx;
        String singolareNew;
        Map<String, String> mappaEx = wikiApiService.leggeMappaModulo(moduloEx);
        Attivita attivita;

        if (mappaEx != null && mappaEx.size() > 0) {
            for (Map.Entry<String, String> entry : mappaEx.entrySet()) {
                singolareEx = entry.getKey();
                singolareNew = TAG_EX_SPAZIO + singolareEx;

                attivita = findFirstBySingolare(singolareEx);
                if (attivita != null) {
                    if (creaIfNotExist(singolareNew, attivita.pluraleParagrafo, attivita.pluraleLista, VUOTA, attivita.type, true) != null) {
                        size++;
                    }
                }
                else {
                    logger.info(new WrapLog().message(String.format("Manca negli extra %s", singolareEx)));
                }
            }
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il %s", mappaEx);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return 0;
        }

        return size;
    }


    /**
     * Legge le mappa dal Modulo:Bio/Link attività <br>
     * Aggiunge il link alla pagina wiki dell'attività <br>
     *
     * @param moduloLink della pagina su wikipedia
     *
     * @return entities create
     */
    public int downloadAttivitaLink(String moduloLink) {
        int cont = 0;
        String singolareConOSenzaEx;
        String singolareSenzaEx;
        String linkPagina;
        Map<String, String> mappaLink = wikiApiService.leggeMappaModulo(moduloLink);
        List<Attivita> listaAllAttivita = findAll();

        if (mappaLink != null && mappaLink.size() > 0) {
            for (Attivita attivita : listaAllAttivita) {
                singolareConOSenzaEx = attivita.singolare;
                singolareSenzaEx = attivita.aggiunta ? textService.levaTesta(singolareConOSenzaEx, TAG_EX_SPAZIO) : singolareConOSenzaEx;

                if (mappaLink.containsKey(singolareSenzaEx)) {
                    linkPagina = mappaLink.get(singolareSenzaEx);
                    attivita.linkPaginaAttivita = linkPagina;
                    save(attivita);
                }
                else {
                    if (queryService.isEsiste(singolareSenzaEx)) {
                        attivita.linkPaginaAttivita = singolareSenzaEx;
                        save(attivita);
                    }
                    else {
                        cont++;
                        logger.info(new WrapLog().message(String.format("Manca %s", singolareConOSenzaEx)));
                    }
                }
            }
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il %s", mappaLink);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return 0;
        }

        logger.info(new WrapLog().message(String.format("Mancano %d linkAttivita", cont)));
        return cont;
    }


    private String getParagrafo(final Genere genere, final String categoria) {
        String paragrafo = VUOTA;
        AETypeGenere type;

        if (genere == null) {
            return categoria;
        }

        type = genere.getType();
        paragrafo = switch (type) {
            case maschile -> genere.pluraleMaschile;
            case femminile -> genere.pluraleFemminile;
            case entrambi -> {
                if (genere.pluraleMaschile.equals(genere.pluraleFemminile)) {
                    yield genere.pluraleMaschile;
                }
                else {
                    yield genere.pluraleMaschile;
                }
            }
            case nessuno -> categoria;
        };

        return paragrafo;
    }

    public int countAttivitaDaCancellare() {
        int daCancellare = 0;
        List<Attivita> listaPlurali = findAttivitaDistinctByPluraliSortPagina();

        for (Attivita attivita : listaPlurali) {
            if (attivita.esistePaginaLista && !attivita.superaSoglia) {
                daCancellare++;
            }
        }

        return daCancellare;
    }


    /**
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void elabora() {
        long inizio = System.currentTimeMillis();
        List<String> listaPlurali;
        List<String> listaSingolari;
        int numBio;
        int numSingolari;
        int soglia = WPref.sogliaAttNazWiki.getInt();
        int cont = 0;
        String size;
        String time;
        int tot = count();

        for (Attivita attivita : findAll()) {
            attivita.numBio = 0;
            attivita.numSingolari = 0;
            attivita.superaSoglia = false;
            attivita.esistePaginaLista = false;
            update(attivita);
        }

        //--Spazzola tutte le attività distinte plurali (circa 662)
        //--Per ognuna recupera le attività singolari
        //--Per ognuna attività singolare calcola quante biografie la usano (in 1 o 3 parametri)
        //--Memorizza e registra il dato nella entityBean
        listaPlurali = findAllPlurali();
        for (String plurale : listaPlurali) {
            numBio = 0;
            numSingolari = 0;

            listaSingolari = findAllSingolariByPlurale(plurale);
            for (String singolare : listaSingolari) {
                numBio += bioBackend.countAttivitaAll(singolare);
                numSingolari++;
            }

            for (Attivita attivitaOK : findAllByPlurale(plurale)) {
                attivitaOK.numBio = numBio;
                attivitaOK.superaSoglia = numBio >= soglia ? true : false;
                attivitaOK.esistePaginaLista = esistePagina(attivitaOK.pluraleLista);
                attivitaOK.numSingolari = numSingolari;
                update(attivitaOK);

                if (Pref.debug.is()) {
                    cont++;
                    if (mathService.multiploEsatto(100, cont)) {
                        size = textService.format(cont);
                        time = dateService.deltaText(inizio);
                        message = String.format("Finora controllata l'esistenza di %s/%s liste di attività, in %s", size, tot, time);
                        logger.info(new WrapLog().message(message).type(AETypeLog.elabora));
                    }
                }
            }
        }

        super.fixElaboraMinuti(inizio, "attività");
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    @Deprecated
    public void uploadAll() {
        WResult result;
        long inizio = System.currentTimeMillis();
        int sottoSoglia = 0;
        int daCancellare = 0;
        int modificate = 0;
        int nonModificate = 0;
        List<String> listaPluraliUnici = findAllPlurali();
        this.fixNext();

        for (String pluraleAttivita : listaPluraliUnici) {
            result = uploadPagina(pluraleAttivita);
            if (result.isValido()) {
                if (result.isModificata()) {
                    modificate++;
                }
                else {
                    nonModificate++;
                }
            }
            else {
                sottoSoglia++;
                if (result.getErrorCode().equals(KEY_ERROR_CANCELLANDA)) {
                    daCancellare++;
                }
            }
        }
        super.fixUploadMinuti(inizio, sottoSoglia, daCancellare, nonModificate, modificate, "attività");
    }

    /**
     * Controlla l'esistenza della pagina wiki relativa a questa attività (lista) <br>
     */
    public boolean esistePagina(String pluraleAttivita) {
        String wikiTitle = "Progetto:Biografie/Attività/" + textService.primaMaiuscola(pluraleAttivita);
        return appContext.getBean(QueryExist.class).isEsiste(wikiTitle);
    }

    /**
     * Scrive una pagina definitiva sul server wiki <br>
     */
    public WResult uploadPagina(String pluraleAttivitaMinuscola) {
        WResult result = WResult.errato();
        String message;
        int numVoci = bioBackend.countAttivitaPlurale(pluraleAttivitaMinuscola);
        String voci = textService.format(numVoci);
        String pluraleAttivitaMaiuscola = textService.primaMaiuscola(pluraleAttivitaMinuscola);
        int soglia = WPref.sogliaAttNazWiki.getInt();
        String wikiTitle = "Progetto:Biografie/Attività/" + pluraleAttivitaMaiuscola;

        if (numVoci > soglia) {
            result = appContext.getBean(UploadAttivita.class).upload(pluraleAttivitaMinuscola);
            if (result.isValido()) {
                if (result.isModificata()) {
                    message = String.format("Lista %s utilizzati in %s voci biografiche", pluraleAttivitaMinuscola, voci);
                }
                else {
                    message = String.format("Attività %s utilizzata in %s voci biografiche. %s", pluraleAttivitaMinuscola, voci, result.getValidMessage());
                }
                if (Pref.debug.is()) {
                    logger.info(new WrapLog().message(message).type(AETypeLog.upload));
                }
            }
            else {
                logger.warn(new WrapLog().message(result.getErrorMessage()).type(AETypeLog.upload));
            }
        }
        else {
            message = String.format("L'attività %s ha solo %s voci biografiche e non raggiunge il numero necessario per avere una pagina dedicata", pluraleAttivitaMinuscola, voci);
            if (Pref.debug.is()) {
                result.setErrorMessage(message).setValido(false);
                logger.info(new WrapLog().message(message).type(AETypeLog.upload));
            }
            if (esistePagina(pluraleAttivitaMinuscola)) {
                result.setErrorCode(KEY_ERROR_CANCELLANDA);
                message = String.format("Esiste la pagina %s che andrebbe cancellata", wikiTitle);
                logger.warn(new WrapLog().message(message).type(AETypeLog.upload).usaDb());
            }
        }

        return result;
    }

    public void fixNext() {
        LocalDateTime adesso = LocalDateTime.now();
        LocalDateTime prossimo = adesso.plusDays(7);
        WPref.uploadAttivitaPrevisto.setValue(prossimo);
    }

    /**
     * Creazione di alcuni dati <br>
     * Esegue SOLO se la collection NON esiste oppure esiste ma è VUOTA <br>
     * Viene invocato alla creazione del programma <br>
     * I dati possono essere presi da una Enumeration, da un file CSV locale, da un file CSV remoto o creati hardcoded <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public AResult resetOnlyEmpty() {
        AResult result = super.resetOnlyEmpty();

        if (result.isValido()) {
            this.download();
            return fixResult(result);
        }
        else {
            return result;
        }
    }

}// end of crud backend class
