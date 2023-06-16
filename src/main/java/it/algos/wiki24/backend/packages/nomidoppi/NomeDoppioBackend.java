package it.algos.wiki24.backend.packages.nomidoppi;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.wiki.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 12-Jun-2023
 * Time: 18:21
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class NomeDoppioBackend extends WikiBackend {


    public NomeDoppioBackend() {
        super(NomeDoppio.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = null;
        super.lastDownload = WPref.downloadNomiDoppi;
        this.unitaMisuraDownload = AETypeTime.secondi;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public NomeDoppio newEntity() {
        return newEntity(VUOTA);
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
    public NomeDoppio newEntity(final String nome) {
        NomeDoppio newEntityBean = NomeDoppio.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .build();

        return (NomeDoppio) super.fixKey(newEntityBean);
    }


    @Override
    public NomeDoppio findById(final String keyID) {
        return (NomeDoppio) super.findById(keyID);
    }

    @Override
    public NomeDoppio findByKey(final String keyValue) {
        return (NomeDoppio) super.findByKey(keyValue);
    }

    @Override
    public NomeDoppio findByProperty(final String propertyName, final Object propertyValue) {
        return (NomeDoppio) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public NomeDoppio save(AEntity entity) {
        return (NomeDoppio) super.save(entity);
    }

    @Override
    public NomeDoppio insert(AEntity entity) {
        return (NomeDoppio) super.insert(entity);
    }

    @Override
    public NomeDoppio update(AEntity entity) {
        return (NomeDoppio) super.update(entity);
    }

    @Override
    public List<NomeDoppio> findAll() {
        return super.findAll();
    }

    @Override
    public List<NomeDoppio> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<NomeDoppio> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<NomeDoppio> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<NomeDoppio> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    /**
     * Fetches all code of Prenome <br>
     *
     * @return all selected property
     */
    public List<String> fetchCode() {
        List<String> lista = new ArrayList<>();
        List<NomeDoppio> listaEntities = null;

        for (NomeDoppio doppio : listaEntities) {
            lista.add(doppio.nome);
        }

        return lista;
    }

    /**
     * ResetOnlyEmpty -> Download. <br>
     * Download -> Cancella tutto e scarica 1 lista di nomi doppi da wiki: Progetto:Antroponimi/Nomi doppi. <br>
     * Upload -> Non previsto. <br>
     * <p>
     * Cancella la (eventuale) precedente lista di nomi doppi <br>
     * Legge i valori dalla pagina wiki: <br>
     * Progetto:Antroponimi/Nomi doppi
     */
    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();

        //--Cancella la (eventuale) precedente lista di nomi doppi
        deleteAll();

        result = downloadNomiDoppi(result);

        return super.fixResetDownload(result);
    }

    /**
     * Legge i valori dalla pagina wiki: Progetto:Antroponimi/Nomi doppi
     * Crea nomi doppi <br>
     *
     * @return entities create
     */
    public AResult downloadNomiDoppi(AResult result) {
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
