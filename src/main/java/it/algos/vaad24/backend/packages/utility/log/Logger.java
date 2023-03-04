package it.algos.vaad24.backend.packages.utility.log;

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
 * Date: mer, 16-mar-2022
 * Time: 19:47
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
@AIEntity(keyPropertyName = "evento")
public class Logger extends AEntity {

    @AIField(type = AETypeField.enumType, enumClazz = AETypeLog.class)
    public AETypeLog type;

    @AIField(type = AETypeField.enumType, enumClazz = AELogLevel.class)
    public AELogLevel livello;

    @AIField(type = AETypeField.localDateTime)
    public LocalDateTime evento;

    @NotEmpty
    @AIField(type = AETypeField.text, flexGrow = true, search = true)
    public String descrizione;

    @AIField(type = AETypeField.text)
    public String company;

    @AIField(type = AETypeField.text)
    public String user;

    @AIField(type = AETypeField.text)
    public String address;

    @AIField(type = AETypeField.text)
    public String classe;

    @AIField(type = AETypeField.text, widthEM = 18)
    public String metodo;

    @AIField(type = AETypeField.integer, header = "#")
    public int linea;

    @Transient
    public LocalDate giorno;

    @Transient
    public LocalTime ora;

    @Override
    public String toString() {
        return DateTimeFormatter.ofPattern("d-MMM-yy HH:mm:ss").format(evento);
    }

}// end of crud entity class