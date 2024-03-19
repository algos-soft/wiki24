package it.algos.wiki24.backend.packages.cognomi.cognomecategoria;

import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "cognomecategoria", keyPropertyName = "cognome", typeList = TypeList.hardWiki)
public class CognomeCategoriaEntity extends AbstractEntity {

    @AField(type = TypeField.text)
    public String cognome;

    @Override
    public String toString() {
        return cognome;
    }

}// end of Entity class
