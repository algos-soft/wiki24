package it.algos.wiki24.backend.components;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.textfield.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.components.*;
import it.algos.vbase.backend.service.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.list.*;
import it.algos.wiki24.backend.logic.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 29-Nov-2023
 * Time: 07:47
 */
@Component(WIKI_QUALIFIER_LIST_BUTTON_BAR)
@Scope(value = SCOPE_PROTOTYPE)
public class WikiListButtonBar extends ListButtonBar {

    @Inject
    TextService textService;

    public WikiModulo currentCrudModulo;

    protected boolean usaBottoneDeleteAll;

    private boolean usaBottoneDownload;

    private boolean usaBottoneElabora;

    private boolean usaBottoneElaboraDue;

    private boolean usaBottoneTransfer;

    private boolean usaBottoneResetEntity;

    private boolean usaBottoneUploadAll;

    public boolean usaBottoneWikiView;

    public boolean usaBottoneWikiEdit;

    public boolean usaBottoneWikiCrono;

    public boolean usaBottoneTest;
    public boolean usaBottoneTest1;

    public boolean usaBottoneTest2;

    public boolean usaBottoneUpload;
    public boolean usaBottoneUpload1;

    public boolean usaBottoneUpload2;


    public boolean usaSearchPageId;

    public boolean usaSearchWikiTitle;

    //    protected Button buttonDeleteAll = new Button();

    protected Button buttonDownload = new Button();

    protected Button buttonElabora = new Button();

    protected Button buttonElaboraDue = new Button();

    protected Button buttonTransfer = new Button();

    protected Button buttonResetEntity = new Button();

    protected Button buttonUploadAll = new Button();

    protected Button buttonWikiView = new Button();

    protected Button buttonWikiEdit = new Button();

    protected Button buttonWikiCrono = new Button();

    protected Button buttonTest = new Button();
    protected Button buttonTest1 = new Button();

    protected Button buttonTest2 = new Button();

    protected Button buttonUpload = new Button();
    protected Button buttonUpload1 = new Button();

    protected Button buttonUpload2 = new Button();

    protected TextField searchPageId = new TextField();

    protected TextField searchWikiTitle = new TextField();

    protected WikiList currentCrudList;

    public WikiListButtonBar(final WikiModulo crudModulo, final WikiList crudList) {
        super(crudList);
        this.currentCrudModulo = crudModulo;
        this.currentCrudList = crudList;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar deleteAll() {
        this.usaBottoneDeleteAll = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar resetDelete() {
//        this.usaBottoneResetDelete = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar download() {
        this.usaBottoneDownload = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar elabora() {
        this.usaBottoneElabora = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar elaboraDue() {
        this.usaBottoneElaboraDue = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar transfer() {
        this.usaBottoneTransfer = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar restEntity() {
        this.usaBottoneResetEntity = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar uploadAll() {
        this.usaBottoneUploadAll = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar wikiView() {
        this.usaBottoneWikiView = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar wikiEdit() {
        this.usaBottoneWikiEdit = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar wikiCrono() {
        this.usaBottoneWikiCrono = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar test() {
        this.usaBottoneTest = true;
        return this;
    }
    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar test1() {
        this.usaBottoneTest1 = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar test2() {
        this.usaBottoneTest2 = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar upload() {
        this.usaBottoneUpload = true;
        return this;
    }
    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar upload1() {
        this.usaBottoneUpload1 = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar upload2() {
        this.usaBottoneUpload2 = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar searchPageId() {
        this.usaSearchPageId = true;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiListButtonBar searchWikiTitle() {
        this.usaSearchWikiTitle = true;
        return this;
    }

    /**
     * Termina la fase pattern Builder <br>
     * Aggiunge tutti e solo i bottoni previsti dal Fluent pattern Builder <br>
     * I bottoni vengono aggiunti al layout nell'ordine fisso previsto in questa classe <br>
     * In alternativa, usare direttamente i metodi addxxx per ogni bottone nell'ordine desiderato <br>
     */
    public WikiListButtonBar build() {
        addButtons();
        return this;
    }

    public void addButtons() {

        if (usaBottoneDeleteAll) {
            this.addDeleteAll();
        }
        if (usaBottoneResetDelete) {
            this.addResetDelete();
        }
        if (usaBottoneDownload) {
            this.addDownload();
        }
        if (usaBottoneElabora) {
            this.addElabora();
        }
        if (usaBottoneElaboraDue) {
            this.addElaboraDue();
        }
        if (usaBottoneTransfer) {
            this.addTransfer();
        }
        if (usaBottoneResetEntity) {
            this.addResetEntity();
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
        if (usaBottoneUploadAll) {
            this.addUploadAll();
        }
        if (usaBottoneWikiView) {
            this.addWikiView();
        }
        if (usaBottoneWikiEdit) {
            this.addWikiEdit();
        }
        if (usaBottoneWikiCrono) {
            this.addWikiCrono();
        }
        if (usaBottoneTest) {
            this.addTest();
        }
        if (usaBottoneTest1) {
            this.addTest1();
        }
        if (usaBottoneTest2) {
            this.addTest2();
        }
        if (usaBottoneUpload) {
            this.addUpload();
        }
        if (usaBottoneUpload1) {
            this.addUpload1();
        }
        if (usaBottoneUpload2) {
            this.addUpload2();
        }
        if (usaBottoneSearch) {
            this.addSearchField();
        }
        if (usaSearchPageId) {
            this.addSearchPageId();
        }
        if (usaSearchWikiTitle) {
            this.addSearchWikiTitle();
        }
    }


    public ListButtonBar addDownload() {
        buttonDownload.getElement().setAttribute("theme", "primary");
        buttonDownload.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonDownload.getElement().setProperty("title", "Download: ricarica tutti i valori dal server");
        buttonDownload.setIcon(new Icon(VaadinIcon.DOWNLOAD));
        buttonDownload.addClickListener(event -> currentCrudList.download(event));
        this.add(buttonDownload);
        return null;
    }

    public ListButtonBar addResetDelete() {
//        buttonResetDelete.getElement().setAttribute("theme", "primary");
//        buttonResetDelete.getElement().setProperty("title", TEXT_RESET_DELETE);
//        buttonResetDelete.setIcon(new Icon(VaadinIcon.REFRESH));
//        buttonResetDelete.addClickListener(event -> currentCrudList.resetDelete());
//        this.add(buttonResetDelete);
        return null;
    }

    private ListButtonBar addElabora() {
        buttonElabora.getElement().setAttribute("theme", "primary");
        buttonElabora.getElement().setProperty("title", "Elabora: tutte le funzioni del package");
        buttonElabora.setIcon(new Icon(VaadinIcon.PUZZLE_PIECE));
        buttonElabora.addClickListener(event -> currentCrudList.elabora());
        this.add(buttonElabora);
        return null;
    }

    private ListButtonBar addElaboraDue() {
        buttonElaboraDue.getElement().setAttribute("theme", "secondary");
        buttonElaboraDue.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonElaboraDue.getElement().setProperty("title", "Elabora: alcune funzioni del package");
        buttonElaboraDue.setIcon(new Icon(VaadinIcon.PUZZLE_PIECE));
        buttonElaboraDue.addClickListener(event -> currentCrudList.elaboraDue());
        this.add(buttonElaboraDue);
        return null;
    }

    private ListButtonBar addTransfer() {
        buttonTransfer.getElement().setAttribute("theme", "secondary");
        buttonTransfer.getElement().setProperty("title", "Transfer: spostamento ad altro modulo");
        buttonTransfer.setIcon(new Icon(VaadinIcon.ARROW_LEFT));
        buttonTransfer.setEnabled(false);
        buttonTransfer.addClickListener(event -> currentCrudList.transfer());
        this.add(buttonTransfer);
        return null;
    }

    private ListButtonBar addResetEntity() {
        buttonResetEntity.getElement().setAttribute("theme", "secondary");
        buttonResetEntity.getElement().setProperty("title", "Reset: elabora la entity");
        buttonResetEntity.setIcon(new Icon(VaadinIcon.REFRESH));
        buttonResetEntity.setEnabled(false);
        buttonResetEntity.addClickListener(event -> currentCrudList.resetEntity());
        this.add(buttonResetEntity);
        return null;
    }

    private ListButtonBar addUploadAll() {
        buttonUploadAll.getElement().setAttribute("theme", "primary");
        buttonUploadAll.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonUploadAll.getElement().setProperty("title", "Upload di tutte le pagine");
        buttonUploadAll.setIcon(new Icon(VaadinIcon.UPLOAD));
        buttonUploadAll.setEnabled(true);
        buttonUploadAll.addClickListener(event -> currentCrudModulo.uploadAll());
        this.add(buttonUploadAll);
        return null;
    }

    private ListButtonBar addWikiView() {
        buttonWikiView.getElement().setAttribute("theme", "primary");
        buttonWikiView.getElement().setProperty("title", "Wiki: pagina in visione");
        buttonWikiView.setIcon(new Icon(VaadinIcon.POINTER));
        buttonWikiView.setEnabled(false);
        buttonWikiView.addClickListener(event -> currentCrudList.wikiView());
        this.add(buttonWikiView);
        return null;
    }

    private ListButtonBar addWikiEdit() {
        buttonWikiEdit.getElement().setAttribute("theme", "secondary");
        buttonWikiEdit.getElement().setProperty("title", "Wiki: pagina in modifica");
        buttonWikiEdit.setIcon(new Icon(VaadinIcon.PENCIL));
        buttonWikiEdit.setEnabled(false);
        buttonWikiEdit.addClickListener(event -> currentCrudModulo.wikiEdit());
        this.add(buttonWikiEdit);
        return null;
    }

    private ListButtonBar addWikiCrono() {
        buttonWikiCrono.getElement().setAttribute("theme", "secondary");
        buttonWikiCrono.getElement().setProperty("title", "Wiki: cronistoria della pagina");
        buttonWikiCrono.setIcon(new Icon(VaadinIcon.CALENDAR));
        buttonWikiCrono.setEnabled(false);
        buttonWikiCrono.addClickListener(event -> currentCrudModulo.wikiCrono());
        this.add(buttonWikiCrono);
        return null;
    }


    private void addTest() {
        buttonTest.getElement().setAttribute("theme", "secondary");
        buttonTest.getElement().setProperty("title", "Test: scrittura di una voce su Utente:Biobot");
        buttonTest.setIcon(new Icon(VaadinIcon.SERVER));
        buttonTest.setEnabled(false);
        buttonTest.addClickListener(event -> currentCrudList.testPagina());
        this.add(buttonTest);
    }

    private void addTest1() {
        buttonTest1.getElement().setAttribute("theme", "secondary");
        buttonTest1.getElement().setProperty("title", "Test: scrittura di una voce nati su Utente:Biobot");
        buttonTest1.setIcon(new Icon(VaadinIcon.SERVER));
        buttonTest1.setEnabled(false);
        buttonTest1.addClickListener(event -> currentCrudList.testPaginaNati());
        this.add(buttonTest1);
    }

    private void addTest2() {
        buttonTest2.getElement().setAttribute("theme", "secondary");
        buttonTest2.getElement().setProperty("title", "Test: scrittura di una voce morti su Utente:Biobot");
        buttonTest2.setIcon(new Icon(VaadinIcon.SERVER));
        buttonTest2.setEnabled(false);
        buttonTest2.addClickListener(event -> currentCrudList.testPaginaMorti());
        this.add(buttonTest2);
    }

    private void addUpload() {
        buttonUpload.getElement().setAttribute("theme", "primary");
        buttonUpload.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonUpload.getElement().setProperty("title", "Upload della singola pagina");
        buttonUpload.setIcon(new Icon(VaadinIcon.UPLOAD));
        buttonUpload.setEnabled(false);
        buttonUpload.addClickListener(event -> currentCrudList.uploadPagina());
        this.add(buttonUpload);
    }

    private void addUpload1() {
        buttonUpload1.getElement().setAttribute("theme", "primary");
        buttonUpload1.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonUpload1.getElement().setProperty("title", "Upload della singola pagina (nati)");
        buttonUpload1.setIcon(new Icon(VaadinIcon.UPLOAD));
        buttonUpload1.setEnabled(false);
        buttonUpload1.addClickListener(event -> currentCrudList.uploadPaginaNati());
        this.add(buttonUpload1);
    }


    private void addUpload2() {
        buttonUpload2.getElement().setAttribute("theme", "primary");
        buttonUpload2.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonUpload2.getElement().setProperty("title", "Upload della singola pagina (morti)");
        buttonUpload2.setIcon(new Icon(VaadinIcon.UPLOAD));
        buttonUpload2.setEnabled(false);
        buttonUpload2.addClickListener(event -> currentCrudList.uploadPaginaMorti());
        this.add(buttonUpload2);
    }

    public void addSearchPageId() {
        searchPageId.setPlaceholder(TAG_ALTRE_BY + FIELD_NAME_PAGE_ID);
        searchPageId.getElement().setProperty("title", "Search: ricerca per il valore del campo " + FIELD_NAME_PAGE_ID);
        searchPageId.setClearButtonVisible(true);
        searchPageId.addValueChangeListener(event -> currentCrudList.sincroFiltri());
        this.add(searchPageId);
    }

    public void addSearchWikiTitle() {
        searchWikiTitle.setPlaceholder(TAG_ALTRE_BY + FIELD_NAME_WIKI_TITLE);
        searchWikiTitle.getElement().setProperty("title", "Search: ricerca testuale da inizio del campo " + FIELD_NAME_WIKI_TITLE);
        searchWikiTitle.setClearButtonVisible(true);
        searchWikiTitle.addValueChangeListener(event -> currentCrudList.sincroFiltri());
        this.add(searchWikiTitle);
    }


    public boolean sincroSelection(boolean singoloSelezionato) {
        buttonDeleteAll.setEnabled(!singoloSelezionato);
        buttonResetDelete.setEnabled(!singoloSelezionato);
        buttonResetAdd.setEnabled(!singoloSelezionato);
        buttonResetPref.setEnabled(!singoloSelezionato);
        buttonDownload.setEnabled(!singoloSelezionato);
        buttonElabora.setEnabled(!singoloSelezionato);
        buttonUploadAll.setEnabled(!singoloSelezionato);

        buttonTransfer.setEnabled(singoloSelezionato);
        buttonResetEntity.setEnabled(singoloSelezionato);
        buttonWikiView.setEnabled(singoloSelezionato);
        buttonWikiEdit.setEnabled(singoloSelezionato);
        buttonWikiCrono.setEnabled(singoloSelezionato);
        buttonTest.setEnabled(singoloSelezionato);
        buttonTest1.setEnabled(singoloSelezionato);
        buttonTest2.setEnabled(singoloSelezionato);
        buttonUpload.setEnabled(singoloSelezionato);
        buttonUpload1.setEnabled(singoloSelezionato);
        buttonUpload2.setEnabled(singoloSelezionato);

        buttonNew.setEnabled(!singoloSelezionato);
        buttonEdit.setEnabled(singoloSelezionato);
        buttonShow.setEnabled(singoloSelezionato);
        buttonDeleteEntity.setEnabled(singoloSelezionato);
        if (downloadAnchor != null) {
            downloadAnchor.setEnabled(!singoloSelezionato);
        }

        return singoloSelezionato;
    }

    public long getSearchPageIdFieldValue() {
        long searchValue = 0;
        String txtValue = VUOTA;

        if (searchPageId != null) {
            txtValue = searchPageId.getValue();
            if (textService.isValid(txtValue)) {
                searchValue = Long.valueOf(searchPageId.getValue());
            }
        }

        return searchValue;
    }


    public String getSearchWikiTitleFieldValue() {
        String searchValue = VUOTA;

        if (searchWikiTitle != null) {
            searchValue = searchWikiTitle.getValue();

        }

        return searchValue;
    }

}
