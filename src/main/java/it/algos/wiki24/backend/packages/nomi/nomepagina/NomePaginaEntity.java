package it.algos.wiki24.backend.packages.nomi.nomepagina;

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
@AEntity(collectionName = "nomepagina", keyPropertyName = "nome", typeList = TypeList.hardWiki)
public class NomePaginaEntity extends AbstractEntity {

    @AField(type = TypeField.text)
    public String nome;

    @AField(type = TypeField.wikiAnchor)
    public String pagina;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
