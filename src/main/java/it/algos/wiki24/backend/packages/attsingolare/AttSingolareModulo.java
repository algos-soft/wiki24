package it.algos.wiki24.backend.packages.attsingolare;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.logic.*;
import org.springframework.stereotype.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 16-Nov-2023
 * Time: 18:35
 */
@Service
public class AttSingolareModulo extends WikiModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public AttSingolareModulo() {
        super(AttSingolareEntity.class, AttSingolareList.class, AttSingolareForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public AttSingolareEntity newEntity() {
        return newEntity(VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param keyPropertyValue (obbligatorio, unico)
     * @param plurale          (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public AttSingolareEntity newEntity(final String keyPropertyValue, String plurale) {
        AttSingolareEntity newEntityBean = AttSingolareEntity.builder()
                .nome(textService.isValid(keyPropertyValue) ? keyPropertyValue : null)
                .plurale(textService.isValid(plurale) ? plurale : null)
                .numBio(0)
                .build();

        return (AttSingolareEntity) fixKey(newEntityBean);
    }


    @Override
    public RisultatoReset resetDelete() {
        //        RisultatoReset typeReset = super.resetDelete();
        String nomeModulo = TAG_MODULO + "Plurale attività";
        String testoLeggibile = VUOTA;

        testoLeggibile = webService.leggeWikiParse(nomeModulo);
        int a = 87;
        return null;
    }

}// end of CrudModulo class
