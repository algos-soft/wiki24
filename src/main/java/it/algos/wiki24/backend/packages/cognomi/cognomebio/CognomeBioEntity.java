package it.algos.wiki24.backend.packages.cognomi.cognomebio;

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
@AEntity(collectionName = "cognomibio", keyPropertyName = "sigla", typeList = TypeList.standard)
public class CognomeBioEntity extends AbstractEntity {

    @AField(type = TypeField.text, widthRem = 6)
    public String sigla;

    @AField(type = TypeField.text, widthRem = 16)
    public String descrizione;

    @Override
    public String toString() {
        return sigla;
    }

}// end of Entity class
