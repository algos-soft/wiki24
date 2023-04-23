package it.algos.wiki24.backend.packages.template;

import it.algos.vaad24.backend.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import lombok.*;

import javax.persistence.*;

import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

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

    private long pageid;

    private String wikiTitle;

    private LocalDateTime timestamp;

    private String templBio;


    @Override
    public String toString() {
        return wikiTitle;
    }

}// end of crud entity class