package it.algos.wiki24.backend.packages.tabelle.attsingolare;

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
@AEntity(collectionName = "attsingolare", keyPropertyName = "singolare", typeList = TypeList.hardWiki)
public class AttSingolareEntity extends AbstractEntity {

    @AField(type = TypeField.text, headerText = "singolare", widthRem = 20)
    public String singolare;

    @AField(type = TypeField.text, widthRem = 20)
    public String plurale;

    @AField(type = TypeField.booleano)
    public boolean ex;

    @AField(type = TypeField.linkWiki)
    public String pagina;

    @AField(type = TypeField.integer,  caption = "Numero di biografie che utilizzano questa singola attività", widthRem = 7)
    public int numBio;

    @Override
    public String toString() {
        return singolare;
    }

}// end of Entity class
