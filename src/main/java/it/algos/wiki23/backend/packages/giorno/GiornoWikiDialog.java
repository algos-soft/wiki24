package it.algos.wiki23.backend.packages.giorno;

import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.ui.views.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.vaadin.crudui.crud.*;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Thu, 14-Jul-2022
 * Time: 20:04
 * <p>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GiornoWikiDialog extends CrudDialogBase {

    //--I fields devono essere class variable e non local variable
    private TextField code;

    //--I fields devono essere class variable e non local variable
    private TextField descrizione;


    /**
     * Constructor not @Autowired. <br>
     * Non utilizzato e non necessario <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Se c'è un SOLO costruttore questo diventa automaticamente @Autowired e IntelliJ Idea 'segna' in rosso i
     * parametri <br>
     * Per evitare il bug (solo visivo), aggiungo un costruttore senza parametri <br>
     */
    public GiornoWikiDialog() {
    }// end of second constructor not @Autowired

    /**
     * Costruttore con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * L'istanza DEVE essere creata con appContext.getBean(PreferenzaDialog.class, operation, itemSaver, itemDeleter, itemAnnulla); <br>
     *
     * @param entityBean  The item to edit; it may be an existing or a newly created instance
     * @param operation   The operation being performed on the item (addNew, edit, editNoDelete, editDaLink, showOnly)
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     * @param fields      da costruire in automatico
     */
    public GiornoWikiDialog(final GiornoWiki entityBean, final CrudOperation operation, final CrudBackend crudBackend, final List fields) {
        super(entityBean, operation, crudBackend, fields);
    }// end of constructor not @Autowired


    /**
     * Crea i fields
     * Inizializza le properties grafiche (caption, visible, editable, width, ecc)
     * Aggiunge i fields al binder
     * Aggiunge eventuali fields specifici direttamente al layout grafico (senza binder e senza fieldMap)
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixBody2() {
        code = new TextField("Code");
        code.setReadOnly(operation == CrudOperation.DELETE);

        descrizione = new TextField("Descrizione");
        descrizione.setReadOnly(operation == CrudOperation.DELETE);

        formLayout.add(code, descrizione);
    }

}// end of crud Dialog class