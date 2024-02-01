package it.algos.base24.backend.packages.anagrafica.address;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.anagrafica.via.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "address", typeList = TypeList.standard, usaStartupReset = true)
public class AddressEntity extends AbstractEntity {

    @AField(type = TypeField.linkDBRef, widthRem = 8, linkClazz = ViaEntity.class)
    public ViaEntity typeVia;

    @AField(type = TypeField.text, widthRem = 14)
    public String indirizzo;

    @AField(type = TypeField.text, widthRem = 18)
    public String localita;

    @AField(type = TypeField.text, widthRem = 5)
    public String cap;

    @Override
    public String toString() {
        return indirizzo;
    }

}// end of Entity class
