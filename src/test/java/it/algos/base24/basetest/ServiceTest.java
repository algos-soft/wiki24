package it.algos.base24.basetest;

import it.algos.vbase.backend.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.service.*;
import org.junit.jupiter.api.*;
import org.springframework.context.*;

import javax.inject.*;
import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Tue, 08-Aug-2023
 * Time: 18:27
 */
public abstract class ServiceTest extends AlgosTest {

    @Inject
    protected ApplicationContext applicationContext;

    @Inject
    protected LogService logger;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazzName = clazz != null ? clazz.getSimpleName() : VUOTA;
    }

    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        inizio = System.currentTimeMillis();
        object = null;
        sorgente = VUOTA;
        sorgente2 = VUOTA;
        sorgente3 = VUOTA;
        typeField = null;
        clazz = null;
        previsto = VUOTA;
        ottenuto = VUOTA;
        message = VUOTA;
        listaStr = null;
        mappa = null;
        listaEntity = null;
    }

    @Test
    @Order(1)
    @DisplayName("1 - costruttore")
    void costruttoreBase() {
        System.out.println(("1 - Costruttore base"));
        System.out.println(VUOTA);

        System.out.println(String.format("La classe [%s] è un SINGLETON (service) ed ha un costruttore SENZA parametri", clazzName));
        System.out.println("Viene creata da SpringBoot");
    }

    @Test
    @Order(2)
    @DisplayName("2 - getBean")
    void getBean() {
        System.out.println(("2 - getBean"));
        System.out.println(VUOTA);

        System.out.println(String.format("La classe [%s] è un SINGLETON (service)", clazzName));
        System.out.println("Viene creata da SpringBoot");
        System.out.println(String.format("Non si può usare appContext.getBean(%s.class)", clazzName));
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
            message = String.format("Nel test '%s' di [%s], manca la model clazz '%s'", testName, this.getClass().getSimpleName(), "(null)");
            System.out.println(message);//@todo Da modificare dopo aver realizzato logService
            return false;
        }

        // Controlla che la classe in ingresso implementi AlgosModel
        //        if (CrudModel.class.isAssignableFrom(genericClazz)) {
        //        }
        //        else {
        //            message = String.format("Nel test '%s' di [%s], la classe '%s' non implementa AlgosModel", testName, this.getClass().getSimpleName(), genericClazz.getSimpleName());
        //            System.out.println(message);//@todo Da modificare dopo aver realizzato logService
        //            return false;
        //        }

        return true;
    }

    protected void printLista(final List lista) {
        int cont = 0;

        if (lista != null) {
            if (lista.size() > 0) {
                System.out.println(String.format("La lista contiene %d elementi", lista.size()));
                for (Object obj : lista) {
                    cont++;
                    System.out.print(cont);
                    System.out.print(PARENTESI_TONDA_END);
                    System.out.print(SPAZIO);
                    System.out.println(obj);
                }
            }
            else {
                System.out.println("Non ci sono elementi nella lista");
            }
        }
        else {
            System.out.println("Manca la lista");
        }
    }

    protected void printMappa(Map<String, Object> mappa) {
        if (mappa != null && mappa.size() > 0) {
            for (String key : mappa.keySet()) {
                System.out.println(String.format("%s%s%s", key, FORWARD, mappa.get(key)));
            }
        }
    }

}
