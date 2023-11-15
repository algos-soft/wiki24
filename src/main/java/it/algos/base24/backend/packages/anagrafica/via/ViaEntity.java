package it.algos.base24.backend.packages.anagrafica.via;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "via", usaStartupReset = true, typeReset = TypeReset.resetAdd)
public class ViaEntity extends AbstractEntity {

    @AField(type = TypeField.ordine, headerText = "#")
    public int ordine;

    @AField(type = TypeField.text)
    public String nome;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
