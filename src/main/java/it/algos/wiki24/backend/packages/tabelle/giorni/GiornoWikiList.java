package it.algos.wiki24.backend.packages.tabelle.giorni;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.packages.crono.mese.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;

import javax.inject.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class GiornoWikiList extends WikiList {

    @Inject
    public MeseModulo meseModulo;

    private ComboBox comboMese;

    public GiornoWikiList(final GiornoWikiModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaBottoneResetDelete = true;
        super.usaBottoneDownload = false;
        super.usaBottoneElabora = true;
        super.usaBottoneUploadAll = true;
        super.usaBottoneSearch = false;
        super.usaBottoneExport = false;
        super.usaInfoElabora = true;
        super.usaInfoUpload = true;
        super.usaVariantCompact = true;

        super.usaBottoneWikiView = true;
        super.usaBottoneTest = false;
        super.usaBottoneTest1 = true;
        super.usaBottoneTest2 = true;
        super.usaBottoneUpload = false;
        super.usaBottoneUpload1 = true;
        super.usaBottoneUpload2 = true;
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
        headerPlaceHolder.removeAll();
        int sogliaSottoPagina = WPref.sogliaSottoPaginaGiorniAnni.getInt();

        //Prima riga (infoScopo): Verde, bold, normale. Informazioni base: tavola (download) oppure Lista (upload) <br>
        super.infoScopo = "Liste di nati e morti per giorno."; ;

        //Secondo gruppo: Blue, normale, normale. Logica di creazione/funzionamento della tavola <br>
        super.infoListaPagina = "sempre (per tutti i 366 GG)";
//        super.infoSottoPagina = String.format("quando numBio della pagina > %s",sogliaSottoPagina);
        super.infoListaSottoPagina = "mai"; //@todo per adesso. C'è solo il 1° gennaio.
        super.infoListaSottoSottoPagina = "mai";

       //Sesto gruppo: Verde, normale, small. Informazioni sulla tempistica <br>
        super.fixHeader();
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        comboMese = new ComboBox<>();
        comboMese.setPlaceholder("Mesi...");
        comboMese.setClearButtonVisible(true);
        comboMese.setWidth("12rem");
        comboMese.setItems(meseModulo.findAll());
        comboMese.addValueChangeListener(event -> sincroFiltri());
        wikiTopPlaceHolder.add(comboMese);
    }

    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (comboMese != null) {
            if (comboMese.getValue() != null) {
                if (comboMese.getValue() instanceof MeseEntity mese) {
                    filtri.uguale("mese", mese);
                    filtri.sort(Sort.by(Sort.Direction.ASC, FIELD_NAME_ORDINE));
                }
            }
            else {
                filtri.remove("mese");
            }
        }
    }

}// end of CrudList class
