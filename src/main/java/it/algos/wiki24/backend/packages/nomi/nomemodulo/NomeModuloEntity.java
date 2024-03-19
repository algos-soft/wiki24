package it.algos.wiki24.backend.packages.nomi.nomemodulo;

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
@AEntity(collectionName = "nomemodulo", keyPropertyName = "nome", typeList = TypeList.hardWiki)
public class NomeModuloEntity extends AbstractEntity {

    @AField(type = TypeField.text)
    public String nome;

    @AField(type = TypeField.wikiAnchor)
    public String pagina;

    @AField(type = TypeField.booleano, typeBool = TypeBool.yesNo, widthRem = 8)
    public boolean aggiunto;

    @AField(type = TypeField.booleano, typeBool = TypeBool.yesNo, widthRem = 8)
    public boolean uguale;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
