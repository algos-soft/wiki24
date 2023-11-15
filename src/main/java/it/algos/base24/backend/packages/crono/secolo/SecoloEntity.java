package it.algos.base24.backend.packages.crono.secolo;

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
@AEntity(collectionName = "secolo", usaStartupReset = true, typeReset = TypeReset.soloShow, usaIdPrimaMinuscola = false)
public class SecoloEntity extends AbstractEntity {

    @AField(type = TypeField.integer, headerText = "#", widthRem = 4, caption = "Ordinamento a partire dal XX secolo a.C.")
    public int ordine;

    @AField(type = TypeField.text, caption = "Nome corrente")
    public String nome;

    @AField(type = TypeField.integer, widthRem = 6, caption = "Primo anno del secolo")
    public int inizio;

    @AField(type = TypeField.integer, widthRem = 6, caption = "Ultimo anno del secolo")
    public int fine;

    @AField(type = TypeField.booleano, headerText = "a.C.")
    public boolean anteCristo;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
