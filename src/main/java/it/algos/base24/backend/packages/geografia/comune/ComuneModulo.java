package it.algos.base24.backend.packages.geografia.comune;

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
 * Time: 09:13
 */
@Service
public class ComuneModulo extends CrudModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ComuneModulo() {
        super(ComuneEntity.class, ComuneView.class, ComuneList.class, ComuneForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    public ComuneEntity creaIfNotExists(String nome) {
        if (existByKey(nome)) {
            return null;
        }
        else {
            return (ComuneEntity) insert(newEntity(nome));
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public ComuneEntity newEntity() {
        return newEntity(VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param nome (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ComuneEntity newEntity(final String nome) {
        ComuneEntity newEntityBean = ComuneEntity.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .build();

        return (ComuneEntity) fixKey(newEntityBean);
    }

    @Override
    public List<ComuneEntity> findAll() {
        return super.findAll();
    }

    @Override
    public ComuneEntity findByKey(final Object keyPropertyValue) {
        return (ComuneEntity) super.findByKey(keyPropertyValue);
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
        String tag = "Comuni d'Italia";
        List<String> lettere = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H-J", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "Z");
        String wikiTitle;
        ComuneEntity newBean;

        for (String key : lettere) {
            wikiTitle = String.format("%s%s%s%s%s", tag, SPAZIO, PARENTESI_TONDA_INI, key, PARENTESI_TONDA_END);
            addComuniPerLettera(wikiTitle);
        }

        return null;
    }


    private void addComuniPerLettera(String wikiTitle) {
        List<List<String>> mappa = webService.getWikiTable(wikiTitle);

        for (List<String> riga : mappa) {
            creaIfNotExists(riga.get(0));
        }
    }

}// end of CrudModulo class
