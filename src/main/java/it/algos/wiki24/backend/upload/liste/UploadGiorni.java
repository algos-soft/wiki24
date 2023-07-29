package it.algos.wiki24.backend.upload.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Tue, 26-Jul-2022
 * Time: 08:47
 * Classe specializzata per caricare (upload) le liste di giorni (nati/morti) sul server wiki. <br>
 * Usata fondamentalmente da GiornoWikiView con appContext.getBean(UploadGiorni.class).nascita/morte().upload(nomeGiorno) <br>
 * <p>
 * Necessita del login come bot <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadGiorni extends UploadListe {

    @Autowired
    public MeseBackend meseBackend;

    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadGiorni.class, nomeLista).esegue() <br>
     * La superclasse usa il metodo @PostConstruct postConstruct() per proseguire dopo l'init del costruttore <br>
     */
    public UploadGiorni(String nomeLista) {
        super(nomeLista);
    }// end of constructor

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.wikiBackend = giornoWikiBackend;
        super.summary = "[[Utente:Biobot/giorniBio|giorniBio]]";
        super.lastUpload = WPref.uploadGiorni;
        super.durataUpload = WPref.uploadGiorniTime;
        super.nextUpload = WPref.uploadGiorniPrevisto;
        super.usaParagrafi = WPref.usaParagrafiGiorni.is();
        super.typeToc = (AETypeToc) WPref.typeTocGiorni.getEnumCurrentObj();
        super.unitaMisuraUpload = AETypeTime.secondi;
        super.patternCompleto = false;
    }


    /**
     * Pattern Builder <br>
     */
    @Override
    public UploadGiorni typeLista(AETypeLista typeLista) {
        super.patternCompleto = false;
        return switch (typeLista) {
            case giornoNascita -> nascita();
            case giornoMorte -> morte();
            default -> this;
        };
    }


    /**
     * Pattern Builder <br>
     */
    public UploadGiorni nascita() {
        super.wikiTitleUpload = wikiUtility.wikiTitleNatiGiorno(nomeLista);
        super.patternCompleto = true;
        return (UploadGiorni) super.typeLista(AETypeLista.giornoNascita);
    }

    /**
     * Pattern Builder <br>
     */
    public UploadGiorni morte() {
        super.wikiTitleUpload = wikiUtility.wikiTitleMortiGiorno(nomeLista);
        super.patternCompleto = true;
        return (UploadGiorni) super.typeLista(AETypeLista.giornoMorte);
    }


    /**
     * Pattern Builder <br>
     */
    @Override
    public UploadGiorni test() {
        return (UploadGiorni) super.test();
    }


    @Override
    public boolean fixMappaWrap() {
        if (!patternCompleto) {
            return false;
        }

        if (!isSottopagina) {
            if (mappaWrap == null) {
                mappaWrap = appContext
                        .getBean(ListaGiorni.class, nomeLista)
                        .typeLista(typeLista)
                        .typeLinkParagrafi(typeLinkParagrafi)
                        .typeLinkCrono(typeLinkCrono)
                        .icona(usaIcona)
                        .mappaWrap();
            }
        }

        return true;
    }


    protected WResult vediSottoPagina(String sottoPagina, List<WrapLista> lista) {
        return appContext.getBean(UploadGiorni.class, sottoPagina).typeLista(typeLista).test(uploadTest).sottoPagina(lista).upload();
    }

    public void uploadSottoPagine(String wikiTitle, String parente, String sottoPagina, int ordineSottoPagina, List<WrapLista> lista) {
        UploadGiorni giorno = appContext.getBean(UploadGiorni.class).typeLista(typeLista);

        if (uploadTest) {
            giorno = giorno.test();
        }

        //        giorno.uploadSottoPagina(wikiTitle, parente, sottoPagina, ordineSottoPagina, lista);
    }

    /**
     * Esegue la scrittura di tutte le pagine <br>
     * Tutti i giorni nati <br>
     * Tutti i giorni morti <br>
     */
    @Override
    public WResult uploadAll() {
        WResult result = WResult.errato();
        logger.info(new WrapLog().type(AETypeLog.upload).message("Inizio upload liste nati e morti dei giorni"));
        List<Mese> mesi = null;
        List<String> giorni;
        String message;
        int modificatiNati;
        int modificatiMorti;

        if (meseBackend == null) {
            logger.error(new WrapLog().type(AETypeLog.upload).message("Manca meseBackend").usaDb());
        }

        try {
            mesi = meseBackend.findAllSortCorrente();
        } catch (Exception unErrore) {
            logService.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }

        if (mesi == null) {
            logger.error(new WrapLog().type(AETypeLog.upload).message("Mancano i mesi").usaDb());
        }
        if (mesi.size() != 12) {
            logger.error(new WrapLog().type(AETypeLog.upload).message("I mesi sono sbagliati").usaDb());
        }

        for (Mese mese : mesi) {
            giorni = giornoBackend.findAllForNomeByMese(mese);
            if (mesi == null) {
                message = String.format("Mancano i giorni del mese %s", mese);
                logger.error(new WrapLog().type(AETypeLog.upload).message(message).usaDb());
            }
            if (mesi.size() < 1) {
                message = String.format("Nel mese %s ci sono troppi pochi giorni", mese);
                logger.error(new WrapLog().type(AETypeLog.upload).message(message).usaDb());
            }
            modificatiNati = 0;
            modificatiMorti = 0;
            for (String nomeGiorno : giorni) {
                result = appContext.getBean(UploadGiorni.class, nomeGiorno).nascita().upload();

                if (result.isValido() && result.isModificata()) {
                    modificatiNati++;
                }
                else {
                    message = result.getMessage();
                    message += String.format(" della pagina %s/%s", nomeGiorno, AETypeLista.giornoNascita.getTag());
                    logger.debug(new WrapLog().type(AETypeLog.upload).message(message).usaDb());
                }

                result = appContext.getBean(UploadGiorni.class, nomeGiorno).morte().upload();
                if (result.isValido() && result.isModificata()) {
                    modificatiMorti++;
                }
                else {
                    message = result.getMessage();
                    message += String.format(" della pagina %s/%s", nomeGiorno, AETypeLista.giornoMorte.getTag());
                    logger.debug(new WrapLog().type(AETypeLog.upload).message(message).usaDb());
                }
            }

            if (Pref.debug.is()) {
                message = String.format("Modificate sul server %d pagine di 'nati' e %d di 'morti' per il mese di %s", modificatiNati, modificatiMorti, mese);
                message += String.format(" in %s", dateService.deltaText(result.getInizio()));
                logger.info(new WrapLog().type(AETypeLog.upload).message(message));
            }
        }

        return result;
    }

    //    @Override
    //    protected String categorie() {
    //        StringBuffer buffer = new StringBuffer();
    //        String message;
    //        String title = wikiUtility.wikiTitle(typeLista, nomeLista);
    //
    //        if (uploadTest) {
    //            buffer.append(CAPO);
    //            message = String.format("{{Categorie bozza|[[Categoria:Liste di %s per %s| %s]][[Categoria:%s| ]]}}", typeLista.getTagLower(), typeLista.getGiornoAnno(), ordineGiornoAnno, title);
    //            buffer.append(message);
    //        }
    //        else {
    //            buffer.append(CAPO);
    //            buffer.append(String.format("*[[Categoria:Liste di %s per %s| %s]]", typeLista.getTagLower(), typeLista.getGiornoAnno(), ordineGiornoAnno));
    //            buffer.append(CAPO);
    //            buffer.append(String.format("*[[Categoria:%s| ]]", title));
    //        }
    //
    //        return buffer.toString();
    //    }

    //    @Override
    //    protected String categorieSotto() {
    //        StringBuffer buffer = new StringBuffer();
    //
    //        if (uploadTest) {
    //            return VUOTA;
    //        }
    //
    //        buffer.append(CAPO);
    //        buffer.append(String.format("*[[Categoria:Liste di %s per %s| %s]]", typeLista.getTagLower(), typeLista.getGiornoAnno(), ordineGiornoAnno));
    //        buffer.append(CAPO);
    //
    //        return buffer.toString();
    //    }

}
