package it.algos.wiki24.backend.packages.template;

import it.algos.vaad24.backend.annotation.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import java.time.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 21-Apr-2023
 * Time: 08:36
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
@AIEntity(collectionName = "template", keyPropertyName = "pageid")
public class Template extends AEntity {

    @Positive()
    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    @AIField(type = AETypeField.lungo, enabled = false, widthEM = 7)
    public long pageId;


    @NotBlank()
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, widthEM = 16)
    public String wikiTitle;

    @Lob
    @AIField(type = AETypeField.textArea, required = true, widthEM = 48)
    public String tmplBio;


    @AIField(type = AETypeField.localDateTime)
    public LocalDateTime lastServer;


    @AIField(type = AETypeField.localDateTime)
    public LocalDateTime lastMongo;

    /**
     * valido se lastMongo >= timestamp
     */
    @AIField(type = AETypeField.booleano)
    public boolean valido;

    @Override
    public String toString() {
        return wikiTitle;
    }

}// end of crud entity class