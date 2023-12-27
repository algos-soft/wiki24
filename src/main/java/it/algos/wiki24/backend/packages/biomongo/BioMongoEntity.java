package it.algos.wiki24.backend.packages.biomongo;

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
@AEntity(collectionName = "biomongo", keyPropertyName = "pageId", typeList = TypeList.vuoto)
public class BioMongoEntity extends AbstractEntity {

    @AField(type = TypeField.lungo, widthRem = 7)
    public long pageId;

    @AField(type = TypeField.text, widthRem = 16)
    public String wikiTitle;

    @AField(type = TypeField.text)
    public String nome;

    @AField(type = TypeField.text)
    public String cognome;

    //    @AField(type = TypeField.text)
    //    public String ordinamento;

    @AField(type = TypeField.text, headerText = "X", widthRem = 4)
    public String sesso;

    @AField(type = TypeField.text, headerText = "giorno", widthRem = 8)
    public String giornoNato;

    @AField(type = TypeField.integer)
    public int giornoNatoOrd;

    @AField(type = TypeField.text, headerText = "anno", widthRem = 6)
    public String annoNato;

    @AField(type = TypeField.integer)
    public int annoNatoOrd;

    @AField(type = TypeField.text)
    public String luogoNato;

    @AField(type = TypeField.text)
    public String luogoNatoLink;

    @AField(type = TypeField.text, headerText = "giorno", widthRem = 8)
    public String giornoMorto;

    @AField(type = TypeField.integer)
    public int giornoMortoOrd;

    @AField(type = TypeField.text, headerText = "anno", widthRem = 7)
    public String annoMorto;

    @AField(type = TypeField.integer)
    public int annoMortoOrd;

    @AField(type = TypeField.text)
    public String luogoMorto;

    @AField(type = TypeField.text)
    public String luogoMortoLink;

    @AField(type = TypeField.text, widthRem = 12)
    public String attivita;

    @AField(type = TypeField.text, widthRem = 8)
    public String attivita2;

    @AField(type = TypeField.text, widthRem = 8)
    public String attivita3;

    @AField(type = TypeField.text, widthRem = 12)
    public String nazionalita;

    @Override
    public String toString() {
        return wikiTitle;
    }

}// end of Entity class
