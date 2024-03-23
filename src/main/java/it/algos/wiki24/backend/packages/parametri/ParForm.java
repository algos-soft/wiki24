package it.algos.wiki24.backend.packages.parametri;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.ui.form.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.parametri.nome.*;
import it.algos.wiki24.ui.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 22-Mar-2024
 * Time: 20:39
 */
public abstract class ParForm extends WikiForm {

    @Inject
    BioMongoModulo bioMongoModulo;

    protected ParModulo currentParModulo;

    public ParForm(ParModulo crudModulo, ParEntity entityBean, CrudOperation operation) {
        super(crudModulo, entityBean, operation);
        currentParModulo = crudModulo;
    }

    @Override
    protected void fixBottom() {
        super.fixBottom();

        Button mongo = new Button("Mongo");
        mongo.getElement().setAttribute("theme", "primary");
        mongo.getElement().setProperty("title", "Recupera il valore da BioMongo");
        mongo.setIcon(new Icon(VaadinIcon.ARROW_DOWN));
        mongo.setEnabled(true);
        mongo.addClickListener(event -> mongo());
        this.getFooter().add(mongo);

        Button elabora = new Button("Elabora");
        elabora.getElement().setAttribute("theme", "primary");
        elabora.getElement().setProperty("title", "Elabora il valore valido del parametro");
        elabora.setIcon(new Icon(VaadinIcon.PUZZLE_PIECE));
        elabora.setEnabled(true);
        elabora.addClickListener(event -> elabora());
        this.getFooter().add(elabora);

        Button elaboraRegistra = new Button("All");
        elaboraRegistra.getElement().setAttribute("theme", "primary");
        elaboraRegistra.addThemeVariants(ButtonVariant.LUMO_ERROR);
        elaboraRegistra.getElement().setProperty("title", "Elabora e registra il valore valido del parametro");
        elaboraRegistra.setIcon(new Icon(VaadinIcon.PUZZLE_PIECE));
        elaboraRegistra.setEnabled(true);
        elaboraRegistra.addClickListener(event -> elaboraRegistra());
        this.getFooter().add(elaboraRegistra);
    }

    protected void mongo() {
        String keyFieldGrezzo = FIELD_NAME_GREZZO;
        String keyFieldWikiTitle = FIELD_NAME_WIKI_TITLE;
        AbstractField fieldTitle = mappaFields.get(keyFieldWikiTitle);
        AbstractField fieldGrezzo = mappaFields.get(keyFieldGrezzo);
        String wikiTitle = VUOTA;
        String valoreGrezzo = VUOTA;
        BioMongoEntity bioMongoEntity = null;

        if (fieldTitle != null) {
            wikiTitle = (String) fieldTitle.getValue();
        }
        bioMongoEntity = bioMongoModulo.findByWikiTitle(wikiTitle);

        if (bioMongoEntity != null) {
            valoreGrezzo = bioMongoEntity.nome;
        }
        if (fieldGrezzo != null) {
            fieldGrezzo.setValue(valoreGrezzo);
        }
    }

    protected void elabora() {
        String keyFieldGrezzo = FIELD_NAME_GREZZO;
        String keyFieldValido = FIELD_NAME_VALIDO;
        String valoreGrezzo = VUOTA;
        String valoreValido = VUOTA;
        AbstractField fieldGrezzo = mappaFields.get(keyFieldGrezzo);
        AbstractField fieldValido = mappaFields.get(keyFieldValido);

        if (fieldGrezzo != null) {
            valoreGrezzo = (String) fieldGrezzo.getValue();
        }

        if (textService.isValid(valoreGrezzo)) {
            valoreValido = currentParModulo.getElaborato(VUOTA, valoreGrezzo);
        }

        if (fieldValido != null) {
            fieldValido.setValue(valoreValido);
        }
    }

    protected void elaboraRegistra() {
        mongo();
        elabora();
        super.saveHandler();
    }

}
