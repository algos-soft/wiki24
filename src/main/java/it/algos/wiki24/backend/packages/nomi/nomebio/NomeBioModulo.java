package it.algos.wiki24.backend.packages.nomi.nomebio;

import com.mongodb.client.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.nomi.nomecategoria.*;
import it.algos.wiki24.backend.packages.nomi.nomedoppio.*;
import it.algos.wiki24.backend.packages.nomi.nomepagina.*;
import org.springframework.stereotype.*;

import java.util.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import javax.inject.*;

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
    NomePaginaModulo nomePaginaModulo;

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
        List<NomePaginaEntity> listaNomiPagina = null;
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
            for (NomePaginaEntity bean : listaNomiPagina) {
                numBio = bioMongoModulo.countAllByNome(bean.nome);
                doppio = listaKeyDoppio.contains(bean.nome);
                categoria = listaKeyCategoria.contains(bean.nome);
                superaSoglia = numBio > minimoVociBioPerAvereUnaPaginaLista;
                entityBean = creaIfNotExists(bean.nome, numBio, bean.pagina, doppio, categoria, true, superaSoglia);
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

}// end of CrudModulo class
