package it.algos.base24.ui.form;

import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Fri, 01-Sep-2023
 * Time: 14:57
 */
@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class DefaultForm extends CrudForm {

    public DefaultForm (CrudModulo crudModulo, AbstractEntity entityBean, CrudOperation operation) {
        super(crudModulo,entityBean,operation);
    }

}
