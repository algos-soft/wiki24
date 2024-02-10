package it.algos.wiki24.backend.list;

import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.ui.form.*;
import it.algos.base24.ui.view.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.function.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 10-Feb-2024
 * Time: 07:58
 */
public class WikiSearch {

    private TextField searchField;

    private String propertyName;

    protected Consumer<Boolean> clickHandler;

    public WikiSearch(String propertyName) {
        this.propertyName = propertyName;

        searchField = new TextField();
        searchField.setPlaceholder(TAG_ALTRE_BY + propertyName);
        searchField.getElement().setProperty("title", "Search: ricerca per il valore del campo " + propertyName);
        searchField.setClearButtonVisible(true);
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WikiSearch clickHandler(Consumer<Boolean> clickHandler) {
        this.clickHandler = clickHandler;

        if (searchField != null) {
            searchField.addValueChangeListener(event -> clickHandler());
        }

        return this;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void clickHandler() {
        if (clickHandler != null) {
            clickHandler.accept(true);
        }
    }

}
