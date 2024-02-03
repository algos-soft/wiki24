package it.algos.base24.backend.packages.utility.preferenza;

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
@AEntity(collectionName = "preferenza", keyPropertyName = "code", typeList = TypeList.pref)
public class PreferenzaEntity extends AbstractEntity {

    @AField(type = TypeField.text, widthRem = 16)
    public String code;

    @AField(type = TypeField.enumType, enumClazz = TypePref.class)
    public TypePref type;

    @AField(type = TypeField.text, widthRem = 40)
    public String descrizione;

    @AField(type = TypeField.preferenza, widthRem = 12)
    public byte[] iniziale;

    @AField(type = TypeField.preferenza, widthRem = 12)
    public byte[] corrente;

    @AField(type = TypeField.booleano,  headerIcon = VaadinIcon.FIRE)
    public boolean critical;

    @AField(type = TypeField.booleano,  headerIcon = VaadinIcon.ASTERISK)
    public boolean dinamica;

    @AField(type = TypeField.booleano,  headerIcon = VaadinIcon.HOME)
    public boolean base24;

    @Override
    public String toString() {
        return code;
    }

}// end of Entity class
