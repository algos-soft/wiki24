package it.algos.wiki24.backend.packages.nazplurale;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.upload.moduli.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 23-Mar-2023
 * Time: 19:20
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class NazPluraleBackend extends WikiBackend {


    public NazPluraleBackend() {
        super(NazPlurale.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastReset = WPref.resetAttPlurale;
        super.lastDownload = WPref.downloadNazPlurale;
        super.durataDownload = WPref.downloadNazPluraleTime;
        super.lastElaborazione = WPref.elaboraNazPlurale;
        super.durataElaborazione = WPref.elaboraNazPluraleTime;
        super.lastUpload = WPref.uploadNazPlurale;
        super.durataUpload = WPref.uploadNazPluraleTime;
        super.nextUpload = WPref.uploadNazPluralePrevisto;
        super.lastStatistica = WPref.statisticaNazPlurale;

        this.unitaMisuraDownload = AETypeTime.secondi;
        this.unitaMisuraElaborazione = AETypeTime.minuti;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public NazPlurale newEntity() {
        return newEntity(VUOTA, null, VUOTA, VUOTA);
    }

    @Override
    public NazPlurale newEntity(final String keyPropertyValue) {
        return newEntity(keyPropertyValue, null, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param nome           (obbligatorio, unico)
     * @param listaSingolari (obbligatorio, unico)
     * @param paginaLista    (obbligatorio)
     * @param linkNazione    (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public NazPlurale newEntity(final String nome, List<NazSingolare> listaSingolari, final String paginaLista, final String linkNazione) {
        NazPlurale newEntityBean = NazPlurale.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .listaSingolari(listaSingolari)
                .paginaLista(textService.isValid(paginaLista) ? paginaLista : textService.primaMaiuscola(nome))
                .linkNazione(textService.isValid(linkNazione) ? linkNazione : null)
                .numBio(0)
                .numSingolari(0)
                .superaSoglia(false)
                .esisteLista(false)
                .build();

        return (NazPlurale) super.fixKey(newEntityBean);
    }


    @Override
    public NazPlurale findById(final String keyID) {
        return (NazPlurale) super.findById(keyID);
    }

    @Override
    public NazPlurale findByKey(final String keyValue) {
        return (NazPlurale) super.findByKey(keyValue);
    }

    @Override
    public NazPlurale findByOrder(final int ordine) {
        return this.findByProperty(FIELD_NAME_ORDINE, ordine);
    }

    @Override
    public NazPlurale findByProperty(final String propertyName, final Object propertyValue) {
        return (NazPlurale) super.findByProperty(propertyName, propertyValue);
    }


    @Override
    public List<NazPlurale> findAllNoSort() {
        return super.findAllNoSort();
    }

    @Override
    public List<NazPlurale> findAllSortCorrente() {
        return super.findAllSortCorrente();
    }

    @Override
    public List<NazPlurale> findAllSortCorrenteReverse() {
        return super.findAllSortCorrenteReverse();
    }

    @Override
    public List<NazPlurale> findAllSort(Sort sort) {
        return super.findAllSort(sort);
    }

    public List<NazPlurale> findAll() {
        return this.findAllNoSort();
    }

    public List<String> findAllForKeyBySingolari(final String keyValue) {
        NazPlurale naz = findByKey(keyValue);
        List<NazSingolare> listaSingolare = naz != null ? naz.listaSingolari : null;
        return listaSingolare != null ? listaSingolare.stream().map(singolo -> singolo.nome).collect(Collectors.toList()) : null;
    }

    public List<String> findSingolari(final String keyValue) {
        return findAllForKeyBySingolari(keyValue);
    }

    public Map<String, String> getMappaPluraleNazione() {
        Map<String, String> mappa = new LinkedHashMap<>();

        for (NazPlurale att : findAll()) {
            mappa.put(att.nome, att.linkNazione);
        }

        return mappa;
    }

    /**
     * Legge le mappa di valori dal modulo di wiki: <br>
     * Modulo:Bio/Link nazionalità
     */
    public WResult download() {
        WResult result = super.download();
        int tempo = WPref.downloadNazPluraleTime.getInt();

        message = String.format("Inizio %s() di %s. Tempo previsto: circa %d secondi.", METHOD_NAME_DOWLOAD, NazPlurale.class.getSimpleName(), tempo);
        logger.debug(new WrapLog().message(message));
        logger.debug(new WrapLog().message(String.format("%sModulo %s.", FORWARD, "nazionalità plurali")));

        result = downloadNazionalitaLink(result);
        result.typeResult(AETypeResult.downloadValido);

        return super.fixDownload(result, "nazionalità plurali");
    }

    /**
     * Legge le mappa dal Modulo:Bio/Link nazionalità <br>
     */
    public WResult downloadNazionalitaLink(WResult result) {
        String moduloLink = PATH_MODULO + PATH_LINK + NAZ_LOWER;
        String plurale;
        String nazione;
        NazPlurale nazPlurale;
        //        Map<String, String> mappaSingolareNazione = wikiApiService.leggeMappaModulo(moduloLink);
//        Map<String, String> mappaSingolarePlurale = nazSingolareBackend.getMappaSingolarePlurale();
        Map<String, String> mappaPluraleNazione = new LinkedHashMap<>();
        String nazSingolareNome;
        String nazPluraleNome = VUOTA;
        String paginaLista;
        String paginaNazioneOld;
        String paginaNazioneNew;
        NazSingolare nazionalitaSin;
        NazPlurale nazionalitaPlur;
        //        List lista = new ArrayList();
        List listaMancanti = new ArrayList();
        List listaDiversi = new ArrayList();

        Map<String, String> mappa = wikiApiService.leggeMappaModulo(moduloLink);

        for (NazPlurale naz : findAll()) {
            naz.linkNazione = VUOTA;
            update(naz);
        }

        if (mappa != null && mappa.size() > 0) {
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                nazSingolareNome = entry.getKey();
                paginaNazioneNew = entry.getValue();
                paginaNazioneNew = textService.primaMaiuscola(paginaNazioneNew);
                nazionalitaSin = nazSingolareBackend.findByKey(nazSingolareNome);

                if (nazionalitaSin == null) {
                    listaMancanti.add(nazSingolareNome);
                    continue;
                }

                nazPluraleNome = nazionalitaSin.plurale;
                nazionalitaPlur = findByKey(nazPluraleNome);
                paginaNazioneOld = nazionalitaPlur.linkNazione;

                if (textService.isEmpty(paginaNazioneOld)) {
                    nazionalitaPlur.linkNazione = paginaNazioneNew;
                    update(nazionalitaPlur);
                }
                else {
                    if (!paginaNazioneNew.equals(paginaNazioneOld)) {
                        listaDiversi.add(paginaNazioneNew);
                    }
                }
            }

            fixDiversi(listaDiversi);
            result.setLista(listaDiversi);
            result.setLista(listaMancanti);
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", moduloLink);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }

        //        if (mappaSingolareNazione != null && mappaSingolareNazione.size() > 0) {
        //            for (String key : mappaSingolareNazione.keySet()) {
        //                nazione = mappaSingolareNazione.get(key);
        //                if (mappaSingolarePlurale.containsKey(key)) {
        //                    plurale = mappaSingolarePlurale.get(key);
        //                    mappaPluraleNazione.put(plurale, nazione);
        //                }
        //            }
        //        }

        //        if (mappaPluraleNazione != null && mappaPluraleNazione.size() > 0) {
        //            for (String key : mappaPluraleNazione.keySet()) {
        //                nazPlurale = findByKey(key);
        //                if (nazPlurale != null) {
        //                    nazPlurale.nazione = mappaPluraleNazione.get(key);
        //                    update(nazPlurale);
        //                }
        //                else {
        //                    message = String.format("Manca la nazionalità %s", key);
        //                    System.out.println(message);
        //                }
        //            }
        //        }

        return result;
    }

    public void fixDiversi(List lista) {
    }

    /**
     * Esegue un azione di upload, specifica del programma/package in corso <br>
     */
    public WResult riordinaModulo() {
        WResult result = appContext.getBean(UploadModuloLinkNazionalita.class).uploadOrdinatoSenzaModifiche();
        return super.fixRiordinaModulo(result);
    }

    /**
     * Esegue un azione di elaborazione, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public WResult elabora() {
        WResult result = super.elabora();
        long inizio = System.currentTimeMillis();
        int tempo = 75;
        List<NazSingolare> singolari;

        message = String.format("Inizio %s() di %s. Tempo previsto: circa %d secondi.", METHOD_NAME_ELABORA, NazPlurale.class.getSimpleName(), tempo);
        logger.debug(new WrapLog().message(message));

        for (NazPlurale nazPlurale : findAll()) {
            singolari = new ArrayList<>();
            nazPlurale.numBio = bioBackend.countNazionalita(nazPlurale.nome);
            singolari.addAll(nazSingolareBackend.findAllByPlurale((nazPlurale.nome)));
            nazPlurale.listaSingolari = singolari;

            nazPlurale.numBio = bioBackend.countNazPlurale(nazPlurale.nome);
            nazPlurale.esisteLista = esistePaginaLista(nazPlurale.nome);
            nazPlurale.numSingolari = singolari.size();

            update(nazPlurale);
        }

        return super.fixElabora(result, inizio, "nazionalità");
    }


    /**
     * Controlla l'esistenza della pagina wiki relativa a questa nazionalità (lista) <br>
     */
    public boolean esistePaginaLista(String plurale) {
        String wikiTitle = LINK_PAGINA_NAZIONALITA + textService.primaMaiuscola(plurale);
        return appContext.getBean(QueryExist.class).isEsiste(wikiTitle);
    }

    /**
     * Creazione di alcuni dati <br>
     * Esegue SOLO se la collection NON esiste oppure esiste ma è VUOTA <br>
     * Viene invocato alla creazione del programma <br>
     * I dati possono essere presi da una Enumeration, da un file CSV locale, da un file CSV remoto o creati hardcoded <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public AResult resetOnlyEmpty(boolean logInfo) {
        AResult result = super.resetOnlyEmpty(false);
        String clazzName = entityClazz.getSimpleName();
        String collectionName = result.getTarget();
        List<NazSingolare> listaSingolari = nazSingolareBackend.findAll();
        List<String> listaPlurali;
        AEntity entityBean;
        List<AEntity> lista;

        if (nazSingolareBackend.count() < 1) {
            AResult resultSingolare = nazSingolareBackend.resetOnlyEmpty(logInfo);
            if (resultSingolare.isErrato()) {
                logger.error(new WrapLog().exception(new AlgosException("Manca la collezione 'nazSingolare'")).usaDb());
                return result.fine();
            }
        }

        if (result.getTypeResult() == AETypeResult.collectionVuota && listaSingolari != null && listaSingolari.size() > 0) {
            result.setValido(true);
            lista = new ArrayList<>();
            listaPlurali = new ArrayList<>();
            for (NazSingolare nazSingola : listaSingolari) {
                if (!listaPlurali.contains(nazSingola.plurale)) {
                    listaPlurali.add(nazSingola.plurale);
                }
            }
            if (listaPlurali.size() > 0) {
                for (String nome : listaPlurali) {
                    entityBean = insert(newEntity(nome));
                    if (entityBean != null) {
                        lista.add(entityBean);
                    }
                    else {
                        logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", nome))));
                        result.setValido(false);
                    }
                }
                result.setIntValue(lista.size());
                result.setLista(lista);
            }
        }
        else {
            return result;
        }

        result = super.fixResult(result, clazzName, collectionName, lista, logInfo);
        result = this.elabora();
        return result;
    }

}// end of crud backend class
