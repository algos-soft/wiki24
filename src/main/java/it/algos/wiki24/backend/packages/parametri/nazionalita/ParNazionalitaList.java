package it.algos.wiki24.backend.packages.parametri.nazionalita;

import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import it.algos.wiki24.backend.packages.parametri.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class ParNazionalitaList extends ParList {


    public ParNazionalitaList(final ParNazionalitaModulo crudModulo) {
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

    @Override
    public void fixAlert() {
        VerticalLayout layout = new SimpleVerticalLayout();
        layout.add(ASpan.text(String.format("Prova")).verde());
    }

}// end of CrudList class
