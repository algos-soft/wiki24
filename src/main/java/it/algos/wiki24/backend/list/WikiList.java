package it.algos.wiki24.backend.list;

import com.vaadin.flow.component.*;
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


    public WPref lastDownload;

    public WPref durataDownload;

    public TypeSchedule scheduledDownload;

    public TypeDurata unitaMisuraDownload;

    public WPref lastElaborazione;

    public WPref durataElaborazione;

    public String scheduledElaborazione;

    public TypeDurata unitaMisuraElaborazione;


    public WPref lastUpload;

    public WPref durataUpload;

    public String scheduledUpload;

    public TypeDurata unitaMisuraUpload;

    public boolean usaBottoneElabora;

    public boolean usaBottoneUpload;

    public WikiModulo currentCrudModulo;

    public WikiList(final WikiModulo crudModulo) {
        super(crudModulo);
        this.currentCrudModulo = crudModulo;
    }

    protected void fixPreferenze() {
        super.fixPreferenze();

        if (typeList != null) {
            this.usaBottoneDeleteAll = typeList.isUsaBottoneDeleteAll();
            this.usaBottoneResetDelete = typeList.isUsaBottoneResetDelete();
            this.usaBottoneResetAdd = typeList.isUsaBottoneResetAdd();
            this.usaBottoneNew = typeList.isUsaBottoneNew();
            this.usaBottoneEdit = typeList.isUsaBottoneEdit();
            this.usaBottoneShows = typeList.isUsaBottoneShows();
            this.usaBottoneDeleteEntity = typeList.isUsaBottoneDeleteEntity();
            this.usaBottoneSearch = typeList.isUsaBottoneSearch();
            this.usaBottoneExport = typeList.isUsaBottoneExport();
        }

        this.usaInfoDownload = true;
        this.usaBottoneDownload = true;
        this.usaBottoneElabora = true;

        this.fixPreferenzeDaModulo();
    }

    protected void fixPreferenzeDaModulo() {
        if (currentCrudModulo != null) {
            lastDownload = currentCrudModulo.lastDownload;
            durataDownload = currentCrudModulo.durataDownload;
            scheduledDownload = currentCrudModulo.scheduledDownload;
            unitaMisuraDownload = currentCrudModulo.unitaMisuraDownload;

            lastElaborazione = currentCrudModulo.lastElaborazione;
            durataElaborazione = currentCrudModulo.durataElaborazione;
            scheduledElaborazione = currentCrudModulo.scheduledElaborazione;
            unitaMisuraElaborazione = currentCrudModulo.unitaMisuraElaborazione;

            lastUpload = currentCrudModulo.lastUpload;
            durataUpload = currentCrudModulo.durataUpload;
            scheduledUpload = currentCrudModulo.scheduledUpload;
            unitaMisuraUpload = currentCrudModulo.unitaMisuraUpload;
        }
    }

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
        String downloadTxt = VUOTA;
        String downloadLast = VUOTA;
        String elaboraTxt = VUOTA;
        String elaboraLast = VUOTA;
        String uploadTxt = VUOTA;
        String uploadLast = VUOTA;

        infoPlaceHolder.removeAll();
        if (usaInfoDownload) {
            if (scheduledDownload != null) {
                downloadTxt = "Scheduled download " + scheduledDownload.getDescrizione();
            }
            else {
                downloadTxt = "Scheduled download non previsto.";
            }
            if (lastDownload != null && lastDownload.getCurrentValue() instanceof LocalDateTime download) {
                if (download.equals(ROOT_DATA_TIME)) {
                    downloadLast = "Download non ancora effettuato.";
                }
                else {
                    downloadLast = String.format("Ultimo download effettuato il %s", dateService.get(download));
                    if (durataDownload != null && durataDownload.getCurrentValue() instanceof Integer durata) {
                        if (durata > 0) {
                            downloadLast += String.format(" in circa %d %s.", durata, unitaMisuraDownload);
                        }
                    }
                }
            }
            message = String.format("%s%s%s", downloadTxt, SPAZIO, downloadLast);
            infoPlaceHolder.add(ASpan.text(message).verde().small());

            if (textService.isValid(scheduledElaborazione)) {
                elaboraTxt = "Scheduled elaborazione " + scheduledElaborazione;
            }
            else {
                elaboraTxt = "Scheduled elaborazione non prevista.";
            }
            if (lastElaborazione != null && lastElaborazione.getCurrentValue() instanceof LocalDateTime elaborazione) {
                if (elaborazione.equals(ROOT_DATA_TIME)) {
                    elaboraLast = "Elaborazione non ancora effettuata.";
                }
                else {
                    elaboraLast = String.format("Ultima elaborazione effettuata il %s", dateService.get(elaborazione));
                    if (durataElaborazione != null && durataElaborazione.getCurrentValue() instanceof Integer durata) {
                        if (durata > 0) {
                            elaboraLast += String.format(" in circa %d %s.", durata, unitaMisuraElaborazione);
                        }
                    }
                }
            }
            message = String.format("%s%s%s", elaboraTxt, SPAZIO, elaboraLast);
            infoPlaceHolder.add(ASpan.text(message).verde().small());

            if (textService.isValid(scheduledUpload)) {
                uploadTxt = "Scheduled upload " + scheduledUpload;
            }
            else {
                uploadTxt = "Scheduled upload non previsto.";
            }
            if (lastUpload != null && lastUpload.getCurrentValue() instanceof LocalDateTime upload) {
                if (upload.equals(ROOT_DATA_TIME)) {
                    uploadLast = "Upload non ancora effettuato.";
                }
                else {
                    uploadLast = String.format("Ultimo upload effettuato il %s", dateService.get(upload));
                    if (durataUpload != null && durataUpload.getCurrentValue() instanceof Integer durata) {
                        if (durata > 0) {
                            uploadLast += String.format(" in circa %d %s.", durata, unitaMisuraUpload);
                        }
                    }
                }
            }
            message = String.format("%s%s%s", uploadTxt, SPAZIO, uploadLast);
            infoPlaceHolder.add(ASpan.text(message).verde().small());

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

        if (usaBottoneDeleteAll) {
            buttonBar.deleteAll();
        }
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
        if (usaBottoneNew) {
            buttonBar.add();
        }
        if (usaBottoneEdit) {
            buttonBar.edit();
        }
        if (usaBottoneShows) {
            buttonBar.shows();
        }
        if (usaBottoneDeleteEntity) {
            buttonBar.deleteEntity();
        }
        if (usaBottoneSearch && textService.isValid(searchFieldName)) {
            buttonBar.searchField(searchFieldName);
        }

        topPlaceHolder.add(buttonBar.build());
    }


    public boolean download(ClickEvent event) {
        currentCrudModulo.download();
        refreshData();
        fixInfo();
        return true;
    }

}
