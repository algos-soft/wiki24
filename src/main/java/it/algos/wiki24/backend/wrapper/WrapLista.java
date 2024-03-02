package it.algos.wiki24.backend.wrapper;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.service.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.service.*;
import org.checkerframework.checker.units.qual.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 26-Feb-2024
 * Time: 21:26
 */
@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class WrapLista {

    @Inject
    private TextService textService;

    @Inject
    WikiUtilityService wikiUtilityService;

    private String titoloPagina;

    private String keyParagrafo;

    private int sogliaSottoPagina;

//    private boolean usaTitoloParagrafo;

    private boolean usaRinvio;

    private List<WrapDidascalia> lista = new ArrayList<>();

    private Map<String, WrapLista> mappa = new HashMap<>();


    public WrapLista(String titoloPagina, String keyParagrafo) {
        this(titoloPagina, keyParagrafo, MAX_INT_VALUE);
    }

    public WrapLista(String titoloPagina, String keyParagrafo, int sogliaSottoPagina) {
        this.titoloPagina = titoloPagina;
        this.keyParagrafo = keyParagrafo;
        this.sogliaSottoPagina = sogliaSottoPagina;
//        this.usaTitoloParagrafo = false;
        this.usaRinvio = false;
    }

//    /**
//     * Fluent pattern Builder <br>
//     */
//    public WrapLista usaTitoloParagrafo() {
//        return usaTitoloParagrafo(true);
//    }

//    /**
//     * Fluent pattern Builder <br>
//     */
//    public WrapLista usaTitoloParagrafo(boolean usaTitoloParagrafo) {
//        this.usaTitoloParagrafo = usaTitoloParagrafo;
//        return this;
//    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLista usaRinvio() {
        return usaRinvio(true);
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLista usaRinvio(boolean usaRinvio) {
        this.usaRinvio = usaRinvio;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLista sogliaSottoPagina(int sogliaSottoPagina) {
        this.sogliaSottoPagina = sogliaSottoPagina;
        return this;
    }

    public void add(WrapDidascalia wrap) {
        this.lista.add(wrap);
    }

    public String getRinvio() {
        String sottoPagina = String.format("%s%s%s", textService.primaMaiuscola(titoloPagina), SLASH, keyParagrafo);
        return String.format("{{Vedi anche|%s}}", sottoPagina) + CAPO;
    }


    public List<WrapDidascalia> getLista() {
        return lista;
    }

//    public boolean isUsaTitoloParagrafo() {
//        return usaTitoloParagrafo;
//    }

    public boolean isUsaRinvio() {
        return usaRinvio;
    }

    public String getKeyParagrafo() {
        return keyParagrafo + CAPO;
    }

    public int getNumBio() {
        return lista != null ? lista.size() : mappa != null ? wikiUtilityService.getSizeMappa(mappa) : 0;
    }

    public void setLista(List<WrapDidascalia> lista) {
        this.lista = lista;
    }

    public int getSogliaSottoPagina() {
        return sogliaSottoPagina;
    }

    public Map<String, WrapLista> getMappa() {
        return mappa;
    }

}
