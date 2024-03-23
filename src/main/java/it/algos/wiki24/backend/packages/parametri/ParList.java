package it.algos.wiki24.backend.packages.parametri;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.ui.wrapper.*;
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

    protected TextField searchValido = new TextField();

    protected IndeterminateCheckbox boxGrezzoPieno;

    protected IndeterminateCheckbox boxValidoPieno;

    protected IndeterminateCheckbox boxUguale;

    public ParList(final WikiModulo crudModulo) {
        super(crudModulo);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.usaBottoneDeleteAll = false;
        this.usaBottoneResetDelete = false;
        this.usaBottoneResetAdd = false;
        this.usaBottoneDownload = false;
        this.usaBottoneTransfer = false;
        this.usaBottoneResetEntity = true;
        this.usaBottoneWikiView = false;
        this.usaBottoneWikiEdit = false;
        this.usaBottoneWikiCrono = false;

        super.usaInfoElabora = true;

        this.usaBottoneSearch = false;
        this.usaSearchPageId = false;
        this.usaSearchWikiTitle = true;
        this.usaBottoneExport = false;
    }

    protected void fixHeader() {
        String message;
        headerPlaceHolder.removeAll();

        //Prima riga (infoScopo): Verde, bold, normale. Informazioni base: tavola (download) oppure Lista (upload) <br>
        message = String.format("%s%sCostruita da BioMongo", TEXT_BASE, SPAZIO);
        headerPlaceHolder.add(ASpan.text(message).bold().verde());

        //Secondo gruppo: Blue, normale, normale. Logica di creazione/funzionamento della tavola <br>
        message = String.format("Valore grezzo del parametro%sValore valido del parametro", FORWARD);
        headerPlaceHolder.add(ASpan.text(message).blue());

        super.fixHeader();
    }

    /**
     * Può essere sovrascritto <br>
     */
    protected void fixTop() {
        wikiTopPlaceHolder.removeAll();

        if (usaBottoneDeleteAll) {
            wikiTopPlaceHolder.deleteAll();
        }
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

        searchValido.setPlaceholder(TAG_ALTRE_BY + FIELD_NAME_VALIDO);
        searchValido.getElement().setProperty("title", "Search: ricerca testuale da inizio del campo " + FIELD_NAME_VALIDO);
        searchValido.setClearButtonVisible(true);
        searchValido.addValueChangeListener(event -> sincroFiltri());

        wikiTopPlaceHolder.build();
        wikiTopPlaceHolder.add(searchGrezzo);
        wikiTopPlaceHolder.add(searchValido);
        boxGrezzoPieno = super.creaFiltroCheckBox(boxGrezzoPieno, FIELD_NAME_GREZZO);
        boxValidoPieno = super.creaFiltroCheckBox(boxValidoPieno, FIELD_NAME_VALIDO);
        boxUguale = super.creaFiltroCheckBox(boxUguale, FIELD_NAME_UGUALE);
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

        if (searchValido != null) {
            String valido = searchValido.getValue();
            if (textService.isValid(valido)) {
                filtri.inizio(FIELD_NAME_VALIDO, valido);
                filtri.sort(Sort.by(Sort.Direction.ASC, FIELD_NAME_VALIDO));
            }
            else {
                filtri.remove(FIELD_NAME_VALIDO);
                filtri.sort(basicSort);
            }
        }

        super.fixFiltroCheckBox(boxGrezzoPieno, FIELD_NAME_GREZZO_PIENO);
        super.fixFiltroCheckBox(boxValidoPieno, FIELD_NAME_VALIDO_PIENO);
        super.fixFiltroCheckBox(boxUguale, FIELD_NAME_UGUALE);
    }

}
