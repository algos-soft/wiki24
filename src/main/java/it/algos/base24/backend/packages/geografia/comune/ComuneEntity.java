package it.algos.base24.backend.packages.geografia.comune;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.geografia.provincia.*;
import it.algos.base24.backend.packages.geografia.regione.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "comune", keyPropertyName = "nome", typeList = TypeList.hardWiki, usaStartupReset = true)
public class ComuneEntity extends AbstractEntity {

    @AField(type = TypeField.text, widthRem = 14)
    public String nome;

    @AField(type = TypeField.linkDBRef, widthRem = 10, linkClazz = ProvinciaEntity.class)
    public ProvinciaEntity provincia;

    @AField(type = TypeField.text, widthRem = 5)
    public String cap;

    @AField(type = TypeField.linkDBRef, widthRem = 10, linkClazz = RegioneEntity.class)
    public RegioneEntity regione;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
