package it.algos.wiki24.backend.upload.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.wrapper.*;
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

        super.usaSottoPagina = WPref.usaSottoPaginaGiorni.is();
        super.wikiBackend = giornoWikiBackend;
        super.summary = "[[Utente:Biobot/giorniBio|giorniBio]]";
        super.lastUpload = WPref.uploadGiorni;
        super.durataUpload = WPref.uploadGiorniTime;
        super.nextUpload = WPref.uploadGiorniPrevisto;
        super.usaParagrafi = WPref.usaParagrafiGiorni.is();
        super.typeToc = (AETypeToc) WPref.typeTocGiorni.getEnumCurrentObj();
        super.unitaMisuraUpload = AETypeTime.secondi;
        super.usaDiv = true;
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

    /**
     * Pattern Builder <br>
     */
    public UploadGiorni sottoPagina(String keyParagrafo) {
        this.wikiTitleUpload = nomeLista;
        this.keyParagrafoSottopagina = keyParagrafo;
        mappaWrap = appContext.getBean(ListaGiorni.class, nomeLista).typeLista(typeLista).mappaWrap();
        List<WrapLista> lista = mappaWrap.get(keyParagrafo);
        mappaWrap = wikiUtility.creaMappaSottopagina(lista, WPref.usaParagrafiGiorniSotto.is());
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


    protected String torna() {
        String localWikiTitle = wikiTitleUpload;
        String text = VUOTA;

        if (isSottopagina) {
            localWikiTitle = textService.levaCodaDaUltimo(localWikiTitle, SLASH);
            text = textService.isValid(localWikiTitle) ? String.format("{{Torna a|%s}}", localWikiTitle) : VUOTA;
        }
        else {
            text = switch (typeLista) {
                case giornoNascita, giornoMorte -> String.format("{{Torna a|%s}}", nomeLista);
                default -> text;
            };
        }

        return text;
    }


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

        buffer.append("{{Lista persone per giorno");
        buffer.append(CAPO);
        buffer.append("|titolo=");
        buffer.append(switch (typeLista) {
            case giornoNascita -> wikiUtility.wikiTitleNatiGiorno(nomeLista);
            case giornoMorte -> wikiUtility.wikiTitleMortiGiorno(nomeLista);
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


    protected String categorie() {
        StringBuffer buffer = new StringBuffer();
        int posCat;
        String nomeGiorno;
        String nomeCat;
        if (isSottopagina) {
            nomeGiorno = textService.levaCodaDaPrimo(nomeLista, SLASH);
            nomeCat = textService.levaCodaDaUltimo(wikiTitleUpload, SLASH);
        }
        else {
            nomeGiorno = nomeLista;
            nomeCat = wikiTitleUpload;
        }
        posCat = giornoWikiBackend.findByKey(nomeGiorno).getOrdine();

        buffer.append(CAPO);
        buffer.append("*");
        if (uploadTest) {
            buffer.append(NO_WIKI_INI);
        }
        buffer.append("[[Categoria:");
        buffer.append(typeLista.getCategoria());
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

    protected WResult vediSottoPagina(String sottoPagina, List<WrapLista> lista) {
        return appContext.getBean(UploadGiorni.class, sottoPagina).typeLista(typeLista).test(uploadTest).sottoPagina(lista).upload();
    }

    @Override
    protected WResult creaSottoPagina(String keyParagrafo, List<WrapLista> lista) {
        String sottoNomeLista = nomeLista + SLASH + textService.primaMaiuscola(keyParagrafo);
//        int ordineCategoriaSottopagina = AEMese.getOrder(keyParagrafo);
//        if (ordineCategoriaSottopagina == 0 && keyParagrafo.equals(TAG_LISTA_NO_GIORNO)) {
//            ordineCategoriaSottopagina = 13;
//        }

        return appContext.getBean(UploadGiorni.class, sottoNomeLista)
                .typeLista(typeLista)
                .test(uploadTest)
                .sottoPagina(lista)
//                .ordineCategoriaSottopagina(ordineCategoriaSottopagina)
                .upload();
    }

}
