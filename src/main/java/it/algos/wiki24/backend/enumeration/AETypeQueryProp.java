package it.algos.wiki24.backend.enumeration;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 01-Jul-2023
 * Time: 07:01
 */
public enum AETypeQueryProp {

    ids("&cmprop=ids"),
    title("&cmprop=title"),
    all("&cmprop=ids|title"),
    ;

    private String tag;


    AETypeQueryProp(String tag) {
        this.tag = tag;
    }

    public String get() {
        return tag;
    }

}
