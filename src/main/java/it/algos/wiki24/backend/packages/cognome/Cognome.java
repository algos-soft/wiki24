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
import javax.validation.constraints.*;

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
@Builder(builderMethodName = "builderCognome")
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass()
@AIEntity(keyPropertyName = "cognome", usaReset = false)
public class Cognome extends AEntity {

    private static final transient int WIDTHEM = 20;

    @AIField(type = AETypeField.text)
    public String cognome;

    @AIField(type = AETypeField.text, widthEM = 12)
    public String paginaLista;

    @AIField(type = AETypeField.integer, header = "bio", caption = "Numero di biografie che utilizzano questo cognome", widthEM = 6)
    public int numBio;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNoReverse, headerIcon = VaadinIcon.HAMMER)
    public boolean esisteLista;

    @Override
    public String toString() {
        return cognome;
    }

}// end of crud entity class