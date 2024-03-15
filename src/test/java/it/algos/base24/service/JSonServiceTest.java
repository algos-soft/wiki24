package it.algos.base24.service;

import it.algos.*;
import it.algos.base24.basetest.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 22-ott-2023
 * Time: 11:27
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("JSon Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JSonServiceTest extends ServiceTest {

    @Autowired
    private JSonService service;

    @Autowired
    private TextService textService;

//    @Autowired
//    private MeseModulo meseModulo;
    // @todo ATTENTION QUI

    protected AbstractEntity entityBean;

    protected Map<String, Object> mappaObject;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = JSonService.class;
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
    @DisplayName("10 - getMappa")
    void getMappa() {
        System.out.println(("10 - getMappa"));
        System.out.println(VUOTA);
        int giorni = 31;
        String sigla = "ago";
        String nome = "agosto";
        int primo = 214;
        int ultimo = 244;

        // @todo ATTENTION QUI
//        MeseModel mese = (MeseModel) meseModulo.findOneByKey(nome);
//        assertNotNull(mese);
//
//        mappaObject = service.getMappa(mese);
//        assertNotNull(mappaObject);
//        assertEquals(mappaObject.size(), 7);
//
//        assertEquals(mappaObject.get("giorni"), giorni);
//        assertEquals(mappaObject.get("sigla"), sigla);
//        assertEquals(mappaObject.get("nome"), nome);
//        assertEquals(mappaObject.get("primo"), primo);
//        assertEquals(mappaObject.get("ultimo"), ultimo);

        printMappa(mappaObject);
    }

    @Test
    @Order(11)
    @DisplayName("11 - getMappaModifiche")
    void getMappaModifiche() {
        System.out.println(("11 - getMappaModifiche"));
        System.out.println(VUOTA);
        Map<String, Object> mappaAlfa;
        Map<String, Object> mappaBeta;

        // @todo ATTENTION QUI
//        MeseModel mese = (MeseModel) meseModulo.findOneByKey("gennaio");
//        assertNotNull(mese);
//        mappaAlfa = service.getMappa(mese);
//        assertNotNull(mappaAlfa);
//        assertEquals(mappaAlfa.size(), 7);
//
//        mappaBeta = service.getMappa(mese);
//        mappaBeta.put("sigla", "Jan");
//        mappaBeta.put("nome", "January");
//
//        mappaObject = service.getMappaModifiche(mappaAlfa, mappaBeta);
        printMappa(mappaObject);
    }


    @Test
    @Order(12)
    @DisplayName("12 - getMappaModifiche2")
    void getMappaModifiche2() {
        System.out.println(("12 - getMappaModifiche2"));
        System.out.println(VUOTA);

        // @todo ATTENTION QUI
//        MeseModel meseOld = (MeseModel) meseModulo.findOneByKey("luglio");
//        assertNotNull(meseOld);

        // @todo ATTENTION QUI
//        MeseModel meseNew = (MeseModel) meseModulo.findOneByKey("luglio");
//        assertNotNull(meseNew);

//        meseNew.sigla = "Jul";
//        meseNew.nome = "July";

//        mappaObject = service.getMappaModifiche(meseOld, meseNew);
        printMappa(mappaObject);
    }

    @Test
    @Order(13)
    @DisplayName("13 - getMappaModifiche3")
    void getMappaModifiche3() {
        System.out.println(("13 - getMappaModifiche3"));
        System.out.println(VUOTA);

        // @todo ATTENTION QUI
//        MeseModel meseOld = (MeseModel) meseModulo.findOneByKey("agosto");
//        assertNotNull(meseOld);
//
//        MeseModel meseNew = (MeseModel) meseModulo.findOneByKey("agosto");
//        assertNotNull(meseNew);
//
//        meseOld.sigla = null;
//        meseNew.nome = null;
//
//        mappaObject = service.getMappaModifiche(meseOld, meseNew);
        printMappa(mappaObject);
        System.out.println(VUOTA);
        System.out.println(mappaObject);
    }


    @Test
    @Order(14)
    @DisplayName("14 - getModifiche2")
    void getModifiche() {
        System.out.println(("14 - getModifiche2"));
        System.out.println(VUOTA);

        // @todo ATTENTION QUI
//        MeseModel meseOld = (MeseModel) meseModulo.findOneByKey("agosto");
//        assertNotNull(meseOld);
//
//        MeseModel meseNew = (MeseModel) meseModulo.findOneByKey("agosto");
//        assertNotNull(meseNew);
//
//        meseOld.sigla = null;
//        meseNew.nome = null;
//
//        ottenuto = service.getModifiche(meseOld, meseNew);
//        assertTrue(textService.isValid(ottenuto));
        System.out.println(ottenuto);
    }

}