package it.algos.wiki24.backend.packages.anno;

import it.algos.vaad24.backend.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import javax.persistence.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Fri, 08-Jul-2022
 * Time: 06:34
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@Component
@Document
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "annoWikiBuilder")
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass()
public class AnnoWiki extends AEntity {

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer, header = "#", widthEM = 6, caption = "Ordine a partire dal 1.000 a.C.")
    public int ordine;

    @AIField(type = AETypeField.text, widthEM = 7, caption = "Nome corrente")
    public String nome;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer, header = "nati", caption = "Numero di biografie che utilizzano i nati in questo anno", widthEM = 6)
    public int bioNati;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer, header = "morti", caption = "Numero di biografie che utilizzano i morti in questo anno", widthEM = 6)
    public int bioMorti;

    @AIField(type = AETypeField.text, widthEM = 10, header = "nati", caption = "Anno di nascita")
    public String pageNati;

    @AIField(type = AETypeField.text, widthEM = 10, header = "morti", caption = "Anno di morte")
    public String pageMorti;

    @AIField(type = AETypeField.booleano)
    public boolean esistePaginaNati;

    @AIField(type = AETypeField.booleano)
    public boolean esistePaginaMorti;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.thumb, header = "n")
    public boolean natiOk;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.thumb, header = "m")
    public boolean mortiOk;

    @AIField(type = AETypeField.integer)
    public int secolo;

    @Override
    public String toString() {
        return VUOTA;
    }

}// end of crud entity class