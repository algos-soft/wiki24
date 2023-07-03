package it.algos.wiki24.backend.packages.nomeincipit;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.nomecategoria.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.upload.moduliSoloAdmin.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 18-Jun-2023
 * Time: 12:06
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class NomeIncipitBackend extends WikiBackend {

    @Autowired
    public NomeCategoriaBackend nomeCategoriaBackend;

    public NomeIncipitBackend() {
        super(NomeIncipit.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.downloadNomiModulo;
        super.lastDownload = WPref.downloadNomiModulo;
        super.lastUpload = WPref.uploadNomiNodulo;

        super.sorgenteDownload = TAG_INCIPIT_NOMI;
        super.tagSplitSorgente = VIRGOLA_CAPO;
        super.uploadTestName = UPLOAD_TITLE_DEBUG + INCIPIT_NOMI;
    }


    public NomeIncipit creaIfNotExist(final String riga) {
        NomeIncipit entityBean;
        WrapDueStringhe wrap = wikiUtility.creaWrapUgualePulito(riga);

        entityBean = newEntity(wrap.prima, wrap.seconda);
        return entityBean != null ? insert(entityBean) : null;
    }

    public NomeIncipit creaIfNotExist(final String keyPropertyValue, final String linkPagina) {
        NomeIncipit entityBean;

        if (textService.isEmpty(keyPropertyValue) || isExistByKey(keyPropertyValue)) {
            return null;
        }
        else {
            entityBean = newEntity(keyPropertyValue, linkPagina);
            return entityBean != null ? insert(entityBean) : null;
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public NomeIncipit newEntity() {
        return newEntity(VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public NomeIncipit newEntity(final String keyPropertyValue) {
        return newEntity(keyPropertyValue, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param nome       (obbligatorio, unico)
     * @param linkPagina (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public NomeIncipit newEntity(final String nome, final String linkPagina) {
        NomeIncipit newEntityBean = NomeIncipit.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .linkPagina(textService.isValid(linkPagina) ? linkPagina : null)
                .build();

        return (NomeIncipit) super.fixKey(newEntityBean);
    }


    @Override
    public NomeIncipit findById(final String keyID) {
        return (NomeIncipit) super.findById(keyID);
    }

    @Override
    public NomeIncipit findByKey(final String keyValue) {
        return (NomeIncipit) super.findByKey(keyValue);
    }

    @Override
    public NomeIncipit findByProperty(final String propertyName, final Object propertyValue) {
        return (NomeIncipit) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public NomeIncipit save(AEntity entity) {
        return (NomeIncipit) super.save(entity);
    }

    @Override
    public NomeIncipit insert(AEntity entity) {
        return (NomeIncipit) super.insert(entity);
    }

    @Override
    public NomeIncipit update(AEntity entity) {
        return (NomeIncipit) super.update(entity);
    }

    @Override
    public List<NomeIncipit> findAll() {
        return super.findAll();
    }

    @Override
    public List<NomeIncipit> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<NomeIncipit> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<NomeIncipit> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<NomeIncipit> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    @Override
    public LinkedHashMap<String, String> findMappa() {
        LinkedHashMap<String, String> mappa = new LinkedHashMap<>();
        List<NomeIncipit> lista = findAllSortKey();

        for (NomeIncipit nomeModulo : lista) {
            mappa.put(nomeModulo.nome, nomeModulo.linkPagina);
        }

        return mappa;
    }

    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();

        //--Cancella la (eventuale) precedente lista di nomi template
        deleteAll();

        result = downloadNomiModulo(result);

        return super.fixResetDownload(result);
    }

    /**
     * Legge i valori dalla pagina wiki: Template:Incipit lista nomi
     * Crea nomi template <br>
     *
     * @return entities create
     */
    public AResult downloadNomiModulo(AResult result) {
        NomeIncipit entityBean;
        List<AEntity> lista = new ArrayList<>();

        for (String riga : super.getRighe()) {
            entityBean = creaIfNotExist(riga);
            result.setValido(fixLista(lista, entityBean, riga));
        }

        return super.fixResult(result, lista);
    }


    @Override
    public WResult elabora() {
        WResult result = super.elabora();
        List<NomeCategoria> listaNomiCategorie = null;
        NomeIncipit entityBean;
        List<AEntity> lista = new ArrayList<>();

        resetDownload();

        //--Controllo e recupero di NomiCategoria
        nomeCategoriaBackend.resetDownload();
        listaNomiCategorie = nomeCategoriaBackend.findAll();

        if (listaNomiCategorie != null) {
            for (NomeCategoria nomeCategoria : listaNomiCategorie) {

                entityBean = creaIfNotExist(nomeCategoria.nome, nomeCategoria.linkPagina);
                result.setValido(fixLista(lista, entityBean, nomeCategoria.nome));
            }
        }

        return super.fixElabora(result);
    }


    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    @Override
    public WResult uploadModulo() {
        WResult result = appContext.getBean(UploadModuloNomiIncipit.class).esegue();

        if (result.isModificata()) {
            message = String.format("Upload e modifica della pagina [%s]", super.sorgenteDownload);
            logService.info(new WrapLog().message(message).type(AETypeLog.upload).usaDb());
        }

        return result;
    }

}// end of crud backend class
