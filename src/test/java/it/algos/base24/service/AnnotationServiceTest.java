package it.algos.base24.service;

import it.algos.*;
import it.algos.base24.backend.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.anagrafica.via.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.packages.crono.mese.*;
import it.algos.base24.backend.packages.crono.secolo.*;
import it.algos.base24.backend.packages.geografia.continente.*;
import it.algos.base24.backend.packages.geografia.regione.*;
import it.algos.base24.backend.packages.geografia.stato.*;
import it.algos.base24.backend.packages.utility.preferenza.*;
import it.algos.base24.backend.packages.utility.role.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.basetest.*;
import it.algos.base24.ui.view.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;
import org.vaadin.lineawesome.*;

import javax.inject.*;
import java.util.stream.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sun, 22-Oct-2023
 * Time: 07:16
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Annotation Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnotationServiceTest extends ServiceTest {

    @Inject
    private AnnotationService service;

    @Inject
    private TextService textService;

    @Inject
    private ReflectionService reflectionService;


    //--class
    //--previsto
    private Stream<Arguments> entity() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(RoleView.class, false),
                Arguments.of(ViaEntity.class, true),
                Arguments.of(CrudModulo.class, false),
                Arguments.of(AnnoEntity.class, true),
                Arguments.of(GiornoEntity.class, true),
                Arguments.of(MeseEntity.class, true),
                Arguments.of(SecoloEntity.class, true),
                Arguments.of(ContinenteEntity.class, true),
                Arguments.of(RegioneEntity.class, true),
                Arguments.of(StatoEntity.class, true),
                Arguments.of(PreferenzaEntity.class, true),
                Arguments.of(RoleEntity.class, true)
        );
    }

    //--class
    //--previsto
    private Stream<Arguments> view() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(RoleView.class, true),
                Arguments.of(ViaView.class, true),
                Arguments.of(CrudModulo.class, false),
                Arguments.of(AnnoView.class, true),
                Arguments.of(GiornoView.class, true),
                Arguments.of(MeseView.class, true),
                Arguments.of(SecoloView.class, true),
                Arguments.of(ContinenteView.class, true),
                Arguments.of(RegioneView.class, true),
                Arguments.of(StatoView.class, true),
                Arguments.of(PreferenzaView.class, true),
                Arguments.of(RoleEntity.class, false)
        );
    }


    //--class
    //--property
    //--previsto
    private Stream<Arguments> field() {
        return Stream.of(
                Arguments.of(null, VUOTA, false),
                Arguments.of(RoleView.class, VUOTA, false),
                Arguments.of(RoleEntity.class, VUOTA, false),
                Arguments.of(RoleEntity.class, FIELD_NAME_DESCRIZIONE, false),
                Arguments.of(RoleEntity.class, FIELD_NAME_NOME, false),
                Arguments.of(RoleEntity.class, FIELD_NAME_CODE, true)
        );
    }


    //--class
    //--property
    //--previsto
    private Stream<Arguments> type() {
        return Stream.of(
                Arguments.of(null, VUOTA, null),
                Arguments.of(RoleView.class, VUOTA, null),
                Arguments.of(RoleEntity.class, VUOTA, null),
                Arguments.of(RoleEntity.class, FIELD_NAME_DESCRIZIONE, null),
                Arguments.of(RoleEntity.class, FIELD_NAME_ORDINE, TypeField.ordine),
                Arguments.of(RoleEntity.class, FIELD_NAME_CODE, TypeField.text)
        );
    }

    //--class
    //--property
    //--previsto
    private Stream<Arguments> width() {
        return Stream.of(
                Arguments.of(null, VUOTA, VUOTA),
                Arguments.of(RoleView.class, VUOTA, VUOTA),
                Arguments.of(RoleEntity.class, VUOTA, VUOTA),
                Arguments.of(RoleEntity.class, FIELD_NAME_DESCRIZIONE, VUOTA),
                Arguments.of(RoleEntity.class, FIELD_NAME_ORDINE, "4rem"),
                Arguments.of(RoleEntity.class, FIELD_NAME_CODE, "12rem")
        );
    }

    //--class
    //--property
    //--previsto
    private Stream<Arguments> header() {
        return Stream.of(
                Arguments.of(null, VUOTA, VUOTA),
                Arguments.of(RoleView.class, VUOTA, VUOTA),
                Arguments.of(RoleEntity.class, VUOTA, VUOTA),
                Arguments.of(RoleEntity.class, FIELD_NAME_DESCRIZIONE, VUOTA),
                Arguments.of(RoleEntity.class, FIELD_NAME_ORDINE, FIELD_HEADER_ORDINE),
                Arguments.of(RoleEntity.class, FIELD_NAME_CODE, textService.primaMaiuscola(FIELD_NAME_CODE))
        );
    }



    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = AnnotationService.class;
        super.setUpAll();
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @ParameterizedTest
    @MethodSource(value = "entity")
    @Order(10)
    @DisplayName("10 - getEntityAnnotation")
    void getEntityAnnotation(final Class clazz, boolean previsto) {
        previstoBooleano = previsto;
        AEntity annotation;
        clazzName = clazz != null ? clazz.getSimpleName() : VUOTA;

        annotation = service.getEntityAnnotation(clazz);
        assertEquals(previstoBooleano, annotation != null);
        if (annotation != null) {
            System.out.println(VUOTA);
            printAEntity(clazz, annotation);
        }
        else {
            message = String.format("Non esiste l'annotation 'AEntity' nella classe [%s]", clazzName);
            System.out.println(message);
        }
    }


    @Test
    @Order(11)
    @DisplayName("11 - getCollectionName")
    void getCollectionName() {
        System.out.println(("11 - getCollectionName"));
        System.out.println((message));
        System.out.println(VUOTA);

        //--class
        //--previsto classe valida
        entity().forEach(this::fixCollectionName);
    }


    //--class
    //--previsto classe valida
    void fixCollectionName(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        previstoBooleano = (boolean) mat[1];

        // Controlla che i parametri in ingresso siano validi
        if (!check(clazz, "fixCollectionName")) {
            return;
        }

        ottenuto = service.getCollectionName(clazz);
        assertEquals(previstoBooleano, textService.isValid(ottenuto));
        message = String.format("%s%s%s", clazz.getSimpleName(), FORWARD, textService.isValid(ottenuto) ? ottenuto : NULLO);
        System.out.println(message);
    }


    @Test
    @Order(12)
    @DisplayName("12 - getKeyPropertyName")
    void getKeyPropertyName() {
        System.out.println(("12 - getKeyPropertyName"));
        System.out.println((message));
        System.out.println(VUOTA);

        //--class
        //--previsto classe valida
        entity().forEach(this::fixKeyPropertyName);
    }


    //--class
    //--previsto classe valida
    void fixKeyPropertyName(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        previstoBooleano = (boolean) mat[1];

        // Controlla che i parametri in ingresso siano validi
        if (!check(clazz, "fixKeyPropertyName")) {
            return;
        }

        ottenuto = service.getKeyPropertyName(clazz);
        printProperty(clazz, ottenuto);
    }


    @Test
    @Order(13)
    @DisplayName("13 - getSearchPropertyName")
    void getSearchPropertyName() {
        System.out.println(("13 - getSearchPropertyName"));
        System.out.println((message));
        System.out.println(VUOTA);

        //--class
        //--previsto classe valida
        entity().forEach(this::fixSearchPropertyName);
    }


    //--class
    //--previsto classe valida
    void fixSearchPropertyName(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        previstoBooleano = (boolean) mat[1];

        // Controlla che i parametri in ingresso siano validi
        if (!check(clazz, "fixSearchPropertyName")) {
            return;
        }

        ottenuto = service.getSearchPropertyName(clazz);
        printProperty(clazz, ottenuto);
    }


    @Test
    @Order(14)
    @DisplayName("14 - getSortPropertyName")
    void getSortPropertyName() {
        System.out.println(("14 - getSortPropertyName"));
        System.out.println((message));
        System.out.println(VUOTA);

        //--class
        //--previsto classe valida
        entity().forEach(this::fixSortPropertyName);
    }


    //--class
    //--previsto classe valida
    void fixSortPropertyName(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        previstoBooleano = (boolean) mat[1];

        // Controlla che i parametri in ingresso siano validi
        if (!check(clazz, "fixSortPropertyName")) {
            return;
        }

        ottenuto = service.getSortPropertyName(clazz);
        printProperty(clazz, ottenuto);
    }


    @Test
    @Order(15)
    @DisplayName("15 - getTypeList")
    void getTypeList() {
        System.out.println(("15 - getTypeList"));
        System.out.println((message));
        System.out.println(VUOTA);

        //--class
        //--previsto classe valida
        entity().forEach(this::fixTypeList);
    }


    //--class
    //--previsto classe valida
    void fixTypeList(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        previstoBooleano = (boolean) mat[1];
        TypeList typeList;

        // Controlla che i parametri in ingresso siano validi
        if (!check(clazz, "fixTypeList")) {
            return;
        }

        typeList = service.getTypeList(clazz);
        message = String.format("%s%s%s", clazz.getSimpleName(), FORWARD, typeList);
        System.out.println(message);
    }


    @Test
    @Order(16)
    @DisplayName("16 - usaIdPrimaMinuscola")
    void usaIdPrimaMinuscola() {
        System.out.println(("16 - usaIdPrimaMinuscola"));
        System.out.println((message));
        System.out.println(VUOTA);

        //--class
        //--previsto classe valida
        entity().forEach(this::fixUsaIdPrimaMinuscola);
    }


    //--class
    //--previsto classe valida
    void fixUsaIdPrimaMinuscola(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];

        // Controlla che i parametri in ingresso siano validi
        if (!check(clazz, "fixUsaIdPrimaMinuscola")) {
            return;
        }

        ottenutoBooleano = service.usaIdPrimaMinuscola(clazz);
        message = String.format("%s%s%s", clazz.getSimpleName(), FORWARD, ottenutoBooleano ? "usa la prima minuscola" : "NON usa la prima minuscola");
        System.out.println(message);
    }


    @Test
    @Order(17)
    @DisplayName("17 - getStartupReset")
    void getStartupReset() {
        System.out.println(("17 - getStartupReset"));
        System.out.println(VUOTA);

        //--class
        //--previsto
        entity().forEach(this::fixStartupReset);
    }


    //--class
    //--previsto
    void fixStartupReset(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        previstoBooleano = (boolean) mat[1];
        String risultato;

        // Controlla che i parametri in ingresso siano validi
        if (!check(clazz, "fixStartupReset")) {
            return;
        }

        ottenutoBooleano = service.usaStartupReset(clazz);
        System.out.println(String.format("%s%s%s", clazz.getSimpleName(), FORWARD, ottenutoBooleano));
    }


    @ParameterizedTest
    @MethodSource(value = "view")
    @Order(20)
    @DisplayName("20 - getViewAnnotation")
    void getViewAnnotation(final Class clazz, boolean previsto) {
        previstoBooleano = previsto;
        AView annotation;
        clazzName = clazz != null ? clazz.getSimpleName() : VUOTA;

        annotation = service.getViewAnnotation(clazz);
        assertEquals(previstoBooleano, annotation != null);
        if (annotation != null) {
            System.out.println(VUOTA);
            printAView(clazz, annotation);
        }
        else {
            message = String.format("Non esiste l'annotation 'AView' nella classe [%s]", clazzName);
            System.out.println(message);
        }
    }


    @Test
    @Order(21)
    @DisplayName("21 - getMenuName")
    void getMenuName() {
        System.out.println(("21 - getMenuName"));
        System.out.println(("Titolo del menu di una view"));
        System.out.println((message));
        System.out.println(VUOTA);

        //--class
        //--previsto classe valida
        view().forEach(this::fixMenuName);
    }


    //--class
    //--previsto classe valida
    void fixMenuName(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        previstoBooleano = (boolean) mat[1];

        // Controlla che i parametri in ingresso siano validi
        if (!check(clazz, "fixMenuName")) {
            return;
        }

        ottenuto = service.getMenuName(clazz);
        message = String.format("%s%s%s", clazz.getSimpleName(), FORWARD, textService.isValid(ottenuto) ? ottenuto : NULLO);
        System.out.println(message);
    }

    @Test
    @Order(22)
    @DisplayName("22 - getMenuGroup")
    void getMenuGroup() {
        System.out.println(("22 - getMenuGroup"));
        System.out.println(("Titolo del gruppo di menu di una view"));
        System.out.println(VUOTA);

        view().forEach(this::fixMenuGroup);
    }

    //--class
    //--previsto classe valida
    void fixMenuGroup(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        previstoBooleano = (boolean) mat[1];
        MenuGroup gruppoOttenuto;

        // Controlla che i parametri in ingresso siano validi
        if (!check(clazz, "fixMenuGroup")) {
            return;
        }

        gruppoOttenuto = service.getMenuGroup(clazz);
        System.out.println(String.format("%s%s%s", clazz.getSimpleName(), FORWARD, gruppoOttenuto));
    }


    @Test
    @Order(23)
    @DisplayName("23 - getMenuGroupName")
    void getMenuGroupName() {
        System.out.println(("23 - getMenuGroupName"));
        System.out.println(("Titolo del gruppo di menu di una view"));
        System.out.println(VUOTA);

        view().forEach(this::fixMenuGroupName);
    }

    //--class
    //--previsto classe valida
    void fixMenuGroupName(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        previstoBooleano = (boolean) mat[1];

        // Controlla che i parametri in ingresso siano validi
        if (!check(clazz, "fixMenuGroupName")) {
            return;
        }

        ottenuto = service.getMenuGroupName(clazz);
        System.out.println(String.format("%s%s%s", clazz.getSimpleName(), FORWARD, ottenuto));
    }

    @Test
    @Order(24)
    @DisplayName("24 - usaMenuAutomatico")
    void usaMenuAutomatico() {
        System.out.println(("24 - usaMenuAutomatico"));
        System.out.println((message));
        System.out.println(VUOTA);

        //--class
        //--previsto classe valida
        view().forEach(this::fixUsaMenuAutomatico);
    }


    //--class
    //--previsto classe valida
    void fixUsaMenuAutomatico(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];

        // Controlla che i parametri in ingresso siano validi
        if (!check(clazz, "fixUsaMenuAutomatico")) {
            return;
        }

        ottenutoBooleano = service.usaMenuAutomatico(clazz);
        message = String.format("%s%s%s", clazz.getSimpleName(), FORWARD, ottenutoBooleano ? "usa il menu automatico" : "NON usa il menu automatico");
        System.out.println(message);
    }


    @Test
    @Order(25)
    @DisplayName("25 - getMenuIcon")
    void getMenuIcon() {
        System.out.println(("25 - getMenuIcon"));
        System.out.println((message));
        System.out.println(VUOTA);

        //--class
        //--previsto classe valida
        view().forEach(this::fixMenuIcon);
    }


    //--class
    //--previsto classe valida
    void fixMenuIcon(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        LineAwesomeIcon menuIcon;

        // Controlla che i parametri in ingresso siano validi
        if (!check(clazz, "fixMenuIcon")) {
            return;
        }

        menuIcon = service.getMenuIcon(clazz);
        message = String.format("%s%s%s", clazz.getSimpleName(), FORWARD, menuIcon);
        System.out.println(message);
    }


    @Test
    @Order(120)
    @DisplayName("120 - getField")
    void getField() {
        System.out.println(("120 - getField"));
        System.out.println(VUOTA);

        //--class
        //--property
        //--previsto
        field().forEach(this::fixField);
    }

    //--class
    //--property
    //--previsto
    void fixField(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        sorgente = (String) mat[1];
        previstoBooleano = (boolean) mat[2];
        AField annotation;

        annotation = service.getFieldAnnotation(clazz, sorgente);
        assertEquals(previstoBooleano, annotation != null);
        if (annotation != null) {
            System.out.println(VUOTA);
            this.printAField(clazz, sorgente, annotation);
        }
    }

    @Test
    @Order(121)
    @DisplayName("121 - getType")
    void getType() {
        System.out.println(("121 - getType"));
        System.out.println(VUOTA);

        //--class
        //--property
        //--previsto
        type().forEach(this::fixType);
    }

    //--class
    //--property
    //--previsto
    void fixType(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        sorgente = (String) mat[1];
        TypeField previsto = (TypeField) mat[2];

        typeField = service.getType(clazz, sorgente);
        assertEquals(previsto, typeField);
        if (typeField != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("Type della property [%s.%s]%s%s", clazz.getSimpleName(), sorgente, FORWARD, typeField));
        }
    }

    @Test
    @Order(122)
    @DisplayName("122 - getWidth")
    void getWidth() {
        System.out.println(("122 - getWidth"));
        System.out.println(("Width di una property"));
        System.out.println(VUOTA);

        //--class
        //--property
        //--previsto
        width().forEach(this::fixWidth);
    }

    //--class
    //--property
    //--previsto
    void fixWidth(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        sorgente = (String) mat[1];
        previsto = (String) mat[2];
        TypeField typeField;
        String widthStandard;
        String valore;

        ottenuto = service.getWidth(clazz, sorgente);
        assertEquals(previsto, ottenuto);
        if (textService.isValid(ottenuto)) {
            typeField = service.getType(clazz, sorgente);
            widthStandard = typeField.getWidthColumn();
            valore = ottenuto.equals(widthStandard) ? VALORE_STANDARD : VALORE_SPECIFICO;
            System.out.println(VUOTA);
            System.out.println(String.format("Width della property [%s.%s] di tipo [%s]%s%s (%s)", clazz.getSimpleName(), sorgente, typeField, FORWARD, ottenuto, valore));
        }
    }

    @Test
    @Order(123)
    @DisplayName("123 - getHeader")
    void getHeader() {
        System.out.println(("123 - getHeader"));
        System.out.println(("Header di una property"));
        System.out.println(VUOTA);

        //--class
        //--property
        //--previsto
        header().forEach(this::fixHeader);
    }

    //--class
    //--property
    //--previsto
    void fixHeader(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        sorgente = (String) mat[1];
        previsto = (String) mat[2];
        String valore;

        ottenuto = service.getHeaderText(clazz, sorgente);
        assertEquals(previsto, ottenuto);
        if (textService.isValid(ottenuto)) {
            valore = ottenuto.equals(textService.primaMaiuscola(sorgente)) ? VALORE_STANDARD : VALORE_SPECIFICO;
            System.out.println(VUOTA);
            System.out.println(String.format("Header della property [%s.%s]%s%s (%s)", clazz.getSimpleName(), sorgente, FORWARD, ottenuto, valore));
        }
    }


    /**
     * Check generico dei parametri.
     *
     * @param genericClazz da controllare
     * @param testName     the test name
     *
     * @return false se mancano parametri o non sono validi
     */
    protected boolean check(final Class genericClazz, final String testName) {
        String message;

        // Controlla che la classe in ingresso non sia nulla
        if (genericClazz == null) {
            message = String.format("Nel test '%s' di [%s], manca la clazz '%s'", testName, this.getClass().getSimpleName(), "(null)");
            System.out.println(message);//@todo Da modificare dopo aver realizzato logService
            return false;
        }

        // Controlla che la classe in ingresso implementi AlgosModel
        if (AbstractEntity.class.isAssignableFrom(genericClazz) || CrudView.class.isAssignableFrom(genericClazz)) {
        }
        else {
            message = String.format("Nel test '%s' di [%s], la classe '%s' non implementa AbstractEntity o CrudView", testName, this.getClass().getSimpleName(), genericClazz.getSimpleName());
            System.out.println(message);//@todo Da modificare dopo aver realizzato logService
            return false;
        }

        return true;
    }

    protected void printAField(final Class clazz, final String sorgente, final AField annotation) {
        if (clazz == null || sorgente == null || sorgente == VUOTA || annotation == null) {
            return;
        }

        message = String.format("Annotation della property '%s' della classe [%s]:", sorgente, clazz.getSimpleName());
        System.out.println(message);

        message = String.format("Annotation%s%s", FORWARD, annotation.annotationType());
        System.out.println(message);
        message = String.format("type%s%s", FORWARD, annotation.type());
        System.out.println(message);
        message = String.format("widthRem%s%s", FORWARD, annotation.widthRem());
        System.out.println(message);
        message = String.format("header%s%s", FORWARD, annotation.headerText());
        System.out.println(message);
    }


    protected void printAEntity(final Class clazz, final AEntity annotation) {
        if (clazz == null || annotation == null) {
            return;
        }

        message = String.format("Annotation '%s' della classe [%s] (di tipo AbstractEntity):", "@AEntity", clazz.getSimpleName());
        System.out.println(message);

        message = String.format("Annotation%s%s", FORWARD, annotation.annotationType());
        System.out.println(message);
        message = String.format("collectionName%s%s", FORWARD, annotation.collectionName());
        System.out.println(message);
        message = String.format("keyPropertyName%s%s", FORWARD, annotation.keyPropertyName());
        System.out.println(message);
        message = String.format("searchPropertyName%s%s", FORWARD, annotation.searchPropertyName());
        System.out.println(message);
        message = String.format("sortPropertyName%s%s", FORWARD, annotation.sortPropertyName());
        System.out.println(message);
        message = String.format("typeList%s%s", FORWARD, annotation.typeList());
        System.out.println(message);
        message = String.format("usaIdPrimaMinuscola%s%s", FORWARD, annotation.usaIdPrimaMinuscola());
        System.out.println(message);
    }


    protected void printAView(final Class clazz, final AView annotation) {
        if (clazz == null || annotation == null) {
            return;
        }

        message = String.format("Annotation '%s' della classe [%s] (di tipo CrudView):", "@AView", clazz.getSimpleName());
        System.out.println(message);

        message = String.format("Annotation%s%s", FORWARD, annotation.annotationType());
        System.out.println(message);
        message = String.format("menuName%s%s", FORWARD, annotation.menuName());
        System.out.println(message);
        message = String.format("menuGroup%s%s", FORWARD, annotation.menuGroup());
        System.out.println(message);
        message = String.format("menuGroupName%s%s", FORWARD, annotation.menuGroupName());
        System.out.println(message);
        message = String.format("menuAutomatico%s%s", FORWARD, annotation.menuAutomatico());
        System.out.println(message);
        message = String.format("menuIcon%s%s", FORWARD, annotation.menuIcon());
        System.out.println(message);
    }

    protected void printProperty(final Class clazz, String propertyValue) {
        if (clazz == null) {
            return;
        }

        sorgente = clazz.getSimpleName();
        listaStr = reflectionService.getFieldNames(clazz);
        assertEquals(previstoBooleano, textService.isValid(ottenuto));
        message = String.format("%s%s%s%s%s%s%s", sorgente, SPAZIO, listaStr, FORWARD, PARENTESI_TONDA_INI, propertyValue, PARENTESI_TONDA_END);
        System.out.println(message);
    }

}
