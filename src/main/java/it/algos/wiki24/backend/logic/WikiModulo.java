package it.algos.wiki24.backend.logic;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;
import java.time.*;

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


    public void fixDownload(final long inizio ) {
        long fine = System.currentTimeMillis();
        Long delta = fine - inizio;

        if (lastDownload != null) {
            lastDownload.setValue(LocalDateTime.now());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("lastDownload è nullo")));
            return;
        }

        if (durataDownload != null) {
            delta = delta / 1000;
                delta = delta / 60;

            durataDownload.setValue(delta.intValue());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("durataDownload è nullo")));
            return;
        }

//        if (textService.isValid(wikiTitle) && sizeServerWiki > 0 && sizeMongoDB > 0) {
//            if (sizeServerWiki == sizeMongoDB) {
//                message = String.format("Download di %s righe da [%s] in %d millisecondi", wikiTxt, wikiTitle, delta);
//            }
//            else {
//                message = String.format("Download di %s righe da [%s] convertite in %s elementi su mongoDB", wikiTxt, wikiTitle, mongoTxt);
//            }
//
//            logger.info(new WrapLog().message(message));
//        }
    }

}
