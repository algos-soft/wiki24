package it.algos.base24.service;

import it.algos.*;
import it.algos.base24.basetest.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.list.*;
import it.algos.vbase.backend.logic.*;
import it.algos.vbase.backend.packages.geografia.continente.*;
import it.algos.vbase.backend.packages.geografia.stato.*;
import it.algos.vbase.backend.packages.utility.role.*;
import it.algos.vbase.backend.service.*;
import it.algos.vbase.ui.view.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sun, 22-Oct-2023
 * Time: 07:12
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Reflection Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReflectionServiceTest extends ServiceTest {

    @Autowired
    private ReflectionService service;

    @Autowired
    private TextService textService;

    private List<Method> methods;

    //--class
    private static Stream<Arguments> CLASSI() {
        return Stream.of(
                Arguments.of(RoleView.class),
                Arguments.of(RoleEntity.class),
                Arguments.of(StatoEntity.class),
                Arguments.of(ContinenteEntity.class),
                Arguments.of(ContinenteList.class),
                Arguments.of(RoleModulo.class),
                Arguments.of(RoleList.class)
        );
    }

    //--clazz
    //--method name
    //--esiste
    protected static Stream<Arguments> CLASSI_METHOD() {
        return Stream.of(
                Arguments.of(null, "creaExcelExporter", false),
                Arguments.of(RoleList.class, VUOTA, false),
                Arguments.of(CrudView.class, "creaExcelExporter", false),
                Arguments.of(TypeColor.class, "creaExcelExporter", false),
                Arguments.of(Mese.class, "creaExcelExporter", false),
                Arguments.of(RoleModulo.class, "creaExcelExporter", false),
                //                Arguments.of(ViaModel.class, "creaExcelExporter", false),
                Arguments.of(RoleList.class, "creaExcelExporter", true)
        );
    }

    //--master clazz
    //--path
    //--esistono subClassi
    protected static Stream<Arguments> CLASSI_MASTER() {
        return Stream.of(
                Arguments.of(null, PATH_ALGOS, false),
                Arguments.of(CrudView.class, VUOTA, false),
                Arguments.of(RoleList.class, PATH_ALGOS, false),
                Arguments.of(RoleEntity.class, PATH_ALGOS, false),
                Arguments.of(StatoEntity.class, PATH_ALGOS, false),
                Arguments.of(ContinenteEntity.class, PATH_ALGOS, false),
                Arguments.of(CrudView.class, PATH_ALGOS, true),
                Arguments.of(TypeColor.class, PATH_ALGOS, false),
                Arguments.of(Mese.class, PATH_ALGOS, false),
                Arguments.of(CrudModulo.class, PATH_ALGOS, true),
                Arguments.of(AbstractEntity.class, PATH_ALGOS, true),
                Arguments.of(CrudList.class, PATH_ALGOS, true)
        );
    }


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ReflectionService.class;
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
        this.methods = null;
    }

    @Test
    @Order(10)
    @DisplayName("10 - getJavaField")
    void getJavaField() {
        System.out.println(("10 - getJavaField"));
        System.out.println(VUOTA);
        Field field;
        clazz = RoleEntity.class;

        sorgente = "code";
        field = service.getJavaField(null, sorgente);
        assertNull(field);
        message = String.format("Nel metodo '%s' manca la classe", "getJavaField()");
        System.out.println(message);

        sorgente = VUOTA;
        field = service.getJavaField(clazz, sorgente);
        assertNull(field);
        message = String.format("Nel metodo '%s' della classe [%s] manca il nome della property", "getJavaField()", clazz.getSimpleName());
        System.out.println(message);

        sorgente = "pippoz";
        field = service.getJavaField(clazz, sorgente);
        assertNull(field);
        message = String.format("Nella classe [%s] non esiste la property '%s'", clazz.getSimpleName(), sorgente);
        System.out.println(message);

        sorgente = "code";
        field = service.getJavaField(clazz, sorgente);
        assertNotNull(field);
        message = String.format("Trovato il field '%s' nella classe [%s] -> %s", sorgente, clazz.getSimpleName(), field.getName());
        System.out.println(message);
    }


    @Test
    @Order(11)
    @DisplayName("11 - isEsiste")
    void isEsiste() {
        System.out.println(("11 - isEsiste"));
        System.out.println(VUOTA);
        clazz = RoleEntity.class;

        sorgente = "code";
        ottenutoBooleano = service.isEsiste(null, sorgente);
        assertFalse(ottenutoBooleano);
        ottenutoBooleano = service.nonEsiste(null, sorgente);
        assertTrue(ottenutoBooleano);
        message = String.format("Nel metodo '%s' manca la classe", "isEsiste()");
        System.out.println(message);

        sorgente = VUOTA;
        ottenutoBooleano = service.isEsiste(clazz, sorgente);
        assertFalse(ottenutoBooleano);
        ottenutoBooleano = service.nonEsiste(clazz, sorgente);
        assertTrue(ottenutoBooleano);
        message = String.format("Nel metodo '%s' della classe [%s] manca il nome della property", "isEsiste()", clazz.getSimpleName());
        System.out.println(message);

        sorgente = "pippoz";
        ottenutoBooleano = service.isEsiste(clazz, sorgente);
        assertFalse(ottenutoBooleano);
        ottenutoBooleano = service.nonEsiste(clazz, sorgente);
        assertTrue(ottenutoBooleano);
        message = String.format("Nella classe [%s] non esiste la property '%s'", clazz.getSimpleName(), sorgente);
        System.out.println(message);

        sorgente = "code";
        ottenutoBooleano = service.isEsiste(clazz, sorgente);
        assertTrue(ottenutoBooleano);
        ottenutoBooleano = service.nonEsiste(clazz, sorgente);
        assertFalse(ottenutoBooleano);
        message = String.format("Trovato il field '%s' nella classe [%s] -> %s", sorgente, clazz.getSimpleName(), sorgente);
        System.out.println(message);
    }

    @ParameterizedTest
    @MethodSource(value = "CLASSI")
    @Order(20)
    @DisplayName("20 - getMethods (Method)")
    void getMethods(final Class clazz) {
        System.out.println(("20 - getMethods (Method)"));
        System.out.println(VUOTA);

        methods = service.getMethods(clazz);
        if (methods.size() == 0) {
            message = String.format("La classe %s non ha nessun metodo (diretto)", clazz.getSimpleName());
            System.out.println(message);
        }
        else {
            message = String.format("La classe %s ha %d metodi (diretti)", clazz.getSimpleName(), methods.size());
            System.out.println(message);
            for (Method metodo : methods) {
                System.out.print(metodo.getName());
                System.out.print(PARENTESI_TONDA_INI);
                if (metodo.getParameterCount() > 0) {
                    message = VUOTA;
                    for (Parameter par : metodo.getParameters()) {
                        message += par.getType().getSimpleName();
                        message += SPAZIO;
                        message += par.getName();
                        message += VIRGOLA_SPAZIO;
                    }
                    message = message.trim();
                    message = textService.levaCoda(message, VIRGOLA);
                    System.out.print(message);
                }
                System.out.println(PARENTESI_TONDA_END);
            }
        }
    }


    @ParameterizedTest
    @MethodSource(value = "CLASSI")
    @Order(21)
    @DisplayName("21 - getMethodNames (String)")
    void getMethodNames(final Class clazz) {
        System.out.println(("21 - getMethodNames (String)"));
        System.out.println(VUOTA);

        listaStr = service.getMethodNames(clazz);
        if (listaStr.size() == 0) {
            message = String.format("La classe %s non ha nessun metodo (diretto)", clazz.getSimpleName());
            System.out.println(message);
        }
        else {
            message = String.format("La classe %s ha %d metodi (diretti)", clazz.getSimpleName(), listaStr.size());
            System.out.println(message);
            System.out.println(VUOTA);
            printLista(listaStr);
        }
        System.out.println(VUOTA);
    }


    @ParameterizedTest
    @MethodSource(value = "CLASSI_METHOD")
    @Order(30)
    @DisplayName("30 - isEsisteMetodo")
    void isEsisteMetodo(final Class clazz, final String nomeMetodo, final boolean previsto) {
        System.out.println(("30 - isEsisteMetodo"));
        System.out.println(VUOTA);

        ottenutoBooleano = service.isEsisteMetodo(clazz, nomeMetodo);
        assertEquals(previsto, ottenutoBooleano);
        if (ottenutoBooleano) {
            message = String.format("SI - Nella classe %s esiste il metodo %s()", clazz.getSimpleName(), nomeMetodo);
            System.out.println(message);
        }
        else {
            message = String.format("NO - Nella classe %s non esiste il metodo %s()", clazz != null ? clazz.getSimpleName() : NULLO, nomeMetodo);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "CLASSI_MASTER")
    @Order(40)
    @DisplayName("40 - getSubClazz")
    void getSubClazz(final Class masterClazz, String path, final boolean previsto) {
        System.out.println(("40 - getSubClazz"));
        System.out.println(VUOTA);

        List<Class> lista = service.getSubClazz(masterClazz, path);
        assertEquals(previsto, lista != null);

        if (masterClazz == null) {
            message = "Manca la masterClazz nel test";
            System.out.println(message);
            return;
        }
        else {
            if (textService.isEmpty(path)) {
                message = String.format("Non può esaminare la classe [%s] perché nel test manca il path", masterClazz.getSimpleName());
                System.out.println(message);
                return;
            }
        }

        if (previsto) {
            message = String.format("Esamina la superClasse [%s]. Ci sono %d sottoclassi che la implementano", masterClazz.getSimpleName(), lista.size());
            System.out.println(message);
            super.printClazz(lista);
        }
        else {
            message = String.format("Non ci sono sottoClassi della classe [%s]", masterClazz.getSimpleName());
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "CLASSI_MASTER")
    @Order(50)
    @DisplayName("50 - getSuperClazz")
    void getSuperClazz(final Class genericClazz) {
        System.out.println(("50 - getSuperClazz"));
        System.out.println(VUOTA);

        if (genericClazz == null) {
            message = "Manca la clazz nel test";
            System.out.println(message);
            return;
        }

        List<Class> lista = service.getSuperClazz(genericClazz);

        if (lista.size() == 0) {
            message = String.format("La classe [%s] non ha nessuna superClasse", genericClazz.getSimpleName());
            System.out.println(message);
        }
        else {
            message = String.format("La classe [%s] estende le '%d' superClassi in ordine crescente:", genericClazz.getSimpleName(), lista.size());
            System.out.println(message);
            super.printClazz(lista);
        }
    }

    @Test
    @Order(41)
    @DisplayName("41 - getSubClazz per type")
    void getSubClazz2() {
        System.out.println(("41 - getSubClazz per type"));
        List<Class> lista;

        lista = service.getSubClazzEntity();
        printSubClazz(lista, "AbstractEntity");

        lista = service.getSubClazzViewBase();
        printSubClazz(lista, "CrudView");

        lista = service.getSubClazzViewProgetto();
        printSubClazz(lista, "CrudView");

        lista = service.getSubClazzModulo();
        printSubClazz(lista, "CrudModulo");

        lista = service.getSubClazzList();
        printSubClazz(lista, "CrudList");

        lista = service.getSubClazzForm();
        printSubClazz(lista, "CrudForm");
    }


    @ParameterizedTest
    @MethodSource(value = "CLASSI_MASTER")
    @Order(60)
    @DisplayName("60 - getFields (Field)")
    void getFields(final Class genericClazz) {
        System.out.println(("60 - getFields (Field)"));
        System.out.println(VUOTA);

        if (genericClazz == null) {
            message = "Manca la clazz nel test";
            System.out.println(message);
            return;
        }

        fieldsArray = service.getFields(genericClazz);
        // Controlla che la classe in ingresso implementi AbstractEntity
        if (fieldsArray == null && !AbstractEntity.class.isAssignableFrom(genericClazz)) {
            message = String.format("La classe [%s] del test non implementa AbstractEntity", genericClazz.getSimpleName());
            System.out.println(message);
            return;
        }
        message = String.format("Nella classe [%s] ci sono %d fields (property):", genericClazz.getSimpleName(), fieldsArray.size());
        System.out.println(message);
        printField(fieldsArray);
    }


    @ParameterizedTest
    @MethodSource(value = "CLASSI_MASTER")
    @Order(70)
    @DisplayName("70 - getFieldNames (String)")
    void getFieldNames(final Class genericClazz) {
        System.out.println(("70 - getFieldNames (String)"));
        System.out.println(VUOTA);

        if (genericClazz == null) {
            message = "Manca la clazz nel test";
            System.out.println(message);
            return;
        }

        listaStr = service.getFieldNames(genericClazz);
        // Controlla che la classe in ingresso implementi AbstractEntity
        if (listaStr == null && !AbstractEntity.class.isAssignableFrom(genericClazz)) {
            message = String.format("La classe [%s] del test non implementa AbstractEntity", genericClazz.getSimpleName());
            System.out.println(message);
            return;
        }
        message = String.format("Nella classe [%s] ci sono %d fields (property):", genericClazz.getSimpleName(), listaStr.size());
        System.out.println(message);
        printLista(listaStr);
    }

    private void printSubClazz(List<Class> lista, String superClasse) {
        assertNotNull(lista);
        message = String.format("Nel path %s ci sono %d sottoclassi che implementano [%s].", PATH_ALGOS, lista.size(), superClasse);
        System.out.println(message);
        for (Class clazz : lista) {
            System.out.println(clazz.getSimpleName());
        }
        System.out.println(VUOTA);
    }

}
