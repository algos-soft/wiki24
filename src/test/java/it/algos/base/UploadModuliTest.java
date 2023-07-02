package it.algos.base;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.upload.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 10-Apr-2023
 * Time: 16:47
 */
public abstract class UploadModuliTest extends WikiTest {

    protected Class moduloClazz;

    protected UploadModulo istanza;


    @Test
    @Order(2)
    @DisplayName("2 - getBean base senza parametri")
    void getBean() {
        System.out.println(("2 - getBean base senza parametri"));
        System.out.println(VUOTA);

        istanza = (UploadModulo) appContext.getBean(moduloClazz);
        assertNotNull(istanza);
        System.out.println(String.format("getBean base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    @Test
    @Order(3)
    @DisplayName("3 - Titolo pagina wiki del modulo")
    void getWikiTitleModulo() {
        System.out.println(("3 - Titolo pagina wiki del modulo"));
        System.out.println(VUOTA);

        istanza = (UploadModulo) appContext.getBean(moduloClazz);
        assertNotNull(istanza);
        ottenuto = istanza.getWikiTitleModulo();
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Titolo della pagina wiki da cui estrarre il modulo%s'%s'", FORWARD, ottenuto);
        System.out.println(message);
    }

    @Test
    @Order(4)
    @DisplayName("4 - Titolo pagina test su cui scrivere il modulo ordinato")
    void getWikiTitleUpload() {
        System.out.println(("4 - Titolo pagina test su cui scrivere il modulo ordinato"));
        System.out.println(VUOTA);

        istanza = (UploadModulo) appContext.getBean(moduloClazz);
        assertNotNull(istanza);
        ottenuto = istanza.getWikiTitleUpload();
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Titolo della pagina test su cui scrivere il modulo ordinato%s'%s'", FORWARD, ottenuto);
        System.out.println(message);
    }


    @Test
    @Order(5)
    @DisplayName("5 - Testo della pagina wiki del modulo")
    void getTextPagina() {
        System.out.println(("5 - Testo della pagina wiki del modulo"));
        System.out.println(VUOTA);

        ottenuto = ((UploadModulo) appContext.getBean(moduloClazz)).leggeTestoPagina();
        assertTrue(textService.isValid(ottenuto));
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @Test
    @Order(6)
    @DisplayName("6 - Testo del modulo")
    void leggeTestoModulo() {
        System.out.println(("6 - Testo del modulo"));
        System.out.println(VUOTA);

        ottenuto = ((UploadModulo) appContext.getBean(moduloClazz)).leggeTestoModulo();
        assertTrue(textService.isValid(ottenuto));
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @Test
    @Order(7)
    @DisplayName("7 - Mappa dei dati del modulo")
    void leggeMappa() {
        System.out.println(("7 - Mappa dei dati del modulo"));
        System.out.println(VUOTA);

        mappaOttenuta = ((UploadModulo) appContext.getBean(moduloClazz)).leggeMappa();
        assertNotNull(mappaOttenuta);
        printMappa(mappaOttenuta);
    }


    @Test
    @Order(8)
    @DisplayName("8 - Mappa ordinata key dei dati del modulo")
    void getMappaOrdinataKey() {
        System.out.println(("8 - Mappa ordinata key dei dati del modulo"));
        System.out.println(VUOTA);

        mappaOttenuta = ((UploadModulo) appContext.getBean(moduloClazz)).getMappaOrdinataKey();
        assertNotNull(mappaOttenuta);
        printMappa(mappaOttenuta);
    }


    @Test
    @Order(9)
    @DisplayName("9 - Mappa ordinata value dei dati del modulo")
    void getMappaOrdinataValue() {
        System.out.println(("9 - Mappa ordinata value dei dati del modulo"));
        System.out.println(("Ordinata sul plurale (value) e non sul singolare"));
        System.out.println(VUOTA);

        mappaOttenuta = ((UploadModulo) appContext.getBean(moduloClazz)).getMappaOrdinataValue();
        assertNotNull(mappaOttenuta);
        printMappa(mappaOttenuta);
    }

    @Test
    @Order(10)
    @DisplayName("10 - Upload mappa ordinata key su pagina di test")
    void uploadOrdinatoSenzaModifiche() {
        System.out.println(("10 - Upload mappa ordinata key su pagina di test"));
        System.out.println(VUOTA);

        ottenutoRisultato = ((UploadModulo) appContext.getBean(moduloClazz)).uploadOrdinatoSenzaModifiche();
        printRisultato(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
    }

    //    @Test
    @Order(11)
    @DisplayName("11 - Upload mappa ordinata value su pagina di test")
    void uploadOrdinatoValoreSenzaModifiche() {
        System.out.println(("11 - Upload mappa ordinata value su pagina di test"));
        System.out.println(("Ordinata sul plurale (value) e non sul singolare"));
        System.out.println(VUOTA);

        ottenutoRisultato = ((UploadModulo) appContext.getBean(moduloClazz)).uploadOrdinatoValoreSenzaModifiche();
        printRisultato(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
    }

}
