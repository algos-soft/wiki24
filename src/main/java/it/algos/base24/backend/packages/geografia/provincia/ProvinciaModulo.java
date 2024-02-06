package it.algos.base24.backend.packages.geografia.provincia;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.anagrafica.via.*;
import it.algos.base24.backend.packages.geografia.regione.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.stereotype.*;

import javax.inject.*;
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

    @Inject
    RegioneModulo regioneModulo;

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

    public ProvinciaEntity creaIfNotExists(String sigla, String nomeBreve, String nomeCompleto, RegioneEntity regione) {
        if (existByKey(sigla)) {
            return null;
        }
        else {
            return (ProvinciaEntity) insert(newEntity(sigla, nomeBreve, nomeCompleto, regione));
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public ProvinciaEntity newEntity() {
        return newEntity(VUOTA, VUOTA, VUOTA, null);
    }

    public ProvinciaEntity newEntity(final String sigla, final String nomeBreve) {
        return newEntity(sigla, nomeBreve, VUOTA, null);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param sigla        (obbligatorio)
     * @param nomeBreve    (obbligatorio)
     * @param nomeCompleto (facoltativa)
     * @param regione      (facoltativa)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ProvinciaEntity newEntity(final String sigla, final String nomeBreve, String nomeCompleto, RegioneEntity regione) {
        ProvinciaEntity newEntityBean = ProvinciaEntity.builder()
                .sigla(textService.isValid(sigla) ? sigla : null)
                .nomeBreve(textService.isValid(nomeBreve) ? nomeBreve : null)
                .nomeCompleto(textService.isValid(nomeCompleto) ? nomeCompleto : null)
                .regione(regione)
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
        resetBase(typeReset);
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        return resetBase(typeReset);
    }

    //    @Deprecated
    //   private RisultatoReset resetBase() {
    //        String nomePaginaWiki = "Province d'Italia";
    //        List<List<String>> mappa = webService.getWikiTable(nomePaginaWiki);
    //        String sigla;
    //        String nomeBreve;
    //        String nomeCompleto;
    //
    //        for (List<String> riga : mappa) {
    //            sigla = riga.get(2);
    //            nomeBreve = webService.getNomeDaLink(sigla);
    //            insert(newEntity(sigla, nomeBreve));
    //        }
    //
    //        return null;
    //    }

    private RisultatoReset resetBase(RisultatoReset typeReset) {
        String nomeFileCSV = "province.csv";
        String message;
        String sigla;
        String nomeBreve;
        String nomeCompleto;
        String regioneTxt;
        RegioneEntity regioneBean = null;
        ViaEntity newBean;

        Map<String, List<String>> mappaSource = resourceService.leggeMappa(nomeFileCSV);
        if (mappaSource != null && mappaSource.size() > 0) {
            for (List<String> rigaUnValore : mappaSource.values()) {
                sigla = rigaUnValore.get(0);
                nomeBreve = rigaUnValore.get(1);
                nomeCompleto = rigaUnValore.size()>2?rigaUnValore.get(2) : VUOTA;
                regioneTxt = rigaUnValore.size()>3?rigaUnValore.get(3) : VUOTA;
                regioneBean = textService.isValid(regioneTxt) ? regioneModulo.findByNome(regioneTxt):null;
                creaIfNotExists(sigla, nomeBreve, nomeCompleto, regioneBean);
            }
        }
        else {
            message = String.format("Manca il file [%s] nella directory /config o sul server", nomeFileCSV);
            logger.warn(new WrapLog().message(message).type(TypeLog.startup));
            typeReset = RisultatoReset.nonIntegrato;
        }

        return typeReset;
    }

}// end of CrudModulo class
