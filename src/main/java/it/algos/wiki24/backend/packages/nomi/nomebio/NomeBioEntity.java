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

    @AField(type = TypeField.text, widthRem = 12)
    public String nome;

    @AField(type = TypeField.integer, widthRem = 7)
    public int numBio;

    @AField(type = TypeField.wikiAnchor)
    public String pagina;

    @AField(type = TypeField.wikiAnchor, anchorPrefix = "Persone di nome ")
    public String lista;

    @AField(type = TypeField.booleano, headerText = "XX", widthRem = 4)
    public boolean doppio;

    @AField(type = TypeField.booleano, headerText = "bio", widthRem = 4)
    public boolean mongo;

    @AField(type = TypeField.booleano, headerText = ">50", widthRem = 4)
    public boolean superaSoglia;

    @AField(type = TypeField.booleano, headerText = "lista", widthRem = 4)
    public boolean esisteLista;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
