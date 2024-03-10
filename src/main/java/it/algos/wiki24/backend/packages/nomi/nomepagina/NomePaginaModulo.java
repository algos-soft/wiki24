package it.algos.wiki24.backend.packages.nomi.nomepagina;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.tabelle.attsingolare.*;
import org.springframework.stereotype.*;

import java.util.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 09-Mar-2024
 * Time: 13:53
 */
@Service
public class NomePaginaModulo extends WikiModulo {

    public static final String INCIPIT_NOMI = "Incipit nomi";

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public NomePaginaModulo() {
        super(NomePaginaEntity.class, NomePaginaView.class, NomePaginaList.class, NomePaginaForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    public NomePaginaEntity creaIfNotExists(String nome, String pagina) {
        if (existByKey(nome)) {
            return null;
        }
        else {
            return (NomePaginaEntity) insert(newEntity(nome, pagina));
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public NomePaginaEntity newEntity() {
        return newEntity(VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param nome   (obbligatorio)
     * @param pagina (facoltativa)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NomePaginaEntity newEntity(final String nome, final String pagina) {
        NomePaginaEntity newEntityBean = NomePaginaEntity.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .pagina(textService.isValid(pagina) ? pagina : null)
                .build();

        return (NomePaginaEntity) fixKey(newEntityBean);
    }

    @Override
    public List<NomePaginaEntity> findAll() {
        return super.findAll();
    }

    @Override
    public NomePaginaEntity findByKey(final Object keyPropertyValue) {
        return (NomePaginaEntity) super.findByKey(keyPropertyValue);
    }


    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        this.download();
        return null;
    }

    /**
     * Legge la mappa di valori dal modulo di wiki: <br>
     * Modulo:Incipit nomi
     * <p>
     * Cancella la (eventuale) precedente lista di nomi pagina <br>
     */
    public void download() {
        inizio = System.currentTimeMillis();
        String testoModulo;
        Map<String, String> mappa;
        String nome;
        String pagina;
        String tagIni = "local tabella = {";
        String tagEnd = "}";

        super.deleteAll();

        testoModulo = wikiApiService.legge(MODULO + INCIPIT_NOMI);
        testoModulo = textService.levaPrimaAncheTag(testoModulo, tagIni);
        testoModulo = textService.levaCodaDaPrimo(testoModulo, tagEnd);
        mappa = wikiApiService.creaMappaTestoModulo(testoModulo);

        if (mappa == null || mappa.size() < 1) {
            message = String.format("Non sono riuscito a leggere da wiki il modulo [%s]", INCIPIT_NOMI);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
            return;
        }

        for (String key : mappa.keySet()) {
            nome = key;
            pagina = mappa.get(key);
            creaIfNotExists(nome, pagina);
        }

        super.fixDownload(inizio);
    }


}// end of CrudModulo class
