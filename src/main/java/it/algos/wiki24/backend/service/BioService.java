package it.algos.wiki24.backend.service;

import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import org.springframework.stereotype.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import javax.inject.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 05-Jan-2024
 * Time: 08:38
 * Classe di libreria. NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 *
 * @Inject public BioService BioService; (iniziale minuscola) <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class BioService {

    @Inject
    TextService textService;

    @Inject
    LogService logger;

    @Inject
    BioMongoModulo bioMongoModulo;

    /**
     * Cerca tutte le entities di una collection filtrate con per giorno di nascita. <br>
     * Selects documents in a collection or view and returns a list of the selected documents. <br>
     *
     * @param giornoNato per costruire la query
     *
     * @return lista di entityBeans ordinata per cognome
     *
     * @see(https://docs.mongodb.com/manual/reference/method/db.collection.find/#db.collection.find/)
     */
    public List<BioMongoEntity> fetchGiornoNato(String giornoNato) {
        List<BioMongoEntity> listaOrdinata = new ArrayList<>();
        List<BioMongoEntity> listaOrdinataNonSuddivisa = null;
        List<BioMongoEntity> listaConAnnoNato;
        List<BioMongoEntity> listaSenzaAnnoNato;

        if (textService.isEmpty(giornoNato)) {
            logger.warn(new WrapLog().exception(new AlgosException("Manca l'indicazione del giorno")));
            return null;
        }

        listaOrdinataNonSuddivisa = bioMongoModulo.findAllByGiornoNato(giornoNato);
        listaSenzaAnnoNato = listaOrdinataNonSuddivisa
                .stream()
                .filter(bio -> bio.annoNatoOrd == 0)
//                .sorted(Comparator.comparing(bio -> bio.cognome))
                .sorted(Comparator.comparing(bio -> bio.ordinamento))
                .collect(Collectors.toList());

        listaConAnnoNato = listaOrdinataNonSuddivisa
                .stream()
                .filter(bio -> bio.annoNatoOrd > 0)
                .sorted(Comparator.comparing(bio -> bio.annoNatoOrd))
                .collect(Collectors.toList());

        //        switch ((AETypeChiaveNulla) WPref.typeChiaveNulla.getEnumCurrentObj()) {
        //            case inTesta -> {
        //                listaOrdinata.addAll(listaSenzaAnnoNato);
        //                listaOrdinata.addAll(listaConAnnoNato);
        //            }
        //
        //            case inCoda -> {
        //                listaOrdinata.addAll(listaConAnnoNato);
        //                listaOrdinata.addAll(listaSenzaAnnoNato);
        //            }
        //        }

        listaOrdinata.addAll(listaConAnnoNato);
        listaOrdinata.addAll(listaSenzaAnnoNato);

        return listaOrdinata;
    }

}// end of Service class