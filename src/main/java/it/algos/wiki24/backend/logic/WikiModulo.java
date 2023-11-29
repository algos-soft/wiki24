package it.algos.wiki24.backend.logic;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.logic.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 28-Nov-2023
 * Time: 21:56
 */
public abstract class WikiModulo extends CrudModulo {

    protected WPref lastDownload;

    protected WPref durataDownload;

    protected WPref lastElaborazione;

    protected WPref durataElaborazione;

    protected long inizio;

    protected String message;

    @Inject
    protected WikiApiService wikiApiService;

    /**
     * Regola la modelClazz associata a questo Modulo <br>
     * Regola la listClazz associata a questo Modulo <br>
     * Regola la formClazz associata a questo Modulo <br>
     */
    public WikiModulo(Class entityClazz, Class listClazz, Class formClazz) {
        super(entityClazz, listClazz, formClazz);
    }

    public void download() {
    }

}
