package it.algos.wiki24.backend.packages.parametri.nome;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.wiki24.backend.packages.nomi.nomebio.*;
import it.algos.wiki24.backend.packages.parametri.*;
import it.algos.wiki24.ui.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class ParNomeForm extends ParForm {


    public ParNomeForm(ParNomeModulo crudModulo, ParNomeEntity entityBean, CrudOperation operation) {
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

//    @Override
//    protected void fixBottom() {
//        super.fixBottom();
//
//        Button numBio = new Button("Elabora");
//        numBio.getElement().setAttribute("theme", "primary");
//        numBio.addThemeVariants(ButtonVariant.LUMO_ERROR);
//        numBio.getElement().setProperty("title", "Elabora il valore valido del parametro");
//        numBio.setIcon(new Icon(VaadinIcon.PUZZLE_PIECE));
//        numBio.setEnabled(true);
//        numBio.addClickListener(event -> elabora());
//
//        this.getFooter().add(numBio);
//    }
//
//    protected void elabora() {
//        String keyFieldGrezzo = "grezzo";
//        String keyFieldValido = "valido";
//        String valoreGrezzo = VUOTA;
//        String valoreValido = VUOTA;
//        AbstractField fieldGrezzo = mappaFields.get(keyFieldGrezzo);
//        AbstractField fieldValido = mappaFields.get(keyFieldValido);
//
//        if (fieldGrezzo != null) {
//            valoreGrezzo = (String) fieldGrezzo.getValue();
//        }
//
//        if (textService.isValid(valoreGrezzo)) {
//            valoreValido = valoreGrezzo;
//        }
//
//        if (fieldValido != null) {
//            fieldValido.setValue(valoreValido);
//        }
//
//        super.saveHandler();
//    }

}// end of CrudForm class
