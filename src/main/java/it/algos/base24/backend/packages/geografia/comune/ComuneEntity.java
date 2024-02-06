package it.algos.base24.backend.packages.geografia.comune;

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
@AEntity(collectionName = "comune", keyPropertyName = "nome", typeList = TypeList.hardWiki)
public class ComuneEntity extends AbstractEntity {

    @AField(type = TypeField.text, widthRem = 14)
    public String nome;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
