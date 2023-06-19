package it.algos.wiki24.backend.packages.attsingolare;

import com.vaadin.flow.component.icon.*;
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
 * Time: 16:49
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
@AIEntity(collectionName = "attsingolare", keyPropertyName = "nome", usaReset = true, usaKeyIdMinuscolaCaseInsensitive = false)
public class AttSingolare extends AEntity {

    @AIField(type = AETypeField.text, header = "singolare", caption = "singolare", widthEM = 20)
    public String nome;

    @AIField(type = AETypeField.text, widthEM = 20)
    public String plurale;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.checkBox, caption = "aggiunta (ex-attività)", usaCheckBox3Vie = true)
    public boolean ex;

    @AIField(type = AETypeField.integer, header = "bio", caption = "Numero di biografie che utilizzano questa singola nazionalità", widthEM = 6)
    public int numBio;

    @Override
    public String toString() {
        return nome;
    }

}// end of crud entity class