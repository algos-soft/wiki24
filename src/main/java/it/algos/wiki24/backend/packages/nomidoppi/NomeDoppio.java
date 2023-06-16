package it.algos.wiki24.backend.packages.nomidoppi;

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
 * Date: Mon, 12-Jun-2023
 * Time: 18:21
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
@AIEntity(collectionName = "nomedoppio", keyPropertyName = "nome", usaReset = true, usaKeyIdMinuscolaCaseInsensitive = false)
public class NomeDoppio extends AEntity {


    @AIField(type = AETypeField.text, widthEM = 20)
    public String nome;


    @Override
    public String toString() {
        return nome;
    }

}// end of crud entity class