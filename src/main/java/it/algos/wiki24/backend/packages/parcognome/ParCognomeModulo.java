package it.algos.wiki24.backend.packages.parcognome;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bioserver.*;
import it.algos.wiki24.backend.packages.par.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 31-Dec-2023
 * Time: 07:23
 */
@Service
public class ParCognomeModulo extends ParModulo {


    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ParCognomeModulo() {
        super(ParCognomeEntity.class, ParCognomeList.class, ParCognomeForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastElabora = WPref.lastElaboraParCognome;
        super.durataElabora = WPref.elaboraParCognomeTime;
        super.unitaMisuraElabora = TypeDurata.minuti;

        super.keyMapName = KEY_MAPPA_COGNOME;
    }


    @Override
    public ParCognomeEntity newEntity() {
        return newEntity(0, VUOTA, VUOTA, VUOTA);
    }

    public ParCognomeEntity newEntity(long pageId, String wikiTitle, String grezzo) {
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
    public ParCognomeEntity newEntity(long pageId, String wikiTitle, String grezzo, String elaborato) {
        ParCognomeEntity newEntityBean = ParCognomeEntity.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .grezzo(textService.isValid(grezzo) ? grezzo : null)
                .elaborato(textService.isValid(elaborato) ? elaborato : null)
                .build();

        return (ParCognomeEntity) fixKey(newEntityBean);
    }

    public String getElaborato(String grezzo) {
        return elaboraService.fixCognome(grezzo);
    }

    public AbstractEntity fixParametri(AbstractEntity parametroEntity, String grezzo, String elaborato) {
        ((ParCognomeEntity) parametroEntity).grezzoVuoto = textService.isEmpty(grezzo);
        ((ParCognomeEntity) parametroEntity).elaboratoVuoto = textService.isEmpty(elaborato);
        ((ParCognomeEntity) parametroEntity).uguale = elaborato != null ? elaborato.equals(grezzo) : grezzo != null ? true : false;

        return parametroEntity;
    }


}// end of CrudModulo class
