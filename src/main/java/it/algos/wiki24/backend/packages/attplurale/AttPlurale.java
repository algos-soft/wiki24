package it.algos.wiki24.backend.packages.attplurale;

import com.vaadin.flow.component.icon.*;
import it.algos.vaad24.backend.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import lombok.*;

import javax.persistence.*;

import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 25-Mar-2023
 * Time: 16:50
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
@AIEntity(collectionName = "attplurale", keyPropertyName = "nome")
public class AttPlurale extends AEntity {


    @AIField(type = AETypeField.text, header = "plurale", caption = "plurale", widthEM = 20)
    public String nome;

    @AIField(type = AETypeField.listaV, widthEM = 30)
    public List<AttSingolare> listaSingolari;

    @AIField(type = AETypeField.text, widthEM = 12)
    public String paginaLista;

    @AIField(type = AETypeField.text, widthEM = 10)
    public String linkAttivita;

    @AIField(type = AETypeField.integer, header = "bio", caption = "Numero di biografie che utilizzano la stessa attività plurale", widthEM = 6)
    public int numBio;

    @AIField(type = AETypeField.integer, header = "sin")
    public int numSingolari;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.thumb, headerIcon = VaadinIcon.LINES)
    public boolean superaSoglia;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNoReverse, headerIcon = VaadinIcon.HAMMER)
    public boolean esisteLista;

    @Override
    public String toString() {
        return nome;
    }

}// end of crud entity class