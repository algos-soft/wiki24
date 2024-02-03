package it.algos.base24.backend.packages.geografia.provincia;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sat, 03-Feb-2024
 * Time: 09:48
 */
@Service
public class ProvinciaModulo extends CrudModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ProvinciaModulo() {
        super(ProvinciaEntity.class, ProvinciaView.class, ProvinciaList.class, ProvinciaForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    public ProvinciaEntity creaIfNotExists(String sigla, String descrizione) {
        if (existByKey(sigla)) {
            return null;
        }
        else {
            return (ProvinciaEntity) insert(newEntity(sigla, descrizione));
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public ProvinciaEntity newEntity() {
        return newEntity(VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param sigla       (obbligatorio)
     * @param descrizione (facoltativa)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ProvinciaEntity newEntity(final String sigla, final String descrizione) {
        ProvinciaEntity newEntityBean = ProvinciaEntity.builder()
                .sigla(textService.isValid(sigla) ? sigla : null)
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .build();

        return (ProvinciaEntity) fixKey(newEntityBean);
    }

    @Override
    public List<ProvinciaEntity> findAll() {
        return super.findAll();
    }

    @Override
    public ProvinciaEntity findByKey(final Object keyPropertyValue) {
        return (ProvinciaEntity) super.findByKey(keyPropertyValue);
    }

    @Override
    public void download() {
        RisultatoReset typeReset = super.resetDelete();
         resetBase();
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        return resetBase();
    }

    private RisultatoReset resetBase() {
        String nomePaginaWiki = "Province d'Italia";
        List<List<String>> mappa= webService.getWikiTable(nomePaginaWiki);
        String sigla;

        for (List<String> riga : mappa) {
            sigla=riga.get(2);
            insert(newEntity(sigla,sigla));
        }

        return null;
    }

}// end of CrudModulo class
