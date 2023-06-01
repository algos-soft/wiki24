package it.algos.wiki24.backend.packages.template;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.logic.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 21-Apr-2023
 * Time: 08:36
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class TemplateBackend extends CrudBackend {


    public TemplateBackend() {
        super(Template.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Template newEntity() {
        return newEntity(0, VUOTA, null, null,VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Template newEntity(final long keyPropertyValue) {
        return newEntity(keyPropertyValue, VUOTA, null, null, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param pageId    interno del server wiki (obbligatorio, unico)
     * @param wikiTitle (obbligatorio, unico)
     * @param timestamp ultima modifica sul server - ora di greenwich (obbligatorio)
     * @param tmplBio   (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Template newEntity(long pageId, String wikiTitle, LocalDateTime timestamp, LocalDateTime lastMongo, String tmplBio) {
        Template newEntityBean = Template.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .timestamp(timestamp != null ? timestamp : ROOT_DATA_TIME)
                .lastMongo(lastMongo != null ? lastMongo : ROOT_DATA_TIME)
                .tmplBio(textService.isValid(tmplBio) ? tmplBio : null)
                .valido(true)
                .build();

        return (Template) super.fixKey(newEntityBean);
    }


    @Override
    public Template findById(final String keyID) {
        return (Template) super.findById(keyID);
    }

    @Override
    public Template findByKey(final String keyValue) {
        return (Template) super.findByKey(keyValue);
    }

    @Override
    public Template findByProperty(final String propertyName, final Object propertyValue) {
        return (Template) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public Template save(AEntity entity) {
        return (Template) super.save(entity);
    }

    @Override
    public Template insert(AEntity entity) {
        return (Template) super.insert(entity);
    }

    @Override
    public Template update(AEntity entity) {
        return (Template) super.update(entity);
    }

    @Override
    public List<Template> findAll() {
        return super.findAll();
    }

    @Override
    public List<Template> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<Template> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<Template> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<Template> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

}// end of crud backend class
