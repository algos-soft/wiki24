package it.algos.wiki24.backend.packages.cognomeincipit;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 21-Jun-2023
 * Time: 18:10
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class CognomeIncipitBackend extends WikiBackend {


    public CognomeIncipitBackend() {
        super(CognomeIncipit.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.downloadCognomiModulo;
        super.lastDownload = WPref.downloadCognomiModulo;

        super.sorgenteDownload = TAG_INCIPIT_COGNOMI;
        super.tagSplitSorgente = VIRGOLA_CAPO;
        super.uploadTestName = UPLOAD_TITLE_DEBUG + INCIPIT_COGNOMI;
    }

    public CognomeIncipit creaIfNotExist(final String riga) {
        CognomeIncipit entityBean;
        WrapDueStringhe wrap = wikiUtility.creaWrapUgualePulito(riga);

        entityBean = newEntity(wrap.prima, wrap.seconda);
        return entityBean != null ? insert(entityBean) : null;
    }

    public CognomeIncipit creaIfNotExist(final String keyPropertyValue, final String linkPagina, boolean aggiunto) {
        CognomeIncipit entityBean;

        if (textService.isEmpty(keyPropertyValue) || isExistByKey(keyPropertyValue)) {
            return null;
        }
        else {
            entityBean = newEntity(keyPropertyValue, linkPagina);
            return entityBean != null ? insert(entityBean) : null;
        }
    }

    public AEntity creaIfNotExist2(final String riga) {
        AEntity entityBean;
        String nome = VUOTA;
        String linkPagina = VUOTA;
        String[] parti = textService.isValid(riga) ? riga.split(UGUALE) : null;

        if (parti != null && parti.length == 2) {
            nome = parti[0].trim();
            linkPagina = parti[1].trim();
        }

        if (textService.isEmpty(nome) || isExistByKey(nome)) {
            return null;
        }
        else {
            entityBean = newEntity(nome, linkPagina);
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
    public CognomeIncipit newEntity() {
        return newEntity(VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public CognomeIncipit newEntity(final String keyPropertyValue) {
        return newEntity(keyPropertyValue, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param cognome       (obbligatorio, unico)
     * @param linkPagina (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public CognomeIncipit newEntity(final String cognome, final String linkPagina) {
        CognomeIncipit newEntityBean = CognomeIncipit.builder()
                .cognome(textService.isValid(cognome) ? cognome : null)
                .linkPagina(textService.isValid(linkPagina) ? linkPagina : null)
                .build();

        return (CognomeIncipit) super.fixKey(newEntityBean);
    }


    @Override
    public CognomeIncipit findById(final String keyID) {
        return (CognomeIncipit) super.findById(keyID);
    }

    @Override
    public CognomeIncipit findByKey(final String keyValue) {
        return (CognomeIncipit) super.findByKey(keyValue);
    }

    @Override
    public CognomeIncipit findByProperty(final String propertyName, final Object propertyValue) {
        return (CognomeIncipit) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public CognomeIncipit save(AEntity entity) {
        return (CognomeIncipit) super.save(entity);
    }

    @Override
    public CognomeIncipit insert(AEntity entity) {
        return (CognomeIncipit) super.insert(entity);
    }

    @Override
    public CognomeIncipit update(AEntity entity) {
        return (CognomeIncipit) super.update(entity);
    }

    @Override
    public List<CognomeIncipit> findAll() {
        return super.findAll();
    }

    @Override
    public List<CognomeIncipit> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<CognomeIncipit> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<CognomeIncipit> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<CognomeIncipit> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();

        //--Cancella la (eventuale) precedente lista di nomi template
        deleteAll();

        result = downloadCognomiModulo(result);

        return super.fixResetDownload(result);
    }

    /**
     * Legge i valori dalla pagina wiki: Template:Incipit lista nomi
     * Crea nomi template <br>
     *
     * @return entities create
     */
    public AResult downloadCognomiModulo(AResult result) {
        CognomeIncipit entityBean;
        List<AEntity> lista = new ArrayList<>();

        for (String riga : super.getRighe()) {
            entityBean = creaIfNotExist(riga);
            result.setValido(fixLista(lista, entityBean, riga));
        }

        return super.fixResult(result, lista);
    }

}// end of crud backend class
