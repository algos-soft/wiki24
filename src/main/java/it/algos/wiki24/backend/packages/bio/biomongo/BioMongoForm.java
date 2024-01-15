package it.algos.wiki24.backend.packages.bio.biomongo;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.ui.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;

import javax.inject.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class BioMongoForm extends WikiForm {

    @Inject
    ElaboraService elaboraService;

    public BioMongoForm(BioMongoModulo crudModulo, BioMongoEntity entityBean, CrudOperation operation) {
        super(crudModulo, entityBean, operation);
    }


    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Sono disponibili tutte le istanze @Autowired <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        super.fixPreferenze();
        super.numResponsiveStepColumn = 2;
    }

    @Override
    protected void fixBottom() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("buttons");
        buttonLayout.setPadding(false);
        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(false);
        buttonLayout.setClassName("confirm-dialog-buttons");

        Div elasticSpace = new Div();
        elasticSpace.getStyle().set("flex-grow", "1");

        Button resetEntity = new Button(BUTTON_RESET);
        resetEntity.getElement().setAttribute("theme", "primary");
        resetEntity.addThemeVariants(ButtonVariant.LUMO_ERROR);
        resetEntity.getElement().setProperty("title", "Reset: elabora la entity");
        resetEntity.setIcon(new Icon(VaadinIcon.REFRESH));
        resetEntity.setEnabled(true);
        resetEntity.addClickListener(event -> resetEntity());

        Button delete = new Button(BUTTON_DELETE);
        delete.setIcon(new Icon(VaadinIcon.TRASH));
        delete.addClickListener(e -> deleteHandler());
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.getStyle().set("margin-left", "auto");
        delete.getStyle().set("margin-inline-end", "auto");

        Button annulla = new Button(BUTTON_CANCELLA);
        annulla.setIcon(new Icon(VaadinIcon.ARROW_LEFT));
        annulla.addClickListener(e -> annullaHandler());

        Button registra = new Button(BUTTON_REGISTRA);
        registra.setIcon(new Icon(VaadinIcon.CHECK));
        registra.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registra.addClickListener(e -> saveHandler());

        buttonLayout.add(resetEntity, elasticSpace, annulla, registra);
        buttonLayout.getStyle().set("flex-wrap", "wrap");
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        this.getFooter().add(buttonLayout);
    }


    public AbstractEntity resetEntity() {
        BioServerEntity bioServerEntity = ((BioMongoModulo) currentCrudModulo).getBioServer(currentEntityModel);
        currentEntityModel = elaboraService.creaModificaBeanMongo(bioServerEntity);
        super.binderFields();

        return currentEntityModel;
    }

}// end of CrudForm class

