package it.algos.wiki24.backend.packages.parametro;

import it.algos.vaad24.backend.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import lombok.*;

import javax.persistence.*;

import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import java.time.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 09-Mar-2023
 * Time: 07:38
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
//Lombok
@Component
@Document
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass()
@AIEntity(keyPropertyName = "pageId")
public class Parametro extends AEntity {

    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    @AIField(type = AETypeField.text, enabled = false, widthEM = 7)
    public String pageId;

    @AIField(type = AETypeField.text, widthEM = 16)
    public String wikiTitle;

    @Lob
    @AIField(type = AETypeField.textArea, required = true)
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
    @AIField(type = AETypeField.booleano)
    public boolean elaborato;


    @AIField(type = AETypeField.text)
    public String titolo;

    @AIField(type = AETypeField.text)
    public String nome;

    @AIField(type = AETypeField.text)
    public String cognome;

    @AIField(type = AETypeField.text)
    public String cognomePrima;

    @AIField(type = AETypeField.text)
    public String pseudonimo;

    @AIField(type = AETypeField.text)
    public String postPseudonimo;

    @AIField(type = AETypeField.text)
    public String postCognome;

    @AIField(type = AETypeField.text)
    public String postCognomeVirgola;

    @AIField(type = AETypeField.text)
    public String forzaOrdinamento;

    @AIField(type = AETypeField.text)
    public String preData;

    @AIField(type = AETypeField.text)
    public String sesso;

    @AIField(type = AETypeField.text)
    public String luogoNascita;

    @AIField(type = AETypeField.text)
    public String luogoNascitaLink;

    @AIField(type = AETypeField.text)
    public String luogoNascitaAlt;

    @AIField(type = AETypeField.text)
    public String giornoMeseNascita;

    @AIField(type = AETypeField.text)
    public String annoNascita;

    @AIField(type = AETypeField.text)
    public String noteNascita;

    @AIField(type = AETypeField.text)
    public String luogoMorte;

    @AIField(type = AETypeField.text)
    public String luogoMorteLink;

    @AIField(type = AETypeField.text)
    public String luogoMorteAlt;

    @AIField(type = AETypeField.text)
    public String giornoMeseMorte;

    @AIField(type = AETypeField.text)
    public String annoMorte;

    @AIField(type = AETypeField.text)
    public String noteMorte;

    @AIField(type = AETypeField.text)
    public String floruit;

    @AIField(type = AETypeField.text)
    public String epoca;

    @AIField(type = AETypeField.text)
    public String epoca2;

    @AIField(type = AETypeField.text)
    public String preAttività;

    @AIField(type = AETypeField.text)
    public String attività;

    @AIField(type = AETypeField.text)
    public String attività2;

    @AIField(type = AETypeField.text)
    public String attività3;

    @AIField(type = AETypeField.text)
    public String attivitàAltre;

    @AIField(type = AETypeField.text)
    public String nazionalità;

    @AIField(type = AETypeField.text)
    public String nazionalitàNaturalizzato;

    @AIField(type = AETypeField.text)
    public String cittadinanza;

    @AIField(type = AETypeField.text)
    public String postNazionalità;

    @AIField(type = AETypeField.text)
    public String categorie;

    @AIField(type = AETypeField.text)
    public String fineIncipit;

    @AIField(type = AETypeField.text)
    public String punto;

    @AIField(type = AETypeField.text)
    public String immagine;

    @AIField(type = AETypeField.text)
    public String didascalia;

    @AIField(type = AETypeField.text)
    public String didascalia2;

    @AIField(type = AETypeField.text)
    public String dimImmagine;


    @Override
    public String toString() {
        return pageId;
    }

}// end of crud entity class