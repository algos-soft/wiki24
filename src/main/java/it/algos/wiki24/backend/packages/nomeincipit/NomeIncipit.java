package it.algos.wiki24.backend.packages.nomeincipit;

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
 * Date: Sun, 18-Jun-2023
 * Time: 12:06
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
@AIEntity(collectionName = "nomeincipit", keyPropertyName = "nome", usaReset = true, usaKeyIdMinuscolaCaseInsensitive = false)
public class NomeIncipit extends AEntity {

    @AIField(type = AETypeField.text, widthEM = 20)
    public String nome;

    @AIField(type = AETypeField.anchor)
    public String linkPagina;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNo,  widthEM = 7)
    public boolean aggiunto;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNo,  widthEM = 7)
    public boolean uguale;

    @Override
    public String toString() {
        return nome;
    }

}// end of crud entity class