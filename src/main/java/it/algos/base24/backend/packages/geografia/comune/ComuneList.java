package it.algos.base24.backend.packages.geografia.comune;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.packages.geografia.provincia.*;
import it.algos.base24.backend.packages.geografia.regione.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.Scope;

import javax.inject.*;
import java.util.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class ComuneList extends CrudList {

    @Inject
    ProvinciaModulo provinciaModulo;

    @Inject
    RegioneModulo regioneModulo;

    private ComboBox comboProvincia;

    private ComboBox comboRegione;

    public ComuneList(final ComuneModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixHeader() {
        Anchor anchor;
        String link;
        String caption;
        String alfa = "Comuni d'Italia (A)";

        link = String.format("%s%s", TAG_WIKI, alfa);
        caption = String.format("%s%s%s%s", QUADRA_INI, alfa, QUADRA_END, " B, C, ...");
        anchor = new Anchor(link, caption);
        anchor.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        Span testo = new Span(typeList.getInfoScopo());
        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());
        headerPlaceHolder.add(new Span(testo, anchor));

        super.fixHeader();
    }

    /**
     * Aggiunge componenti al Top della Lista <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse se si vogliono aggiungere componenti IN CODA <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse se si vogliono aggiungere componenti in ordine diverso <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();
        List itemsProvincia = provinciaModulo.findAll();
        List itemsRegione = regioneModulo.findAllItalia();

        comboProvincia = new ComboBox<>();
        comboProvincia.setPlaceholder("Province...");
        comboProvincia.setClearButtonVisible(true);
        comboProvincia.setWidth("14rem");
        if (itemsProvincia != null && itemsProvincia.size() > 0) {
            comboProvincia.setItems(itemsProvincia);
        }
        comboProvincia.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboProvincia);

        comboRegione = new ComboBox<>();
        comboRegione.setPlaceholder("Regioni...");
        comboRegione.setClearButtonVisible(true);
        comboRegione.setWidth("14rem");
        if (itemsRegione != null && itemsRegione.size() > 0) {
            comboRegione.setItems(itemsRegione);
        }
        comboRegione.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboRegione);
    }


    @Override
    protected void fixFiltri() {
        super.fixFiltri();
        String provinciaProperty = "provincia";
        String regioneProperty = "regione";

        if (comboProvincia != null) {
            if (comboProvincia.getValue() != null) {
                if (comboProvincia.getValue() instanceof ProvinciaEntity provincia) {
                    filtri.uguale(provinciaProperty, provincia);
                }
            }
            else {
                filtri.remove(provinciaProperty);
            }
        }

        if (comboRegione != null) {
            if (comboRegione.getValue() != null) {
                if (comboRegione.getValue() instanceof RegioneEntity regione) {
                    filtri.uguale(regioneProperty, regione);
                }
            }
            else {
                filtri.remove(regioneProperty);
            }
        }
    }

}// end of CrudList class
