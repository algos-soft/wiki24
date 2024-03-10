package it.algos.wiki24.backend.packages.nomi.nomecategoria;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
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
 * Date: Sat, 09-Mar-2024
 * Time: 13:52
 */
@Service
public class NomeCategoriaModulo extends WikiModulo {

    @Inject
    public BotLogin botLogin;

    public static final String CAT_MASCHILE = "Prenomi italiani maschili";

    public static final String CAT_FEMMINILE = "Prenomi italiani femminili";

    public static final String CAT_ENTRAMBI = "Prenomi italiani sia maschili che femminili";

    @Inject
    QueryService queryService;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public NomeCategoriaModulo() {
        super(NomeCategoriaEntity.class, NomeCategoriaView.class, NomeCategoriaList.class, NomeCategoriaForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    public NomeCategoriaEntity creaIfNotExists(final String wikiTitle, final TypeGenere typeGenere) {
        if (textService.isEmpty(wikiTitle)) {
            return null;
        }

        if (existByKey(wikiTitle)) {
            return null;
        }

        return (NomeCategoriaEntity) insert(newEntity(wikiTitle, typeGenere, null));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public NomeCategoriaEntity newEntity() {
        return newEntity(VUOTA, null, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param nome       (obbligatorio)
     * @param typeGenere (obbligatorio)
     * @param linkPagina (facoltativa)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NomeCategoriaEntity newEntity(final String nome, final TypeGenere typeGenere, final String linkPagina) {
        NomeCategoriaEntity newEntityBean = NomeCategoriaEntity.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .typeGenere(typeGenere != null ? typeGenere : TypeGenere.nessuno)
                .linkPagina(textService.isValid(linkPagina) ? linkPagina : null)
                .build();

        return (NomeCategoriaEntity) fixKey(newEntityBean);
    }

    @Override
    public List<NomeCategoriaEntity> findAll() {
        return super.findAll();
    }

    @Override
    public NomeCategoriaEntity findByKey(final Object keyPropertyValue) {
        return (NomeCategoriaEntity) super.findByKey(keyPropertyValue);
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
        //--Cancella la (eventuale) precedente lista di nomi template
        deleteAll();

        if (botLogin != null && botLogin.isValido() && botLogin.isBot()) {
            downloadCategoria(CAT_MASCHILE, TypeGenere.maschile);
            downloadCategoria(CAT_FEMMINILE, TypeGenere.femminile);
            downloadCategoria(CAT_ENTRAMBI, TypeGenere.entrambi);
        }
    }

    /**
     * Legge i valori dalla Categoria:Prenomi italiani maschili
     *
     * @return entities create
     */
    public void downloadCategoria(String wikiCatTitle, TypeGenere typeGenere) {
        List<String> listaWikiTitle = queryService.getTitles(wikiCatTitle);

        if (listaWikiTitle != null) {
            for (String wikiTitle : listaWikiTitle) {
                if (wikiTitle.contains(PARENTESI_TONDA_INI)) {
                    wikiTitle = textService.levaCodaDaPrimo(wikiTitle, PARENTESI_TONDA_INI);
                }
                creaIfNotExists(wikiTitle, typeGenere);
            }
        }
    }


}// end of CrudModulo class
