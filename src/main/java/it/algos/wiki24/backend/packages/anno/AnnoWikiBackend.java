package it.algos.wiki24.backend.packages.anno;

import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.wiki.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Fri, 08-Jul-2022
 * Time: 06:34
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class AnnoWikiBackend extends WikiBackend {


    public AnnoWikiRepository repository;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AnnoBackend annoBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public SecoloBackend secoloBackend;

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
    //@todo registrare eventualmente come costante in VaadCost il valore del Qualifier
    public AnnoWikiBackend(@Autowired @Qualifier("AnnoWiki") final MongoRepository crudRepository) {
        super(crudRepository, AnnoWiki.class);
        this.repository = (AnnoWikiRepository) crudRepository;
    }

    public AnnoWiki creaIfNotExist(final Anno annoBase, int ordine) {
        return checkAndSave(newEntity(annoBase, ordine));
    }

    public AnnoWiki checkAndSave(final AnnoWiki annoWiki) {
        return findByNome(annoWiki.nome) != null ? null : repository.insert(annoWiki);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public AnnoWiki newEntity() {
        return newEntity((Anno) null, 0);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param annoBase proveniente da vaadin23
     * @param ordine   progressivo partendo da -1000 e moltiplicando per 100 per categorizzare in ordine anche le sottopagine
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public AnnoWiki newEntity(final Anno annoBase, int ordine) {
        AnnoWiki annoWiki = AnnoWiki.annoWikiBuilder()
                .ordine(ordine)
                .nome(annoBase.nome)
                .secolo(annoBase.secolo.ordine)
                .build();

        return fixProperties(annoWiki);
    }

    public AnnoWiki fixProperties(AnnoWiki annoWiki) {
        annoWiki.pageNati = wikiUtility.wikiTitleNatiAnno(annoWiki.nome);
        annoWiki.pageMorti = wikiUtility.wikiTitleMortiAnno(annoWiki.nome);

        return annoWiki;
    }

    @Override
    public List<AnnoWiki> findAll() {
        return repository.findAllByOrderByOrdineAsc();
    }
    public List<AnnoWiki> findAllReverse() {
        return repository.findAllByOrderByOrdineDesc();
    }

    public List<String> findAllNomi() {
        return annoBackend.findNomi();
    }

    public AnnoWiki findByNome(final String nome) {
        return repository.findFirstByNome(nome);
    }

    public List<String> findAllPagine() {
        List<String> listaNomi = new ArrayList<>();
        List<Anno> listaAnni = annoBackend.findAll();

        for (Anno anno : listaAnni) {
            listaNomi.add(wikiUtility.wikiTitleNatiAnno(anno.nome));
            listaNomi.add(wikiUtility.wikiTitleMortiAnno(anno.nome));
        }

        return listaNomi;
    }

    public int countListeDaCancellare() {
        int daCancellare = 0;

        daCancellare += ((Long) repository.countAnnoWikiByNatiOkFalse()).intValue();
        daCancellare += ((Long) repository.countAnnoWikiByMortiOkFalse()).intValue();

        return daCancellare;
    }

    public List<AnnoWiki> fetchDaCancellare() {
        List<AnnoWiki> lista = null;
        lista = repository.findAllByNatiOkFalseOrMortiOkFalse();
        return lista;
    }

    /**
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void elabora() {
        long inizio = System.currentTimeMillis();
        int cont = 0;
        String size;
        String time;
        int tot = count();
        Anno anno;
        int bioNati = 0;
        int bioMorti = 0;
        String wikiTitleNati;
        String wikiTitleMorti;
        boolean esistePaginaNati = false;
        boolean esistePaginaMorti = false;
        boolean natiOk = false;
        boolean mortiOk = false;

        //--Per ogni anno calcola quante biografie lo usano (nei 2 parametri)
        //--Memorizza e registra il dato nella entityBean
        for (AnnoWiki annoWiki : findAllReverse()) {
            anno = annoBackend.findByNome(annoWiki.nome);
            bioNati = bioBackend.countAnnoNato(annoWiki.nome);
            bioMorti = bioBackend.countAnnoMorto(annoWiki.nome);

            annoWiki.bioNati = bioNati;
            annoWiki.bioMorti = bioMorti;

            wikiTitleNati = wikiUtility.wikiTitleNatiAnno(anno.nome);
            wikiTitleMorti = wikiUtility.wikiTitleMortiAnno(anno.nome);

            esistePaginaNati = queryService.isEsiste(wikiTitleNati);
            esistePaginaMorti = queryService.isEsiste(wikiTitleMorti);

            annoWiki.esistePaginaNati = esistePaginaNati;
            annoWiki.esistePaginaMorti = esistePaginaMorti;

            if (esistePaginaNati) {
                natiOk = bioNati > 0;
            }
            else {
                natiOk = bioNati == 0;
            }
            if (esistePaginaMorti) {
                mortiOk = bioMorti > 0;
            }
            else {
                mortiOk = bioMorti == 0;
            }

            annoWiki.natiOk = natiOk;
            annoWiki.mortiOk = mortiOk;

            update(annoWiki);

            if (Pref.debug.is()) {
                cont++;
                if (mathService.multiploEsatto(303, cont)) {
                    size = textService.format(cont);
                    time = dateService.deltaText(inizio);
                    message = String.format("Finora controllata l'esistenza di %s/%s anni, in %s", size, tot, time);
                    logger.info(new WrapLog().message(message).type(AETypeLog.elabora));
                }
            }
        }

        super.fixElaboraMinuti(inizio, "anni");
    }

    public Map elaboraValidi() {
        Map<String, Integer> mappa = new HashMap<>();
        List<String> nati = mongoService.projectionString(Bio.class, "annoNato");
        List<String> morti = mongoService.projectionString(Bio.class, "annoMorto");
        int vociBiografiche = mongoService.count(Bio.class);
        Long natiSenzaParametro; //senza parametro
        Long natiParametroVuoto; //parametro vuoto
        Long natiValoreEsistente; //qualsiasi valore
        Long mortiSenzaParametro; //senza parametro
        Long mortiParametroVuoto; //parametro vuoto
        Long mortiValoreEsistente; //qualsiasi valore
        List<String> mortiLinkati;
        int checkSum;

        natiSenzaParametro = nati.stream().filter(anno -> anno == null).count();
        natiParametroVuoto = nati.stream().filter(anno -> anno != null && anno.length() == 0).count();
        natiValoreEsistente = nati.stream().filter(anno -> anno != null && anno.length() > 0).count();

        mortiSenzaParametro = morti.stream().filter(anno -> anno == null).count();
        mortiParametroVuoto = morti.stream().filter(anno -> anno != null && anno.length() == 0).count();
        mortiValoreEsistente = morti.stream().filter(anno -> anno != null && anno.length() > 0).count();

        checkSum = natiSenzaParametro.intValue() + natiParametroVuoto.intValue() + natiValoreEsistente.intValue();
        if (checkSum != vociBiografiche) {
            logger.warn(WrapLog.build().message("Somma anno di nascita errata"));
        }
        checkSum = mortiSenzaParametro.intValue() + mortiParametroVuoto.intValue() + mortiValoreEsistente.intValue();
        if (checkSum != vociBiografiche) {
            logger.warn(WrapLog.build().message("Somma anno di morte errata"));
        }

        mappa.put(KEY_MAP_NATI_SENZA_PARAMETRO, natiSenzaParametro.intValue());
        mappa.put(KEY_MAP_NATI_PARAMETRO_VUOTO, natiParametroVuoto.intValue());
        mappa.put(KEY_MAP_NATI_VALORE_ESISTENTE, natiValoreEsistente.intValue());

        mappa.put(KEY_MAP_MORTI_SENZA_PARAMETRO, mortiSenzaParametro.intValue());
        mappa.put(KEY_MAP_MORTI_PARAMETRO_VUOTO, mortiParametroVuoto.intValue());
        mappa.put(KEY_MAP_MORTI_VALORE_ESISTENTE, mortiValoreEsistente.intValue());

        return mappa;
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
        List<Anno> anniBase;
        int delta = DELTA_ORDINE_ANNI;
        int ordine = 0;

        if (secoloBackend.count() < 1) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la collezione 'Secolo'")).usaDb());
            return result;
        }

        if (result.isValido()) {
            Sort sort = Sort.by(Sort.Direction.ASC, "ordine");
            anniBase = annoBackend.findAll(sort);
            for (Anno anno : anniBase) {
                ordine += delta;
                creaIfNotExist(anno, ordine);
            }
        }
        else {
            return result;
        }

        return fixResult(result);
    }

}// end of crud backend class
