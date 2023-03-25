package it.algos.wiki24.backend.packages.attplurale;

import it.algos.vaad24.backend.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import lombok.*;

import javax.persistence.*;

import org.springframework.data.mongodb.core.mapping.*;
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
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@Component
@Document
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass()
@AIEntity(collectionName = "code", keyPropertyName = "code")
public class AttPlurale extends AEntity {

    @AIField(type = AETypeField.integer, header = "#", widthEM = 3, caption = "Ordinamento")
    public int ordine;

    @AIField(type = AETypeField.text, widthEM = 20)
    public String code;

    public String descrizione;

    @Override
    public String toString() {
        return code;
    }

}// end of crud entity class