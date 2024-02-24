package it.algos.base24.backend.list;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.server.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.importexport.*;
import it.algos.base24.backend.layer.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.base24.ui.dialog.*;
import it.algos.base24.ui.view.*;
import it.algos.base24.ui.wrapper.*;
import jakarta.annotation.*;
import lombok.*;
import org.springframework.context.*;
import org.springframework.data.domain.*;

import javax.inject.*;
import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Wed, 02-Aug-2023
 * Time: 19:19
 */
public abstract class CrudList extends VerticalLayout {


    public boolean usaDataProvider;


    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Inject
    public ApplicationContext appContext;

    @Inject
    public ColumnService columnService;

    @Inject
    public AnnotationService annotationService;

    @Inject
    public ReflectionService reflectionService;

    @Inject
    public LogService logger;

    @Inject
    public TextService textService;

    @Inject
    public DataProviderService dataProviderService;

    @Inject
    public MongoService mongoService;

    @Inject
    public DateService dateService;

    protected Class currentCrudEntityClazz;

    public CrudModulo currentCrudModulo;

    protected CrudOperation currentCrudOperation;

    @Getter
    protected Grid<AbstractEntity> grid;

    public List<String> propertyListNames;

    protected FiltroSort filtri;

    protected VerticalLayout headerPlaceHolder;

    @Getter
    protected ListButtonBar topPlaceHolder;

    protected VerticalLayout bottomPlaceHolder;

    protected String infoScopo;

    public boolean usaBottoneDeleteAll;

    public boolean usaBottoneResetDelete;

    public boolean usaBottoneResetAdd;

    public boolean usaBottoneResetPref;

    public boolean usaBottoneDownload;

    public boolean usaBottoneNew;

    public boolean usaBottoneEdit;

    public boolean usaBottoneShows;

    public boolean usaBottoneDeleteEntity;

    public boolean usaBottoneExport;

    public boolean usaBottoneSearch;

    public boolean autoCreateColumns;

    public String searchFieldName;


    public Sort basicSort;

    protected TypeResetOld typeReset;

    protected TypeList typeList;

    protected String message;

    protected boolean usaVariantCompact;

    protected boolean usaVariantStripes;

    protected Grid.SelectionMode gridSelection;

    public CrudList() {
    }


    public CrudList(final CrudModulo crudModulo) {
        this.currentCrudModulo = crudModulo;
        this.currentCrudEntityClazz = crudModulo.getCurrentCrudEntityClazz();
    }

    @PostConstruct
    public void postConstruct() {

        this.getElement().setAttribute("id", "crudList");
        this.setPadding(true);
        this.setSpacing(false);
        this.setMargin(false);
        setSizeFull();

        this.fixPreferenze();
        this.fixForm();
        this.fixView();
    }


    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Sono disponibili tutte le istanze @Autowired <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.typeList = annotationService.getTypeList(currentCrudEntityClazz);

        this.propertyListNames = currentCrudModulo.getListPropertyNames();
        this.usaDataProvider = true;
        this.basicSort = currentCrudModulo.getBasicSort();
        this.searchFieldName = annotationService.getSearchPropertyName(currentCrudEntityClazz);
        this.usaVariantCompact = false;
        this.usaVariantStripes = true;
        this.gridSelection = Grid.SelectionMode.SINGLE;
        this.autoCreateColumns = true;

        if (typeList != null) {
            this.usaBottoneDeleteAll = typeList.isUsaBottoneDeleteAll();
            this.usaBottoneResetDelete = typeList.isUsaBottoneResetDelete();
            this.usaBottoneResetAdd = typeList.isUsaBottoneResetAdd();
            this.usaBottoneResetPref = typeList.isUsaBottoneResetPref();
            this.usaBottoneDownload = typeList.isUsaBottoneDownload();
            this.usaBottoneNew = typeList.isUsaBottoneNew();
            this.usaBottoneEdit = typeList.isUsaBottoneEdit();
            this.usaBottoneShows = typeList.isUsaBottoneShows();
            this.usaBottoneDeleteEntity = typeList.isUsaBottoneDeleteEntity();
            this.usaBottoneSearch = typeList.isUsaBottoneSearch();
            this.usaBottoneExport = typeList.isUsaBottoneExport();
        }
    }

    /**
     * Regola le modalità di apertura dela Form/Scheda <br>
     */
    private void fixForm() {
        String message;
        if (usaBottoneEdit && usaBottoneShows) {
            message = String.format("Nella classe [%s] ci sono usaBottoneEdit=true e usaBottoneShows=true", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message));
            return;
        }
        currentCrudOperation = usaBottoneShows ? CrudOperation.shows : null;
    }

    /**
     * Qui va tutta la logica della lista <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Sono disponibili tutte le istanze @Autowired <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixView() {

        //--Aggiunge un layout per informazioni aggiuntive come header della lista <br>
        //--Qui costruisce sempre il contenitore (placeHolder) anche vuoto <br>
        //--Nella sottoclasse lo riempie (eventualmente) di contenuti informativi <br>
        this.addHeaderPlaceHolder();
        this.fixHeader();

        //--Costruisce un layout per i bottoni di comando al Top della lista <br>
        //--Eventualmente i bottoni potrebbero andare su due righe <br>
        //--Qui costruisce sempre il contenitore (placeHolder) che ha sempre dei bottoni decisi in fixPreferenze() <br>
        //--Aggiunge eventuali bottoni/combobox della sottoclasse <br>
        //--Aggiunge in fondo un DownloadAnchor per l'export se previsto nelle preferenze <br>
        this.addTopPlaceHolder();
        this.fixExport();

        //--Corpo principale della Grid/Form sempre presente <br>
        this.fixBodyLayout();

        //--Costruisce un layout per gli avvisi in calce alla pagina <br>
        //--Qui costruisce sempre il contenitore (placeHolder) anche vuoto <br>
        this.addBottomPlaceHolder();
        this.fixBottom();

        this.sincroFiltri();
    }


    /**
     * Costruisce un placeHolder per informazioni aggiuntive come header della lista <br>
     * Può essere utilizzato o meno ma viene SEMPRE aggiunto al layout <br>
     */
    protected void addHeaderPlaceHolder() {
        headerPlaceHolder = new SimpleVerticalLayout();
        headerPlaceHolder.getElement().setAttribute("id", "headerPlaceHolder");
        this.add(headerPlaceHolder);
    }


    /**
     * Utilizza il placeHolder header della view per informazioni sulla tavola/lista <br>
     * Può essere sovrascritto, invocando PRIMA o DOPO il metodo della superclasse <br>
     */
    protected void fixHeader() {
        if (typeList != TypeList.hardWiki && typeList != TypeList.softWiki) {
            if (textService.isEmpty(infoScopo)) {
                infoScopo = typeList.getInfoScopo();
            }
            if (textService.isValid(infoScopo)) {
                headerPlaceHolder.add(ASpan.text(infoScopo).verde().bold());
            }
        }

        headerPlaceHolder.add(ASpan.text(typeList.getInfoCreazione()).rosso());
        headerPlaceHolder.add(ASpan.text(typeList.getInfoReset()).rosso());

        if (usaBottoneSearch && textService.isValid(searchFieldName)) {
            headerPlaceHolder.add(ASpan.text(String.format(TEXT_SEARCH, textService.primaMaiuscola(searchFieldName))).rosso().italic());
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
        topPlaceHolder = (ListButtonBar) appContext.getBean(QUALIFIER_LIST_BUTTON_BAR, this);
        topPlaceHolder.getElement().setAttribute("id", "topPlaceHolder");
        topPlaceHolder.setClassName("buttons");
        topPlaceHolder.setClassName("confirm-dialog-buttons");

        // filtro base (vuoto)
        // ordinamento iniziale di default bypassabile in fixPreferenze()
        filtri = appContext.getBean(FiltroSort.class, currentCrudEntityClazz);
        filtri.sort(basicSort);

        this.fixTop();
        this.add(topPlaceHolder);
    }


    /**
     * Aggiunge componenti al Top della Lista <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse se si vogliono aggiungere componenti IN CODA <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse se si vogliono aggiungere componenti in ordine diverso <br>
     */
    protected void fixTop() {
        if (usaBottoneDeleteAll) {
            topPlaceHolder.deleteAll();
        }
        if (usaBottoneResetDelete) {
            topPlaceHolder.resetDelete();
        }
        if (usaBottoneResetAdd) {
            topPlaceHolder.resetAdd();
        }
        if (usaBottoneResetPref) {
            topPlaceHolder.resetPref();
        }
        if (usaBottoneDownload) {
            topPlaceHolder.download();
        }
        if (usaBottoneNew) {
            topPlaceHolder.add();
        }
        if (usaBottoneEdit) {
            topPlaceHolder.edit();
        }
        if (usaBottoneShows) {
            topPlaceHolder.shows();
        }
        if (usaBottoneDeleteEntity) {
            topPlaceHolder.deleteEntity();
        }
        if (usaBottoneSearch && textService.isValid(searchFieldName)) {
            topPlaceHolder.searchField(searchFieldName);
        }

        topPlaceHolder.build();
    }

    protected void fixExport() {
        if (usaBottoneExport) {
            //--fix per gestire anche i test che NON hanno la UI e andrebbero in errore
            if (UI.getCurrent() == null) {
                return;
            }

            String nomeLista = annotationService.getMenuName(currentCrudEntityClazz);
            Anchor downloadAnchor = new DownloadAnchor(new StreamResource(nomeLista + ".xlsx", () -> this.creaExcelExporter().getInputStream()), "Esporta");
            downloadAnchor.getStyle().set("margin-left", "0.4rem");
            topPlaceHolder.export(downloadAnchor);
        }
    }


    /**
     * Costruisce il corpo principale (obbligatorio) della Grid <br>
     * <p>
     * Metodo chiamato da postConstruct() e fixList() <br>
     * Costruisce un' istanza dedicata con la Grid <br>
     */
    protected void fixBodyLayout() {

        // costruisce la Grid SENZA colonne
        grid = new Grid<>(currentCrudEntityClazz, false);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        grid.setSizeUndefined();

        // flag FALSE di default; modificabile in fixPreferenze() della singola sottoclasse
        if (usaVariantCompact) {
            grid.addThemeVariants(GridVariant.LUMO_COMPACT);
        }

        // The row-stripes theme produces a background color for every other row.
        // flag TRUE di default; modificabile in fixPreferenze() della singola sottoclasse
        if (usaVariantStripes) {
            grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        }

        // switch to type select mode
        // selection = Grid.SelectionMode.SINGLE di default; modificabile in fixPreferenze() della singola sottoclasse
        grid.setSelectionMode(gridSelection);

        // flag TRUE di default; modificabile in fixPreferenze() della singola sottoclasse
        if (autoCreateColumns) {
            if (propertyListNames == null) {
                propertyListNames = currentCrudModulo.getPropertyNames();
            }
            // aggiunge tutte le colonne (standard) previste nella lista utilizzando ColumnService
            columnService.addColumnsOneByOne(grid, currentCrudEntityClazz, propertyListNames);
        }

        // aggiunge le colonne (specifiche) previste nel metodo fixColumns() della sottoClasse
        this.fixColumns();

        // Pass all objects to a grid from a Data Provider
        this.refreshData();

        // sincronizza l'abilitazione dei bottoni in funzione della selezione delle righe
        // alcuni bottoni si abilitano/disabilitano se è selezionata solo 1 riga
        grid.addSelectionListener(event -> sincroSelection());

        // doppio click per l'apertura del Form <br>
        // stesso risultato del click sul bottone Edit (ser esiste)
        // stesso risultato del click sul bottone Shows (ser esiste)
        // stesso risultato del click sul bottone Delete (ser esiste)
        if (usaBottoneEdit || usaBottoneShows) {
            if (usaBottoneEdit) {
                grid.addItemDoubleClickListener(listener -> updateItem(listener.getItem()));
            }
            if (usaBottoneShows) {
                grid.addItemDoubleClickListener(listener -> showItem(listener.getItem()));
            }
        }

        this.sincroSelection();
        this.add(grid);
    }


    /**
     * Regola numero, ordine e visibilità delle colonne della grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixColumns() {
    }

    public void refreshData() {
        DataProvider provider = null;
        List items;

        if (usaDataProvider) {
            provider = dataProviderService.getProvider(filtri);
            if (provider != null) {
                grid.setDataProvider(provider);
            }
        }

        if (provider == null) {
            items = currentCrudModulo.findAll();
            if (items != null) {
                grid.setItems(items);
            }
        }

        this.sincroBottomLayout();
    }


    /**
     * Costruisce un layout per i componenti al Bottom della Lista <br>
     */
    private void addBottomPlaceHolder() {
        bottomPlaceHolder = new SimpleVerticalLayout();
        bottomPlaceHolder.getElement().setAttribute("id", "bottomPlaceHolder");
        this.add(bottomPlaceHolder);
    }


    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixBottom() {
        sincroBottomLayout();
    }


    /**
     * Apre un dialogo di creazione <br>
     * Proveniente da un click sul bottone New della buttonBar <br>
     * Passa al dialogo gli handler per annullare e creare <br>
     */
    public void newItem() {
        currentCrudModulo
                .creaForm()
                .annullaHandler(this::annullaHandler)
                .saveHandler(this::saveHandler);
    }


    /**
     * Apre un dialogo di editing <br>
     * Proveniente da un doppio click su una riga della Grid <br>
     * Proveniente da un click sul bottone Edit della buttonBar <br>
     * Passa al dialogo gli handler per cancellare, annullare e modificare <br>
     */
    public void updateItem() {
        AbstractEntity crudEntityBean = getSingleEntity();
        if (crudEntityBean != null) {
            updateItem(crudEntityBean);
        }
    }

    /**
     * Apre un dialogo di editing <br>
     * Proveniente da un doppio click su una riga della Grid <br>
     * Proveniente da un click sul bottone Edit della buttonBar <br>
     * Passa al dialogo gli handler per cancellare, annullare e modificare <br>
     *
     * @param entityBeanDaRegistrare (nuova o esistente)
     */
    public void updateItem(AbstractEntity entityBeanDaRegistrare) {
        currentCrudModulo.creaForm(entityBeanDaRegistrare, CrudOperation.update)
                .annullaHandler(this::annullaHandler)
                .deleteHandler(this::deleteHandler)
                .saveHandler(this::saveHandler)
                .deleteHandler(this::deleteHandler);
    }

    /**
     * Apre un dialogo <br>
     * Proveniente da un doppio click su una riga della Grid <br>
     * Proveniente da un click sul bottone Shows della buttonBar <br>
     * Passa al dialogo gli handler per annullare <br>
     */
    public void showItem() {
        AbstractEntity crudEntityBean = getSingleEntity();
        if (crudEntityBean != null) {
            showItem(crudEntityBean);
        }
    }


    /**
     * Apre un dialogo di editing <br>
     * Proveniente da un doppio click su una riga della Grid <br>
     * Proveniente da un click sul bottone Shows della buttonBar <br>
     * Passa al dialogo gli handler per annullare <br>
     *
     * @param entityBeanDaRegistrare (nuova o esistente)
     */
    public void showItem(AbstractEntity entityBeanDaRegistrare) {
        currentCrudModulo.creaForm(entityBeanDaRegistrare, CrudOperation.shows)
                .annullaHandler(this::annullaHandler)
                .deleteHandler(this::deleteHandler)
                .saveHandler(this::saveHandler);
    }


    /**
     * Primo ingresso di ritorno dopo il click sul bottone del dialogo <br>
     * Aggiorna il contenuto della Grid tramite DataProvider <br>
     */
    public void deleteHandler(boolean cancellato) {
        if (cancellato) {
            // ?
        }

        this.refreshData();
    }

    /**
     * Ingresso di ritorno dopo il click sul bottone del dialogo <br>
     * Non fa nulla <br>
     */
    public void annullaHandler() {
    }


    /**
     * Ingresso di ritorno dopo il click su sul bottone [save] del dialogo <br>
     * Non fa nulla <br>
     */
    public void saveHandler(final AbstractEntity entityBean, final CrudOperation operation) {
        this.refreshData();
    }


    public boolean isUnoSoloSelezionato() {
        return grid.getSelectedItems().size() == 1;
    }


    protected void sincroSelection() {
        if (topPlaceHolder != null) {
            topPlaceHolder.sincroSelection(isUnoSoloSelezionato());
        }
    }

    public AbstractEntity getSingleEntity() {
        Optional entityBean;

        if (isUnoSoloSelezionato()) {
            entityBean = grid.getSelectedItems().stream().findFirst();
            if (entityBean.isPresent() && entityBean.get() instanceof AbstractEntity crudEntityBean) {
                return crudEntityBean;
            }
            return null;
        }
        else {
            return null;
        }
    }


    public void sincroFiltri() {
        fixFiltri();
        refreshData();
    }

    protected void fixFiltri() {
        String searchValue = topPlaceHolder.getSearchFieldValue();

        if (textService.isValid(searchValue)) {
            filtri.inizio(searchFieldName, searchValue);
            filtri.sort(Sort.by(Sort.Direction.ASC, FIELD_NAME_ORDINE));
        }
        else {
            filtri.remove(searchFieldName);
            filtri.sort(basicSort);
        }
    }

    public void deleteAll() {
        if (Pref.usaConfermaCancellazione.is()) {
            DialogDelete.deleteAll(this::deleteAllEsegue);
        }
        else {
            deleteAllEsegue();
        }
    }

    public boolean deleteAllEsegue() {
        RisultatoDelete typeDelete = currentCrudModulo.deleteAll();

        if (typeDelete == RisultatoDelete.empty) {
            Notifica.message("Non ci sono entities da cancellare").primary().open();
        }

        if (typeDelete == RisultatoDelete.eseguito) {
            Notifica.message("Delete all").success().open();
            refreshData();
        }

        if (typeDelete == RisultatoDelete.errore) {
            Notifica.message("Non sono riuscito a cancellare la collection").error().durata(6).open();
        }

        return true;
    }

    public boolean resetDelete() {
        boolean usaNotification = Pref.usaNotification.is();
        Pref.usaNotification.setValue(false);

        currentCrudModulo.resetDelete();
        refreshData();

        Pref.usaNotification.setValue(usaNotification);
        return true;
    }

    /**
     * Proveniente da un click sul bottone RestAdd della buttonBar <br>
     * Invoca il metodo dedicato del Modulo specifico <br>
     * Aggiorna il contenuto della Grid tramite DataProvider <br>
     */
    public boolean resetAdd() {
        boolean usaNotification = Pref.usaNotification.is();
        Pref.usaNotification.setValue(false);

        currentCrudModulo.dialogResetAdd();
        refreshData();

        Pref.usaNotification.setValue(usaNotification);
        return true;
    }

    /**
     * Proveniente da un click sul bottone RestPref della buttonBar <br>
     * Invoca il metodo dedicato del Modulo specifico <br>
     * Aggiorna il contenuto della Grid tramite DataProvider <br>
     */
    public boolean resetPref() {
        currentCrudModulo.resetPref();
        refreshData();

        return true;
    }


    /**
     * Proveniente da un click sul bottone RestPref della buttonBar <br>
     * Invoca il metodo dedicato del Modulo specifico <br>
     * Aggiorna il contenuto della Grid tramite DataProvider <br>
     */
    public boolean download() {
        currentCrudModulo.download();
        refreshData();
        return true;
    }

    /**
     * Proveniente da un click sul bottone Delete della buttonBar <br>
     */
    public void dialogDeleteItem() {
        AbstractEntity crudEntityBean = getSingleEntity();

        if (crudEntityBean != null) {
            if (Pref.usaConfermaCancellazione.is()) {
                DialogDelete.delete(this::deleteItem);
            }
            else {
                deleteItem();
            }
        }
    }

    /**
     * Proveniente da un click sul bottone Delete della buttonBar <br>
     * Aggiorna il contenuto della Grid tramite DataProvider <br>
     */
    public boolean deleteItem() {
        boolean cancellata = false;
        AbstractEntity crudEntityBean = getSingleEntity();

        if (crudEntityBean != null) {
            cancellata = currentCrudModulo.delete(crudEntityBean);
        }

        //aggiornamento della lista
        if (cancellata) {
            refreshData();
        }

        // eventuale avviso
        if (cancellata) {
            Notifica.message("Cancellata").success().open();
        }
        else {
            Notifica.message("Non cancellata").error().open();
        }

        return cancellata;
    }

    protected void sincroBottomLayout() {
        String collectionName = annotationService.getCollectionName(currentCrudEntityClazz);
        collectionName = textService.primaMaiuscola(collectionName);
        int elementiTotali = currentCrudModulo.count();
        String totaleTxt = textService.format(elementiTotali);
        int elementiFiltrati = dataProviderService.count(filtri);
        String filtratiTxtTxt = textService.format(elementiFiltrati);

        String message = switch (elementiFiltrati) {
            case 0 -> String.format("%s: non ci sono elementi", collectionName);
            case 1 -> String.format("%s: c'è un solo elemento", collectionName, totaleTxt);
            default -> {
                if (elementiFiltrati == elementiTotali) {
                    yield String.format("%s: in totale ci sono %s elementi non filtrati", collectionName, totaleTxt);
                }
                else {
                    yield String.format("%s: filtrati %s elementi sul totale di %s", collectionName, filtratiTxtTxt, totaleTxt);
                }
            }
        };

        if (bottomPlaceHolder != null) {
            bottomPlaceHolder.removeAll();

            Span span = new Span();
            span.setText(message);
            span.getElement().setProperty("innerHTML", message);
            span.getElement().getStyle().set("color", "green");
            span.getElement().getStyle().set("font-weight", "bold");
            span.getElement().getStyle().set("font-size", "0.7em");
            bottomPlaceHolder.add(span);
        }
    }

    public void setAllRowsVisible(boolean visible) {
        if (grid != null) {
            grid.setAllRowsVisible(visible);
        }
    }


    public ExcelExporter creaExcelExporter() {
        ExcelExporter exporter;
        List<String> properties = currentCrudModulo.getListPropertyNames();
        String title = String.format("Lista %s", annotationService.getMenuName(currentCrudEntityClazz));
        int width;
        double multi = 1.5;
        Double doppio;

        exporter = new ExcelExporter(currentCrudEntityClazz, filtri, properties, mongoService);
        exporter.setTitle(title);
        for (String key : properties) {
            width = annotationService.getWidthInt(currentCrudEntityClazz, key);
            doppio = width * multi;
            width = doppio.intValue();
            exporter.setColumnWidth(key, width);
        }

        return exporter;
    }


    public Grid.Column getColumnByKey(String key) {
        List<Grid.Column<AbstractEntity>> columns = grid.getColumns();
        for (Grid.Column<AbstractEntity> column : columns) {
            if (column.getKey().equals(key)) {
                return column;
            }
        }
        return null;
    }

}
