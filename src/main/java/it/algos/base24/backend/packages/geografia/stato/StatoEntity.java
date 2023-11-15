package it.algos.base24.backend.packages.geografia.stato;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.geografia.continente.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "stato", keyPropertyName = "nome", usaStartupReset = true, typeReset = TypeReset.resetAdd)
public class StatoEntity extends AbstractEntity {

    @AField(type = TypeField.ordine)
    public int ordine;

    @AField(type = TypeField.text, widthRem = 14)
    public String nome;

    @AField(type = TypeField.text, widthRem = 14)
    public String capitale;

    @AField(type = TypeField.text)
    public String alfa3;

    @AField(type = TypeField.text)
    public String alfa2;

    @AField(type = TypeField.text)
    public String numerico;

//    @DBRef //@todo perché non funziona?
    @AField(type = TypeField.linkDBRef, widthRem = 14)
    public ContinenteEntity continente;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
