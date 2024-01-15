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
@Component(QUALIFIER_LIST_BUTTON_BAR)
@Scope(value = SCOPE_PROTOTYPE)
public class ListButtonBar extends HorizontalLayout {

    @Autowired
    AnnotationService annotationService;

    protected CrudList currentCrudList;

    protected CrudModulo currentCrudModulo;

    protected Class currentCrudModelClazz;

    protected boolean usaBottoneDeleteAll;

    protected boolean usaBottoneResetDelete;

    protected boolean usaBottoneResetAdd;

    protected boolean usaBottoneResetPref;

    protected boolean usaBottoneDownload;

    protected boolean usaBottoneNew;

    protected boolean usaBottoneEdit;

    protected boolean usaBottoneShows;

    protected boolean usaBottoneDeleteEntity;

    protected boolean usaBottoneExport;

    protected boolean usaBottoneSearch;

    protected Button buttonDeleteAll = new Button();

    protected Button buttonResetDelete = new Button();

    protected Button buttonResetAdd = new Button();

    protected Button buttonResetPref = new Button();

    protected Button buttonDownload = new Button();

    protected Button buttonNew = new Button();

    protected Button buttonEdit = new Button();

    protected Button buttonShow = new Button();

    protected Button buttonDeleteEntity = new Button();

    protected Anchor downloadAnchor;

    protected TextField searchField = new TextField();

    protected String searchFieldName;

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
    public ListButtonBar resetPref() {
        this.usaBottoneResetPref = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public ListButtonBar download() {
        this.usaBottoneDownload = true;
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

    public void addButtons() {

        if (usaBottoneDeleteAll) {
            this.addDeleteAll();
        }
        if (usaBottoneResetDelete) {
            this.addResetDelete();
        }
        if (usaBottoneResetAdd) {
            this.addResetAdd();
        }
        if (usaBottoneResetPref) {
            this.addResetPref();
        }
        if (usaBottoneDownload) {
            this.addDownload();
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

    public void addDeleteAll() {
        buttonDeleteAll.getElement().setAttribute("theme", "primary");
        buttonDeleteAll.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonDeleteAll.getElement().setProperty("title", TEXT_DELETE);
        buttonDeleteAll.setIcon(new Icon(VaadinIcon.TRASH));
        buttonDeleteAll.addClickListener(event -> currentCrudList.deleteAll());
        this.add(buttonDeleteAll);
    }

    public void addResetDelete() {
        buttonResetDelete.getElement().setAttribute("theme", "primary");
        buttonResetDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonResetDelete.getElement().setProperty("title", TEXT_RESET_DELETE);
        buttonResetDelete.setIcon(new Icon(VaadinIcon.REFRESH));
        buttonResetDelete.addClickListener(event -> currentCrudList.resetDelete());
        this.add(buttonResetDelete);
    }

    public void addResetAdd() {
        buttonResetAdd.getElement().setAttribute("theme", "primary");
        buttonResetAdd.getElement().setProperty("title", TEXT_RESET_ADD);
        buttonResetAdd.setIcon(new Icon(VaadinIcon.REFRESH));
        buttonResetAdd.addClickListener(event -> currentCrudList.resetAdd());
        this.add(buttonResetAdd);
    }


    public void addResetPref() {
        buttonResetPref.getElement().setAttribute("theme", "primary");
        buttonResetPref.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonResetPref.getElement().setProperty("title", TEXT_RESET_PREF);
        buttonResetPref.setIcon(new Icon(VaadinIcon.REFRESH));
        buttonResetPref.addClickListener(event -> currentCrudList.resetPref());
        this.add(buttonResetPref);
    }

    public void addDownload() {
        buttonDownload.getElement().setAttribute("theme", "primary");
        buttonDownload.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonDownload.getElement().setProperty("title", TEXT_DOWNLOAD);
        buttonDownload.setIcon(new Icon(VaadinIcon.DOWNLOAD));
        buttonDownload.addClickListener(event -> currentCrudList.download());
        this.add(buttonDownload);
    }

    public void addNew() {
        buttonNew.getElement().setAttribute("theme", "primary");
        buttonNew.getElement().setProperty("title", "New: aggiunge un elemento alla collezione");
        buttonNew.setIcon(new Icon(VaadinIcon.PLUS));
        buttonNew.setEnabled(true);
        buttonNew.addClickListener(event -> currentCrudList.newItem());
        this.add(buttonNew);
    }


    public void addEdit() {
        buttonEdit.getElement().setAttribute("theme", "primary");
        buttonEdit.getElement().setProperty("title", "Edit: modifica il singolo elemento selezionato");
        buttonEdit.setIcon(new Icon(VaadinIcon.PENCIL));
        buttonEdit.setEnabled(false);
        buttonEdit.addClickListener(event -> currentCrudList.updateItem());
        this.add(buttonEdit);
    }

    public void addShows() {
        buttonShow.getElement().setAttribute("theme", "secondary");
        buttonShow.getElement().setProperty("title", "Shows: mostra il singolo elemento selezionato");
        buttonShow.setIcon(new Icon(VaadinIcon.SEARCH));
        buttonShow.setEnabled(false);
        buttonShow.addClickListener(event -> currentCrudList.showItem());
        this.add(buttonShow);
    }


    public void addDeleteEntity() {
        buttonDeleteEntity.getElement().setAttribute("theme", "primary");
        buttonDeleteEntity.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonDeleteEntity.getElement().setProperty("title", "Delete: cancella il singolo elemento selezionato");
        buttonDeleteEntity.setIcon(new Icon(VaadinIcon.CLOSE));
        buttonDeleteEntity.setEnabled(false);
        buttonDeleteEntity.addClickListener(event -> currentCrudList.dialogDeleteItem());
        this.add(buttonDeleteEntity);
    }



    public void addSearchField() {
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
        buttonResetDelete.setEnabled(!singoloSelezionato);
        buttonResetAdd.setEnabled(!singoloSelezionato);
        buttonResetPref.setEnabled(!singoloSelezionato);
        buttonDownload.setEnabled(!singoloSelezionato);
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
