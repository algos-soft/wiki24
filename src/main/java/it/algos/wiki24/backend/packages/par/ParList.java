package it.algos.wiki24.backend.packages.par;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.list.*;
import it.algos.wiki24.backend.logic.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.data.domain.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 31-Dec-2023
 * Time: 07:11
 */
public abstract class ParList extends WikiList {

    protected TextField searchGrezzo = new TextField();

    protected TextField searchElaborato = new TextField();

    protected IndeterminateCheckbox boxGrezzoVuoto;

    protected IndeterminateCheckbox boxElaboratoVuoto;

    protected IndeterminateCheckbox boxUguale;

    public ParList(final WikiModulo crudModulo) {
        super(crudModulo);
    }


    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        searchGrezzo.setPlaceholder(TAG_ALTRE_BY + FIELD_NAME_GREZZO);
        searchGrezzo.getElement().setProperty("title", "Search: ricerca testuale da inizio del campo " + FIELD_NAME_GREZZO);
        searchGrezzo.setClearButtonVisible(true);
        searchGrezzo.addValueChangeListener(event -> sincroFiltri());

        searchElaborato.setPlaceholder(TAG_ALTRE_BY + FIELD_NAME_ELABORATO);
        searchElaborato.getElement().setProperty("title", "Search: ricerca testuale da inizio del campo " + FIELD_NAME_ELABORATO);
        searchElaborato.setClearButtonVisible(true);
        searchElaborato.addValueChangeListener(event -> sincroFiltri());

        boxGrezzoVuoto = new IndeterminateCheckbox();
        boxGrezzoVuoto.setLabel("Grezzo");
        boxGrezzoVuoto.setIndeterminate(true);
        boxGrezzoVuoto.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout1 = new HorizontalLayout(boxGrezzoVuoto);
        layout1.setAlignItems(Alignment.CENTER);

        boxElaboratoVuoto = new IndeterminateCheckbox();
        boxElaboratoVuoto.setLabel("Elaborato");
        boxElaboratoVuoto.setIndeterminate(true);
        boxElaboratoVuoto.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout2 = new HorizontalLayout(boxElaboratoVuoto);
        layout2.setAlignItems(Alignment.CENTER);

        boxUguale = new IndeterminateCheckbox();
        boxUguale.setLabel("Uguale");
        boxUguale.setIndeterminate(true);
        boxUguale.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout3 = new HorizontalLayout(boxUguale);
        layout3.setAlignItems(Alignment.CENTER);

        this.add(new HorizontalLayout(searchGrezzo, searchElaborato, layout1, layout2, layout3));
    }


    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (searchGrezzo != null) {
            String grezzo = searchGrezzo.getValue();
            if (textService.isValid(grezzo)) {
                filtri.inizio(FIELD_NAME_GREZZO, grezzo);
                filtri.sort(Sort.Order.asc(FIELD_NAME_GREZZO));
            }
            else {
                filtri.remove(FIELD_NAME_GREZZO);
                filtri.sort(basicSortOrder);
            }
        }

        if (searchElaborato != null) {
            String elaborato = searchElaborato.getValue();
            if (textService.isValid(elaborato)) {
                filtri.inizio(FIELD_NAME_ELABORATO, elaborato);
                filtri.sort(Sort.Order.asc(FIELD_NAME_ELABORATO));
            }
            else {
                filtri.remove(FIELD_NAME_ELABORATO);
                filtri.sort(basicSortOrder);
            }
        }

        if (boxGrezzoVuoto != null && !boxGrezzoVuoto.isIndeterminate()) {
            filtri.uguale(FIELD_NAME_GREZZO_VUOTO, boxGrezzoVuoto.getValue());
        }
        else {
            filtri.remove(FIELD_NAME_GREZZO_VUOTO);
        }

        if (boxElaboratoVuoto != null && !boxElaboratoVuoto.isIndeterminate()) {
            filtri.uguale(FIELD_NAME_ELABORATO_VUOTO, boxElaboratoVuoto.getValue());
        }
        else {
            filtri.remove(FIELD_NAME_ELABORATO_VUOTO);
        }

        if (boxUguale != null && !boxUguale.isIndeterminate()) {
            filtri.uguale(FIELD_NAME_UGUALE, boxUguale.getValue());
        }
        else {
            filtri.remove(FIELD_NAME_UGUALE);
        }

    }

}
