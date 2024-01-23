package it.algos.wiki24.backend.packages.bio.biomongo;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import static it.algos.wiki24.backend.boot.WikiCost.FIELD_NAME_NOME;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class BioMongoList extends WikiList {

    protected TextField searchNome;

    protected TextField searchCognome;

    protected TextField searchSesso;

    protected TextField searchLuogoNato;

    protected TextField searchGiornoNato;

    protected TextField searchAnnoNato;

    protected TextField searchLuogoMorto;

    protected TextField searchGiornoMorto;

    protected TextField searchAnnoMorto;

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

        super.usaBottoneEdit = true;
        super.usaInfoElabora = true;
        super.usaBottoneElaboraDue = true;
        super.usaBottoneResetEntity = true;
    }
    @Override
    public void fixAlert() {
        String category = WPref.categoriaBio.getStr();
        Span biografie;
        Span contiene;
        String numPagine;
        String categoria = TAG_WIKI + CAT + WPref.categoriaBio.getStr();

        Anchor anchor = new Anchor(categoria, textService.setQuadre(category));
        anchor.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        message = "Biografie con i parametri significativi. Elaborati da BioServer";
        biografie = new Span(message);
        biografie.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        biografie.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());
        alertPlaceHolder.add(new Span(biografie));

        message = "Elabora: ricontrolla tutta la collection. La singola elaborazione viene effettuata dal download di BioServer.";
        alertPlaceHolder.add(ASpan.text(message).size(FontSize.em8).rosso());
    }


    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        searchNome = creaSearch(FIELD_NAME_NOME);
        searchCognome = creaSearch(FIELD_NAME_COGNOME);
        searchSesso = creaSearch(FIELD_NAME_SESSO);
        buttonBar.add(searchNome, searchCognome, searchSesso);

        searchLuogoNato = creaSearch(FIELD_NAME_LUOGO_NATO);
        searchGiornoNato = creaSearch(FIELD_NAME_GIORNO_NATO);
        searchAnnoNato = creaSearch(FIELD_NAME_ANNO_NATO);
        searchLuogoMorto = creaSearch(FIELD_NAME_LUOGO_MORTO);
        searchGiornoMorto = creaSearch(FIELD_NAME_GIORNO_MORTO);
        searchAnnoMorto = creaSearch(FIELD_NAME_ANNO_MORTO);

        this.add(new HorizontalLayout(searchLuogoNato, searchGiornoNato, searchAnnoNato, searchLuogoMorto, searchGiornoMorto, searchAnnoMorto));
    }

    protected TextField creaSearch(String fieldName) {
        TextField searchField = new TextField();

        searchField.setPlaceholder(TAG_ALTRE_BY + fieldName);
        searchField.getElement().setProperty("title", "Search: ricerca per il valore del campo " + fieldName);
        searchField.setClearButtonVisible(true);
        searchField.addValueChangeListener(event -> this.sincroFiltri());

        return searchField;
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
