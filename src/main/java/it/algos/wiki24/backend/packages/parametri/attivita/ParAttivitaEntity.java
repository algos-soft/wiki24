package it.algos.wiki24.backend.packages.parametri.attivita;

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
@AEntity(collectionName = "parattivita", keyPropertyName = "pageId", typeList = TypeList.vuoto)
public class ParAttivitaEntity extends AbstractEntity {

    @AField(type = TypeField.lungo, widthRem = 7)
    public long pageId;

    @AField(type = TypeField.text, widthRem = 16)
    public String wikiTitle;

    @AField(type = TypeField.text, widthRem = 16)
    public String grezzo;

    @AField(type = TypeField.text, widthRem = 10)
    public String elaborato;

    @AField(type = TypeField.text, widthRem = 10)
    public String grezzo2;

    @AField(type = TypeField.text, widthRem = 10)
    public String elaborato2;

    @AField(type = TypeField.text, widthRem = 10)
    public String grezzo3;

    @AField(type = TypeField.text, widthRem = 10)
    public String elaborato3;

    @AField(type = TypeField.booleano, headerText = "GV")
    public boolean grezzoVuoto;

    @AField(type = TypeField.booleano, headerText = "EV")
    public boolean elaboratoVuoto;

    @AField(type = TypeField.booleano, headerText = "=")
    public boolean uguale;

    @Override
    public String toString() {
        return grezzo;
    }

}// end of Entity class
