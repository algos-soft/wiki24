package it.algos.wiki24.backend.upload;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 12-Jan-2024
 * Time: 20:03
 */
public abstract class UploadGiorni extends Upload {

    /**
     * Costruttore base con 1 parametro (obbligatorio) <br>
     * Not annotated with @Autowired annotation, classe astratta <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public UploadGiorni(String nomeLista) {
        super(nomeLista);
    }// end of constructor not @Autowired and used

    protected void fixPreferenze() {
        super.fixPreferenze();
        this.moduloCorrente = this.giornoModulo;
    }

    protected String categorie() {
        StringBuffer buffer = new StringBuffer();
        int posCat;
        String nomeGiorno;
        String nomeCat;

//        if (isSottopagina) {
//            nomeGiorno = textService.levaCodaDaPrimo(nomeLista, SLASH);
//            nomeCat = textService.levaCodaDaUltimo(wikiTitleUpload, SLASH);
//        }
//        else {
//            nomeGiorno = nomeLista;
//            nomeCat = titoloPagina;
//        }

        nomeGiorno = nomeLista;
        nomeCat = titoloPagina;
        posCat = ((GiornoEntity)giornoModulo.findByKey(nomeGiorno)).getOrdine();

        buffer.append(CAPO);
        buffer.append("*");
        if (uploadTest) {
            buffer.append(NO_WIKI_INI);
        }
        buffer.append("[[Categoria:");
        buffer.append(type.getCategoria());
        buffer.append("|");
        buffer.append(SPAZIO);
        buffer.append(posCat);
        buffer.append("]]");
        if (uploadTest) {
            buffer.append(NO_WIKI_END);
        }

        buffer.append(CAPO);
        buffer.append("*");
        if (uploadTest) {
            buffer.append(NO_WIKI_INI);
        }
        buffer.append("[[Categoria:");
        buffer.append(nomeCat);
        buffer.append("|");
        buffer.append(SPAZIO);
        buffer.append("]]");
        if (uploadTest) {
            buffer.append(NO_WIKI_END);
        }

        return buffer.toString();
    }

}
