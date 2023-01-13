package it.algos.wiki23.backend.packages.genere;

import static it.algos.wiki23.backend.boot.Wiki23Cost.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki
 * Created by Algos
 * User: gac
 * Date: dom, 24-apr-2022
 * Time: 10:17
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <p>
 * Annotated with @Repository (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Eventualmente usare una costante di VaadCost come @Qualifier sia qui che nella corrispondente classe xxxBackend <br>
 */
@Repository
@Qualifier(TAG_GENERE)
public interface GenereRepository extends MongoRepository<Genere, String> {

    @Override
    List<Genere> findAll();

    @Override
    Genere insert(Genere entity);

    @Override
    Genere save(Genere entity);

    @Override
    void delete(Genere entity);

    List<Genere> findBySingolareStartingWithIgnoreCaseOrderBySingolareAsc(String singolare);

    Genere findFirstBySingolare(String singolare);

}// end of crud repository class