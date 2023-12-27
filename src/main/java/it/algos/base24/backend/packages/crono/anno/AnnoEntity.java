package it.algos.base24.backend.packages.crono.anno;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.crono.secolo.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "anno", typeList = TypeList.hardCode)
public class AnnoEntity extends AbstractEntity {

    @AField(type = TypeField.integer, headerText = "#", widthRem = 6, caption = "Ordine a partire dal 1.000 a.C.")
    public int ordine;

    @AField(type = TypeField.text, widthRem = 7, caption = "Nome corrente")
    public String nome;

    //    @DBRef
    @AField(type = TypeField.linkDBRef, widthRem = 10)
    public SecoloEntity secolo;

    @AField(type = TypeField.booleano, headerText = "d.C.")
    public boolean dopoCristo;

    @AField(type = TypeField.booleano, headerText = "BS")
    public boolean bisestile;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
