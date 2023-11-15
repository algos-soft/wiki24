package it.algos.base24.backend.packages.crono.mese;

import com.vaadin.flow.component.icon.*;
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
@AEntity(collectionName = "mese", keyPropertyName = "nome",usaStartupReset = true, typeReset = TypeReset.soloShow)
public class MeseEntity extends AbstractEntity {

    @AField(type = TypeField.integer, widthRem = 6)
    public int ordine;

    @AField(type = TypeField.text)
    public String sigla;

    @AField(type = TypeField.text)
    public String nome;

    @AField(type = TypeField.integer, widthRem = 6)
    public int giorni;

    @AField(type = TypeField.integer, widthRem = 6, headerIcon = VaadinIcon.STEP_BACKWARD, caption = "Primo giorno (annuo) del mese")
    public int primo;

    @AField(type = TypeField.integer, widthRem = 6, headerIcon = VaadinIcon.STEP_FORWARD, caption = "Ultimo giorno (annuo) del mese")
    public int ultimo;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
