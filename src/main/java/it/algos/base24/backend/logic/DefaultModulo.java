package it.algos.base24.backend.logic;

import org.springframework.stereotype.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Wed, 02-Aug-2023
 * Time: 16:54
 */
@Service
//@Qualifier("DefaultService")
public class DefaultModulo extends CrudModulo {

    /**
     * Regola la modelClazz associata a questo service CrudService e la passa alla superclasse <br>
     */
    public DefaultModulo() {
        super(null);
    }


}
