package it.algos.wiki24.backend.packages.cognomeincipit;

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
@AIEntity(collectionName = "cognomeincipit", keyPropertyName = "cognome", usaReset = false, usaKeyIdMinuscolaCaseInsensitive = false)
public class CognomeIncipit extends AEntity {

    @AIField(type = AETypeField.text, widthEM = 20)
    public String cognome;

    @AIField(type = AETypeField.anchor)
    public String linkPagina;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNo,  widthEM = 7)
    public boolean aggiunto;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNo,  widthEM = 7)
    public boolean uguale;

    @Override
    public String toString() {
        return cognome;
    }

}// end of crud entity class