package it.algos.wiki24.backend.upload;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.packages.crono.anno.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 12-Jan-2024
 * Time: 19:58
 */
public abstract class UploadAnni extends Upload {

    /**
     * Costruttore base con 1 parametro (obbligatorio) <br>
     * Not annotated with @Autowired annotation, classe astratta <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public UploadAnni(String nomeLista) {
        super(nomeLista);
    }// end of constructor not @Autowired and used

    protected void fixPreferenze() {
        super.fixPreferenze();
        this.moduloCorrente = this.annoModulo;
    }


    protected String categorie() {
        StringBuffer buffer = new StringBuffer();
//        String nomeAnno = isSottopagina ? textService.levaCodaDaUltimo(nomeLista, SLASH) : nomeLista;
        String nomeAnno;
        AnnoEntity anno ;
        int posCat ;

        String secolo ;
        String nomeCat;

//        if (isSottopagina) {
//            nomeCat = textService.levaCodaDaUltimo(wikiTitleUpload, SLASH);
//            posCat += ordineCategoriaSottopagina;
//        }
//        else {
//            nomeCat = titoloPagina;
//        }
        nomeAnno = nomeLista;
        anno = (AnnoEntity)annoModulo.findByKey(nomeAnno);
        secolo = anno.getSecolo().nome;
        nomeCat = titoloPagina;
        posCat = anno.getOrdine();

        buffer.append(CAPO);
        buffer.append("*");
        if (uploadTest) {
            buffer.append(NO_WIKI_INI);
        }
        buffer.append("[[Categoria:");
        buffer.append(type.getCategoria());
        buffer.append(secolo);
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

    protected String interprogetto() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(CAPO);
        buffer.append("{{subst:#if:{{subst:#invoke:string|match|{{subst:#invoke:interprogetto|interprogetto}}|template vuoto|nomatch=}}||== Altri progetti ==\n" +
                "{{interprogetto}}}}");

        return buffer.toString();
    }

}
