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

    private boolean usaBottoneDownload;

    private boolean usaBottoneElabora;

    private boolean usaBottoneUpload;

    protected Button buttonDownload = new Button();

    protected Button buttonElabora = new Button();

    protected Button buttonUpload = new Button();

    protected WikiList currentCrudList;

    public WikiListButtonBar(final WikiList crudList) {
        super(crudList);
        this.currentCrudList = crudList;
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

    protected void addButtons() {

        if (usaBottoneDownload) {
            this.addDownload();
        }
        if (usaBottoneElabora) {
            this.addElabora();
        }
        if (usaBottoneUpload) {
            //            this.addUpload();
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


    private void addDownload() {
        buttonDownload.getElement().setAttribute("theme", "primary");
        buttonDownload.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonDownload.getElement().setProperty("title", "Download: ricarica tutti i valori dal server");
        buttonDownload.setIcon(new Icon(VaadinIcon.DOWNLOAD));
        buttonDownload.addClickListener(event -> currentCrudList.download());
        this.add(buttonDownload);
    }

    private void addElabora() {
        buttonElabora.getElement().setAttribute("theme", "primary");
        buttonElabora.getElement().setProperty("title", "Elabora: tutte le funzioni del package");
        buttonElabora.setIcon(new Icon(VaadinIcon.PUZZLE_PIECE));
        //        buttonDownload.addClickListener(event -> currentCrudList.elabora());
        this.add(buttonElabora);
    }


    public boolean sincroSelection(boolean singoloSelezionato) {
        buttonDeleteAll.setEnabled(!singoloSelezionato);
        buttonResetDelete.setEnabled(!singoloSelezionato);
        buttonResetAdd.setEnabled(!singoloSelezionato);
        buttonResetPref.setEnabled(!singoloSelezionato);
        buttonDownload.setEnabled(!singoloSelezionato);
        buttonElabora.setEnabled(!singoloSelezionato);
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
