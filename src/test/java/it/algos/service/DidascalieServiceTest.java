package it.algos.service;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.*;
import it.algos.base.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.test.context.junit.jupiter.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 17-Jul-2023
 * Time: 20:15
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Didascalie Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DidascalieServiceTest extends AlgosTest {


    @Test
    @Order(1)
    @DisplayName("1 - Divisione double")
    void divisione() {
        System.out.println("1 - Divisione double");
    }

    @Test
    @Order(2)
    @DisplayName("2 - Divisione interi")
    void divisione2() {
        System.out.println("2 - Divisione interi");
    }


    @Test
    @Order(4)
    @DisplayName("3 - Divisione interi")
    void divisione4() {
        System.out.println("2 - Divisione interi");
    }


    @Test
    @Order(3)
    @DisplayName("3 - Divisione interi")
    void divisione3() {
        System.out.println("2 - Divisione interi");
    }

}
