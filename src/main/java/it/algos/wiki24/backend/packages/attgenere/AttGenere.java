package it.algos.wiki24.backend.packages.attgenere;

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
 * Date: Wed, 28-Jun-2023
 * Time: 10:18
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
@AIEntity(collectionName = "attgenere", keyPropertyName = "singolare", usaReset = false, usaKeyIdMinuscolaCaseInsensitive = false)
public class AttGenere extends AEntity {

    @AIField(type = AETypeField.text, header = "singolare", caption = "singolare", widthEM = 20)
    public String singolare;

    @AIField(type = AETypeField.text, widthEM = 20)
    public String paragrafoMaschile;

    @AIField(type = AETypeField.text, widthEM = 20)
    public String paragrafoFemminile;


    @Override
    public String toString() {
        return singolare;
    }

}// end of crud entity class