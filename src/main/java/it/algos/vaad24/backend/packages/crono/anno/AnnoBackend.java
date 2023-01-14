package it.algos.vaad24.backend.packages.crono.anno;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 02-mag-2022
 * Time: 16:05
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class AnnoBackend extends CrudBackend {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public SecoloBackend secoloBackend;

    public AnnoRepository repository;

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
    public AnnoBackend(@Autowired @Qualifier(TAG_ANNO) final MongoRepository crudRepository) {
        super(crudRepository, Anno.class);
        this.repository = (AnnoRepository) crudRepository;
    }

    public boolean crea(final int ordine, final String nome, final Secolo secolo, final boolean dopoCristo, final boolean bisestile) {
        Anno anno = newEntity(ordine, nome, secolo, dopoCristo, bisestile);
        return crudRepository.insert(anno) != null;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Anno newEntity() {
        return newEntity(0, VUOTA, null, false, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine     di presentazione nel popup/combobox (obbligatorio, unico)
     * @param nome       corrente
     * @param secolo     di appartenenza
     * @param dopoCristo flag per gli anni prima/dopo cristo
     * @param bisestile  flag per gli anni bisestili
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Anno newEntity(final int ordine, final String nome, final Secolo secolo, final boolean dopoCristo, final boolean bisestile) {
        return Anno.builder()
                .ordine(ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .secolo(secolo)
                .dopoCristo(dopoCristo)
                .bisestile(bisestile)
                .build();
    }

    public Anno findByNome(final String nome) {
        return repository.findFirstByNome(nome);
    }

    public Anno findByOrdine(final int ordine) {
        return repository.findFirstByOrdine(ordine);
    }

    @Override
    public List<Anno> findAll() {
        return findAllDiscendente();
    }

    public List<Anno> findAllDiscendente() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "ordine"));
    }

    public List<Anno> findAllAscendente() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "ordine"));
    }

    public List<String> findNomi() {
        return findNomiDiscendente();
    }

    public List<String> findNomiDiscendente() {
        return findAllDiscendente().stream()
                .map(anno -> anno.nome)
                .collect(Collectors.toList());
    }

    public List<String> findNomiAscendente() {
        return findAllAscendente().stream()
                .map(anno -> anno.nome)
                .collect(Collectors.toList());
    }

    public List<Anno> findAllBySecolo(Secolo secolo) {
        return findAllAscendente().stream()
                .filter(anno -> anno.secolo.nome.equals(secolo.nome))
                .collect(Collectors.toList());
    }


    public List<String> findNomiBySecolo(String secolo) {
        return findAllAscendente().stream()
                .filter(anno -> anno.secolo.nome.equals(secolo))
                .map(anno -> anno.nome)
                .collect(Collectors.toList());
    }

    /**
     * Creazione di alcuni dati <br>
     * Esegue SOLO se la collection NON esiste oppure esiste ma è VUOTA <br>
     * Viene invocato alla creazione del programma <br>
     * I dati possono essere presi da una Enumeration, da un file CSV locale, da un file CSV remoto o creati hardcoded <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public AResult resetOnlyEmpty() {
        AResult result = super.resetOnlyEmpty();

        if (secoloBackend.count() < 1) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la collezione 'Secolo'")).usaDb());
            return result;
        }

        if (result.isValido()) {
            //--costruisce gli anni prima di cristo partendo da ANTE_CRISTO_MAX che coincide con DELTA_ANNI
            for (int k = 1; k <= ANTE_CRISTO_MAX; k++) {
                creaPrima(k);
            }

            //--costruisce gli anni dopo cristo fino all'anno DOPO_CRISTO_MAX
            for (int k = 1; k <= DOPO_CRISTO_MAX; k++) {
                creaDopo(k);
            }
        }
        else {
            return result;
        }

        return fixResult(result);
    }

    public void creaPrima(int numeroProgressivo) {
        int delta = DELTA_ANNI;
        int numeroAnno = delta - numeroProgressivo + 1;
        int ordine = numeroProgressivo;
        String tagPrima = " a.C.";
        String nomeVisibile = numeroAnno + tagPrima;
        Secolo secolo = secoloBackend.getSecoloAC(numeroAnno);

        crea(ordine, nomeVisibile, secolo, false, false);
    }

    public void creaDopo(int numeroProgressivo) {
        int delta = DELTA_ANNI;
        int numeroAnno = numeroProgressivo;
        int ordine = numeroProgressivo + delta;
        String nomeVisibile = numeroProgressivo + VUOTA;
        Secolo secolo = secoloBackend.getSecoloDC(numeroAnno);
        boolean bisestile = dateService.isBisestile(numeroAnno);

        crea(ordine, nomeVisibile, secolo, true, bisestile);
    }

}// end of crud backend class
