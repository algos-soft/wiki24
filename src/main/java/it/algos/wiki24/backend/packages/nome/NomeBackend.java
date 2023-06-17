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
        super.lastElaborazione = WPref.elaboraNomi;
        super.durataElaborazione = WPref.elaboraNomiTime;
        super.lastUpload = WPref.uploadNomi;
        super.durataUpload = WPref.uploadNomiTime;

        this.unitaMisuraDownload = AETypeTime.secondi;
        this.unitaMisuraElaborazione = AETypeTime.secondi;
        this.unitaMisuraUpload = AETypeTime.minuti;
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
        return newEntity(keyPropertyValue, VUOTA);
    }

    public Nome newEntity(final String keyPropertyValue, final String paginaLista) {
        Nome newEntityBean = Nome.builder()
                .nome(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .paginaLista(textService.isValid(paginaLista) ? paginaLista : textService.primaMaiuscola(keyPropertyValue))
                .numBio(0)
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


    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();
        long inizio = System.currentTimeMillis();
        List<NomeDoppio> listaDoppi;
        String message;
        int tot = 0;
        int cont = 0;
        String contTxt;
        String totTxt;
        String tempo;
        Nome entityBean;
        String tagSpazio = SPAZIO;
        message = String.format("Creazione completa nomi delle biografie. Circa 2 minuti.");
        System.out.println(message);

        //--Ricrea al volo (per sicurezza di aggiornamento) tutta la collezione mongoDb dei doppinomi
        doppioNomeBackend.download();
        listaDoppi = doppioNomeBackend.findAll();

        //--Cancella tutte le entities della collezione
        deleteAll();

        DistinctIterable<String> listaNomiDistinti = mongoService.getCollection(TAG_BIO).distinct("nome", String.class);
        for (String nomeTxt : listaNomiDistinti) {
            tot++;

            //--Nome 'semplici'. Quelli 'doppi' vengono inseriti dopo da apposita lista
            if (textService.isValid(nomeTxt) && !nomeTxt.contains(tagSpazio)) {
                if (creaIfNotExist(nomeTxt) != null) {
                    cont++;
                }// end of if cycle
            }// end of if cycle

        }// end of for cycle

        //--Nome 'doppi' inseriti da apposita lista
//        if (listaDoppi != null) {
//            for (NomeDoppio nomeDoppio : listaDoppi) {
//                if (!isExistByKey(nomeDoppio.nome)) {
//                    entityBean = newEntity(nomeDoppio.nome);
//                    entityBean.doppio = true;
//                    save(entityBean);
//                    cont++;
//                }
//            }// end of for cycle
//        }// end of if cycle

        //        super.setLastElabora(EATempo.minuti, inizio);
        //        contTxt = textService.format(cont);
        //        totTxt = textService.format(tot);
        //        tempo = dateService.get(inizio);
        //        message = String.format("Creazione di %s nomi su un totale di %s nomi distinti. Tempo impiegato: %s", contTxt, totTxt, tempo);
        //        System.out.println(message);
        //        logService.info(new WrapLog().message(message).usaDb());
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

}// end of crud backend class
