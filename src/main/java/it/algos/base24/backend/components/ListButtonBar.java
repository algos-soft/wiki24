package it.algos.base24.backend.components;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Sat, 02-Sep-2023
 * Time: 15:23
 */
@Component
@Scope(value = SCOPE_PROTOTYPE)
public class ListButtonBar extends HorizontalLayout {

    @Autowired
    AnnotationService annotationService;

   protected CrudList currentCrudList;

    protected  CrudModulo currentCrudModulo;

    protected  Class currentCrudModelClazz;

    private boolean usaBottoneDeleteAll;

    private boolean usaBottoneResetDelete;

    private boolean usaBottoneResetAdd;

    private boolean usaBottoneNew;

    private boolean usaBottoneEdit;

    private boolean usaBottoneShows;

    private boolean usaBottoneDeleteEntity;

    private boolean usaBottoneExport;

    private boolean usaBottoneSearch;

    private Button buttonDeleteAll = new Button();

    private Button buttonResetDelete = new Button();

    private Button buttonResetAdd = new Button();


    private Button buttonNew = new Button();

    private Button buttonEdit = new Button();

    private Button buttonShow = new Button();

    private Button buttonDeleteEntity = new Button();

    private Anchor downloadAnchor;

    private TextField searchField = new TextField();

    private String searchFieldName;

    public ListButtonBar(final CrudList crudList) {
        this.currentCrudList = crudList;
        this.currentCrudModulo = currentCrudList.currentCrudModulo;
        this.currentCrudModelClazz = currentCrudModulo.currentCrudEntityClazz;
    }


    /**
     * Fluent pattern Builder <br>
     */
    public ListButtonBar deleteAll() {
        this.usaBottoneDeleteAll = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public ListButtonBar resetDelete() {
        this.usaBottoneResetDelete = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public ListButtonBar resetAdd() {
        this.usaBottoneResetAdd = true;
        return this;
    }


    /**
     * Fluent pattern Builder <br>
     */
    public ListButtonBar add() {
        this.usaBottoneNew = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public ListButtonBar edit() {
        this.usaBottoneEdit = true;
        this.usaBottoneShows = false;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public ListButtonBar shows() {
        this.usaBottoneShows = true;
        this.usaBottoneEdit = false;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public ListButtonBar deleteEntity() {
        this.usaBottoneDeleteEntity = true;
        return this;
    }


    /**
     * Fluent pattern Builder <br>
     */
    public ListButtonBar searchField(String searchFieldName) {
        this.searchFieldName = searchFieldName;
        this.usaBottoneSearch = true;
        return this;
    }


    /**
     * Fluent pattern Builder <br>
     */
    public ListButtonBar build() {
        addButtons();
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public ListButtonBar export(Anchor downloadAnchor) {
        this.usaBottoneExport = true;
        this.downloadAnchor = downloadAnchor;
        downloadAnchor.getElement().setProperty("title", "Export: esporta gli elementi della lista filtrati e ordinati");
        this.add(downloadAnchor);
        return this;
    }

    protected void addButtons() {

        if (usaBottoneDeleteAll) {
            this.addDeleteAll();
        }
        if (usaBottoneResetDelete) {
            this.addResetDelete();
        }
        if (usaBottoneResetAdd) {
            this.addResetAdd();
        }
        if (usaBottoneNew) {
            this.addNew();
        }
        if (usaBottoneEdit) {
            this.addEdit();
        }
        if (usaBottoneShows) {
            this.addShows();
        }
        if (usaBottoneDeleteEntity) {
            this.addDeleteEntity();
        }
        if (usaBottoneSearch) {
            this.addSearchField();
        }
    }

    private void addDeleteAll() {
        buttonDeleteAll.getElement().setAttribute("theme", "primary");
        buttonDeleteAll.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonDeleteAll.getElement().setProperty("title", "Delete: cancella completamente tutta la collezione");
        buttonDeleteAll.setIcon(new Icon(VaadinIcon.TRASH));
        buttonDeleteAll.addClickListener(event -> currentCrudList.deleteAll());
        this.add(buttonDeleteAll);
    }

    private void addResetDelete() {
        buttonResetDelete.getElement().setAttribute("theme", "primary");
        buttonResetDelete.getElement().setProperty("title", "Reset: cancella completamente la collection e la ricrea.");
        buttonResetDelete.setIcon(new Icon(VaadinIcon.REFRESH));
        buttonResetDelete.addClickListener(event -> currentCrudList.resetDelete());
        this.add(buttonResetDelete);
    }

    private void addResetAdd() {
        buttonResetAdd.getElement().setAttribute("theme", "primary");
        buttonResetAdd.getElement().setProperty("title",TEXT_RESET_ADD);
        buttonResetAdd.setIcon(new Icon(VaadinIcon.REFRESH));
        buttonResetAdd.addClickListener(event -> currentCrudList.resetAdd());
        this.add(buttonResetAdd);
    }


    private void addNew() {
        buttonNew.getElement().setAttribute("theme", "primary");
        buttonNew.getElement().setProperty("title", "New: aggiunge un elemento alla collezione");
        buttonNew.setIcon(new Icon(VaadinIcon.PLUS));
        buttonNew.setEnabled(true);
        buttonNew.addClickListener(event -> currentCrudList.newItem());
        this.add(buttonNew);
    }


    private void addEdit() {
        buttonEdit.getElement().setAttribute("theme", "primary");
        buttonEdit.getElement().setProperty("title", "Edit: modifica il singolo elemento selezionato");
        buttonEdit.setIcon(new Icon(VaadinIcon.PENCIL));
        buttonEdit.setEnabled(false);
        buttonEdit.addClickListener(event -> currentCrudList.updateItem());
        this.add(buttonEdit);
    }

    private void addShows() {
        buttonShow.getElement().setAttribute("theme", "primary");
        buttonShow.getElement().setProperty("title", "Shows: mostra il singolo elemento selezionato");
        buttonShow.setIcon(new Icon(VaadinIcon.POINTER));
        buttonShow.setEnabled(false);
        buttonShow.addClickListener(event -> currentCrudList.showItem());
        this.add(buttonShow);
    }


    private void addDeleteEntity() {
        buttonDeleteEntity.getElement().setAttribute("theme", "primary");
        buttonDeleteEntity.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonDeleteEntity.getElement().setProperty("title", "Delete: cancella il singolo elemento selezionato");
        buttonDeleteEntity.setIcon(new Icon(VaadinIcon.CLOSE));
        buttonDeleteEntity.setEnabled(false);
        buttonDeleteEntity.addClickListener(event -> currentCrudList.dialogDeleteItem());
        this.add(buttonDeleteEntity);
    }

    //    private void addExport() {
    //        buttonExport.getElement().setAttribute("theme", "primary");
    //        buttonExport.getElement().setProperty("title", "Export: esporta l'intera lista");
    //        buttonExport.setIcon(new Icon(VaadinIcon.LEVEL_RIGHT_BOLD));
    //        buttonExport.setEnabled(true);
    //        buttonExport.addClickListener(event -> currentCrudList.export());
    //
    //
    //        Anchor downloadAnchor = new DownloadAnchor(new StreamResource("Vie_.xlsx", () -> currentCrudList.creaExcelExporter().getInputStream()), "Esporta");
    //        downloadAnchor.getStyle().set("margin-left", "0.4rem");
    //
    //
    ////        this.add(downloadAnchor);
    //    }


    private void addSearchField() {
        //        searchFieldName = annotationService.getKeyPropertyName(currentCrudModelClazz);
        searchField.setPlaceholder(TAG_ALTRE_BY + searchFieldName);
        searchField.getElement().setProperty("title", "Search: ricerca testuale da inizio del campo " + searchFieldName);
        //            searchField.setWidth(WIDTH_EM);
        searchField.setClearButtonVisible(true);
        searchField.addValueChangeListener(event -> currentCrudList.sincroFiltri());
        this.add(searchField);
    }


    public boolean sincroSelection(boolean singoloSelezionato) {
        buttonDeleteAll.setEnabled(!singoloSelezionato);
        buttonResetAdd.setEnabled(!singoloSelezionato);
        buttonResetDelete.setEnabled(!singoloSelezionato);
        buttonNew.setEnabled(!singoloSelezionato);
        buttonEdit.setEnabled(singoloSelezionato);
        buttonShow.setEnabled(singoloSelezionato);
        buttonDeleteEntity.setEnabled(singoloSelezionato);
        if (downloadAnchor != null) {
            downloadAnchor.setEnabled(!singoloSelezionato);
        }

        return singoloSelezionato;
    }

    public String getSearchFieldValue() {
        String searchValue = VUOTA;

        if (searchField != null) {
            searchValue = searchField.getValue();

        }

        return searchValue;
    }

    public Button getButtonResetAdd() {
        return buttonResetAdd;
    }

}
