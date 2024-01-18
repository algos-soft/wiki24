package it.algos.base24.backend.packages.utility.nota;

import com.vaadin.flow.component.icon.*;
import it.algos.base24.backend.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.time.format.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "nota", sortPropertyName = "inizio", sortDiscendente = true, typeList = TypeList.standard)
public class NotaEntity extends AbstractEntity {

    @AField(type = TypeField.enumType, enumClazz = TypeLog.class)
    public TypeLog typeLog;

    @AField(type = TypeField.enumType, enumClazz = LogLevel.class)
    public LogLevel typeLevel;

    @AField(type = TypeField.localDate, typeDate = TypeDate.dateNormal)
    public LocalDate inizio;

    @AField(type = TypeField.text, widthRem = 20, focus = true)
    public String descrizione;

    @AField(type = TypeField.booleano, headerIcon = VaadinIcon.CHECK)
    public boolean fatto;

    @AField(type = TypeField.localDate, typeDate = TypeDate.dateNormal)
    public LocalDate fine;

    @Override
    public String toString() {
        return inizio != null ? DateTimeFormatter.ofPattern("d-MMM-yy").format(inizio) : VUOTA;
    }

}// end of Entity class
