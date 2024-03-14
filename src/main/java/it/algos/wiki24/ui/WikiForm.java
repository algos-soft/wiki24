package it.algos.wiki24.ui;

import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.ui.form.*;
import it.algos.wiki24.backend.logic.*;

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
