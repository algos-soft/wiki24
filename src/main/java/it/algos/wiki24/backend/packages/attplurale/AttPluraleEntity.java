package it.algos.wiki24.backend.packages.attplurale;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "attplurale", keyPropertyName = "plurale", typeList = TypeList.hardWiki)
public class AttPluraleEntity extends AbstractEntity {

    @AField(type = TypeField.text, widthRem = 20)
    public String plurale;

    @AField(type = TypeField.linkStatico, headerText = "singolari", widthRem = 30)
    public List<String> listaSingolari;

    @AField(type = TypeField.linkWiki, headerText = "lista", anchorPrefix = "Progetto:Biografie/Attività/")
    public String paginaLista;


    @AField(type = TypeField.integer, headerText = "bio", widthRem = 20)
    public int numBio;

    @AField(type = TypeField.integer, headerText = "numSin")
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
