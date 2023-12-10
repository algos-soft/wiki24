package it.algos.wiki24.backend.list;

import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.ui.wrapper.*;
import it.algos.wiki24.backend.components.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.time.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 28-Nov-2023
 * Time: 18:32
 */
public abstract class WikiList extends CrudList {

    protected WikiListButtonBar buttonBar;

    protected boolean usaInfoDownload;

    protected VerticalLayout infoPlaceHolder;


    protected WPref lastDownload;

    protected WPref durataDownload;

    protected WPref lastElaborazione;

    protected WPref durataElaborazione;

    protected String unitaMisuraDownload;

    protected String unitaMisuraElaborazione;

    private boolean usaBottoneDownload;

    private boolean usaBottoneElabora;

    private boolean usaBottoneUpload;

    protected WikiModulo currentCrudModulo;

    public WikiList(final WikiModulo crudModulo) {
        super(crudModulo);
        this.currentCrudModulo = crudModulo;
    }

    protected void fixPreferenze() {
        super.fixPreferenze();

        if (typeReset != null) {
            this.usaBottoneDeleteAll = typeReset.isUsaBottoneDeleteAll();
            this.usaBottoneResetDelete = typeReset.isUsaBottoneResetDelete();
            this.usaBottoneResetAdd = typeReset.isUsaBottoneResetAdd();
            this.usaBottoneNew = typeReset.isUsaBottoneNew();
            this.usaBottoneEdit = typeReset.isUsaBottoneEdit();
            this.usaBottoneShows = typeReset.isUsaBottoneShows();
            this.usaBottoneDeleteEntity = typeReset.isUsaBottoneDeleteEntity();
            this.usaBottoneSearch = typeReset.isUsaBottoneSearch();
            this.usaBottoneExport = typeReset.isUsaBottoneExport();
        }

        this.usaBottoneDownload = true;
        this.usaBottoneElabora = true;
        this.usaInfoDownload = true;
    }

    //    protected void fixPreferenzeBackend() {
    //        if (crudBackend != null) {
    //            crudBackend.lastDownload = lastDownload;
    //            crudBackend.durataDownload = durataDownload;
    //            crudBackend.lastElabora = lastElaborazione;
    //            crudBackend.durataElaborazione = durataElaborazione;
    //            crudBackend.lastUpload = lastUpload;
    //            crudBackend.durataUpload = durataUpload;
    //        }
    //    }

    /**
     * Costruisce un layout per informazioni aggiuntive come header della lista <br>
     */
    protected void addAlertPlaceHolder() {
        super.addAlertPlaceHolder();

        infoPlaceHolder = new SimpleVerticalLayout();
        this.add(infoPlaceHolder);
        this.fixInfo();
    }


    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixInfo() {
        infoPlaceHolder.removeAll();
        if (usaInfoDownload) {
            //            if (lastReset != null && lastReset.get() instanceof LocalDateTime reset) {
            //                if (reset.equals(ROOT_DATA_TIME)) {
            //                    message = "Reset non ancora effettuato";
            //                }
            //                else {
            //                    message = String.format("Ultimo reset effettuato il %s", dateService.get(reset));
            //                    if (durataReset != null && durataDownload.get() instanceof Integer durata) {
            //                        message += String.format(" in circa %d %s.", durata, "minuti");
            //                    }
            //                }
            //                addSpan(ASpan.text(message).verde().small());
            //            }
            if (lastDownload != null && lastDownload.getCurrentValue() instanceof LocalDateTime download) {
                if (download.equals(ROOT_DATA_TIME)) {
                    message = "Download non ancora effettuato";
                }
                else {
                    message = String.format("Ultimo download effettuato il %s", dateService.get(download));
                    if (durataDownload != null && durataDownload.getCurrentValue() instanceof Integer durata) {
                        if (durata > 0) {
                            message += String.format(" in circa %d %s.", durata, unitaMisuraDownload);
                        }
                    }
                }
                infoPlaceHolder.add(ASpan.text(message).verde().small());
            }
            if (lastElaborazione != null && lastElaborazione.getCurrentValue() instanceof LocalDateTime elaborazione) {
                if (elaborazione.equals(ROOT_DATA_TIME)) {
                    message = "Elaborazione non ancora effettuata";
                }
                else {
                    message = String.format("Ultima elaborazione effettuata il %s", dateService.get(elaborazione));
                    if (durataElaborazione != null && durataElaborazione.getCurrentValue() instanceof Integer durata) {
                        if (durata > 0) {
                            message += String.format(" in circa %d %s.", durata, unitaMisuraElaborazione);
                        }
                    }
                }
                infoPlaceHolder.add(ASpan.text(message).verde().small());
            }
            //
            //            if (lastStatistica != null && lastStatistica.get() instanceof LocalDateTime statistica) {
            //                if (statistica.equals(ROOT_DATA_TIME)) {
            //                    message = "Statistiche non ancora registrate sul server";
            //                }
            //                else {
            //                    message = String.format("Ultime statistiche registrate il %s", DateTimeFormatter.ofPattern("EEE, d MMM yyy 'alle' HH:mm").format(statistica));
            //                }
            //                addSpan(ASpan.text(message).verde().small());
            //            }
            //
            //            if (lastUpload != null && lastUpload.get() instanceof LocalDateTime upload) {
            //                if (upload.equals(ROOT_DATA_TIME)) {
            //                    message = "Upload non ancora effettuato";
            //                }
            //                else {
            //                    message = String.format("Ultimo upload effettuato il %s", dateService.get(upload));
            //                    if (durataUpload != null && durataUpload.get() instanceof Integer durata) {
            //                        message += String.format(" in circa %d %s.", durata, unitaMisuraUpload);
            //                    }
            //                    if (nextUpload != null && nextUpload.get() instanceof LocalDateTime next) {
            //                        message += String.format(" Prossimo upload previsto %s.", DateTimeFormatter.ofPattern("EEE, d MMM yyy 'alle' HH:mm").format(next));
            //                    }
            //                }
            //                addSpan(ASpan.text(message).verde().small());
            //            }
        }
    }

    public VerticalLayout addAlert(VerticalLayout layout) {
        alertPlaceHolder.add(layout);
        return layout;
    }


    /**
     * Può essere sovrascritto <br>
     */
    protected void fixTop() {
        this.buttonBar = appContext.getBean(WikiListButtonBar.class, this);
        super.buttonBar = this.buttonBar;

        if (usaBottoneDownload) {
            buttonBar.download();
        }
        if (usaBottoneElabora) {
            buttonBar.elabora();
        }
        //        if (usaBottoneResetDelete) {
        //            buttonBar.resetDelete();
        //        }
        //        if (usaBottoneResetAdd) {
        //            buttonBar.resetAdd();
        //        }
        //        if (usaBottoneNew) {
        //            buttonBar.add();
        //        }
        //        if (usaBottoneEdit) {
        //            buttonBar.edit();
        //        }
        //        if (usaBottoneShows) {
        //            buttonBar.shows();
        //        }
        //        if (usaBottoneDeleteEntity) {
        //            buttonBar.deleteEntity();
        //        }
        if (usaBottoneSearch && textService.isValid(searchFieldName)) {
            buttonBar.searchField(searchFieldName);
        }

        topPlaceHolder.add(buttonBar.build());
    }


    public boolean download() {
        currentCrudModulo.resetDelete();
        refreshData();
        fixInfo();
        return true;
    }

}
