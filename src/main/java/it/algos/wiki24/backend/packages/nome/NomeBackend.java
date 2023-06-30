package it.algos.wiki24.backend.packages.nome;

import com.mongodb.client.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.nomedoppio.*;
import it.algos.wiki24.backend.packages.nomemodulo.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

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
    public NomeDoppioBackend nomeDoppioBackend;

    @Autowired
    public NomeModuloBackend nomeModuloBackend;


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

        this.unitaMisuraDownload = AETypeTime.secondi;
        this.unitaMisuraElaborazione = AETypeTime.minuti;
        this.unitaMisuraUpload = AETypeTime.minuti;
    }

    public Nome creaIfNotExist(final String keyPropertyValue, int numBio, boolean distinto, boolean doppio, boolean template) {
        return creaIfNotExist(keyPropertyValue, numBio, distinto, doppio, template, VUOTA, VUOTA);
    }

    public Nome creaIfNotExist(final String keyPropertyValue, int numBio, boolean distinto, boolean doppio, boolean template, String paginaVoce, String paginaLista) {
        Nome newNome;

        if (textService.isEmpty(keyPropertyValue) || isExistByKey(keyPropertyValue)) {
            return null;
        }
        else {
            newNome = newEntity(keyPropertyValue, numBio, distinto, doppio, template, paginaVoce, paginaLista);
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
        return newEntity(keyPropertyValue, 0, false, false, false, VUOTA, VUOTA);
    }

    public Nome newEntity(final String keyPropertyValue, int numBio, boolean distinto, boolean doppio, boolean modulo, String paginaVoce, String paginaLista) {
        Nome newEntityBean = Nome.builder()
                .nome(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .numBio(numBio)
                .distinto(distinto)
                .modulo(modulo)
                .doppio(doppio)
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

        //--Nomi 'semplici'. Ricavati dalla collection Bio sul server mongo. Quelli 'doppi' vengono inseriti dopo da apposita lista
        estraeNomiDistinti(result);

        //--Nome 'doppi' inseriti da apposita lista
        addNomiDoppi(result);

        //--Nomi 'incipit'. Ricavati dal Modulo sul server Wiki.
        addNomiModulo(result);

        result.fine();
        result.valido(true).eseguito();

        return super.fixResetDownload(result);

    }// end of method

    /**
     * Legge i valori dalle biografie
     * Nomi 'semplici'. Ricavati dalla collection Bio sul server mongo. <br>
     *
     * @return entities create
     */
    public AResult estraeNomiDistinti(AResult result) {
        DistinctIterable<String> listaNomiDistinti = mongoService.getCollection(TAG_BIO).distinct("nome", String.class);
        int numBio;
        int sogliaMongo = WPref.sogliaMongoNomi.getInt(); //--Soglia minima per creare una entity nella collezione Nomi sul mongoDB
        AEntity entityBean;
        List<AEntity> lista = new ArrayList<>();
        boolean debug = Pref.debug.is();

        for (String distinto : listaNomiDistinti) {
            numBio = bioBackend.countNome(distinto);
            if (numBio > sogliaMongo) {
                entityBean = creaIfNotExist(distinto, numBio, true, false, false, VUOTA, distinto);
                result.setValido(fixLista(lista, entityBean, entityBean.id));
            }
            else {
                if (debug) {
                    message = String.format("Le %d occorrenze di %s non sono sufficienti per creare una entity su mongo", numBio, distinto);
                    logService.info(new WrapLog().message(message));
                }
            }
        }// end of for cycle

        return super.fixResult(result, lista);
    }


    /**
     * Integra con i valori dalla tavola NomiDoppi <br>
     * Ricrea al volo (per sicurezza di aggiornamento) tutta la collezione mongoDb dei doppinomi <br>
     * Se il nome esiste già, cambia solo il flag altrimenti lo crea (anche se non esiste nessuna voce Bio sul server Mongo) <br>
     */
    public AResult addNomiDoppi(AResult result) {
        List<NomeDoppio> listaDoppi;
        Nome entityBean;
        int numBio;

        nomeDoppioBackend.download();
        listaDoppi = nomeDoppioBackend.findAll();

        if (listaDoppi != null) {
            for (NomeDoppio entityBeanNomeDoppio : listaDoppi) {
                if (isExistByKey(entityBeanNomeDoppio.nome)) {
                    entityBean = findByKey(entityBeanNomeDoppio.nome);
                    entityBean.doppio = true;
                    save(entityBean);
                }
                else {
                    numBio = bioBackend.countNome(entityBeanNomeDoppio.nome);
                    creaIfNotExist(entityBeanNomeDoppio.nome, numBio, false, true, false);
                }
            }
        }

        return result;
    }

    /**
     * Integra con i valori dal modulo incipit nomi
     *
     * @return lista dei valori
     */
    public AResult addNomiModulo(AResult result) {
        List<NomeModulo> listaModulo;
        Nome entityBean;
        int numBio;
        String linkPagina = VUOTA;

        nomeModuloBackend.download();
        listaModulo = nomeModuloBackend.findAll();

        if (listaModulo != null) {
            for (NomeModulo entityBeanNomeTemplate : listaModulo) {
                linkPagina = textService.isValid(entityBeanNomeTemplate.linkPagina) ? entityBeanNomeTemplate.linkPagina : VUOTA;
                if (isExistByKey(entityBeanNomeTemplate.nome)) {
                    entityBean = findByKey(entityBeanNomeTemplate.nome);
                    entityBean.modulo = true;
                    entityBean.paginaVoce = linkPagina;
                    save(entityBean);
                }
                else {
                    numBio = bioBackend.countNome(entityBeanNomeTemplate.nome);
                    creaIfNotExist(entityBeanNomeTemplate.nome, numBio, false, false, true, linkPagina, entityBeanNomeTemplate.nome);
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

        resetDownload();

        for (Nome nome : findAll()) {
            nome.numBio = bioBackend.countNome(nome.nome);
            nome.paginaLista = VUOTA;
            nome.esisteLista = false;
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

}// end of crud backend class
