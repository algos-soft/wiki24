package it.algos.wiki24.backend.packages.bio.biomongo;

import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import static it.algos.wiki24.backend.boot.WikiCost.FIELD_NAME_NOME;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class BioMongoList extends WikiList {

    protected TextField searchNome = new TextField();

    protected TextField searchCognome = new TextField();

    protected TextField searchSesso = new TextField();

    public BioMongoList(final BioMongoModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.usaDataProvider = true;
        this.basicSortOrder = currentCrudModulo.getBasicSortOrder();
        this.searchFieldName = annotationService.getSearchPropertyName(currentCrudEntityClazz);

        this.usaBottoneSearch = false;
        this.usaSearchPageId = true;
        this.usaSearchWikiTitle = true;
        this.usaBottoneDownload = false;
        this.usaBottoneTransfer = true;

        super.usaInfoElabora = true;
    }


    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        searchNome.setPlaceholder(TAG_ALTRE_BY + FIELD_NAME_NOME);
        searchNome.getElement().setProperty("title", "Search: ricerca per il valore del campo " + FIELD_NAME_NOME);
        searchNome.setClearButtonVisible(true);
        searchNome.addValueChangeListener(event -> this.sincroFiltri());

        searchCognome.setPlaceholder(TAG_ALTRE_BY + FIELD_NAME_COGNOME);
        searchCognome.getElement().setProperty("title", "Search: ricerca per il valore del campo " + FIELD_NAME_COGNOME);
        searchCognome.setClearButtonVisible(true);
        searchCognome.addValueChangeListener(event -> this.sincroFiltri());

        searchSesso.setPlaceholder(TAG_ALTRE_BY + FIELD_NAME_SESSO);
        searchSesso.getElement().setProperty("title", "Search: ricerca per il valore del campo " + FIELD_NAME_SESSO);
        searchSesso.setClearButtonVisible(true);
        searchSesso.addValueChangeListener(event -> this.sincroFiltri());

        buttonBar.add(searchNome, searchCognome, searchSesso);
    }

    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (searchNome != null) {
            String nome = searchNome.getValue();
            if (textService.isValid(nome)) {
                filtri.inizio(FIELD_NAME_NOME, nome);
                filtri.sort(Sort.Order.asc(FIELD_NAME_NOME));
            }
            else {
                filtri.remove(FIELD_NAME_NOME);
                filtri.sort(basicSortOrder);
            }
        }

        if (searchCognome != null) {
            String cognome = searchCognome.getValue();
            if (textService.isValid(cognome)) {
                filtri.inizio(FIELD_NAME_COGNOME, cognome);
                filtri.sort(Sort.Order.asc(FIELD_NAME_COGNOME));
            }
            else {
                filtri.remove(FIELD_NAME_COGNOME);
                filtri.sort(basicSortOrder);
            }
        }

        if (searchSesso != null) {
            String sesso = searchSesso.getValue();
            if (textService.isValid(sesso)) {
                filtri.uguale(FIELD_NAME_SESSO, sesso);
                filtri.sort(Sort.Order.asc(FIELD_NAME_SESSO));
            }
            else {
                filtri.remove(FIELD_NAME_SESSO);
                filtri.sort(basicSortOrder);
            }
        }
    }

}// end of CrudList class
