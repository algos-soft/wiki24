package it.algos.wiki24.backend.packages.nomi.nomebio;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.service.*;
import it.algos.vbase.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.FIELD_NAME_NOME;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.nomi.nomecategoria.*;
import it.algos.wiki24.backend.packages.nomi.nomedoppio.*;
import it.algos.wiki24.backend.packages.nomi.nomemodulo.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 11-Mar-2024
 * Time: 15:03
 */
@Service
public class NomeBioModulo extends WikiModulo {

    @Inject
    NomeDoppioModulo nomeDoppioModulo;

    @Inject
    MongoService mongoService;

    @Inject
    NomeModuloModulo nomeModuloModulo;

    @Inject
    NomeCategoriaModulo nomeCategoriaModulo;

    @Inject
    BioMongoModulo bioMongoModulo;

    @Inject
    QueryService queryService;

    @Inject
    WikiUtilityService wikiUtilityService;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public NomeBioModulo() {
        super(NomeBioEntity.class, NomeBioView.class, NomeBioList.class, NomeBioForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.scheduledDownload = null;

        super.flagElabora = WPref.usaTaskNomi;
        super.flagUpload = WPref.usaTaskNomi;

        super.lastDownload = null;
        super.durataDownload = null;
        super.unitaMisuraDownload = null;

        super.lastElabora = WPref.lastElaboraNomi;
        super.durataElabora = WPref.elaboraNomiTime;
        super.unitaMisuraElabora = TypeDurata.minuti;

        super.lastUpload = WPref.lastUploadNomi;
        super.durataUpload = WPref.uploadNomiTime;
        super.unitaMisuraUpload = TypeDurata.minuti;
    }

    /**
     * Regola le property visibili in una lista CrudList <br>
     * Di default prende tutti i fields della ModelClazz specifica <br>
     * Può essere sovrascritto SENZA richiamare il metodo della superclasse <br>
     */
    public List<String> getListPropertyNames() {
        return Arrays.asList("nome", "numBio", "pagina", "lista", "doppio", "mongo", "superaSoglia", "esisteLista");
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public NomeBioEntity newEntity() {
        return newEntity(VUOTA, 0, VUOTA, VUOTA, false, false, false);
    }


    public NomeBioEntity newEntity(String nome) {
        return newEntity(nome, 0, VUOTA, VUOTA, false, false, false);
    }

    public NomeBioEntity newEntity(String nome, boolean doppio) {
        return newEntity(nome, 0, VUOTA, VUOTA, doppio, false, false);
    }

    public NomeBioEntity newEntity(String nome, String pagina) {
        return newEntity(nome, 0, pagina, VUOTA, false, false, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param nome   (obbligatorio)
     * @param numBio (facoltativa)
     * @param pagina (facoltativa)
     * @param lista  (facoltativa)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NomeBioEntity newEntity(String nome, int numBio, String pagina, String lista, boolean doppio, boolean superaSoglia, boolean esisteLista) {
        NomeBioEntity newEntityBean = NomeBioEntity.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .numBio(numBio)
                .pagina(textService.isValid(pagina) ? pagina : null)
                .lista(textService.isValid(lista) ? lista : null)
                .doppio(doppio)
                .superaSoglia(superaSoglia)
                .esisteLista(esisteLista)
                .build();

        return (NomeBioEntity) fixKey(newEntityBean);
    }

    @Override
    public List<NomeBioEntity> findAll() {
        return super.findAll();
    }

    public List<NomeBioEntity> findAllSuperaSoglia() {
        return findAll().stream().filter(bean -> bean.superaSoglia).toList();
    }

    @Override
    public NomeBioEntity findByKey(final Object keyPropertyValue) {
        return (NomeBioEntity) super.findByKey(keyPropertyValue);
    }


    /**
     * Elaborazione della tavola <br>
     * <p>
     * Cancellazione della Collection <br>
     * Download di NomeDoppio <br>
     * Download di NomeCategoria <br>
     * Download ed elaborazione di NomePagina <br>
     * Creazione delle nuove entities <br>
     * Calcolo delle occorrenze di numBio per ogni Nome <br>
     * (eventuale) Recupero dei Nomi Distinct presenti in BioMongo alla property [nome] <br>
     */
    @Override
    public String elabora() {
        List<NomeModuloEntity> listaNomiBio = null;
        List<NomeDoppioEntity> listaNomiDoppi = null;
        List<NomeModuloEntity> listaNomiModuli = null;
        List<NomeCategoriaEntity> listaNomiCategoria = null;
        NomeBioEntity newBean;
        String nomeCategoria;
        boolean categoria;
        boolean doppio;
        boolean superaSoglia;
        int soglia = WPref.sogliaPaginaNomi.getInt();

        super.elabora();

        // Cancellazione della Collection
        super.deleteAll();

        // Download di NomeDoppio <br>
        nomeDoppioModulo.download();
        listaNomiDoppi = nomeDoppioModulo.findAll();
        if (listaNomiDoppi != null && listaNomiDoppi.size() > 0) {
            for (NomeDoppioEntity bean : listaNomiDoppi) {
                newBean = newEntity(bean.nome, true);
                if (!mappaBeans.containsKey(bean.nome)) {
                    mappaBeans.put(bean.nome, newBean);
                }
            }
        }
        else {
            logger.warn(new WrapLog().message("Mancano i nomi doppi"));
        }

        // Download di NomeModulo <br>
        nomeModuloModulo.download();
        listaNomiModuli = nomeModuloModulo.findAll();
        if (listaNomiModuli != null && listaNomiModuli.size() > 0) {
            for (NomeModuloEntity bean : listaNomiModuli) {
                newBean = newEntity(bean.nome, bean.pagina);
                if (!mappaBeans.containsKey(bean.nome)) {
                    mappaBeans.put(bean.nome, newBean);
                }
            }
        }
        else {
            logger.warn(new WrapLog().message("Mancano i nomi modulo (incipit)"));
        }

        // Download di NomeCategoria <br>
        nomeCategoriaModulo.download();
        listaNomiCategoria = nomeCategoriaModulo.findAll();
        if (listaNomiCategoria != null && listaNomiCategoria.size() > 0) {
            for (NomeCategoriaEntity bean : listaNomiCategoria) {
                nomeCategoria = bean.nome;
                if (nomeCategoria.contains(PARENTESI_TONDA_INI)) {
                    nomeCategoria = textService.levaCodaDaPrimo(nomeCategoria, PARENTESI_TONDA_INI);
                    nomeCategoria = nomeCategoria.trim();
                }
                if (!mappaBeans.containsKey(nomeCategoria)) {
                    newBean = newEntity(nomeCategoria, bean.nome);
                    mappaBeans.put(bean.nome, newBean);
                }
            }
        }
        else {
            logger.warn(new WrapLog().message("Mancano i nomi delle categorie"));
        }

        //        DistinctIterable<String> listaNomiMongo = mongoService.getCollection("biomongo").distinct("nome", String.class);
        //        for (String nome : listaNomiMongo) {
        //            numBio = bioMongoModulo.countAllByNome(nome);
        //            creaIfNotExists(nome, numBio);
        //        }
        //        int a = 87;

        //        mappaBeans.values().stream().toList().subList(150, 200).stream().forEach(bean -> checkElabora((NomeBioEntity) bean, soglia));
        ;
        mappaBeans.values().stream().forEach(bean -> checkElabora((NomeBioEntity) bean, soglia));

        super.fixInfoElabora();
        return VUOTA;
    }

    public void checkElabora(NomeBioEntity newBean, int soglia) {
        newBean.numBio = bioMongoModulo.countAllByNome(newBean.nome);
        newBean.superaSoglia = newBean.numBio > soglia;
        newBean.lista = newBean.nome;
        newBean.esisteLista = queryService.isEsiste(wikiUtilityService.wikiTitleNomi(newBean.lista));
        if (newBean.esisteLista) {
            if (newBean.superaSoglia) {
                newBean.listaTypeAnchor = TypeAnchor.esisteMantenere;
            }
            else {
                newBean.listaTypeAnchor = TypeAnchor.esisteCancellare;
            }
        }
        else {
            if (newBean.superaSoglia) {
                newBean.listaTypeAnchor = TypeAnchor.mancaCreare;
            }
            else {
                newBean.listaTypeAnchor = TypeAnchor.mancaNonPrevisto;
            }
        }
        insert(newBean);
    }


    @Override
    public String uploadAll() {
        inizio = System.currentTimeMillis();

        message = String.format("Inizio del ciclo di upload di tutti nomi (che superano la soglia prevista)");
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.upload));
        logger.info(new WrapLog().message(message).type(TypeLog.upload));

        for (NomeBioEntity nomBean : findAllSuperaSoglia()) {
            uploadPagina(nomBean);
        }

        message = String.format("Fine del ciclo di upload di tutti nomi (che superano la soglia prevista)");
        logger.info(new WrapLog().message(message).type(TypeLog.upload));
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.upload));

        return super.fixUpload(inizio);
    }


    @Override
    public void testPagina(AbstractEntity nome) {
        uploadService.nomeTest((NomeBioEntity) nome);
    }


    @Override
    public void uploadPagina(AbstractEntity nome) {
        uploadService.nome((NomeBioEntity) nome);
    }

}// end of CrudModulo class
