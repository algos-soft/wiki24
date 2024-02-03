package it.algos.base24.backend.packages.geografia.provincia;

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
@AEntity(collectionName = "provincia", keyPropertyName = "sigla", typeList = TypeList.hardWiki)
public class ProvinciaEntity extends AbstractEntity {

    @AField(type = TypeField.text, widthRem = 6)
    public String sigla;

    @AField(type = TypeField.text, widthRem = 16)
    public String descrizione;

    @Override
    public String toString() {
        return sigla;
    }

}// end of Entity class
