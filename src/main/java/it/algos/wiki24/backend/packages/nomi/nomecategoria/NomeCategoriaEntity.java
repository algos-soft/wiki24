package it.algos.wiki24.backend.packages.nomi.nomecategoria;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
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

    @AField(type = TypeField.linkWiki, headerText = "pagina", anchorPrefix = "Progetto:Biografie/Attività/")
    public String linkPagina;

    @AField(type = TypeField.enumType)
    public TypeGenere typeGenere;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
