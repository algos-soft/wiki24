package it.algos.base24.backend.packages.geografia.provincia;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.packages.geografia.regione.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;

import javax.inject.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class ProvinciaList extends CrudList {

    @Inject
    RegioneModulo regioneModulo;

    TextField searchField;

    private TextField searchNomeBreve;

    private ComboBox comboRegione;

    public ProvinciaList(final ProvinciaModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixHeader() {
        super.infoScopo = String.format(typeList.getInfoScopo(), "province");
        super.fixHeader();
    }

    //    @Override
    //    public void fixHeader() {
    //        Anchor anchor;
    //        String link;
    //        String caption;
    //        String alfa = "Province d'Italia";
    //
    //        link = String.format("%s%s", TAG_WIKI, alfa);
    //        caption = String.format("%s%s%s", QUADRA_INI, alfa, QUADRA_END);
    //        anchor = new Anchor(link, caption);
    //        anchor.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
    //
    //        Span testo = new Span(typeList.getInfoScopo());
    //        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
    //        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());
    //        headerPlaceHolder.add(new Span(testo, anchor));
    //
    //        super.fixHeader();
    //    }

    /**
     * Aggiunge componenti al Top della Lista <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse se si vogliono aggiungere componenti IN CODA <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse se si vogliono aggiungere componenti in ordine diverso <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        searchNomeBreve = new TextField();
        searchNomeBreve.setPlaceholder(TAG_ALTRE_BY + "nome breve");
        searchNomeBreve.getElement().setProperty("title", "Search: ricerca testuale da inizio del campo " + searchNomeBreve);
        searchNomeBreve.setClearButtonVisible(true);
        searchNomeBreve.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(searchNomeBreve);

        comboRegione = new ComboBox<>();
        comboRegione.setPlaceholder("Regioni...");
        comboRegione.setClearButtonVisible(true);
        comboRegione.setWidth("14rem");
        comboRegione.setItems(regioneModulo.findAllItalia());
        comboRegione.addValueChangeListener(event -> sincroFiltri());
        topPlaceHolder.add(comboRegione);
    }


    @Override
    protected void fixFiltri() {
        super.fixFiltri();
        String searchProperty = "nomeBreve";
        String regioneProperty = "regione";
        String value = VUOTA;
        if (searchNomeBreve != null) {
            value = searchNomeBreve.getValue();
        }

        if (textService.isValid(value)) {
            filtri.inizio(searchProperty, value);
            filtri.sort(Sort.by(Sort.Direction.ASC, value));
        }
        else {
            filtri.remove(searchProperty);
            filtri.sort(basicSort);
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
