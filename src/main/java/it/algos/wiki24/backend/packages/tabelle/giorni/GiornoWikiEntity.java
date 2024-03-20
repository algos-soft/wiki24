package it.algos.wiki24.backend.packages.tabelle.giorni;

import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.packages.crono.mese.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "giorni", keyPropertyName = "nome", typeList = TypeList.hardWiki)
public class GiornoWikiEntity extends AbstractEntity {

    @AField(type = TypeField.integer, headerText = "#", widthRem = 3, caption = "Ordinamento")
    public int ordine;

    @AField(type = TypeField.wikiAnchor, headerText = "Pagina")
    public String nome;

    @AField(type = TypeField.linkDBRef, widthRem = 7)
    public MeseEntity mese;

    @AField(type = TypeField.wikiAnchor, headerText = "lista nati", caption = "Pagina su wiki con la lista di biografie per giorno di nascita")
    public String pageNati;

    @AField(type = TypeField.numberFormat, headerText = "numBio", caption = "Numero di biografie che utilizzano i nati in questo giorno")
    public int bioNati;


    @AField(type = TypeField.wikiAnchor, headerText = "lista morti", caption = "Pagina su wiki con la lista di biografie per giorno di morte")
    public String pageMorti;

    @AField(type = TypeField.numberFormat, headerText = "numBio", caption = "Numero di biografie che utilizzano i morti in questo giorno")
    public int bioMorti;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
