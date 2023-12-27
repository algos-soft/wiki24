package it.algos.wiki24.backend.components;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.list.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 29-Nov-2023
 * Time: 07:47
 */
@Component
@Scope(value = SCOPE_PROTOTYPE)
public class WikiListButtonBar extends ListButtonBar {

    protected boolean usaBottoneDeleteAll;

    private boolean usaBottoneDownload;

    private boolean usaBottoneElabora;

    private boolean usaBottoneUpload;

    public boolean usaBottoneWikiView;

    public boolean usaBottoneWikiEdit;

    public boolean usaBottoneWikiCrono;


    protected Button buttonDeleteAll = new Button();

    protected Button buttonDownload = new Button();

    protected Button buttonElabora = new Button();

    protected Button buttonUpload = new Button();

    protected Button buttonWikiView = new Button();

    protected Button buttonWikiEdit = new Button();

    protected Button buttonWikiCrono = new Button();

    protected WikiList currentCrudList;

    public WikiListButtonBar(final WikiList crudList) {
        super(crudList);
        this.currentCrudList = crudList;
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

    public void addButtons() {

        if (usaBottoneDeleteAll) {
            this.addDeleteAll();
        }
        if (usaBottoneDownload) {
            this.addDownload();
        }
        if (usaBottoneElabora) {
            this.addElabora();
        }
        if (usaBottoneUpload) {
            //            this.addUpload();
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


    public void addDownload() {
        buttonDownload.getElement().setAttribute("theme", "primary");
        buttonDownload.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonDownload.getElement().setProperty("title", "Download: ricarica tutti i valori dal server");
        buttonDownload.setIcon(new Icon(VaadinIcon.DOWNLOAD));
        buttonDownload.addClickListener(event -> currentCrudList.download(event));
        this.add(buttonDownload);
    }

    private void addElabora() {
        buttonElabora.getElement().setAttribute("theme", "primary");
        buttonElabora.getElement().setProperty("title", "Elabora: tutte le funzioni del package");
        buttonElabora.setIcon(new Icon(VaadinIcon.PUZZLE_PIECE));
        buttonElabora.addClickListener(event -> currentCrudList.elabora());
        this.add(buttonElabora);
    }

    private void addWikiView() {
        buttonWikiView.getElement().setAttribute("theme", "secondary");
        buttonWikiView.getElement().setProperty("title", "Wiki: pagina in visione");
        buttonWikiView.setIcon(new Icon(VaadinIcon.SEARCH));
        buttonWikiView.setEnabled(false);
        buttonWikiView.addClickListener(event -> currentCrudList.wikiView());
        this.add(buttonWikiView);
    }

    private void addWikiEdit() {
        buttonWikiEdit.getElement().setAttribute("theme", "secondary");
        buttonWikiEdit.getElement().setProperty("title", "Wiki: pagina in modifica");
        buttonWikiEdit.setIcon(new Icon(VaadinIcon.PENCIL));
        buttonWikiEdit.setEnabled(false);
        buttonWikiEdit.addClickListener(event -> currentCrudList.wikiEdit());
        this.add(buttonWikiEdit);
    }

    private void addWikiCrono() {
        buttonWikiCrono.getElement().setAttribute("theme", "secondary");
        buttonWikiCrono.getElement().setProperty("title", "Wiki: cronistoria della pagina");
        buttonWikiCrono.setIcon(new Icon(VaadinIcon.CALENDAR));
        buttonWikiCrono.setEnabled(false);
        buttonWikiCrono.addClickListener(event -> currentCrudList.wikiCrono());
        this.add(buttonWikiCrono);
    }


    public boolean sincroSelection(boolean singoloSelezionato) {
        buttonDeleteAll.setEnabled(!singoloSelezionato);
        buttonResetDelete.setEnabled(!singoloSelezionato);
        buttonResetAdd.setEnabled(!singoloSelezionato);
        buttonResetPref.setEnabled(!singoloSelezionato);
        buttonDownload.setEnabled(!singoloSelezionato);
        buttonElabora.setEnabled(!singoloSelezionato);

        buttonWikiView.setEnabled(singoloSelezionato);
        buttonWikiEdit.setEnabled(singoloSelezionato);
        buttonWikiCrono.setEnabled(singoloSelezionato);

        buttonNew.setEnabled(!singoloSelezionato);
        buttonEdit.setEnabled(singoloSelezionato);
        buttonShow.setEnabled(singoloSelezionato);
        buttonDeleteEntity.setEnabled(singoloSelezionato);
        if (downloadAnchor != null) {
            downloadAnchor.setEnabled(!singoloSelezionato);
        }

        return singoloSelezionato;
    }

}
