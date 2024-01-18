package it.algos.base24.backend.packages.utility.logs;

import it.algos.base24.backend.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "logger", sortPropertyName = "evento", sortDiscendente = true, typeList = TypeList.standard)
public class LogEntity extends AbstractEntity {

    /**
     * raggruppamento logico dei log per type di eventi (obbligatorio)
     */
    @AField(type = TypeField.enumType, enumClazz = TypeLog.class)
    public TypeLog typeLog;

    /**
     * raggruppamento logico dei log per livello di eventi (obbligatorio)
     */
    @AField(type = TypeField.enumType, enumClazz = LogLevel.class, widthRem = 8)
    public LogLevel typeLevel;

    /**
     * Data dell'evento (obbligatoria, non modificabile)
     * Gestita in automatico
     * Field visibile solo al buttonAdmin
     */
    @AField(type = TypeField.localDateTime, typeDate = TypeDate.normaleOrario, widthRem = 10)
    public LocalDateTime evento;

    /**
     * descrizione (facoltativa, non unica) <br>
     */
    @AField(type = TypeField.text, widthRem = 40)
    public String descrizione;


    @Override
    public String toString() {
        return evento != null ? evento.format(DateTimeFormatter.ofPattern("d-MMM-yy H:mm", Locale.ITALIAN)) : VUOTA;
    }

}// end of Entity class
