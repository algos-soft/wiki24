package it.algos.wiki24.backend.packages.nomecategoria;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.wiki.query.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 30-Jun-2023
 * Time: 21:18
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class NomeCategoriaBackend extends WikiBackend {

    public String catMaschile = "Prenomi italiani maschili";
    public String catFemminile = "Prenomi italiani femminili";
    public String catEntrambi = "Prenomi italiani sia maschili che femminili";

    public NomeCategoriaBackend() {
        super(NomeCategoria.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastDownload = WPref.downloadNomiModulo;
        //        super.lastUpload = WPref.uploadNomiNodulo;

        super.sorgenteDownload = TAG_INCIPIT_NOMI;
        super.tagSplitSorgente = VIRGOLA_CAPO;
        super.uploadTestName = UPLOAD_TITLE_DEBUG + INCIPIT_NOMI;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public NomeCategoria newEntity() {
        return newEntity(VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public NomeCategoria newEntity(final String keyPropertyValue) {
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
    public NomeCategoria newEntity(final String nome, final String linkPagina) {
        NomeCategoria newEntityBean = NomeCategoria.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .linkPagina(textService.isValid(linkPagina) ? linkPagina : null)
                .build();

        return (NomeCategoria) super.fixKey(newEntityBean);
    }


    @Override
    public NomeCategoria findById(final String keyID) {
        return (NomeCategoria) super.findById(keyID);
    }

    @Override
    public NomeCategoria findByKey(final String keyValue) {
        return (NomeCategoria) super.findByKey(keyValue);
    }

    @Override
    public NomeCategoria findByProperty(final String propertyName, final Object propertyValue) {
        return (NomeCategoria) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public NomeCategoria save(AEntity entity) {
        return (NomeCategoria) super.save(entity);
    }

    @Override
    public NomeCategoria insert(AEntity entity) {
        return (NomeCategoria) super.insert(entity);
    }

    @Override
    public NomeCategoria update(AEntity entity) {
        return (NomeCategoria) super.update(entity);
    }

    @Override
    public List<NomeCategoria> findAll() {
        return super.findAll();
    }

    @Override
    public List<NomeCategoria> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<NomeCategoria> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<NomeCategoria> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<NomeCategoria> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();

        //--Cancella la (eventuale) precedente lista di nomi template
        deleteAll();

        result = downloadCatNomiMaschili(result);
        result = downloadCatNomiFemminili(result);
        result = downloadCatNomiEntrambi(result);

        return super.fixResetDownload(result);
    }

    /**
     * Legge i valori dalla Categoria:Prenomi italiani maschili
     *
     * @return entities create
     */
    public AResult downloadCatNomiMaschili(AResult result) {
        AEntity entityBean;
        List<AEntity> lista = new ArrayList<>();

         appContext.getBean(QueryCat.class).getListaPageIds(CATEGORIA + DUE_PUNTI + catMaschile);
//        for (String riga : super.getRighe()) {
//            entityBean = creaIfNotExist(riga);
//            result.setValido(fixLista(lista, entityBean, riga));
//        }

        return super.fixResult(result, lista);
    }

    /**
     * Legge i valori dalla Categoria:Prenomi italiani femminili
     *
     * @return entities create
     */
    public AResult downloadCatNomiFemminili(AResult result) {
        AEntity entityBean;
        List<AEntity> lista = new ArrayList<>();

        //        for (String riga : super.getRighe()) {
        //            entityBean = creaIfNotExist(riga);
        //            result.setValido(fixLista(lista, entityBean, riga));
        //        }

        return super.fixResult(result, lista);
    }

    /**
     * Legge i valori dalla Categoria:Prenomi italiani sia maschili che femminili
     *
     * @return entities create
     */
    public AResult downloadCatNomiEntrambi(AResult result) {
        AEntity entityBean;
        List<AEntity> lista = new ArrayList<>();

        //        for (String riga : super.getRighe()) {
        //            entityBean = creaIfNotExist(riga);
        //            result.setValido(fixLista(lista, entityBean, riga));
        //        }

        return super.fixResult(result, lista);
    }

}// end of crud backend class
