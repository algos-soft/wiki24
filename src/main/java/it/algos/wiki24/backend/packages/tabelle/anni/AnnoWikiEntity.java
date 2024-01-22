package it.algos.wiki24.backend.packages.tabelle.anni;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.crono.secolo.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "anni", keyPropertyName = "nome", sortPropertyName = "ordine", sortDiscendente = true, typeList = TypeList.hardWiki)
public class AnnoWikiEntity extends AbstractEntity {

    @AField(type = TypeField.integer, headerText = "#", widthRem = 4, caption = "Ordinamento")
    public int ordine;

    @AField(type = TypeField.text, widthRem = 9, caption = "Anno")
    public String nome;

    @AField(type = TypeField.linkDBRef, widthRem = 7)
    public SecoloEntity secolo;

    @AField(type = TypeField.integer, headerText = "nati", caption = "Numero di biografie che utilizzano i nati in questo anno", widthRem
            = 6)
    public int bioNati;

    @AField(type = TypeField.linkWiki, headerText = "pagina", widthRem = 13, caption = "Pagina su wiki con la lista di biografie per anno" +
            " di nascita")
    public String pageNati;

    @AField(type = TypeField.integer, headerText = "morti", caption = "Numero di biografie che utilizzano i morti in questo anno", widthRem
            = 6)
    public int bioMorti;

    @AField(type = TypeField.linkWiki, headerText = "pagina", widthRem = 13, caption = "Pagina su wiki con la lista di biografie per anno" +
            " di morte")
    public String pageMorti;

    @AField(type = TypeField.booleano, typeBool = TypeBool.thumb, headerText = "n")
    public boolean natiOk;

    @AField(type = TypeField.booleano, typeBool = TypeBool.thumb, headerText = "m")
    public boolean mortiOk;

    @AField(type = TypeField.integer)
    public int ordineSecolo;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
