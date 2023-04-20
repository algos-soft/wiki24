package it.algos.wiki24.backend.packages.nazsingolare;

import it.algos.vaad24.backend.annotation.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import lombok.*;

import javax.persistence.*;

import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

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
@AIEntity(collectionName = "nazsingolare", keyPropertyName = "nome", usaReset = true, usaKeyIdMinuscolaCaseInsensitive = false)
public class NazSingolare extends AEntity {

    @AIField(type = AETypeField.text, header = "singolare", caption = "singolare", widthEM = 20)
    public String nome;

    @AIField(type = AETypeField.text, widthEM = 20)
    public String plurale;

    @AIField(type = AETypeField.integer, header = "bio", caption = "Numero di biografie che utilizzano questa singola nazionalità", widthEM = 6)
    public int numBio;

    @Override
    public String toString() {
        return nome;
    }

}// end of crud entity class