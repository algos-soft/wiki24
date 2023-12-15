package it.algos.base24.service;

import com.opencsv.*;
import com.opencsv.bean.*;
import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.packages.anagrafica.via.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.base24.basetest.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 22-ott-2023
 * Time: 20:02
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Resource Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourceServiceTest extends ServiceTest {

    @Autowired
    private ResourceService service;

    @Autowired
    private TextService textService;

    //--nome
    //--esiste
    //--testo
    private Stream<Arguments> dirConfig() {
        return Stream.of(
                Arguments.of(VUOTA, false, VUOTA),
                Arguments.of("config.password.txt", false, VUOTA),
                Arguments.of("/config.password.txt", false, VUOTA),
                Arguments.of("/config/password.txt", false, VUOTA),
                Arguments.of("/config/vie", false, VUOTA),
                Arguments.of("/config/vie.txt", false, VUOTA),
                Arguments.of("vie.txt", false, VUOTA),
                Arguments.of("vie", false, VUOTA),
                Arguments.of("continenti.csv", false, VUOTA),
                Arguments.of("secoli.csv", true, VUOTA),
                Arguments.of("vie.csv", true, "nome\nvia,\nlargo,\ncorso,\npiazza,\nviale")
        );
    }


    //--nome
    //--esiste
    //--testo
    private Stream<Arguments> dirServer() {
        return Stream.of(
                Arguments.of(VUOTA, false, VUOTA),
                Arguments.of("config.password.txt", false, VUOTA),
                Arguments.of("/config.password.txt", false, VUOTA),
                Arguments.of("/config/password.txt", false, VUOTA),
                Arguments.of("/config/vie", false, VUOTA),
                Arguments.of("/config/vie.txt", false, VUOTA),
                Arguments.of("vie.txt", false, VUOTA),
                Arguments.of("vie", true, "nome\nvia,\nlargo,\ncorso,\npiazza,\nviale")
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ResourceService.class;
        super.setUpAll();
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(10)
    @DisplayName("10 - leggeConfig")
    void leggeConfig() {
        System.out.println(("10 - Legge il testo di una risorsa nella cartella 'config'"));
        System.out.println(VUOTA);

        //--nome
        //--esiste
        //--testo
        dirConfig().forEach(this::fixLeggeConfig);
    }

    //--nome
    //--esiste
    //--testo
    void fixLeggeConfig(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        previsto = (String) mat[2];
        String esistenza = previstoBooleano ? "Esiste" : "Non esiste";
        String inizio;
        int max = 38;

        ottenuto = service.leggeConfig(sorgente);
        assertEquals(previstoBooleano, textService.isValid(ottenuto));

        inizio = ottenuto != null ? ottenuto.substring(0, Math.min(max, ottenuto.length())).trim() : VUOTA;
        sorgente = textService.isValid(sorgente) ? sorgente : NULLO;
        inizio = textService.isValid(inizio) ? inizio : NULLO;
        if (!inizio.equals(NULLO)) {
            System.out.println(VUOTA);
        }
        message = String.format("[%s] %s%s%s", esistenza, sorgente, FORWARD, inizio);
        System.out.println(message);
    }

    @Test
    @Order(20)
    @DisplayName("20 - leggeServer")
    void leggeServer() {
        System.out.println(("20 - Legge il testo di una risorsa dal server 'algos'"));
        System.out.println(VUOTA);

        //--nome
        //--esiste
        //--testo
        dirServer().forEach(this::fixLeggeServer);
    }

    //--nome
    //--esiste
    //--testo
    void fixLeggeServer(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        previsto = (String) mat[2];
        String esistenza = previstoBooleano ? "Esiste" : "Non esiste";
        String inizio;
        int max = 38;

        ottenuto = service.leggeServer(sorgente);
        assertEquals(previstoBooleano, textService.isValid(ottenuto));

        inizio = ottenuto != null ? ottenuto.substring(0, Math.min(max, ottenuto.length())) : "";
        sorgente = textService.isValid(sorgente) ? sorgente : NULLO;
        inizio = textService.isValid(inizio) ? inizio : NULLO;
        message = String.format("[%s] %s%s%s", esistenza, sorgente, FORWARD, inizio);
        System.out.println(message);
    }


    @Test
    @Order(30)
    @DisplayName("30 - leggeListaConfig")
    void leggeListaConfig() {
        System.out.println(("30 - Legge la lista di una risorsa nella cartella 'config'"));
        System.out.println(VUOTA);

        //--nome
        //--esiste
        //--testo
        dirConfig().forEach(this::fixLeggeListaConfig);
    }

    //--nome
    //--esiste
    //--testo
    void fixLeggeListaConfig(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        previsto = (String) mat[2];
        String esistenza = previstoBooleano ? "Esiste" : "Non esiste";
        String inizio;
        String message = VUOTA;

        listaStr = service.leggeListaConfig(sorgente);
        assertEquals(previstoBooleano, listaStr != null);

        if (listaStr != null) {
            message = String.format("Nel file [%s] della cartella 'config' c'è una lista di %d elementi", sorgente, listaStr.size());
            System.out.println(message);
            for (String riga : listaStr) {
                System.out.println(riga); ;
            }
            System.out.println(VUOTA);
        }
    }


    @Test
    @Disabled("Disabled")
    @Order(40)
    @DisplayName("40 - leggeListaServer")
    void leggeListaServer() {
        System.out.println(("30 - Legge la lista di una risorsa dal server 'algos'"));
        System.out.println(VUOTA);

        //--nome
        //--esiste
        //--testo
        dirServer().forEach(this::fixLeggeListaServer);
    }

    //--nome
    //--esiste
    //--testo
    void fixLeggeListaServer(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        previsto = (String) mat[2];
        String esistenza = previstoBooleano ? "Esiste" : "Non esiste";
        String inizio;

        listaStr = service.leggeListaServer(sorgente);
        assertEquals(previstoBooleano, listaStr != null);

        if (listaStr != null) {
            message = String.format("Nel file [%s] del server 'algos' c'è una lista di %d elementi", sorgente, listaStr.size());
            System.out.println(message);
            for (String riga : listaStr) {
                System.out.println(riga); ;
            }
            System.out.println(VUOTA);
        }
    }


    @Test
    @Order(50)
    @DisplayName("50 - leggeMappaConfig")
    void leggeMappaConfig() {
        System.out.println(("50 - Legge la mappa di una risorsa nella cartella 'config'"));
        System.out.println(VUOTA);

        //--nome
        //--esiste
        //--testo
        dirConfig().forEach(this::fixLeggeMappaConfig);
    }

    //--nome
    //--esiste
    //--testo
    void fixLeggeMappaConfig(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        previsto = (String) mat[2];
        String esistenza = previstoBooleano ? "Esiste" : "Non esiste";
        String inizio;
        String message = VUOTA;

        mappa = service.leggeMappaConfig(sorgente);
        assertEquals(previstoBooleano, mappa != null);

        if (mappa != null) {
            message = String.format("Nel file [%s] della cartella 'config' c'è una mappa di %d elementi", sorgente, mappa.size());
            System.out.println(message);
            for (String key : mappa.keySet()) {
                System.out.print(key);
                System.out.print(FORWARD);
                System.out.println(mappa.get(key));
            }
            System.out.println(VUOTA);
        }
    }


    @Test
    @Disabled("Disabled")
    @Order(60)
    @DisplayName("60 - leggeMappaServer")
    void leggeMappaServer() {
        System.out.println(("60 - Legge la mappa di una risorsa dal server 'algos'"));
        System.out.println(VUOTA);

        //--nome
        //--esiste
        //--testo
        dirConfig().forEach(this::fixLeggeMappaServer);
    }

    //--nome
    //--esiste
    //--testo
    void fixLeggeMappaServer(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        previsto = (String) mat[2];
        String esistenza = previstoBooleano ? "Esiste" : "Non esiste";
        String inizio;

        mappa = service.leggeMappaServer(sorgente);

        if (mappa != null) {
            message = String.format("Nel file [%s] del server 'algos' c'è una mappa di %d elementi", sorgente, mappa.size());
            System.out.println(message);
            for (String key : mappa.keySet()) {
                System.out.print(key);
                System.out.print(FORWARD);
                System.out.println(mappa.get(key));
            }
            System.out.println(VUOTA);
        }
    }

    @Test
    @Order(70)
    @DisplayName("70 - getBean(Class.forName")
    void getBean() {
        System.out.println(("70 - getBean(Class.forName"));
        System.out.println(VUOTA);
        String path = "it.algos.base24.backend.packages.utility.role.RoleModulo";
        //        String path2 = "it.algos.prova.backend.boot.ProvaBoot";
        String path3 = "it.algos.base24.backend.boot.BaseBoot";
        Object bean = null;

        try {
            bean = applicationContext.getBean(Class.forName(path));
        } catch (Exception unErrore) {
            logger.warn(new WrapLog().message(unErrore.getMessage()));
        }
        System.out.println(String.format("Class.forName%s%s", FORWARD, bean.getClass().getSimpleName()));

        try {
            bean = applicationContext.getBean(Class.forName("ProvaBoot"));
        } catch (Exception unErrore) {
            logger.warn(new WrapLog().message(unErrore.getMessage()));
        }
        System.out.println(String.format("Class.forName%s%s", FORWARD, bean.getClass().getSimpleName()));

        try {
            bean = applicationContext.getBean(Class.forName(path3));
        } catch (Exception unErrore) {
            logger.warn(new WrapLog().message(unErrore.getMessage()));
        }
        System.out.println(String.format("Class.forName%s%s", FORWARD, bean.getClass().getSimpleName()));

        //        try {
        //            String bootServiceName = "ProvaBoot";
        //            Object currentBoot = applicationContext.getBean(Class.forName(bootServiceName));
        //            int a=87;
        //        } catch (Exception unErrore) {
        //            logger.warn(new WrapLog().message(unErrore.getMessage()));
        //        }
        //        System.out.println(String.format("Class.forName%s%s", FORWARD, bean.getClass().getSimpleName()));

    }

    //    @Test
    @Order(110)
    @DisplayName("110 - readAll con CSVReader")
    void readAll() {
        System.out.println(("110 - readAll"));
        System.out.println(VUOTA);
        //        String path = "config/continenti";
        String path = "config/vie";
        FileReader fileReader = null;
        List<String[]> allData = null;

        // Create an object of file reader class with CSV file as a parameter.
        try {
            fileReader = new FileReader(path);
        } catch (Exception unErrore) {

        }

        CSVReader csvReader = new CSVReaderBuilder(fileReader).build();

        // Read all data at once
        try {
            allData = csvReader.readAll();
        } catch (Exception unErrore) {
        }

        for (String[] riga : allData) {
            System.out.println(Arrays.stream(riga).toList());
        }
    }

    //    @Test
    @Order(111)
    @DisplayName("111 - readAllSkip con CSVReader")
    void readAllSkip() {
        System.out.println(("111 - readAllSkip"));
        System.out.println(VUOTA);
        //        String path = "config/continenti";
        String path = "config/vie";
        FileReader fileReader = null;
        List<String[]> allData = null;

        // Create an object of file reader class with CSV file as a parameter.
        try {
            fileReader = new FileReader(path);
        } catch (Exception unErrore) {

        }

        CSVReader csvReader = new CSVReaderBuilder(fileReader)
                .withSkipLines(1)
                .build();

        // Read all data at once
        try {
            allData = csvReader.readAll();
        } catch (Exception unErrore) {
        }

        // print Data
        for (String[] row : allData) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
    }


    @Test
    @Order(120)
    @DisplayName("120 - leggeListaCSV")
    void leggeListaCSV() {
        System.out.println(("120 - leggeListaCSV"));
        System.out.println(VUOTA);
        List<List<String>> listaCsv;

        sorgente = "/Users/gac/Documents/IdeaProjects/operativi/base24/config/continenti";
        listaCsv = service.leggeListaCSV(sorgente);
        assertFalse(listaCsv != null);

        sorgente = "/Users/gac/Documents/IdeaProjects/operativi/base24/config/vie";
        listaCsv = service.leggeListaCSV(sorgente);
        assertFalse(listaCsv != null);

        sorgente = "/Users/gac/Documents/IdeaProjects/operativi/base24/config/vie.csv";
        listaCsv = service.leggeListaCSV(sorgente);
        assertTrue(listaCsv != null);
        System.out.println((String.format("Ci sono %d elementi nel file [%s], situato%s%s", listaCsv.size(), "vie.csv", FORWARD, "config/vie.csv")));
        System.out.println(VUOTA);
        for (List<String> riga : listaCsv) {
            System.out.println(riga);
        }
    }

    //    @Test
    @Order(130)
    @DisplayName("130 - beanCsvBuilder")
    void beanCsvBuilder() throws IOException {
        System.out.println(("130 - beanCsvBuilder"));
        System.out.println(VUOTA);
        sorgente = "/Users/gac/Documents/IdeaProjects/operativi/base24/config/vie.csv";
        FileReader fileReader = null;
        List<String[]> allData = null;
        clazz = ViaEntity.class;
        Path path = Paths.get(sorgente);

        String path2 = "/Users/gac/Documents/IdeaProjects/operativi/base2023/config/continenti";

        CsvToBean<ContinenteModelTest> bean = null;

        try {
            Path path3 = Paths.get(ClassLoader.getSystemResource(path2).toURI());
        } catch (Exception unErrore) {
        }

        File initialFile = new File(sorgente);
        initialFile.createNewFile();
        Reader targetReader = new FileReader(initialFile);
        bean = new CsvToBeanBuilder<VieModelTest>(targetReader)
                .withType(clazz)
                .build();
        targetReader.close();
        bean.stream().forEach(bean2 -> System.out.println(bean2));
        try {
            listaEntity = service.beanCsvBuilder(path, clazz);
        } catch (Exception unErrore) {
            assertFalse(true);
        }

        try {
            Reader reader = Files.newBufferedReader(path);
            int a3 = 87;

            bean = new CsvToBeanBuilder<ContinenteModelTest>(reader)
                    .withType(clazz)
                    .build();
            int a = 87;
        } catch (Exception unErrore) {
            assertFalse(true);
        }

        //        bean.stream().forEach(bean2 -> System.out.println(bean2));
        listaEntity.stream().forEach(bean2 -> System.out.println(bean2));
    }


    public class VieModelTest {


        @CsvBindByName
        public String nome;


    }// end of CrudModel class


    public class ContinenteModelTest {

        @CsvBindByName
        public int ordine;

        @CsvBindByName
        public String nome;

        @CsvBindByName
        public boolean abitato;

    }// end of CrudModel class

}
