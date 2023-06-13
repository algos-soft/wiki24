package it.algos.wiki24.backend.packages.doppionome;

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
 * Date: Mon, 12-Jun-2023
 * Time: 18:21
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class DoppioNomeBackend extends CrudBackend {


    public DoppioNomeBackend() {
        super(DoppioNome.class);
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
    public DoppioNome newEntity() {
        return newEntity(0, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public DoppioNome newEntity(final String keyPropertyValue) {
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
    public DoppioNome newEntity(final int ordine, final String code, final String descrizione) {
        return null;
    }


    @Override
    public DoppioNome findById(final String keyID) {
        return (DoppioNome) super.findById(keyID);
    }

    @Override
    public DoppioNome findByKey(final String keyValue) {
        return (DoppioNome) super.findByKey(keyValue);
    }

    @Override
    public DoppioNome findByProperty(final String propertyName, final Object propertyValue) {
        return (DoppioNome) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public DoppioNome save(AEntity entity) {
        return (DoppioNome) super.save(entity);
    }

    @Override
    public DoppioNome insert(AEntity entity) {
        return (DoppioNome) super.insert(entity);
    }

    @Override
    public DoppioNome update(AEntity entity) {
        return (DoppioNome) super.update(entity);
    }

    @Override
    public List<DoppioNome> findAll() {
        return super.findAll();
    }

    @Override
    public List<DoppioNome> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<DoppioNome> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<DoppioNome> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<DoppioNome> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    /**
     * Fetches all code of Prenome <br>
     *
     * @return all selected property
     */
    public List<String> fetchCode() {
        List<String> lista = new ArrayList<>();
        List<DoppioNome> listaEntities =null;

        for (DoppioNome doppio : listaEntities) {
            lista.add(doppio.nome);
        }

        return lista;
    }

}// end of crud backend class
