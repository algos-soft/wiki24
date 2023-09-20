package it.algos.wiki24.backend.upload.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.anno.*;
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

        super.usaSottoPagina = WPref.usaSottoPaginaAnni.is();
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

    /**
     * Pattern Builder <br>
     */
    public UploadAnni sottoPagina(String keyParagrafo) {
        this.wikiTitleUpload = nomeLista;
        this.keyParagrafoSottopagina = keyParagrafo;
        mappaWrap = appContext.getBean(ListaAnni.class, nomeLista).typeLista(typeLista).mappaWrap();
        List<WrapLista> lista = mappaWrap.get(keyParagrafo);
        mappaWrap = wikiUtility.creaMappaSottopagina(lista, WPref.usaParagrafiAnniSotto.is());
        this.costruttoreValido = true;
        this.isSottopagina = true;
        return this;
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


    @Override
    protected String torna() {
        String localWikiTitle = wikiTitleUpload;
        String text = VUOTA;

        if (isSottopagina) {
            localWikiTitle = textService.levaCodaDaUltimo(localWikiTitle, SLASH);
            text = textService.isValid(localWikiTitle) ? String.format("{{Torna a|%s}}", localWikiTitle) : VUOTA;
        }
        else {
            text = switch (typeLista) {
                case annoNascita, annoMorte -> String.format("{{Torna a|%s}}", nomeLista);
                default -> text;
            };
        }

        return text;
    }


    @Override
    protected String creaBodyLayer() {
        StringBuffer buffer = new StringBuffer();

        if (isSottopagina) {
            buffer.append(creaBody());
        }
        else {
            buffer.append(getListaIni());
            buffer.append(creaBody());
            buffer.append(DOPPIE_GRAFFE_END);
        }

        this.bodyText = buffer.toString();
        return bodyText;
    }


    protected String getListaIni() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("{{Lista persone per anno");
        buffer.append(CAPO);
        buffer.append("|titolo=");
        buffer.append(switch (typeLista) {
            case annoNascita -> wikiUtility.wikiTitleNatiAnno(nomeLista);
            case annoMorte -> wikiUtility.wikiTitleMortiAnno(nomeLista);
            default -> VUOTA;
        });
        buffer.append(CAPO);
        buffer.append("|voci=");
        buffer.append(mappaWrap != null ? wikiUtility.getSizeAllWrap(mappaWrap) : VUOTA);
        buffer.append(CAPO);
        buffer.append("|testo=<nowiki></nowiki>");
        buffer.append(CAPO);

        return buffer.toString();
    }


    @Override
    protected String categorie() {
        StringBuffer buffer = new StringBuffer();
        String nomeAnno = isSottopagina ? textService.levaCodaDaUltimo(nomeLista, SLASH) : nomeLista;
        AnnoWiki anno = annoWikiBackend.findByKey(nomeAnno);
        int posCat = anno.getOrdine();
        String secolo = anno.getSecolo().getNome();
        String nomeCat;

        if (isSottopagina) {
            nomeCat = textService.levaCodaDaUltimo(wikiTitleUpload, SLASH);
            posCat += ordineCategoriaSottopagina;
        }
        else {
            nomeCat = wikiTitleUpload;
        }

        buffer.append(CAPO);
        buffer.append("*");
        if (uploadTest) {
            buffer.append(NO_WIKI_INI);
        }
        buffer.append("[[Categoria:");
        buffer.append(typeLista.getCategoria());
        buffer.append(secolo);
        buffer.append("|");
        buffer.append(SPAZIO);
        buffer.append(posCat);
        buffer.append("]]");
        if (uploadTest) {
            buffer.append(NO_WIKI_END);
        }

        buffer.append(CAPO);
        buffer.append("*");
        if (uploadTest) {
            buffer.append(NO_WIKI_INI);
        }
        buffer.append("[[Categoria:");
        buffer.append(nomeCat);
        buffer.append("|");
        buffer.append(SPAZIO);
        buffer.append("]]");
        if (uploadTest) {
            buffer.append(NO_WIKI_END);
        }

        return buffer.toString();
    }

    @Override
    protected WResult creaSottoPagina(String keyParagrafo, List<WrapLista> lista) {
        String sottoNomeLista = nomeLista + SLASH + textService.primaMaiuscola(keyParagrafo);
        int ordineCategoriaSottopagina = AEMese.getOrder(keyParagrafo);
        if (ordineCategoriaSottopagina == 0 && keyParagrafo.equals(TAG_LISTA_NO_GIORNO)) {
            ordineCategoriaSottopagina = 13;
        }

        return appContext.getBean(UploadAnni.class, sottoNomeLista)
                .typeLista(typeLista)
                .test(uploadTest)
                .sottoPagina(lista)
                .ordineCategoriaSottopagina(ordineCategoriaSottopagina)
                .upload();
    }


}
