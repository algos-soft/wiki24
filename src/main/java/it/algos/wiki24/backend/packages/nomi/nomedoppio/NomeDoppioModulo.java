package it.algos.wiki24.backend.packages.nomi.nomedoppio;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.logic.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 01-Feb-2024
 * Time: 15:01
 */
@Service
public class NomeDoppioModulo extends WikiModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public NomeDoppioModulo() {
        super(NomeDoppioEntity.class, NomeDoppioView.class, NomeDoppioList.class, NomeDoppioForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    public NomeDoppioEntity creaIfNotExists(String nome) {
        if (existById(nome)) {
            return null;
        }
        else {
            return (NomeDoppioEntity) insert(newEntity(nome));
        }
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public NomeDoppioEntity newEntity() {
        return newEntity(VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param nome (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NomeDoppioEntity newEntity(String nome) {
        NomeDoppioEntity newEntityBean = NomeDoppioEntity.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .build();

        return (NomeDoppioEntity) fixKey(newEntityBean);
    }

    @Override
    public List<NomeDoppioEntity> findAll() {
        return super.findAll();
    }
    public List<String> findAllForKey() {
        return findAll().stream().map(bean -> bean.nome).toList();
    }

    @Override
    public NomeDoppioEntity findByKey(final Object keyPropertyValue) {
        return (NomeDoppioEntity) super.findByKey(keyPropertyValue);
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
     * Cancella la (eventuale) precedente lista di nomi doppi <br>
     */
    public void download() {
        String testoGrezzo;
        String wikiTitle = TAG_ANTROPONIMI + DOPPI;
        inizio = System.currentTimeMillis();
        String[] parti;
        super.deleteAll();

        testoGrezzo = wikiApiService.legge(wikiTitle);
        parti = testoGrezzo.split(CAPO);
        for (String riga : parti) {
            if (riga.startsWith(ASTERISCO)) {
                riga = textService.levaTesta(riga, ASTERISCO).trim();
                creaIfNotExists(riga);
            }
        }
    }


}// end of CrudModulo class
