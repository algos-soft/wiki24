package it.algos.base;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import it.algos.vaad24.backend.packages.utility.log.*;
import it.algos.vaad24.backend.packages.utility.nota.*;
import it.algos.vaad24.backend.packages.utility.preferenza.*;
import it.algos.vaad24.backend.packages.utility.versione.*;
import it.algos.vaad24.backend.wrapper.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Fri, 24-Feb-2023
 * Time: 20:03
 * Test delle classi 'backend' senza repository <br>
 * <p>
 * isExistId()
 * isExistKey(), se esiste una key
 * isExistProperty()
 * findByID()
 * findByKey(), se esiste una key
 * findByProperty()
 * save()
 * insert()
 * update()
 * delete()
 * count()
 * findAllNoSort()
 * findAllSortCorrente()
 * findAllSort()
 * findAllKey()
 * resetOnlyEmpty()
 * deleteAll()
 */

public abstract class BackendTest extends AlgosIntegrationTest {

    @InjectMocks
    protected NotaBackend notaBackend;

    @InjectMocks
    protected GiornoBackend giornoBackend;

    @InjectMocks
    protected MeseBackend meseBackend;

    @InjectMocks
    protected AnnoBackend annoBackend;

    @InjectMocks
    protected SecoloBackend secoloBackend;

    @InjectMocks
    protected ContinenteBackend continenteBackend;

    @InjectMocks
    protected LoggerBackend loggerBackend;

    @InjectMocks
    protected VersioneBackend versioneBackend;

    @InjectMocks
    protected PreferenzaBackend preferenzaBackend;

    @Autowired
    protected PreferenzaRepository preferenzaRepository;

    @Autowired
    protected ViaBackend viaBackend;

    protected CrudBackend crudBackend;

    protected String backendName;

    protected String collectionName;

    protected String keyPropertyName;

    protected TypeBackend typeBackend;

    protected Sort sortOrder;


    //--giorno
    //--esistente
    protected static Stream<Arguments> GIORNI() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
                Arguments.of("23 febbraio", true),
                Arguments.of("43 marzo", false),
                Arguments.of("19 dicembra", false),
                Arguments.of("4 gennaio", true)
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        clazzName = entityClazz.getSimpleName();
        backendName = clazzName + SUFFIX_BACKEND;
        collectionName = annotationService.getCollectionName(entityClazz);
        keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        this.typeBackend = typeBackend != null ? typeBackend : TypeBackend.nessuno;

        if (reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE)) {
            sortOrder = Sort.by(Sort.Direction.ASC, FIELD_NAME_ORDINE);
        }
        else {
            sortOrder = Sort.by(Sort.Direction.ASC, FIELD_NAME_ID_CON);
        }
        crudBackend.sortOrder = sortOrder;
    }

    /**
     * Regola tutti riferimenti incrociati <br>
     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
     * Pu?? essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixRiferimentiIncrociati() {
        super.fixRiferimentiIncrociati();

        crudBackend.arrayService = arrayService;
        crudBackend.dateService = dateService;
        crudBackend.textService = textService;
        crudBackend.resourceService = resourceService;
        crudBackend.reflectionService = reflectionService;
        crudBackend.mongoService = mongoService;
        crudBackend.annotationService = annotationService;
        crudBackend.logger = logger;
        crudBackend.beanService = beanService;

        crudBackend.crudRepository = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - collection")
    protected void collection() {
        System.out.println("1 - Esistenza della collection");

        ottenutoBooleano = crudBackend.isExistsCollection();
        if (ottenutoBooleano) {
            message = String.format("Esiste la collection della classe [%s] e si chiama '%s'", clazzName, collectionName);
        }
        else {
            message = String.format("Non esiste la collection '%s' della classe [%s]", collectionName, clazzName);
        }
        System.out.println(message);
    }


    @Test
    @Order(2)
    @DisplayName("2 - count")
    protected void count2() {
        System.out.println("2 - count");

        ottenutoIntero = crudBackend.count();
        if (ottenutoIntero > 0) {
            message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        }
        else {
            if (reflectionService.isEsisteMetodo(crudBackend.getClass(), METHOD_NAME_RESET_ONLY)) {
                message = String.format("La collection '%s' della classe [%s] ?? ancora vuota. Usa il metodo %s.%s()", collectionName, clazzName, backendName, METHOD_NAME_RESET_ONLY);
            }
            else {
                message = String.format("Nel database mongoDB la collection '%s' della classe [%s] ?? ancora vuota", collectionName, clazzName);
            }
        }
        System.out.println(message);
    }


    @Test
    @Order(21)
    @DisplayName("21 - findAllNoSort (entityBeans)")
    protected void findAllNoSort() {
        System.out.println("21 - findAllNoSort (entityBeans)");
        System.out.println(VUOTA);

        listaBeans = crudBackend.findAllNoSort();
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        System.out.println(message);
        printBackend(listaBeans);
    }


    @Test
    @Order(22)
    @DisplayName("22 - findAll findAllSortCorrente (entityBeans)")
    protected void findAllSortCorrente() {
        System.out.println("22 - findAll findAllSortCorrente (entityBeans)");
        System.out.println(VUOTA);

        listaBeans = crudBackend.findAllSortCorrente();
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        System.out.println(message);
        printBackend(listaBeans);
    }


    @Test
    @Order(23)
    @DisplayName("23 - findAllSortCorrenteReverse (entityBeans)")
    protected void findAllSort() {
        System.out.println("23 - findAllSortCorrenteReverse (entityBeans)");
        System.out.println(VUOTA);

        listaBeans = crudBackend.findAllSortCorrenteReverse();
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        System.out.println(message);
        printBackend(listaBeans);
    }


    @Test
    @Order(31)
    @DisplayName("31 - findAllForKey (String)")
    protected void findAllForKey() {
        System.out.println("31 - findAllForKey (String)");
        System.out.println(VUOTA);

        if (!annotationService.usaKeyPropertyName(entityClazz)) {
            System.out.println("Il metodo usato da questo test presuppone che esista una keyProperty");

            message = String.format("Nella entityClazz [%s] la keyProperty non ?? prevista", clazzName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(keyPropertyName = \"nome\")");
            System.out.println(message);
            return;
        }

        if (!crudBackend.isExistsCollection()) {
            message = String.format("Il metodo usato da questo test presuppone che esista la collection '%s' che invece ?? assente", collectionName);
            System.out.println(message);
            message = String.format("Non esiste la collection '%s' della classe [%s]", collectionName, clazzName);
            System.out.println(message);
            return;
        }

        listaStr = crudBackend.findAllForKey();
        assertNotNull(listaStr);
        ottenutoIntero = listaStr.size();
        sorgente = textService.format(ottenutoIntero);
        sorgente2 = keyPropertyName;
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities. Valori (String) del campo chiave '%s':", collectionName, clazzName, sorgente, sorgente2);
        System.out.println(message);

        printSubLista(listaStr);
    }

    @Test
    @Order(32)
    @DisplayName("32 - findAllForKeyReverseOrder (String)")
    protected void findAllForKeyReverseOrder() {
        System.out.println("32 - findAllForKeyReverseOrder (String)");
        System.out.println(VUOTA);

        if (!annotationService.usaKeyPropertyName(entityClazz)) {
            System.out.println("Il metodo usato da questo test presuppone che esista una keyProperty");

            message = String.format("Nella entityClazz [%s] la keyProperty non ?? prevista", clazzName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(keyPropertyName = \"nome\")");
            System.out.println(message);
            return;
        }

        if (!crudBackend.isExistsCollection()) {
            message = String.format("Il metodo usato da questo test presuppone che esista la collection '%s' che invece ?? assente", collectionName);
            System.out.println(message);
            message = String.format("Non esiste la collection '%s' della classe [%s]", collectionName, clazzName);
            System.out.println(message);
            return;
        }

        listaStr = crudBackend.findAllForKeyReverseOrder();
        assertNotNull(listaStr);
        ottenutoIntero = listaStr.size();
        sorgente = textService.format(ottenutoIntero);
        sorgente2 = keyPropertyName;
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities. Valori (String) del campo chiave '%s' in ordine inverso:", collectionName, clazzName, sorgente, sorgente2);
        System.out.println(message);

        printSubLista(listaStr);
    }

    @Test
    @Order(40)
    @DisplayName("40 - toString")
    protected void toStringTest() {
        System.out.println("40 - toString");
        System.out.println(VUOTA);

        sorgente = "Topo Lino";

        if (annotationService.usaKeyPropertyName(entityClazz)) {
            keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        }
        else {
            message = String.format("Nella entityClazz [%s] la keyProperty non ?? prevista", clazzName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(keyPropertyName = \"nome\")");
            System.out.println(message);
            return;
        }

        if (reflectionService.isEsisteMetodoConParametri(crudBackend.getClass(), METHOD_NAME_NEW_ENTITY, 1)) {
            try {
                entityBean = crudBackend.newEntity(sorgente);
            } catch (Exception unErrore) {
                message = String.format("Non sono riuscito a creare una entityBean della classe [%s] col metodo newEntity() ad un solo parametro", clazzName);
                System.out.println(message);
                message = String.format("Probabilmente il valore [%s] usato per la keyPropertyName '%s' non ?? adeguato", sorgente, keyPropertyName);
                System.out.println(message);
                message = String.format("Devi scrivere un test alternativo per controllare la funzione toString() della classe [%s]", clazzName);
                System.out.println(message);
                return;
            }
            assertNotNull(entityBean);
            ottenuto = entityBean.toString();
            if (textService.isEmpty(ottenuto)) {
                message = String.format("Non esiste il valore toString() della entity appena creata di classe [%s]", clazzName);
                System.out.println(message);
                message = String.format("Devi creare/modificare il metodo [%s].toString()", clazzName);
                System.out.println(message);
            }
            assertTrue(textService.isValid(ottenuto));
            System.out.println(String.format("toString%s%s", FORWARD, ottenuto));
            return;
        }

        if (reflectionService.isEsisteMetodoConParametri(crudBackend.getClass(), METHOD_NAME_NEW_ENTITY, 0)) {
            entityBean = crudBackend.newEntity();
            assertNotNull(entityBean);
            assertNotNull(entityBean);
            ottenuto = entityBean.toString();
            if (textService.isEmpty(ottenuto)) {
                message = String.format("Non esiste il valore toString() della entity di classe [%s]", clazzName);
                System.out.println(message);
                message = String.format("Perch?? ?? stata creata con %s() senza parametri", METHOD_NAME_NEW_ENTITY);
                System.out.println(message);
                message = String.format("E quindi non ha recepito il valore del keyPropertyName '%s'", keyPropertyName);
                System.out.println(message);
            }
            return;
        }

        message = String.format("Questo test presuppone che esista il metodo '%s' nella classe [%s] con un parametro solo o senza", METHOD_NAME_NEW_ENTITY, backendName);
        System.out.println(message);
        message = String.format("Devi scrivere un test alternativo oppure modificare la classe [%s]", backendName);
        System.out.println(message);
        message = String.format("Aggiungendo un metodo '%s' senza parametri oppure con un parametro", METHOD_NAME_NEW_ENTITY);
        System.out.println(message);
    }

    @Test
    @Order(41)
    @DisplayName("41 - newEntity con ID ma non registrata")
    protected void newEntity() {
        System.out.println("41 - newEntity con ID ma non registrata");
        System.out.println(VUOTA);
        String keyPropertyName = VUOTA;

        sorgente = "Topo Lino";
        previsto = "topolino";
        previsto2 = "Topo Lino";

        if (annotationService.usaKeyPropertyName(entityClazz)) {
            keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        }

        if (reflectionService.isEsisteMetodoConParametri(crudBackend.getClass(), METHOD_NAME_NEW_ENTITY, 1)) {
            entityBean = crudBackend.newEntity(getParamEsistente());
            assertNotNull(entityBean);
            ottenuto = entityBean.id;
            ottenuto2 = reflectionService.getPropertyValueStr(entityBean, keyPropertyName);
            if (annotationService.usaKeyPropertyName(entityClazz)) {
                if (textService.isEmpty(ottenuto)) {
                    message = String.format("La entity appena creata ?? senza keyID mentre dovrebbe essere id=%s (valore del field %s)", sorgente, keyPropertyName);
                    System.out.println(message);
                    message = String.format("Molto probabilmente manca la chiusura del metodo base newEntity -> 'return (%s) fixKey(newEntityBean);' ", clazzName);
                    System.out.println(message);
                }

                assertEquals(previsto, ottenuto);
                assertEquals(previsto2, ottenuto2);
                assertTrue(textService.isValid(ottenuto));
                System.out.println(String.format("KeyId%s%s", FORWARD, ottenuto));
            }
            return;
        }

        if (reflectionService.isEsisteMetodoConParametri(crudBackend.getClass(), METHOD_NAME_NEW_ENTITY, 0)) {
            entityBean = crudBackend.newEntity();
            assertNotNull(entityBean);
            ottenuto = entityBean.id;
            assertTrue(textService.isEmpty(ottenuto));
            message = String.format("Nella classe [%s] esiste il metodo '%s' ma senza parametri", backendName, METHOD_NAME_NEW_ENTITY);
            System.out.println(message);
            if (annotationService.usaKeyPropertyName(entityClazz)) {
                message = String.format("Non ?? quindi possibile creare e testare una nuova entity che utilizzi la keyPropertyName '%s' come ID", keyPropertyName);
                System.out.println(message);
            }
            else {
                message = String.format("La classe non utilizza una keyPropertyName e la entity '%s' ?? senza keyID", entityBean);
                System.out.println(message);
            }
            return;
        }

        message = String.format("Questo test presuppone che esista il metodo '%s' nella classe [%s] con un parametro solo o senza", METHOD_NAME_NEW_ENTITY, backendName);
        System.out.println(message);
        message = String.format("Devi scrivere un test alternativo oppure modificare la classe [%s]", backendName);
        System.out.println(message);
        message = String.format("Aggiungendo un metodo '%s' senza parametri oppure con un parametro", METHOD_NAME_NEW_ENTITY);
        System.out.println(message);
    }


    @Test
    @Order(42)
    @DisplayName("42 - CRUD operations")
    protected void crud() {
        System.out.println("42 - CRUD operations");
        System.out.println(VUOTA);

        if (!reflectionService.isEsisteMetodoConParametri(crudBackend.getClass(), METHOD_NAME_NEW_ENTITY, 1)) {
            message = String.format("Questo test presuppone che esista il metodo '%s' nella classe [%s] con un parametro", METHOD_NAME_NEW_ENTITY, backendName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la classe [%s]", backendName);
            System.out.println(message);
            message = String.format("Aggiungendo il metodo '%s' con un parametro", METHOD_NAME_NEW_ENTITY);
            System.out.println(message);
            return;
        }
        if (!annotationService.usaKeyPropertyName(entityClazz)) {
            System.out.println("Le operazioni CRUD standard di questo test presuppongono che esista una keyProperty");

            message = String.format("Nella entityClazz [%s] la keyProperty non ?? prevista", clazzName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(keyPropertyName = \"nome\")");
            System.out.println(message);
            return;
        }

        sorgente = "Topo Lino";
        previsto = "topolino";
        previsto2 = "Giuseppe";

        ottenutoBooleano = crudBackend.isExistId(sorgente);
        assertFalse(ottenutoBooleano);
        message = String.format("1) isExistId -> Nella collection '%s' non esiste (false) la entity [%s]", collectionName, sorgente);
        System.out.println(message);

        ottenutoBooleano = crudBackend.creaIfNotExist(sorgente);
        assertTrue(ottenutoBooleano);
        message = String.format("2) creaIfNotExist -> Nella collection '%s' ?? stata creata (true) la entity [%s].%s che prima non esisteva", collectionName, previsto, sorgente);
        System.out.println(message);

        ottenutoBooleano = crudBackend.isExistId(previsto);
        assertTrue(ottenutoBooleano);
        message = String.format("3) isExistId -> Controllo l'esistenza (true) della entity [%s].%s tramite l'ID", previsto, sorgente);
        System.out.println(message);

        System.out.println(VUOTA);

        ottenutoBooleano = crudBackend.creaIfNotExist(sorgente);
        assertFalse(ottenutoBooleano);
        message = String.format("4) creaIfNotExist -> La entity [%s].%s esisteva gi?? e non ?? stata creata (false)", previsto, sorgente);
        System.out.println(message);

        System.out.println(VUOTA);

        ottenutoBooleano = crudBackend.isExistId(previsto);
        assertTrue(ottenutoBooleano);
        message = String.format("5) isExistId -> Controllo l'esistenza (true) della entity [%s].%s tramite l'ID", previsto, sorgente);
        System.out.println(message);
        ottenutoBooleano = crudBackend.isExistKey(sorgente);
        message = String.format("6) isExistKey -> Esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", previsto, sorgente, sorgente, keyPropertyName);
        assertTrue(ottenutoBooleano);
        System.out.println(message);
        ottenutoBooleano = crudBackend.isExistKey(previsto2);
        assertFalse(ottenutoBooleano);
        message = String.format("7) isExistKey -> Non esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", previsto, previsto2, previsto2, keyPropertyName);
        System.out.println(message);
        ottenutoBooleano = crudBackend.isExistProperty(keyPropertyName, sorgente);
        message = String.format("8) isExistProperty -> Esiste la entity [%s].%s individuata dal valore '%s' della property [%s]", previsto, previsto2, sorgente, keyPropertyName);
        assertTrue(ottenutoBooleano);
        System.out.println(message);

        entityBean = crudBackend.findById(previsto);
        assertNotNull(entityBean);
        message = String.format("9) findById -> Recupero la entity [%s].%s dalla keyID", previsto, sorgente);
        System.out.println(message);

        entityBean = crudBackend.findByKey(sorgente);
        assertNotNull(entityBean);
        message = String.format("10) findByKey -> Recupero la entity [%s].%s dal valore '%s' della keyProperty [%s]", previsto, sorgente, sorgente, keyPropertyName);
        System.out.println(message);
        entityBean = crudBackend.findByProperty(keyPropertyName, sorgente);
        assertNotNull(entityBean);
        message = String.format("11) findByProperty -> Recupero la entity [%s].%s dal valore '%s' della property [%s]", previsto, sorgente, sorgente, keyPropertyName);
        System.out.println(message);

        System.out.println(VUOTA);

        reflectionService.setPropertyValue(entityBean, keyPropertyName, previsto2);
        entityBean = crudBackend.save(entityBean);
        assertNotNull(entityBean);
        assertEquals(previsto2, reflectionService.getPropertyValue(entityBean, keyPropertyName));
        entityBean = crudBackend.findById(previsto);
        assertNotNull(entityBean);
        assertEquals(previsto2, reflectionService.getPropertyValue(entityBean, keyPropertyName));
        message = String.format("12) save -> Modifica la entity [%s].%s in [%s].%s", previsto, sorgente, previsto, previsto2);
        System.out.println(message);

        ottenutoBooleano = crudBackend.isExistKey(sorgente);
        message = String.format("13) isExistKey -> Non esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", previsto, sorgente, sorgente, keyPropertyName);
        assertFalse(ottenutoBooleano);
        System.out.println(message);
        ottenutoBooleano = crudBackend.isExistKey(previsto2);
        assertTrue(ottenutoBooleano);
        message = String.format("14) isExistKey -> Esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", previsto, previsto2, previsto2, keyPropertyName);
        System.out.println(message);
        ottenutoBooleano = crudBackend.isExistProperty(keyPropertyName, previsto2);
        message = String.format("15) isExistProperty -> Esiste la entity [%s].%s individuata dal valore '%s' della property [%s]", previsto, previsto2, previsto2, keyPropertyName);
        assertTrue(ottenutoBooleano);
        System.out.println(message);

        System.out.println(VUOTA);

        ottenutoBooleano = crudBackend.delete(entityBean);
        assertTrue(ottenutoBooleano);
        message = String.format("16) delete -> Cancello la entity [%s].%s", previsto, previsto2);
        System.out.println(message);

        ottenutoBooleano = crudBackend.isExistId(previsto);
        message = String.format("17) isExistId -> Alla fine, nella collection '%s' non esiste pi?? la entity [%s] che ?? stata cancellata", collectionName, previsto);
        System.out.println(message);
    }

    //Segnaposto
    @Order(51)
    protected void libero51() {
    }

    //Segnaposto
    @Order(52)
    protected void libero52() {
    }


    //Segnaposto
    @Order(53)
    protected void libero53() {
    }

    //Segnaposto
    @Order(54)
    protected void libero54() {
    }

    //Segnaposto
    @Order(55)
    protected void libero55() {
    }

    //Segnaposto
    @Order(61)
    protected void libero61() {
    }

    //Segnaposto
    @Order(62)
    protected void libero62() {
    }


    //Segnaposto
    @Order(63)
    protected void libero63() {
    }


    //Segnaposto
    @Order(71)
    protected void libero71() {
    }

    //Segnaposto
    @Order(72)
    protected void libero72() {
    }


    //Segnaposto
    @Order(73)
    protected void libero73() {
    }


    //Segnaposto
    @Order(81)
    protected void libero81() {
    }

    //Segnaposto
    @Order(82)
    protected void libero82() {
    }


    //Segnaposto
    @Order(83)
    protected void libero83() {
    }

    @Test
    @Order(91)
    @DisplayName("91 - resetOnlyEmpty")
    protected void resetOnlyEmpty() {
        System.out.println("91 - resetOnlyEmpty");
        System.out.println(VUOTA);

        if (!annotationService.usaReset(entityClazz)) {
            message = String.format("Questo test presuppone che la entity [%s] preveda la funzionalit?? '%s'", clazzName, METHOD_NAME_RESET_ONLY);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entity [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(usaReset = true)");
            System.out.println(message);
            return;
        }

        ottenutoRisultato = crudBackend.resetOnlyEmpty();
        assertNotNull(ottenutoRisultato);
        if (ottenutoRisultato.isValido()) {
            System.out.println(ottenutoRisultato.getMessage());
            printRisultato(ottenutoRisultato);

            System.out.println(VUOTA);
            printBackend(ottenutoRisultato.getLista());
        }
        else {
            logger.warn(new WrapLog().message(ottenutoRisultato.getErrorMessage()));
        }
        assertTrue(ottenutoRisultato.isValido());
    }


    /**
     * Chiamato solo dalla sottoclasse
     */
    protected void resetForcing() {
        System.out.println("92 - resetForcing");
        System.out.println(VUOTA);

        if (!annotationService.usaReset(entityClazz)) {
            message = String.format("Questo test presuppone che la entity [%s] preveda la funzionalit?? '%s'", clazzName, METHOD_NAME_RESET_ONLY);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entity [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(usaReset = true)");
            System.out.println(message);
            return;
        }

        ottenutoRisultato = crudBackend.resetForcing();
        assertNotNull(ottenutoRisultato);
        if (ottenutoRisultato.isValido()) {
            System.out.println(ottenutoRisultato.getMessage());
            printRisultato(ottenutoRisultato);

            System.out.println(VUOTA);
            printBackend(ottenutoRisultato.getLista());
        }
        else {
            logger.warn(new WrapLog().message(ottenutoRisultato.getErrorMessage()));
        }
        assertTrue(ottenutoRisultato.isValido());
    }

    protected Object getParamEsistente() {
        return sorgente;
    }

    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
    }


    protected void printBackend(final List lista) {
        printBackend(lista, 10);
    }


    protected void printBackend(final List lista, int max) {
        String message = VUOTA;
        int cont = 1;
        int tot;

        if (lista != null) {
            if (lista.size() > 0) {
                tot = Math.min(lista.size(), max);
                message = String.format("La lista contiene %d elementi.", lista.size());
                if (lista.size() > tot) {
                    message += String.format(" Mostro solo i primi %d", tot);
                }
                System.out.println(message);

                switch (typeBackend) {
                    case giorno -> printTestaGiorno();
                    case mese -> {
                        System.out.print("ordine");
                        System.out.print(SEP);
                        System.out.print("breve");
                        System.out.print(SEP);
                        System.out.print("nome");
                        System.out.print(SEP);
                        System.out.print("giorni");
                        System.out.print(SEP);
                        System.out.print("primo");
                        System.out.print(SEP);
                        System.out.println("ultimo");
                    }
                    case secolo -> {
                        System.out.print("ordine");
                        System.out.print(SEP);
                        System.out.print("nome");
                        System.out.print(SEP);
                        System.out.print("inizio");
                        System.out.print(SEP);
                        System.out.print("fine");
                        System.out.print(SEP);
                        System.out.println("avanti Cristo");
                    }
                    case anno -> printTestaAnno();
                    case nota -> {
                        System.out.print("type");
                        System.out.print(SEP);
                        System.out.print("livello");
                        System.out.print(SEP);
                        System.out.print("inizio");
                        System.out.print(SEP);
                        System.out.print("descrizione");
                        System.out.print(SEP);
                        System.out.print("fatto");
                        System.out.print(SEP);
                        System.out.println("fine");
                    }
                    default -> {}
                } ;
                System.out.println(VUOTA);

                for (Object obj : lista.subList(0, tot)) {
                    System.out.print(cont);
                    System.out.print(PARENTESI_TONDA_END);
                    System.out.print(SPAZIO);
                    switch (typeBackend) {
                        case giorno -> printGiorno(obj);
                        case mese -> printMese(obj);
                        case secolo -> printSecolo(obj);
                        case anno -> printAnno(obj);
                        case nota -> printNota(obj);
                        default -> {
                            System.out.println(obj);
                        }
                    } ;
                    cont++;
                }
                if (lista.size() > tot) {
                    System.out.print(cont);
                    System.out.print(PARENTESI_TONDA_END);
                    System.out.print(SPAZIO);
                    System.out.println(TRE_PUNTI);
                }
            }
            else {
                System.out.println("Non ci sono elementi nella lista");
            }
        }
        else {
            System.out.println("Manca la lista");
        }
    }

    protected void printTestaGiorno() {
        System.out.print("ordine");
        System.out.print(SEP);
        System.out.print("nome");
        System.out.print(SEP);
        System.out.print("Trascorsi");
        System.out.print(SEP);
        System.out.print("mancanti");
        System.out.println(SPAZIO);
    }

    protected void printGiorno(Object obj) {
        if (obj instanceof Giorno giorno) {
            System.out.print(giorno.nome);
            System.out.print(SEP);
            System.out.print(giorno.trascorsi);
            System.out.print(SEP);
            System.out.print(giorno.mancanti);
            System.out.println(SPAZIO);
        }
    }

    protected void printGiorni(List<Giorno> listaGiorni) {
        int k = 0;

        for (Giorno giorno : listaGiorni) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            printGiorno(giorno);
        }
    }


    protected void printMese(Object obj) {
        if (obj instanceof Mese mese) {
            System.out.print(mese.breve);
            System.out.print(SEP);
            System.out.print(mese.nome);
            System.out.print(SEP);
            System.out.print(mese.giorni);
            System.out.print(SEP);
            System.out.print(mese.primo);
            System.out.print(SEP);
            System.out.print(mese.ultimo);
            System.out.println(SPAZIO);
        }
    }

    protected void printMesi(List<Mese> listaMesi) {
        int k = 0;

        for (Mese mese : listaMesi) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            printMese(mese);
        }
    }

    protected void printSecolo(Object obj) {
        if (obj instanceof Secolo secolo) {
            System.out.print(secolo.nome);
            System.out.print(SEP);
            System.out.print(secolo.inizio);
            System.out.print(SEP);
            System.out.print(secolo.fine);
            System.out.print(SEP);
            System.out.print(secolo.anteCristo ? true : false);
            System.out.println(SPAZIO);
        }
    }

    protected void printTestaAnno() {
        System.out.print("ordine");
        System.out.print(SEP);
        System.out.print("nome");
        System.out.print(SEP);
        System.out.print("secolo");
        System.out.print(SEP);
        System.out.print("dopoCristo");
        System.out.print(SEP);
        System.out.print("bisestile");
        System.out.println(SPAZIO);
    }

    protected void printAnno(Object obj) {
        if (obj instanceof Anno anno) {
            System.out.print(anno.nome);
            System.out.print(SPAZIO);
            System.out.print(anno.secolo);
            System.out.print(SPAZIO);
            System.out.print(anno.dopoCristo ? true : false);
            System.out.print(SPAZIO);
            System.out.print(anno.bisestile ? true : false);
            System.out.println(SPAZIO);
        }
    }


    protected void printNota(Object obj) {
        if (obj instanceof Nota nota) {
            System.out.print(nota.type);
            System.out.print(SPAZIO);
            System.out.print(nota.livello);
            System.out.print(SPAZIO);
            System.out.print(dateService.get(nota.inizio));
            System.out.print(SPAZIO);
            System.out.print(nota.descrizione);
            System.out.print(SPAZIO);
            System.out.print(nota.livello);
            System.out.print(SPAZIO);
            System.out.print(nota.fatto ? true : false);
            System.out.print(SPAZIO);
            System.out.print(dateService.get(nota.fine));
            System.out.println(SPAZIO);
        }
    }

    protected void printNota() {
        System.out.print("type");
        System.out.print(SEP);
        System.out.print("livello");
        System.out.print(SEP);
        System.out.print("inizio");
        System.out.print(SEP);
        System.out.print("descrizione");
        System.out.print(SEP);
        System.out.print("fatto");
        System.out.print(SEP);
        System.out.print("fine");
        System.out.println(SPAZIO);
    }

    protected enum TypeBackend {nessuno, via, anno, giorno, mese, secolo, continente, nota, versione, logger}

}