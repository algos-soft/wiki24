package it.algos.base24.backend.annotation;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;

import java.lang.annotation.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: ven, 13-ott-2017
 * Time: 14:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //can use in class and interface.
public @interface AEntity {

    /**
     * (Optional) nome della collection (minuscolo) solo se diverso dal nome della EntityClass (POJO) <br>
     * Di default lo stesso nome della classe Entity (POJO) con iniziale minuscola <br>
     */
    String collectionName() default VUOTA;

    /**
     * (Optional) key property unica <br>
     * Di default usa la property 'id' della collection mongoDB <br>
     */
    String keyPropertyName() default VUOTA;

    /**
     * (Optional) search property  <br>
     * Di default usa la property 'key' del modello dati (EntityClass) <br>
     */
    String searchPropertyName() default VUOTA;

    /**
     * (Optional) sort key property <br>
     * Di default usa la property 'key' del modello dati (EntityClass) <br>
     */
    String sortPropertyName() default VUOTA;

    /**
     * (Optional) usa il metodo resetStartup() alla creazione della classe xxxModulo <br>
     * Di default false <br>
     *
     * @return the status
     */
    boolean usaStartupReset() default false;

    /**
     * (Optional) pacchetto di preferenze per l'uso dei bottoni nella lista <br>
     * Di default nessuno <br>
     */
    TypeResetOld typeReset() default TypeResetOld.standard;

    /**
     * (Optional) pacchetto di preferenze per l'uso dei bottoni nella lista <br>
     * Di default nessuno <br>
     */
    TypeList typeList() default TypeList.standard;

    /**
     * (Optional) usa forzare la primaMinuscola per l'Idkey <br>
     * Di default true <br>
     *
     * @return the status
     */
    boolean usaIdPrimaMinuscola() default true;

}// end of interface annotation