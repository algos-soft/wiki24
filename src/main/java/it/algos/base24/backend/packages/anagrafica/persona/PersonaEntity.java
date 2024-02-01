package it.algos.base24.backend.packages.anagrafica.persona;

import it.algos.base24.backend.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.anagrafica.address.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "persona", typeList = TypeList.standard)
public class PersonaEntity extends AbstractEntity {

    @AField(type = TypeField.text, widthRem = 6)
    public String titolo;

    @AField(type = TypeField.text)
    public String nome;

    @AField(type = TypeField.text)
    public String cognome;

    @AField(type = TypeField.text, widthRem = 8)
    public String telefono;

    @AField(type = TypeField.text, widthRem = 16)
    public String mail;

    @AField(type = TypeField.linkDBRef)
    public AddressEntity address;

    @Override
    public String toString() {
        return nome + SPAZIO + cognome;
    }

}// end of Entity class
