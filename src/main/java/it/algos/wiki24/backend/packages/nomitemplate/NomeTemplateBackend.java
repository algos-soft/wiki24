package it.algos.wiki24.backend.packages.nomitemplate;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
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

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public NomeTemplate newEntity() {
        return newEntity(0, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public NomeTemplate newEntity(final String keyPropertyValue) {
        return newEntity(0, keyPropertyValue, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine      di presentazione nel popup/combobox (obbligatorio, unico)
     * @param code        (obbligatorio, unico)
     * @param descrizione (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public NomeTemplate newEntity(final int ordine, final String code, final String descrizione) {
        NomeTemplate newEntityBean = NomeTemplate.builder()
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

    /**
     * Legge i valori dalla pagina wiki: Template:Incipit lista nomi
     * Crea nomi template <br>
     *
     * @return entities create
     */
    public AResult downloadNomiTemplate(AResult result) {
        String paginaNomiDoppi = TAG_ANTROPONIMI + DOPPI;
        String testoPagina;
        String tag = CAPO + "\\*";
        String nome;
        String[] righe = null;
        List lista = new ArrayList();
        AEntity entityBean;

        testoPagina = wikiApiService.legge(paginaNomiDoppi);

        if (textService.isValid(testoPagina)) {
            righe = testoPagina.split(tag);
        }// end of if cycle

        //--il primo va eliminato (non pertinente)
        for (int k = 1; k < righe.length; k++) {
            nome = righe[k];

            //--l'ultimo va troncato
            if (k == righe.length - 1) {
                nome = nome.substring(0, nome.indexOf("\n\n"));
            }// end of if cycle

            entityBean = insert(newEntity(nome));

        }// end of for cycle

        return result;
    }

}// end of crud backend class
