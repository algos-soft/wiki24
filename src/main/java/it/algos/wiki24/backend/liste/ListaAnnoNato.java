package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.wiki24.backend.enumeration.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 08-Jan-2024
 * Time: 08:18
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ListaAnnoNato extends ListaAnni {


    /**
     * Costruttore base con 1 parametro (obbligatorio) <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(ListaAnnoNato.class, nomeLista) <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaAnnoNato(String nomeLista) {
        super(nomeLista);
    }// end of constructor not @Autowired and used


    protected void fixPreferenze() {
        super.fixPreferenze();

        super.type = TypeLista.annoNascita;
        super.titoloPagina = wikiUtilityService.wikiTitleNatiAnno(nomeLista);
    }

}
