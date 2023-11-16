package it.algos.wiki24.backend.packages.nazsingolare;

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
@AEntity(collectionName = "nazsingolare", keyPropertyName = "nome",usaStartupReset = true, typeReset = TypeReset.resetAdd)
public class NazSingolareEntity extends AbstractEntity {

    @AField(type = TypeField.text, headerText = "singolare", caption = "singolare", widthRem = 20)
    public String nome;

    @AField(type = TypeField.text, widthRem = 20)
    public String plurale;

    @AField(type = TypeField.integer, headerText = "bio", caption = "Numero di biografie che utilizzano questa singola nazionalità", widthRem = 6)
    public int numBio;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
