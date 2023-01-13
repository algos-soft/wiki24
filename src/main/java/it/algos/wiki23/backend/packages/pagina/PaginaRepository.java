package it.algos.wiki23.backend.packages.pagina;

import it.algos.vaad24.backend.entity.*;
import it.algos.wiki23.backend.enumeration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Wed, 21-Sep-2022
 * Time: 17:39
 * <p>
 * Estende l'interfaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <p>
 * Annotated with @Repository (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Eventualmente usare una costante di VaadCost come @Qualifier sia qui che nella corrispondente classe xxxBackend <br>
 */
@Repository
@Qualifier("Pagina")
public interface PaginaRepository extends MongoRepository<Pagina, String> {

    @Override
    List<Pagina> findAll();

    <Pagina extends AEntity> Pagina insert(Pagina entity);

    <Pagina extends AEntity> Pagina save(Pagina entity);

    @Override
    void delete(Pagina entity);

    Pagina findFirstByPagina(String pagina);

    long countByTypeAndCancella(AETypePaginaCancellare type, boolean cancella);


}// end of crud repository class