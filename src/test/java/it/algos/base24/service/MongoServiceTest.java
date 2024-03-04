package it.algos.base24.service;

import com.mongodb.client.*;
import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.boot.*;
import it.algos.base24.backend.packages.utility.role.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.basetest.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sun, 22-Oct-2023
 * Time: 09:07
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Mongo Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MongoServiceTest extends ServiceTest {

    @Autowired
    private MongoService service;

    @Autowired
    private AnnotationService annotationService;

    private MongoDatabase dataBase;

    private MongoClient mongoClient;

    private MongoCollection collection;

    private List<String> listaCollectionsName;

    private List<MongoCollection> listaCollections;

    //--clazz
    //--esiste
    private Stream<Arguments> collezioniClasse() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(RoleView.class, false),
                //                Arguments.of(ViaView.class, true),
                //                Arguments.of(ViaEntity.class, true),
                Arguments.of(RoleEntity.class, true)
        );
    }

    //--clazz
    //--esiste
    private Stream<Arguments> countCollection() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(RoleView.class, false),
                //                Arguments.of(ViaView.class, true),
                //                Arguments.of(ViaEntity.class, true),
                Arguments.of(RoleEntity.class, true)
        );
    }


    //--nome
    //--esiste
    private Stream<Arguments> collezioniNome() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
                Arguments.of("nomeErrato", false),
                Arguments.of("role", true),
                Arguments.of("continenteModel", false),
                Arguments.of("Continente", false)
        );
    }


    //--clazz
    //--keyName
    //--keyValue
    //--errore
    //--esiste
    private Stream<Arguments> existsKeyValue() {
        return Stream.of(
                Arguments.of(null, VUOTA, VUOTA, true, false),
                Arguments.of(RoleView.class, VUOTA, VUOTA, true, false),
                Arguments.of(RoleEntity.class, VUOTA, VUOTA, true, false),
                Arguments.of(RoleEntity.class, FIELD_NAME_DESCRIZIONE, VUOTA, true, false),
                Arguments.of(RoleEntity.class, FIELD_NAME_NOME, VUOTA, true, false),
                Arguments.of(RoleEntity.class, FIELD_NAME_NOME, "errato", false, false)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = MongoService.class;
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


    @Test
    @Order(10)
    @DisplayName("10 - getDB")
    void getDB() {
        System.out.println(("10 - getDB"));
        System.out.println(("Controllo del nome e dell'esistenza del DB 'corrente'"));
        System.out.println(VUOTA);

        sorgente = BaseVar.mongoDatabaseName;
        previsto = sorgente;
        dataBase = service.getDB(sorgente);
        assertNotNull(dataBase);
        ottenuto = dataBase.getName();
        assertEquals(previsto, ottenuto);
        System.out.println(VUOTA);
        System.out.println(String.format("Controllo nome del dataBase [corrente]%s%s", FORWARD, ottenuto));
    }


    @Test
    @Order(11)
    @DisplayName("11 - getDBAdmin")
    void getDBAdmin() {
        System.out.println(("11 - getDBAdmin"));
        System.out.println(("Controllo del nome e dell'esistenza del DB 'admin' in 'system'"));
        System.out.println(VUOTA);

        previsto = DATABASE_ADMIN;
        dataBase = service.getDBAdmin();
        assertNotNull(dataBase);
        ottenuto = dataBase.getName();
        assertEquals(previsto, ottenuto);
        System.out.println(VUOTA);
        System.out.println(String.format("Controllo nome del dataBase [admin]%s%s", FORWARD, ottenuto));
    }


    @Test
    @Order(12)
    @DisplayName("12 - versione")
    void versione() {
        System.out.println("12 - versione");
        System.out.println(VUOTA);

        ottenuto = service.versione();
        System.out.println(VUOTA);
        message = String.format("Versione corrente del mongoDb installato: %s", ottenuto);
        System.out.println(message);
    }

    @Test
    @Order(13)
    @DisplayName("13 - getCollezioni")
    void getCollezioni() {
        System.out.println(("13 - getCollezioni"));
        System.out.println(("Lista dei nomi delle 'collections' esistenti"));
        System.out.println(VUOTA);

        listaCollectionsName = service.getCollezioni();
        assertNotNull(listaCollectionsName);
        printLista(listaCollectionsName);
    }

    @Test
    @Order(20)
    @DisplayName("20 - existsCollectionName")
    void existsCollectionName() {
        System.out.println(("20 - existsCollectionName"));
        System.out.println(VUOTA);

        //--nome
        //--esiste
        collezioniNome().forEach(this::fixEsisteName);
    }

    //--nome
    //--esiste
    void fixEsisteName(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];

        ottenutoBooleano = service.existsCollectionName(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        if (ottenutoBooleano) {
            System.out.println(String.format("La collection [%s] ESISTE", sorgente));
        }
        else {
            System.out.println(String.format("La collection [%s] NON esiste", sorgente));
        }
    }


    @Test
    @Order(21)
    @DisplayName("21 - existsCollectionClazz")
    void existsCollectionClazz() {
        System.out.println(("21 - existsCollectionClazz"));
        System.out.println(VUOTA);

        //--nome
        //--esiste
        collezioniClasse().forEach(this::fixEsisteClasse);
    }

    //--nome
    //--esiste
    void fixEsisteClasse(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        previstoBooleano = (boolean) mat[1];
        clazzName = clazz != null ? clazz.getSimpleName() : NULLO;
        sorgente = annotationService.getCollectionName(clazz);

        ottenutoBooleano = service.existsCollectionClazz(clazz);
        if (ottenutoBooleano) {
            System.out.println(String.format("La collection [%s], corrispondente alla modelClazz [%s], ESISTE", sorgente, clazzName));
        }
        else {
            System.out.println(String.format("La collection [%s], corrispondente alla modelClazz [%s], NON resiste", sorgente, clazzName));
        }
    }


    @Test
    @Order(30)
    @DisplayName("30 - count")
    void count() {
        System.out.println(("30 - count"));
        System.out.println(VUOTA);

        //--nome
        //--esiste
        countCollection().forEach(this::fixCount);
    }

    //--nome
    //--esiste
    void fixCount(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        previstoBooleano = (boolean) mat[1];
        clazzName = clazz != null ? clazz.getSimpleName() : NULLO;
        sorgente = annotationService.getCollectionName(clazz);

        ottenutoIntero = service.count(clazz);
        assertEquals(previstoBooleano, ottenutoIntero > 0);
        System.out.println(String.format("La collection [%s], corrispondente alla modelClazz '%s', ha '%d' entities", sorgente, clazzName, ottenutoIntero));
    }


    @Test
    @Order(40)
    @Disabled()
    @DisplayName("40 - insert")
    void insert() {
        System.out.println(("40 - insert"));
        System.out.println(VUOTA);
        //        DeleteResult risultato;
        //        ContinenteModel continenteNew = new ContinenteModel();
        //        ContinenteModel continenteRisultante;
        //        continenteNew.id = "xyz";
        //        continenteNew.nome = "Paperino";
        //
        //        continenteRisultante = (ContinenteModel) service.insert(continenteNew);
        //        assertNotNull(continenteRisultante);
        //        System.out.println(String.format("Creata la entity [%s]", continenteRisultante.nome));
        //
        //        risultato = service.delete(continenteRisultante);
        //        ottenutoIntero = ((Long) risultato.getDeletedCount()).intValue();
        //        assertTrue(ottenutoIntero == 1);
        //        System.out.println(String.format("Cancellata la entity [%s]", continenteRisultante.nome));
    }


    @Test
    @Order(50)
    @DisplayName("50 - exists")
    void exists() {
        System.out.println(("50 - exists"));
        System.out.println(VUOTA);

        //--collectionName
        //--keyName
        //--keyValue
        //--errore
        //--esiste
        existsKeyValue().forEach(this::fixExists);
    }

    //--collectionName
    //--keyName
    //--keyValue
    //--errore
    //--esiste
    void fixExists(Arguments arg) {
        Object[] mat = arg.get();
        clazz = (Class) mat[0];
        String keyName = (String) mat[1];
        Object keyValue = mat[2];
        previstoBooleano = (boolean) mat[3];
        boolean previstoBooleano = (boolean) mat[4];

        ottenutoBooleano = service.exists(clazz, keyName, keyValue);
        if (ottenutoBooleano) {
            ottenuto = annotationService.getCollectionName(clazz);
            System.out.println(String.format("Nella collection '%s' (campo chiave=%s) esiste la entity [%s.%s]", ottenuto, keyName, ottenuto, keyValue));
            System.out.println(VUOTA);
        }
        else {
            ottenuto = annotationService.getCollectionName(clazz);
            System.out.println(String.format("Nella collection '%s' (campo chiave=%s) NON esiste la entity [%s.%s]", ottenuto, keyName, ottenuto, keyValue));
            System.out.println(VUOTA);
        }

        //        if (previstoBooleano != ottenutoBooleano) {
        //            System.out.println(String.format("Nella collection '%s' (campo chiave=%s) la entity [%s.%s] non sembra adeguata", ottenuto, keyName, ottenuto, keyValue));
        //            System.out.println(VUOTA);
        //        }
    }



}
