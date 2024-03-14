package it.algos.wiki24.backend.packages.nomi.nomecategoria;

import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.wiki24.backend.enumeration.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "nomecategoria", keyPropertyName = "nome", typeList = TypeList.hardWiki)
public class NomeCategoriaEntity extends AbstractEntity {

    @AField(type = TypeField.text)
    public String nome;

    @AField(type = TypeField.enumType, enumClazz = TypeGenere.class)
    public TypeGenere type;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
