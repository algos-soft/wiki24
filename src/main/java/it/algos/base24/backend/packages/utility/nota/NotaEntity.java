package it.algos.base24.backend.packages.utility.nota;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.time.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "nota", typeList = TypeList.standard)
public class NotaEntity extends AbstractEntity {

    @AField(type = TypeField.text)
    public String code;

    @AField(type = TypeField.lungo)
    public long lungo;

    @AField(type = TypeField.localDateTime, typeDate = TypeDate.normaleOrario)
    public LocalDateTime evento;

    @Override
    public String toString() {
        return code;
    }

}// end of Entity class