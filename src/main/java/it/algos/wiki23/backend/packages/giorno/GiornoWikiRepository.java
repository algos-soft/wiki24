package it.algos.wiki23.backend.packages.giorno;

import it.algos.vaad24.backend.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Thu, 14-Jul-2022
 * Time: 20:04
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <p>
 * Annotated with @Repository (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Eventualmente usare una costante di VaadCost come @Qualifier sia qui che nella corrispondente classe xxxBackend <br>
 */
@Repository
@Qualifier("GiornoWiki")
public interface GiornoWikiRepository extends MongoRepository<GiornoWiki, String> {

    @Override
    List<GiornoWiki> findAll();

    <GiornoWiki extends AEntity> GiornoWiki insert(GiornoWiki entity);

    <GiornoWiki extends AEntity> GiornoWiki save(GiornoWiki entity);

    @Override
    void delete(GiornoWiki entity);

    GiornoWiki findFirstByNomeWiki(String nome);

}// end of crud repository class