package it.algos.wiki24.backend.packages.nomi.nomecategoria;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.login.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

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
     */
    public NomeCategoriaModulo() {
        super(NomeCategoriaEntity.class, NomeCategoriaView.class, NomeCategoriaList.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastDownload = WPref.lastDownloadNomiCategoria;
    }

    public NomeCategoriaEntity creaIfNotExists(final String nome, final TypeGenere typeGenere) {
        if (textService.isEmpty(nome)) {
            return null;
        }

        if (existByKey(nome)) {
            return null;
        }

        return (NomeCategoriaEntity) insert(newEntity(nome, typeGenere));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public NomeCategoriaEntity newEntity() {
        return newEntity(VUOTA, null);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param nome (obbligatorio)
     * @param type (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NomeCategoriaEntity newEntity(final String nome, final TypeGenere type) {
        NomeCategoriaEntity newEntityBean = NomeCategoriaEntity.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .type(type != null ? type : TypeGenere.nessuno)
                .build();

        return (NomeCategoriaEntity) fixKey(newEntityBean);
    }

    @Override
    public List<NomeCategoriaEntity> findAll() {
        return super.findAll();
    }

    public List<String> findAllForKey() {
        return findAll().stream().map(bean -> bean.nome).toList();
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

        inizio = System.currentTimeMillis();
        if (botLogin != null && botLogin.isValido() && botLogin.isBot()) {
            downloadCategoria(CAT_MASCHILE, TypeGenere.maschile);
            downloadCategoria(CAT_FEMMINILE, TypeGenere.femminile);
            downloadCategoria(CAT_ENTRAMBI, TypeGenere.entrambi);
        }

        super.fixDownload(inizio);
    }

    /**
     * Legge i valori dalla Categoria:Prenomi italiani maschili
     *
     * @return entities create
     */
    public void downloadCategoria(String wikiCatTitle, TypeGenere typeGenere) {
        List<String> listaNomi = queryService.getTitles(wikiCatTitle);

        if (listaNomi != null) {
            for (String nome : listaNomi) {
//                if (nome.contains(PARENTESI_TONDA_INI)) {
//                    nome = textService.levaCodaDaPrimo(nome, PARENTESI_TONDA_INI);
//                }
                creaIfNotExists(nome, typeGenere);
            }
        }
    }


}// end of CrudModulo class
