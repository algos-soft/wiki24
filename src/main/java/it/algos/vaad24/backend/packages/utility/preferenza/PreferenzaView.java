package it.algos.vaad24.backend.packages.utility.preferenza;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.page.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.data.selection.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.*;
import it.algos.vaad24.backend.annotation.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.vaadin.crudui.crud.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 27-mar-2022
 * Time: 10:29
 */
@PageTitle("Preferenze")
@Route(value = TAG_PRE, layout = MainLayout.class)
@AIView(lineawesomeClassnames = "wrench")
public class PreferenzaView extends VerticalLayout implements AfterNavigationObserver {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    //    /**
    //     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
    //     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
    //     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    public VaadBoot vaadBoot;

    @Autowired
    protected ApplicationContext appContext;

    protected Grid<Preferenza> grid;

    protected List<Preferenza> items;

    protected TextField filter;

    protected ComboBox<AETypePref> comboTypePref;

    @Autowired
    protected PreferenzaBackend backend;
    @Autowired
    protected ColumnService columnService;

    @Autowired
    protected HtmlService htmlService;

    @Autowired
    protected LogService logger;

    @Autowired
    protected PreferenceService preferenceService;

    protected AIEnumPref enumPref;

    protected Button refreshButton;

    protected Button addButton;

    protected Button editButton;

    protected Button deleteButton;

    protected int width;

    protected IndeterminateCheckbox boxBoxVaad23;

    protected IndeterminateCheckbox boxBoxRiavvio;

    protected IndeterminateCheckbox boxBoxDefault;
    protected IndeterminateCheckbox boxBoxDinamica;

    protected int elementiFiltrati;

    protected boolean usaBottomInfo;

    protected boolean usaBottomTotale;

    protected VerticalLayout bottomPlaceHolder;

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        WebBrowser browser = VaadinSession.getCurrent().getBrowser();
        UI.getCurrent().getPage().retrieveExtendedClientDetails(details -> fixBrowser(details));
        this.fixPreferenze();
        this.fixAlert();
        this.fixTop();
        this.fixCrud();
        this.fixColumns();
        this.fixFields();
        this.fixOrder();
        this.fixBottomLayout();
        this.addListeners();
    }

    /**
     *
     */
    public void fixBrowser(ExtendedClientDetails details) {
        width = details.getBodyClientWidth();
    }

    /**
     * Preferenze usate da questa view <br>
     * Primo metodo chiamato dopo AfterNavigationEvent <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */

    public void fixPreferenze() {
        this.enumPref = VaadVar.prefInstance;
        this.usaBottomInfo = true;
        this.usaBottomTotale = true;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixAlert() {
        VerticalLayout span = new VerticalLayout();
        span.setPadding(false);
        span.setSpacing(false);
        span.setMargin(false);
        this.add(span);

        span.add(ASpan.text("Preferenze registrate nel database mongoDB").blue());
        span.add(ASpan.text("Mostra solo le properties di un programma non multiCompany").rosso());
        span.add(ASpan.text(String.format("Vaad24=true per le preferenze del programma base '%s'", VaadVar.frameworkVaadin24)).verde());
        span.add(ASpan.text(String.format("Vaad24=false per le preferenze del programma corrente '%s'", VaadVar.projectCurrent)).verde());
        span.add(ASpan.text("NeedRiavvio=true se la preferenza ha effetto solo dopo un riavvio del programma").verde());
        span.add(ASpan.text("Le preferenze sono create/cancellate solo via hardcode (tramite una Enumeration)").rosso());
        span.add(ASpan.text("Refresh ripristina nel database i valori di default annullando le successive modifiche").rosso());
    }

    /**
     * Costruisce un (eventuale) layout per bottoni di comando in testa alla grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixTop() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setClassName("buttons");
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setMargin(false);
        layout.setClassName("confirm-dialog-buttons");

        refreshButton = new Button();
        refreshButton.getElement().setAttribute("theme", "error");
        refreshButton.setIcon(new Icon(VaadinIcon.REFRESH));
        refreshButton.addClickListener(e -> refresh());
        layout.add(refreshButton);

        addButton = new Button();
        addButton.getElement().setAttribute("theme", "secondary");
        addButton.setIcon(new Icon(VaadinIcon.PLUS));
        addButton.addClickListener(e -> newItem());
        //        layout.add(addButton);

        editButton = new Button();
        editButton.getElement().setAttribute("theme", "secondary");
        editButton.setIcon(new Icon(VaadinIcon.PENCIL));
        editButton.addClickListener(e -> updateItem());
        editButton.setEnabled(false);
        layout.add(editButton);

        deleteButton = new Button();
        deleteButton.getElement().setAttribute("theme", "secondary");
        deleteButton.setIcon(new Icon(VaadinIcon.TRASH));
        deleteButton.addClickListener(e -> deleteItem());
        deleteButton.setEnabled(false);
        //        layout.add(deleteButton);

        filter = new TextField();
        filter.setPlaceholder("Filter by code");
        filter.setClearButtonVisible(true);
        filter.addValueChangeListener(event -> sincroFiltri());
        layout.add(filter);

        comboTypePref = new ComboBox<>();
        comboTypePref.setPlaceholder("Type");
        comboTypePref.setClearButtonVisible(true);
        List<AETypePref> items2 = AETypePref.getAllEnums();
        comboTypePref.setItems(items2);
        comboTypePref.addValueChangeListener(event -> sincroFiltri());
        layout.add(comboTypePref);

        boxBoxVaad23 = new IndeterminateCheckbox();
        boxBoxVaad23.setLabel(String.format("%s / %s", VaadVar.frameworkVaadin24, VaadVar.projectCurrentUpper));
        boxBoxVaad23.setIndeterminate(true);
        boxBoxVaad23.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout2 = new HorizontalLayout(boxBoxVaad23);
        layout2.setAlignItems(Alignment.CENTER);
        layout.add(layout2);

        boxBoxRiavvio = new IndeterminateCheckbox();
        boxBoxRiavvio.setLabel("Riavvio");
        boxBoxRiavvio.setIndeterminate(true);
        boxBoxRiavvio.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout3 = new HorizontalLayout(boxBoxRiavvio);
        layout3.setAlignItems(Alignment.CENTER);
        layout.add(layout3);

        boxBoxDefault = new IndeterminateCheckbox();
        boxBoxDefault.setLabel("Default");
        boxBoxDefault.setIndeterminate(true);
        boxBoxDefault.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout4 = new HorizontalLayout(boxBoxDefault);
        layout4.setAlignItems(Alignment.CENTER);
        layout.add(layout4);

        boxBoxDinamica = new IndeterminateCheckbox();
        boxBoxDinamica.setLabel("Dinamica");
        boxBoxDinamica.setIndeterminate(true);
        boxBoxDinamica.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout5 = new HorizontalLayout(boxBoxDinamica);
        layout5.setAlignItems(Alignment.CENTER);
        layout.add(layout5);

        this.add(layout);
    }

    /**
     * Logic configuration <br>
     * Qui vanno i collegamenti con la logica del backend <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixCrud() {
        // Create a listing component for a bean type
        grid = new Grid<>(Preferenza.class, false);

        // Pass all Preferenza objects to a grid from a Spring Data repository object
        grid.setItems(backend.findAll());

        // The row-stripes theme produces a background color for every other row.
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        // switch to single select mode
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addSelectionListener(event -> sincroSelection(event));

        // layout configuration
        setSizeFull();
        this.add(grid);
    }

    /**
     * Regola la visibilità delle colonne della grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixColumns() {
        Icon iconDB= new Icon(VaadinIcon.DATABASE);
        iconDB.setColor("blue");

        grid.addColumns("code", "type");

        grid.addColumn(new ComponentRenderer<>(pref ->
                switch (pref.getType()) {
                    case string, integer, lungo, localdate, localtime, localdatetime, email -> {
                        Label label = new Label(pref.getType().bytesToString(pref.getValue()));
                        label.getElement().getStyle().set("color", pref.getType().getColor());
                        label.getElement().getStyle().set("fontWeight", "bold");
                        yield label;
                    }
                    case bool -> {
                        boolean vero = (boolean) pref.getType().bytesToObject(pref.getValue());
                        Icon icona = vero ? VaadinIcon.CHECK.create() : VaadinIcon.CLOSE.create();
                        icona.setColor(vero ? COLOR_VERO : COLOR_FALSO);
                        yield icona;
                    }
                    case enumerationType, enumerationString -> {
                        String value = pref.getType().bytesToString(pref.getValue());
                        Label label = new Label(value);
                        label.getElement().getStyle().set("color", pref.getType().getColor());
                        label.getElement().getStyle().set("fontWeight", "bold");
                        yield label;
                    }
                    default -> new Label(TRE_PUNTI);
                })).setHeader("Value").setKey("value");

        grid.addColumns("descrizione");

        String larCode = "12em";
        String larType = "9em";
        String larValue = "10em";
        String larDesc = "30em";
        String larBool = "5em";

        grid.getColumnByKey("code").setWidth(larCode).setFlexGrow(0);
        grid.getColumnByKey("type").setWidth(larType).setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);
        grid.getColumnByKey("value").setWidth(larValue).setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);
        grid.getColumnByKey("descrizione").setWidth(larDesc).setFlexGrow(1);

        columnService.crea(grid, Preferenza.class, "vaad23");
        columnService.crea(grid, Preferenza.class, "needRiavvio");

        grid.addColumn(new ComponentRenderer<>(prefDB -> {
            boolean usaDefault = preferenceService.isStandard(prefDB.code);
            Icon icona = usaDefault ? new Icon(VaadinIcon.CHECK) : new Icon(VaadinIcon.CLOSE);
            icona.setColor(usaDefault ? "green" : "red");
            icona.setSize("1em");
            return icona;
        })).setHeader(iconDB).setKey("usaDefault").setWidth(larBool).setFlexGrow(0);

        columnService.crea(grid, Preferenza.class, "dinamica");
    }

    /**
     * Regola la visibilità dei fields del Form<br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixFields() {
    }

    /**
     * Regola l'ordinamento della <grid <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    public void fixOrder() {
    }


    /**
     * Aggiunge tutti i listeners ai bottoni di 'topPlaceholder' che sono stati creati SENZA listeners <br>
     * <p>
     * Chiamato da afterNavigation() <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addListeners() {
        // pass the row/item that the user double-clicked to method openDialog
        grid.addItemClickListener(listener -> {
            if (listener.getClickCount() == 2)
                updateItem(listener.getItem());
        });
    }


    protected void fixBottomLayout() {
        this.bottomPlaceHolder = new VerticalLayout();
        this.bottomPlaceHolder.setPadding(false);
        this.bottomPlaceHolder.setSpacing(false);
        this.bottomPlaceHolder.setMargin(false);

        sicroBottomLayout();
        this.add(bottomPlaceHolder);
    }

    public void newItem() {
        Preferenza entityBean = new Preferenza();
        entityBean.setType(AETypePref.string);
        PreferenzaDialog dialog = appContext.getBean(PreferenzaDialog.class, entityBean, CrudOperation.UPDATE);
        dialog.open(this::saveHandler, this::deleteHandler, this::annullaHandler);
    }

    public void updateItem() {
        Optional entityBean = grid.getSelectedItems().stream().findFirst();
        if (entityBean.isPresent()) {
            updateItem((Preferenza) entityBean.get());
        }
    }

    public void updateItem(Preferenza entityBean) {
        PreferenzaDialog dialog = appContext.getBean(PreferenzaDialog.class, entityBean, CrudOperation.UPDATE);
        dialog.open(this::saveHandler, this::deleteHandler, this::annullaHandler);
    }

    public void deleteItem() {
        Optional entityBean = grid.getSelectedItems().stream().findFirst();
        if (entityBean.isPresent()) {
            PreferenzaDialog dialog = appContext.getBean(PreferenzaDialog.class, entityBean.get(), CrudOperation.DELETE);
            dialog.open(this::saveHandler, this::deleteHandler, this::annullaHandler);
        }
    }

    protected void sincroFiltri() {
        List<Preferenza> items = backend.findAll();

        final String textSearch = filter != null ? filter.getValue() : VUOTA;
        if (textService.isValid(textSearch)) {
            items = items.stream()
                    .filter(pref -> pref.code.matches("^(?i)" + textSearch + ".*$"))
                    .toList();
        }

        final AETypePref type = comboTypePref != null ? comboTypePref.getValue() : null;
        if (type != null) {
            items = items.stream()
                    .filter(pref -> pref.type == type)
                    .toList();
        }

        if (boxBoxVaad23 != null && !boxBoxVaad23.isIndeterminate()) {
            items = items.stream()
                    .filter(pref -> pref.vaad23 == boxBoxVaad23.getValue())
                    .toList();
        }

        if (boxBoxRiavvio != null && !boxBoxRiavvio.isIndeterminate()) {
            items = items.stream()
                    .filter(pref -> pref.needRiavvio == boxBoxRiavvio.getValue())
                    .toList();
        }

        if (boxBoxDefault != null && !boxBoxDefault.isIndeterminate()) {
            items = items.stream()
                    .filter(pref -> preferenceService.isStandard(pref.code) == boxBoxDefault.getValue())
                    .toList();
        }

        if (boxBoxDinamica != null && !boxBoxDinamica.isIndeterminate()) {
            items = items.stream()
                    .filter(pref -> pref.dinamica == boxBoxDinamica.getValue())
                    .toList();
        }


        if (items != null) {
            grid.setItems((List) items);
            elementiFiltrati = items.size();
            sicroBottomLayout();
        }
    }

    protected void sincroSelection(SelectionEvent event) {
        boolean singoloSelezionato = event.getAllSelectedItems().size() == 1;
        refreshButton.setEnabled(!singoloSelezionato);
        editButton.setEnabled(singoloSelezionato);
        deleteButton.setEnabled(singoloSelezionato);
    }

    protected void refresh() {
        appContext.getBean(DialogRefreshPreferenza.class).open(this::refreshAll);
    }

    protected void refreshAll() {
        List<AIGenPref> listaPref = VaadVar.prefList;
        boolean almenoUnaModificata = false;
        String message;
        String keyCode;
        Object oldValue;

        for (AIGenPref pref : listaPref) {
            oldValue = backend.getValore(pref);
            if (backend.resetStandard(pref)) {
                keyCode = pref.getKeyCode();
                message = String.format("Reset preferenza [%s]: %s%s(%s)%s%s", keyCode, oldValue, FORWARD, pref.getType(), FORWARD, pref.getDefaultValue());
                logger.info(new WrapLog().type(AETypeLog.reset).message(message).usaDb());
                almenoUnaModificata = true;
            }
        }

        if (!almenoUnaModificata) {
            message = "Reset preferenze - Tutte le preferenze avevano già il valore standard";
            logger.info(new WrapLog().type(AETypeLog.reset).message(message).usaDb());
        }

        grid.setItems(backend.findAll());
        Avviso.message("Reset preferenze").primary().open();
    }

    /**
     * Primo ingresso dopo il click sul bottone del dialogo <br>
     */
    protected void saveHandler(final Preferenza entityBean, final CrudOperation operation) {
        grid.setItems(backend.findAll());
    }

    public void deleteHandler(final Preferenza entityBean) {
        backend.delete(entityBean);
        grid.setItems(backend.findAll());
        Avviso.message(String.format("%s successfully deleted", entityBean.code)).success().open();
    }

    public void annullaHandler(final Preferenza entityBean) {
        //        Notification.show(entityBean + " successfully deleted.", 3000, Notification.Position.BOTTOM_START);
    }

    protected void sicroBottomLayout() {
        String view = "Preferenza";
        String message;
        int elementiTotali = backend.count();
        String totaleTxt = textService.format(elementiTotali);
        String filtratiTxtTxt = textService.format(elementiFiltrati);

        if (elementiFiltrati == 0 || elementiFiltrati == elementiTotali) {
            message = String.format("%s: in totale ci sono %s elementi", view, totaleTxt);
        }
        else {
            message = String.format("%s: filtrati %s elementi sul totale di %s", view, filtratiTxtTxt, totaleTxt);
        }

        if (bottomPlaceHolder != null) {
            bottomPlaceHolder.removeAll();
            if (usaBottomTotale) {
                bottomPlaceHolder.add(htmlService.getSpan(new WrapSpan(message).color(AETypeColor.verde).weight(AEFontWeight.bold).fontHeight(AEFontSize.em7)));
            }

            if (usaBottomInfo) {
                double doppio = VaadVar.projectVersion;
                String nome = VaadVar.projectCurrentUpper;
                String data = VaadVar.projectDate;

                //--Locale.US per forzare la visualizzazione grafica di un punto anziché una virgola
                message = String.format(Locale.US, "Algos® - %s %2.1f di %s", nome, doppio, data);
                bottomPlaceHolder.add(htmlService.getSpan(new WrapSpan(message).color(AETypeColor.blue).weight(AEFontWeight.bold).fontHeight(AEFontSize.em7)));
            }
        }
    }

    public Span getSpan(final String avviso) {
        return htmlService.getSpanVerde(avviso);
    }

    public void spanBlue(final String message) {
        span(new WrapSpan(message).color(AETypeColor.blue));
    }

    public void spanRosso(final String message) {
        span(new WrapSpan(message).color(AETypeColor.rosso));
    }

    public void span(final String message) {
        span(new WrapSpan(message));
    }

    public void span(WrapSpan wrap) {
        Span span;

        if (wrap.getColor() == null) {
            wrap.color(AETypeColor.verde);
        }
        if (wrap.getWeight() == null) {
            wrap.weight(AEFontWeight.bold);
        }
        if (wrap.getFontHeight() == null) {
            if (width == 0 || width > 500) {
                wrap.fontHeight(AEFontSize.em9);
            }
            else {
                wrap.fontHeight(AEFontSize.em7);
            }
        }

        if (wrap.getLineHeight() == null) {
            if (width == 0 || width > 500) {
                wrap.lineHeight(AELineHeight.em3);
            }
            else {
                wrap.lineHeight(AELineHeight.em12);
            }
        }

        span = htmlService.getSpan(wrap);
        if (span != null) {
            this.add(span);
        }
    }

}