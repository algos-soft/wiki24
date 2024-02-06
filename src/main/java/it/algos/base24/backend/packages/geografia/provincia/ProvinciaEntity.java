package it.algos.base24.backend.packages.geografia.provincia;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.geografia.regione.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "provincia", keyPropertyName = "sigla", typeList = TypeList.hardCsv)
public class ProvinciaEntity extends AbstractEntity {

    @AField(type = TypeField.text, widthRem = 6)
    public String sigla;

    @AField(type = TypeField.text, widthRem = 12)
    public String nomeBreve;

    @AField(type = TypeField.text, widthRem = 24)
    public String nomeCompleto;

    @AField(type = TypeField.linkDBRef, widthRem = 10, linkClazz = RegioneEntity.class)
    public RegioneEntity regione;

    @Override
    public String toString() {
        return sigla;
    }

}// end of Entity class
