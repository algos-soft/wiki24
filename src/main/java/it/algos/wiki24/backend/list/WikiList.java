package it.algos.wiki24.backend.list;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.server.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.components.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.layer.*;
import it.algos.vbase.backend.list.*;
import it.algos.vbase.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.components.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.service.*;
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


    //    protected WikiListButtonBar buttonBar;
    protected WikiListButtonBar wikiTopPlaceHolder;

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

    public boolean usaBottoneTest;

    public boolean usaBottoneTest1;

    public boolean usaBottoneTest2;

    public boolean usaBottoneUpload;

    public boolean usaBottoneUpload1;

    public boolean usaBottoneUpload2;

    public WikiModulo currentCrudModulo;

    protected String infoListaPagina;

    protected String infoListaSottoPagina;

    protected String infoListaSottoSottoPagina;

    protected String infoUpload;

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

        this.usaBottoneTest = false;
        this.usaBottoneTest1 = false;
        this.usaBottoneTest2 = false;
        this.usaBottoneUpload = false;
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
     * <p>
     * Prima riga (infoScopo): Verde, bold, normale. Informazioni base: tavola (download) oppure Lista (upload) <br>
     * Secondo gruppo: Blue, normale, normale. Logica di creazione/funzionamento della tavola <br>
     * Terzo gruppo (infoLista): Blue, bold, small. Parametri logici di creazione delle liste <br>
     * Quarto gruppo: Rosso, bold, small. Esecuzione upload <br>
     * Quinto gruppo: Rosso, normale, small. Parametri delete/reset <br>
     * Sesto gruppo: Verde, normale, small. Informazioni sulla tempistica <br>
     * <p>
     * Può essere sovrascritto, invocando PRIMA o DOPO il metodo della superclasse <br>
     */
    @Override
    protected void fixHeader() {
        String messageInfo = VUOTA;
        String messageUpload = VUOTA;
        String sep = DUE_PUNTI_SPAZIO;
        int elementiTotali = currentCrudModulo.count();
        int elementiFiltrati = dataProviderService.count(filtri);

        if (textService.isValid(infoScopo)) {
            headerPlaceHolder.add(ASpan.text(infoScopo).verde().bold());
        }

        if (textService.isValid(infoListaPagina)) {
            messageInfo = String.format("Lista/Pagina%s%s%s%s", sep, infoListaPagina, PUNTO, SPAZIO_DOPPIO);
        }
        if (textService.isValid(infoListaSottoPagina)) {
            messageInfo += String.format("SottoPagine%s%s%s%s", sep, infoListaSottoPagina, PUNTO, SPAZIO_DOPPIO);
        }
        if (textService.isValid(infoListaSottoSottoPagina)) {
            messageInfo += String.format("SottoSottoPagine%s%s%s%s", sep, infoListaSottoSottoPagina, PUNTO, SPAZIO_DOPPIO);
        }
        if (textService.isValid(messageInfo)) {
            headerPlaceHolder.add(ASpan.text(messageInfo).blue().bold().small());
        }

        if (usaBottoneUploadAll) {
            if (isUnoSoloSelezionato()) {
                messageUpload = String.format("Upload della lista selezionata%s%s", PUNTO, SPAZIO);
            }
            else {
                if (elementiTotali == elementiFiltrati) {
                    messageUpload = String.format("Upload di tutte le [%d] liste%s%s", elementiTotali, PUNTO, SPAZIO);
                }
                else {
                    messageUpload = String.format("Upload delle sole [%d] liste selezionate coi filtri%s%s", elementiFiltrati, PUNTO, SPAZIO);
                }
            }
            if (WPref.scriveComunque.is()) {
                messageUpload += WPref.scriveComunque.getDescrizione();
            }
            else {
                messageUpload += "Registra solo se ci sono modifiche.";
            }
            headerPlaceHolder.add(ASpan.text(messageUpload).rosso().bold().small());
        }

        if (textService.isValid(infoCreazione)) {
            headerPlaceHolder.add(ASpan.text(infoCreazione).rosso().small());
        }
        if (textService.isValid(infoReset)) {
            if (isUnoSoloSelezionato()) {
                headerPlaceHolder.add(ASpan.text(infoReset).rosso().small());
            }
            else {
                headerPlaceHolder.add(ASpan.text(infoReset).rosso().bold());
            }
        }

        if (usaBottoneSearch && textService.isValid(searchFieldName)) {
            headerPlaceHolder.add(ASpan.text(String.format(TEXT_SEARCH, textService.primaMaiuscola(searchFieldName))).rosso().italic().small());
        }
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
        String elaboraTxt;
        String elaboraLast = VUOTA;
        WPref flagElabora = currentCrudModulo.flagElabora;
        String status = flagElabora != null ? flagElabora.is() ? "acceso" : "spento" : "indefinito";

        if (usaInfoElabora) {
            if (scheduledElabora != null) {
                elaboraTxt = String.format("Elabora (%s=%s). Task %s", flagElabora, status, textService.primaMinuscola(scheduledElabora.getNota()));
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
                uploadTxt = "Scheduled upload " + scheduledUpload.getNota();
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
     * Costruisce un layout (standard) per i componenti al Top della Lista <br>
     * I componenti possono essere (nell'ordine):
     * Bottoni standard (solo icone) Reset, New, Edit, Delete, ecc.. <br>
     * SearchField per il filtro testuale di ricerca <br>
     * ComboBox e CheckBox di filtro <br>
     * Bottoni specifici non standard <br>
     * <p>
     * Per aggiunte od ordinamenti specifici, sovrascrivere il metodo fixTop() <br>
     */
    protected void addTopPlaceHolder() {
        wikiTopPlaceHolder = (WikiListButtonBar) appContext.getBean(WIKI_QUALIFIER_LIST_BUTTON_BAR, currentCrudModulo, this);
        wikiTopPlaceHolder.getElement().setAttribute("id", "wikiTopPlaceHolder");
        wikiTopPlaceHolder.setClassName("buttons");
        wikiTopPlaceHolder.setClassName("confirm-dialog-buttons");

        // filtro base (vuoto)
        // ordinamento iniziale di default bypassabile in fixPreferenze()
        filtri = appContext.getBean(FiltroSort.class, currentCrudEntityClazz);
        filtri.sort(basicSort);

        this.fixTop();
        this.add(wikiTopPlaceHolder);
    }

    /**
     * Può essere sovrascritto <br>
     */
    protected void fixTop() {
        if (usaBottoneDeleteAll) {
            wikiTopPlaceHolder.deleteAll();
        }
        if (usaBottoneResetDelete) {
            wikiTopPlaceHolder.resetDelete();
        }
        if (usaBottoneDownload) {
            wikiTopPlaceHolder.download();
        }
        if (usaBottoneElabora) {
            wikiTopPlaceHolder.elabora();
        }
        if (usaBottoneElaboraDue) {
            wikiTopPlaceHolder.elaboraDue();
        }
        if (usaBottoneUploadAll) {
            wikiTopPlaceHolder.uploadAll();
        }
        if (usaBottoneTransfer) {
            wikiTopPlaceHolder.transfer();
        }
        if (usaBottoneResetEntity) {
            wikiTopPlaceHolder.restEntity();
        }
        if (usaBottoneWikiView) {
            wikiTopPlaceHolder.wikiView();
        }
        if (usaBottoneWikiEdit) {
            wikiTopPlaceHolder.wikiEdit();
        }
        if (usaBottoneWikiCrono) {
            wikiTopPlaceHolder.wikiCrono();
        }

        if (usaBottoneTest) {
            wikiTopPlaceHolder.test();
        }
        if (usaBottoneTest1) {
            wikiTopPlaceHolder.test1();
        }
        if (usaBottoneTest2) {
            wikiTopPlaceHolder.test2();
        }
        if (usaBottoneUpload) {
            wikiTopPlaceHolder.upload();
        }
        if (usaBottoneUpload1) {
            wikiTopPlaceHolder.upload1();
        }
        if (usaBottoneUpload2) {
            wikiTopPlaceHolder.upload2();
        }
        if (usaBottoneNew) {
            wikiTopPlaceHolder.add();
        }
        if (usaBottoneEdit) {
            wikiTopPlaceHolder.edit();
        }
        if (usaBottoneShows) {
            wikiTopPlaceHolder.shows();
        }
        if (usaBottoneDeleteEntity) {
            wikiTopPlaceHolder.deleteEntity();
        }
        if (usaBottoneSearch && textService.isValid(searchFieldName)) {
            wikiTopPlaceHolder.searchField(searchFieldName);
        }
        if (usaSearchPageId) {
            wikiTopPlaceHolder.searchPageId();
        }
        if (usaSearchWikiTitle) {
            wikiTopPlaceHolder.searchWikiTitle();
        }

        wikiTopPlaceHolder.build();
    }

    protected IndeterminateCheckbox creaFiltroCheckBox(IndeterminateCheckbox checkBox, String labelText) {
        checkBox = new IndeterminateCheckbox(labelText);
        checkBox.getStyle().set("margin-top", "0.5rem");
        checkBox.addValueChangeListener(event -> sincroFiltri());
        wikiTopPlaceHolder.add(checkBox);

        return checkBox;
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

    protected void sincroSelection() {
        if (headerPlaceHolder != null) {
            fixHeader();
        }
        if (wikiTopPlaceHolder != null) {
            wikiTopPlaceHolder.sincroSelection(isUnoSoloSelezionato());
        }
    }

    @Override
    protected void fixFiltri() {
        long searchPageId = 0;
        String searchWikiTitle = VUOTA;

        String searchValue = wikiTopPlaceHolder.getSearchFieldValue();

        if (textService.isValid(searchValue)) {
            filtri.inizio(searchFieldName, searchValue);
            filtri.sort(Sort.by(Sort.Direction.ASC, FIELD_NAME_ORDINE));
        }
        else {
            filtri.remove(searchFieldName);
            filtri.sort(basicSort);
        }

        if (usaSearchPageId) {
            searchPageId = wikiTopPlaceHolder.getSearchPageIdFieldValue();
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
            searchWikiTitle = wikiTopPlaceHolder.getSearchWikiTitleFieldValue();
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

    protected void fixFiltroCheckBox(IndeterminateCheckbox checkBox, String propertyName) {
        if (checkBox != null) {
            if (checkBox.isIndeterminate()) {
                filtri.remove(propertyName);
                filtri.sort(basicSort);
            }
            else if (checkBox.getValue()) {
                filtri.add(propertyName, true);
                filtri.sort(Sort.by(Sort.Direction.ASC, propertyName));
            }
            else {
                filtri.add(propertyName, false);
                filtri.sort(Sort.by(Sort.Direction.ASC, propertyName));
            }
        }
    }

    public void wikiView() {
        AbstractEntity crudEntityBean = getSingleEntity();

        if (crudEntityBean != null) {
            currentCrudModulo.wikiView(crudEntityBean);
        }
    }

    public void uploadAll() {

        int a = 87;
        //        currentCrudModulo.uploadAll();
    }

    public void testPagina() {
        AbstractEntity crudEntityBean = getSingleEntity();

        if (crudEntityBean != null) {
            currentCrudModulo.testPagina(crudEntityBean);
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

    public void uploadPagina() {
        AbstractEntity crudEntityBean = getSingleEntity();

        if (crudEntityBean != null) {
            currentCrudModulo.uploadPagina(crudEntityBean);
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

    @Override
    protected void fixExport() {
        if (usaBottoneExport) {
            //--fix per gestire anche i test che NON hanno la UI e andrebbero in errore
            if (UI.getCurrent() == null) {
                return;
            }

            String nomeLista = annotationService.getMenuName(currentCrudEntityClazz);
            Anchor downloadAnchor = new DownloadAnchor(new StreamResource(nomeLista + ".xlsx", () -> this.creaExcelExporter().getInputStream()), "Esporta");
            downloadAnchor.getStyle().set("margin-left", "0.4rem");
            wikiTopPlaceHolder.export(downloadAnchor);
        }
    }

}
