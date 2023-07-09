package it.algos.wiki24.backend.packages.cognomecategoria;

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

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 08-Jul-2023
 * Time: 19:00
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
@AIEntity(collectionName = "cognomecategoria", keyPropertyName = "nome", usaReset = true, usaKeyIdMinuscolaCaseInsensitive = false)
public class CognomeCategoria extends AEntity {

    @AIField(type = AETypeField.text, widthEM = 20)
    public String nome;

    @AIField(type = AETypeField.anchor)
    public String linkPagina;

    @AIField(type = AETypeField.text, widthEM = 14)
    public String lingua;

    @Override
    public String toString() {
        return nome;
    }

}// end of crud entity class