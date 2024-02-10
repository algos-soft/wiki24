package it.algos.wiki24.backend.wrapper;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.mese.*;
import it.algos.base24.backend.packages.crono.secolo.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.tabelle.attsingolare.*;
import it.algos.wiki24.backend.packages.tabelle.nazsingolare.*;
import it.algos.wiki24.backend.service.*;
import jakarta.annotation.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 03-Jan-2024
 * Time: 12:18
 */
@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class WrapDidascalia {

    @Inject
    private DidascaliaService didascaliaService;

    @Inject
    private TextService textService;

    @Inject
    MeseModulo meseModulo;

    @Inject
    SecoloModulo secoloModulo;

    @Inject
    NazSingolareModulo nazSingolareModulo;

    @Inject
    AttSingolareModulo attSingolareModulo;

    @Inject
    LogService logger;

    private String primoLivello;

    private String secondoLivello;

    private String terzoLivello;

    private String quartoLivello;

    private String didascalia;

    private BioMongoEntity bio;

    private TypeLista type;

    int ordinamento;

    public WrapDidascalia() {
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapDidascalia type(TypeLista type) {
        this.type = type;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapDidascalia giornoNascita() {
        this.type = TypeLista.giornoNascita;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapDidascalia giornoMorte() {
        this.type = TypeLista.giornoMorte;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapDidascalia annoNascita() {
        this.type = TypeLista.annoNascita;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapDidascalia annoMorte() {
        this.type = TypeLista.annoMorte;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapDidascalia attivita() {
        this.type = TypeLista.attivitaPlurale;
        return this;
    }

    public WrapDidascalia get(BioMongoEntity bio) {
        didascalia = switch (type) {
            case giornoNascita -> didascaliaService.didascaliaGiornoNato(bio);
            case giornoMorte -> didascaliaService.didascaliaGiornoMorto(bio);
            case annoNascita -> didascaliaService.didascaliaAnnoNato(bio);
            case annoMorte -> didascaliaService.didascaliaAnnoMorto(bio);
            case attivitaSingolare -> didascaliaService.didascaliaLista(bio);
            case attivitaPlurale -> didascaliaService.didascaliaLista(bio);
            case nazionalitaSingolare -> didascaliaService.didascaliaLista(bio);
            case nazionalitaPlurale -> didascaliaService.didascaliaLista(bio);
            default -> VUOTA;
        };

        fixOrdinamento(bio);
        fixPrimoLivello(bio);
        fixSecondoLivello(bio);
        fixTerzoLivello(bio);
        fixQuartoLivello(bio);
        return this;
    }

    public void fixOrdinamento(BioMongoEntity bio) {
        ordinamento = switch (type) {
            case giornoNascita -> bio.annoNatoOrd;
            case giornoMorte -> bio.annoMortoOrd;
            case annoNascita -> 0;
            case annoMorte -> 0;
            default -> 0;
        };
    }

    public void fixPrimoLivello(BioMongoEntity bio) {
        primoLivello = switch (type) {
            case giornoNascita -> getTitoloParagrafoSecoloNato(bio);
            case giornoMorte -> getTitoloParagrafoSecoloMorto(bio);
            case annoNascita -> getTitoloParagrafoMeseNato(bio);
            case annoMorte -> getTitoloParagrafoMeseMorto(bio);
            case attivitaSingolare, attivitaPlurale -> getTitoloParagrafoNazionalita(bio);
            case nazionalitaSingolare, nazionalitaPlurale -> getTitoloParagrafoAttivita(bio);
            default -> VUOTA;
        };
    }

    public void fixSecondoLivello(BioMongoEntity bio) {
        secondoLivello = switch (type) {
            case giornoNascita -> didascaliaService.getDecade(bio.annoNato);
            case giornoMorte -> didascaliaService.getDecade(bio.annoMorto);
            case annoNascita, annoMorte -> VUOTA;
            case attivitaSingolare, attivitaPlurale -> getSecondoLivelloAlfabetico(bio);
            case nazionalitaSingolare, nazionalitaPlurale -> getSecondoLivelloAlfabetico(bio);
            default -> VUOTA;
        };
    }

    public void fixTerzoLivello(BioMongoEntity bio) {
        terzoLivello = switch (type) {
            case giornoNascita -> bio.annoNato;
            case giornoMorte -> bio.annoMorto;
            case annoNascita -> bio.giornoNato;
            case annoMorte -> bio.giornoMorto;
            default -> VUOTA;
        };
    }

    public void fixQuartoLivello(BioMongoEntity bio) {
        quartoLivello = switch (type) {
            case giornoNascita -> bio.giornoNato;
            case giornoMorte -> bio.giornoMorto;
            case annoNascita -> bio.annoNato;
            case annoMorte -> bio.annoMorto;
            default -> VUOTA;
        };
    }

    public String getTitoloParagrafoSecoloNato(BioMongoEntity bio) {
        String titoloParagrafo = "Senza anno specificato";
        SecoloEntity secoloBean;
        String anno = bio.annoNato;

        if (textService.isEmpty(anno)) {
            return titoloParagrafo;
        }

        secoloBean = secoloModulo.getSecolo(anno);
        return secoloBean != null ? secoloBean.nome : titoloParagrafo;
    }


    public String getTitoloParagrafoSecoloMorto(BioMongoEntity bio) {
        String titoloParagrafo = "Senza anno specificato";
        SecoloEntity secoloBean;
        String anno = bio.annoMorto;

        if (textService.isEmpty(anno)) {
            return titoloParagrafo;
        }

        secoloBean = secoloModulo.getSecolo(anno);
        return secoloBean != null ? secoloBean.nome : titoloParagrafo;
    }


    public String getTitoloParagrafoMeseNato(BioMongoEntity bio) {
        String titoloParagrafo = "Senza giorno specificato";
        String giorno = bio.giornoNato;

        if (textService.isEmpty(giorno)) {
            return titoloParagrafo;
        }

        if (giorno.contains(SPAZIO)) {
            titoloParagrafo = textService.levaTesta(giorno, SPAZIO);
        }
        else {
            return titoloParagrafo;
        }

        return textService.primaMaiuscola(titoloParagrafo);
    }


    public String getTitoloParagrafoMeseMorto(BioMongoEntity bio) {
        String titoloParagrafo = "Senza giorno specificato";
        String giorno = bio.giornoMorto;

        if (textService.isEmpty(giorno)) {
            return titoloParagrafo;
        }

        if (giorno.contains(SPAZIO)) {
            titoloParagrafo = textService.levaTesta(giorno, SPAZIO);
        }
        else {
            return titoloParagrafo;
        }

        return textService.primaMaiuscola(titoloParagrafo);
    }

    public String getTitoloParagrafoNazionalita(BioMongoEntity bio) {
        String titoloParagrafo = "Senza nazionalità specificata";
        String nazionalita = bio.nazionalita;
        NazSingolareEntity nazionalitaBean;

        if (textService.isEmpty(nazionalita)) {
            return titoloParagrafo;
        }
        else {
            nazionalitaBean = nazSingolareModulo.findOneById(nazionalita);
            if (nazionalitaBean != null) {
                return textService.primaMaiuscola(nazionalitaBean.plurale);
            }
            else {
                return titoloParagrafo;
            }
        }
    }


    public String getTitoloParagrafoAttivita(BioMongoEntity bio) {
        String titoloParagrafo = "Senza attività specificata";
        String attivita = bio.attivita;
        AttSingolareEntity attivitaBean;

        if (textService.isEmpty(attivita)) {
            return titoloParagrafo;
        }
        else {
            attivitaBean = attSingolareModulo.findOneById(attivita);
            if (attivitaBean != null) {
                return textService.primaMaiuscola(attivitaBean.plurale);
            }
            else {
                return titoloParagrafo;
            }
        }
    }


    public String getSecondoLivelloAlfabetico(BioMongoEntity bio) {
        String secondoLivello = VUOTA;
        String cognome = bio.cognome;
        String wikiTitle = bio.wikiTitle;

        if (textService.isValid(cognome)) {
            secondoLivello = cognome.substring(0, 1).toUpperCase();
        }
        else {
            if (textService.isValid(wikiTitle)) {
                secondoLivello = wikiTitle.substring(0, 1).toUpperCase();
            }
            else {
                logger.warn(new WrapLog().message("Manca il wikiTitle"));
            }
        }

        return secondoLivello;
    }

    public TypeLista getType() {
        return type;
    }

    public String getDidascalia() {
        return didascalia;
    }

    public String getPrimoLivello() {
        return primoLivello;
    }

    public String getSecondoLivello() {
        return secondoLivello;
    }

    public String getTerzoLivello() {
        return terzoLivello;
    }

    public String getQuartoLivello() {
        return quartoLivello;
    }

    public int getOrdinamento() {
        return ordinamento;
    }

}
