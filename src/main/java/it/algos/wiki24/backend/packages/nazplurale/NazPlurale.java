package it.algos.wiki24.backend.packages.nazplurale;

import com.vaadin.flow.component.icon.*;
import it.algos.vaad24.backend.annotation.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import lombok.*;

import javax.persistence.*;

import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 23-Mar-2023
 * Time: 19:20
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
@AIEntity(collectionName = "nazplurale", keyPropertyName = "nome", usaReset = true, usaKeyIdMinuscolaCaseInsensitive = false)
public class NazPlurale extends AEntity {

    @AIField(type = AETypeField.text, header = "plurale", caption = "plurale", widthEM = 20)
    public String nome;

    @AIField(type = AETypeField.listaV, widthEM = 30)
    public List<NazSingolare> listaSingolari;

    @AIField(type = AETypeField.text, widthEM = 12)
    public String paginaLista;

    @AIField(type = AETypeField.text, widthEM = 10)
    public String linkNazione;

    @AIField(type = AETypeField.integer, header = "bio", caption = "Numero di biografie che utilizzano la stessa nazionalità plurale", widthEM = 6)
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