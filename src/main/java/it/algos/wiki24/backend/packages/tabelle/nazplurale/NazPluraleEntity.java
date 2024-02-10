package it.algos.wiki24.backend.packages.tabelle.nazplurale;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "nazplurale", keyPropertyName = "plurale", typeList = TypeList.hardWiki)
public class NazPluraleEntity extends AbstractEntity {

    @AField(type = TypeField.text, widthRem = 16)
    public String plurale;

    @AField(type = TypeField.linkStatico, headerText = "singolari", widthRem = 30)
    public List<String> txtSingolari;

    @AField(type = TypeField.linkWiki, headerText = "lista", anchorPrefix = "Progetto:Biografie/Nazionalità/")
    public String lista;

    @AField(type = TypeField.linkWiki)
    public String pagina;

    @AField(type = TypeField.integer, widthRem = 7)
    public int numBio;

    @AField(type = TypeField.integer, headerText = "numSin", widthRem = 7)
    public int numSingolari;

    @AField(type = TypeField.booleano, headerText = "s")
    public boolean superaSoglia;

    @AField(type = TypeField.booleano, headerText = "l")
    public boolean esisteLista;

    @Override
    public String toString() {
        return plurale;
    }

}// end of Entity class
