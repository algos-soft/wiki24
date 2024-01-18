package it.algos.base24.backend.packages.crono.giorno;

import com.vaadin.flow.component.icon.*;
import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.packages.crono.mese.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "giorno", typeList = TypeList.hardCode)
public class GiornoEntity extends AbstractEntity {

    @AField(type = TypeField.integer, headerText = "#", widthRem = 3, caption = "Ordinamento da inizio anno")
    public int ordine;

    @AField(type = TypeField.text, caption = "Nome corrente")
    public String nome;

    //    @DBRef
    @AField(type = TypeField.linkDBRef, linkClazz = MeseEntity.class)
    public MeseEntity mese;

    @AField(type = TypeField.integer, widthRem = 6, headerIcon = VaadinIcon.STEP_BACKWARD, caption = "Progressivo da inizio anno")
    public int trascorsi;

    @AField(type = TypeField.integer, widthRem = 6, headerIcon = VaadinIcon.STEP_FORWARD, caption = "Mancanti alla fine dell'anno")
    public int mancanti;

    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
