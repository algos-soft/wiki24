package it.algos.wiki24.backend.packages.nome;

import com.vaadin.flow.component.icon.*;
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
 * Date: Wed, 14-Jun-2023
 * Time: 09:10
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
@AIEntity(collectionName = "nome", keyPropertyName = "nome", usaReset = false, usaKeyIdMinuscolaCaseInsensitive = false)
public class Nome extends AEntity {

    @AIField(type = AETypeField.text, header = "nome", caption = "nome", widthEM = 14)
    public String nome;

    @AIField(type = AETypeField.integer, header = "bio", caption = "Numero di biografie che utilizzano lo stesso nome", widthEM = 6)
    public int numBio;

    @AIField(type = AETypeField.anchor)
    public String paginaVoce;

    @AIField(type = AETypeField.anchor)
    public String paginaLista;


    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNo, widthEM = 7)
    public boolean categoria;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNo, widthEM = 7)
    public boolean doppio;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNo, widthEM = 7)
    public boolean modulo;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNo, widthEM = 7)
    public boolean mongo;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.checkIcon, headerIcon = VaadinIcon.TRENDING_UP)
    public boolean superaSoglia;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.checkBox, headerIcon = VaadinIcon.LIST)
    public boolean esisteLista;

    @Override
    public String toString() {
        return nome;
    }

}// end of crud entity class