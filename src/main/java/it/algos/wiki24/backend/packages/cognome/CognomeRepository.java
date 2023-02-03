package it.algos.wiki24.backend.packages.cognome;

import it.algos.vaad24.backend.entity.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Wed, 10-Aug-2022
 * Time: 08:43
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <p>
 * Annotated with @Repository (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Eventualmente usare una costante di VaadCost come @Qualifier sia qui che nella corrispondente classe xxxBackend <br>
 */
@Repository
@Qualifier(TAG_COGNOME)
public interface CognomeRepository extends MongoRepository<Cognome, String> {

    @Override
    List<Cognome> findAll();

    <Cognome extends AEntity> Cognome insert(Cognome entity);

    <Cognome extends AEntity> Cognome save(Cognome entity);

    @Override
    void delete(Cognome entity);

    Cognome findFirstByCognome(String cognome);

}// end of crud repository class