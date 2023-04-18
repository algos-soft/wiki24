package it.algos.wiki24.backend.packages.parametro;

import com.vaadin.flow.router.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 09-Mar-2023
 * Time: 07:38
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("Parametro")
@Route(value = "parametro", layout = MainLayout.class)
public class ParametroView extends CrudView {


    //--per eventuali metodi specifici
    private ParametroBackend backend;

    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public ParametroView(@Autowired final ParametroBackend crudBackend) {
        super(crudBackend, Parametro.class);
        this.backend = crudBackend;
    }

    /**
     * Preferenze usate da questa 'view' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.gridPropertyNamesList = Arrays.asList("pageId","titolo"); // controllare la congruità con la Entity
//        super.formPropertyNamesList = Arrays.asList("pageId","titolo"); // controllare la congruità con la Entity
        super.riordinaColonne = true; //se rimane true, uguale al default, si può cancellare
//        super.usaBottoneRefresh = false; //se rimane false, uguale al default, si può cancellare
        super.usaBottoneReset = false; //se rimane false, uguale al default, si può cancellare
        super.usaBottoneNew = true; //se rimane true, uguale al default, si può cancellare
        super.usaBottoneEdit = true; //se rimane true, uguale al default, si può cancellare
        super.usaBottoneDeleteAll = true; //se rimane true, uguale al default, si può cancellare
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();
        addSpan(ASpan.text("Prova di colore").verde());
    }

}// end of crud @Route view class