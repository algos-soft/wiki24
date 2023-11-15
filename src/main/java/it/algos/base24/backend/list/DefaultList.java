package it.algos.base24.backend.list;

import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Wed, 02-Aug-2023
 * Time: 19:18
 */
@Component
@Scope(value = SCOPE_PROTOTYPE)
public class DefaultList extends CrudList {

    // @todo ATTENTION QUI
//    public DefaultList(final CrudModulo crudModulo) {
//        super(crudModulo);
//    }

}// end of class

