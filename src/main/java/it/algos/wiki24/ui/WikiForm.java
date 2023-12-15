package it.algos.wiki24.ui;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.form.*;
import it.algos.wiki24.backend.logic.*;
import org.atmosphere.interceptor.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 14-Dec-2023
 * Time: 07:42
 */
public abstract class WikiForm extends CrudForm {

    public WikiForm(WikiModulo crudModulo, AbstractEntity entityBean, CrudOperation operation) {
        super(crudModulo, entityBean, operation);
    }


    /**
     * Barra dei bottoni <br>
     * Placeholder (eventuale, presente di default) <br>
     */

}
