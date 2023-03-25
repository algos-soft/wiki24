package it.algos.wiki24.backend.packages.attplurale;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.logic.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 25-Mar-2023
 * Time: 16:50
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class AttPluraleBackend extends CrudBackend {


    public AttPluraleBackend() {
        super(AttPlurale.class);
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
    public AttPlurale newEntity() {
        return newEntity(0, VUOTA, VUOTA);
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
    public AttPlurale newEntity(final int ordine, final String code, final String descrizione) {
        AttPlurale newEntityBean = AttPlurale.builder()
                .ordine(ordine)
                .code(textService.isValid(code) ? code : null)
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .build();

        return (AttPlurale) super.fixKey(newEntityBean);
    }

}// end of crud backend class
