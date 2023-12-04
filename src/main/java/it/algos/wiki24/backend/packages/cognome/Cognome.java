package it.algos.wiki24.backend.packages.cognome;

import com.vaadin.flow.component.icon.*;
import it.algos.vaad24.backend.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import javax.persistence.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Wed, 10-Aug-2022
 * Time: 08:43
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
@AIEntity(collectionName = "cognome", keyPropertyName = "cognome", usaReset = false, usaKeyIdMinuscolaCaseInsensitive = false)
public class Cognome extends AEntity {


    @AIField(type = AETypeField.text, header = "cognome", caption = "cognome", widthEM = 14)
    public String cognome;

    @AIField(type = AETypeField.integer, header = "bio", caption = "Numero di biografie che utilizzano lo stesso cognome", widthEM = 6)
    public int numBio;

    @AIField(type = AETypeField.anchor)
    public String paginaVoce;

    @AIField(type = AETypeField.text, widthEM = 18)
    public String paginaLista;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNo, widthEM = 7)
    public boolean categoria;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNo, widthEM = 7)
    public boolean modulo;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNo, widthEM = 7)
    public boolean mongo;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.checkIcon, headerIcon = VaadinIcon.TRENDING_UP)
    public boolean superaSoglia;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.checkBox, headerIcon = VaadinIcon.LIST)
    public boolean esisteLista;


    @Override
    public String toString() {
        return cognome;
    }

}// end of crud entity class