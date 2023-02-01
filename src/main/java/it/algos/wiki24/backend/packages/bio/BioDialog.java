package it.algos.wiki24.backend.packages.bio;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.ui.views.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.*;
import org.vaadin.crudui.crud.*;

import java.util.*;
import java.util.function.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: gio, 28-apr-2022
 * Time: 11:57
 * <p>
 */
@SpringComponent
@Primary
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BioDialog extends CrudDialog {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ElaboraService elaboraService;

    protected Bio currentItem;

    protected Consumer<Bio> downloadHandler;
    protected Consumer<Bio> elaboraHandler;

    protected Button buttonDownload;

    protected BioBackend backend;

    /**
     * Constructor not @Autowired. <br>
     * Non utilizzato e non necessario <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Se c'è un SOLO costruttore questo diventa automaticamente @Autowired e IntelliJ Idea 'segna' in rosso i
     * parametri <br>
     * Per evitare il bug (solo visivo), aggiungo un costruttore senza parametri <br>
     */
    public BioDialog() {
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
    public BioDialog(final Bio entityBean, final CrudOperation operation, final BioBackend crudBackend, final List fields) {
        super(entityBean, operation, crudBackend, fields);
        this.currentItem = entityBean;
        this.backend = crudBackend;
    }// end of constructor not @Autowired

    @Override
    protected void fixBottom() {
        super.fixBottom();

        buttonDownload = new Button();
        buttonDownload.setText("Download");
        buttonDownload.getElement().setAttribute("theme", "primary");
        buttonDownload.getElement().setProperty("title", "Download: ricarica tutti i valori dal server wiki");
        buttonDownload.setIcon(new Icon(VaadinIcon.DOWNLOAD));
        buttonDownload.addClickListener(event -> downloadHandler());
        bottomPlaceHolder.add(buttonDownload);

        Button elaboraButton = new Button();
        elaboraButton.setText("Elabora");
        elaboraButton.getElement().setAttribute("theme", "error");
        elaboraButton.addClickListener(e -> elaboraHandler());
        elaboraButton.setIcon(new Icon(VaadinIcon.AMBULANCE));
        bottomPlaceHolder.add(elaboraButton);
    }

    public void openBio(final BiConsumer<AEntity, CrudOperation> saveHandler, final Consumer<AEntity> annullaHandler,
                        final Consumer<Bio> downloadHandler, final Consumer<Bio> elaboraHandler) {
        this.downloadHandler = downloadHandler;
        this.elaboraHandler = elaboraHandler;
        this.open(saveHandler, null, annullaHandler);
    }

    /**
     * Esegue un azione di download <br>
     */
    public void downloadHandler() {
        WrapBio wrap = appContext.getBean(QueryBio.class).getWrap(currentItem.pageId);
        currentItem = backend.fixWrap(currentItem.id, wrap);
        elaboraService.esegue(currentItem);
        binder.readBean(currentItem);
    }

    protected void elaboraHandler() {
        currentItem = elaboraService.esegue(currentItem);
        if (elaboraHandler != null) {
            elaboraHandler.accept(currentItem);
        }
        close();
    }


}// end of crud Dialog class