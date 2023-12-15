package it.algos.wiki24.backend.packages.bioserver;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.ui.form.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.ui.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;

import javax.inject.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class BioServerForm extends WikiForm {

    @Inject
    WebService webService;

    @Inject
    WikiBotService wikiBotService;

    public BioServerForm(BioServerModulo crudModulo, BioServerEntity entityBean, CrudOperation operation) {
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

        Button download = new Button("Download");
        download.getElement().setAttribute("theme", "primary");
        download.addThemeVariants(ButtonVariant.LUMO_ERROR);
        download.getElement().setProperty("title", "Download: ricarica WikiTitle dal server");
        download.setIcon(new Icon(VaadinIcon.DOWNLOAD));
        download.addClickListener(event -> this.download());

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

        buttonLayout.add(download, elasticSpace, annulla, registra);
        buttonLayout.getStyle().set("flex-wrap", "wrap");
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        this.getFooter().add(buttonLayout);
    }

    public void download() {
        String wikiTitle="Matteo Renzi";
        String tmplBio = wikiBotService.getTmplBio(wikiTitle);
        ((BioServerEntity)currentEntityModel).wikiTitle=wikiTitle;
        ((BioServerEntity)currentEntityModel).pageId=234567;
        ((BioServerEntity)currentEntityModel).tmplBio=tmplBio;

        super.binderFields();
    }


}// end of CrudForm class
