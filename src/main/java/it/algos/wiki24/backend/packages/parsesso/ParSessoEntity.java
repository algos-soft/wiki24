package it.algos.wiki24.backend.packages.parsesso;

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
@AEntity(collectionName = "parsesso", keyPropertyName = "pageId", typeList = TypeList.vuoto)
public class ParSessoEntity extends AbstractEntity {

    @AField(type = TypeField.lungo, widthRem = 7)
    public long pageId;

    @AField(type = TypeField.text)
    public String grezzo;

    @AField(type = TypeField.text)
    public String elaborato;

    @AField(type = TypeField.booleano)
    public boolean valido;

    @AField(type = TypeField.booleano)
    public boolean pieno;

    @Override
    public String toString() {
        return grezzo;
    }

}// end of Entity class
