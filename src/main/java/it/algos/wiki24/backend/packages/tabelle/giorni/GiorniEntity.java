package it.algos.wiki24.backend.packages.tabelle.giorni;

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
@AEntity(collectionName = "giorno", typeList = TypeList.standard)
public class GiorniEntity extends AbstractEntity {

    @AField(type = TypeField.text)
    public String code;

    @Override
    public String toString() {
        return code;
    }

}// end of Entity class
