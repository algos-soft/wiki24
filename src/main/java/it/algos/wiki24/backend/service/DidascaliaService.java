package it.algos.wiki24.backend.service;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.service.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import org.springframework.stereotype.*;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 03-Jan-2024
 * Time: 07:35
 * Classe di libreria. NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 *
 * @Inject public DidascaliaService DidascaliaService; (iniziale minuscola) <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class DidascaliaService {

    @Inject
    TextService textService;

    @Inject
    WikiUtilityService wikiUtilityService;

    /**
     * Costruisce il nome e cognome (obbligatori) <br>
     * Si usa il titolo della voce direttamente, se non contiene parentesi <br>
     *
     * @param bio completa
     *
     * @return nome e cognome elaborati e inseriti nelle doppie quadre
     */
    public String nomeCognome(final BioMongoEntity bio) {
        return nomeCognome(getWikiTitle(bio), bio.nome, bio.cognome);
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
            if (wikiTitle.contains(tagPar) && !wikiTitle.contains(PIPE)) {
                nomePrimaDellaParentesi = wikiTitle.substring(0, wikiTitle.indexOf(tagPar));
                nomeCognome = wikiTitle + tagPipe + nomePrimaDellaParentesi;
            }
            else {
                nomeCognome = wikiTitle;
            }
        }

        nomeCognome = nomeCognome.trim();
        if (!nomeCognome.startsWith(DOPPIE_QUADRE_INI)) {
            nomeCognome = textService.setDoppieQuadre(nomeCognome);
        }

        return nomeCognome;
    }

    /**
     * Costruisce attività e nazionalità (obbligatori) <br>
     *
     * @param bio completa
     *
     * @return attività e nazionalità elaborati
     */
    public String attivitaNazionalita(final BioMongoEntity bio) {
        return attivitaNazionalita(getWikiTitle(bio), bio.attivita, bio.attivita2, bio.attivita3, bio.nazionalita);
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

    public String getWikiTitle(final BioMongoEntity bio) {
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

    public String luogoNato(final BioMongoEntity bio) {
        String luogoNato = textService.isValid(bio.luogoNato) ? bio.luogoNato : VUOTA;
        String luogoNatoLink = textService.isValid(bio.luogoNatoLink) ? bio.luogoNatoLink : VUOTA;
        if (textService.isValid(luogoNato) && textService.isValid(luogoNatoLink)) {
            luogoNato = luogoNatoLink + PIPE + luogoNato;
        }

        return textService.isValid(luogoNato) ? luogoNato : VUOTA;
    }

    public String luogoMorto(final BioMongoEntity bio) {
        String luogoMorto = textService.isValid(bio.luogoMorto) ? bio.luogoMorto : VUOTA;
        String luogoMortoLink = textService.isValid(bio.luogoMortoLink) ? bio.luogoMortoLink : VUOTA;
        if (textService.isValid(luogoMorto) && textService.isValid(luogoMortoLink)) {
            luogoMorto = luogoMortoLink + PIPE + luogoMorto;
        }

        return textService.isValid(luogoMorto) ? luogoMorto : VUOTA;
    }

    public String giornoNato(final BioMongoEntity bio) {
        String giornoNato = bio.giornoNato;

        if (textService.isEmpty(giornoNato)) {
            return VUOTA;
        }

        giornoNato = wikiUtilityService.wikiTitleNatiGiorno(giornoNato) + PIPE + giornoNato;
        return textService.isValid(giornoNato) ? textService.setDoppieQuadre(giornoNato) : VUOTA;
    }

    public String giornoMorto(final BioMongoEntity bio) {
        String giornoMorto = bio.giornoMorto;

        if (textService.isEmpty(giornoMorto)) {
            return VUOTA;
        }

        giornoMorto = wikiUtilityService.wikiTitleMortiGiorno(giornoMorto) + PIPE + giornoMorto;
        return textService.isValid(giornoMorto) ? textService.setDoppieQuadre(giornoMorto) : VUOTA;
    }

    public String annoNato(final BioMongoEntity bio) {
        String annoNato = bio.annoNato;

        if (textService.isEmpty(annoNato)) {
            return VUOTA;
        }

        annoNato = wikiUtilityService.wikiTitleNatiAnno(annoNato) + PIPE + annoNato;
        return textService.isValid(annoNato) ? textService.setDoppieQuadre(annoNato) : VUOTA;
    }

    public String annoNatoIcona(final BioMongoEntity bio) {
        String annoNatoLinkato = annoNato(bio);
        String tagNato = WPref.iconaNato.getStr();

        if (textService.isEmpty(annoNatoLinkato)) {
            return VUOTA;
        }

        return tagNato + annoNatoLinkato;
    }

    public String annoNatoIconaTonde(final BioMongoEntity bio) {
        String annoNatoLinkato = annoNato(bio);
        String tagNato = WPref.iconaNato.getStr();

        if (textService.isEmpty(annoNatoLinkato)) {
            return VUOTA;
        }

        return textService.setTonde(tagNato + annoNatoLinkato);
    }

    public String annoMorto(final BioMongoEntity bio) {
        String annoMorto = bio.annoMorto;

        if (textService.isEmpty(annoMorto)) {
            return VUOTA;
        }

        annoMorto = wikiUtilityService.wikiTitleMortiAnno(annoMorto) + PIPE + annoMorto;
        return textService.isValid(annoMorto) ? textService.setDoppieQuadre(annoMorto) : VUOTA;
    }


    public String annoMortoIcona(final BioMongoEntity bio) {
        String annoMortoLinkato = annoMorto(bio);
        String tagMorto = WPref.iconaMorto.getStr();

        if (textService.isEmpty(annoMortoLinkato)) {
            return VUOTA;
        }

        return tagMorto + annoMortoLinkato;
    }
    public String annoMortoIconaTonde(final BioMongoEntity bio) {
        String annoMortoLinkato = annoMorto(bio);
        String tagMorto = WPref.iconaMorto.getStr();

        if (textService.isEmpty(annoMortoLinkato)) {
            return VUOTA;
        }

        return textService.setTonde(tagMorto + annoMortoLinkato);
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
    public String didascaliaGiornoNato(final BioMongoEntity bio) {
        StringBuffer buffer = new StringBuffer();
        String annoNato = annoNato(bio);
        String annoMorto = annoMortoIconaTonde(bio);
        String attivitaNazionalita = attivitaNazionalita(bio);

        if (textService.isEmpty(bio.giornoNato)) {
            return VUOTA;
        }

        if (textService.isValid(annoNato)) {
            buffer.append(annoNato);
            buffer.append(SEP);
        }

        buffer.append(getWikiTitle(bio));

        if (textService.isValid(attivitaNazionalita)) {
            buffer.append(VIRGOLA_SPAZIO);
            buffer.append(attivitaNazionalita);
        }

        if (textService.isValid(annoMorto)) {
            buffer.append(SPAZIO);
            buffer.append(annoMorto);
        }

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
    public String didascaliaGiornoMorto(final BioMongoEntity bio) {
        StringBuffer buffer = new StringBuffer();
        String annoMorto = annoMorto(bio);
        String annoNato = annoNatoIconaTonde(bio);
        String attivitaNazionalita = attivitaNazionalita(bio);

        if (textService.isEmpty(bio.giornoMorto)) {
            return VUOTA;
        }

        if (textService.isValid(annoMorto)) {
            buffer.append(annoMorto);
            buffer.append(SEP);
        }

        buffer.append(getWikiTitle(bio));

        if (textService.isValid(attivitaNazionalita)) {
            buffer.append(VIRGOLA_SPAZIO);
            buffer.append(attivitaNazionalita);
        }

        if (textService.isValid(annoNato)) {
            buffer.append(SPAZIO);
            buffer.append(annoNato);
        }

        return buffer.toString().trim();
    }

    /**
     * Costruisce la didascalia completa per una lista di nati nel anno <br>
     * WikiTitle (sempre)
     * AttivitàNazionalità (sempre)
     * Anno di morte (opzionale)
     *
     * @param bio completa
     *
     * @return didascalia completa
     */
    public String didascaliaAnnoNato(final BioMongoEntity bio) {
        StringBuffer buffer = new StringBuffer();
        String giornoNato = giornoNato(bio);
        String annoMorto = annoMortoIconaTonde(bio);
        String attivitaNazionalita = attivitaNazionalita(bio);

        if (textService.isEmpty(bio.annoNato)) {
            return VUOTA;
        }

        if (textService.isValid(giornoNato)) {
            buffer.append(giornoNato);
            buffer.append(SEP);
        }

        buffer.append(getWikiTitle(bio));

        if (textService.isValid(attivitaNazionalita)) {
            buffer.append(VIRGOLA_SPAZIO);
            buffer.append(attivitaNazionalita);
        }

        if (textService.isValid(annoMorto)) {
            buffer.append(SPAZIO);
            buffer.append(annoMorto);
        }

        return buffer.toString().trim();
    }


    /**
     * Costruisce la didascalia completa per una lista di morti nel anno <br>
     * WikiTitle (sempre)
     * AttivitàNazionalità (sempre)
     * Anno di nascita (opzionale)
     *
     * @param bio completa
     *
     * @return didascalia completa
     */
    public String didascaliaAnnoMorto(final BioMongoEntity bio) {
        StringBuffer buffer = new StringBuffer();
        String giornoMorto = giornoMorto(bio);
        String annoNato = annoNatoIconaTonde(bio);
        String attivitaNazionalita = attivitaNazionalita(bio);

        if (textService.isEmpty(bio.annoMorto)) {
            return VUOTA;
        }

        if (textService.isValid(giornoMorto)) {
            buffer.append(giornoMorto);
            buffer.append(SEP);
        }

        buffer.append(getWikiTitle(bio));

        if (textService.isValid(attivitaNazionalita)) {
            buffer.append(VIRGOLA_SPAZIO);
            buffer.append(attivitaNazionalita);
        }

        if (textService.isValid(annoNato)) {
            buffer.append(SPAZIO);
            buffer.append(annoNato);
        }

        return buffer.toString().trim();
    }

    /**
     * Costruisce la didascalia completa per una lista di attività/nazionalità <br>
     * WikiTitle (sempre)
     * AttivitàNazionalità (sempre)
     * Blocco luogo e anni nascita e morte (sempre)
     *
     * @param bio completa
     *
     * @return didascalia completa
     */
    public String didascaliaLista(final BioMongoEntity bio) {
        StringBuffer buffer = new StringBuffer();
        String attivitaNazionalita = attivitaNazionalita(bio);
        String localitaCrono = localitaCrono(bio);

        buffer.append(getWikiTitle(bio));

        if (textService.isValid(attivitaNazionalita)) {
            buffer.append(VIRGOLA_SPAZIO);
            buffer.append(attivitaNazionalita);
        }

        if (textService.isValid(localitaCrono)) {
            buffer.append(SPAZIO);
            buffer.append(localitaCrono);
        }

        return buffer.toString().trim();
    }

    public String luogoNatoCrono(final BioMongoEntity bio) {
        String luogoNato = luogoNato(bio);
        String annoNato = annoNatoIcona(bio);

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
            return VUOTA;
        }
    }

    public String luogoMortoCrono(final BioMongoEntity bio) {
        String luogoMorto = luogoMorto(bio);
        String annoMorto = annoMortoIcona(bio);

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
            return VUOTA;
        }
    }

    public String localitaCrono(final BioMongoEntity bio) {
        String luogoNatoCrono = luogoNatoCrono(bio);
        String luogoMortoCrono = luogoMortoCrono(bio);

        if (textService.isValid(luogoNatoCrono) && textService.isValid(luogoMortoCrono)) {
            return textService.setTonde(luogoNatoCrono + SEP + luogoMortoCrono);
        }
        else {
            if (textService.isValid(luogoNatoCrono)) {
                return textService.setTonde(luogoNatoCrono);
            }
            if (textService.isValid(luogoMortoCrono)) {
                return textService.setTonde(luogoMortoCrono);
            }
            return VUOTA;
        }
    }


    public String getDecade(final String annoIn) {
        String decade = VUOTA;
        String anno;
        char character;
        char character2;

        if (textService.isEmpty(annoIn)) {
            return VUOTA;
        }

        anno = annoIn.trim();
        anno = textService.levaCoda(anno, ANNI_AC);

        if (anno.length() > 1) {
            character = anno.charAt(anno.length() - 2);
            character2 = anno.charAt(anno.length() - 1);
            if (character2 == 48) {
                if (character == 48) {
                    character += 9;
                }
                else {
                    character -= 1;
                }
            }
        }
        else {
            character = 48;
        }

        decade = switch (character) {
            case 48 -> "1-10";
            case 49 -> "11-20";
            case 50 -> "21-30";
            case 51 -> "31-40";
            case 52 -> "41-50";
            case 53 -> "51-60";
            case 54 -> "61-70";
            case 55 -> "71-80";
            case 56 -> "81-90";
            case 57 -> "91-00";
            default -> VUOTA;
        };

        return decade;
    }

}// end of Service class