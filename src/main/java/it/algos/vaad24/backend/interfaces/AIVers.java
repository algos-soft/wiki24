package it.algos.vaad24.backend.interfaces;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: gio, 10-feb-2022
 * Time: 12:18
 * <p>
 * Interfaccia col metodo inizia() <br>
 * Viene implementata 'una' delle sottoclassi concrete 'singleton', <br>
 * tramite @Qualifier(QUALIFIER_VERSION_VAAD) selezionato in VaadBoot.setVersInstance(), oppure <br>
 * tramite @Qualifier(QUALIFIER_VERSION_xxx) selezionato in xxxBoot.setVersInstance() <br>
 */
public interface AIVers {

    /**
     * Executed on container startup <br>
     * Setup non-UI logic here <br>
     * This method is called prior to the servlet context being initialized (when the Web application is deployed). <br>
     * You can initialize servlet context related data here. <br>
     * Metodo eseguito solo quando l'applicazione viene caricata/parte nel server (Tomcat o altri) <br>
     * Eseguito quindi a ogni avvio/riavvio del server e NON a ogni sessione <br>
     * <p>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Tutte le aggiunte, modifiche e patch vengono inserite con una versione <br>
     * L'ordine di inserimento è FONDAMENTALE <br>
     */
    void inizia();

}// end of interface

