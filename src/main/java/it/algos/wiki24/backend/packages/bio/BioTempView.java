package it.algos.wiki24.backend.packages.bio;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.fields.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 21-Apr-2023
 * Time: 08:36
 * <p>
 * Vista iniziale e principale di un package <br>
 *
 * @Route chiamata dal menu generale <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 */
@PageTitle("Template")
@Route(value = "template", layout = MainLayout.class)
public class BioTempView extends CrudView {


    //--per eventuali metodi specifici
    @Autowired
    private BioBackend backend;

    @Autowired
    private UtilityService utilityService;


    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public BioTempView(@Autowired final BioBackend crudBackend) {
        super(crudBackend, Bio.class);
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

        super.gridPropertyNamesList = Arrays.asList(FIELD_NAME_PAGE_ID, FIELD_NAME_WIKI_TITLE, "lastServer", "lastMongo", "valido");
        super.formPropertyNamesList = Arrays.asList(FIELD_NAME_PAGE_ID, FIELD_NAME_WIKI_TITLE, "lastServer", "lastMongo", "valido", "tmplBio");
        //        backend.sortOrder = Sort.by(Sort.Direction.ASC, FIELD_NAME_WIKI_TITLE);
        super.sortOrder = backend.sortOrder;
        super.usaDataProvider = true;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();
//        addSpan(ASpan.text("Prova di colore").verde());
    }

    /**
     * Bottoni standard (solo icone) Reset, New, Edit, Delete, ecc.. <br>
     * Può essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    @Override
    protected void fixBottoniTopStandard() {
        //        super.fixBottoniTopStandard();
        this.fixBottoniTopSpecificiTemplate();
    }

    private void fixBottoniTopSpecificiTemplate() {
        Sort sort = backend.sortOrder;
        String startingProperty = sort != null ? utilityService.getFirstSortProperty(sort) : VUOTA;
        ComboBox<String> comboSort = new ComboBox<>();
        comboSort.setClearButtonVisible(true);
        comboSort.setPlaceholder("Sort...");
        comboSort.setItems(Arrays.asList(FIELD_NAME_ID_CON, FIELD_NAME_PAGE_ID, FIELD_NAME_WIKI_TITLE,"lastServer"));
        comboSort.setValue(startingProperty);
        comboSort.addValueChangeListener(event -> {
            String property = comboSort.getValue();
            property = textService.isValid(property) ? property : FIELD_NAME_ID_CON;
            backend.sortOrder = Sort.by(Sort.Direction.ASC, property);
            reload();
        });
        topPlaceHolder.add(comboSort);
    }

}// end of crud @Route view class