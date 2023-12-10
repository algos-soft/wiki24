package it.algos.wiki24.backend.packages.attplurale;

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
@AEntity(collectionName = "attplurale", keyPropertyName = "plurale", typeList = TypeList.hardWiki)
public class AttPluraleEntity extends AbstractEntity {

    @AField(type = TypeField.text, headerText = "plurale", widthRem = 20)
    public String plurale;

    @Override
    public String toString() {
        return plurale;
    }

}// end of Entity class
