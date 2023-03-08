package it.algos.wiki24.backend.packages.giorno;

import it.algos.vaad24.backend.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.wiki24.backend.packages.wiki.*;
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
 * Date: Thu, 14-Jul-2022
 * Time: 20:04
 * <p>
 */
@Component
@Document
// Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderGiornoWiki")
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass()
@AIEntity(keyPropertyName = "nome", usaReset = true)
public class GiornoWiki extends AEntity {

    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    @AIField(type = AETypeField.integer, header = "#", widthEM = 3, caption = "Ordinamento")
    public int ordine;

    @AIField(type = AETypeField.text, caption = "Nome corrente", sortProperty = "ordine")
    public String nome;

    @DBRef
    @AIField(type = AETypeField.linkDinamico, linkClazz = MeseBackend.class)
    public Mese mese;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer, header = "nati", caption = "Numero di biografie che utilizzano i nati in questo giorno", widthEM = 6)
    public int bioNati;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer, header = "morti", caption = "Numero di biografie che utilizzano i morti in questo giorno", widthEM = 6)
    public int bioMorti;

    @AIField(type = AETypeField.text, widthEM = 10, header = "nati", caption = "Giorno/anno di nascita")
    public String pageNati;

    @AIField(type = AETypeField.text, widthEM = 10, header = "morti", caption = "Giorno/anno di morte")
    public String pageMorti;

    @AIField(type = AETypeField.booleano)
    public boolean esistePaginaNati;

    @AIField(type = AETypeField.booleano)
    public boolean esistePaginaMorti;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.thumb, header = "n")
    public boolean natiOk;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.thumb, header = "m")
    public boolean mortiOk;

    @Override
    public String toString() {
        return nome;
    }


}// end of crud entity class