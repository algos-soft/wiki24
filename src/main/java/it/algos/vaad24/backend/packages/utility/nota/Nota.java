package it.algos.vaad24.backend.packages.utility.nota;

import com.vaadin.flow.component.icon.*;
import it.algos.vaad24.backend.annotation.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.time.format.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 18-mar-2022
 * Time: 06:55
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@Component
@Document
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass()
public class Nota extends AEntity {

    @AIField(type = AETypeField.enumerationType, enumClazz = AETypeLog.class)
    public AETypeLog type;

    @AIField(type = AETypeField.enumerationType, enumClazz = AENotaLevel.class)
    public AENotaLevel livello;

    @AIField(type = AETypeField.localDate)
    public LocalDate inizio;

    @NotEmpty
    @AIField(type = AETypeField.text, flexGrow = true, focus = true, search = true)
    public String descrizione;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.thumb, headerIcon = VaadinIcon.CHECK)
    public boolean fatto;

    @AIField(type = AETypeField.localDate)
    public LocalDate fine;

    @Override
    public String toString() {
        return DateTimeFormatter.ofPattern("d-MMM-yy").format(inizio);
    }

}// end of crud entity class