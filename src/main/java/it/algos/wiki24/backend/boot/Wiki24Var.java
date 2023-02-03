package it.algos.wiki24.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import it.algos.wiki24.backend.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Sat, 31-Dec-2022
 * Time: 12:07
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Wiki24Var {

    /**
     * Schedule per ogni task del programma <br>
     * Regolata dall' applicazione durante l' esecuzione del 'container startup' (non-UI logic) <br>
     */
    public static AETypeSchedule typeSchedule;

    public static String BANNER = " _______ _______ _______ _______ _______ _______ \n" +
            "|\\     /|\\     /|\\     /|\\     /|\\     /|\\     /|\n" +
            "| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |\n" +
            "| |   | | |   | | |   | | |   | | |   | | |   | |\n" +
            "| |w  | | |i  | | |k  | | |i  | | |2  | | |4  | |\n" +
            "| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |\n" +
            "|/_____\\|/_____\\|/_____\\|/_____\\|/_____\\|/_____\\|\n";


}
