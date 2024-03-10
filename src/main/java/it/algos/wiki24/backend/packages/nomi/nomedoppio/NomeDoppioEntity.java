package it.algos.wiki24.backend.packages.nomi.nomedoppio;

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
@AEntity(collectionName = "nomedoppio", keyPropertyName = "nome", typeList = TypeList.hardWiki)
public class NomeDoppioEntity extends AbstractEntity {

    @AField(type = TypeField.text)
    public String nome;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
