package it.algos.base24.backend.service;

import com.vaadin.flow.component.sidenav.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.functional.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Thu, 16-Nov-2023
 * Time: 06:42
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Inject public ArrayService arrayService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class ArrayService {

    @Inject
    private TextService textService;

    @Inject
    LogService logger;

    /**
     * Crea un array di stringhe con singolo valore <br>
     *
     * @param singoloValore da inserire
     *
     * @return array di un solo elemento oppure vuoto
     *
     * @since Java 9
     */
    public List<String> creaArraySingolo(final String singoloValore) {
        return textService.isValid(singoloValore) ? List.of(singoloValore) : List.of();
    }

    /**
     * Crea un array di stringhe <br>
     *
     * @param valori da inserire
     *
     * @return array di ritorno
     */
    public List<String> crea(String... valori) {
        return List.of(valori);
    }

    /**
     * Crea una mappa con una singola coppia chiave-valore <br>
     * Entrambe le stringhe devono essere valide <br>
     *
     * @param key   della mappa
     * @param value della mappa
     *
     * @return mappa chiave-valore con un solo elemento
     *
     * @since Java 9
     */
    public Map<String, String> creaMappaSingola(final String key, final String value) {
        return (textService.isValid(key) && textService.isValid(value)) ? Map.of(key, value) : Map.of();
    }


    /**
     * Controlla la validità di un array (lista). <br>
     * Deve esistere (not null) <br>
     * Deve avere degli elementi (size maggiore di 0) <br>
     * Non ci devono essere elementi con valore null <br>
     * Non sono accettate le stringhe vuote -> "" <br>
     *
     * @param array (List) in ingresso da controllare
     *
     * @return vero se l'array soddisfa le condizioni previste
     *
     * @since Java 11
     */
    public boolean isAllValid(final List array) {
        return array != null && array.size() > 0 && array.stream().filter(APredicate.nonValido).count() == 0;
    }


    /**
     * Controlla la validità di una matrice. <br>
     * Deve esistere (not null) <br>
     * Deve avere degli elementi (length maggiore di 0) <br>
     * Non ci devono essere elementi con valore null <br>
     * Non sono accettate le stringhe vuote -> "" <br>
     *
     * @param matrice (String[]) in ingresso da controllare
     *
     * @return vero se la matrice soddisfa le condizioni previste
     *
     * @since Java 11
     */
    public boolean isAllValid(final String[] matrice) {
        return matrice != null && matrice.length > 0 && Arrays.asList(matrice).stream().filter(APredicate.nonValido).count() == 0;
    }


    /**
     * Controlla la validità di una mappa. <br>
     * Deve esistere (not null) <br>
     * Deve avere degli elementi (size maggiore di 0) <br>
     * Non ci devono essere chiavi con valore null <br>
     * Non sono accettate le chiavi vuote -> "" <br>
     *
     * @param mappa (Map) in ingresso da controllare
     *
     * @return vero se la mappa soddisfa le condizioni previste
     *
     * @since Java 11
     */
    public boolean isAllValid(final Map mappa) {
        return mappa != null && mappa.size() > 0 && mappa.keySet().stream().filter(APredicate.nonValido).count() == 0;
    }

    /**
     * Controlla che l'array sia nullo o vuoto <br>
     * Non deve esistere (null) <br>
     * Se esiste, non deve avere elementi (size = 0) <br>
     * Se ci sono elementi devono avere tutti valore null <br>
     *
     * @param array (List) in ingresso da controllare
     *
     * @return vero se l'array soddisfa le condizioni previste
     *
     * @since Java 11
     */
    public boolean isEmpty(final List array) {
        return array == null || array.size() == 0 || array.stream().filter(APredicate.valido).count() == 0;
    }


    /**
     * Controlla che la matrice sia nulla o vuota. <br>
     * Non deve esistere (null) <br>
     * Se esiste, non deve avere elementi (size = 0) <br>
     * Se ci sono elementi devono avere tutti valore null o vuoto -> "" <br>
     *
     * @param matrice (String[]) in ingresso da controllare
     *
     * @return vero se la matrice soddisfa le condizioni previste
     *
     * @since Java 11
     */
    public boolean isEmpty(final String[] matrice) {
        return matrice == null || matrice.length == 0 || Arrays.asList(matrice).stream().filter(APredicate.valido).count() == 0;
    }


    /**
     * Controlla che la mappa sia nulla o vuota. <br>
     * Non deve esistere (null) <br>
     * Se esiste, non deve avere elementi (size = 0) <br>
     * Se ci sono elementi devono avere tutti la chiave null o vuota -> "" <br>
     *
     * @param mappa (Map) in ingresso da controllare
     *
     * @return vero se la mappa soddisfa le condizioni previste
     *
     * @since Java 11
     */
    public boolean isEmpty(final Map mappa) {
        return mappa == null || mappa.size() == 0 || mappa.keySet().stream().filter(APredicate.valido).count() == 0;
    }

    /**
     * Controlla se una mappa può essere semplificata. <br>
     * <p>
     * Esamina una mappa del tipo Map<String, List<String>> e se tutte <br>
     * le liste hanno un solo valore, semplifica in Map<String, String> <br>
     * <p>
     * La mappa deve essere valida <br>
     * Deve esistere (not null) <br>
     * Deve avere degli elementi (size maggiore di 0) <br>
     * Ogni lista deve avere un solo valore <br>
     *
     * @param multiParametersMap mappa di liste di stringhe
     *
     * @return true se è semplificabile sostituendo le liste con un singole valore
     *
     * @since Java 11
     */
    public boolean isMappaSemplificabile(Map<String, List<String>> multiParametersMap) {
        return multiParametersMap != null && multiParametersMap.size() > 0 && multiParametersMap.values().stream().filter(n -> n.size() > 1).count() == 0;
    }


    /**
     * Crea una stringa con i valori dell'array separati da virgola <br>
     *
     * @param array lista di valori
     *
     * @return stringa con i singoli valori separati da virgola
     *
     * @since Java 9
     */
    public String toStringaVirgola(final List array) {
        return toStringaBase(array, VIRGOLA);
    }

    /**
     * Crea una stringa con i valori dell'array separati da virgola e spazio <br>
     *
     * @param array lista di valori
     *
     * @return stringa con i singoli valori separati da virgola
     *
     * @since Java 9
     */
    public String toStringaVirgolaSpazio(final List array) {
        return toStringaBase(array, VIRGOLA_SPAZIO);
    }

    /**
     * Costruisce una stringa con i singoli valori separati da un pipe <br>
     *
     * @param array lista di valori
     *
     * @return stringa con i singoli valori separati da pipe
     */
    public String toStringaPipe(final List array) {
        return toStringaBase(array, PIPE);
    }

    /**
     * Crea una stringa con i valori dell'array separati da un separatore <br>
     *
     * @param array lista di valori
     * @param sep   caratteri separatore
     *
     * @return stringa con i singoli valori separati da separatore
     *
     * @since Java 9
     */
    private String toStringaBase(final List array, final String sep) {
        StringBuffer buffer = new StringBuffer();

        if (array != null) {
            array.forEach(obj -> {
                {
                    buffer.append(sep);
                    buffer.append(obj);
                }
            });
            buffer.delete(0, sep.length());
        }

        return buffer.toString();
    }

    /**
     * Ordina la mappa secondo la chiave
     *
     * @param mappaDisordinata in ingresso
     *
     * @return mappa ordinata, null se mappaDisordinata è null
     */
    public LinkedHashMap sort(final Map mappaDisordinata) {
        LinkedHashMap mappaOrdinata = new LinkedHashMap();
        Object[] listaChiavi;

        if (mappaDisordinata == null) {
            return null;
        }
        if (mappaDisordinata.size() == 1) {
            for (Object chiave : mappaDisordinata.keySet()) {
                mappaOrdinata.put(chiave, mappaDisordinata.get(chiave));
            }
            return mappaOrdinata;
        }

        listaChiavi = mappaDisordinata.keySet().toArray();

        try {
            Arrays.sort(listaChiavi);
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }

        for (Object chiave : listaChiavi) {
            mappaOrdinata.put(chiave, mappaDisordinata.get(chiave));
        }

        return mappaOrdinata;
    }

    /**
     * Ordina la mappa secondo il valore
     *
     * @param mappaDisordinata in ingresso
     *
     * @return mappa ordinata, null se mappaDisordinata è null
     */
    public Map sortValue(final Map mappaDisordinata) {
        LinkedHashMap mappaOrdinata = new LinkedHashMap();
        Object[] listaValues;
        List listaKeys;

        if (!isAllValid(mappaDisordinata)) {
            return mappaDisordinata;
        }

        listaValues = mappaDisordinata.values().toArray();

        try {
            Arrays.sort(listaValues);
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }

        for (Object value : listaValues) {
            listaKeys = getSet(mappaDisordinata, value);
            for (Object key : listaKeys) {
                mappaOrdinata.put(key, value);
            }
        }

        return mappaOrdinata;
    }

    public Object getKey(final Map mappa, Object value) {
        for (Object key : mappa.keySet()) {
            if (mappa.get(key).equals(value)) {
                return key;
            }
        }

        return null;
    }

    public List getSet(final Map mappa, Object value) {
        List sortedList;
        Set keys = new HashSet<>();

        for (Object key : mappa.keySet()) {
            if (mappa.get(key).equals(value)) {
                keys.add(key);
            }
        }

        sortedList = new ArrayList<>(keys);
        Collections.sort(sortedList);

        return sortedList;
    }


    /**
     * Ordina la mappa secondo la chiave
     *
     * @param mappaDisordinata in ingresso
     *
     * @return mappa ordinata, null se mappaDisordinata è null
     */
    public Map sortVuota(final Map mappaDisordinata) {
        LinkedHashMap mappaOrdinata = new LinkedHashMap();
        Set listaChiaviDisordinata;
        List<String> listaChiaviOrdinata = null;

        listaChiaviDisordinata = mappaDisordinata.keySet();
        try {
            listaChiaviOrdinata = listaChiaviDisordinata.stream().filter(c -> textService.isValid(c)).sorted().toList();
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }

        for (Object chiave : listaChiaviOrdinata) {
            mappaOrdinata.put(chiave, mappaDisordinata.get(chiave));
        }

        if (mappaDisordinata.keySet().contains(VUOTA)) {
            mappaOrdinata.put(VUOTA, mappaDisordinata.get(VUOTA));
        }

        return mappaOrdinata;
    }

    /**
     * Differenza di due array <br>
     *
     * @param arrayA lista di valori A
     * @param arrayB lista di valori B
     *
     * @return lista dei valori mancanti
     */
    public List<Integer> differenzaInt(final List<Integer> arrayA, final List<Integer> arrayB) {
        return arrayA.stream().filter(a -> !arrayB.contains(a)).toList();
    }


    public List<String> getList(String testo) {
        List<String> lista = null;
        String tag = VIRGOLA;
        String[] parti = null;

        if (textService.isValid(testo) && testo.contains(tag)) {
            parti = testo.split(tag);
        }

        if (parti != null && parti.length > 0) {
            lista = Arrays.asList(parti);
        }

        return lista;
    }


    public List<String> orderTree(List<String> listaDisordinata) {
        List<String> listaOrdinata = new ArrayList<>();
        List<String> listaSingoli = new ArrayList<>();
        List<String> listaDoppi = new ArrayList<>();
        String[] parti;
        String base;
        String ancestor;
        int posBase;
        int posAncestor;

        for (String token : listaDisordinata) {
            if (token.contains(VIRGOLA)) {
                if (!listaDoppi.contains(token)) {
                    listaDoppi.add(token);
                }
            }
            else {
                if (!listaSingoli.contains(token)) {
                    listaSingoli.add(token);
                }
            }
        }
        listaOrdinata.addAll(listaSingoli);

        for (String tokenDoppio : listaDoppi) {
            parti = tokenDoppio.split(VIRGOLA);
            base = parti[0];
            ancestor = parti[1];

            if (listaOrdinata.contains(ancestor)) {
                posAncestor = listaOrdinata.indexOf(ancestor);
                posBase = posAncestor + 1;
                listaOrdinata.add(posBase, base);
            }
            else {
                listaOrdinata.add(ancestor);
            }
        }

        return listaOrdinata;
    }

    /**
     * Differenza di due array tramite stream <br>
     *
     * @param arrayA lista di valori A
     * @param arrayB lista di valori B
     *
     * @return lista dei valori di A che mancano in B
     */
    public List<Long> deltaLongStream(final List<Long> arrayA, final List<Long> arrayB) {
        return arrayA.stream().filter(a -> !arrayB.contains(a)).toList();
    }


    /**
     * Differenza di due array tramite Collections.binarySearch <br>
     * Gli array devono essere ordinati <br>
     *
     * @param arrayA lista di valori A
     * @param arrayB lista di valori B
     *
     * @return lista dei valori di A che non sono presenti in B
     */
    public List deltaBinary(List arrayA, List arrayB) {
        List listaMancanti = new ArrayList<>();
        Object maggiore;
        int pos;

        Object[] alfa = arrayA.toArray();
        Arrays.sort(alfa);
        arrayA = Arrays.stream(alfa).toList();

        Object[] beta = arrayB.toArray();
        Arrays.sort(beta);
        arrayB = Arrays.stream(beta).toList();

        for (int k = 0; k < alfa.length; k++) {
            maggiore = alfa[k];
            pos = Collections.binarySearch(arrayB, maggiore);

            if (pos < 0) {
                listaMancanti.add(maggiore);
            }
        }
        return listaMancanti;
    }

    public LinkedHashMap<String, List<SideNavItem>> orderMap(LinkedHashMap<String, List<SideNavItem>> mappaIn) {
        for (List<SideNavItem> lista : mappaIn.values()) {
            orderList(lista);
        }

        return mappaIn;
    }

    public List<SideNavItem> orderList(List<SideNavItem> lista) {
        Map<String, SideNavItem> mappa = new HashMap<>();

        for (SideNavItem item : lista) {
            mappa.put(item.getLabel(), item);
        }
        mappa = sortVuota(mappa);
        lista.removeAll(lista);
        for (Object key : mappa.keySet()) {
            lista.add(mappa.get(key));
        }

        return lista;
    }


}// end of Service class