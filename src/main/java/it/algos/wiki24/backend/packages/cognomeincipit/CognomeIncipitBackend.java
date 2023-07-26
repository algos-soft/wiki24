package it.algos.wiki24.backend.packages.cognomeincipit;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.packages.cognomecategoria.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.upload.moduloProgettoAncheBot.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 21-Jun-2023
 * Time: 18:10
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class CognomeIncipitBackend extends WikiBackend {

    @Autowired
    public CognomeCategoriaBackend cognomeCategoriaBackend;

    public CognomeIncipitBackend() {
        super(CognomeIncipit.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.downloadCognomiIncipit;
        super.lastDownload = WPref.downloadCognomiIncipit;
        super.lastElaborazione = WPref.elaboraCognomiIncipit;
        super.durataElaborazione = WPref.elaboraCognomiIncipitTime;
        super.lastUpload = WPref.uploadCognomiIncipit;

        this.unitaMisuraElaborazione = AETypeTime.secondi;
        super.sorgenteDownload = TAG_INCIPIT_COGNOMI;
        super.tagSplitSorgente = VIRGOLA_CAPO;
        super.uploadTestName = UPLOAD_TITLE_DEBUG + INCIPIT_COGNOMI;
    }

    public CognomeIncipit creaIfNotExist(final String riga) {
        CognomeIncipit entityBean;
        WrapDueStringhe wrap = wikiUtility.creaWrapUgualePulito(riga);

        entityBean = newEntity(wrap.prima, wrap.seconda, false);
        return entityBean != null ? insert(entityBean) : null;
    }

    public CognomeIncipit creaIfNotExist(final String keyPropertyValue, final String linkPagina, boolean aggiunto) {
        CognomeIncipit entityBean;

        if (textService.isEmpty(keyPropertyValue) || isExistByKey(keyPropertyValue)) {
            return null;
        }
        else {
            entityBean = newEntity(keyPropertyValue, linkPagina, aggiunto);
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
    public CognomeIncipit newEntity() {
        return newEntity(VUOTA, VUOTA, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public CognomeIncipit newEntity(final String keyPropertyValue) {
        return newEntity(keyPropertyValue, VUOTA, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param cognome    (obbligatorio, unico)
     * @param linkPagina (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public CognomeIncipit newEntity(final String cognome, final String linkPagina, boolean aggiunto) {
        CognomeIncipit newEntityBean = CognomeIncipit.builder()
                .cognome(textService.isValid(cognome) ? cognome : null)
                .linkPagina(textService.isValid(linkPagina) ? linkPagina : null)
                .aggiunto(aggiunto)
                .build();

        return (CognomeIncipit) super.fixKey(newEntityBean);
    }


    @Override
    public CognomeIncipit findById(final String keyID) {
        return (CognomeIncipit) super.findById(keyID);
    }

    @Override
    public CognomeIncipit findByKey(final String keyValue) {
        return (CognomeIncipit) super.findByKey(keyValue);
    }

    @Override
    public CognomeIncipit findByProperty(final String propertyName, final Object propertyValue) {
        return (CognomeIncipit) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public CognomeIncipit save(AEntity entity) {
        return (CognomeIncipit) super.save(entity);
    }

    @Override
    public CognomeIncipit insert(AEntity entity) {
        return (CognomeIncipit) super.insert(entity);
    }

    @Override
    public CognomeIncipit update(AEntity entity) {
        return (CognomeIncipit) super.update(entity);
    }

    @Override
    public List<CognomeIncipit> findAll() {
        List<CognomeIncipit> lista = super.findAll();
        return lista.stream().sorted(Comparator.comparing(cognomeIncipit -> ((CognomeIncipit) cognomeIncipit).cognome)).collect(Collectors.toList());
    }

    @Override
    public List<CognomeIncipit> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<CognomeIncipit> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<CognomeIncipit> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<CognomeIncipit> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    public List<CognomeIncipit> findAllByNotUguali() {
        return findAll()
                .stream()
                .filter(cognome -> !cognome.uguale)
                .collect(Collectors.toList());
    }

    @Override
    public LinkedHashMap<String, String> findMappa() {
        LinkedHashMap<String, String> mappa = new LinkedHashMap<>();
        List<CognomeIncipit> lista = findAllByNotUguali();

        for (CognomeIncipit cognomeModulo : lista) {
            mappa.put(cognomeModulo.cognome, cognomeModulo.linkPagina);
        }

        return mappa;
    }


    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();

        //--Cancella la (eventuale) precedente lista di nomi template
        deleteAll();

        result = downloadCognomiModulo(result);

        return super.fixResetDownload(result);
    }

    /**
     * Legge i valori dalla pagina wiki: Template:Incipit lista nomi
     * Crea nomi template <br>
     *
     * @return entities create
     */
    public AResult downloadCognomiModulo(AResult result) {
        CognomeIncipit entityBean;
        List<AEntity> lista = new ArrayList<>();

        for (String riga : super.getRighe()) {
            entityBean = creaIfNotExist(riga);
            result.setValido(fixLista(lista, entityBean, riga));
        }

        return super.fixResult(result, lista);
    }


    @Override
    public WResult elabora() {
        WResult result = super.elabora();
        List<CognomeCategoria> listaCognomiCategorie = null;
        CognomeIncipit entityBean;
        List<AEntity> lista = new ArrayList<>();
        List<CognomeIncipit> listaCognomi;
        String suffissoCognome = SPAZIO + "(Cognome)";

        resetDownload();

        //--Controllo e recupero di NomiCategoria
        cognomeCategoriaBackend.resetDownload();
        listaCognomiCategorie = cognomeCategoriaBackend.findAll();

        if (listaCognomiCategorie != null) {
            for (CognomeCategoria cognomeCategoria : listaCognomiCategorie) {

                //devo fare un doppio controllo perché alcuni nomi potrebbero già esserci e NON è un errore
                if (isExistByKey(cognomeCategoria.cognome)) {
                    message = String.format("Il cognome [%s] esiste già", cognomeCategoria.cognome);
                    logService.debug(new WrapLog().message(message));
                }
                else {
                    entityBean = creaIfNotExist(cognomeCategoria.cognome, cognomeCategoria.linkPagina, true);
                    result.setValido(fixLista(lista, entityBean, cognomeCategoria.cognome));
                }
            }
        }

        //        //--Controllo nomi uguali che vanno omessi nel modulo
        //        //--Sono uguali se il nome della persona coincide con la pagina di riferimento
        //        //--Sono uguali se il nome della persona coincide con la pagina di riferimento seguita dal suffisso (nome)
        //        listaNomi = findAll();
        //        for (NomeIncipit nome : listaNomi) {
        //            if (nome.linkPagina.equals(nome.nome) || nome.linkPagina.equals(nome.nome + suffissoNome)) {
        //                nome.uguale = true;
        //                save(nome);
        //            }
        //        }

        //--Controllo nomi uguali che vanno omessi nel modulo
        //--Sono uguali se il nome della persona coincide con la pagina di riferimento
        //        listaCognomi = findAll();
        //        for (CognomeIncipit cognome : listaCognomi) {
        //            if (cognome.linkPagina.equals(cognome.cognome)) {
        //                cognome.uguale = true;
        //                save(cognome);
        //            }
        //        }

        return super.fixElabora(result);
    }


    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    @Override
    public WResult uploadModulo() {
        WResult result = new WResult();
//        WResult result = appContext.getBean(UploadModuloCognomiIncipit.class).esegue();

        if (result.isModificata()) {
            message = String.format("Upload e modifica della pagina [%s]", super.sorgenteDownload);
            logService.info(new WrapLog().message(message).type(AETypeLog.upload).usaDb());
        }

        return result;
    }

}// end of crud backend class
