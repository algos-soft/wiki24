package it.algos.wiki24.backend.packages.nome;

import com.mongodb.client.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.nomidoppi.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

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
    public NomeDoppioBackend doppioNomeBackend;


    public NomeBackend() {
        super(Nome.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = null;
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

    public Nome creaIfNotExist(final String keyPropertyValue, boolean distinto, boolean incipit, boolean doppio) {
        Nome newNome;

        if (textService.isEmpty(keyPropertyValue) || isExistByKey(keyPropertyValue)) {
            return null;
        }
        else {
            newNome = newEntity(keyPropertyValue, distinto, incipit, doppio);
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
        return newEntity(keyPropertyValue, false, false, false);
    }

    public Nome newEntity(final String keyPropertyValue, boolean distinto, boolean incipit, boolean doppio) {
        Nome newEntityBean = Nome.builder()
                .nome(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .numBio(0)
                .distinto(distinto)
                .incipit(incipit)
                .doppio(doppio)
                .superaSoglia(false)
                .esisteLista(false)
                .build();

        return (Nome) super.fixKey(newEntityBean);
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
        List<String> listaIncipit;
        Nome entityBean;
        List<NomeDoppio> listaDoppi;
        String message;
        int numBio;
        int sogliaMongo = WPref.sogliaNomiMongo.getInt(); //--Soglia minima per creare una entity nella collezione Nomi sul mongoDB
        int sogliaWiki = WPref.sogliaNomiWiki.getInt(); //--Soglia minima per creare una pagina sul server wiki
        int tot = 0;
        int cont = 0;
        String contTxt;
        String totTxt;
        String tempo;
        String tagSpazio = SPAZIO;
        message = String.format("Creazione completa nomi delle biografie. Circa 2 minuti.");
        System.out.println(message);

        //--Ricrea al volo (per sicurezza di aggiornamento) tutta la collezione mongoDb dei doppinomi
        doppioNomeBackend.download();
        listaDoppi = doppioNomeBackend.findAll();

        //--Cancella tutte le entities della collezione
        deleteAll();

        //--Nomi 'semplici'. Ricavati dalla collection Bio sul server mongo. Quelli 'doppi' vengono inseriti dopo da apposita lista
        DistinctIterable<String> listaNomiDistinti = mongoService.getCollection(TAG_BIO).distinct("nome", String.class);
        for (String distinto : listaNomiDistinti) {
            tot++;

            if (textService.isValid(distinto) && !distinto.contains(tagSpazio)) {
                numBio = bioBackend.countNome(distinto);
                if (numBio > sogliaMongo) {
                    if (creaIfNotExist(distinto, true, false, false) != null) {
                        cont++;
                    }// end of if cycle
                }
                else {
                    message = String.format("Le %d occorrenze di %s non sono sufficienti per creare una entity su mongo", numBio, distinto);
                    System.out.println(message);
                }
            }// end of if cycle
        }// end of for cycle

        //--Nomi 'incipit'. Ricavati dal Template Bio sul server Wiki. Quelli 'doppi' vengono inseriti dopo da apposita lista
        //--Se il nome esiste già, cambia solo il flag altrimenti lo crea (anche se non esiste nessuna voce Bio sul server Mongo)
        listaIncipit = downloadNomiIncipit();
        for (String incipit : listaIncipit) {
            if (isExistByKey(incipit)) {
                entityBean = findByKey(incipit);
                entityBean.incipit = true;
                entityBean.doppio = false;
                save(entityBean);
            }
            else {
                insert(newEntity(incipit, false, true, false));
            }
        }

        //--Nome 'doppi' inseriti da apposita lista
        //--Se il nome esiste già, cambia solo il flag altrimenti lo crea (anche se non esiste nessuna voce Bio sul server Mongo)
        if (listaDoppi != null) {
            for (NomeDoppio nomeDoppio : listaDoppi) {
                if (isExistByKey(nomeDoppio.nome)) {
                    entityBean = findByKey(nomeDoppio.nome);
                    entityBean.doppio = true;
                    save(entityBean);
                }
                else {
                    insert(newEntity(nomeDoppio.nome, false, false, true));
                }
            }// end of for cycle
        }// end of if cycle

        return super.fixResetDownload(result);
    }// end of method

    //    /**
    //     * Registra il numero di voci biografiche che hanno il nome indicato <br>
    //     * Sono validi i nome 'semplici' oppure quelli dell'apposita collection 'doppinomi' <br>
    //     */
    //    public Nome saveNome(String nomeTxt) {
    //        Nome nome = null;
    //        //--Soglia minima per creare una entity nella collezione Nomi sul mongoDB
    //        int sogliaMongo = pref.getInt(SOGLIA_NOMI_MONGO, 40);
    //        //--Soglia minima per creare una pagina sul server wiki
    //        int sogliaWiki = pref.getInt(SOGLIA_NOMI_PAGINA_WIKI, 50);
    //        boolean valido;
    //        long numVoci = 0;
    //        Query query = new Query();
    //
    //        query.addCriteria(Criteria.where("nome").is(nomeTxt));
    //        numVoci = mongo.mongoOp.count(query, Bio.class);
    //        valido = numVoci > sogliaWiki;
    //
    //        if (numVoci >= sogliaMongo && text.isValid(nomeTxt)) {
    //            nome = findOrCrea(nomeTxt, (int) numVoci, valido);
    //        }// end of if cycle
    //
    //        return nome;
    //    }// end of method

    /**
     * Legge i valori dalla pagina wiki: Template:Incipit lista nomi
     *
     * @return lista dei valori
     */
    public List<String> downloadNomiIncipit() {
        List<String> lista = new ArrayList<>();
        String testoPagina;
        String tag = CAPO + "\\|";
        String secondTag = UGUALE;
        String nome;
        String[] righe = null;
        String[] parti;

        testoPagina = wikiApiService.legge(TAG_INCIPIT_NOMI);

        if (textService.isValid(testoPagina)) {
            righe = testoPagina.split(tag);
        }// end of if cycle

        //--il primo e l'ultimo vanno eliminati (non pertinenti)
        for (int k = 1; k < righe.length - 1; k++) {
            nome = righe[k];

            parti = nome.split(secondTag);
            nome = parti[0].trim();
            lista.add(nome);
        }// end of for cycle

        return lista;
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
        int sogliaMongo = WPref.sogliaNomiMongo.getInt();
        int sogliaWiki = WPref.sogliaNomiWiki.getInt();

        resetDownload();

        for (Nome nome : findAll()) {
            nome.numBio = bioBackend.countNome(nome.nome);
            nome.paginaLista = VUOTA;
            nome.esisteLista = false;
            nome.superaSoglia = nome.numBio > sogliaWiki;
            if (nome.numBio > sogliaMongo) {
                nome.paginaLista = TAG_PERSONE_NOME + nome.nome;
            }
            if (nome.superaSoglia) {
                nome.esisteLista = queryService.isEsiste(nome.paginaLista);
            }
            update(nome);
        }

        return super.fixElabora(result);
    }

}// end of crud backend class
