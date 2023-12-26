package it.algos.wiki24.backend.packages.biomongo;

import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class BioMongoList extends WikiList {


    public BioMongoList(final BioMongoModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.propertyListNames = currentCrudModulo.getPropertyNames();
        this.usaDataProvider = true;
        this.basicSortOrder = currentCrudModulo.getBasicSortOrder();
        this.searchFieldName = annotationService.getSearchPropertyName(currentCrudEntityClazz);

        this.usaBottoneDownload = false;
    }

    @Override
    public void fixAlert() {
        VerticalLayout layout = new SimpleVerticalLayout();
        layout.add(ASpan.text(String.format("Prova")).verde());
    }

}// end of CrudList class
