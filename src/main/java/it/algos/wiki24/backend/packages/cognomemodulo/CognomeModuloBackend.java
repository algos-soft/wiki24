package it.algos.wiki24.backend.packages.cognomemodulo;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.entity.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
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
public class CognomeModuloBackend extends WikiBackend {


    public CognomeModuloBackend() {
        super(CognomeModulo.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.downloadCognomiModulo;
        super.lastDownload = WPref.downloadCognomiModulo;

        super.sorgenteDownload = TAG_INCIPIT_COGNOMI;
        super.tagIniSorgente = "switch:{{{nome}}}";
        super.tagEndSorgente = "|#default";
        super.tagSplitSorgente = PIPE_REGEX;
        super.uploadTest = UPLOAD_TITLE_DEBUG + INCIPIT_COGNOMI;
    }

    public AEntity creaIfNotExist(final String riga) {
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
    public CognomeModulo newEntity() {
        return newEntity(VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public CognomeModulo newEntity(final String keyPropertyValue) {
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
    public CognomeModulo newEntity(final String cognome, final String linkPagina) {
        CognomeModulo newEntityBean = CognomeModulo.builder()
                .cognome(textService.isValid(cognome) ? cognome : null)
                .linkPagina(textService.isValid(linkPagina) ? linkPagina : null)
                .build();

        return (CognomeModulo) super.fixKey(newEntityBean);
    }


    @Override
    public CognomeModulo findById(final String keyID) {
        return (CognomeModulo) super.findById(keyID);
    }

    @Override
    public CognomeModulo findByKey(final String keyValue) {
        return (CognomeModulo) super.findByKey(keyValue);
    }

    @Override
    public CognomeModulo findByProperty(final String propertyName, final Object propertyValue) {
        return (CognomeModulo) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public CognomeModulo save(AEntity entity) {
        return (CognomeModulo) super.save(entity);
    }

    @Override
    public CognomeModulo insert(AEntity entity) {
        return (CognomeModulo) super.insert(entity);
    }

    @Override
    public CognomeModulo update(AEntity entity) {
        return (CognomeModulo) super.update(entity);
    }

    @Override
    public List<CognomeModulo> findAll() {
        return super.findAll();
    }

    @Override
    public List<CognomeModulo> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<CognomeModulo> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<CognomeModulo> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<CognomeModulo> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

}// end of crud backend class
