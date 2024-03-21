package it.algos.wiki24.backend.packages.nomi.nomemodulo;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.exception.*;
import it.algos.vbase.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.nomi.nomecategoria.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 09-Mar-2024
 * Time: 13:53
 */
@Service
public class NomeModuloModulo extends WikiModulo {

    public static final String INCIPIT_NOMI = "Incipit nomi";

    @Inject
    NomeCategoriaModulo nomeCategoriaModulo;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public NomeModuloModulo() {
        super(NomeModuloEntity.class, NomeModuloView.class, NomeModuloList.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastDownload = WPref.lastDownloadNomiModulo;
    }


    public NomeModuloEntity creaIfNotExists(String nome, String pagina) {
        if (existByKey(nome)) {
            return null;
        }
        else {
            return (NomeModuloEntity) insert(newEntity(nome, pagina));
        }
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public NomeModuloEntity newEntity() {
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
    public NomeModuloEntity newEntity(final String nome, final String pagina) {
        NomeModuloEntity newEntityBean = NomeModuloEntity.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .pagina(textService.isValid(pagina) ? pagina : null)
                .build();

        return (NomeModuloEntity) fixKey(newEntityBean);
    }

    @Override
    public List<NomeModuloEntity> findAll() {
        return super.findAll();
    }
    public List<String> findAllForKey() {
        return findAll().stream().map(bean -> bean.nome).toList();
    }

    @Override
    public NomeModuloEntity findByKey(final Object keyPropertyValue) {
        return (NomeModuloEntity) super.findByKey(keyPropertyValue);
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

//    @Override
//    public String elabora() {
//        super.elabora();
//
//        NomeModuloEntity entityBean;
//        List<AbstractEntity> lista = new ArrayList<>();
//        List<NomeModuloEntity> listaNomi;
//        String suffissoNome = SPAZIO + "(nome)";
//
//
//        //--Controllo e recupero di NomiCategoria
//        nomeCategoriaModulo.download();
//        listaNomiCategoria = nomeCategoriaModulo.findAll();
//
//        if (listaNomiCategoria != null) {
//            for (NomeCategoriaEntity nomeCategoria : listaNomiCategoria) {
//                //devo fare un doppio controllo perché alcuni nomi potrebbero già esserci e NON è un errore
//                if (existByKey(nomeCategoria.nome)) {
//                    message = String.format("Il nome [%s] esiste già", nomeCategoria.nome);
//                    logger.debug(new WrapLog().message(message));
//                }
//                else {
//                    entityBean = creaIfNotExists(nomeCategoria.nome, null);
//                }
//            }
//        }

        //        //--Controllo nomi uguali che vanno omessi nel modulo
        //        //--Sono uguali se il nome della persona coincide con la pagina di riferimento
        //        //--Sono uguali se il nome della persona coincide con la pagina di riferimento seguita dal suffisso (nome)
        //        listaNomi = findAll();
        //        for (NomeIncipit nome : listaNomi) {
        //            if (nome.linkPagina.equals(nome.nome) || nome.linkPagina.equals(nome.nome + suffissoNome)) {
        //                nome.uguale = true;
        //                save(nome);
        //            }
        //        }

        //--Controllo nomi uguali che vanno omessi nel modulo
        //--Sono uguali se il nome della persona coincide con la pagina di riferimento
//        listaNomi = findAll();
//        for (NomeIncipit nome : listaNomi) {
//            if (nome.linkPagina.equals(nome.nome)) {
//                nome.uguale = true;
//                save(nome);
//            }
//        }

//        super.fixInfoElabora();
//        return VUOTA;
//    }

}// end of CrudModulo class
