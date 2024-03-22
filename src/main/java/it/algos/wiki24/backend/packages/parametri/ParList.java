package it.algos.wiki24.backend.packages.parametri;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.list.*;
import it.algos.wiki24.backend.logic.*;
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
     * Può essere sovrascritto <br>
     */
    protected void fixTop() {
        wikiTopPlaceHolder.removeAll();

        wikiTopPlaceHolder.deleteAll();
        wikiTopPlaceHolder.elabora();

        if (usaBottoneEdit) {
            wikiTopPlaceHolder.edit();
        }
        if (usaSearchWikiTitle) {
            wikiTopPlaceHolder.searchWikiTitle();
        }

        searchGrezzo.setPlaceholder(TAG_ALTRE_BY + FIELD_NAME_GREZZO);
        searchGrezzo.getElement().setProperty("title", "Search: ricerca testuale da inizio del campo " + FIELD_NAME_GREZZO);
        searchGrezzo.setClearButtonVisible(true);
        searchGrezzo.addValueChangeListener(event -> sincroFiltri());

        searchElaborato.setPlaceholder(TAG_ALTRE_BY + FIELD_NAME_ELABORATO);
        searchElaborato.getElement().setProperty("title", "Search: ricerca testuale da inizio del campo " + FIELD_NAME_ELABORATO);
        searchElaborato.setClearButtonVisible(true);
        searchElaborato.addValueChangeListener(event -> sincroFiltri());

        wikiTopPlaceHolder.build();
        boxGrezzoVuoto = super.creaFiltroCheckBox(boxGrezzoVuoto, "Grezzo");
        boxElaboratoVuoto = super.creaFiltroCheckBox(boxElaboratoVuoto, "Elaborato");
        boxUguale = super.creaFiltroCheckBox(boxUguale, "Uguale");
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixTop2() {
        wikiTopPlaceHolder.removeAll();

        boxGrezzoVuoto = super.creaFiltroCheckBox(boxGrezzoVuoto, "Grezzo");
        //        boxGrezzoVuoto = new IndeterminateCheckbox();
        //        boxGrezzoVuoto.setLabel("Grezzo");
        //        boxGrezzoVuoto.setIndeterminate(false);
        //        boxGrezzoVuoto.setValue(false);
        //        boxGrezzoVuoto.addValueChangeListener(event -> sincroFiltri());
        //        HorizontalLayout layout1 = new HorizontalLayout(boxGrezzoVuoto);
        //        layout1.setAlignItems(Alignment.CENTER);

        boxElaboratoVuoto = super.creaFiltroCheckBox(boxElaboratoVuoto, "Elaborato");
        //        boxElaboratoVuoto = new IndeterminateCheckbox();
        //        boxElaboratoVuoto.setLabel("Elaborato");
        //        boxElaboratoVuoto.setIndeterminate(false);
        //        boxElaboratoVuoto.setValue(false);
        //        boxElaboratoVuoto.addValueChangeListener(event -> sincroFiltri());
        //        HorizontalLayout layout2 = new HorizontalLayout(boxElaboratoVuoto);
        //        layout2.setAlignItems(Alignment.CENTER);

        boxUguale = super.creaFiltroCheckBox(boxUguale, "Uguale");
        //        boxUguale = new IndeterminateCheckbox();
        //        boxUguale.setLabel("Uguale");
        //        boxUguale.setIndeterminate(false);
        //        boxUguale.setValue(false);
        //        boxUguale.addValueChangeListener(event -> sincroFiltri());
        //        HorizontalLayout layout3 = new HorizontalLayout(boxUguale);
        //        layout3.setAlignItems(Alignment.CENTER);

        //        headerPlaceHolder.add(new HorizontalLayout(searchGrezzo, searchElaborato, layout1, layout2, layout3));
    }


    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (searchGrezzo != null) {
            String grezzo = searchGrezzo.getValue();
            if (textService.isValid(grezzo)) {
                filtri.inizio(FIELD_NAME_GREZZO, grezzo);
                filtri.sort(Sort.by(Sort.Direction.ASC, FIELD_NAME_GREZZO));
            }
            else {
                filtri.remove(FIELD_NAME_GREZZO);
                filtri.sort(basicSort);
            }
        }

        if (searchElaborato != null) {
            String elaborato = searchElaborato.getValue();
            if (textService.isValid(elaborato)) {
                filtri.inizio(FIELD_NAME_ELABORATO, elaborato);
                filtri.sort(Sort.by(Sort.Direction.ASC, FIELD_NAME_ELABORATO));
            }
            else {
                filtri.remove(FIELD_NAME_ELABORATO);
                filtri.sort(basicSort);
            }
        }

        super.fixFiltroCheckBox(boxGrezzoVuoto, FIELD_NAME_GREZZO_VUOTO);
        //        if (boxGrezzoVuoto != null && !boxGrezzoVuoto.isIndeterminate()) {
        //            filtri.uguale(FIELD_NAME_GREZZO_VUOTO, boxGrezzoVuoto.getValue());
        //        }
        //        else {
        //            filtri.remove(FIELD_NAME_GREZZO_VUOTO);
        //        }

        super.fixFiltroCheckBox(boxElaboratoVuoto, FIELD_NAME_ELABORATO_VUOTO);
        //        if (boxElaboratoVuoto != null && !boxElaboratoVuoto.isIndeterminate()) {
        //            filtri.uguale(FIELD_NAME_ELABORATO_VUOTO, boxElaboratoVuoto.getValue());
        //        }
        //        else {
        //            filtri.remove(FIELD_NAME_ELABORATO_VUOTO);
        //        }

        super.fixFiltroCheckBox(boxUguale, FIELD_NAME_UGUALE);
        //        if (boxUguale != null && !boxUguale.isIndeterminate()) {
        //            filtri.uguale(FIELD_NAME_UGUALE, boxUguale.getValue());
        //        }
        //        else {
        //            filtri.remove(FIELD_NAME_UGUALE);
        //        }

    }

}
