package it.algos.wiki24.backend.packages.parcognome;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.par.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "parcognome", keyPropertyName = "pageId", typeList = TypeList.vuoto)
public class ParCognomeEntity extends AbstractEntity {

    @AField(type = TypeField.lungo, widthRem = 7)
    public long pageId;

    @AField(type = TypeField.text, widthRem = 16)
    public String wikiTitle;

    @AField(type = TypeField.text, widthRem = 16)
    public String grezzo;

    @AField(type = TypeField.text, widthRem = 10)
    public String elaborato;

    @AField(type = TypeField.booleano,headerText = "GV")
    public boolean grezzoVuoto;

    @AField(type = TypeField.booleano,headerText = "EV")
    public boolean elaboratoVuoto;

    @AField(type = TypeField.booleano,headerText = "=")
    public boolean uguale;

    @Override
    public String toString() {
        return grezzo;
    }

}// end of Entity class
