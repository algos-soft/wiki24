package it.algos.wiki24.backend.enumeration;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 08-Jul-2023
 * Time: 22:40
 */
public enum AETypeQueryType {
    page("&cmtype=page"),
    subCat("&cmtype=subcat"),
    file("&cmtype=file"),
    ;

    private String tag;


    AETypeQueryType(String tag) {
        this.tag = tag;
    }

    public String get() {
        return tag;
    }

}
