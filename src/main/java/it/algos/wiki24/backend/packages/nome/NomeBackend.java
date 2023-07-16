package it.algos.wiki24.backend.packages.nome;

import com.mongodb.client.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.nomecategoria.*;
import it.algos.wiki24.backend.packages.nomedoppio.*;
import it.algos.wiki24.backend.packages.nomeincipit.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.statistiche.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import java.text.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 14-Jun-2023
 * Time: 09:10
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class NomeBackend extends WikiBackend {

    @Autowired
    public NomeCategoriaBackend nomeCategoriaBackend;

    @Autowired
    public NomeDoppioBackend nomeDoppioBackend;

    @Autowired
    public NomeIncipitBackend nomeModuloBackend;


    public NomeBackend() {
        super(Nome.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.downloadNomi;
        super.lastDownload = WPref.downloadNomi;
        super.durataDownload = WPref.downloadNomiTime;
        super.lastElaborazione = WPref.elaboraNomi;
        super.durataElaborazione = WPref.elaboraNomiTime;
        super.lastUpload = WPref.uploadNomi;
        super.durataUpload = WPref.uploadNomiTime;

        this.unitaMisuraDownload = AETypeTime.minuti;
        this.unitaMisuraElaborazione = AETypeTime.minuti;
        this.unitaMisuraUpload = AETypeTime.minuti;
    }

    public Nome creaIfNotExist(final String keyPropertyValue, int numBio, boolean categoria, boolean doppio, boolean modulo, boolean mongo) {
        return creaIfNotExist(keyPropertyValue, numBio, categoria, doppio, modulo, mongo, VUOTA, VUOTA);
    }

    public Nome creaIfNotExist(final String keyPropertyValue, int numBio, boolean categoria, boolean doppio, boolean modulo, boolean mongo, String paginaVoce, String paginaLista) {
        Nome newNome;

        if (textService.isEmpty(keyPropertyValue) || isExistByKey(keyPropertyValue)) {
            return null;
        }
        else {
            newNome = newEntity(keyPropertyValue, numBio, categoria, doppio, modulo, mongo, paginaVoce, paginaLista);
            return newNome != null ? insert(newNome) : null;
        }
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Nome newEntity() {
        return newEntity(VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public Nome newEntity(final String keyPropertyValue) {
        return newEntity(keyPropertyValue, 0, false, false, false, false, VUOTA, VUOTA);
    }

    public Nome newEntity(final String keyPropertyValue, int numBio, boolean categoria, boolean doppio, boolean modulo, boolean mongo, String paginaVoce, String paginaLista) {
        Nome newEntityBean = Nome.builder()
                .nome(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .numBio(numBio)
                .categoria(categoria)
                .doppio(doppio)
                .modulo(modulo)
                .mongo(mongo)
                .paginaVoce(textService.isValid(paginaVoce) ? paginaVoce : null)
                .paginaLista(textService.isValid(paginaLista) ? PATH_NOMI + paginaLista : null)
                .superaSoglia(false)
                .esisteLista(false)
                .build();

        return (Nome) super.fixKey(newEntityBean);
    }


    public int count() {
        return super.count();
    }

    public int countBySopraSoglia() {
        Query query = new Query();
        String keyProperty = "superaSoglia";
        Long lungo;
        query.addCriteria(Criteria.where(keyProperty).is(true));
        lungo = mongoService.mongoOp.count(query, Nome.class);
        return lungo > 0 ? lungo.intValue() : 0;
    }


    @Override
    public Nome findById(final String keyID) {
        return (Nome) super.findById(keyID);
    }

    @Override
    public Nome findByKey(final String keyValue) {
        return (Nome) super.findByKey(keyValue);
    }

    @Override
    public Nome findByProperty(final String propertyName, final Object propertyValue) {
        return (Nome) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public Nome save(AEntity entity) {
        return (Nome) super.save(entity);
    }

    @Override
    public Nome insert(AEntity entity) {
        return (Nome) super.insert(entity);
    }

    @Override
    public Nome update(AEntity entity) {
        return (Nome) super.update(entity);
    }

    @Override
    public List<Nome> findAll() {
        return super.findAll();
    }

    @Override
    public List<Nome> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<Nome> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<Nome> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<Nome> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    public List<Nome> findAllByNumBio(int soglia) {
        Collator collator = Collator.getInstance(Locale.getDefault());

        return findAllNoSort()
                .stream()
                .filter(nome -> nome.numBio > soglia)
                .sorted(Comparator.comparing(Nome::getNome, collator))
                .collect(Collectors.toList());
    }

    public List<String> findAllForKeyByNumBio() {
        int soglia = WPref.sogliaWikiNomi.getInt();
        List<Nome> listaAll = findAllSortKey();
        return listaAll != null ? listaAll.stream().filter(nome -> nome.numBio > soglia).map(nome -> nome.nome).collect(Collectors.toList()) : null;
    }

    /**
     * ResetOnlyEmpty -> Download. <br>
     * Download -> Recupera una lista di nomi distinti dalle biografie. Crea una entity se bio>%s. <br>
     * Download -> Esegue un Download del Template:Incipit. Aggiunge i valori alla lista. <br>
     * Download -> Aggiunge alla lista i nomi doppi. <br>
     * Elabora -> Calcola le voci biografiche che usano ogni singolo nome e la effettiva presenza della paginaLista <br>
     * Upload -> Previsto per tutte le liste di nomi con bio>%s. <br>
     */
    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();
        String message;

        message = String.format("Creazione completa nomi delle biografie. Circa %d secondi.", WPref.downloadNomiTime.getInt());
        System.out.println(message);

        //--Cancella tutte le entities della collezione
        deleteAll();

        //--Nomi 'categorie'. Ricavati dalle 3 categorie su wiki
        result = addNomiCategoria(result);

        //--Nome 'doppi' Ricavati da apposita lista di progetto
        result = addNomiDoppi(result);

        //--Nomi 'incipit'. Ricavati dal Modulo sul server Wiki.
        result = addNomiModulo(result);

        //--Nomi 'semplici'. Ricavati dalla collection Bio sul server mongo.
        result = estraeNomiDistinti(result);

        result.fine();
        result.valido(true).eseguito();

        return super.fixResetDownload(result);

    }// end of method

    /**
     * Legge i valori dalla tavola NomeCategoria
     *
     * @return entities create
     */
    public AResult addNomiCategoria(AResult result) {
        Nome entityBean;
        List<AEntity> lista = new ArrayList<>();
        List<NomeCategoria> listaNomiCategorie = null;

        //--Controllo e recupero di NomiCategoria
        nomeCategoriaBackend.resetDownload();
        listaNomiCategorie = nomeCategoriaBackend.findAll();

        if (listaNomiCategorie != null) {
            for (NomeCategoria nomeCategoria : listaNomiCategorie) {
                entityBean = creaIfNotExist(nomeCategoria.nome, 0, true, false, false, false, nomeCategoria.linkPagina, null);
                result.setValido(fixLista(result, entityBean, nomeCategoria.nome));
            }
        }
        else {
            message = String.format("Mancano i NomiCategoria");
            logService.warn(new WrapLog().message(message));
        }

        return super.fixResult(result, lista);
    }


    /**
     * Legge i valori dalla tavola NomeDoppio
     */
    public AResult addNomiDoppi(AResult result) {
        Nome entityBean;
        List<AEntity> lista = new ArrayList<>();
        List<NomeDoppio> listaNomiDoppi = null;

        //--Controllo e recupero di NomiDoppi
        nomeDoppioBackend.resetDownload();
        listaNomiDoppi = nomeDoppioBackend.findAll();

        if (listaNomiDoppi != null) {
            for (NomeDoppio nomeDoppio : listaNomiDoppi) {
                if (isExistByKey(nomeDoppio.nome)) {
                    entityBean = findByKey(nomeDoppio.nome);
                    entityBean.doppio = true;
                    save(entityBean);
                }
                else {
                    entityBean = creaIfNotExist(nomeDoppio.nome, 0, false, true, false, false, null, null);
                    result.setValido(fixLista(result, entityBean, nomeDoppio.nome));
                }
            }
        }
        else {
            message = String.format("Mancano i NomiDoppi");
            logService.warn(new WrapLog().message(message));
        }

        return super.fixResult(result, lista);
    }

    /**
     * Legge i valori dalla tavola NomeModulo
     *
     * @return lista dei valori
     */
    public AResult addNomiModulo(AResult result) {
        Nome entityBean;
        List<AEntity> lista = new ArrayList<>();
        List<NomeIncipit> listaNomiModulo = null;

        //--Controllo e recupero di NomiDoppi
        nomeModuloBackend.resetDownload();
        listaNomiModulo = nomeModuloBackend.findAll();

        if (listaNomiModulo != null) {
            for (NomeIncipit nomeModulo : listaNomiModulo) {
                if (isExistByKey(nomeModulo.nome)) {
                    entityBean = findByKey(nomeModulo.nome);
                    entityBean.modulo = true;
                    if (entityBean.paginaVoce == null) {
                        entityBean.paginaVoce = nomeModulo.linkPagina;
                    }
                    save(entityBean);
                }
                else {
                    entityBean = creaIfNotExist(nomeModulo.nome, 0, false, false, true, false, nomeModulo.linkPagina, null);
                    result.setValido(fixLista(result, entityBean, nomeModulo.nome));
                }
            }
        }
        else {
            message = String.format("Mancano i NomiModulo");
            logService.warn(new WrapLog().message(message));
        }

        return super.fixResult(result, lista);
    }


    /**
     * @return lista dei valori
     */
    public AResult estraeNomiDistinti(AResult result) {
        DistinctIterable<String> listaNomiMongo = mongoService.getCollection(TAG_BIO).distinct("nome", String.class);
        Nome entityBean;
        int numBio;
        int sogliaMongo = WPref.sogliaMongoNomi.getInt();
        List<AEntity> lista = new ArrayList<>();
        boolean debug = Pref.debug.is();

        if (listaNomiMongo == null) {
            message = "listaNomiMongo is null";
            logService.warn(new WrapLog().message(message));
            return result.errorMessage(message);
        }

        for (String mongo : listaNomiMongo) {
            if (isExistByKey(mongo)) {
                entityBean = findByKey(mongo);
                entityBean.mongo = true;
                save(entityBean);
            }
            else {
                numBio = bioBackend.countNome(mongo);
                if (numBio > sogliaMongo) {
                    entityBean = creaIfNotExist(mongo, numBio, false, false, false, true, null, null);
                    result.setValido(fixLista(result, entityBean, mongo));
                }
                else {
                    if (debug) {
                        message = String.format("Le %d occorrenze di %s non sono sufficienti per creare una entity di %s su mongo", numBio, mongo, Nome.class.getSimpleName());
                        logService.info(new WrapLog().message(message));
                    }
                }
            }
        }

        return result;
    }


    /**
     * ResetOnlyEmpty -> Download. <br>
     * Download -> Recupera una lista di nomi distinti dalle biografie. Crea una entity se bio>%s. <br>
     * Download -> Esegue un Download del Template:Incipit. Aggiunge i valori alla lista. <br>
     * Download -> Aggiunge alla lista i nomi doppi. <br>
     * Elabora -> Esegue un download. Calcola le voci biografiche che usano ogni singolo nome e la effettiva presenza della paginaLista <br>
     * Upload -> Previsto per tutte le liste di nomi con bio>%s. <br>
     * <p>
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public WResult elabora() {
        WResult result = super.elabora();
        int sogliaMongo = WPref.sogliaMongoNomi.getInt();
        int sogliaWiki = WPref.sogliaWikiNomi.getInt();

        //check temporale per elaborare la collection SOLO se non è già stata elaborata di recente (1 ora)
        //visto che l'elaborazione impiega più di parecchio tempo
        LocalDateTime elaborazioneAttuale = LocalDateTime.now();
        LocalDateTime lastElaborazione = (LocalDateTime) this.lastElaborazione.get();

        lastElaborazione = lastElaborazione.plusHours(1);
        if (elaborazioneAttuale.isBefore(lastElaborazione)) {
            return result;
        }

        resetDownload();

        for (Nome nome : findAll()) {
            nome.numBio = bioBackend.countNome(nome.nome);
            nome.superaSoglia = nome.numBio > sogliaWiki;
            if (nome.numBio > sogliaMongo) {
                nome.paginaLista = PATH_NOMI + nome.nome;
            }
            if (nome.superaSoglia) {
                nome.esisteLista = queryService.isEsiste(nome.paginaLista);
            }
            update(nome);
        }

        return super.fixElabora(result);
    }

    /**
     * Esegue un azione di upload delle statistiche, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     * Prima esegue una Elaborazione <br>
     */
    @Override
    public WResult uploadStatistiche() {
        WResult result = appContext.getBean(StatisticheNomi.class).esegue();
        result = appContext.getBean(StatisticheListeNomi.class).esegue();

        logger.info(new WrapLog().message(result.getMessage()).type(AETypeLog.upload).usaDb());
        return result;
    }

}// end of crud backend class
