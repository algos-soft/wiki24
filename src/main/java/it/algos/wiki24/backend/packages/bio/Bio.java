package it.algos.wiki24.backend.packages.bio;

import it.algos.vaad24.backend.annotation.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.wiki24.backend.enumeration.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: gio, 28-apr-2022
 * Time: 11:57
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
//@QueryEntity
@Component
@Document
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass()
public class Bio extends AEntity {

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
     * valido se lastLettura >= lastModifica
     */
    @AIField(type = AETypeField.booleano)
    public boolean valido;

    /**
     * elaborato se sono riempiti i campi derivati dal tmplBio
     */
    @AIField(type = AETypeField.booleano, header = "fix")
    public boolean elaborato;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text)
    public String nome;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text)
    public String cognome;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, search = true, widthEM = 16)
    public String ordinamento;

    @AIField(type = AETypeField.text, header = "XY", widthEM = 3)
    public String sesso;

    @AIField(type = AETypeField.text, header = "giorno", widthEM = 8)
    public String giornoNato;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer)
    public int giornoNatoOrd;

    @AIField(type = AETypeField.text, header = "anno", widthEM = 6)
    public String annoNato;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer)
    public int annoNatoOrd;

    @AIField(type = AETypeField.text)
    public String luogoNato;

    @AIField(type = AETypeField.text)
    public String luogoNatoLink;

    @AIField(type = AETypeField.text, header = "giorno", widthEM = 8)
    public String giornoMorto;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer)
    public int giornoMortoOrd;

    @AIField(type = AETypeField.text, header = "anno", widthEM = 7)
    public String annoMorto;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.integer)
    public int annoMortoOrd;

    @AIField(type = AETypeField.text)
    public String luogoMorto;

    @AIField(type = AETypeField.text)
    public String luogoMortoLink;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, widthEM = 12)
    public String attivita;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, widthEM = 8)
    public String attivita2;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, widthEM = 8)
    public String attivita3;

    @Indexed(unique = false, direction = IndexDirection.DESCENDING)
    @AIField(type = AETypeField.text, widthEM = 12)
    public String nazionalita;

    /**
     * errore generico
     */
    @AIField(type = AETypeField.booleano)
    public boolean errato;

    @AIField(type = AETypeField.enumerationType, enumClazz = AETypeBioError.class, nullSelectionAllowed = true, widthEM = 12)
    public AETypeBioError errore;

    @Override
    public String toString() {
        return wikiTitle;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}// end of crud entity class