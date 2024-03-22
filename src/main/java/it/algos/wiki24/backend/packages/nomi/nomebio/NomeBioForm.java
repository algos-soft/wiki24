package it.algos.wiki24.backend.packages.nomi.nomebio;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.ui.form.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.ui.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;

import javax.inject.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NomeBioForm extends WikiForm {

    @Inject
    BioMongoModulo bioMongoModulo;

    public NomeBioForm(NomeBioModulo crudModulo, NomeBioEntity entityBean, CrudOperation operation) {
        super(crudModulo, entityBean, operation);
    }

    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Sono disponibili tutte le istanze @Autowired <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        super.fixPreferenze();
        super.numResponsiveStepColumn = 1;
    }

    @Override
    protected void fixBottom() {
        super.fixBottom();

        Button numBio = new Button("NumBio");
        numBio.getElement().setAttribute("theme", "primary");
        numBio.addThemeVariants(ButtonVariant.LUMO_ERROR);
        numBio.getElement().setProperty("title", "Elabora numBio per questo nome");
        numBio.setIcon(new Icon(VaadinIcon.PUZZLE_PIECE));
        numBio.setEnabled(true);
        numBio.addClickListener(event -> elaboraNumBio());

        this.getFooter().add(numBio);
    }

    protected void elaboraNumBio() {
        int numBio = bioMongoModulo.countAllByNome(((NomeBioEntity) currentEntityModel).nome);
        mappaFields.get("numBio").setValue(numBio);
        super.saveHandler();
    }

}// end of CrudForm class
