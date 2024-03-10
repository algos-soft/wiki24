package it.algos.wiki24.backend.packages.nomi.nome;

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
@AEntity(collectionName = "nome", keyPropertyName = "sigla", typeList = TypeList.standard)
public class NomeEntity extends AbstractEntity {

    @AField(type = TypeField.text, widthRem = 6)
    public String sigla;

    @AField(type = TypeField.text, widthRem = 16)
    public String descrizione;

    @Override
    public String toString() {
        return sigla;
    }

}// end of Entity class
