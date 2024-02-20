package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 17-Feb-2024
 * Time: 06:32
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ListaPagina extends ListaAstratta {

    /**
     * Costruttore base con 2 parametri (obbligatori) <br>
     * Not annotated with @Autowired annotation, classe astratta <br>
     * La classe usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaPagina(final String nomeLista, TypeLista typeLista) {
        super(nomeLista, typeLista);
    }// end of constructor not @Autowired and used


//    /**
//     * Numero delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
//     * Rimanda direttamente al metodo dedicato del service BioMongoModulo, SENZA nessuna elaborazione in questa classe <br>
//     *
//     * @return -1 se il pattern della classe non è valido, zero se i dati sono validi ma non ci sono biografie <br>
//     */
//    @Override
//    protected int numBio() {
//        if (bioMongoModulo == null || textService.isEmpty(nomeLista)) {
//            return INT_ERROR;
//        }
//
//        super.numBio = switch (typeLista) {
//            case giornoNascita -> bioMongoModulo.countAllByGiornoNato(nomeLista);
//            case giornoMorte -> bioMongoModulo.countAllByGiornoMorto(nomeLista);
//            case annoNascita -> bioMongoModulo.countAllByAnnoNato(nomeLista);
//            case annoMorte -> bioMongoModulo.countAllByAnnoMorto(nomeLista);
//            case attivitaSingolare -> bioMongoModulo.countAllByAttivitaSingolare(nomeLista);
//            case attivitaPlurale -> bioMongoModulo.countAllByAttivitaPlurale(nomeLista);
//            case nazionalitaSingolare -> bioMongoModulo.countAllByNazionalitaSingolare(nomeLista);
//            case nazionalitaPlurale -> bioMongoModulo.countAllByNazionalitaPlurale(nomeLista);
//            default -> 0;
//        };
//
//        return numBio;
//    }


//    /**
//     * Lista ordinata delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
//     * Rimanda direttamente al metodo dedicato del service BioMongoModulo, SENZA nessuna elaborazione in questa classe <br>
//     *
//     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
//     */
//    @Override
//    protected List<BioMongoEntity> listaBio() {
//        if (bioMongoModulo == null || textService.isEmpty(nomeLista)) {
//            return null;
//        }
//
//        super.listaBio = switch (typeLista) {
//            case giornoNascita -> bioMongoModulo.findAllByGiornoNato(nomeLista);
//            case giornoMorte -> bioMongoModulo.findAllByGiornoMorto(nomeLista);
//            case annoNascita -> bioMongoModulo.findAllByAnnoNato(nomeLista);
//            case annoMorte -> bioMongoModulo.findAllByAnnoMorto(nomeLista);
//            case attivitaSingolare -> bioMongoModulo.findAllByAttivitaSingolare(nomeLista);
//            case attivitaPlurale -> bioMongoModulo.findAllByAttivitaPlurale(nomeLista);
//            case nazionalitaSingolare -> bioMongoModulo.findAllByNazionalitaSingolare(nomeLista);
//            case nazionalitaPlurale -> bioMongoModulo.findAllByNazionalitaPlurale(nomeLista);
//            default -> null;
//        };
//
//        return listaBio;
//    }


//    /**
//     * Lista ordinata di tutti i wrapLista che hanno una valore valido per la pagina specifica <br>
//     * Costruisce un wrap per ogni elemento della listaBio recuperata da BioMongoModulo <br>
//     *
//     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
//     */
//    @Override
//    protected List<WrapDidascalia> listaWrapDidascalie() {
//        WrapDidascalia wrap;
//
//        if (listaBio == null || listaBio.size() == 0) {
//            listaBio = listaBio();
//        }
//
//        if (listaBio != null && listaBio.size() > 0) {
//            for (BioMongoEntity bio : listaBio) {
//                wrap = appContext.getBean(WrapDidascalia.class).type(typeLista).get(bio);
//                if (wrap != null) {
//                    listaWrapDidascalie.add(wrap);
//                }
//            }
//        }
//
//        return listaWrapDidascalie;
//    }

//    /**
//     * Mappa ordinata di WrapDidascalie per tutti i paragrafi <br>
//     *
//     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
//     */
//    @Override
//    protected Map<String, List<WrapDidascalia>> mappaWrapDidascalie() {
//        String key;
//        List<WrapDidascalia> lista;
//
//        if (listaWrapDidascalie == null || listaWrapDidascalie.size() == 0) {
//            listaWrapDidascalie = listaWrapDidascalie();
//        }
//
//        for (WrapDidascalia wrap : listaWrapDidascalie) {
//
//            //--primo livello - paragrafi
//            key = wrap.getPrimoLivello();
//            if (!mappaWrapDidascalie.containsKey(key)) {
//                lista = new ArrayList<>();
//                mappaWrapDidascalie.put(key, lista);
//            }
//
//            //--didascalia
//            lista = mappaWrapDidascalie.get(key);
//            lista.add(wrap);
//        }
//
//        super.fixMappaWrapDidascalie();
//        return mappaWrapDidascalie;
//    }

}
