package it.algos.wiki24.backend.packages.attsingolare;

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
@AEntity(collectionName = "attsingolare", keyPropertyName = "nome",usaStartupReset = true, typeReset = TypeReset.resetDelete)
public class AttSingolareEntity extends AbstractEntity {

    @AField(type = TypeField.text, headerText = "singolare", caption = "singolare", widthRem = 20)
    public String nome;

    @AField(type = TypeField.text, widthRem = 20)
    public String plurale;

    @AField(type = TypeField.integer, headerText = "bio", caption = "Numero di biografie che utilizzano questa singola attività", widthRem = 6)
    public int numBio;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
