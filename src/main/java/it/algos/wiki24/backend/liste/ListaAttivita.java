package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Fri, 03-Jun-2022
 * Time: 16:08
 * <p>
 * Lista delle biografie per attività <br>
 * <p>
 * La lista è una mappa di WrapLista suddivisa in paragrafi, che contiene tutte le informazioni per scrivere le righe della pagina <br>
 * Usata fondamentalmente da UploadAttivita con appContext.getBean(ListaAttivita.class).plurale(nomeAttività).mappaWrap() <br>
 * Il costruttore è senza parametri e serve solo per preparare l'istanza che viene ''attivata'' con plurale(nomeAttività) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ListaAttivita extends Lista {


    /**
     * Costruttore base con 1 parametro (obbligatorio) <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(ListaNomi.class, nomeLista) <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaAttivita(String nomeLista) {
        super(nomeLista);
    }// end of constructor not @Autowired and used

    /**
     * Costruttore base <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(ListaGiorni.class, nomeLista) <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaAttivita(final AttSingolare attivitaSingolare) {
        super(attivitaSingolare.nome);
        super.typeLista = AETypeLista.attivitaSingolare;
    }

    /**
     * Costruttore base <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(ListaGiorni.class, nomeLista) <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaAttivita(final AttPlurale attivitaPlurale) {
        super(attivitaPlurale.nome);
        super.typeLista = AETypeLista.attivitaPlurale;
    }

    protected void fixPreferenze() {
        super.fixPreferenze();

        super.backend = super.attPluraleBackend;
        super.nomeLista = textService.primaMaiuscola(nomeLista);
        super.titoloPagina = wikiUtility.wikiTitleAttivita(nomeLista);
        super.typeLista = AETypeLista.attivitaPlurale;
        super.typeLinkParagrafi = (AETypeLink) WPref.linkParametriAttNaz.getEnumCurrentObj();
        super.paragrafoAltre = TAG_LISTA_NO_NAZIONALITA;
        super.patternCompleto = true;

        if (typeLista == AETypeLista.attivitaPlurale) {
            AttPlurale attivitaPlurale = attPluraleBackend.findByKey(textService.primaMinuscola(nomeLista));
            listaNomiSingoli = attivitaPlurale != null ? attivitaPlurale.listaSingolari.stream().map(att -> att.nome).collect(Collectors.toList()) : null;
        }

    }

    /**
     * Pattern Builder <br>
     */
    public ListaAttivita typeLista(AETypeLista typeLista) {
        super.patternCompleto = false;
        return switch (typeLista) {
            case attivitaSingolare -> singolare();
            case attivitaPlurale -> plurale();
            default -> this;
        };
    }

    /**
     * Pattern Builder <br>
     */
    public ListaAttivita singolare() {
        super.patternCompleto = true;
        return (ListaAttivita) super.typeLista(AETypeLista.attivitaSingolare);
    }

    /**
     * Pattern Builder <br>
     */
    public ListaAttivita plurale() {
        super.patternCompleto = true;
        return (ListaAttivita) super.typeLista(AETypeLista.attivitaPlurale);
    }

    /**
     * Lista ordinata di tutti i wrapLista che hanno una valore valido per la pagina specifica <br>
     */
    public List<WrapLista> listaWrap() {
        listaWrap = new ArrayList<>();
        WrapLista wrap;
        AttSingolare attSingolare;
        String singolare;
        String plurale;
        String titoloPaginaSingolare = NULL;

        if (listaBio == null || listaBio.size() == 0) {
            listaBio();
        }

        if (listaBio != null && listaBio.size() > 0) {
            if (typeLinkParagrafi == null) {
                logger.error(new WrapLog().exception(new AlgosException("Manca typeLinkParagrafi")));
                return null;
            }
            if (typeLinkCrono == null) {
                logger.error(new WrapLog().exception(new AlgosException("Manca typeLinkCrono")));
                return null;
            }

            if (typeLista == AETypeLista.attivitaSingolare) {
                singolare = this.nomeLista;
                attSingolare = attSingolareBackend.findByKey(textService.primaMinuscola(singolare));
                plurale = attSingolare != null ? attSingolare.plurale : NULL;
                titoloPaginaSingolare = singolare + FORWARD + wikiUtility.wikiTitleAttivita(plurale);
            }

            for (Bio bio : listaBio) {
                if (typeLista == AETypeLista.attivitaSingolare) {
                    wrap = didascaliaService.getWrap(titoloPaginaSingolare, bio, typeLista, typeLinkParagrafi, typeLinkCrono, usaIcona);
                }
                else {
                    wrap = didascaliaService.getWrap(titoloPagina, bio, typeLista, typeLinkParagrafi, typeLinkCrono, usaIcona);
                }

                if (wrap != null) {
                    listaWrap.add(wrap);
                }
            }
        }

        return listaWrap;
    }

    /**
     * Ordina la mappa <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    public LinkedHashMap<String, List<WrapLista>> sortMap(LinkedHashMap<String, List<WrapLista>> mappa) {
        return wikiUtility.sort(mappa);
    }

}
