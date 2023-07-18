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

    private static String NATI = "Nati nel ";

    private static String MORTI = "Morti nel ";

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
    public String didascaliaGiornoNato(final Bio bio, final AETypeLink typeLinkParagrafi) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getWikiTitle(bio));
        if (textService.isValid(attivitaNazionalita(bio))) {
            buffer.append(VIRGOLA_SPAZIO);
            buffer.append(attivitaNazionalita(bio));
        }
        buffer.append(SPAZIO);
        buffer.append(annoMortoSimboloParentesi(bio, typeLinkParagrafi));

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
    public String didascaliaGiornoMorto(final Bio bio, final AETypeLink typeLinkParagrafi) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getWikiTitle(bio));
        if (textService.isValid(attivitaNazionalita(bio))) {
            buffer.append(VIRGOLA_SPAZIO);
            buffer.append(attivitaNazionalita(bio));
        }
        buffer.append(SPAZIO);
        buffer.append(annoNatoSimboloParentesi(bio, typeLinkParagrafi));

        return buffer.toString();
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
    public String didascaliaAnnoNato(final Bio bio, final AETypeLink typeLinkParagrafi) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getWikiTitle(bio));
        if (textService.isValid(attivitaNazionalita(bio))) {
            buffer.append(VIRGOLA_SPAZIO);
            buffer.append(attivitaNazionalita(bio));
        }
        buffer.append(SPAZIO);
        buffer.append(annoMortoSimboloParentesi(bio, typeLinkParagrafi));

        return buffer.toString();
    }

    /**
     * Costruisce la didascalia completa per una lista di morti nell'anno <br>
     *
     * @param bio completa
     *
     * @return didascalia completa
     */
    public String didascaliaAnnoMorto(final Bio bio, final AETypeLink typeLinkParagrafi) {
        StringBuffer buffer = new StringBuffer();
        boolean nonBreaking = Pref.usaNonBreaking.is();

        buffer.append(getWikiTitle(bio));
        if (textService.isValid(attivitaNazionalita(bio))) {
            buffer.append(VIRGOLA_SPAZIO);
            buffer.append(attivitaNazionalita(bio));
        }
        buffer.append(nonBreaking ? Pref.nonBreaking.getStr() : SPAZIO);
        buffer.append(annoNatoSimboloParentesi(bio, typeLinkParagrafi));

        return buffer.toString();
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
    public WrapLista getWrapGiornoNato(final Bio bio, final AETypeLink typeLinkParagrafi) {
        String paragrafo = wikiUtility.fixSecoloNato(bio);
        String paragrafoLink;

        paragrafoLink = switch (typeLinkParagrafi) {
            case linkVoce -> textService.setDoppieQuadre(paragrafo);
            case nessunLink -> paragrafo;
            default -> paragrafo;
        };

        if (textService.isEmpty(paragrafo)) {
            paragrafo = TAG_LISTA_NO_ANNO;
            paragrafoLink = paragrafo;
        }

        String sottoParagrafo = wikiUtility.annoNatoTesta(bio, typeLinkParagrafi);
        String didascalia = this.didascaliaGiornoNato(bio, typeLinkParagrafi);

        return new WrapLista(paragrafo, paragrafoLink, sottoParagrafo, didascalia);
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
    public WrapLista getWrapGiornoMorto(final Bio bio, final AETypeLink typeLinkParagrafi) {
        String paragrafo = wikiUtility.fixSecoloMorto(bio);
        String paragrafoLink;

        paragrafoLink = switch (typeLinkParagrafi) {
            case linkVoce -> textService.setDoppieQuadre(paragrafo);
            case nessunLink -> paragrafo;
            default -> paragrafo;
        };

        if (textService.isEmpty(paragrafo)) {
            paragrafo = TAG_LISTA_NO_ANNO;
            paragrafoLink = paragrafo;
        }

        String sottoParagrafo = wikiUtility.annoMortoTesta(bio, typeLinkParagrafi);
        String didascalia = this.didascaliaGiornoMorto(bio, typeLinkParagrafi);

        return new WrapLista(paragrafo, paragrafoLink, sottoParagrafo, didascalia);
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
    public WrapLista getWrapAnnoNato(final Bio bio, final AETypeLink typeLinkParagrafi) {
        String paragrafo = wikiUtility.fixMeseNato(bio);
        String paragrafoLink;

        paragrafoLink = switch (typeLinkParagrafi) {
            case linkVoce -> textService.setDoppieQuadre(paragrafo);
            case nessunLink -> paragrafo;
            default -> paragrafo;
        };

        if (textService.isEmpty(paragrafo)) {
            paragrafo = TAG_LISTA_NO_GIORNO;
            paragrafoLink = paragrafo;
        }

        String sottoParagrafo = wikiUtility.giornoNatoTesta(bio, typeLinkParagrafi);
        String didascalia = this.didascaliaAnnoNato(bio, typeLinkParagrafi);

        return new WrapLista(paragrafo, paragrafoLink, sottoParagrafo, didascalia);
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
    public WrapLista getWrapAnnoMorto(final Bio bio, final AETypeLink typeLinkParagrafi) {
        String paragrafo = wikiUtility.fixMeseMorto(bio);
        String paragrafoLink;

        paragrafoLink = switch (typeLinkParagrafi) {
            case linkVoce -> textService.setDoppieQuadre(paragrafo);
            case nessunLink -> paragrafo;
            default -> paragrafo;
        };

        if (textService.isEmpty(paragrafo)) {
            paragrafo = TAG_LISTA_NO_GIORNO;
            paragrafoLink = paragrafo;
        }

        String sottoParagrafo = wikiUtility.giornoMortoTesta(bio, typeLinkParagrafi);
        String didascalia = this.didascaliaAnnoMorto(bio, typeLinkParagrafi);

        return new WrapLista(paragrafo, paragrafoLink, sottoParagrafo, didascalia);
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
    public WrapLista getWrapAttivita(final Bio bio) {
        NazSingolare nazSingolare = nazSingolareBackend.findByKey(bio.nazionalita);
        NazPlurale nazPlurale;
        String paragrafo;
        String paragrafoLink;
        String message;

        if (nazSingolare != null) {
            nazPlurale = nazPluraleBackend.findByKey(nazSingolare.plurale);
            if (nazPlurale != null) {
                paragrafo = textService.primaMaiuscola(nazPlurale.nome);
                paragrafoLink = switch ((AETypeLink) WPref.linkAttNaz.getEnumCurrentObj()) {
                    case linkVoce -> textService.setDoppieQuadre(paragrafo);
                    case linkLista -> textService.setDoppieQuadre(PATH_NAZIONALITA + SLASH + paragrafo + PIPE + paragrafo);
                    case nessunLink -> paragrafo;
                };
            }
            else {
                paragrafo = textService.primaMaiuscola(nazSingolare.nome);
                paragrafoLink = paragrafo;
                message = String.format("Manca l'attività plurale di %s", nazSingolare);
                System.out.println(message);
                logService.warn(new WrapLog().message(message));
            }
        }
        else {
            paragrafo = TAG_LISTA_NO_NAZIONALITA;
            paragrafoLink = TAG_LISTA_NO_NAZIONALITA;
        }

        String sottoParagrafo = bio.ordinamento.substring(0, 1);
        String didascalia = this.lista(bio, null);//@todo ERRORE

        return new WrapLista(paragrafo, paragrafoLink, bio.ordinamento, sottoParagrafo, didascalia);
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
    public WrapLista getWrapNazionalita(final Bio bio) {
        AttSingolare attSingolare = attSingolareBackend.findByKey(bio.attivita);
        AttPlurale attPlurale;
        String paragrafo;
        String paragrafoLink;
        String message;

        if (attSingolare != null) {
            attPlurale = attPluraleBackend.findByKey(attSingolare.plurale);
            if (attPlurale != null) {
                paragrafo = textService.primaMaiuscola(attPlurale.nome);
                paragrafoLink = switch ((AETypeLink) WPref.linkAttNaz.getEnumCurrentObj()) {
                    case linkVoce -> textService.setDoppieQuadre(paragrafo);
                    case linkLista -> textService.setDoppieQuadre(PATH_ATTIVITA + SLASH + paragrafo + PIPE + paragrafo);
                    case nessunLink -> paragrafo;
                };
            }
            else {
                paragrafo = textService.primaMaiuscola(attSingolare.nome);
                paragrafoLink = paragrafo;
                message = String.format("Manca la nazionalità plurale di %s", attSingolare);
                System.out.println(message);
                logService.warn(new WrapLog().message(message));
            }
        }
        else {
            paragrafo = TAG_LISTA_NO_ATTIVITA;
            paragrafoLink = TAG_LISTA_NO_ATTIVITA;
        }

        String sottoParagrafo = bio.ordinamento.substring(0, 1);
        String didascalia = this.lista(bio, null);//@todo ERRORE

        return new WrapLista(paragrafo, paragrafoLink, bio.ordinamento, sottoParagrafo, didascalia);
    }

    /**
     * Costruisce una wrapLista specializzata per le righe delle pagine 'Persone di nome' <br>
     * Contiene il paragrafo 'attivita'
     * Contiene la didascalia con 'wikiTitle', 'attività/nazionalità', 'luogo e anno di nascita', 'luogo e anno di morte'
     *
     * @param bio completa
     *
     * @return wrapLista
     */
    public WrapLista getWrapNomi(final Bio bio, final AETypeLink typeLinkParagrafi) {
        String paragrafo;
        String paragrafoLink;

        paragrafo = genereBackend.getPluraleParagrafo(bio);
        paragrafoLink = VUOTA;
        String sottoParagrafo = bio.ordinamento.substring(0, 1);
        String didascalia = this.lista(bio, null);//@todo ERRORE

        return new WrapLista(paragrafo, paragrafoLink, bio.ordinamento, sottoParagrafo, didascalia);
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
    public WrapLista getWrapCognomi(final Bio bio, final AETypeLink typeLinkParagrafi) {
        String paragrafo;
        String paragrafoLink;
        String sottoParagrafo = VUOTA;

        paragrafo = genereBackend.getPluraleParagrafo(bio);
        paragrafoLink = VUOTA;
        if (textService.isValid(bio.nome)) {
            sottoParagrafo = bio.nome.substring(0, 1);
        }
        String didascalia = this.lista(bio, null);//@todo ERRORE

        return new WrapLista(paragrafo, paragrafoLink, bio.ordinamento, sottoParagrafo, didascalia);
    }


    /**
     * Costruisce una wrapLista <br>
     *
     * @param bio completa
     *
     * @return wrapLista
     */
    public WrapLista getWrap(final Bio bio, final AETypeLista typeLista, final AETypeLink typeLinkParagrafi, final AETypeLink typeLinkCrono) {
        return switch (typeLista) {
            case giornoNascita -> this.getWrapGiornoNato(bio, typeLinkParagrafi);
            case giornoMorte -> this.getWrapGiornoMorto(bio, typeLinkParagrafi);
            case annoNascita -> this.getWrapAnnoNato(bio, typeLinkParagrafi);
            case annoMorte -> this.getWrapAnnoMorto(bio, typeLinkParagrafi);
            case attivitaSingolare, attivitaPlurale -> this.getWrapAttivita(bio);
            case nazionalitaSingolare, nazionalitaPlurale -> this.getWrapNazionalita(bio);
            case nomi -> this.getWrapNomi(bio, typeLinkParagrafi);
            case cognomi -> this.getWrapCognomi(bio, typeLinkParagrafi);
            case listaBreve -> null;
            case listaEstesa -> null;
            default -> null;
        };
    }

    public String giornoNato(final Bio bio, AETypeLink typeLinkParagrafi) {
        return wikiUtility.giornoNatoTesta(bio, typeLinkParagrafi);
    }

    public String giornoNatoSimbolo(final Bio bio, AETypeLink typeLinkParagrafi) {
        return wikiUtility.giornoNatoCoda(bio, typeLinkParagrafi, false);
    }

    public String giornoNatoSimboloParentesi(final Bio bio, AETypeLink typeLinkParagrafi) {
        return wikiUtility.giornoNatoCoda(bio, typeLinkParagrafi, true);
    }


    public String giornoMorto(final Bio bio, AETypeLink typeLinkParagrafi) {
        return wikiUtility.giornoMortoTesta(bio, typeLinkParagrafi);
    }

    public String giornoMortoSimbolo(final Bio bio, AETypeLink typeLinkParagrafi) {
        return wikiUtility.giornoMortoCoda(bio, typeLinkParagrafi, false);
    }

    public String giornoMortoSimboloParentesi(final Bio bio, AETypeLink typeLinkParagrafi) {
        return wikiUtility.giornoMortoCoda(bio, typeLinkParagrafi, true);
    }


    public String annoNato(final Bio bio, AETypeLink typeLinkParagrafi) {
        return wikiUtility.annoNatoTesta(bio, typeLinkParagrafi);
    }

    public String annoNatoSimbolo(final Bio bio, AETypeLink typeLinkParagrafi) {
        return wikiUtility.annoNatoCoda(bio, typeLinkParagrafi, false);
    }

    public String annoNatoSimboloParentesi(final Bio bio, AETypeLink typeLinkParagrafi) {
        return wikiUtility.annoNatoCoda(bio, typeLinkParagrafi, true);
    }


    public String annoMorto(final Bio bio, AETypeLink typeLinkParagrafi) {
        return wikiUtility.annoMortoTesta(bio, typeLinkParagrafi);
    }

    public String annoMortoSimbolo(final Bio bio, AETypeLink typeLinkParagrafi) {
        return wikiUtility.annoMortoCoda(bio, typeLinkParagrafi, false);
    }

    public String annoMortoSimboloParentesi(final Bio bio, AETypeLink typeLinkParagrafi) {
        return wikiUtility.annoMortoCoda(bio, typeLinkParagrafi, true);
    }

    public String luogoNato(final Bio bio) {
        String luogoNato = textService.isValid(bio.luogoNato) ? bio.luogoNato : VUOTA;
        String luogoNatoLink = textService.isValid(bio.luogoNatoLink) ? bio.luogoNatoLink : VUOTA;
        if (textService.isValid(luogoNato) && textService.isValid(luogoNatoLink)) {
            luogoNato = luogoNatoLink + PIPE + luogoNato;
        }

        return textService.isValid(luogoNato) ? textService.setDoppieQuadre(luogoNato) : VUOTA;
    }

    public String luogoNatoAnno(final Bio bio, AETypeLink typeLinkParagrafi) {
        String luogoNato = luogoNato(bio);
        String annoNato = annoNatoSimbolo(bio, typeLinkParagrafi);

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

    public String luogoMortoAnno(final Bio bio, AETypeLink typeLinkParagrafi) {
        String luogoMorto = luogoMorto(bio);
        String annoMorto = annoMortoSimbolo(bio, typeLinkParagrafi);

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

    public String luogoNatoMorto(final Bio bio, AETypeLink typeLinkParagrafi) {
        String luogoNatoAnno = luogoNatoAnno(bio, typeLinkParagrafi);
        String luogoMortoAnno = luogoMortoAnno(bio, typeLinkParagrafi);

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
    public String lista(final Bio bio, AETypeLink typeLinkParagrafi) {
        return nomeCognome(bio) + VIRGOLA_SPAZIO + attivitaNazionalita(bio) + SPAZIO + luogoNatoMorto(bio, typeLinkParagrafi);
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
        return nomeCognome(bio) + VIRGOLA_SPAZIO + attivitaNazionalita(bio) + SPAZIO + luogoNatoMorto(bio, typeLinkParagrafi);
    }

}