package it.algos.base24.enumeration;

import it.algos.*;
import it.algos.base24.basetest.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.interfaces.*;
import it.algos.vbase.backend.packages.utility.preferenza.*;
import it.algos.vbase.backend.service.*;
import it.algos.vbase.backend.wrapper.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

import javax.inject.*;
import java.time.*;
import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Mon, 11-Sep-2023
 * Time: 15:09
 * <p>
 * Unit test di una enumeration <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = {Application.class})
@Tag("enums")
@DisplayName("Enumeration Pref")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrefTest extends EnumTest {

    @Inject
    public PreferenzaModulo preferenzaModulo;

    @Inject
    public TextService textService;

    @Inject
    public LogService logger;

    private Pref[] matrice;

    //    private List<Pref> listaEnum;
    //
    //    private List<String> listaTag;

    private List<IPref> listaEnumPref;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        super.typeZero = Arrays.stream(Pref.values()).toList().get(0);
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        matrice = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - matrice dei valori")
    void matrice() {
        matrice = Pref.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(VUOTA);
        for (Pref valore : matrice) {
            System.out.println(valore);
        }
    }


    @Test
    @Order(10)
    @DisplayName("10 - enum complete")
    void getAllEnums() {
        listaEnumPref = Pref.getAllEnums();
        assertNotNull(listaEnumPref);

        System.out.println("Tutti i valori delle enum");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnumPref.size()));
        System.out.println(VUOTA);
        listaEnumPref.forEach(e -> {
            System.out.print(e.getKeyCode());
            System.out.print(FORWARD);
            System.out.print(e.getType().getTag());
            System.out.print(VIRGOLA_SPAZIO);
            System.out.print(e.getDefaultValue());
            System.out.print(VIRGOLA_SPAZIO);
            System.out.println(e.getDescrizione());
        });
    }


    @Test
    @Order(50)
    @DisplayName("50 - defaultValue")
    void defaultValue() {
        listaEnumPref = Pref.getAllEnums();
        assertNotNull(listaEnumPref);

        System.out.println("Tutti i valori di default delle enum");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnumPref.size()));
        System.out.println("Valori di default della Enumeration");
        System.out.println(VUOTA);
        listaEnumPref.forEach(e -> {
            System.out.print(e.getKeyCode());
            System.out.print(FORWARD);
            System.out.print(String.format("[%s]", e.getType().getTag()));
            System.out.print(SPAZIO);
            System.out.print(String.format("(%s)", e.getDescrizione()));
            System.out.print(SPAZIO);
            System.out.println(String.format("value=[%s]", e.getDefaultValue()));
        });
    }


    @Test
    @Order(51)
    @DisplayName("51 - currentValue")
    void currentValue() {
        listaEnumPref = Pref.getAllEnums();
        assertNotNull(listaEnumPref);

        System.out.println("Tutti i valori correnti sul DataBase delle preferenze corrispondenti alle enum");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnumPref.size()));
        System.out.println("Valori di default della Enumeration");
        System.out.println("Valori correnti delle Preferenze");
        System.out.println(VUOTA);
        listaEnumPref.forEach(e -> {
            System.out.print(e.getKeyCode());
            System.out.print(SPAZIO);
            System.out.print(String.format("[%s]", e.getType().getTag()));
            System.out.print(SPAZIO);
            System.out.print(String.format("(%s)", e.getDescrizione()));
            System.out.print(SPAZIO);
            System.out.print(String.format("default=[%s]", e.getDefaultValue()));
            System.out.print(FORWARD);
            System.out.println(String.format("current=[%s]", preferenzaModulo.getValueCorrente(e.getType(), e.getKeyCode())));
        });
    }

    @Test
    @Order(52)
    @DisplayName("52 - currentValueCasting")
    void currentValueCasting() {
        TypePref type = null;
        listaEnumPref = Pref.getAllEnums();
        assertNotNull(listaEnumPref);
        String stringValue;
        int intValue;
        boolean boolValue;
        LocalDateTime dataTimeValue;

        System.out.println("Valori correnti con casting di ogni Enumeration");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnumPref.size()));
        System.out.println(VUOTA);
        for (IPref pref : listaEnumPref) {
            type = pref.getType();
            switch (type) {
                case string -> {
                    stringValue = pref.getStr();
                    System.out.print(pref.getKeyCode());
                    System.out.print(FORWARD);
                    System.out.print(String.format("current value (%s)", type.getTag()));
                    System.out.print(SPAZIO);
                    System.out.println(String.format("[%s]", stringValue));
                }
                case bool -> {
                    boolValue = pref.is();
                    System.out.print(pref.getKeyCode());
                    System.out.print(FORWARD);
                    System.out.print(String.format("current value (%s)", type.getTag()));
                    System.out.print(SPAZIO);
                    System.out.println(String.format("[%s]", boolValue));
                }
                case integer -> {
                    intValue = pref.getInt();
                    System.out.print(pref.getKeyCode());
                    System.out.print(FORWARD);
                    System.out.print(String.format("current value (%s)", type.getTag()));
                    System.out.print(SPAZIO);
                    System.out.println(String.format("[%s]", intValue));
                }
                case localdatetime -> {
                    dataTimeValue = pref.getDateTime();
                    System.out.print(pref.getKeyCode());
                    System.out.print(FORWARD);
                    System.out.print(String.format("current value (%s)", type.getTag()));
                    System.out.print(SPAZIO);
                    System.out.println(String.format("[%s]", dataTimeValue));
                }
                default -> {
                    message = String.format("La preferenza '%s' è di type [%s] e non è gestita col valore (cast) corretto", pref.getKeyCode(), type.getTag());
                    logger.warn(new WrapLog().message(message).type(TypeLog.test));
                }
            }
        }
    }


    @Test
    @Order(53)
    @DisplayName("53 - checkMethod")
    void checkMethod() {
        listaEnumPref = Pref.getAllEnums();
        assertNotNull(listaEnumPref);

        System.out.println("Controllo del metodo chiamato per recuperare il valore col casting");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaEnumPref.size()));
        System.out.println(VUOTA);
        for (IPref pref : listaEnumPref) {
            if (textService.isValid(pref.getStr())) {
                message = String.format("La preferenza [%s] di type (%s) è stata correttamente chiamata col metodo '%s'", pref.getKeyCode(), pref.getType().getTag(), "getStr");
                logger.info(new WrapLog().message(message).type(TypeLog.test));
            }

            pref.is();

            if (pref.getInt() > 0) {
                message = String.format("La preferenza [%s] di type (%s) è stata correttamente chiamata col metodo '%s'", pref.getKeyCode(), pref.getType().getTag(), "getInt");
                logger.info(new WrapLog().message(message).type(TypeLog.test));
            }

            if (pref.getType() == TypePref.localdatetime) {
                long prefValue= pref.getDateTime().toEpochSecond(ZoneOffset.UTC);
                long errorValue= ERROR_DATA_TIME.toEpochSecond(ZoneOffset.UTC);
                if (prefValue > errorValue) {
                    message = String.format("La preferenza [%s] di type (%s) è stata correttamente chiamata col metodo '%s'", pref.getKeyCode(), pref.getType().getTag(), "getDateTime");
                    logger.info(new WrapLog().message(message).type(TypeLog.test));
                }
            }

            System.out.println(VUOTA);
        }
    }

}