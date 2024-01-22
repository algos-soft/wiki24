package it.algos.wiki24.backend.list;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.components.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.data.domain.*;

import javax.inject.*;
import java.time.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 28-Nov-2023
 * Time: 18:32
 */
public abstract class WikiList extends CrudList {

    @Inject
    WikiApiService wikiApiService;


    protected WikiListButtonBar buttonBar;

    protected boolean usaInfoDownload;

    protected boolean usaInfoElabora;

    protected boolean usaInfoUpload;

    protected VerticalLayout infoPlaceHolder;


    public WPref lastDownload;

    public WPref durataDownload;

    public TypeSchedule scheduledDownload;

    public TypeDurata unitaMisuraDownload;

    public WPref lastElabora;

    public WPref durataElabora;

    public TypeSchedule scheduledElabora;

    public TypeDurata unitaMisuraElabora;


    public WPref lastUpload;

    public WPref durataUpload;

    public TypeSchedule scheduledUpload;

    public TypeDurata unitaMisuraUpload;

    public boolean usaBottoneElabora;

    public boolean usaBottoneElaboraDue;

    public boolean usaBottoneTransfer;

    public boolean usaBottoneResetEntity;

    public boolean usaBottoneUploadAll;

    public boolean usaSearchPageId;

    public boolean usaSearchWikiTitle;

    public boolean usaBottoneWikiView;

    public boolean usaBottoneWikiEdit;

    public boolean usaBottoneWikiCrono;

    public boolean usaBottoneTest1;

    public boolean usaBottoneTest2;

    public boolean usaBottoneUpload1;

    public boolean usaBottoneUpload2;

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

        this.usaInfoDownload = false;
        this.usaInfoElabora = false;
        this.usaBottoneElaboraDue = false;
        this.usaInfoUpload = false;

        this.usaBottoneDownload = true;
        this.usaBottoneElabora = true;
        this.usaBottoneUploadAll = false;
        this.usaBottoneTransfer = false;
        this.usaBottoneResetEntity = false;

        this.usaSearchPageId = false;
        this.usaSearchWikiTitle = false;

        this.usaBottoneWikiView = false;
        this.usaBottoneWikiEdit = false;
        this.usaBottoneWikiCrono = false;

        this.usaBottoneTest1 = false;
        this.usaBottoneTest2 = false;
        this.usaBottoneUpload1 = false;
        this.usaBottoneUpload2 = false;

        this.fixPreferenzeDaModulo();
    }

    protected void fixPreferenzeDaModulo() {
        if (currentCrudModulo != null) {
            scheduledDownload = currentCrudModulo.scheduledDownload;
            lastDownload = currentCrudModulo.lastDownload;
            durataDownload = currentCrudModulo.durataDownload;
            unitaMisuraDownload = currentCrudModulo.unitaMisuraDownload;

            scheduledElabora = currentCrudModulo.scheduledElabora;
            lastElabora = currentCrudModulo.lastElabora;
            durataElabora = currentCrudModulo.durataElabora;
            unitaMisuraElabora = currentCrudModulo.unitaMisuraElabora;

            scheduledUpload = currentCrudModulo.scheduledUpload;
            lastUpload = currentCrudModulo.lastUpload;
            durataUpload = currentCrudModulo.durataUpload;
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
        infoPlaceHolder.removeAll();

        this.fixInfoDownload();
        this.fixInfoElabora();
        this.fixInfoUpload();
    }


    public void fixInfoDownload() {
        String downloadTxt = VUOTA;
        String downloadLast = VUOTA;
        WPref flagDownload = currentCrudModulo.flagDownload;
        String status = flagDownload != null ? flagDownload.is() ? "acceso" : "spento" : "indefinito";

        if (usaInfoDownload) {
            if (scheduledDownload != null) {
                downloadTxt = String.format("Download (%s=%s). Task %s", flagDownload, status, textService.primaMinuscola(scheduledDownload.getNota()));
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
        }
    }


    public void fixInfoElabora() {
        String elaboraTxt = VUOTA;
        String elaboraLast = VUOTA;
        WPref flagElabora = currentCrudModulo.flagElabora;
        String status = flagElabora != null ? flagElabora.is() ? "acceso" : "spento" : "indefinito";

        if (usaInfoElabora) {
            if (scheduledElabora != null) {
                elaboraTxt = String.format("Elabora (%s=%s). Task %s", flagElabora, status,
                        textService.primaMinuscola(scheduledElabora.getNota()));
            }
            else {
                elaboraTxt = "Scheduled elaborazione non prevista.";
            }
            if (lastElabora != null && lastElabora.getCurrentValue() instanceof LocalDateTime elaborazione) {
                if (elaborazione.equals(ROOT_DATA_TIME)) {
                    elaboraLast = "Elaborazione non ancora effettuata.";
                }
                else {
                    elaboraLast = String.format("Ultima elaborazione effettuata il %s", dateService.get(elaborazione));
                    if (durataElabora != null && durataElabora.getCurrentValue() instanceof Integer durata) {
                        if (durata > 0) {
                            elaboraLast += String.format(" in circa %d %s.", durata, unitaMisuraElabora);
                        }
                    }
                }
            }
            message = String.format("%s%s%s", elaboraTxt, SPAZIO, elaboraLast);
            infoPlaceHolder.add(ASpan.text(message).verde().small());
        }
    }

    public void fixInfoUpload() {
        String uploadTxt = VUOTA;
        String uploadLast = VUOTA;

        if (usaInfoUpload) {
            if (scheduledUpload != null) {
                //                uploadTxt = "Scheduled upload " + scheduledUpload.getDescrizione();
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
        this.buttonBar = appContext.getBean(WikiListButtonBar.class, currentCrudModulo, this);
        super.buttonBar = this.buttonBar;

        if (usaBottoneDeleteAll) {
            buttonBar.deleteAll();
        }
        if (usaBottoneResetDelete) {
            buttonBar.resetDelete();
        }
        if (usaBottoneDownload) {
            buttonBar.download();
        }
        if (usaBottoneElabora) {
            buttonBar.elabora();
        }
        if (usaBottoneElaboraDue) {
            buttonBar.elaboraDue();
        }
        if (usaBottoneUploadAll) {
            buttonBar.uploadAll();
        }
        if (usaBottoneTransfer) {
            buttonBar.transfer();
        }
        if (usaBottoneResetEntity) {
            buttonBar.restEntity();
        }
        if (usaBottoneWikiView) {
            buttonBar.wikiView();
        }
        if (usaBottoneWikiEdit) {
            buttonBar.wikiEdit();
        }
        if (usaBottoneWikiCrono) {
            buttonBar.wikiCrono();
        }

        if (usaBottoneTest1) {
            buttonBar.test1();
        }
        if (usaBottoneTest2) {
            buttonBar.test2();
        }
        if (usaBottoneUpload1) {
            buttonBar.upload1();
        }
        if (usaBottoneUpload2) {
            buttonBar.upload2();
        }

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
        if (usaSearchPageId) {
            buttonBar.searchPageId();
        }
        if (usaSearchWikiTitle) {
            buttonBar.searchWikiTitle();
        }

        topPlaceHolder.add(buttonBar.build());
    }

    public boolean resetDelete() {
        boolean usaNotification = Pref.usaNotification.is();
        Pref.usaNotification.setValue(false);

        currentCrudModulo.resetDelete();
        refreshData();

        Pref.usaNotification.setValue(usaNotification);
        return true;
    }

    public boolean download(ClickEvent event) {
        currentCrudModulo.download();
        refreshData();
        fixInfo();
        return true;
    }

    public boolean elabora() {
        currentCrudModulo.elabora();
        refreshData();
        fixInfo();
        return true;
    }

    public boolean elaboraDue() {
        currentCrudModulo.elaboraDue();
        refreshData();
        fixInfo();
        return true;
    }

    public boolean transfer() {
        AbstractEntity crudEntityBean = getSingleEntity();

        if (crudEntityBean != null) {
            currentCrudModulo.transfer(crudEntityBean);
        }

        return true;
    }


    public boolean resetEntity() {
        AbstractEntity crudEntityBean = getSingleEntity();

        if (crudEntityBean != null) {
            currentCrudModulo.resetEntity(crudEntityBean);
        }

        refreshData();
        return true;
    }


    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        long searchPageId = 0;
        String searchWikiTitle = VUOTA;

        if (usaSearchPageId) {
            searchPageId = buttonBar.getSearchPageIdFieldValue();
        }

        if (searchPageId > 0) {
            filtri.uguale(FIELD_NAME_PAGE_ID, searchPageId);
            filtri.sort(Sort.Order.asc(FIELD_NAME_PAGE_ID));
        }
        else {
            filtri.remove(FIELD_NAME_PAGE_ID);
            filtri.sort(basicSortOrder);
        }

        if (usaSearchWikiTitle) {
            searchWikiTitle = buttonBar.getSearchWikiTitleFieldValue();
        }

        if (textService.isValid(searchWikiTitle)) {
            filtri.inizio(FIELD_NAME_WIKI_TITLE, searchWikiTitle);
            filtri.sort(Sort.Order.asc(FIELD_NAME_WIKI_TITLE));
        }
        else {
            filtri.remove(FIELD_NAME_WIKI_TITLE);
            filtri.sort(basicSortOrder);
        }
    }


    public void wikiView() {
        AbstractEntity crudEntityBean = getSingleEntity();

        if (crudEntityBean != null) {
            currentCrudModulo.wikiView(crudEntityBean);
        }
    }

    public void testPaginaNati() {
        AbstractEntity crudEntityBean = getSingleEntity();

        if (crudEntityBean != null) {
            currentCrudModulo.testPaginaNati(crudEntityBean);
        }
    }

    public void testPaginaMorti() {
        AbstractEntity crudEntityBean = getSingleEntity();

        if (crudEntityBean != null) {
            currentCrudModulo.testPaginaMorti(crudEntityBean);
        }
    }

    public void uploadPaginaNati() {
        AbstractEntity crudEntityBean = getSingleEntity();

        if (crudEntityBean != null) {
            currentCrudModulo.uploadPaginaNati(crudEntityBean);
        }
    }

    public void uploadPaginaMorti() {
        AbstractEntity crudEntityBean = getSingleEntity();

        if (crudEntityBean != null) {
            currentCrudModulo.uploadPaginaMorti(crudEntityBean);
        }
    }

}
