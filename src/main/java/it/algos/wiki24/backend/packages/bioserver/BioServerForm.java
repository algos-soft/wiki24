package it.algos.wiki24.backend.packages.bioserver;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.ui.form.*;
import it.algos.base24.ui.view.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.packages.parsesso.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.ui.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;

import javax.inject.*;
import java.util.function.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class BioServerForm extends WikiForm {

    @Inject
    WebService webService;

    @Inject
    WikiBotService wikiBotService;

    @Inject
    QueryService queryService;

    @Inject
    BioServerModulo bioServerModulo;

    @Inject
    WikiApiService wikiApiService;

    @Inject
    ParSessoModulo parSessoModulo;

    @Inject
    ElaboraService elaboraService;
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

        Button wikiView = new Button();
        wikiView.getElement().setAttribute("theme", "secondary");
        wikiView.getElement().setProperty("title", "Wiki: pagina in visione");
        wikiView.setIcon(new Icon(VaadinIcon.SEARCH));
        wikiView.setEnabled(true);
        wikiView.addClickListener(event -> wikiView());

        Button wikiEdit = new Button();
        wikiEdit.getElement().setAttribute("theme", "secondary");
        wikiEdit.getElement().setProperty("title", "Wiki: pagina in modifica");
        wikiEdit.setIcon(new Icon(VaadinIcon.PENCIL));
        wikiEdit.setEnabled(true);
        wikiEdit.addClickListener(event -> wikiEdit());

        Button wikiCrono = new Button();
        wikiCrono.getElement().setAttribute("theme", "secondary");
        wikiCrono.getElement().setProperty("title", "Wiki: cronistoria della pagina");
        wikiCrono.setIcon(new Icon(VaadinIcon.CALENDAR));
        wikiCrono.setEnabled(true);
        wikiCrono.addClickListener(event -> wikiCrono());

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

        buttonLayout.add(wikiView, wikiEdit, wikiCrono, elasticSpace, download, elasticSpace, annulla, registra);
        buttonLayout.getStyle().set("flex-wrap", "wrap");
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        this.getFooter().add(buttonLayout);
    }

    public void download() {
        WrapBio wrap;
        String txtValue;
        long pageId = 0;

        if (mappaFields.containsKey(FIELD_NAME_PAGE_ID) && textService.isValid(mappaFields.get(FIELD_NAME_PAGE_ID).getValue())) {
            txtValue = (String) mappaFields.get(FIELD_NAME_PAGE_ID).getValue();
            txtValue = txtValue.replaceAll("\\.", VUOTA);
            pageId = Long.valueOf(txtValue);

            if (pageId == 0) {
                Notifica.message("Manca il pageId").error().open();
                return;
            }
        }
        else {
            return;
        }

        wrap = queryService.getBio(pageId);
        if (wrap != null && wrap.isValida()) {
            currentEntityModel = wrap.getBeanBioServer();
        }

        super.binderFields();
    }

    public void wikiView() {
        BioServerEntity bioServerEntity = (BioServerEntity) currentEntityModel;
        long pageId;
        String wikiTitle;

        if (bioServerEntity != null) {
            pageId = bioServerEntity.pageId;
            wikiTitle = bioServerModulo.findByKey(pageId).wikiTitle;
            wikiApiService.openWikiPage(wikiTitle);
        }
    }

    public void wikiEdit() {
        BioServerEntity bioServerEntity = (BioServerEntity) currentEntityModel;
        long pageId;
        String wikiTitle;

        if (bioServerEntity != null) {
            pageId = bioServerEntity.pageId;
            wikiTitle = bioServerModulo.findByKey(pageId).wikiTitle;
            wikiApiService.editWikiPage(wikiTitle);
        }
    }

    public void wikiCrono() {
        BioServerEntity bioServerEntity = (BioServerEntity) currentEntityModel;
        long pageId;
        String wikiTitle;

        if (bioServerEntity != null) {
            pageId = bioServerEntity.pageId;
            wikiTitle = bioServerModulo.findByKey(pageId).wikiTitle;
            wikiApiService.cronoWikiPage(wikiTitle);
        }
    }

    public void saveHandler() {
        super.saveHandler();
        elaboraService.creaBeanMongo((BioServerEntity)currentEntityModel);
    }

}// end of CrudForm class
