package it.algos.wiki24.backend.packages.attgenere;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.entity.*;
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
 * Date: Wed, 28-Jun-2023
 * Time: 10:18
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class AttGenereBackend extends CrudBackend {


    public AttGenereBackend() {
        super(AttGenere.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public AttGenere newEntity() {
        return newEntity(0, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public AttGenere newEntity(final String keyPropertyValue) {
        return newEntity(0, keyPropertyValue, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine      di presentazione nel popup/combobox (obbligatorio, unico)
     * @param code        (obbligatorio, unico)
     * @param descrizione (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public AttGenere newEntity(final int ordine, final String code, final String descrizione) {
        AttGenere newEntityBean = AttGenere.builder()
                .build();

        return (AttGenere) super.fixKey(newEntityBean);
    }


    @Override
    public AttGenere findById(final String keyID) {
        return (AttGenere) super.findById(keyID);
    }

    @Override
    public AttGenere findByKey(final String keyValue) {
        return (AttGenere) super.findByKey(keyValue);
    }

    @Override
    public AttGenere findByProperty(final String propertyName, final Object propertyValue) {
        return (AttGenere) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public AttGenere save(AEntity entity) {
        return (AttGenere) super.save(entity);
    }

    @Override
    public AttGenere insert(AEntity entity) {
        return (AttGenere) super.insert(entity);
    }

    @Override
    public AttGenere update(AEntity entity) {
        return (AttGenere) super.update(entity);
    }

    @Override
    public List<AttGenere> findAll() {
        return super.findAll();
    }

    @Override
    public List<AttGenere> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<AttGenere> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<AttGenere> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<AttGenere> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

}// end of crud backend class
