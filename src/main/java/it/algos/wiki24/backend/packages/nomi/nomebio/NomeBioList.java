package it.algos.wiki24.backend.packages.nomi.nomebio;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.ui.dialog.*;
import it.algos.vbase.ui.wrapper.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NomeBioList extends WikiList {

    private IndeterminateCheckbox checkDoppio;
    private IndeterminateCheckbox checkCategoria;
    private IndeterminateCheckbox checkIncipit;
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
    }

    /**
     * Utilizza il placeHolder header della view per informazioni sulla tavola/lista <br>
     * Può essere sovrascritto, invocando PRIMA o DOPO il metodo della superclasse <br>
     */
    @Override
    public void fixHeader() {
        String message;

        message = "Lista upload. Costruita dalla tavola BioMongo.";
        BSpan testo = BSpan.text(message).bold().verde();
        headerPlaceHolder.add(testo);

        message = "Elabora: recupera tutti i nomi da NomeDoppio, NomeCategoria e NomePagina.";
        headerPlaceHolder.add(ASpan.text(message).blue());
        message = "Spazzola ogni nome per calcolare le occorrenze di numBio.";
        headerPlaceHolder.add(ASpan.text(message).blue());

        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeader();
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        checkDoppio = super.creaFiltroCheckBox(checkDoppio, "Doppi");
        checkCategoria = super.creaFiltroCheckBox(checkCategoria, "Categorie");
        checkIncipit = super.creaFiltroCheckBox(checkIncipit, "Modulo");
        checkMongo = super.creaFiltroCheckBox(checkMongo, "BioMongo");
        checkSoglia = super.creaFiltroCheckBox(checkSoglia, "soglia");
        checkLista = super.creaFiltroCheckBox(checkLista, "lista");
    }


    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        super.fixFiltroCheckBox(checkDoppio, "doppio");
        super.fixFiltroCheckBox(checkCategoria, "categoria");
        super.fixFiltroCheckBox(checkIncipit, "incipit");
        super.fixFiltroCheckBox(checkMongo, "mongo");
        super.fixFiltroCheckBox(checkSoglia, "superaSoglia");
        super.fixFiltroCheckBox(checkLista, "esisteLista");
    }

}// end of CrudList class
