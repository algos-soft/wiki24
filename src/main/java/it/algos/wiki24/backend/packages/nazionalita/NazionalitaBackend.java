package it.algos.wiki24.backend.packages.nazionalita;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
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
import java.util.stream.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: lun, 25-apr-2022
 * Time: 18:21
 */
@Service
public class NazionalitaBackend extends WikiBackend {

    //    public NazionalitaRepository repository;


    public NazionalitaBackend() {
        super(Nazionalita.class);
    }

    //    /**
    //     * Costruttore @Autowired (facoltativo) @Qualifier (obbligatorio) <br>
    //     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
    //     * Si usa un @Qualifier(), per specificare la classe che incrementa l'interfaccia repository <br>
    //     * Si usa una costante statica, per essere sicuri di scriverla uguale a quella di xxxRepository <br>
    //     * Regola la classe di persistenza dei dati specifica e la passa al costruttore della superclasse <br>
    //     * Regola la entityClazz (final nella superclasse) associata a questo service <br>
    //     *
    //     * @param crudRepository per la persistenza dei dati
    //     */
    //    public NazionalitaBackend(@Autowired @Qualifier(TAG_NAZIONALITA) final MongoRepository crudRepository) {
    //        super(crudRepository, Nazionalita.class);
    //        this.repository = (NazionalitaRepository) crudRepository;
    //        //        super.lastDownload = WPref.downloadNazionalita;
    //    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();


        this.unitaMisuraDownload = AETypeTime.secondi;
        this.unitaMisuraElaborazione = AETypeTime.minuti;
    }

    //    public Nazionalita creaIfNotExist(final String singolare, String pluraleParagrafo, String pluraleLista, String linkPaginaNazione) {
    //        return checkAndSave(newEntity(singolare, pluraleParagrafo, pluraleLista, linkPaginaNazione));
    //    }
    //
    //    public Nazionalita checkAndSave(final Nazionalita nazionalita) {
    //        return null;
    //        //        return isExist(nazionalita.singolare) ? null : repository.insert(nazionalita);
    //    }
    //
    //    public boolean isExist(final String singolare) {
    //        //        return repository.findFirstBySingolare(singolare) != null;
    //        return false;
    //    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Nazionalita newEntity() {
        return newEntity(VUOTA, VUOTA, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param keyPropertyValue proveniente da vaad24
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Nazionalita newEntity(final String keyPropertyValue) {
        return newEntity(keyPropertyValue, VUOTA, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param singolare         di riferimento (obbligatorio, unico)
     * @param pluraleParagrafo  di riferimento (obbligatorio, non unico)
     * @param pluraleLista      di riferimento (obbligatorio, non unico)
     * @param linkPaginaNazione di riferimento (obbligatorio, non unico)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Nazionalita newEntity(
            final String singolare,
            final String pluraleParagrafo,
            final String pluraleLista,
            final String linkPaginaNazione) {
        Nazionalita newEntityBean = Nazionalita.builder()
                .singolare(textService.isValid(singolare) ? singolare : null)
                .pluraleParagrafo(textService.isValid(pluraleParagrafo) ? pluraleParagrafo : null)
                .pluraleLista(textService.isValid(pluraleLista) ? pluraleLista : null)
                .linkPaginaNazione(textService.isValid(linkPaginaNazione) ? linkPaginaNazione : null)
                .numBio(0)
                .numSingolari(0)
                .superaSoglia(false)
                .esistePaginaLista(false)
                .build();

        return (Nazionalita) super.fixKey(newEntityBean);
    }

    @Override
    public Nazionalita findById(final String keyID) {
        return (Nazionalita) super.findById(keyID);
    }

    @Override
    public Nazionalita findByKey(final String keyValue) {
        return (Nazionalita) super.findByKey(keyValue);
    }

    @Override
    public Nazionalita findByOrder(final int ordine) {
        return this.findByProperty(FIELD_NAME_ORDINE, ordine);
    }

    @Override
    public Nazionalita findByProperty(final String propertyName, final Object propertyValue) {
        return (Nazionalita) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public List<Nazionalita> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<Nazionalita> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<Nazionalita> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<Nazionalita> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    public List<Nazionalita> findAll() {
        return this.findAllNoSort();
    }

    public List<String> findAllPluraliDistinti() {
        // Lista completa di tutte le entities
        List<Nazionalita> listaAll = this.findAll();

        // Lista completa della singola property
        List<String> pluraleLista = listaAll.stream()
                .map(naz -> naz.pluraleLista)
                .collect(Collectors.toList());

        // Lista di tutte le distinct di una property
        List<String> lista = pluraleLista.stream()
                .distinct()
                .collect(Collectors.toList());

        return lista;
    }

    /**
     * Retrieves the first entity by a 'singular' property.
     * Cerca una singola entity con una query. <br>
     * Restituisce un valore valido ANCHE se esistono diverse entities <br>
     *
     * @param nazionalitaSingolare per costruire la query
     *
     * @return the FIRST founded entity
     */
    public Nazionalita findFirstBySingolare(final String nazionalitaSingolare) {
        return findByKey(nazionalitaSingolare);
        //        return repository.findFirstBySingolare(nazionalitaSingolare);
    }

    /**
     * Retrieves the first entity by a 'plural' property.
     * Cerca una singola entity con una query. <br>
     * Restituisce un valore valido ANCHE se esistono diverse entities <br>
     *
     * @param nazionalitaPlurale per costruire la query
     *
     * @return the FIRST founded entity
     */
    public Nazionalita findFirstByPlurale(final String nazionalitaPlurale) {
        return findByProperty("pluraleLista", nazionalitaPlurale);
        //        return repository.findFirstByPluraleLista(nazionalitaPlurale);
    }


    /**
     * Retrieves the first entity by a 'plural' property.
     * Cerca una singola entity con una query. <br>
     * Restituisce un valore valido ANCHE se esistono diverse entities <br>
     *
     * @param nazionalitaPlurale per costruire la query
     *
     * @return the FIRST founded entity
     */
    public Nazionalita findFirstByPluraleLista(final String nazionalitaPlurale) {
        return findByProperty("pluraleLista", nazionalitaPlurale);
        //        return repository.findFirstByPluraleLista(nazionalitaPlurale);
    }

    /**
     * Retrieves the first entity by a 'singular' or 'pluraleLista' property.
     * Cerca una singola entity con una query. <br>
     * Restituisce un valore valido ANCHE se esistono diverse entities <br>
     *
     * @param nazionalitaSingolarePlurale per costruire la query
     *
     * @return the FIRST founded entity
     */
    public Nazionalita findFirst(final String nazionalitaSingolarePlurale) {
        Nazionalita nazionalita;

        nazionalita = this.findFirstBySingolare(nazionalitaSingolarePlurale);
        if (nazionalita == null) {
            nazionalita = this.findFirstByPluraleLista(nazionalitaSingolarePlurale);
        }

        return nazionalita;
    }


    public List<Nazionalita> findNazionalitaDistinctByPlurali(String property) {
        List<Nazionalita> lista = new ArrayList<>();
        Set<String> set = new HashSet();
        Sort sortOrder = Sort.by(Sort.Direction.ASC, property);
        List<Nazionalita> listaAll = this.findAllSort(sortOrder);

        for (Nazionalita attivita : listaAll) {
            if (set.add(attivita.pluraleLista)) {
                lista.add(attivita);
            }
        }

        return lista;
    }

        public List<Nazionalita> findNazionalitaDistinctByPluraliSortPagina() {
            return findNazionalitaDistinctByPlurali("pagina");
        }

        public List<Nazionalita> findNazionalitaDistinctByPluraliSortPlurali() {
            return findNazionalitaDistinctByPlurali("pluraleLista");
        }

    /**
     * Pagine che esistono sul server wikipedia e che non superano la soglia prevista per le liste <br>
     * flag esistePagina=true <br>
     * flag superaSoglia=false <br>
     *
     * @return attività con liste da cancellare
     */
    public List<Nazionalita> findPagineDaCancellare() {
        List<Nazionalita> listaDaCancellare = new ArrayList<>();
        List<Nazionalita> listaPlurali = findNazionalitaDistinctByPluraliSortPlurali();

        for (Nazionalita nazionalita : listaPlurali) {
            if (nazionalita.esistePaginaLista && !nazionalita.superaSoglia) {
                listaDaCancellare.add(nazionalita);
            }
        }

        return listaDaCancellare;
    }

    //    public List<Nazionalita> findAllBySingolare(final String singolare) {
    //        Nazionalita nazionalita = findFirstBySingolare(singolare);
    //        if (nazionalita != null) {
    //            return null;
    ////            return repository.findAllByPluraleListaOrderBySingolareAsc(nazionalita.pluraleLista);
    //        }
    //        else {
    //            return null;
    //        }
    //    }

    public List<Nazionalita> findAllByPlurale(final String nazionalitaPlurale) {
        return findAllByProperty("pluraleLista", nazionalitaPlurale);
        //        return repository.findAllByPluraleListaOrderBySingolareAsc(plurale);
    }

    //    public List<Nazionalita> findAllBySingolarePlurale(final String singolarePlurale) {
    //        Nazionalita nazionalita = findFirst(singolarePlurale);
    //        return nazionalita != null ? repository.findAllByPluraleListaOrderBySingolareAsc(nazionalita.pluraleLista) : null;
    //    }

    public String pluraleBySingolarePlurale(final String nazionalitaSingolarePlurale) {
        Nazionalita nazionalita;
        nazionalita = findFirstBySingolare(nazionalitaSingolarePlurale);

        if (nazionalita != null) {
            return nazionalita.pluraleLista;
        }
        else {
            nazionalita = findFirstByPlurale(nazionalitaSingolarePlurale);
            if (nazionalita != null) {
                return nazionalita.pluraleLista;
            }
            else {
                return VUOTA;
            }
        }
    }

    public List<String> findAllSingolari(final String singolarePlurale) {
        Nazionalita nazionalita = findFirst(singolarePlurale);
        return nazionalita != null ? findAllSingolariByPlurale(nazionalita.pluraleLista) : null;
    }

    public List<String> findAllSingolariBySingolare(final String singolare) {
        String plurale = pluraleBySingolarePlurale(singolare);
        return findSingolariByPlurale(plurale);
    }

    public List<String> findSingolariByPlurale(final String plurale) {
        List<String> listaNomi = new ArrayList<>();
        List<Nazionalita> listaNazionalita = findAllByPlurale(plurale);

        for (Nazionalita nazionalita : listaNazionalita) {
            listaNomi.add(nazionalita.singolare);
        }

        return listaNomi;
    }

    /**
     * Crea una lista di singolari che hanno lo stesso plurale. <br>
     *
     * @param nazionalitaPlurale da selezionare
     *
     * @return lista di singolari filtrati
     */
    public List<String> findAllSingolariByPlurale(final String nazionalitaPlurale) {
        List<String> listaNomi = new ArrayList<>();
        List<Nazionalita> listaNazionalita = findAllByPlurale(nazionalitaPlurale);
        Nazionalita nazionalitaSingola;

        if (listaNazionalita.size() == 0) {
            nazionalitaSingola = findFirstBySingolare(nazionalitaPlurale);
            if (nazionalitaSingola != null) {
                listaNazionalita.add(nazionalitaSingola);
            }
        }

        for (Nazionalita attivita : listaNazionalita) {
            listaNomi.add(attivita.singolare);
        }

        return listaNomi;
    }


    public LinkedHashMap<String, List<String>> findMappaSingolariByPlurale() {
        LinkedHashMap<String, List<String>> mappa = new LinkedHashMap<>();
        List<String> lista;
        List<Nazionalita> listaAll = this.findAll();
        String plurale;
        String singolare;

        for (Nazionalita nazionalita : listaAll) {
            plurale = nazionalita.pluraleLista;
            singolare = nazionalita.singolare;

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

    /**
     * Legge le mappa di valori dai moduli di wiki: <br>
     * Modulo:Bio/Plurale nazionalità
     * Modulo:Bio/Link nazionalità
     * <p>
     * Cancella la (eventuale) precedente lista di attività <br>
     */
    public WResult download() {
        WResult result = super.download();
        long inizio = System.currentTimeMillis();
        String moduloPlurale = PATH_MODULO + PATH_PLURALE + NAZ_LOWER;
        String moduloLink = PATH_MODULO + PATH_LINK + NAZ_LOWER;
        int tempo = 3;
        int size = 0;

        message = String.format("Inizio %s() di %s. Tempo previsto: circa %d secondi.", METHOD_NAME_DOWLOAD, Nazionalita.class.getSimpleName(), tempo);
        logService.debug(new WrapLog().message(message));
        logService.debug(new WrapLog().message(String.format("%sModulo %s.", FORWARD, moduloPlurale)));
        logService.debug(new WrapLog().message(String.format("%sModulo %s.", FORWARD, moduloLink)));

        size += downloadNazionalitaPlurali(moduloPlurale);
        size += downloadNazionalitaLink(moduloLink);
        result.setIntValue(size);

        return result;
    }

    /**
     * Legge le mappa dal Modulo:Bio/Plurale nazionalità <br>
     * Crea le nazionalità <br>
     *
     * @param moduloPlurale della pagina su wikipedia
     *
     * @return entities create
     */
    public int downloadNazionalitaPlurali(String moduloPlurale) {
        int size = 0;
        String singolare;
        String pluraleParagrafo;
        String pluraleLista = VUOTA;
        String linkPaginaNazione = VUOTA;
        AETypeGenere typeGenere = null;
        Genere genere;
        AEntity entityBean;
        List<AEntity> lista;

        Map<String, String> mappa = wikiApiService.leggeMappaModulo(moduloPlurale);

        if (mappa != null && mappa.size() > 0) {
            deleteAll();
            lista = new ArrayList<>();
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                singolare = entry.getKey();
                pluraleLista = entry.getValue();
                //                genere = genereBackend.findFirstBySingolare(singolare);
                //                typeGenere = genere != null ? genere.getType() : AETypeGenere.nessuno;
                //                pluraleParagrafo = getParagrafo(genere, pluraleLista);
                pluraleParagrafo = pluraleLista;

                //                public Nazionalita newEntity(
                //                final String singolare,
                //                final String pluraleParagrafo,
                //                final String pluraleLista,
                //                final String linkPaginaNazione) {

                entityBean = insert(newEntity(singolare, pluraleParagrafo, pluraleLista, linkPaginaNazione));
                if (entityBean != null) {
                    size++;
                }
                else {
                    logService.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", singolare))));
                }
            }
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", moduloPlurale);
            logService.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

        return size;
    }


    /**
     * Legge le mappa dal Modulo:Bio/Link nazionalità <br>
     * Aggiunge il link alla pagina wiki della nazionalità <br>
     *
     * @param moduloLink della pagina su wikipedia
     *
     * @return entities create
     */
    public int downloadNazionalitaLink(String moduloLink) {
        int cont = 0;
        String singolare;
        String linkPagina;
        Map<String, String> mappaLink = wikiApiService.leggeMappaModulo(moduloLink);
        List<Nazionalita> listaAllAttivita = findAll();

        if (mappaLink != null && mappaLink.size() > 0 && listaAllAttivita != null) {
            for (Nazionalita attivita : listaAllAttivita) {
                singolare = attivita.singolare;

                if (mappaLink.containsKey(singolare)) {
                    linkPagina = mappaLink.get(singolare);
                    attivita.linkPaginaNazione = linkPagina;
                    save(attivita);
                }
                else {
                    if (queryService.isEsiste(singolare)) {
                        attivita.linkPaginaNazione = singolare;
                        save(attivita);
                    }
                    else {
                        cont++;
                        logService.info(new WrapLog().message(String.format("Manca %s", singolare)));
                    }
                }
            }
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il %s", mappaLink);
            logService.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return 0;
        }

        if (cont > 0) {
            logService.info(new WrapLog().message(String.format("Mancano %d linkAttivita", cont)));
        }

        return cont;
    }


    public int countNazionalitaDaCancellare() {
        int daCancellare = 0;
        List<Nazionalita> listaPlurali = findNazionalitaDistinctByPluraliSortPagina();

        for (Nazionalita attivita : listaPlurali) {
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
    public WResult elabora() {
        WResult result = super.elabora();
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
        int tempo = 77;

        //--Check di validità del database mongoDB
        if (checkValiditaDatabase().isErrato()) {
            return null;
        }

        message = String.format("Inizio %s() di %s. Tempo previsto: circa %d secondi.", METHOD_NAME_ELABORA, Nazionalita.class.getSimpleName(), tempo);
        logService.debug(new WrapLog().message(message));

        for (Nazionalita nazionalita : findAll()) {
            nazionalita.numSingolari = 0;
            nazionalita.numBio = 0;
            nazionalita.superaSoglia = false;
            nazionalita.esistePaginaLista = false;
            update(nazionalita);
        }

        //--Spazzola tutte le nazionalità distinte plurali (circa 284)
        //--Per ognuna recupera le nazionalità singolari
        //--Per ognuna nazionalità singolare calcola quante biografie la usano
        //--Memorizza e registra il dato nella entityBean
        listaPlurali = findAllPluraliDistinti();
        for (String plurale : listaPlurali) {
            numBio = 0;
            numSingolari = 0;

            listaSingolari = findSingolariByPlurale(plurale);
            for (String singolare : listaSingolari) {
                numBio += bioBackend.countNazionalita(singolare);
                numSingolari++;
            }

            for (Nazionalita nazionalitaOK : findAllByPlurale(plurale)) {
                nazionalitaOK.numBio = numBio;
                nazionalitaOK.superaSoglia = numBio >= soglia ? true : false;
                nazionalitaOK.esistePaginaLista = esistePagina(nazionalitaOK.pluraleLista);
                nazionalitaOK.numSingolari = numSingolari;
                update(nazionalitaOK);

                if (Pref.debug.is()) {
                    cont++;
                    if (mathService.multiploEsatto(100, cont)) {
                        size = textService.format(cont);
                        time = dateService.deltaText(inizio);
                        message = String.format("Finora controllata l'esistenza di %s/%s liste di nazionalità, in %s", size, tot, time);
                        logService.info(new WrapLog().message(message).type(AETypeLog.elabora));
                    }
                }
            }
        }

        return super.fixElabora(result);
    }

    //    /**
    //     * Esegue un azione di upload, specifica del programma/package in corso <br>
    //     */
    //    public void uploadAll() {
    //        appContext.getBean(UploadNazionalita.class).test().uploadA();
    //        WResult result;
    //        long inizio = System.currentTimeMillis();
    //        int sottoSoglia = 0;
    //        int daCancellare = 0;
    //        int modificate = 0;
    //        int nonModificate = 0;
    //        List<String> listaPluraliUnici = findAllPlurali();
    //        this.fixNext();
    //
    //        for (String pluraleNazionalita : listaPluraliUnici) {
    //            result = uploadPagina(pluraleNazionalita);
    //            if (result.isValido()) {
    //                if (result.isModificata()) {
    //                    modificate++;
    //                }
    //                else {
    //                    nonModificate++;
    //                }
    //            }
    //            else {
    //                sottoSoglia++;
    //                if (result.getErrorCode().equals(KEY_ERROR_CANCELLANDA)) {
    //                    daCancellare++;
    //                }
    //            }
    //        }
    //        super.fixUploadMinuti(inizio, sottoSoglia, daCancellare, nonModificate, modificate, "nazionalità");
    //    }

    /**
     * Controlla l'esistenza della pagina wiki relativa a questa nazionalità (lista) <br>
     */
    public boolean esistePagina(String pluraleNazionalita) {
        String wikiTitle = "Progetto:Biografie/Nazionalità/" + textService.primaMaiuscola(pluraleNazionalita);
        return appContext.getBean(QueryExist.class).isEsiste(wikiTitle);
    }

    /**
     * Scrive una pagina definitiva sul server wiki <br>
     */
    public WResult uploadPagina(String pluraleNazionalitaMinuscolo) {
        WResult result = WResult.errato();
        appContext.getBean(UploadNazionalita.class).upload(pluraleNazionalitaMinuscolo);

        //        String message;
        //        int numVoci = bioBackend.countNazionalitaPlurale(pluraleNazionalitaMinuscola);
        //        String voci = textService.format(numVoci);
        //        String pluraleNazionalitaMaiuscola = textService.primaMaiuscola(pluraleNazionalitaMinuscola);
        //        int soglia = WPref.sogliaAttNazWiki.getInt();
        //        String wikiTitle = "Progetto:Biografie/Nazionalità/" + pluraleNazionalitaMaiuscola;
        //
        //        if (numVoci > soglia) {
        //            appContext.getBean(UploadNazionalita.class).pagina().upload(pluraleNazionalitaMinuscola);
        //            if (result.isValido()) {
        //                if (result.isModificata()) {
        //                    message = String.format("Lista %s utilizzati in %s voci biografiche", pluraleNazionalitaMinuscola, voci);
        //                }
        //                else {
        //                    message = String.format("Nazionalità %s utilizzata in %s voci biografiche. %s", pluraleNazionalitaMinuscola, voci, result.getValidMessage());
        //                }
        //                if (Pref.debug.is()) {
        //                    logger.info(new WrapLog().message(message).type(AETypeLog.upload));
        //                }
        //            }
        //            else {
        //                logger.warn(new WrapLog().message(result.getErrorMessage()).type(AETypeLog.upload));
        //            }
        //        }
        //        else {
        //            message = String.format("La nazionalità %s ha solo %s voci biografiche e non raggiunge il numero necessario per avere una pagina dedicata", pluraleNazionalitaMinuscola, voci);
        //            if (Pref.debug.is()) {
        //                result.setErrorMessage(message).setValido(false);
        //                logger.info(new WrapLog().message(message).type(AETypeLog.upload));
        //            }
        //            if (esistePagina(pluraleNazionalitaMinuscola)) {
        //                result.setErrorCode(KEY_ERROR_CANCELLANDA);
        //                message = String.format("Esiste la pagina %s che andrebbe cancellata", wikiTitle);
        //                logger.warn(new WrapLog().message(message).type(AETypeLog.upload).usaDb());
        //            }
        //        }

        return result;
    }

    public void fixNext() {
        LocalDateTime adesso = LocalDateTime.now();
        LocalDateTime prossimo = adesso.plusDays(7);
        WPref.uploadNazPluralePrevisto.setValue(prossimo);
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

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            return this.download();
        }
        else {
            return result;
        }
    }

}// end of crud backend class
