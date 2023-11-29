package it.algos.base24.backend.service;

import com.google.common.base.*;
import it.algos.base24.backend.boot.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import org.apache.commons.lang3.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Tue, 08-Aug-2023
 * Time: 19:02
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Autowired public TextService textService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class TextService {

    /**
     * Null-safe, short-circuit evaluation. <br>
     *
     * @param testoIn in ingresso da controllare
     *
     * @return vero se la stringa è vuota o nulla
     */
    public boolean isEmpty(final String testoIn) {
        return Strings.isNullOrEmpty(testoIn);
    }


    /**
     * Null-safe, short-circuit evaluation. <br>
     * Controlla che sia una stringa e che sia valida.
     *
     * @param obj in ingresso da controllare che non sia nullo, che sia una stringa e che non sia vuota
     *
     * @return vero se la stringa esiste e non è vuota
     */
    public boolean isValid(final Object obj) {
        if (obj == null) {
            return false;
        }

        return (obj instanceof String stringa) && !isEmpty(stringa);
    }

    public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    /**
     * Forza il primo carattere della stringa (e solo il primo) al carattere maiuscolo <br>
     * <p>
     * Se la stringa è nulla, ritorna un nullo <br>
     * Se la stringa è vuota, ritorna una stringa vuota <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param testoIn stringa in ingresso
     *
     * @return testo formattato in uscita
     */
    public String primaMaiuscola(final String testoIn) {
        String testoOut = isValid(testoIn) ? testoIn.trim() : BaseCost.VUOTA;
        String primoCarattere;

        if (isValid(testoOut)) {
            primoCarattere = testoOut.substring(0, 1).toUpperCase();
            testoOut = primoCarattere + testoOut.substring(1);
        }

        return testoOut.trim();
    }


    /**
     * Forza il primo carattere della stringa (e solo il primo) al carattere minuscolo <br>
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn stringa in ingresso
     *
     * @return testo formattato in uscita
     */
    public String primaMinuscola(final String testoIn) {
        String testoOut = isValid(testoIn) ? testoIn.trim() : BaseCost.VUOTA;
        String primoCarattere;

        if (isValid(testoOut)) {
            primoCarattere = testoOut.substring(0, 1).toLowerCase();
            testoOut = primoCarattere + testoOut.substring(1);
        }

        return testoOut.trim();
    }


    /**
     * Elimina dal testo il tagIniziale (compreso), se esiste. <br>
     * <p>
     * Esegue solo se il testo è valido <br>
     * Se tagIniziale è vuoto, restituisce il testo <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param testoIn     ingresso
     * @param tagIniziale da eliminare
     *
     * @return testo ridotto in uscita
     */
    public String levaTesta(final String testoIn, final String tagIniziale) {
        String testoOut = testoIn.trim();
        String tag;
        int pos;

        if (this.isValid(testoOut) && this.isValid(tagIniziale)) {
            tag = tagIniziale.trim();
            if (testoOut.startsWith(tag)) {
                pos = testoOut.indexOf(tagIniziale);
                if (pos>-1) {
                    testoOut = testoOut.substring(pos + tag.length());
                }
            }
        }

        return testoOut.trim();
    }

    /**
     * Elimina dal testo il tagFinale (compreso), se esiste. <br>
     * <p>
     * Esegue solo se il testo è valido <br>
     * Se tagFinale è vuoto o è uguale a testoIn oppure testoIn NON termina con tagFinale, restituisce testoIn intatto <br>
     * Elimina solo gli spazi vuoti finali e NON eventuali spazi vuoti iniziali <br>
     *
     * @param testoIn   stringa in ingresso
     * @param tagFinale da eliminare
     *
     * @return testo convertito
     */
    public String levaCoda(final String testoIn, final String tagFinale) {
        String testoOut = testoIn;

        if (this.isValid(testoOut) && this.isValid(tagFinale) && !tagFinale.trim().equals(testoIn.trim()) && !tagFinale.equals(BaseCost.SPAZIO)) {
            testoOut = StringUtils.stripEnd(testoIn, BaseCost.SPAZIO);
            if (testoOut.endsWith(tagFinale)) {
                testoOut = testoOut.substring(0, testoOut.length() - tagFinale.length());
                testoOut = StringUtils.stripEnd(testoOut, BaseCost.SPAZIO);
            }
            else {
                testoOut = testoIn;
            }
        }

        return testoOut;
    }

    /**
     * Elimina il testo dalla prima occorrenza di tagInterrompi (compreso) in poi <br>
     * <p>
     * Esegue solo se il testo è valido <br>
     * Se tagInterrompi è vuoto o non contenuto nella stringa, restituisce il testo originale intatto <br>
     * Elimina solo spazi vuoti finali e NON eventuali spazi vuoti iniziali <br>
     *
     * @param testoIn       stringa in ingresso
     * @param tagInterrompi da dove inizia il testo da eliminare
     *
     * @return test ridotto in uscita
     */
    public String levaCodaDaPrimo(final String testoIn, final String tagInterrompi) {
        String testoOut = testoIn;

        if (this.isValid(testoOut) && this.isValid(tagInterrompi) && !tagInterrompi.trim().equals(testoIn.trim()) && !tagInterrompi.equals(BaseCost.SPAZIO)) {
            testoOut = StringUtils.stripEnd(testoIn, BaseCost.SPAZIO);
            if (testoOut.contains(tagInterrompi)) {
                testoOut = testoOut.substring(0, testoOut.indexOf(tagInterrompi));
                testoOut = StringUtils.stripEnd(testoOut, BaseCost.SPAZIO);
            }
            else {
                testoOut = testoIn;
            }
        }

        return testoOut;
    }


    /**
     * Elimina il testo dall'ultima occorrenza di tagInterrompi (compreso) in poi <br>
     * <p>
     * Esegue solo se il testo è valido <br>
     * Se tagInterrompi è vuoto o non contenuto nella stringa, restituisce il testo originale intatto <br>
     * Elimina solo spazi vuoti finali e NON eventuali spazi vuoti iniziali <br>
     *
     * @param testoIn       stringa in ingresso
     * @param tagInterrompi da dove inizia il testo da eliminare
     *
     * @return test ridotto in uscita
     */
    public String levaCodaDaUltimo(final String testoIn, final String tagInterrompi) {
        String testoOut = testoIn;

        if (this.isValid(testoOut) && this.isValid(tagInterrompi) && !tagInterrompi.trim().equals(testoIn.trim()) && !tagInterrompi.equals(BaseCost.SPAZIO)) {
            testoOut = StringUtils.stripEnd(testoIn, BaseCost.SPAZIO);
            if (testoOut.contains(tagInterrompi)) {
                testoOut = testoOut.substring(0, testoOut.lastIndexOf(tagInterrompi));
                testoOut = StringUtils.stripEnd(testoOut, BaseCost.SPAZIO);
            }
            else {
                testoOut = testoIn;
            }
        }

        return testoOut;
    }

    /**
     * Sostituisce nel testo tutte le occorrenze di oldTag con newTag.
     * Esegue solo se il testo è valido
     * Esegue solo se il oldTag è valido
     * newTag può essere vuoto (per cancellare le occorrenze di oldTag)
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn ingresso da elaborare
     * @param oldTag  da sostituire
     * @param newTag  da inserire
     *
     * @return testo convertito
     */
    public String sostituisce(final String testoIn, final String oldTag, final String newTag) {
        String testoOut = testoIn;
        String prima = BaseCost.VUOTA;
        String rimane = testoIn;
        int pos = 0;
        String charVuoto = BaseCost.SPAZIO;

        if (this.isValid(testoIn) && this.isValid(oldTag)) {
            if (rimane.contains(oldTag)) {
                pos = rimane.indexOf(oldTag);

                while (pos != -1) {
                    pos = rimane.indexOf(oldTag);
                    if (pos != -1) {
                        prima += rimane.substring(0, pos);
                        prima += newTag;
                        pos += oldTag.length();
                        rimane = rimane.substring(pos);
                        if (prima.endsWith(charVuoto) && rimane.startsWith(charVuoto)) {
                            rimane = rimane.substring(1);
                        }
                    }
                }

                testoOut = prima + rimane;
            }
        }

        return testoOut.trim();
    }


    /**
     * Elimina tutti i caratteri contenuti nella stringa. <br>
     * Esegue solo se il testo è valido <br>
     *
     * @param testoIn    stringa in ingresso
     * @param subStringa da eliminare
     *
     * @return testoOut stringa convertita
     */
    public String levaTesto(final String testoIn, final String subStringa) {
        String testoOut = testoIn;

        if (testoIn != null && subStringa != null) {
            testoOut = testoIn.trim();
            if (testoOut.contains(subStringa)) {
                testoOut = sostituisce(testoOut, subStringa, BaseCost.VUOTA);
            }
        }

        return testoOut;
    }

    /**
     * Elimina tutte le virgole contenute nella stringa. <br>
     * Esegue solo se la stringa è valida <br>
     * Se arriva un oggetto non stringa, restituisce l'oggetto <br>
     *
     * @param testoIn stringa in ingresso
     *
     * @return testo convertito
     */
    public String levaVirgole(final String testoIn) {
        return levaTesto(testoIn, BaseCost.VIRGOLA);
    }


    /**
     * Elimina tutti i punti contenuti nella stringa. <br>
     * Esegue solo se la stringa è valida <br>
     * Se arriva un oggetto non stringa, restituisce l'oggetto <br>
     *
     * @param testoIn stringa in ingresso
     *
     * @return testo convertito
     */
    public String levaPunti(final String testoIn) {
        return levaTesto(testoIn, BaseCost.PUNTO);
    }


    /**
     * Elimina gli spazi della stringa <br>
     *
     * @param stringaIn ingresso
     *
     * @return etichetta visualizzata
     */
    public String levaSpazi(String stringaIn) {
        String stringaOut = stringaIn;
        String tag = BaseCost.SPAZIO;

        if (stringaIn.contains(tag)) {
            stringaOut = stringaIn.replaceAll(tag, BaseCost.VUOTA);
        }

        return stringaOut;
    }


    /**
     * Sostituisce gli slash con punti. <br>
     * <p>
     * NON sostituisce lo slash iniziale (se esiste) <br>
     * Elimina lo slash finale (se esiste) <br>
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn stringa in ingresso
     *
     * @return testo convertito
     */
    public String slashToPoint(final String testoIn) {
        String testoOut = testoIn.trim();

        if (testoOut.endsWith(SLASH)) {
            testoOut = levaCoda(testoOut, SLASH);
        }

        if (testoOut.startsWith(SLASH)) {
            testoOut = testoOut.substring(SLASH.length());
            return SLASH + sostituisce(testoOut, SLASH, PUNTO);
        }
        else {
            return sostituisce(testoOut, SLASH, PUNTO);
        }
    }


    /**
     * Sostituisce i punti con slash. <br>
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn stringa in ingresso
     *
     * @return testo convertito
     */
    public String pointToSlash(final String testoIn) {
        return sostituisce(testoIn, PUNTO, SLASH);
    }

    /**
     * Costruisce un array da una stringa di valori multipli separati da virgole. <br>
     * <p>
     * Se la stringa è nulla, ritorna un array vuoto <br>
     * Se la stringa è vuota, ritorna un array vuoto <br>
     * Se manca la virgola, ritorna un array di un solo valore col testo completo <br>
     * Elimina spazi vuoti iniziali e finali di ogni valore <br>
     *
     * @param stringaMultipla in ingresso
     *
     * @return lista di singole stringhe
     */
    public List<String> getArray(final String stringaMultipla) {
        List<String> lista = new ArrayList<>();
        String tag = VIRGOLA;
        String[] parti;
        String value;

        if (isEmpty(stringaMultipla)) {
            return lista;
        }

        if (stringaMultipla.contains(tag)) {
            parti = stringaMultipla.split(tag);
            for (String parte : parti) {
                value = parte.trim();
                if (isValid(value)) {
                    lista.add(value);
                }
            }
        }
        else {
            value = stringaMultipla.trim();
            if (isValid(value)) {
                lista.add(value);
            }
        }

        return lista;
    }


    /**
     * Formattazione di un numero. <br>
     * <p>
     * Il numero può arrivare come stringa, intero o double <br>
     * Se la stringa contiene punti e virgole, viene pulita <br>
     * Se la stringa non è convertibile in numero, viene restituita uguale <br>
     * Inserisce il punto separatore ogni 3 cifre <br>
     * Se arriva un oggetto non previsto, restituisce null <br>
     *
     * @param numObj da formattare (stringa, intero, long o double)
     *
     * @return testo formattato
     */
    public String format(final Object numObj) {
        String formattato;
        String numText = BaseCost.VUOTA;
        String sep = BaseCost.PUNTO;
        int len;
        String num3;
        String num6;
        String num9;
        String num12;

        if (numObj instanceof String || numObj instanceof Integer || numObj instanceof Long || numObj instanceof Double || numObj instanceof List || numObj instanceof Object[]) {
            if (numObj instanceof String) {
                numText = (String) numObj;
                numText = levaVirgole(numText);
                numText = levaPunti(numText);
                try {
                    Integer.decode(numText);
                } catch (Exception unErrore) {
                    return (String) numObj;
                }
            }
            else {
                if (numObj instanceof Integer) {
                    numText = Integer.toString((int) numObj);
                }
                if (numObj instanceof Long) {
                    numText = Long.toString((long) numObj);
                }
                if (numObj instanceof Double) {
                    numText = Double.toString((double) numObj);
                }
                if (numObj instanceof List) {
                    numText = Integer.toString((int) ((List) numObj).size());
                }
                if (numObj instanceof Object[]) {
                    numText = Integer.toString(((Object[]) numObj).length);
                }
            }
        }
        else {
            return null;
        }

        formattato = numText;
        len = numText.length();
        if (len > 3) {
            num3 = numText.substring(0, len - 3);
            num3 += sep;
            num3 += numText.substring(len - 3);
            formattato = num3;
            if (len > 6) {
                num6 = num3.substring(0, len - 6);
                num6 += sep;
                num6 += num3.substring(len - 6);
                formattato = num6;
                if (len > 9) {
                    num9 = num6.substring(0, len - 9);
                    num9 += sep;
                    num9 += num6.substring(len - 9);
                    formattato = num9;
                    if (len > 12) {
                        num12 = num9.substring(0, len - 12);
                        num12 += sep;
                        num12 += num9.substring(len - 12);
                        formattato = num12;
                    }
                }
            }
        }

        //--valore di ritorno
        return formattato;
    }

    /**
     * Elimina il testo prima di tagIniziale. <br>
     * <p>
     * Esegue solo se il testo è valido <br>
     * Se tagIniziale è vuoto, restituisce il testo <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param testoIn     ingresso
     * @param tagIniziale da dove inizia il testo da tenere
     *
     * @return testo ridotto in uscita
     */
    public String levaTestoPrimaDi(final String testoIn, final String tagIniziale) {
        String testoOut = testoIn.trim();
        String tag;

        if (this.isValid(testoOut) && this.isValid(tagIniziale)) {
            tag = tagIniziale.equals(CAPO) ? tagIniziale : tagIniziale.trim();
            if (testoOut.contains(tag)) {
                testoOut = testoOut.substring(testoOut.indexOf(tag));
            }
        }

        return testoOut.trim();
    }

    /**
     * Elimina il testo prima di tagIniziale, lasciando il tag. <br>
     * <p>
     * Esegue solo se il testo è valido <br>
     * Se tagIniziale è vuoto, restituisce il testo <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param testoIn     ingresso
     * @param tagIniziale da dove inizia il testo da tenere
     *
     * @return testo ridotto in uscita
     */
    public String levaPrimaDelTag(final String testoIn, final String tagIniziale) {
        String testoOut = testoIn.trim();
        String tag;

        if (this.isValid(testoOut) && this.isValid(tagIniziale)) {
            tag = tagIniziale.equals(CAPO) ? tagIniziale : tagIniziale.trim();
            if (testoOut.contains(tag)) {
                testoOut = testoOut.substring(testoOut.indexOf(tag));
            }
        }

        return testoOut.trim();
    }
    /**
     * Elimina il testo prima di tagIniziale, levando anche il tag. <br>
     * <p>
     * Esegue solo se il testo è valido <br>
     * Se tagIniziale è vuoto, restituisce il testo <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param testoIn     ingresso
     * @param tagIniziale da dove inizia il testo da tenere
     *
     * @return testo ridotto in uscita
     */
    public String levaPrimaAncheTag(final String testoIn, final String tagIniziale) {
        String testoOut = testoIn.trim();

        if (this.isValid(testoOut) && this.isValid(tagIniziale)) {
            testoOut = this.levaTestoPrimaDi(testoOut, tagIniziale);
            testoOut = this.levaTesta(testoOut, tagIniziale);
        }

        return testoOut.trim();
    }

    public String estraeCon(String valueIn, String tagIni, String tagEnd) {
        String valueOut = valueIn;
        int length = 0;
        int posIni = 0;
        int posEnd = 0;

        if (isValid(valueIn) && valueIn.contains(tagIni) && valueIn.contains(tagEnd)) {
            length = tagIni.length();
            posIni = valueIn.indexOf(tagIni);
            posEnd = valueIn.indexOf(tagEnd, posIni + length);
            valueOut = valueIn.substring(posIni, posEnd + tagEnd.length());
        }

        return valueOut.trim();
    }

    public String estraeSenza(String valueIn, String tagIni, String tagEnd) {
        String valueOut = valueIn;
        int length = 0;
        int posIni = 0;
        int posEnd = 0;

        if (isValid(valueIn) && valueIn.contains(tagIni) && valueIn.contains(tagEnd)) {
            length = tagIni.length();
            posIni = valueIn.indexOf(tagIni);
            posEnd = valueIn.indexOf(tagEnd, posIni + length);
            valueOut = valueIn.substring(posIni + length, posEnd);
        }

        return valueOut.trim();
    }


    /**
     * Allunga un testo alla lunghezza desiderata. <br>
     * Se è più corta, aggiunge spazi vuoti <br>
     * Se è più lungo, rimane inalterato <br>
     * La stringa in ingresso viene 'giustificata' a sinistra <br>
     * Vengono eliminati gli spazi vuoti che precedono la stringa <br>
     * La lunghezza è come minimo quella indicata, ma potrebbe essere maggiore <br>
     *
     * @param testoIn stringa in ingresso
     * @param length  minima della stringa
     *
     * @return testo almeno della 'lunghezza' richiesta
     */
    public String rightPad(final String testoIn, int length) {
        return StringUtils.rightPad(testoIn.trim(), length);
    }


    /**
     * Forza un testo alla lunghezza desiderata. <br>
     * Se è più corta, aggiunge spazi vuoti <br>
     * Se è più lungo, lo tronca <br>
     * La stringa in ingresso viene 'giustificata' a sinistra <br>
     * Vengono eliminati gli spazi vuoti che precedono la stringa <br>
     * La lunghezza è sempre quella indicata <br>
     *
     * @param testoIn stringa in ingresso
     * @param length  finale della stringa
     *
     * @return testo sempre della 'lunghezza' richiesta
     */

    public String fixSize(final String testoIn, int length) {
        String testoOut = rightPad(testoIn, length);

        if (testoOut.length() > length) {
            testoOut = testoOut.substring(0, length);
        }

        return testoOut;
    }


    /**
     * Forza un testo alla lunghezza desiderata e aggiunge singole parentesi quadre in testa e coda. <br>
     * Se arriva una stringa vuota, restituisce una stringa vuota con singole parentesi quadre aggiunte <br>
     *
     * @param testoIn stringa in ingresso
     *
     * @return stringa con lunghezza prefissata e singole parentesi quadre aggiunte
     */
    public String fixSizeQuadre(final String testoIn, final int size) {
        String stringaOut = testoIn;
        stringaOut = fixSize(stringaOut, size);

        stringaOut = QUADRA_INI + stringaOut + QUADRA_END;
        return stringaOut.trim();
    }

    public String trim(String testoIn) {
        if (testoIn.startsWith(CAPO_REGEX)) {
            testoIn = this.levaTesta(testoIn, CAPO_REGEX);
        }
        if (testoIn.startsWith(CAPO_SPLIT)) {
            testoIn = this.levaTesta(testoIn, CAPO_SPLIT);
        }

        if (testoIn.endsWith(CAPO_REGEX)) {
            testoIn = this.levaCoda(testoIn, CAPO_REGEX);
        }
        if (testoIn.endsWith(CAPO_SPLIT)) {
            testoIn = this.levaCoda(testoIn, CAPO_SPLIT);
        }

        return testoIn.trim();
    }

    /**
     * Elimina (eventuali) graffe doppie in testa e coda della stringa.
     * Funziona solo se le graffe sono esattamente in TESTA e in CODA alla stringa
     * Se arriva una stringa vuota, restituisce una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     *
     * @param stringaIn in ingresso
     *
     * @return stringa con doppie graffe eliminate
     */
    public String setNoDoppieGraffe(String stringaIn) {
        String stringaOut = stringaIn;

        if (isValid(stringaIn)) {
            stringaIn = stringaIn.trim();

            if (stringaIn.startsWith(DOPPIE_GRAFFE_INI) && stringaIn.endsWith(DOPPIE_GRAFFE_END)) {
                stringaOut = stringaIn;
                stringaOut = levaCoda(stringaOut, DOPPIE_GRAFFE_END);
                stringaOut = levaTesta(stringaOut, DOPPIE_GRAFFE_INI);
            }
        }

        return stringaOut.trim();
    }


    /**
     * Elimina (eventuali) quadre doppie in testa e coda della stringa.
     * Funziona solo se le quadre sono esattamente in TESTA e in CODA alla stringa
     * Se arriva una stringa vuota, restituisce una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     *
     * @param stringaIn in ingresso
     *
     * @return stringa con doppie quadre eliminate
     */
    public String setNoDoppieQuadre(String stringaIn) {
        String stringaOut = stringaIn;

        if (isValid(stringaIn)) {
            stringaIn = stringaIn.trim();

            if (stringaIn.startsWith(DOPPIE_QUADRE_INI) && stringaIn.endsWith(DOPPIE_QUADRE_END)) {
                stringaOut = stringaIn;
                stringaOut = levaCoda(stringaOut, DOPPIE_QUADRE_END);
                stringaOut = levaTesta(stringaOut, DOPPIE_QUADRE_INI);
            }
        }

        //--se c'erano delle quadre interne, non elimina quelle esterne in quanto non è un singolo link
        if (stringaOut.contains(DOPPIE_QUADRE_INI) && stringaOut.contains(DOPPIE_QUADRE_END)) {
            stringaOut = stringaIn;
        }

        return stringaOut.trim();
    }


    /**
     * Elimina (eventuali) 'doppi apici' " in testa ed in coda alla stringa. <br>
     * Se arriva una stringa vuota, restituisce una stringa vuota <br>
     *
     * @param stringaIn in ingresso
     *
     * @return stringa senza doppi apici iniziali e finali
     */
    public String setNoDoppiApici(String stringaIn) {
        String stringaOut = stringaIn.trim();
        String doppioApice = "\"";
        int cicli = 4;

        if (this.isValid(stringaOut)) {
            for (int k = 0; k < cicli; k++) {
                stringaOut = this.levaTesta(stringaOut, doppioApice);
                stringaOut = this.levaCoda(stringaOut, doppioApice);
            }
        }

        return stringaOut.trim();
    }

}