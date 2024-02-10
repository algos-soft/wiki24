package it.algos.wiki24.backend.packages.tabelle.nazsingolare;

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
@AEntity(collectionName = "nazsingolare", keyPropertyName = "singolare", typeList = TypeList.hardWiki)
public class NazSingolareEntity extends AbstractEntity {

    @AField(type = TypeField.text, headerText = "singolare", widthRem = 20)
    public String singolare;

    @AField(type = TypeField.text, widthRem = 20)
    public String plurale;

    @AField(type = TypeField.linkWiki, widthRem = 18)
    public String pagina;

    @AField(type = TypeField.integer, caption = "Numero di biografie che utilizzano questa singola nazionalità", widthRem = 7)
    public int numBio;

    @Override
    public String toString() {
        return singolare;
    }

}// end of Entity class
