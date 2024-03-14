package it.algos.wiki24.backend.packages.nomi.nomebio;

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
@AEntity(collectionName = "nomebio", keyPropertyName = "nome", typeList = TypeList.hardWiki)
public class NomeBioEntity extends AbstractEntity {

    @AField(type = TypeField.text)
    public String nome;

    @AField(type = TypeField.integer, headerText = "bio")
    public int numBio;

    @AField(type = TypeField.wikiAnchor)
    public String pagina;

    @AField(type = TypeField.wikiAnchor)
    public String lista;


    @AField(type = TypeField.booleano, headerText = "dop", widthRem = 4)
    public boolean doppio;

    @AField(type = TypeField.booleano, headerText = "cat", widthRem = 4)
    public boolean categoria;

    @AField(type = TypeField.booleano, headerText = "inc", widthRem = 4)
    public boolean incipit;

    @AField(type = TypeField.booleano, headerText = "mon", widthRem = 4)
    public boolean mongo;

    @AField(type = TypeField.booleano, headerText = "bio", widthRem = 4)
    public boolean superaSoglia;

    @AField(type = TypeField.booleano, headerText = "lista", widthRem = 4)
    public boolean esisteLista;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
