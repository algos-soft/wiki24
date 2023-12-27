package it.algos.wiki24.backend.packages.sesso;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.logic.*;
import it.algos.wiki24.backend.packages.bioserver.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 27-Dec-2023
 * Time: 07:43
 */
@Service
public class ParametroSessoModulo extends WikiModulo {

    @Inject
    ElaboraService elaboraService;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ParametroSessoModulo() {
        super(ParametroSessoEntity.class, ParametroSessoList.class, ParametroSessoForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastElabora = WPref.lastElaboraParSesso;
        super.durataElabora = WPref.elaboraParSessoTime;
        super.unitaMisuraElabora = TypeDurata.secondi;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public ParametroSessoEntity newEntity() {
        return newEntity(0, VUOTA, VUOTA);
    }

    public ParametroSessoEntity newEntity(long pageId, String grezzo) {
        return newEntity(pageId, grezzo, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param pageId    (obbligatorio)
     * @param grezzo    (obbligatorio)
     * @param elaborato (facoltativo)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ParametroSessoEntity newEntity(long pageId, String grezzo, String elaborato) {
        ParametroSessoEntity newEntityBean = ParametroSessoEntity.builder()
                .pageId(pageId)
                .grezzo(textService.isValid(grezzo) ? grezzo : null)
                .elaborato(textService.isValid(elaborato) ? elaborato : null)
                .build();

        return (ParametroSessoEntity) fixKey(newEntityBean);
    }


    public void elabora() {
        inizio = System.currentTimeMillis();
        ParametroSessoEntity parametroSessoEntity;
        Map<String, String> mappa;
        long pageId;
        String grezzo;

        List<BioServerEntity> lista = mongoService.findAll(BioServerEntity.class);

        for (BioServerEntity bioServerBean : lista) {
            mappa = elaboraService.estraeMappa(bioServerBean);
            pageId = bioServerBean.getPageId();
            grezzo = mappa.get(KEY_MAPPA_SESSO);
            parametroSessoEntity = newEntity(pageId, grezzo);
            parametroSessoEntity = elabora(parametroSessoEntity);
            insertSave(parametroSessoEntity);
        }

        super.fixElabora(inizio);
    }

    public ParametroSessoEntity elabora(ParametroSessoEntity beanGrezzo) {
        ParametroSessoEntity beanElaborato = beanGrezzo;
        String grezzo;

        if (beanGrezzo.grezzo == null) {
            message = String.format("Parametro sesso di [%s] è nullo");
            logger.wrap(new WrapLog().message(message));
            beanElaborato.valido = false;
        }
        grezzo = beanGrezzo.grezzo.trim();

        if (!grezzo.equals(beanGrezzo.grezzo)) {
            message = String.format("Parametro sesso di [%s] contiene spazi vuoti non previsti");
            logger.wrap(new WrapLog().message(message));
            beanElaborato.valido = false;
        }

        return beanElaborato;
    }

}// end of CrudModulo class
