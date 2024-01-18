package it.algos.base24.ui.form;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.dialog.*;
import com.vaadin.flow.component.formlayout.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.converter.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.ui.view.*;
import jakarta.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import java.util.*;
import java.util.function.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Thu, 03-Aug-2023
 * Time: 07:17
 */

public abstract class CrudForm extends Dialog {

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    @Autowired
    public TextService textService;

    @Autowired
    public AnnotationService annotationService;

    @Autowired
    public LogService logger;

    @Autowired
    public FieldService fieldService;

    protected AbstractEntity currentEntityModel;

    protected CrudModulo currentCrudModulo;

    protected int numResponsiveStepColumn;

    /**
     * Corpo centrale del Form <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected final FormLayout formLayout = new FormLayout();

    //--collegamento tra i fields e la entityBean
    protected BeanValidationBinder<AbstractEntity> binder;

    protected List<String> propertyFormNames;

    protected CrudOperation crudOperation = CrudOperation.update;

    protected boolean formValido = false;


    protected Runnable annullaHandler;

    protected Consumer<Boolean> deleteHandler;

    protected BiConsumer<AbstractEntity, CrudOperation> saveHandler;

    protected Map<String, AbstractField> mappaFields;

    public CrudForm() {
    }


    public CrudForm(CrudModulo crudModulo, AbstractEntity entityBean, CrudOperation operation) {
        this.currentCrudModulo = crudModulo;
        this.currentEntityModel = entityBean;
        this.crudOperation = operation;
    }


    @PostConstruct
    public void postConstruct() {
        this.fixPreferenze();
        this.fixView();
    }


    /**
     * Fluent pattern Builder <br>
     */
    public CrudForm annullaHandler(Runnable annullaHandler) {
        this.annullaHandler = annullaHandler;
        return this;
    }


    /**
     * Fluent pattern Builder <br>
     */
    public CrudForm deleteHandler(Consumer<Boolean> deleteHandler) {
        this.deleteHandler = deleteHandler;
        return this;
    }


    /**
     * Fluent pattern Builder <br>
     */
    public CrudForm saveHandler(BiConsumer<AbstractEntity, CrudOperation> saveHandler) {
        this.saveHandler = saveHandler;
        return this;
    }


    /**
     * Fluent pattern Builder <br>
     */
    public CrudForm fields(List<String> propertyFormNames) {
        this.propertyFormNames = propertyFormNames;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public CrudForm colonne(int numColonne) {
        this.numResponsiveStepColumn = numColonne;
        return this;
    }


    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Sono disponibili tutte le istanze @Autowired <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.propertyFormNames = currentCrudModulo.getFormPropertyNames();
        this.numResponsiveStepColumn = 1;
    }

    private void fixView() {
        //        if (!formValido) {
        //            logService.warn("Form non valido. Mancano alcune regolazioni. Vedi GenericForm.fixView().");
        //            return null;
        //        }
        if (currentEntityModel == null && crudOperation == CrudOperation.add) {
            currentEntityModel = currentCrudModulo.newEntity();
        }

        this.fixFormLayout();

        this.fixHeader();

        this.fixBody();

        this.fixBottom();

        this.open();
    }

    /**
     * Body placeholder per i campi <br>
     * Normalmente colonna singola <br>
     */
    protected void fixFormLayout() {
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", numResponsiveStepColumn));
        //        formLayout.setResponsiveSteps(
        //                // Use one column by default
        //                new FormLayout.ResponsiveStep("0", numResponsiveStepColumn),
        //                // Use two columns, if layout's width exceeds 500px
        //                new FormLayout.ResponsiveStep("500px", 2)
        //        );

        addClassName("no-padding");
    }

    /**
     * Titolo del dialogo <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected void fixHeader() {
        String message;
        String tag;
        String collectionName = VUOTA;

        tag = switch (crudOperation) {
            case shows -> FORM_TAG_SHOW;
            case add -> FORM_TAG_NEW;
            case update, delete -> FORM_TAG_EDIT;
        };

        // @todo ATTENTION QUI
        //        collectionName = annotationService.getCollectionName(currentCrudModulo.getCurrentCrudModelClazz());
        message = String.format("%s%s%s", tag, SPAZIO, collectionName);
        this.setHeaderTitle(message);
    }


    protected void fixBody() {
        //--crea i fields come AbstractField components
        this.creaFields();

        //--crea e regola un binder per questo Dialog e questa entityBean (currentItem)
        this.binderFields();

        //--valori dal modello alla GUI degli eventuali fields extra binder
        this.valueModelToPresentationFields();

        //--aggiunge i componenti grafici al layout
        this.addFields();
    }

    /**
     * Crea i fields come AbstractField components
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void creaFields() {
        mappaFields = fieldService.creaAll(currentEntityModel.getClass(), propertyFormNames);
    }

    /**
     * Crea i fields come AbstractField components
     * Updates the value in each bound field component
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void binderFields() {
        String key2;
        AbstractField field;
        TypeField type;

        //--crea e regola un nuovo binder (vuoto) per questo Dialog e questa entityBean (currentItem)
        binder = new BeanValidationBinder(currentEntityModel.getClass());

        //--aggiunge i valori dei fields al binder
        for (String key : mappaFields.keySet()) {
            field = mappaFields.get(key);
            type = annotationService.getType(currentEntityModel.getClass(), key);
            key2 = field.getElement().getAttribute(KEY_TAG_PROPERTY_KEY);
            if (textService.isValid(key2)) {
                if (type == TypeField.lungo) {
                    String message = String.format("%s deve contenere solo caratteri numerici", key);
                    StringToLongConverter longConverter = new StringToLongConverter(0L, message);
                    binder.forField(field)
                            .withConverter(longConverter)
                            .bind(key);
                }
                else {
                    binder.forField(field).bind(key2);
                }
            }
        }

        try {
            binder.bindInstanceFields(this);
        } catch (Exception unErrore) {
            //            logger.error(new WrapLog().exception(unErrore).usaDb());
            logger.error(unErrore.getMessage());
        }

        // Updates the value in each bound field component
        try {
            binder.readBean(currentEntityModel);
        } catch (Exception unErrore) {
            //            logger.error(new WrapLog().exception(unErrore).usaDb());
            logger.error(unErrore.getMessage());
        }
    }

    /**
     * Valori dal modello alla GUI degli eventuali fields extra binder <br>
     * Chiamato da fixBody() alla creazione del Form <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void valueModelToPresentationFields() {
    }

    /**
     * Valori dagli eventuali fields extra binder della GUI al modello dati <br>
     * Chiamato da saveHandler() alla registrazione delle modifiche <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void valuePresentationFieldsToModel() {
    }

    /**
     * Aggiunge i componenti grafici al layout
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addFields() {
        for (String key : mappaFields.keySet()) {
            formLayout.add(mappaFields.get(key));
        }

        this.add(formLayout);

        for (String key : mappaFields.keySet()) {
            if (mappaFields.get(key) instanceof TextArea testoAreaField) {
                formLayout.setColspan(testoAreaField, numResponsiveStepColumn);
            }
        }
    }

    /**
     * Barra dei bottoni <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected void fixBottom() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("buttons");
        buttonLayout.setPadding(false);
        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(false);
        buttonLayout.setClassName("confirm-dialog-buttons");

        Div elasticSpace = new Div();
        elasticSpace.getStyle().set("flex-grow", "1");

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
        if (Pref.usaShortcut.is()) {
            registra.addClickShortcut(Key.ENTER);
        }
        registra.addClickListener(e -> saveHandler());

        switch (crudOperation) {
            case shows -> buttonLayout.add(annulla);
            case add -> buttonLayout.add(annulla, registra);
            case update, delete -> buttonLayout.add(delete, elasticSpace, annulla, registra);
        } ;

        buttonLayout.getStyle().set("flex-wrap", "wrap");
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        this.getFooter().add(buttonLayout);
    }

    public void sincroValueToModel() {
    }

    /**
     * Annulla l'operazione <br>
     * Rimanda al chiamante (se esiste) per eventuali altre incombenze (tipo reload della view) <br>
     */
    public void annullaHandler() {
        switch (crudOperation) {
            case add -> Notifica.message("Non registrato").primary().open();
            case shows -> Notifica.message("Letto").success().open();
            case update -> Notifica.message("Non modificato").primary().open();
            case delete -> Notifica.message("Non cancellato").primary().open();
            default -> Notifica.message("Caso non previsto").error().open();
        }

        if (annullaHandler != null) {
            annullaHandler.run();
        }
        close();
    }

    /**
     * La cancellazione avviene nel modulo <br>
     * Delega il service di xxxService <br>
     * Poi rimanda al chiamante (se esiste) per eventuali altre incombenze (tipo reload della view) <br>
     */
    public void deleteHandler() {
        boolean cancellato = currentCrudModulo.delete(currentEntityModel);

        if (deleteHandler != null) {
            deleteHandler.accept(cancellato);
        }
        close();
    }


    /**
     * Controlla che il binder sia valido e registrabile <br>
     * Trasferisce i valori dalla GUI al binder e da questo al currentEntityModel <br>
     * La registrazione avviene nel modulo <br>
     * Delega il service di xxxService <br>
     * Poi rimanda al chiamante (se esiste) per eventuali altre incombenze (tipo reload della view) <br>
     */
    public void saveHandler() {
        try {
            if (binder.writeBeanIfValid(currentEntityModel)) {
                binder.writeBean(currentEntityModel);
            }
            else {
                //                logger.info(new WrapLog().exception(new AlgosException("binder non valido")));
                logger.warn("binder non valido");
                return;
            }
        } catch (ValidationException error) {
            logger.error(error.getMessage());
            return;
        }

        //--Valori dagli eventuali fields extra binder della GUI al modello dati <br>
        this.valuePresentationFieldsToModel();

        switch (crudOperation) {
            case shows:
                logger.warn("Registrazione di una scheda in sola lettura");
                break;
            case add:
                currentEntityModel = currentCrudModulo.insert(currentEntityModel);
                break;
            case update:
                currentEntityModel = currentCrudModulo.save(currentEntityModel);
                break;
            case delete:
                logger.warn("Registrazione di una scheda in cancellazione");
                break;
        }

        if (saveHandler != null) {
            saveHandler.accept(currentEntityModel, crudOperation);
        }
        close();
    }


}
