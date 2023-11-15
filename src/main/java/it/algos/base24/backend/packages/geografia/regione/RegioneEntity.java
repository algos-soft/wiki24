package it.algos.base24.backend.packages.geografia.regione;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.geografia.stato.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "regione", keyPropertyName = "sigla", searchPropertyName = "nome", usaStartupReset = true, typeReset = TypeReset.soloShow, usaIdPrimaMinuscola = false)
public class RegioneEntity extends AbstractEntity {

    @AField(type = TypeField.ordine)
    public int ordine;

    @AField(type = TypeField.text)
    public String sigla;

    @AField(type = TypeField.text, widthRem = 14)
    public String nome;

    //    @DBRef //@todo perché non funziona?
    @AField(type = TypeField.linkDBRef, widthRem = 14)
    public StatoEntity stato;

    @AField(type = TypeField.text, widthRem = 20)
    public TypeRegione type;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
