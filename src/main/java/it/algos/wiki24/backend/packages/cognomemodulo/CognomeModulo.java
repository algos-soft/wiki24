package it.algos.wiki24.backend.packages.cognomemodulo;

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
 * Date: Wed, 21-Jun-2023
 * Time: 18:10
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
@AIEntity(collectionName = "cognomemodulo", keyPropertyName = "cognome", usaReset = true, usaKeyIdMinuscolaCaseInsensitive = false)
public class CognomeModulo extends AEntity {

    @AIField(type = AETypeField.text, widthEM = 20)
    public String cognome;

    @AIField(type = AETypeField.text, widthEM = 14)
    public String linkPagina;

    @Override
    public String toString() {
        return cognome;
    }

}// end of crud entity class