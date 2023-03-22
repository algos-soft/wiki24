package it.algos.wiki24.backend.packages.nazsingola;

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
 * Date: Sat, 18-Mar-2023
 * Time: 15:17
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
@AIEntity(keyPropertyName = "nome", usaReset = true)
public class NazSingola extends AEntity {

    @AIField(type = AETypeField.text, widthEM = 20, search = true)
    public String nome;

    @AIField(type = AETypeField.integer, header = "bio", caption = "Numero di biografie che utilizzano questa nazionalità", widthEM = 6)
    public int numBio;

    @Override
    public String toString() {
        return nome;
    }

}// end of crud entity class