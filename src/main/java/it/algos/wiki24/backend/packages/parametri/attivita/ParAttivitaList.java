package it.algos.wiki24.backend.packages.parametri.attivita;

import com.vaadin.flow.spring.annotation.*;
import it.algos.wiki24.backend.packages.parametri.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class ParAttivitaList extends ParList {


    public ParAttivitaList(final ParAttivitaModulo crudModulo) {
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

}// end of CrudList class
