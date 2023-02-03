package it.algos.wiki24.backend.packages.attivita;

import it.algos.vaad24.backend.entity.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: lun, 18-apr-2022
 * Time: 21:25
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <p>
 * Annotated with @Repository (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Eventualmente usare una costante di VaadCost come @Qualifier sia qui che nella corrispondente classe xxxBackend <br>
 */
@Repository
@Qualifier(TAG_ATTIVITA)
public interface AttivitaRepository extends MongoRepository<Attivita, String> {

    @Override
    List<Attivita> findAll();

    <Attivita extends AEntity> Attivita insert(Attivita entity);

    <Attivita extends AEntity> Attivita save(Attivita entity);

    @Override
    void delete(Attivita entity);

    Attivita findFirstBySingolare(String singolare);

    Attivita findFirstByPluraleParagrafo(String plurale);
    Attivita findFirstByPluraleLista(String plurale);

    List<Attivita> findAllByPluraleParagrafoOrderBySingolareAsc(String paragrafo);
    List<Attivita> findAllByPluraleListaOrderBySingolareAsc(String pagina);

    List<Attivita> findBySuperaSogliaFalseAndEsistePaginaListaTrue();

}// end of crud repository class