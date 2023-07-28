package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Tue, 14-Jun-2022
 * Time: 07:06
 * Lista delle biografie per nazionalità <br>
 * * <p>
 * La lista è una mappa di WrapLista suddivisa in paragrafi, che contiene tutte le informazioni per scrivere le righe della pagina <br>
 * Usata fondamentalmente da UploadNazionalita con appContext.getBean(ListaNazionalita.class).plurale(nomeNazionalità).mappaWrap() <br>
 * Il costruttore è senza parametri e serve solo per preparare l'istanza che viene ''attivata'' con plurale(nomeNazionalità) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ListaNazionalita extends Lista {


    /**
     * Costruttore base con 1 parametro (obbligatorio) <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(ListaNomi.class, nomeLista) <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaNazionalita(String nomeLista) {
        super(nomeLista);
    }// end of constructor not @Autowired and used


    public ListaNazionalita nazionalita(final NazSingolare nazionalitaSingolare) {
        this.nomeLista = nazionalitaSingolare.nome;
        super.typeLista = AETypeLista.nazionalitaSingolare;
        return this;
    }

    public ListaNazionalita nazionalita(final NazPlurale nazionalitaPlurale) {
        this.nomeLista = nazionalitaPlurale.nome;
        super.typeLista = AETypeLista.nazionalitaPlurale;
        return this;
    }

    protected void fixPreferenze() {
        super.fixPreferenze();

        super.backend = super.nazPluraleBackend;
        super.nomeLista = textService.primaMaiuscola(nomeLista);
        super.titoloPagina = wikiUtility.wikiTitleNazionalita(nomeLista);
        super.typeLista = AETypeLista.nazionalitaPlurale;
        super.typeLinkParagrafi = (AETypeLink) WPref.linkParametriAttNaz.getEnumCurrentObj();
        super.paragrafoAltre = TAG_LISTA_NO_ATTIVITA;
        super.patternCompleto = false;

        if (typeLista == AETypeLista.nazionalitaPlurale) {
            NazPlurale nazionalitaPlurale = nazPluraleBackend.findByKey(textService.primaMinuscola(nomeLista));
            listaNomiSingoli = nazionalitaPlurale != null ? nazionalitaPlurale.listaSingolari.stream().map(naz -> naz.nome).collect(Collectors.toList()) : null;
        }
    }


    /**
     * Pattern Builder <br>
     */
    public ListaNazionalita typeLista(AETypeLista typeLista) {
        super.patternCompleto = false;
        return switch (typeLista) {
            case nazionalitaSingolare -> singolare();
            case nazionalitaPlurale -> plurale();
            default -> this;
        };
    }

    /**
     * Pattern Builder <br>
     */
    public ListaNazionalita singolare() {
        super.patternCompleto = true;
        return (ListaNazionalita) super.typeLista(AETypeLista.nazionalitaSingolare);
    }

    /**
     * Pattern Builder <br>
     */
    public ListaNazionalita plurale() {
        super.patternCompleto = true;
        return (ListaNazionalita) super.typeLista(AETypeLista.nazionalitaPlurale);
    }



    /**
     * Ordina la mappa <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse <br>
     */
    public LinkedHashMap<String, List<WrapLista>> sortMap(LinkedHashMap<String, List<WrapLista>> mappa) {
        return wikiUtility.sort(mappa);
    }

    //    /**
    //     * Costruisce una lista dei wrapper per gestire i dati necessari ad una didascalia <br>
    //     * La sottoclasse specifica esegue l'ordinamento <br>
    //     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
    //     */
    //    @Deprecated
    //    public List<WrapDidascalia> listaWrapDidascalie() {
    //        listaWrapDidascalie = super.listaWrapDidascalie();
    //        return listaWrapDidascalie != null ? sortByAttivita(listaWrapDidascalie) : null;
    //    }
    //
    //    /**
    //     * Mappa ordinata dei wrapper (WrapDidascalia) per gestire i dati necessari ad una didascalia <br>
    //     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
    //     */
    //    @Deprecated
    //    public LinkedHashMap<String, LinkedHashMap<String, List<WrapDidascalia>>> mappaWrapDidascalie() {
    //        super.mappaWrapDidascalie();
    //
    //        LinkedHashMap<String, List<WrapDidascalia>> mappaNaz = creaMappaAttivita(listaWrapDidascalie);
    //        LinkedHashMap<String, List<WrapDidascalia>> mappaLista;
    //
    //        if (mappaNaz != null) {
    //            for (String key : mappaNaz.keySet()) {
    //                mappaLista = creaMappaCarattere(mappaNaz.get(key));
    //                mappaWrapDidascalie.put(key, mappaLista);
    //            }
    //        }
    //
    //        return mappaWrapDidascalie;
    //    }
    //
    //    //
    //    //    /**
    //    //     * Mappa ordinata delle didascalie che hanno una valore valido per la pagina specifica <br>
    //    //     * La mappa è composta da una chiave (ordinata) che corrisponde al titolo del paragrafo <br>
    //    //     * Ogni valore della mappa è costituito da una lista di didascalie per ogni paragrafo <br>
    //    //     * La visualizzazione dei paragrafi può anche essere esclusa, ma questi sono comunque presenti <br>
    //    //     */
    //    //    @Override
    //    //    public LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaDidascalie() {
    //    //        super.mappaDidascalie();
    //    //
    //    //        LinkedHashMap<String, List<WrapDidascalia>> mappaWrap;
    //    //        List<WrapDidascalia> listaWrap;
    //    //        List<String> listaDidascalia;
    //    //        String didascalia;
    //    //
    //    //        for (String key1 : mappaWrapDidascalie.keySet()) {
    //    //            mappaWrap = mappaWrapDidascalie.get(key1);
    //    //            mappaDidascalie.put(key1, new LinkedHashMap<>());
    //    //
    //    //            for (String key2 : mappaWrap.keySet()) {
    //    //                listaWrap = mappaWrap.get(key2);
    //    //                listaDidascalia = new ArrayList<>();
    //    //                for (WrapDidascalia wrap : listaWrap) {
    //    //                    didascalia = didascaliaService.getDidascaliaLista(wrap.getBio());
    //    //                    listaDidascalia.add(didascalia);
    //    //                }
    //    //                mappaDidascalie.get(key1).put(key2, listaDidascalia);
    //    //            }
    //    //        }
    //    //
    //    //        return mappaDidascalie;
    //    //    }
    //
    //    //    /**
    //    //     * Mappa dei paragrafi delle didascalie che hanno una valore valido per la pagina specifica <br>
    //    //     * La mappa è composta da una chiave (ordinata) che è il titolo visibile del paragrafo <br>
    //    //     * Ogni valore della mappa è costituito da una lista di didascalie per ogni paragrafo <br>
    //    //     * La visualizzazione dei paragrafi può anche essere esclusa, ma questi sono comunque presenti <br>
    //    //     */
    //    //    @Override
    //    //    public LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaParagrafi() {
    //    //        super.mappaParagrafi();
    //    //
    //    //        LinkedHashMap<String, List<String>> mappaSub;
    //    //        String paragrafo;
    //    //
    //    //        for (String key : mappaDidascalie.keySet()) {
    //    //            paragrafo = key;
    //    //            mappaSub = mappaDidascalie.get(key);
    ////                paragrafo = wikiUtility.fixTitolo(titoloParagrafo, paragrafo);
    //    //
    //    //            mappaParagrafi.put(paragrafo, mappaSub);
    //    //        }
    //    //
    //    //        return mappaParagrafi;
    //    //    }
    //    @Deprecated
    //    public LinkedHashMap<String, List<WrapDidascalia>> creaMappaAttivita(List<WrapDidascalia> listaWrapNonOrdinata) {
    //        LinkedHashMap<String, List> mappa = new LinkedHashMap<>();
    //        List lista;
    //        String attivita;
    //
    //        if (listaWrapNonOrdinata != null) {
    //            for (WrapDidascalia wrap : listaWrapNonOrdinata) {
    //                attivita = wrap.getAttivitaParagrafo();
    //                attivita = attivita != null ? attivita : VUOTA;
    //
    //                if (mappa.containsKey(attivita)) {
    //                    lista = mappa.get(attivita);
    //                }
    //                else {
    //                    lista = new ArrayList();
    //                }
    //                lista.add(wrap);
    //                mappa.put(attivita, lista);
    //            }
    //        }
    //
    //        return (LinkedHashMap) arrayService.sortVuota(mappa);
    //    }
    //
    //
    //    public List<WrapDidascalia> sortByAttivita(List<WrapDidascalia> listaWrapNonOrdinata) {
    //        List<WrapDidascalia> sortedList = new ArrayList<>();
    //        List<WrapDidascalia> listaConAttivitaOrdinata = new ArrayList<>(); ;
    //        List<WrapDidascalia> listaSenzaAttivitaOrdinata = new ArrayList<>(); ;
    //
    //        listaConAttivitaOrdinata = listaWrapNonOrdinata
    //                .stream()
    //                .filter(wrap -> textService.isValid(wrap.getNazionalitaSingola()))
    //                .sorted(Comparator.comparing(funAttivita))
    //                .collect(Collectors.toList());
    //
    //        listaSenzaAttivitaOrdinata = listaWrapNonOrdinata
    //                .stream()
    //                .filter(wrap -> textService.isEmpty(wrap.getNazionalitaSingola()))
    //                .collect(Collectors.toList());
    //
    //        sortedList.addAll(listaConAttivitaOrdinata);
    //        sortedList.addAll(listaSenzaAttivitaOrdinata);
    //        return sortedList;
    //    }

}
