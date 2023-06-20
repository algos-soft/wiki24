package it.algos.wiki24.backend.packages.nomitemplate;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.upload.progetto.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 18-Jun-2023
 * Time: 12:06
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class NomeTemplateBackend extends WikiBackend {


    public NomeTemplateBackend() {
        super(NomeTemplate.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.downloadNomiTemplate;
        super.lastDownload = WPref.downloadNomiTemplate;
        this.unitaMisuraDownload = AETypeTime.secondi;
    }

    public AEntity creaIfNotExist(final String keyPropertyValue, String linkPagina) {
        AEntity entityBean;

        if (textService.isEmpty(keyPropertyValue) || isExistByKey(keyPropertyValue)) {
            return null;
        }
        else {
            entityBean = newEntity(keyPropertyValue, linkPagina);
            return entityBean != null ? insert(entityBean) : null;
        }
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public NomeTemplate newEntity() {
        return newEntity( VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public NomeTemplate newEntity(final String keyPropertyValue) {
        return newEntity(keyPropertyValue, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param nome        (obbligatorio, unico)
     * @param linkPagina (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public NomeTemplate newEntity( final String nome, final String linkPagina) {
        NomeTemplate newEntityBean = NomeTemplate.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .linkPagina(textService.isValid(linkPagina) ? linkPagina : null)
                .build();

        return (NomeTemplate) super.fixKey(newEntityBean);
    }


    @Override
    public NomeTemplate findById(final String keyID) {
        return (NomeTemplate) super.findById(keyID);
    }

    @Override
    public NomeTemplate findByKey(final String keyValue) {
        return (NomeTemplate) super.findByKey(keyValue);
    }

    @Override
    public NomeTemplate findByProperty(final String propertyName, final Object propertyValue) {
        return (NomeTemplate) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public NomeTemplate save(AEntity entity) {
        return (NomeTemplate) super.save(entity);
    }

    @Override
    public NomeTemplate insert(AEntity entity) {
        return (NomeTemplate) super.insert(entity);
    }

    @Override
    public NomeTemplate update(AEntity entity) {
        return (NomeTemplate) super.update(entity);
    }

    @Override
    public List<NomeTemplate> findAll() {
        return super.findAll();
    }

    @Override
    public List<NomeTemplate> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<NomeTemplate> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<NomeTemplate> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<NomeTemplate> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }


    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();

        //--Cancella la (eventuale) precedente lista di nomi template
        deleteAll();

        result = downloadNomiTemplate(result);

        return super.fixResetDownload(result);
    }

    /**
     * Legge i valori dalla pagina wiki: Template:Incipit lista nomi
     * Crea nomi template <br>
     *
     * @return entities create
     */
    public AResult downloadNomiTemplate(AResult result) {
        String paginaNomiDoppi = TAG_INCIPIT_NOMI;
        String testoPagina;
        String testoCore;
        String nome;
        String linkPagina;
        String[] righe = null;
        String[] parti = null;
        String tagIni = "switch:{{{nome}}}";
        String tagEnd = "|#default";
        AEntity entityBean;
        List<AEntity> lista = new ArrayList<>();

        testoPagina = wikiApiService.legge(paginaNomiDoppi);
        testoCore = textService.estrae(testoPagina, tagIni, tagEnd);
        if (textService.isValid(testoCore)) {
            if (testoCore.startsWith(PIPE)) {
                testoCore = textService.levaTesta(testoCore, PIPE);
            }
            righe = testoCore.split(PIPE_REGEX);
        }// end of if cycle

        //--valori validi
        for (int k = 0; k < righe.length; k++) {
            parti = righe[k].split(UGUALE);
            if (parti.length == 2) {
                nome = parti[0].trim();
                linkPagina = parti[1].trim();

                entityBean = creaIfNotExist(nome, linkPagina);
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logService.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", nome))).usaDb());
                    result.setValido(false);
                }
            }
        }// end of for cycle

        return super.fixResult(result, lista);
    }


    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    public WResult riordinaModulo() {
        WResult result;

        result = appContext.getBean(UploadProgettoAntroponimiNomiTemplate.class).uploadOrdinatoConModifiche();
        super.fixRiordinaModulo(result);

        return result;
    }

}// end of crud backend class
