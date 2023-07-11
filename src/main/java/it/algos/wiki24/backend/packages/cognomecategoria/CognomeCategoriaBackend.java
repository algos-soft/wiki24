package it.algos.wiki24.backend.packages.cognomecategoria;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
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
 * Date: Sat, 08-Jul-2023
 * Time: 19:00
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class CognomeCategoriaBackend extends WikiBackend {


    public CognomeCategoriaBackend() {
        super(CognomeCategoria.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.downloadCognomi;
        super.lastDownload = WPref.downloadCognomi;
        super.durataDownload = WPref.downloadCognomiTime;

        this.unitaMisuraDownload = AETypeTime.secondi;
        super.uploadTestName = UPLOAD_TITLE_DEBUG + COGNOMI_LINGUA;
    }

    public AEntity creaIfNotExist(final String wikiTitle, String lingua) {
        AEntity entityBean;
        String keyPropertyValue = wikiTitle;

        if (textService.isEmpty(wikiTitle)) {
            return null;
        }
        if (wikiTitle.contains(PARENTESI_TONDA_INI)) {
            keyPropertyValue = textService.levaCodaDaPrimo(wikiTitle, PARENTESI_TONDA_INI);
        }

        if (isExistByKey(keyPropertyValue)) {
            return null;
        }

        entityBean = newEntity(keyPropertyValue, wikiTitle, lingua);
        save(entityBean);

        return entityBean;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public CognomeCategoria newEntity(final String keyPropertyValue) {
        return newEntity(keyPropertyValue, VUOTA, VUOTA);
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
    public CognomeCategoria newEntity(final String nome, final String linkPagina, final String lingua) {
        CognomeCategoria newEntityBean = CognomeCategoria.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .linkPagina(textService.isValid(linkPagina) ? linkPagina : null)
                .lingua(textService.isValid(lingua) ? lingua : null)
                .build();

        return (CognomeCategoria) super.fixKey(newEntityBean);
    }


    @Override
    public CognomeCategoria findById(final String keyID) {
        return (CognomeCategoria) super.findById(keyID);
    }

    @Override
    public CognomeCategoria findByKey(final String keyValue) {
        return (CognomeCategoria) super.findByKey(keyValue);
    }

    @Override
    public CognomeCategoria findByProperty(final String propertyName, final Object propertyValue) {
        return (CognomeCategoria) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public CognomeCategoria save(AEntity entity) {
        return (CognomeCategoria) super.save(entity);
    }

    @Override
    public CognomeCategoria insert(AEntity entity) {
        return (CognomeCategoria) super.insert(entity);
    }

    @Override
    public CognomeCategoria update(AEntity entity) {
        return (CognomeCategoria) super.update(entity);
    }

    @Override
    public List<CognomeCategoria> findAll() {
        return super.findAll();
    }

    @Override
    public List<CognomeCategoria> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<CognomeCategoria> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<CognomeCategoria> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<CognomeCategoria> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();

        //--Cancella la (eventuale) precedente lista di nomi template
        deleteAll();

        result = downloadCategoriaPrincipale(result);

        result = super.fixResult(result);
        return super.fixResetDownload(result);
    }

    /**
     * Legge i valori dalla Categoria:Cognomi per lingua
     *
     * @return entities create
     */
    public AResult downloadCategoriaPrincipale(AResult result) {
        List<String> listaSubCategorie = this.getLingue();

        if (listaSubCategorie != null) {
            for (String catTitle : listaSubCategorie) {
                result = downloadSubCategoria(result, catTitle);
            }
        }
        return result;
    }

    /**
     * Legge i valori dalla Categoria:Cognomi per lingua
     *
     * @return entities create
     */
    public AResult downloadSubCategoria(AResult result, String catTitle) {
        AEntity entityBean;
        String tag = "Cognomi";
        List<String> listaWikiTitle = appContext.getBean(QueryCat.class, catTitle).filtro().getTitles();

        if (listaWikiTitle != null) {
            for (String wikiTitle : listaWikiTitle) {
                if (!wikiTitle.startsWith(tag)) {
                    entityBean = creaIfNotExist(wikiTitle, catTitle);
                    result.setValido(fixLista(result, entityBean, wikiTitle));
                }
            }
        }
        return result;
    }

    public List<String> getLingue() {
        return queryService.getSubCat(COGNOMI_LINGUA);
    }

}// end of crud backend class
