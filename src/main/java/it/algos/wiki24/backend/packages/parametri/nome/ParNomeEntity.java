package it.algos.wiki24.backend.packages.parametri.nome;

import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.wiki24.backend.packages.parametri.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "parnome", keyPropertyName = "pageId")
public class ParNomeEntity extends ParEntity {

    @AField(type = TypeField.lungo, widthRem = 7)
    public long pageId;

    @AField(type = TypeField.text, widthRem = 16)
    public String wikiTitle;

    @AField(type = TypeField.text, widthRem = 16)
    public String grezzo;

    @AField(type = TypeField.text, widthRem = 16)
    public String valido;

    @AField(type = TypeField.booleano,headerText = "grezzo")
    public boolean grezzoPieno;

    @AField(type = TypeField.booleano,headerText = "valido")
    public boolean validoPieno;

    @AField(type = TypeField.booleano)
    public boolean uguale;

    @Override
    public String toString() {
        return grezzo;
    }

}// end of Entity class
