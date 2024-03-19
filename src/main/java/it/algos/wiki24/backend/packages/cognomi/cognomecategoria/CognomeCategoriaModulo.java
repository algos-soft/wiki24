package it.algos.wiki24.backend.packages.cognomi.cognomecategoria;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.logic.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.login.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.stereotype.*;

import java.util.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 19-Mar-2024
 * Time: 07:36
 */
@Service
public class CognomeCategoriaModulo extends WikiModulo {

    @Inject
    public BotLogin botLogin;

    @Inject
    QueryService queryService;

    public static final String COGNOMI_LINGUA = "Cognomi per lingua";

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public CognomeCategoriaModulo() {
        super(CognomeCategoriaEntity.class, CognomeCategoriaView.class, CognomeCategoriaList.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    public CognomeCategoriaEntity creaIfNotExists(String cognome) {
        if (existByKey(cognome)) {
            return null;
        }
        else {
            return (CognomeCategoriaEntity) insert(newEntity(cognome));
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public CognomeCategoriaEntity newEntity() {
        return newEntity(VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param cognome (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public CognomeCategoriaEntity newEntity(final String cognome) {
        CognomeCategoriaEntity newEntityBean = CognomeCategoriaEntity.builder()
                .cognome(textService.isValid(cognome) ? cognome : null)
                .build();

        return (CognomeCategoriaEntity) fixKey(newEntityBean);
    }

    @Override
    public List<CognomeCategoriaEntity> findAll() {
        return super.findAll();
    }

    @Override
    public CognomeCategoriaEntity findByKey(final Object keyPropertyValue) {
        return (CognomeCategoriaEntity) super.findByKey(keyPropertyValue);
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        this.download();
        return null;
    }


    /**
     * Legge le mappa di valori dalla pagina di wiki: <br>
     * Progetto:Antroponimi/Nomi doppi
     * <p>
     * Legge i valori dalla Categoria:Prenomi italiani maschili
     * Legge i valori dalla Categoria:Prenomi italiani femminili
     * Legge i valori dalla Categoria:Prenomi italiani sia maschili che femminili
     * <p>
     * Cancella la (eventuale) precedente lista di nomi doppi <br>
     */
    public void download() {
        List<String> listaSubCategorie;

        //--Cancella la (eventuale) precedente lista di nomi template
        deleteAll();

        if (botLogin != null && botLogin.isValido() && botLogin.isBot()) {
            listaSubCategorie = queryService.getSubCat(COGNOMI_LINGUA);
            if (listaSubCategorie != null && listaSubCategorie.size() > 0) {
                for (String wikiCatTitle : listaSubCategorie) {
                    downloadCategoria(wikiCatTitle);
                }
            }
        }
    }

    /**
     * Legge i valori dalla Categoria:Prenomi italiani maschili
     *
     * @return entities create
     */
    public void downloadCategoria(String wikiCatTitle) {
        List<String> listaCognomi = queryService.getTitles(wikiCatTitle);

        if (listaCognomi != null) {
            for (String cognome : listaCognomi) {
                if (cognome.contains(PARENTESI_TONDA_INI)) {
                    cognome = textService.levaCodaDaPrimo(cognome, PARENTESI_TONDA_INI);
                }
                creaIfNotExists(cognome);
            }
        }
    }


}// end of CrudModulo class
