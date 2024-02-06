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

    //    protected VerticalLayout infoPlaceHolder;


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

    @Override
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
     * Utilizza il placeHolder header della view per informazioni sulla tavola/lista <br>
     * Può essere sovrascritto, invocando PRIMA o DOPO il metodo della superclasse <br>
     */
    @Override
    protected void fixHeader() {
        super.fixHeader();
        this.fixInfo();
    }


    /**
     * Aggiunge al placeHolder header della view alcune informazioni aggiuntive, tipiche di Wiki24 <br>
     */
    private void fixInfo() {
        this.fixInfoDownload();
        this.fixInfoElabora();
        this.fixInfoUpload();
    }


    private void fixInfoDownload() {
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
            headerPlaceHolder.add(ASpan.text(message).verde().small());
        }
    }


    private void fixInfoElabora() {
        String elaboraTxt = VUOTA;
        String elaboraLast = VUOTA;
        WPref flagElabora = currentCrudModulo.flagElabora;
        String status = flagElabora != null ? flagElabora.is() ? "acceso" : "spento" : "indefinito";

        if (usaInfoElabora) {
            if (scheduledElabora != null) {
                elaboraTxt = String.format("Elabora (%s=%s). Task %s", flagElabora, status,
                        textService.primaMinuscola(scheduledElabora.getNota())
                );
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
            headerPlaceHolder.add(ASpan.text(message).verde().small());
        }
    }

    private void fixInfoUpload() {
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
            headerPlaceHolder.add(ASpan.text(message).verde().small());
        }
    }


    /**
     * Può essere sovrascritto <br>
     */
    protected void fixTop() {
        this.buttonBar = appContext.getBean(WikiListButtonBar.class, currentCrudModulo, this);
        super.topPlaceHolder = this.buttonBar;

        if (usaBottoneDeleteAll) {
            topPlaceHolder.deleteAll();
        }
        if (usaBottoneResetDelete) {
            topPlaceHolder.resetDelete();
        }
        if (usaBottoneDownload) {
            topPlaceHolder.download();
        }
        if (usaBottoneElabora) {
//            topPlaceHolder.elabora();
        }
        if (usaBottoneElaboraDue) {
//            topPlaceHolder.elaboraDue();
        }
        if (usaBottoneUploadAll) {
//            topPlaceHolder.uploadAll();
        }
        if (usaBottoneTransfer) {
//            topPlaceHolder.transfer();
        }
        if (usaBottoneResetEntity) {
//            topPlaceHolder.restEntity();
        }
        if (usaBottoneWikiView) {
//            topPlaceHolder.wikiView();
        }
        if (usaBottoneWikiEdit) {
//            topPlaceHolder.wikiEdit();
        }
        if (usaBottoneWikiCrono) {
//            topPlaceHolder.wikiCrono();
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
            filtri.sort(Sort.by(Sort.Direction.ASC, FIELD_NAME_PAGE_ID));
        }
        else {
            filtri.remove(FIELD_NAME_PAGE_ID);
            filtri.sort(basicSort);
        }

        if (usaSearchWikiTitle) {
            searchWikiTitle = buttonBar.getSearchWikiTitleFieldValue();
        }

        if (textService.isValid(searchWikiTitle)) {
            filtri.inizio(FIELD_NAME_WIKI_TITLE, searchWikiTitle);
            filtri.sort(Sort.by(Sort.Direction.ASC, FIELD_NAME_WIKI_TITLE));
        }
        else {
            filtri.remove(FIELD_NAME_WIKI_TITLE);
            filtri.sort(basicSort);
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
