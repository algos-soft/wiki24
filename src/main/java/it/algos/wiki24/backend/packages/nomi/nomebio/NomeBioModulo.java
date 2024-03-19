package it.algos.wiki24.backend.packages.nomi.nomebio;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.nomi.nomecategoria.*;
import it.algos.wiki24.backend.packages.nomi.nomedoppio.*;
import it.algos.wiki24.backend.packages.nomi.nomemodulo.*;
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
    NomeCategoriaModulo nomeCategoriaModulo;

    @Inject
    NomeModuloModulo nomePaginaModulo;

    @Inject
    BioMongoModulo bioMongoModulo;

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


    public NomeBioEntity creaIfNotExists(String nome, int numBio, String pagina, boolean doppio, boolean categoria, boolean incipit, boolean superaSoglia) {
        if (existByKey(nome)) {
            return null;
        }
        else {
            return (NomeBioEntity) insert(newEntity(nome, numBio, pagina, doppio, categoria, incipit, superaSoglia));
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public NomeBioEntity newEntity() {
        return newEntity(VUOTA, 0, VUOTA, VUOTA, false, false, false, false);
    }


    public NomeBioEntity newEntity(String nome, int numBio, String pagina, boolean doppio, boolean categoria, boolean incipit, boolean superaSoglia) {
        return newEntity(nome, numBio, pagina, VUOTA, doppio, categoria, incipit, superaSoglia);
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
    public NomeBioEntity newEntity(String nome, int numBio, String pagina, String lista, boolean doppio, boolean categoria, boolean incipit, boolean superaSoglia) {
        NomeBioEntity newEntityBean = NomeBioEntity.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .numBio(numBio)
                .pagina(textService.isValid(pagina) ? pagina : null)
                .lista(textService.isValid(lista) ? lista : null)
                .doppio(doppio)
                .categoria(categoria)
                .incipit(incipit)
                .superaSoglia(superaSoglia)
                .build();

        return (NomeBioEntity) fixKey(newEntityBean);
    }

    @Override
    public List<NomeBioEntity> findAll() {
        return super.findAll();
    }
    public List<NomeBioEntity> findAllSuperaSoglia() {
        return findAll().stream().filter(bean->bean.superaSoglia).toList();
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
        List<NomeModuloEntity> listaNomiPagina = null;
        List<String> listaKeyDoppio = null;
        List<String> listaKeyCategoria = null;
        int numBio = 0;
        NomeBioEntity entityBean;
        boolean categoria;
        boolean doppio;
        boolean superaSoglia;
        int minimoVociBioPerAvereUnaPaginaLista = WPref.sogliaPaginaNomi.getInt();

        super.elabora();

        // Cancellazione della Collection
        super.deleteAll();

        // Download di NomeDoppio <br>
        nomeDoppioModulo.download();
        listaKeyDoppio = nomeDoppioModulo.findAllForKey();
        if (listaKeyDoppio == null || listaKeyDoppio.size() == 0) {
            logger.warn(new WrapLog().message("Mancano i nomi doppi"));
        }

        // Download di NomeCategoria <br>
        nomeCategoriaModulo.download();
        listaKeyCategoria = nomeCategoriaModulo.findAllForKey();
        if (listaKeyCategoria == null || listaKeyCategoria.size() == 0) {
            logger.warn(new WrapLog().message("Mancano i nomi delle categorie"));
        }

        // Download ed elaborazione di NomePagina <br>
        nomePaginaModulo.download();
        nomePaginaModulo.elabora();

        listaNomiPagina = nomePaginaModulo.findAll();
        if (listaNomiPagina != null && listaNomiPagina.size() > 0) {
            for (NomeModuloEntity bean : listaNomiPagina) {
                numBio = bioMongoModulo.countAllByNome(bean.nome);
                doppio = listaKeyDoppio.contains(bean.nome);
                categoria = listaKeyCategoria.contains(bean.nome);
                superaSoglia = numBio > minimoVociBioPerAvereUnaPaginaLista;
                entityBean = creaIfNotExists(bean.nome, numBio, bean.pagina, doppio, categoria, true, superaSoglia);
            }
        }

        if (listaKeyDoppio != null && listaKeyDoppio.size() > 0) {
            for (String nome : listaKeyDoppio) {
                numBio = bioMongoModulo.countAllByNome(nome);
                superaSoglia = numBio > minimoVociBioPerAvereUnaPaginaLista;
                entityBean = creaIfNotExists(nome, numBio, VUOTA, true, false, true, superaSoglia);
            }
        }

        //        DistinctIterable<String> listaNomiMongo = mongoService.getCollection("biomongo").distinct("nome", String.class);
        //        for (String nome : listaNomiMongo) {
        //            numBio = bioMongoModulo.countAllByNome(nome);
        //            creaIfNotExists(nome, numBio);
        //        }
        //        int a = 87;

        super.fixInfoElabora();
        return VUOTA;
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
