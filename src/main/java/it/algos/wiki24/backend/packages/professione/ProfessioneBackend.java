package it.algos.wiki24.backend.packages.professione;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: mar, 26-apr-2022
 * Time: 07:19
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class ProfessioneBackend extends WikiBackend {


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public WikiApiService wikiApiService;

    private ProfessioneRepository repository;

    /**
     * Costruttore @Autowired (facoltativo) @Qualifier (obbligatorio) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per specificare la classe che incrementa l'interfaccia repository <br>
     * Si usa una costante statica, per essere sicuri di scriverla uguale a quella di xxxRepository <br>
     * Regola la classe di persistenza dei dati specifica e la passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questo service <br>
     *
     * @param crudRepository per la persistenza dei dati
     */
    public ProfessioneBackend(@Autowired @Qualifier(TAG_PROFESSIONE) final MongoRepository crudRepository) {
        super(crudRepository, Professione.class);
        this.repository = (ProfessioneRepository) crudRepository;
//        super.lastDownload = WPref.downloadProfessione;
    }

    public Professione creaIfNotExist(final String attivita, final String pagina, final boolean aggiunta) {
        return checkAndSave(newEntity(attivita, pagina, aggiunta));
    }

    public Professione checkAndSave(final Professione professione) {
        return isExist(professione.attivita) ? null : repository.insert(professione);
    }

    public boolean isExist(final String attivita) {
        return repository.findFirstByAttivita(attivita) != null;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Professione newEntity() {
        return newEntity(VUOTA, VUOTA, true);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Professione newEntity(final String attivita, final String pagina, final boolean aggiunta) {
        return Professione.builder()
                .attivita(textService.isValid(attivita) ? attivita : null)
                .pagina(textService.isValid(pagina) ? pagina : null)
                .aggiunta(aggiunta)
                .build();
    }


    /**
     * Legge la mappa di valori dal modulo di wiki <br>
     * Cancella la (eventuale) precedente lista di attività <br>
     * Elabora la mappa per creare le singole attività <br>
     * Integra le attività con quelle di genere <br>
     *
     * @param wikiTitle della pagina su wikipedia
     *
     * @return true se l'azione è stata eseguita
     */
    public void download(String wikiTitle) {
        long inizio = System.currentTimeMillis();
        int size = 0;

        Map<String, String> mappa = wikiApiService.leggeMappaModulo(wikiTitle);

        if (mappa != null && mappa.size() > 0) {
            deleteAll();
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                if (creaIfNotExist(entry.getKey(), entry.getValue(), false) != null) {
                    size++;
                }
            }
        }
        else {
            message = String.format("Non sono riuscito a leggere da wiki il modulo %s", wikiTitle);
            logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
        }
        super.fixDownloadSecondi(inizio, wikiTitle, mappa.size(), size);
    }

}// end of crud backend class
