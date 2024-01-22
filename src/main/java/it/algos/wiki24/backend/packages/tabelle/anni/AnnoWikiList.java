package it.algos.wiki24.backend.packages.tabelle.anni;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.packages.crono.secolo.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.Scope;

import javax.inject.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class AnnoWikiList extends WikiList {

    @Inject
    public SecoloModulo secoloModulo;

    private ComboBox comboSecolo;

    public AnnoWikiList(final AnnoWikiModulo crudModulo) {
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
        this.usaInfoUpload = true;
        super.usaVariantCompact = true;

        super.usaBottoneWikiView = true;
        super.usaBottoneTest1 = true;
        super.usaBottoneTest2 = true;
        super.usaBottoneUpload1 = true;
        super.usaBottoneUpload2 = true;
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        comboSecolo = new ComboBox<>();
        comboSecolo.setPlaceholder("Secoli...");
        comboSecolo.setClearButtonVisible(true);
        comboSecolo.setWidth("12rem");
        comboSecolo.setItems(secoloModulo.findAll());
        comboSecolo.addValueChangeListener(event -> sincroFiltri());
        buttonBar.add(comboSecolo);
    }

    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (comboSecolo != null) {
            if (comboSecolo.getValue() != null) {
                if (comboSecolo.getValue() instanceof SecoloEntity mese) {
                    filtri.uguale("secolo", mese);
                }
            }
            else {
                filtri.remove("secolo");
            }
        }
    }

}// end of CrudList class
