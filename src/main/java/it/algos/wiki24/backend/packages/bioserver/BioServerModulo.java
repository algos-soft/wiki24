package it.algos.wiki24.backend.packages.bioserver;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import javax.inject.*;
import java.time.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 13-Dec-2023
 * Time: 21:41
 */
@Service
public class BioServerModulo extends WikiModulo {

    @Inject
    WikiBotService wikiBotService;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public BioServerModulo() {
        super(BioServerEntity.class, BioServerList.class, BioServerForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    public BioServerEntity newEntity(WrapPage wrapPage) {
        String tmplBio = wikiBotService.estraeTmplBio(wrapPage.getContent());
        return newEntity(wrapPage.getPageid(), wrapPage.getTitle(), tmplBio, wrapPage.getTimeStamp(),null);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public BioServerEntity newEntity() {
        return newEntity(0, VUOTA, VUOTA, null,null);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param pageId     (obbligatorio)
     * @param wikiTitle  (obbligatorio)
     * @param tmplBio    (facoltativo)
     * @param lastServer (facoltativo)
     * @param lastMongo (facoltativo)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public BioServerEntity newEntity(
            long pageId,
            String wikiTitle,
            String tmplBio,
            LocalDateTime lastServer,
            LocalDateTime lastMongo) {
        BioServerEntity newEntityBean = BioServerEntity.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .tmplBio(textService.isValid(tmplBio) ? tmplBio : null)
                .lastServer(lastServer != null ? lastServer : ROOT_DATA_TIME)
                .lastMongo(lastMongo != null ? lastMongo : LocalDateTime.now())
                .build();

        return (BioServerEntity) fixKey(newEntityBean);
    }

}// end of CrudModulo class
