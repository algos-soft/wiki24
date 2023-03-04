package it.algos.vaad24.backend.packages.utility.versione;

import com.vaadin.flow.component.icon.*;
import it.algos.vaad24.backend.annotation.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import javax.persistence.*;
import java.time.*;


/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 09-mar-2022
 * Time: 18:17
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
@AIEntity(keyPropertyName = "code")
public class Versione extends AEntity {

    @AIField(type = AETypeField.integer)
    public int ordine;

    @AIField(type = AETypeField.text, widthEM = 6)
    public String code;

    @AIField(type = AETypeField.enumerationType, enumClazz = AETypeVers.class)
    public AETypeVers type;

    @AIField(type = AETypeField.doppio)
    public double release;

    @AIField(type = AETypeField.text, widthEM = 12)
    public String titolo;

    @AIField(type = AETypeField.localDate)
    public LocalDate giorno;

    @AIField(type = AETypeField.text, flexGrow = true, search = true)
    public String descrizione;

    @AIField(type = AETypeField.text)
    public String company;

    @AIField(type = AETypeField.booleano, headerIcon = VaadinIcon.FACTORY)
    public boolean vaadin23;


    @Override
    public String toString() {
        return code;
    }


}// end of crud entity class