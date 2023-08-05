package it.algos.wiki24.backend.upload.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
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
 * Date: Fri, 22-Jul-2022
 * Time: 10:20
 * Classe specializzata per caricare (upload) le liste di anni (nati/morti) sul server wiki. <br>
 * Usata fondamentalmente da GiornoWikiView con appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
 * <p>
 * Necessita del login come bot <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadAnni extends UploadListe {

    @Autowired
    public SecoloBackend secoloBackend;

    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadGiorni.class, nomeLista).esegue() <br>
     * La superclasse usa il metodo @PostConstruct postConstruct() per proseguire dopo l'init del costruttore <br>
     */
    public UploadAnni(String nomeLista) {
        super(nomeLista);
    }// end of constructor

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.wikiBackend = annoWikiBackend;
        super.summary = "[[Utente:Biobot/anniBio|anniBio]]";
        super.lastUpload = WPref.uploadAnni;
        super.durataUpload = WPref.uploadAnniTime;
        super.nextUpload = WPref.uploadAnniPrevisto;
        super.usaParagrafi = WPref.usaParagrafiAnni.is();
        super.typeToc = (AETypeToc) WPref.typeTocAnni.getEnumCurrentObj();
        super.unitaMisuraUpload = AETypeTime.secondi;
        super.usaDiv = true;
        super.patternCompleto = false;
    }


    /**
     * Pattern Builder <br>
     */
    public UploadAnni typeLista(AETypeLista typeLista) {
        super.patternCompleto = false;
        return switch (typeLista) {
            case annoNascita -> nascita();
            case annoMorte -> morte();
            default -> this;
        };
    }


    /**
     * Pattern Builder <br>
     */
    public UploadAnni nascita() {
        super.wikiTitleUpload = wikiUtility.wikiTitleNatiAnno(nomeLista);
        super.patternCompleto = true;
        return (UploadAnni) super.typeLista(AETypeLista.annoNascita);
    }

    /**
     * Pattern Builder <br>
     */
    public UploadAnni morte() {
        super.wikiTitleUpload = wikiUtility.wikiTitleMortiAnno(nomeLista);
        super.patternCompleto = true;
        return (UploadAnni) super.typeLista(AETypeLista.annoMorte);
    }

    /**
     * Pattern Builder <br>
     */
    @Override
    public UploadAnni test() {
        return (UploadAnni) super.test();
    }


    @Override
    public boolean fixMappaWrap() {
        if (!patternCompleto) {
            return false;
        }

        if (!isSottopagina) {
            if (mappaWrap == null) {
                mappaWrap = appContext
                        .getBean(ListaAnni.class, nomeLista)
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
        return appContext.getBean(UploadAnni.class, sottoPagina).typeLista(typeLista).test(uploadTest).sottoPagina(lista).upload();
    }

//    /**
//     * Esegue la scrittura della pagina <br>
//     */
//    @Override
//    public WResult upload(String nomeAnno) {
//        anno = annoWikiBackend.findByKey(nomeAnno);
//        return super.upload(nomeAnno);
//    }

//    public void uploadSottoPagine(String wikiTitle, String parente, String sottoPagina, int ordineSottoPagina, List<WrapLista> lista) {
//        UploadAnni anno = appContext.getBean(UploadAnni.class).typeLista(typeLista);
//
//        if (uploadTest) {
//            anno = anno.test();
//        }
//
//        anno.uploadSottoPagina(wikiTitle, parente, sottoPagina, ordineSottoPagina, lista);
//    }

//    /**
//     * Esegue la scrittura di tutte le pagine <br>
//     * Tutti gli anni nati <br>
//     * Tutti gli anni morti <br>
//     */
//    public WResult uploadAll() {
//        WResult result = WResult.errato();
//        logger.info(new WrapLog().type(AETypeLog.upload).message("Inizio upload liste nati e morti degli anni"));
//        long inizio = System.currentTimeMillis();
//        List<String> anni;
//        String message;
//        int modificatiNati;
//        int modificatiMorti;
//
//        List<Secolo> secoli = secoloBackend.findAllSortCorrente();
//        for (Secolo secolo : secoli) {
//            anni = annoBackend.findAllForNomeBySecolo(secolo);
//            modificatiNati = 0;
//            modificatiMorti = 0;
//
//            for (String nomeAnno : anni) {
//                result = nascita().upload(nomeAnno);
//                if (result.isValido() && result.isModificata()) {
//                    modificatiNati++;
//                }
//
//                result = morte().upload(nomeAnno);
//                if (result.isValido() && result.isModificata()) {
//                    modificatiMorti++;
//                }
//            }
//            message = String.format("Modificate sul server %d pagine di 'nati' e %d di 'morti' per il secolo %s", modificatiNati, modificatiMorti, secolo);
//            logger.info(new WrapLog().type(AETypeLog.upload).message(message));
//        }
//
//        fixUploadMinuti(inizio);
//        return result;
//    }

//    @Override
//    protected String categorie() {
//        StringBuffer buffer = new StringBuffer();
//        String message;
//        String title = wikiUtility.wikiTitle(typeLista, nomeLista);
//        Secolo secolo = anno.secolo;
//        String secoloTxt = secolo != null ? secolo.nome : VUOTA;
//
//        if (uploadTest) {
//            buffer.append(CAPO);
//            if (WPref.sottoCategorieNatiPerAnno.is()) {
//                message = String.format("{{Categorie bozza|[[Categoria:Liste di %s nel %s| %s]][[Categoria:%s| ]]}}", typeLista.getTagLower(), secoloTxt, ordineGiornoAnno, title);
//            }
//            else {
//                message = String.format("{{Categorie bozza|[[Categoria:Liste di %s per %s| %s]][[Categoria:%s| ]]}}", typeLista.getTagLower(), typeLista.getGiornoAnno(), ordineGiornoAnno, title);
//            }
//            buffer.append(message);
//        }
//        else {
//            if (WPref.sottoCategorieNatiPerAnno.is()) {
//                buffer.append(CAPO);
//                buffer.append(String.format("*[[Categoria:Liste di %s nel %s| %s]]", typeLista.getTagLower(), secoloTxt, ordineGiornoAnno));
//                buffer.append(CAPO);
//                buffer.append(String.format("*[[Categoria:%s| ]]", title));
//            }
//            else {
//                buffer.append(CAPO);
//                buffer.append(String.format("*[[Categoria:Liste di %s per %s| %s]]", typeLista.getTagLower(), typeLista.getGiornoAnno(), ordineGiornoAnno));
//                buffer.append(CAPO);
//                buffer.append(String.format("*[[Categoria:%s| ]]", title));
//            }
//        }
//
//        return buffer.toString();
//    }

//    @Override
//    protected String categorieSotto() {
//        StringBuffer buffer = new StringBuffer();
//        Secolo secolo = anno.secolo;
//        String secoloTxt = secolo != null ? secolo.nome : VUOTA;
//        String message;
//
//        if (uploadTest) {
//            if (WPref.sottoCategorieNatiPerAnno.is()) {
//                message = String.format("{{Categorie bozza|[[Categoria:Liste di %s nel %s| %s]]}}", typeLista.getTagLower(), secoloTxt, ordineGiornoAnno);
//            }
//            else {
//                message = String.format("{{Categorie bozza|[[Categoria:Liste di %s per %s| %s]]}}", typeLista.getTagLower(), typeLista.getGiornoAnno(), ordineGiornoAnno);
//            }
//            buffer.append(message);
//        }
//
//        if (WPref.sottoCategorieNatiPerAnno.is()) {
//            buffer.append(CAPO);
//            buffer.append(String.format("*[[Categoria:Liste di %s nel %s| %s]]", typeLista.getTagLower(), secoloTxt, ordineGiornoAnno));
//            buffer.append(CAPO);
//        }
//        else {
//            buffer.append(CAPO);
//            buffer.append(String.format("*[[Categoria:Liste di %s per %s| %s]]", typeLista.getTagLower(), typeLista.getGiornoAnno(), ordineGiornoAnno));
//            buffer.append(CAPO);
//        }
//
//        return buffer.toString();
//    }

}
