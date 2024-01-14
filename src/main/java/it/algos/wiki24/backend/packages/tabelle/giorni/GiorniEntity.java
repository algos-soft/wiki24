package it.algos.wiki24.backend.packages.tabelle.giorni;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.crono.mese.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "giorni", keyPropertyName = "nome", typeList = TypeList.hardWiki)
public class GiorniEntity extends AbstractEntity {

    @AField(type = TypeField.integer, headerText = "#", widthRem = 3, caption = "Ordinamento")
    public int ordine;

    @AField(type = TypeField.text, widthRem = 9, caption = "Giorno")
    public String nome;

    @AField(type = TypeField.linkDBRef, widthRem = 7)
    public MeseEntity mese;

    @AField(type = TypeField.integer, headerText = "nati", caption = "Numero di biografie che utilizzano i nati in questo giorno", widthRem = 6)
    public int bioNati;

    @AField(type = TypeField.linkWiki, headerText = "pagina", widthRem = 13, caption = "Pagina su wiki con la lista di biografie per giorno di nascita")
    public String pageNati;

    @AField(type = TypeField.integer, headerText = "morti", caption = "Numero di biografie che utilizzano i morti in questo giorno", widthRem = 6)
    public int bioMorti;

    @AField(type = TypeField.linkWiki, headerText = "pagina", widthRem = 13, caption = "Pagina su wiki con la lista di biografie per giorno di morte")
    public String pageMorti;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
