package it.algos.wiki24.backend.packages.nomi.nomebio;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.ui.dialog.*;
import it.algos.vbase.ui.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.list.*;
import it.algos.wiki24.backend.packages.nazionalita.nazplurale.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NomeBioList extends WikiList {

    private IndeterminateCheckbox checkDoppio;

    private IndeterminateCheckbox checkMongo;

    private IndeterminateCheckbox checkSoglia;

    private IndeterminateCheckbox checkLista;

    public NomeBioList(final NomeBioModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.usaInfoElabora = true;
        this.usaInfoUpload = true;

        this.usaBottoneDownload = false;
        this.usaBottoneElabora = true;
        this.usaBottoneUploadAll = true;
        super.usaBottoneUpload = true;

        this.usaBottoneEdit = true;
        this.usaBottoneShows = false;
    }

    /**
     * Utilizza il placeHolder header della view per informazioni sulla tavola/lista <br>
     * <p>
     * Prima riga (infoScopo): Verde, bold, normale. Informazioni base: tavola (download) oppure Lista (upload) <br>
     * Secondo gruppo: Blue, normale, normale. Logica di creazione/funzionamento della tavola <br>
     * Terzo gruppo (infoLista): Blue, bold, small. Parametri logici di creazione delle liste <br>
     * Quarto gruppo: Rosso, bold, small. Esecuzione upload <br>
     * Quinto gruppo: Rosso, normale, small. Parametri delete/reset <br>
     * Sesto gruppo: Verde, normale, small. Informazioni sulla tempistica <br>
     * <p>
     * Può essere sovrascritto, invocando PRIMA o DOPO il metodo della superclasse <br>
     */
    @Override
    public void fixHeader() {
        String message;
        int sogliaPagina = WPref.sogliaPaginaNomi.getInt();
        headerPlaceHolder.removeAll();

        //Prima riga (infoScopo): Verde, bold, normale. Informazioni base: tavola (download) oppure Lista (upload) <br>
        message = "Lista upload. Costruita dalle tavole di base (Doppio, Modulo, Categoria) e da BioMongo.";
        BSpan testo = BSpan.text(message).bold().verde();
        headerPlaceHolder.add(testo);

        //Secondo gruppo: Blue, normale, normale. Logica di creazione/funzionamento della tavola <br>
        message = "Elabora: recupera tutti i nomi da NomeDoppio, NomeModulo e NomeCategoria.";
        headerPlaceHolder.add(ASpan.text(message).blue());
        message = "Spazzola ogni nome per calcolare le occorrenze di numBio.";
        headerPlaceHolder.add(ASpan.text(message).blue());

        super.infoListaPagina = String.format("quando numBio della pagina > %s", sogliaPagina);
        super.infoListaSottoPagina = String.format("quando numBio del paragrafo > %s", "50");
        super.infoListaSottoSottoPagina = "mai";

        super.infoCreazione = TEXT_HARD;
        super.infoReset = VUOTA;

        //Sesto gruppo: Verde, normale, small. Informazioni sulla tempistica <br>
        super.fixHeader();
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        checkDoppio = super.creaFiltroCheckBox(checkDoppio, "Doppi");
        checkMongo = super.creaFiltroCheckBox(checkMongo, "BioMongo");
        checkSoglia = super.creaFiltroCheckBox(checkSoglia, "soglia");
        checkLista = super.creaFiltroCheckBox(checkLista, "lista");
    }

    /**
     * Regola numero, ordine e visibilità delle colonne della grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixColumns() {
        int soglia = WPref.sogliaPaginaNomi.getInt();
        Grid.Column superaSoglia = grid.getColumnByKey("superaSoglia");
        superaSoglia.setHeader(">" + soglia);
    }

    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        super.fixFiltroCheckBox(checkDoppio, "doppio");
        super.fixFiltroCheckBox(checkMongo, "mongo");
        super.fixFiltroCheckBox(checkSoglia, "superaSoglia");
        super.fixFiltroCheckBox(checkLista, "esisteLista");
    }

}// end of CrudList class
