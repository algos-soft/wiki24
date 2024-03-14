package it.algos.wiki24.backend.packages.bio.biomongo;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.components.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.FIELD_NAME_NOME;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.*;

import java.util.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class BioMongoList extends WikiList {

    private List<WikiSearch> wikiSearchListAnte;

    private List<WikiSearch> wikiSearchListPost;

    public BioMongoList(final BioMongoModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.usaDataProvider = true;
        this.basicSort = currentCrudModulo.getBasicSort();
        this.searchFieldName = annotationService.getSearchPropertyName(currentCrudEntityClazz);

        this.usaBottoneSearch = false;
        this.usaSearchPageId = true;
        this.usaSearchWikiTitle = true;
        this.usaBottoneDownload = false;
        this.usaBottoneTransfer = true;

        super.usaBottoneEdit = true;
        super.usaInfoElabora = true;
        super.usaBottoneElaboraDue = true;
        super.usaBottoneResetEntity = true;
    }

    @Override
    protected void fixHeader() {
        String category = WPref.categoriaBio.getStr();
        Span biografie;

        message = "Biografie con i parametri significativi. Elaborati da BioServer";
        biografie = new Span(message);
        biografie.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        biografie.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());
        headerPlaceHolder.add(new Span(biografie));

        message = "Elabora: ricontrolla tutta la collection. La singola elaborazione viene effettuata dal download di BioServer.";
        headerPlaceHolder.add(ASpan.text(message).size(FontSize.em8).rosso());

        super.fixHeader();
    }

    /**
     * Può essere sovrascritto <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        wikiSearchListAnte = new ArrayList<>();

        wikiSearchListAnte.add(new WikiSearch(FIELD_NAME_NOME));
        wikiSearchListAnte.add(new WikiSearch(FIELD_NAME_COGNOME));
        wikiSearchListAnte.add(new WikiSearch(FIELD_NAME_SESSO));

        wikiSearchListAnte.stream().forEach(wikiSearch -> wikiSearch.clickHandler(this::sincroFiltri));
        wikiSearchListAnte.stream().forEach(wikiSearch -> wikiTopPlaceHolder.add(wikiSearch.getSearchField()));
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void addTopPlaceHolder() {
        super.addTopPlaceHolder();

        SimpleHorizontalLayout layout = new SimpleHorizontalLayout();
        wikiSearchListPost = new ArrayList<>();

        wikiSearchListPost.add(new WikiSearch(FIELD_NAME_LUOGO_NATO));
        wikiSearchListPost.add(new WikiSearch(FIELD_NAME_GIORNO_NATO));
        wikiSearchListPost.add(new WikiSearch(FIELD_NAME_ANNO_NATO));
        wikiSearchListPost.add(new WikiSearch(FIELD_NAME_LUOGO_MORTO));
        wikiSearchListPost.add(new WikiSearch(FIELD_NAME_GIORNO_MORTO));
        wikiSearchListPost.add(new WikiSearch(FIELD_NAME_ANNO_MORTO));
        wikiSearchListPost.add(new WikiSearch(FIELD_NAME_ATTIVITA));
        wikiSearchListPost.add(new WikiSearch(FIELD_NAME_NAZIONALITA));

        wikiSearchListPost.stream().forEach(wikiSearch -> wikiSearch.clickHandler(this::sincroFiltri));
        wikiSearchListPost.stream().forEach(wikiSearch -> layout.add(wikiSearch.getSearchField()));
        this.add(layout);
    }

    protected TextField creaSearch(String fieldName) {
        TextField searchField = new TextField();

        searchField.setPlaceholder(TAG_ALTRE_BY + fieldName);
        searchField.getElement().setProperty("title", "Search: ricerca per il valore del campo " + fieldName);
        searchField.setClearButtonVisible(true);
        searchField.addValueChangeListener(event -> this.sincroFiltri());

        return searchField;
    }

    public void sincroFiltri(boolean flag) {
        super.sincroFiltri();
    }

    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        if (wikiSearchListAnte != null) {
            wikiSearchListAnte.stream().forEach(wikiSearch -> fixFiltro(wikiSearch));
        }
        if (wikiSearchListPost != null) {
            wikiSearchListPost.stream().forEach(wikiSearch -> fixFiltro(wikiSearch));
        }
    }


    protected void fixFiltro(WikiSearch wikiSearch) {
        String textFieldValue;
        TextField searchField = wikiSearch.getSearchField();
        String propertyName = wikiSearch.getPropertyName();

        if (searchField != null) {
            textFieldValue = searchField.getValue();
            if (textService.isValid(textFieldValue)) {
                filtri.inizio(propertyName, textFieldValue);
                filtri.sort(Sort.by(Sort.Direction.ASC, propertyName));
            }
            else {
                filtri.remove(propertyName);
                filtri.sort(basicSort);
            }
        }
    }

}// end of CrudList class
