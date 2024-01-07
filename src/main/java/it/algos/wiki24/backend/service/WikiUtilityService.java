package it.algos.wiki24.backend.service;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.service.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 03-Jan-2024
 * Time: 09:59
 * Classe di libreria. NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 *
 * @Inject public WikiUtilityService WikiUtilityService; (iniziale minuscola) <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class WikiUtilityService {

    @Inject
    TextService textService;

    @Inject
    RegexService regexService;

    /**
     * I numeri che iniziano (parlato) con vocale richiedono l'apostrofo  <br>
     * Sono:
     * 8
     * 11
     *
     * @param tag         nati/morti
     * @param textMatcher di riferimento
     *
     * @return titolo della pagina wiki
     */
    public String natiMortiGiorno(String tag, String textMatcher) {
        String tagBase = tag + SPAZIO + "il" + SPAZIO;
        String tagSpecifico = tag + SPAZIO + "l'";
        String textPattern = "^8 .+|^11 .+";

        if (textService.isEmpty(textMatcher)) {
            return VUOTA;
        }

        if (regexService.isEsiste(textMatcher, textPattern)) {
            return tagSpecifico + textMatcher;
        }
        else {
            return tagBase + textMatcher;
        }
    }


    /**
     * I numeri che iniziano (parlato) con vocale richiedono l'apostrofo  <br>
     * Sono:
     * 1
     * 11
     * tutti quelli che iniziano con 8
     *
     * @param tag         nati/morti
     * @param textMatcher di riferimento
     *
     * @return titolo della pagina wiki
     */
    private String natiMortiAnno(String tag, String textMatcher) {
        String tagBase = tag + SPAZIO + "nel" + SPAZIO;
        String tagSpecifico = tag + SPAZIO + "nell'";
        String textPattern = "^1$|^11$|^1 *a\\.C\\.|^11 *a\\.C\\.|^8.*";

        if (textService.isEmpty(textMatcher)) {
            return VUOTA;
        }

        if (regexService.isEsiste(textMatcher, textPattern)) {
            return tagSpecifico + textMatcher;
        }
        else {
            return tagBase + textMatcher;
        }
    }

    public String wikiTitleNatiGiorno(String giorno) {
        return natiMortiGiorno("Nati", giorno);
    }

    public String wikiTitleMortiGiorno(String giorno) {
        return natiMortiGiorno("Morti", giorno);
    }

    public String wikiTitleNatiAnno(String anno) {
        return natiMortiAnno("Nati", anno);
    }

    public String wikiTitleMortiAnno(String anno) {
        return natiMortiAnno("Morti", anno);
    }

    /**
     * Inserisce un numero in caratteri ridotti <br>
     *
     * @param numero da visualizzare
     *
     * @return testo coi tag html
     */
    public String smallNumero(final int numero) {
        String testo = VUOTA;

        testo += "<span style=\"font-size:70%\">(";
        testo += numero;
        testo += ")</span>";

        return testo;
    }

    /**
     * Inserisce un numero in caratteri ridotti <br>
     *
     * @param titolo da inglobare nei tag wiki (paragrafo)
     *
     * @return testo coi tag html
     */
    public String setParagrafo(final String titolo) {
        return setParagrafo(titolo, 0);
    }

    /**
     * Inserisce un numero in caratteri ridotti <br>
     *
     * @param titolo da inglobare nei tag wiki (paragrafo)
     * @param numero da visualizzare (maggiore di zero)
     *
     * @return testo coi tag html
     */
    public String setParagrafo(final String titolo, final int numero) {
        StringBuffer buffer = new StringBuffer();
        String titoloUpperCase = textService.primaMaiuscola(titolo);

        buffer.append(CAPO);
        buffer.append(PARAGRAFO);
        buffer.append(SPAZIO);
        buffer.append(titoloUpperCase);

        if (numero > 0) {
            buffer.append(SPAZIO);
            buffer.append(smallNumero(numero));
        }
        buffer.append(SPAZIO);
        buffer.append(PARAGRAFO);
        buffer.append(CAPO);

        return buffer.toString();
    }

    public String setParagrafoIncludeOnly(final String titolo, final int numero) {
        StringBuffer buffer = new StringBuffer();
        String titoloUpperCase = textService.primaMaiuscola(titolo);

        buffer.append(CAPO);
        buffer.append(PARAGRAFO_INCLUDE_INI);
        buffer.append(SPAZIO);
        buffer.append(titoloUpperCase);

        if (numero > 0) {
            buffer.append(SPAZIO);
            buffer.append(NO_INCLUDE_INI);
            buffer.append(smallNumero(numero));
            buffer.append(NO_INCLUDE_END);
        }
        buffer.append(SPAZIO);
        buffer.append(PARAGRAFO_INCLUDE_END);
        buffer.append(CAPO);

        return buffer.toString();
    }

}// end of Service class