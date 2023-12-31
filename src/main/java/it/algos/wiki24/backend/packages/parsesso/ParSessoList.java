package it.algos.wiki24.backend.packages.parsesso;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.list.*;
import it.algos.wiki24.backend.packages.bioserver.*;
import it.algos.wiki24.backend.service.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;

import javax.inject.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class ParSessoList extends WikiList {

    @Inject
    BioServerModulo bioServerModulo;

    @Inject
    WikiApiService wikiApiService;

    private IndeterminateCheckbox boxValido;

    private IndeterminateCheckbox boxPieno;

    public ParSessoList(final ParSessoModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.usaBottoneDownload = false;
        this.usaBottoneTransfer = true;
        this.usaBottoneResetEntity = true;
        this.usaBottoneWikiView = true;
        this.usaBottoneWikiEdit = true;
        this.usaBottoneWikiCrono = true;

        super.usaInfoElabora = true;

        this.usaBottoneSearch = false;
        this.usaSearchPageId = true;
        this.usaSearchWikiTitle = true;
    }


    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        boxValido = new IndeterminateCheckbox();
        boxValido.setLabel("Valido");
        boxValido.setIndeterminate(false);
        boxValido.setValue(false);
        boxValido.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout1 = new HorizontalLayout(boxValido);
        layout1.setAlignItems(Alignment.CENTER);
        buttonBar.add(layout1);

        boxPieno = new IndeterminateCheckbox();
        boxPieno.setLabel("Pieno");
        boxPieno.setIndeterminate(false);
        boxPieno.setValue(false);
        boxPieno.addValueChangeListener(event -> sincroFiltri());
        HorizontalLayout layout2 = new HorizontalLayout(boxPieno);
        layout2.setAlignItems(Alignment.CENTER);
        buttonBar.add(layout2);
    }


    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (boxValido != null && !boxValido.isIndeterminate()) {
            filtri.uguale("valido", boxValido.getValue());
        }
        else {
            filtri.remove("valido");
        }

        if (boxPieno != null && !boxPieno.isIndeterminate()) {
            filtri.uguale("pieno", boxPieno.getValue());
        }
        else {
            filtri.remove("pieno");
        }

        if (filtri.mappaFiltri.containsKey(FIELD_NAME_PAGE_ID) || filtri.mappaFiltri.containsKey(FIELD_NAME_WIKI_TITLE)) {
            filtri.remove("valido");
            filtri.remove("pieno");
        }
    }

    public void wikiView() {
        AbstractEntity crudEntityBean = getSingleEntity();
        long pageId;
        String wikiTitle;

        if (crudEntityBean != null && crudEntityBean instanceof ParSessoEntity parSessoBean) {
            pageId = parSessoBean.pageId;
            wikiTitle = bioServerModulo.findByKey(pageId).wikiTitle;
            wikiApiService.openWikiPage(wikiTitle);
        }
    }

    public void wikiEdit() {
        AbstractEntity crudEntityBean = getSingleEntity();
        long pageId;
        String wikiTitle;

        if (crudEntityBean != null && crudEntityBean instanceof ParSessoEntity parSessoBean) {
            pageId = parSessoBean.pageId;
            wikiTitle = bioServerModulo.findByKey(pageId).wikiTitle;
            wikiApiService.editWikiPage(wikiTitle);
        }
    }

    public void wikiCrono() {
        AbstractEntity crudEntityBean = getSingleEntity();
        long pageId;
        String wikiTitle;

        if (crudEntityBean != null && crudEntityBean instanceof ParSessoEntity parSessoBean) {
            pageId = parSessoBean.pageId;
            wikiTitle = bioServerModulo.findByKey(pageId).wikiTitle;
            wikiApiService.cronoWikiPage(wikiTitle);
        }
    }


}// end of CrudList class
