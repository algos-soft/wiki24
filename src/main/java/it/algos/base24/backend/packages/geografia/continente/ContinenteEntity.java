package it.algos.base24.backend.packages.geografia.continente;

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
@AEntity(collectionName = "continente", typeList = TypeList.hardEnum)
public class ContinenteEntity extends AbstractEntity {

    @AField(type = TypeField.ordine, headerText = "#")
    public int ordine;

    @AField(type = TypeField.text, widthRem = 12)
    public String nome;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
