package it.algos.wiki24.backend.upload.liste;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Sat, 30-Jul-2022
 * Time: 07:39
 * <p>
 * Superclasse astratta per le classi UploadGiorni e UploadAnni <br>
 */
public abstract class UploadGiorniAnni extends UploadListe {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public MeseBackend meseBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public SecoloBackend secoloBackend;

    protected int ordineGiornoAnno;

    protected GiornoWiki giorno;

    protected AnnoWiki anno;

    protected boolean usaSottoGiorniAnni;

    public UploadGiorniAnni(String nomeLista) {
        super(nomeLista);
    }// end of constructor

    //    public UploadGiorniAnni() {
//        super.typeToc = AETypeToc.noToc;
//        this.uploadTest = false;
//        super.usaParagrafi = true;
//    }// end of constructor

//    /**
//     * Costruttore base con parametri <br>
//     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
//     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
//     */
//    public UploadGiorniAnni(String nomeLista) {
//        super.typeToc = AETypeToc.noToc;
//        this.uploadTest = false;
//        super.usaParagrafi = true;
//    }// end of constructor


    public UploadGiorniAnni nascita() {
        return this;
    }

    public UploadGiorniAnni morte() {
        return this;
    }

    public UploadGiorniAnni conParagrafi() {
        this.usaParagrafi = true;
        return this;
    }

    public UploadGiorniAnni senzaParagrafi() {
        this.usaParagrafi = false;
        return this;
    }

    public UploadGiorniAnni test() {
        this.uploadTest = true;
        return this;
    }
    protected void fixPreferenze() {
        super.fixPreferenze();

        this.usaSottoGiorniAnni = false;
    }


    /**
     * Esegue la scrittura della pagina <br>
     */
    public WResult upload(final String nomeGiornoAnno) {
        this.nomeLista = nomeGiornoAnno;
        int numVoci;
        int numBio;
        int sogliaIncludeAll = WPref.sogliaIncludeAll.getInt();
        int sogliaMaxPagina = WPref.maxBioPageAnniGiorni.getInt();

        if (textService.isValid(nomeGiornoAnno)) {
            wikiTitleUpload = switch (typeLista) {
                case giornoNascita -> wikiUtility.wikiTitleNatiGiorno(nomeGiornoAnno);
                case giornoMorte -> wikiUtility.wikiTitleMortiGiorno(nomeGiornoAnno);
                case annoNascita -> wikiUtility.wikiTitleNatiAnno(nomeGiornoAnno);
                case annoMorte -> wikiUtility.wikiTitleMortiAnno(nomeGiornoAnno);
                default -> VUOTA;
            };

            switch (typeLista) {
                case giornoNascita, giornoMorte -> {
                    giorno = giornoWikiBackend.findByKey(nomeLista);
                    this.ordineGiornoAnno = giorno != null ? giorno.getOrdine() : 0;
                }
                case annoNascita, annoMorte -> {
                    anno = annoWikiBackend.findByKey(nomeLista);
                    this.ordineGiornoAnno = anno != null ? anno.getOrdine() : 0;
                }
                default -> {}
            }
            mappaWrap = switch (typeLista) {
//                case giornoNascita -> appContext.getBean(ListaGiorni.class).nascita(nomeLista).mappaWrap();
//                case giornoMorte -> appContext.getBean(ListaGiorni.class).morte(nomeLista).mappaWrap();
//                case annoNascita -> appContext.getBean(ListaAnni.class).nascita(nomeLista).mappaWrap();
//                case annoMorte -> appContext.getBean(ListaAnni.class).morte(nomeLista).mappaWrap();
                default -> null;
            };

            //--controllo delle dimensioni in byte della pagina wiki
            usaSottoGiorniAnni = false;
//            if (WPref.usaSottoGiorniAnni.is()) {
                numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
                if (numVoci > sogliaIncludeAll) {
                    numBio = wikiUtility.getSizeAllWrap(mappaWrap);
                    if (numBio > sogliaMaxPagina) {
                        usaSottoGiorniAnni = true;
                    }
                }
//            }

            if (uploadTest) {
                this.wikiTitleUpload = UPLOAD_TITLE_DEBUG + wikiTitleUpload;
            }

            if (textService.isValid(wikiTitleUpload) && mappaWrap != null && mappaWrap.size() > 0) {
                return this.esegueUpload(wikiTitleUpload, mappaWrap);
            }
        }

        return WResult.crea();
    }


    protected WResult esegueUpload(String wikiTitle, LinkedHashMap<String, List<WrapLista>> mappa) {
        StringBuffer buffer = new StringBuffer();
        int numVoci = wikiUtility.getSizeAllWrap(mappa);

        if (numVoci < 1) {
            logger.info(new WrapLog().message(String.format("Non creata la pagina %s perché non ci sono voci", wikiTitle, numVoci)));
            return WResult.crea();
        }

        buffer.append(avviso());
        buffer.append(CAPO);
        buffer.append(includeIni());
        buffer.append(fixToc());
        buffer.append(fixUnConnected());
        buffer.append(fixUnEditableSection());
        buffer.append(torna());
        buffer.append(tmpListaBio(numVoci));
        buffer.append(includeEnd());
        buffer.append(CAPO);
        buffer.append(tmpListaPersoneIni(numVoci));
        buffer.append(testoBody(mappa));
        buffer.append(uploadTest ? VUOTA : DOPPIE_GRAFFE_END);
        buffer.append(includeIni());
        buffer.append(portale());
        buffer.append(categorie());
        buffer.append(includeEnd());

        return registra(wikiTitle, buffer.toString().trim());
    }


    protected WResult esegueUploadSotto(String wikiTitle, String attNazPrincipale, String attNazSottoPagina, int ordineSottoPagina, List<WrapLista> lista) {
        StringBuffer buffer = new StringBuffer();
        int numVoci = lista.size();

        if (numVoci < 1) {
            logger.info(new WrapLog().message(String.format("Non creata la pagina %s perché non ci sono voci", wikiTitle, numVoci)));
            return WResult.crea();
        }

        switch (typeLista) {
            case giornoNascita, giornoMorte -> {
                giorno = giornoWikiBackend.findByKey(nomeLista);
                this.ordineGiornoAnno = giorno != null ? giorno.getOrdine() : 0;
            }
            case annoNascita, annoMorte -> {
                anno = annoWikiBackend.findByKey(nomeLista);
                this.ordineGiornoAnno = anno != null ? anno.getOrdine() + ordineSottoPagina : 0;
            }
            default -> {}
        }

        buffer.append(avviso());
        buffer.append(CAPO);
        buffer.append(fixToc());
        buffer.append(fixUnConnected());
        buffer.append(fixUnEditableSection());
        buffer.append(tornaSotto());
        buffer.append(tmpListaBio(numVoci));
        buffer.append(CAPO);
        buffer.append(tmpListaPersoneIni(numVoci));
        buffer.append(senzaParagrafiNonRaggruppate(lista));
        buffer.append(uploadTest ? VUOTA : DOPPIE_GRAFFE_END);
        buffer.append(portale());
        buffer.append(categorieSotto());

        return registra(wikiTitle, buffer.toString().trim());
    }


    protected String torna() {
        return textService.isValid(nomeLista) ? String.format("{{Torna a|%s}}", nomeLista) : VUOTA;
    }

    protected String tornaSotto() {
        String torna = wikiUtility.wikiTitle(typeLista, nomeLista);
        return textService.isValid(torna) ? String.format("{{Torna a|%s}}", torna) : VUOTA;
    }

    protected String tmpListaPersoneIni(int numVoci) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(String.format("{{Lista persone per %s", typeLista.getGiornoAnno()));
        buffer.append(CAPO);
        buffer.append("|titolo=");
        buffer.append(wikiTitleUpload);
        buffer.append(CAPO);
        buffer.append("|voci=");
        buffer.append(numVoci);
        buffer.append(CAPO);
        buffer.append("|testo=");
        buffer.append("<nowiki></nowiki>");
        buffer.append(CAPO);

        return uploadTest ? VUOTA : buffer.toString();
    }


    /**
     * I giorni superano sempre le 200 voci (salvo il 29 febbraio) <br>
     * Gli anni:
     * 1) fino a 50 voci SENZA paragrafi
     * 2) da 50 a 200 voci con paragrafi ridotti
     * 3) sopra le 200 voci con paragrafi normali
     */
    public String testoBody(Map<String, List<WrapLista>> mappa) {
        int numVoci = wikiUtility.getSizeAllWrap(mappaWrap);
        int sogliaIncludeAll = WPref.sogliaIncludeAll.getInt();
        int sogliaIncludeParagrafo = WPref.sogliaIncludeParagrafo.getInt();
        boolean righeRaggruppate;

        righeRaggruppate = switch (typeLista) {
            case giornoNascita, giornoMorte -> WPref.usaRigheGiorni.is();
            case annoNascita, annoMorte -> WPref.usaRigheAnni.is();
            default -> false;
        };

        //--meno di 50 (secondo il flag)
        if (numVoci < sogliaIncludeParagrafo) {
            if (righeRaggruppate) {
                return senzaParagrafiMaRaggruppate(mappa);
            }
            else {
                return senzaParagrafiNonRaggruppate(mappa);
            }
        }

        //--da 50 a 200 (secondo il flag)
        if (numVoci < sogliaIncludeAll) {
            return conParagrafiSenzaSottopagine(mappa, true);
        }
        else {
            if (usaSottoGiorniAnni) {
                return conParagrafiConSottopagine(mappa);
            }
            else {
                return conParagrafiSenzaSottopagine(mappa, false);
            }
        }
    }


    public String senzaParagrafiNonRaggruppate(Map<String, List<WrapLista>> mappa) {
        StringBuffer buffer = new StringBuffer();
        List<WrapLista> lista;

        buffer.append("{{Div col}}" + CAPO);
        for (String keyParagrafo : mappa.keySet()) {
            lista = mappa.get(keyParagrafo);
            for (WrapLista wrap : lista) {
                buffer.append(ASTERISCO);

                if (textService.isValid(wrap.titoloSottoParagrafo)) {
                    buffer.append(wrap.titoloSottoParagrafo);
                    buffer.append(SEP);
                }
                buffer.append(wrap.didascaliaBreve);
                buffer.append(CAPO);
            }
        }

        buffer.append("{{Div col end}}" + CAPO);
        return buffer.toString().trim();
    }


    public String senzaParagrafiNonRaggruppate(List<WrapLista> lista) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("{{Div col}}" + CAPO);
        for (WrapLista wrap : lista) {
            buffer.append(ASTERISCO);

            if (textService.isValid(wrap.titoloSottoParagrafo)) {
                buffer.append(wrap.titoloSottoParagrafo);
                buffer.append(SEP);
            }
            buffer.append(wrap.didascaliaBreve);
            buffer.append(CAPO);
        }

        buffer.append("{{Div col end}}" + CAPO);
        return buffer.toString().trim();
    }

    public String senzaParagrafiMaRaggruppate(Map<String, List<WrapLista>> mappa) {
        StringBuffer buffer = new StringBuffer();
        List<WrapLista> lista;
        List<WrapLista> listaSub;
        LinkedHashMap<String, List<WrapLista>> mappaWrapSub;
        int numRighe;

        buffer.append("{{Div col}}" + CAPO);
        for (String keyParagrafo : mappa.keySet()) {
            lista = mappa.get(keyParagrafo);
            mappaWrapSub = creaSubMappa(lista);

            for (String inizioRiga : mappaWrapSub.keySet()) {
                listaSub = mappaWrapSub.get(inizioRiga);

                if (listaSub.size() == 1) {
                    buffer.append(ASTERISCO);
                    buffer.append(listaSub.get(0).didascalia);
                    buffer.append(CAPO);
                }
                else {
                    if (textService.isValid(inizioRiga)) {
                        buffer.append(ASTERISCO);
                        buffer.append(inizioRiga);
                        buffer.append(CAPO);
                        for (WrapLista wrapSub : listaSub) {
                            buffer.append(ASTERISCO);
                            buffer.append(ASTERISCO);
                            buffer.append(wrapSub.didascaliaBreve);
                            buffer.append(CAPO);
                        }
                    }
                    else {
                        for (WrapLista wrapSub : listaSub) {
                            buffer.append(ASTERISCO);
                            buffer.append(wrapSub.didascaliaBreve);
                            buffer.append(CAPO);
                        }
                    }
                }
            }
        }
        buffer.append("{{Div col end}}" + CAPO);

        return buffer.toString().trim();
    }


    public String conParagrafiConSottopagine(Map<String, List<WrapLista>> mappa) {
        StringBuffer buffer = new StringBuffer();
        List<WrapLista> lista;
        int numVoci;
        int maxDiv = WPref.sogliaDiv.getInt();
        int sogliaSottoPaginaGiorniAnni = WPref.sogliaSottoPaginaGiorniAnni.getInt();
        boolean usaDiv = true;
        String titoloParagrafoLink;
        String vedi;
        String sottoPagina;
        int ordineSottoPagina = 0;

        for (String keyParagrafo : mappa.keySet()) {
            lista = mappa.get(keyParagrafo);
            numVoci = lista.size();
            titoloParagrafoLink = lista.get(0).titoloParagrafoLink;
            buffer.append(fixTitolo(titoloParagrafoLink, numVoci, false));

            if (numVoci > sogliaSottoPaginaGiorniAnni) {
                ordineSottoPagina = fixOrdineSottoPagina(keyParagrafo);
                sottoPagina = String.format("%s%s%s", wikiTitleUpload, SLASH, keyParagrafo);

                vedi = String.format("{{Vedi anche|%s}}", sottoPagina);
                buffer.append(vedi + CAPO);
                uploadSottoPagine(sottoPagina, nomeLista, keyParagrafo, ordineSottoPagina, lista);
            }
            else {
                buffer.append(usaDiv ? "{{Div col}}" + CAPO : VUOTA);
                for (WrapLista wrap : lista) {
                    buffer.append(ASTERISCO);
                    if (textService.isValid(wrap.titoloSottoParagrafo)) {
                        buffer.append(wrap.titoloSottoParagrafo);
                        buffer.append(SEP);
                    }
                    buffer.append(wrap.didascaliaBreve);
                    buffer.append(CAPO);
                }
                buffer.append(usaDiv ? "{{Div col end}}" + CAPO : VUOTA);
            }
        }

        return buffer.toString().trim();
    }


    public String conParagrafiSenzaSottopagine(Map<String, List<WrapLista>> mappa, boolean includeOnly) {
        StringBuffer buffer = new StringBuffer();
        List<WrapLista> lista;
        int numVoci;
        int maxDiv = WPref.sogliaDiv.getInt();
        boolean usaDiv;
        String titoloParagrafoLink;

        for (String keyParagrafo : mappa.keySet()) {
            lista = mappa.get(keyParagrafo);
            numVoci = lista.size();
            usaDiv = lista.size() > maxDiv;
            titoloParagrafoLink = lista.get(0).titoloParagrafoLink;
            buffer.append(fixTitolo(titoloParagrafoLink, numVoci, includeOnly));
            buffer.append(usaDiv ? "{{Div col}}" + CAPO : VUOTA);
            for (WrapLista wrap : lista) {
                buffer.append(ASTERISCO);
                if (textService.isValid(wrap.titoloSottoParagrafo)) {
                    buffer.append(wrap.titoloSottoParagrafo);
                    buffer.append(SEP);
                }
                buffer.append(wrap.didascaliaBreve);
                buffer.append(CAPO);
            }
            buffer.append(usaDiv ? "{{Div col end}}" + CAPO : VUOTA);
        }

        return buffer.toString().trim();
    }

    protected String fixTitolo(String titoloParagrafoLink, int numVoci, boolean includeOnly) {
        String titolo = VUOTA;
        String tag = "<includeonly>=</includeonly>";
        String tagIniOld = "<span style";
        String tagIniNew = "<noinclude>" + tagIniOld;
        String tagEndOld = "</span>";
        String tagEndNew = tagEndOld + "</noinclude>";

        titolo = wikiUtility.fixTitolo(VUOTA, titoloParagrafoLink, numVoci);

        if (includeOnly) {
            titolo = textService.sostituisce(titolo, tagIniOld, tagIniNew);
            titolo = textService.sostituisce(titolo, tagEndOld, tagEndNew);
            titolo = titolo.trim();
            titolo = CAPO + tag + titolo + tag + CAPO;
        }

        return titolo;
    }

    protected int fixOrdineSottoPagina(String titoloParagrafoLink) {
        Mese mese = meseBackend.findByKey(titoloParagrafoLink.toLowerCase());
        int ordine = mese != null ? mese.ordine : 0;

        if (ordine == 0) {
            if (titoloParagrafoLink.equals(TAG_LISTA_NO_GIORNO) || titoloParagrafoLink.equals(TAG_LISTA_NO_ANNO)) {
                ordine = 13;
            }
        }

        return ordine;
    }

    protected LinkedHashMap<String, List<WrapLista>> creaSubMappa(List<WrapLista> listaWrapSub) {
        LinkedHashMap<String, List<WrapLista>> mappaWrapSub = new LinkedHashMap<>();
        String inizioRiga;
        List<WrapLista> listaSub;

        if (listaWrapSub != null && listaWrapSub.size() > 0) {
            for (WrapLista wrap : listaWrapSub) {
                inizioRiga = wrap.titoloSottoParagrafo;

                if (mappaWrapSub.containsKey(inizioRiga)) {
                    listaSub = mappaWrapSub.get(inizioRiga);
                }
                else {
                    listaSub = new ArrayList();
                }
                listaSub.add(wrap);
                mappaWrapSub.put(inizioRiga, listaSub);
            }
        }

        return mappaWrapSub;
    }


    /**
     * Esegue la scrittura della sottopagina <br>
     */
    public WResult uploadSottoPagina(final String wikiTitle, String parente, String sottoPagina, int ordineSottoPagina, List<WrapLista> lista) {
        this.wikiTitleUpload = wikiTitle;
        this.nomeLista = parente;
        this.nomeSottoPagina = sottoPagina;

        if (textService.isValid(this.wikiTitleUpload) && lista != null) {
            this.esegueUploadSotto(this.wikiTitleUpload, parente, nomeSottoPagina, ordineSottoPagina, lista);
        }

        return WResult.crea();
    }

    protected String categorie() {
        return VUOTA;
    }

    protected String categorieSotto() {
        return VUOTA;
    }


}
