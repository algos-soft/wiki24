package it.algos.wiki24.backend.packages.tabelle.giorni;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.packages.crono.mese.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.Scope;

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
        super.usaBottoneSearch = typeList.isUsaBottoneSearch();
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
    }

    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (comboMese != null) {
            if (comboMese.getValue() != null) {
                if (comboMese.getValue() instanceof MeseEntity mese) {
                    filtri.uguale("mese", mese);
                }
            }
            else {
                filtri.remove("mese");
            }
        }
    }

}// end of CrudList class
