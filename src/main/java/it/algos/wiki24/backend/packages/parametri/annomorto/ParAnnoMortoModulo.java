package it.algos.wiki24.backend.packages.parametri.annomorto;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.parametri.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 01-Jan-2024
 * Time: 15:20
 */
@Service
public class ParAnnoMortoModulo extends ParModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ParAnnoMortoModulo() {
        super(ParAnnoMortoEntity.class, ParAnnoMortoList.class, ParAnnoMortoForm.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastElabora = WPref.lastElaboraParAnnoMorto;
        super.durataElabora = WPref.elaboraParAnnoMortoTime;
        super.unitaMisuraElabora = TypeDurata.minuti;

        super.keyMapName = KEY_MAPPA_ANNO_MORTE;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public ParAnnoMortoEntity newEntity() {
        return newEntity(0, VUOTA, VUOTA, VUOTA);
    }

    public ParAnnoMortoEntity newEntity(long pageId, String wikiTitle, String grezzo) {
        return newEntity(pageId, wikiTitle, grezzo, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param pageId    (obbligatorio)
     * @param wikiTitle (obbligatorio)
     * @param grezzo    (obbligatorio)
     * @param elaborato (facoltativo)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ParAnnoMortoEntity newEntity(long pageId, String wikiTitle, String grezzo, String elaborato) {
        ParAnnoMortoEntity newEntityBean = ParAnnoMortoEntity.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .grezzo(textService.isValid(grezzo) ? grezzo : null)
                .elaborato(textService.isValid(elaborato) ? elaborato : null)
                .build();

        return (ParAnnoMortoEntity) fixKey(newEntityBean);
    }

    public String getElaborato(String wikiTitle, String grezzo) {
        return elaboraService.fixAnno(wikiTitle, grezzo);
    }

}// end of CrudModulo class
