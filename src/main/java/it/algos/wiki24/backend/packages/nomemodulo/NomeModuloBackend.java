package it.algos.wiki24.backend.packages.nomemodulo;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.upload.moduli.*;
import it.algos.wiki24.backend.upload.progetto.*;
import it.algos.wiki24.backend.wrapper.*;
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
public class NomeModuloBackend extends WikiBackend {


    public NomeModuloBackend() {
        super(NomeModulo.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.downloadNomiTemplate;
        super.lastDownload = WPref.downloadNomiTemplate;

        super.sorgenteDownload = TAG_INCIPIT_NOMI;
        super.tagSplitSorgente = VIRGOLA_CAPO;
        super.uploadTestName = UPLOAD_TITLE_DEBUG + INCIPIT_NOMI;
    }


    public AEntity creaIfNotExist(final String riga) {
        AEntity entityBean;
        WrapDueStringhe wrap = wikiUtility.creaWrapUgualePulito(riga);

        entityBean = newEntity(wrap.getPrima(), wrap.getSeconda());
        return entityBean != null ? insert(entityBean) : null;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public NomeModulo newEntity() {
        return newEntity(VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public NomeModulo newEntity(final String keyPropertyValue) {
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
    public NomeModulo newEntity(final String nome, final String linkPagina) {
        NomeModulo newEntityBean = NomeModulo.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .linkPagina(textService.isValid(linkPagina) ? linkPagina : null)
                .build();

        return (NomeModulo) super.fixKey(newEntityBean);
    }


    @Override
    public NomeModulo findById(final String keyID) {
        return (NomeModulo) super.findById(keyID);
    }

    @Override
    public NomeModulo findByKey(final String keyValue) {
        return (NomeModulo) super.findByKey(keyValue);
    }

    @Override
    public NomeModulo findByProperty(final String propertyName, final Object propertyValue) {
        return (NomeModulo) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public NomeModulo save(AEntity entity) {
        return (NomeModulo) super.save(entity);
    }

    @Override
    public NomeModulo insert(AEntity entity) {
        return (NomeModulo) super.insert(entity);
    }

    @Override
    public NomeModulo update(AEntity entity) {
        return (NomeModulo) super.update(entity);
    }

    @Override
    public List<NomeModulo> findAll() {
        return super.findAll();
    }

    @Override
    public List<NomeModulo> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<NomeModulo> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<NomeModulo> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<NomeModulo> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    @Override
    public LinkedHashMap<String, String> findMappa() {
        LinkedHashMap<String, String> mappa = new LinkedHashMap<>();
        List<NomeModulo> lista = findAllSortKey();

        for (NomeModulo nomeModulo : lista) {
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
        AEntity entityBean;
        List<AEntity> lista = new ArrayList<>();

        for (String riga : super.getRighe()) {
            entityBean = creaIfNotExist(riga);
            result.setValido(fixLista(lista, entityBean, riga));
        }

        return super.fixResult(result, lista);
    }


    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    @Override
    public WResult uploadModulo() {
        WResult result = appContext.getBean(UploadModuloIncipitNomi.class).esegue();
        return super.fixRiordinaModulo(result);
    }

}// end of crud backend class
