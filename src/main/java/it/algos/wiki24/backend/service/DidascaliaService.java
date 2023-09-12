package it.algos.wiki24.backend.service;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: dom, 15-ago-2021
 * Time: 08:18
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(ADidascaliaService.class); <br>
 * 3) @Autowired public DidascaliaService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DidascaliaService extends WAbstractService {

    public static String NATI = "Nati nel ";

    public static String MORTI = "Morti nel ";

    /**
     * Restituisce una lista semplice di didascalie complete <br>
     *
     * @param listaBio di biografie in ingresso
     *
     * @return lista di didascalie completa
     */
    public List<String> getLista(final List<Bio> listaBio) {
        List<String> listaDidascalie = null;
        String didascalia;

        if (listaBio != null) {
            listaDidascalie = new ArrayList<>();
            for (Bio bio : listaBio) {
                didascalia = lista(null, null);//@todo Controllare
                if (textService.isValid(didascalia)) {
                    listaDidascalie.add(didascalia);
                }
            }
        }

        return listaDidascalie;
    }


    /**
     * Costruisce il nome e cognome (obbligatori) <br>
     * Si usa il titolo della voce direttamente, se non contiene parentesi <br>
     *
     * @param bio completa
     *
     * @return nome e cognome elaborati e inseriti nelle doppie quadre
     */
    public String nomeCognome(final Bio bio) {
        return nomeCognome(bio.wikiTitle, bio.nome, bio.cognome);
    }

    /**
     * Costruisce il nome e cognome (obbligatori) <br>
     * Si usa il titolo della voce direttamente, se non contiene parentesi <br>
     *
     * @param wikiTitle della pagina sul server wiki
     * @param nome      semplice (solo primo nome esclusi i nomi doppi)
     * @param cognome   completo
     *
     * @return nome e cognome elaborati e inseriti nelle doppie quadre
     */
    public String nomeCognome(final String wikiTitle, final String nome, final String cognome) {
        String nomeCognome;
        String tagPar = PARENTESI_TONDA_INI;
        String tagPipe = PIPE;
        String nomePrimaDellaParentesi;
        boolean usaNomeCognomePerTitolo = false;

        if (usaNomeCognomePerTitolo) {
            nomeCognome = nome + SPAZIO + cognome;
            if (!nomeCognome.equals(wikiTitle)) {
                nomeCognome = wikiTitle + tagPipe + nomeCognome;
            }
        }
        else {
            // se il titolo NON contiene la parentesi, il nome non va messo perché coincide col titolo della voce
            if (wikiTitle.contains(tagPar)) {
                nomePrimaDellaParentesi = wikiTitle.substring(0, wikiTitle.indexOf(tagPar));
                nomeCognome = wikiTitle + tagPipe + nomePrimaDellaParentesi;
            }
            else {
                nomeCognome = wikiTitle;
            }
        }

        nomeCognome = nomeCognome.trim();
        nomeCognome = textService.setDoppieQuadre(nomeCognome);

        return nomeCognome;
    }

    /**
     * Costruisce attività e nazionalità (obbligatori) <br>
     *
     * @param bio completa
     *
     * @return attività e nazionalità elaborati
     */
    public String attivitaNazionalita(final Bio bio) {
        return attivitaNazionalita(bio.wikiTitle, bio.attivita, bio.attivita2, bio.attivita3, bio.nazionalita);
    }

    /**
     * Costruisce attività e nazionalità (obbligatori) <br>
     *
     * @param wikiTitle   della pagina sul server wiki
     * @param attivita    principale
     * @param attivita2   facoltativa
     * @param attivita3   facoltativa
     * @param nazionalita unica
     *
     * @return attività e nazionalità elaborati
     */
    public String attivitaNazionalita(final String wikiTitle, final String attivita, final String attivita2, final String attivita3, final String nazionalita) {
        String attivitaNazionalita = VUOTA;

        if (textService.isValid(attivita)) {
            attivitaNazionalita += attivita;
        }

        if (textService.isValid(attivita2)) {
            if (textService.isValid(attivita3)) {
                attivitaNazionalita += VIRGOLA_SPAZIO;
            }
            else {
                attivitaNazionalita += SPAZIO + "e" + SPAZIO;
            }
            attivitaNazionalita += attivita2;
        }

        if (textService.isValid(attivita3)) {
            attivitaNazionalita += SPAZIO + "e" + SPAZIO;
            attivitaNazionalita += attivita3;
        }

        if (textService.isValid(nazionalita)) {
            attivitaNazionalita += SPAZIO;
            attivitaNazionalita += nazionalita;
        }

        return attivitaNazionalita;
    }


    /**
     * Costruisce la didascalia completa per una lista di nati nel giorno <br>
     * WikiTitle (sempre)
     * AttivitàNazionalità (sempre)
     * Anno di morte (opzionale)
     *
     * @param bio completa
     *
     * @return didascalia completa
     */
    public String didascaliaGiornoNato(final Bio bio, AETypeLink typeLinkCrono, boolean usaIcona) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getWikiTitle(bio));
        if (textService.isValid(attivitaNazionalita(bio))) {
            buffer.append(VIRGOLA_SPAZIO);
            buffer.append(attivitaNazionalita(bio));
        }
        buffer.append(SPAZIO);
        buffer.append(wikiUtility.annoMortoCoda(bio, typeLinkCrono, usaIcona, true));

        return buffer.toString().trim();
    }

    /**
     * Costruisce la didascalia completa per una lista di morti nel giorno <br>
     * WikiTitle (sempre)
     * AttivitàNazionalità (sempre)
     * Anno di nascita (opzionale)
     *
     * @param bio completa
     *
     * @return didascalia completa
     */
    public String didascaliaGiornoMorto(final Bio bio, AETypeLink typeLinkCrono, boolean usaIcona) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getWikiTitle(bio));
        if (textService.isValid(attivitaNazionalita(bio))) {
            buffer.append(VIRGOLA_SPAZIO);
            buffer.append(attivitaNazionalita(bio));
        }
        buffer.append(SPAZIO);
        buffer.append(wikiUtility.annoNatoCoda(bio, typeLinkCrono, usaIcona, true));

        return buffer.toString().trim();
    }


    /**
     * Costruisce la didascalia completa per una lista di nati nell'anno <br>
     * WikiTitle (sempre)
     * AttivitàNazionalità (sempre)
     * Anno di morte (opzionale)
     *
     * @param bio completa
     *
     * @return didascalia completa
     */
    public String didascaliaAnnoNato(final Bio bio, AETypeLink typeLinkCrono, boolean usaIcona) {
        StringBuffer buffer = new StringBuffer();
        String coda;

        buffer.append(getWikiTitle(bio));
        if (textService.isValid(attivitaNazionalita(bio))) {
            buffer.append(VIRGOLA_SPAZIO);
            buffer.append(attivitaNazionalita(bio));
        }
        buffer.append(SPAZIO);
        buffer.append(wikiUtility.annoMortoCoda(bio, typeLinkCrono, usaIcona, true));

        return buffer.toString().trim();
    }

    /**
     * Costruisce la didascalia completa per una lista di morti nell'anno <br>
     *
     * @param bio completa
     *
     * @return didascalia completa
     */
    public String didascaliaAnnoMorto(final Bio bio, AETypeLink typeLinkCrono, boolean usaIcona) {
        StringBuffer buffer = new StringBuffer();
        String coda;

        buffer.append(getWikiTitle(bio));
        if (textService.isValid(attivitaNazionalita(bio))) {
            buffer.append(VIRGOLA_SPAZIO);
            buffer.append(attivitaNazionalita(bio));
        }
        buffer.append(SPAZIO);
        buffer.append(wikiUtility.annoNatoCoda(bio, typeLinkCrono, usaIcona, true));

        return buffer.toString().trim();
    }


    public String getWikiTitle(final Bio bio) {
        String wikiTitle = bio.wikiTitle;
        String nomeVisibile;

        if (textService.isValid(wikiTitle)) {
            if (wikiTitle.contains(PARENTESI_TONDA_END)) {
                nomeVisibile = textService.levaCodaDaUltimo(wikiTitle, PARENTESI_TONDA_INI);
                wikiTitle += PIPE;
                wikiTitle += nomeVisibile;
            }
            wikiTitle = textService.setDoppieQuadre(wikiTitle);
        }

        return wikiTitle;
    }


    /**
     * Costruisce una wrapLista specializzata per le righe delle pagine 'Nati nel giorno' <br>
     * Contiene il paragrafo 'secolo'
     * Contiene l'inizio riga 'anno nascita'
     * Contiene la didascalia con 'wikiTitle', 'attività/nazionalità', 'anno di morte'
     *
     * @param bio completa
     *
     * @return wrapLista
     */
    public WrapLista getWrapGiornoNato(String titoloPagina, final Bio bio, AETypeLink typeLinkParagrafi, AETypeLink typeLinkCrono, boolean usaIcona) {
        String paragrafo = wikiUtility.fixSecoloNato(bio);
        String paragrafoLink;
        String sottoParagrafo;
        String ordinamento = annoNato(bio, typeLinkCrono);

        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiGiorniAnni.getEnumCurrentObj();
        }
        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = AETypeLink.nessunLink;
        }

        paragrafoLink = switch (typeLinkParagrafi) {
            case linkVoce, linkLista -> textService.setDoppieQuadre(paragrafo);
            case nessunLink -> VUOTA;
        };

        if (textService.isEmpty(paragrafo)) {
            paragrafo = TAG_LISTA_NO_ANNO;
        }

        if (WPref.usaParagrafiGiorniSotto.is()) {
            sottoParagrafo = textService.isValid(bio.annoNato) ? ANNI + SPAZIO + wikiUtility.getDecade(bio.annoNato) : VUOTA;
        }
        else {
            sottoParagrafo = VUOTA;
        }

        return getWrap(
                titoloPagina,
                bio,
                typeLinkCrono,
                usaIcona,
                AETypeLista.giornoNascita,
                paragrafo,
                paragrafoLink,
                sottoParagrafo,
                ordinamento
        );
    }


    /**
     * Costruisce una wrapLista specializzata per le righe delle pagine 'Morti nel giorno' <br>
     * Contiene il paragrafo 'secolo'
     * Contiene l'inizio riga 'anno morte'
     * Contiene la didascalia con 'wikiTitle', 'attività/nazionalità', 'anno di nascita'
     *
     * @param bio completa
     *
     * @return wrapLista
     */
    public WrapLista getWrapGiornoMorto(String titoloPagina, final Bio bio, AETypeLink typeLinkParagrafi, AETypeLink typeLinkCrono, boolean usaIcona) {
        String paragrafo = wikiUtility.fixSecoloMorto(bio);
        String paragrafoLink;
        String sottoParagrafo;
        String ordinamento = annoMorto(bio, typeLinkCrono);

        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiGiorniAnni.getEnumCurrentObj();
        }
        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = AETypeLink.nessunLink;
        }

        paragrafoLink = switch (typeLinkParagrafi) {
            case linkVoce, linkLista -> textService.setDoppieQuadre(paragrafo);
            case nessunLink -> VUOTA;
        };

        if (textService.isEmpty(paragrafo)) {
            paragrafo = TAG_LISTA_NO_ANNO;
        }

        if (WPref.usaParagrafiGiorniSotto.is()) {
            sottoParagrafo = textService.isValid(bio.annoNato) ? ANNI + SPAZIO + wikiUtility.getDecade(bio.annoMorto) : VUOTA;
        }
        else {
            sottoParagrafo = VUOTA;
        }

        return getWrap(
                titoloPagina,
                bio,
                typeLinkCrono,
                usaIcona,
                AETypeLista.giornoMorte,
                paragrafo,
                paragrafoLink,
                sottoParagrafo,
                ordinamento
        );
    }

    /**
     * Costruisce una wrapLista specializzata per le righe delle pagine 'Nati nell'anno' <br>
     * Contiene il paragrafo 'mese'
     * Contiene l'inizio riga 'giorno nascita'
     * Contiene la didascalia con 'wikiTitle', 'attività/nazionalità', 'anno di morte'
     *
     * @param bio completa
     *
     * @return wrapLista
     */
    public WrapLista getWrapAnnoNato(String titoloPagina, final Bio bio, AETypeLink typeLinkParagrafi, AETypeLink typeLinkCrono, boolean usaIcona) {
        String paragrafo = wikiUtility.fixMeseNato(bio);
        String paragrafoLink;
        String sottoParagrafo;
        String ordinamento = giornoNato(bio, typeLinkCrono);

        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiGiorniAnni.getEnumCurrentObj();
        }
        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = AETypeLink.nessunLink;
        }

        paragrafoLink = switch (typeLinkParagrafi) {
            case linkVoce, linkLista -> textService.setDoppieQuadre(paragrafo);
            case nessunLink -> VUOTA;
        };

        if (textService.isEmpty(paragrafo)) {
            paragrafo = TAG_LISTA_NO_GIORNO;
        }

        if (WPref.usaParagrafiAnniSotto.is()) {
            sottoParagrafo = VUOTA;
        }
        else {
            sottoParagrafo = VUOTA;
        }

        return getWrap(
                titoloPagina,
                bio,
                typeLinkCrono,
                usaIcona,
                AETypeLista.annoNascita,
                paragrafo,
                paragrafoLink,
                sottoParagrafo,
                ordinamento
        );
    }

    /**
     * Costruisce una wrapLista specializzata per le righe delle pagine 'Morti nell'anno' <br>
     * Contiene il paragrafo 'mese'
     * Contiene l'inizio riga 'giorno morte'
     * Contiene la didascalia con 'wikiTitle', 'attività/nazionalità', 'anno di nascita'
     *
     * @param bio completa
     *
     * @return wrapLista
     */
    public WrapLista getWrapAnnoMorto(String titoloPagina, final Bio bio, AETypeLink typeLinkParagrafi, AETypeLink typeLinkCrono, boolean usaIcona) {
        String paragrafo = wikiUtility.fixMeseMorto(bio);
        String paragrafoLink;
        String sottoParagrafo;
        String ordinamento = giornoMorto(bio, typeLinkCrono);

        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiGiorniAnni.getEnumCurrentObj();
        }
        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = AETypeLink.nessunLink;
        }

        paragrafoLink = switch (typeLinkParagrafi) {
            case linkVoce, linkLista -> textService.setDoppieQuadre(paragrafo);
            case nessunLink -> VUOTA;
        };

        if (textService.isEmpty(paragrafo)) {
            paragrafo = TAG_LISTA_NO_GIORNO;
        }

        if (WPref.usaParagrafiAnniSotto.is()) {
            sottoParagrafo = VUOTA;
        }
        else {
            sottoParagrafo = VUOTA;
        }

        return getWrap(
                titoloPagina,
                bio,
                typeLinkCrono,
                usaIcona,
                AETypeLista.annoMorte,
                paragrafo,
                paragrafoLink,
                sottoParagrafo,
                ordinamento
        );
    }

    /**
     * Costruisce una wrapLista specializzata per le righe delle pagine 'Attività' <br>
     * Contiene il paragrafo 'nazionalità'
     * Contiene il sotto-paragrafo 'primoCarattere'
     * Contiene la didascalia con 'wikiTitle', 'attività/nazionalità', 'luogo e anno di nascita', 'luogo e anno di morte'
     *
     * @param bio completa
     *
     * @return wrapLista
     */
    public WrapLista getWrapAttivita(String titoloPagina, final Bio bio, AETypeLink typeLinkParagrafi, AETypeLink typeLinkCrono, boolean usaIcona) {
        NazSingolare nazSingolare = nazSingolareBackend.findByKey(bio.nazionalita);
        NazPlurale nazPlurale;
        String paragrafo;
        String paragrafoLink;
        String sottoParagrafo;
        String ordinamento = VUOTA;

        if (nazSingolare != null) {
            nazPlurale = nazPluraleBackend.findByKey(nazSingolare.plurale);
            if (nazPlurale != null) {
                paragrafo = textService.primaMaiuscola(nazPlurale.nome);
            }
            else {
                paragrafo = textService.primaMaiuscola(nazSingolare.nome);
                logService.warn(new WrapLog().message(String.format("Manca la nazionalità plurale di %s", nazSingolare.nome)));
            }
        }
        else {
            paragrafo = TAG_LISTA_NO_NAZIONALITA;
        }

        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = (AETypeLink) WPref.linkParametriAttNaz.getEnumCurrentObj();
        }
        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = AETypeLink.nessunLink;
        }
        paragrafoLink = switch (typeLinkParagrafi) {
            case linkVoce -> textService.setDoppieQuadre(paragrafo);
            case linkLista -> textService.setDoppieQuadre(PATH_NAZIONALITA + SLASH + paragrafo + PIPE + paragrafo);
            case nessunLink -> VUOTA;
        };

        sottoParagrafo = bio.ordinamento.substring(0, 1);

        return getWrap(
                titoloPagina,
                bio,
                typeLinkCrono,
                usaIcona,
                AETypeLista.attivitaPlurale,
                paragrafo,
                paragrafoLink,
                sottoParagrafo,
                ordinamento
        );
    }


    /**
     * Costruisce una wrapLista specializzata per le righe delle pagine 'Nazionalità' <br>
     * Contiene il paragrafo 'attivita'
     * Contiene il sotto-paragrafo 'primoCarattere'
     * Contiene la didascalia con 'wikiTitle', 'attività/nazionalità', 'luogo e anno di nascita', 'luogo e anno di morte'
     *
     * @param bio completa
     *
     * @return wrapLista
     */
    public WrapLista getWrapNazionalita(String titoloPagina, final Bio bio, AETypeLink typeLinkParagrafi, AETypeLink typeLinkCrono, boolean usaIcona) {
        //        AttSingolare attSingolare = attSingolareBackend.findByKey(bio.attivita);
        //        AttPlurale attPlurale;
        String paragrafo = genereBackend.getPluraleParagrafo(bio);
        String paragrafoLink;
        String sottoParagrafo;
        String ordinamento = VUOTA;

        //        if (attSingolare != null) {
        //            attPlurale = attPluraleBackend.findByKey(attSingolare.plurale);
        //            if (attPlurale != null) {
        //                paragrafo = textService.primaMaiuscola(attPlurale.nome);
        //            }
        //            else {
        //                paragrafo = textService.primaMaiuscola(attSingolare.nome);
        //                logService.warn(new WrapLog().message(String.format("Manca l'attività plurale di %s", attSingolare.nome)));
        //            }
        //        }
        //        else {
        //            paragrafo = TAG_LISTA_NO_ATTIVITA;
        //        }
        //
        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = (AETypeLink) WPref.linkParametriAttNaz.getEnumCurrentObj();
        }
        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = AETypeLink.nessunLink;
        }
        paragrafoLink = switch (typeLinkParagrafi) {
            case linkVoce -> textService.setDoppieQuadre(paragrafo);
            case linkLista -> textService.setDoppieQuadre(PATH_NAZIONALITA + SLASH + paragrafo + PIPE + paragrafo);
            case nessunLink -> VUOTA;
        };

        sottoParagrafo = bio.ordinamento.substring(0, 1);

        return getWrap(
                titoloPagina,
                bio,
                typeLinkCrono,
                usaIcona,
                AETypeLista.nazionalitaPlurale,
                paragrafo,
                paragrafoLink,
                sottoParagrafo,
                ordinamento
        );
    }

    public WrapLista getWrapNomi(final Bio bio, AETypeLink typeLinkParagrafi, AETypeLink typeLinkCrono, boolean usaIcona) {
        return getWrapNomi(VUOTA, bio, typeLinkParagrafi, typeLinkCrono, usaIcona);
    }

    public WrapLista getWrapNomi(String titoloPagina, final Bio bio, AETypeLink typeLinkParagrafi, AETypeLink typeLinkCrono, boolean usaIcona) {
        String paragrafo = genereBackend.getPluraleParagrafo(bio);
        String paragrafoLink;
        String sottoParagrafo;
        String ordinamento = VUOTA;
        Map<String, String> mappa;
        String cognomeGrezzo;

        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiNomi.getEnumCurrentObj();
        }
        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = AETypeLink.nessunLink;
        }

        paragrafoLink = switch (typeLinkParagrafi) {
            case linkVoce -> textService.setDoppieQuadre(paragrafo);
            case linkLista -> textService.setDoppieQuadre(PATH_ATTIVITA + SLASH + paragrafo + PIPE + paragrafo);
            case nessunLink -> VUOTA;
        };

        if (textService.isValid(bio.cognome)) {
            sottoParagrafo = bio.cognome.substring(0, 1);
        }
        else {
            mappa = bioService.estraeMappa(bio);
            cognomeGrezzo = mappa != null ? mappa.get("Cognome") : VUOTA;
            if (textService.isValid(cognomeGrezzo)) {
                sottoParagrafo = cognomeGrezzo.substring(0, 1);
            }
            else {
                sottoParagrafo = textService.isValid(bio.ordinamento) ? bio.ordinamento.substring(0, 1) : VUOTA;
            }
        }

        return getWrap(
                titoloPagina,
                bio,
                typeLinkCrono,
                usaIcona,
                AETypeLista.nomi,
                paragrafo,
                paragrafoLink,
                sottoParagrafo,
                ordinamento
        );
    }

    public WrapLista getWrapCognomi(final Bio bio, AETypeLink typeLinkParagrafi, AETypeLink typeLinkCrono, boolean usaIcona) {
        return getWrapCognomi(VUOTA, bio, typeLinkParagrafi, typeLinkCrono, usaIcona);
    }

    /**
     * Costruisce una wrapLista specializzata per le righe delle pagine 'Persone di cognome' <br>
     * Contiene il paragrafo 'attivita'
     * Contiene il sotto-paragrafo 'primoCarattere'
     * Contiene la didascalia con 'wikiTitle', 'attività/nazionalità', 'luogo e anno di nascita', 'luogo e anno di morte'
     *
     * @param bio completa
     *
     * @return wrapLista
     */
    public WrapLista getWrapCognomi(String titoloPagina, final Bio bio, AETypeLink typeLinkParagrafi, AETypeLink typeLinkCrono, boolean usaIcona) {
        String paragrafo = genereBackend.getPluraleParagrafo(bio); ;
        String paragrafoLink;
        String sottoParagrafo;
        String ordinamento = VUOTA;
        Map<String, String> mappa;
        String nomeGrezzo;

        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiCognomi.getEnumCurrentObj();
        }
        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = AETypeLink.nessunLink;
        }

        paragrafoLink = switch (typeLinkParagrafi) {
            case linkVoce -> textService.setDoppieQuadre(paragrafo);
            case linkLista -> textService.setDoppieQuadre(PATH_ATTIVITA + SLASH + paragrafo + PIPE + paragrafo);
            case nessunLink -> VUOTA;
        };

        if (textService.isValid(bio.nome)) {
            sottoParagrafo = bio.nome.substring(0, 1);
        }
        else {
            mappa = bioService.estraeMappa(bio);
            nomeGrezzo = mappa != null ? mappa.get("Nome") : VUOTA;
            if (textService.isValid(nomeGrezzo)) {
                sottoParagrafo = nomeGrezzo.substring(0, 1);
            }
            else {
                sottoParagrafo = textService.isValid(bio.ordinamento) ? bio.ordinamento.substring(0, 1) : VUOTA;
            }
        }

        return getWrap(
                titoloPagina,
                bio,
                typeLinkCrono,
                usaIcona,
                AETypeLista.cognomi,
                paragrafo,
                paragrafoLink,
                sottoParagrafo,
                ordinamento
        );
    }


    public WrapLista getWrapNomi(final Bio bio) {
        return getWrapNomi(VUOTA, bio, null, null, true);
    }

    public WrapLista getWrap(final Bio bio, final AETypeLista typeLista) {
        return getWrap(bio, typeLista, null, null);
    }

    /**
     * Costruisce una wrapLista <br>
     *
     * @param bio completa
     *
     * @return wrapLista
     */
    public WrapLista getWrap(final Bio bio, final AETypeLista typeLista, AETypeLink typeLinkParagrafi) {
        if (typeLinkParagrafi == null) {
            typeLinkParagrafi = AETypeLink.nessunLink;
        }
        return getWrap(bio, typeLista, typeLinkParagrafi, null);
    }

    public WrapLista getWrap(final Bio bio, final AETypeLista typeLista, final AETypeLink typeLinkParagrafi, AETypeLink typeLinkCrono) {
        return getWrap(bio, typeLista, typeLinkParagrafi, typeLinkCrono, true);
    }

    public WrapLista getWrap(final Bio bio, final AETypeLista typeLista, final AETypeLink typeLinkParagrafi, AETypeLink typeLinkCrono, boolean usaIcona) {
        return getWrap(VUOTA, bio, typeLista, typeLinkParagrafi, typeLinkCrono, usaIcona);
    }


    /**
     * Costruisce una wrapLista <br>
     *
     * @param bio completa
     *
     * @return wrapLista
     */
    public WrapLista getWrap(final String titoloPagina, final Bio bio, final AETypeLista typeLista, final AETypeLink typeLinkParagrafi, AETypeLink typeLinkCrono, boolean usaIcona) {
        if (typeLinkCrono == null) {
            typeLinkCrono = AETypeLink.linkLista;
        }
        return switch (typeLista) {
            case giornoNascita -> this.getWrapGiornoNato(titoloPagina, bio, typeLinkParagrafi, typeLinkCrono, usaIcona);
            case giornoMorte -> this.getWrapGiornoMorto(titoloPagina, bio, typeLinkParagrafi, typeLinkCrono, usaIcona);
            case annoNascita -> this.getWrapAnnoNato(titoloPagina, bio, typeLinkParagrafi, typeLinkCrono, usaIcona);
            case annoMorte -> this.getWrapAnnoMorto(titoloPagina, bio, typeLinkParagrafi, typeLinkCrono, usaIcona);
            case attivitaSingolare, attivitaPlurale -> this.getWrapAttivita(titoloPagina, bio, typeLinkParagrafi, typeLinkCrono, usaIcona);
            case nazionalitaSingolare, nazionalitaPlurale -> this.getWrapNazionalita(titoloPagina, bio, typeLinkParagrafi, typeLinkCrono, usaIcona);
            case nomi -> this.getWrapNomi(titoloPagina, bio, typeLinkParagrafi, typeLinkCrono, usaIcona);
            case cognomi -> this.getWrapCognomi(titoloPagina, bio, typeLinkParagrafi, typeLinkCrono, usaIcona);
            case listaBreve -> null;
            case listaEstesa -> null;
            default -> null;
        };
    }

    public String giornoNato(final Bio bio, AETypeLink typeLinkCrono) {
        return wikiUtility.giornoNatoTesta(bio, typeLinkCrono);
    }

    public String giornoNatoSimbolo(final Bio bio, AETypeLink typeLinkCrono) {
        return wikiUtility.giornoNatoCoda(bio, typeLinkCrono, true, false);
    }

    public String giornoNatoSimboloParentesi(final Bio bio, AETypeLink typeLinkCrono) {
        return wikiUtility.giornoNatoCoda(bio, typeLinkCrono, true, true);
    }


    public String giornoMorto(final Bio bio, AETypeLink typeLinkCrono) {
        return wikiUtility.giornoMortoTesta(bio, typeLinkCrono);
    }

    public String giornoMortoSimbolo(final Bio bio, AETypeLink typeLinkCrono) {
        return wikiUtility.giornoMortoCoda(bio, typeLinkCrono, true, false);
    }

    public String giornoMortoSimboloParentesi(final Bio bio, AETypeLink typeLinkCrono) {
        return wikiUtility.giornoMortoCoda(bio, typeLinkCrono, true, true);
    }


    public String annoNato(final Bio bio, AETypeLink typeLinkCrono) {
        return wikiUtility.annoNatoTesta(bio, typeLinkCrono);
    }

    public String annoNatoSimbolo(final Bio bio, AETypeLink typeLinkCrono) {
        return wikiUtility.annoNatoCoda(bio, typeLinkCrono, true, false);
    }

    public String annoNatoSimboloParentesi(final Bio bio, AETypeLink typeLinkCrono) {
        return wikiUtility.annoNatoCoda(bio, typeLinkCrono, true, true);
    }


    public String annoMorto(final Bio bio, AETypeLink typeLinkCrono) {
        return wikiUtility.annoMortoTesta(bio, typeLinkCrono);
    }

    public String annoMortoSimbolo(final Bio bio, AETypeLink typeLinkCrono) {
        return wikiUtility.annoMortoCoda(bio, typeLinkCrono, true, false);
    }

    public String annoMortoSimboloParentesi(final Bio bio, AETypeLink typeLinkCrono) {
        return wikiUtility.annoMortoCoda(bio, typeLinkCrono, true, true);
    }

    public String luogoNato(final Bio bio) {
        String luogoNato = textService.isValid(bio.luogoNato) ? bio.luogoNato : VUOTA;
        String luogoNatoLink = textService.isValid(bio.luogoNatoLink) ? bio.luogoNatoLink : VUOTA;
        if (textService.isValid(luogoNato) && textService.isValid(luogoNatoLink)) {
            luogoNato = luogoNatoLink + PIPE + luogoNato;
        }

        return textService.isValid(luogoNato) ? textService.setDoppieQuadre(luogoNato) : VUOTA;
    }

    public String luogoNatoAnno(final Bio bio) {
        return luogoNatoAnno(bio, null, WPref.usaSimboliCrono.is());
    }

    public String luogoNatoAnno(final Bio bio, AETypeLink typeLinkCrono, boolean usaIcona) {
        String luogoNato = luogoNato(bio);
        String annoNato;

        if (typeLinkCrono == null) {
            typeLinkCrono = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = AETypeLink.linkLista;
        }
        //         annoNato = annoNatoSimbolo(bio, typeLinkCrono);
        annoNato = wikiUtility.annoNatoCoda(bio, typeLinkCrono, usaIcona, false);

        if (textService.isValid(luogoNato) && textService.isValid(annoNato)) {
            return luogoNato + VIRGOLA_SPAZIO + annoNato;
        }
        else {
            if (textService.isValid(luogoNato)) {
                return luogoNato;
            }
            if (textService.isValid(annoNato)) {
                return annoNato;
            }
        }

        return VUOTA;
    }


    public String luogoMorto(final Bio bio) {
        String luogoMorto = textService.isValid(bio.luogoMorto) ? bio.luogoMorto : VUOTA;
        String luogoMortoLink = textService.isValid(bio.luogoMortoLink) ? bio.luogoMortoLink : VUOTA;
        if (textService.isValid(luogoMorto) && textService.isValid(luogoMortoLink)) {
            luogoMorto = luogoMortoLink + PIPE + luogoMorto;
        }

        return textService.isValid(luogoMorto) ? textService.setDoppieQuadre(luogoMorto) : VUOTA;
    }


    public String luogoMortoAnno(final Bio bio) {
        return luogoMortoAnno(bio, null, WPref.usaSimboliCrono.is());
    }

    public String luogoMortoAnno(final Bio bio, AETypeLink typeLinkCrono, boolean usaIcona) {
        String luogoMorto = luogoMorto(bio);
        String annoMorto;

        if (typeLinkCrono == null) {
            typeLinkCrono = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = AETypeLink.linkLista;
        }
        //         annoMorto = annoMortoSimbolo(bio, typeLinkCrono);
        annoMorto = wikiUtility.annoMortoCoda(bio, typeLinkCrono, usaIcona, false);

        if (textService.isValid(luogoMorto) && textService.isValid(annoMorto)) {
            return luogoMorto + VIRGOLA_SPAZIO + annoMorto;
        }
        else {
            if (textService.isValid(luogoMorto)) {
                return luogoMorto;
            }
            if (textService.isValid(annoMorto)) {
                return annoMorto;
            }
        }

        return VUOTA;
    }

    public String luogoNatoMorto(final Bio bio) {
        return luogoNatoMorto(bio, null, WPref.usaSimboliCrono.is());
    }

    public String luogoNatoMorto(final Bio bio, AETypeLink typeLinkCrono, boolean usaIcona) {
        String luogoNatoAnno;
        String luogoMortoAnno;

        if (typeLinkCrono == null) {
            typeLinkCrono = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        }
        if (typeLinkCrono == null) {
            typeLinkCrono = AETypeLink.linkLista;
        }
        luogoNatoAnno = luogoNatoAnno(bio, typeLinkCrono, usaIcona);
        luogoMortoAnno = luogoMortoAnno(bio, typeLinkCrono, usaIcona);

        if (textService.isValid(luogoNatoAnno) && textService.isValid(luogoMortoAnno)) {
            return textService.setTonde(luogoNatoAnno + SEP + luogoMortoAnno);
        }
        else {
            if (textService.isValid(luogoNatoAnno)) {
                return textService.setTonde(luogoNatoAnno);
            }
            if (textService.isValid(luogoMortoAnno)) {
                return textService.setTonde(luogoMortoAnno);
            }
        }

        return VUOTA;
    }

    /**
     * Costruisce la didascalia completa per una lista: <br>
     * attività, nazionalità, persone di nome, persone di cognome <br>
     *
     * @param bio completa
     *
     * @return didascalia completa
     */
    public String lista(final Bio bio) {
        return lista(bio, null);
    }

    public String lista(final Bio bio, boolean usaIcona) {
        return lista(bio, null, usaIcona);
    }


    public String lista(final Bio bio, AETypeLink typeLinkCrono) {
        return lista(bio, typeLinkCrono, true);
    }

    public String lista(final Bio bio, AETypeLink typeLinkCrono, boolean usaIcona) {
        return nomeCognome(bio) + VIRGOLA_SPAZIO + attivitaNazionalita(bio) + SPAZIO + luogoNatoMorto(bio, typeLinkCrono, usaIcona);
    }

    /**
     * Costruisce la didascalia completa per una lista: <br>
     * attività, nazionalità, persone di nome, persone di cognome <br>
     *
     * @param bio completa
     *
     * @return didascalia completa
     */
    public String lista(final AETypeLink typeLinkCrono, final Bio bio, AETypeLink typeLinkParagrafi) {
        return nomeCognome(bio) + VIRGOLA_SPAZIO + attivitaNazionalita(bio) + SPAZIO + luogoNatoMorto(bio, typeLinkParagrafi, true);
    }


    public WrapLista getWrap(String titoloPagina, final Bio bio, AETypeLink typeLinkCrono, boolean usaIcona, AETypeLista typeLista, String paragrafo, String paragrafoLink, String sottoParagrafo, String ordinamento) {

        String lista = this.lista(bio, typeLinkCrono, usaIcona);
        String giornoNato = this.didascaliaGiornoNato(bio, typeLinkCrono, usaIcona);
        String giornoMorto = this.didascaliaGiornoMorto(bio, typeLinkCrono, usaIcona);
        String annoNato = this.didascaliaAnnoNato(bio, typeLinkCrono, usaIcona);
        String annoMorto = this.didascaliaAnnoMorto(bio, typeLinkCrono, usaIcona);

        return new WrapLista(
                titoloPagina,
                typeLista,
                paragrafo,
                paragrafoLink,
                sottoParagrafo,
                ordinamento,
                lista,
                giornoNato,
                giornoMorto,
                annoNato,
                annoMorto
        );
    }

}