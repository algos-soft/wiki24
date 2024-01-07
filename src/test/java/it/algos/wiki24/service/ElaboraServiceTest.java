package it.algos.wiki24.service;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.service.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 26-Dec-2023
 * Time: 17:11
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Elabora Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ElaboraServiceTest extends WikiTest {

    @Inject
    private ElaboraService service;

    @Inject
    TextService textService;

    @Inject
    BioServerModulo bioServerModulo;

    @Inject
    BioMongoModulo bioMongoModulo;

    @Inject
    private BioServerEntity bioServerEntity;

    @Inject
    private BioMongoEntity bioMongoEntity;

    private Map<String, String> mappaBio;

    //--wikiTitle
    //--previsto nato
    //--previsto morto
    protected static Stream<Arguments> GIORNO() {
        return Stream.of(
                Arguments.of("Malcolm IV di Scozia", VUOTA, "9 dicembre"),
                Arguments.of("William Dobson", "4 marzo", "28 ottobre"),
                Arguments.of("Carolina Matilde di Hannover", "22 luglio", "10 maggio"),
                Arguments.of("Giuseppe Lillo", VUOTA, "4 febbraio"),
                Arguments.of("Hendrick Goltzius", VUOTA, "1º gennaio"),
                Arguments.of("Eraldo Da Roma", "1º marzo", "27 marzo"),
                Arguments.of("Marcello Barlocco", "1º marzo", "12 novembre"),
                Arguments.of("Orazio Capuana Yaluna", "1º marzo", VUOTA),
                Arguments.of("Cecilia di York", "20 marzo", "24 agosto"),
                Arguments.of("Papa Leone II", VUOTA, VUOTA),
                Arguments.of("Deep Roy", "1º dicembre", VUOTA),
                Arguments.of("Nanuka Gogichaishvili", "1º maggio", VUOTA),
                Arguments.of("Yui Natsukawa", "1º giugno", VUOTA),
                Arguments.of("Jacobus Boonen", "11 ottobre", "30 giugno"),
                Arguments.of("Max Isidor Bodenheimer", "12 marzo", "19 luglio"),
                Arguments.of("Gabriele Oriali", "25 novembre", VUOTA),
                Arguments.of("Hinako Sugiura", "30 novembre", "22 luglio"),
                Arguments.of("Maurice O'Fihely", VUOTA, "25 marzo"),
                Arguments.of("Aleksandr Michajlovič Sibirjakov", VUOTA, "2 novembre"),
                Arguments.of("Elizaveta Andreevna Šuvalova", "25 luglio", "28 luglio"),
                Arguments.of("Rudol'f Lazarevič Samojlovič", VUOTA, "4 marzo"),
                Arguments.of("Pak Ui-chun", VUOTA, VUOTA),
                Arguments.of("Zheng Junli", "6 dicembre", "23 aprile"),
                Arguments.of("Bradley Fuller", "5 novembre", VUOTA),
                Arguments.of("TomSka", "27 giugno", VUOTA),
                Arguments.of("Pierre Baour-Lormian", "24 marzo", "18 dicembre"),
                Arguments.of("Angelina Lanza Damiani", "13 febbraio", "14 luglio"),
                Arguments.of("Torquato Nanni", "4 febbraio", "22 aprile"),
                Arguments.of("John Arbuthnot", "29 aprile", "27 febbraio"),
                Arguments.of("Fabrizio Zani", "9 giugno", VUOTA),
                Arguments.of("Piera Oppezzo", "2 agosto", "19 dicembre"),
                Arguments.of("Giovanni Silvestri (editore)", VUOTA, "9 settembre"),
                Arguments.of("Stefan Merrill Block", "26 febbraio", VUOTA),
                Arguments.of("Maurizio Roffredi", "22 novembre", "3 maggio"),
                Arguments.of("Aleksandr Ivanovič Vvedenskij", "23 novembre", "20 dicembre"),
                Arguments.of("Vincenzo Massabò", "4 novembre", "20 giugno"),
                Arguments.of("Sergej Nikolaevič Bulgakov", "28 luglio", "12 luglio"),
                Arguments.of("Sof'ja Stepanovna Razumovskaja", VUOTA, VUOTA),
                Arguments.of("Aleksej Feofilaktovič Pisemskij", "22 marzo", "2 febbraio")
        );
    }


    //--wikiTitle
    //--previsto nato
    //--previsto morto
    protected static Stream<Arguments> ANNO() {
        return Stream.of(
                Arguments.of("Malcolm IV di Scozia", "1141", "1165"),
                Arguments.of("William Dobson", "1610", "1646"),
                Arguments.of("Carolina Matilde di Hannover", "1751", "1775"),
                Arguments.of("Jacopo Palma il Vecchio", VUOTA, "1528"),
                Arguments.of("Pietro Nolasco", VUOTA, "1256"),
                Arguments.of("Pedro de Candia", VUOTA, "1542"),
                Arguments.of("Publio Ovidio Nasone", "43 a.C.", VUOTA),
                Arguments.of("Marco Sciarra", VUOTA, "1593"),
                Arguments.of("Fra Bevignate", VUOTA, VUOTA),
                Arguments.of("Giuditta di Baviera", "800", "843"),
                Arguments.of("Vasilij Ivanovič Baženov", VUOTA, "1799"),
                Arguments.of("Aleksej Feofilaktovič Pisemskij", "1821", "1881")
        );
    }

    //--wikiTitle
    //--previsto nato
    //--previsto morto
    protected static Stream<Arguments> LUOGO() {
        return Stream.of(
                Arguments.of("Charles Yuill", "[[Greater Madawaska|Calabogie]]", "[[Barrhead (Alberta)|Barrhead]]")
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = ElaboraService.class;
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

        this.mappaBio = null;
        this.bioServerEntity = null;
        this.bioMongoEntity = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - costruttore")
    void costruttoreBase() {
        System.out.println(("1 - Costruttore base"));
        System.out.println(VUOTA);

        System.out.println(String.format("La classe [%s] è un SINGLETON (service) ed ha un costruttore SENZA parametri", clazzName));
        System.out.println("Viene creata da SpringBoot");
    }

    @Test
    @Order(2)
    @DisplayName("2 - getBean")
    void getBean() {
        System.out.println(("2 - getBean"));
        System.out.println(VUOTA);

        System.out.println(String.format("La classe [%s] è un SINGLETON (service)", clazzName));
        System.out.println("Viene creata da SpringBoot");
        System.out.println(String.format("Non si può usare appContext.getBean(%s.class)", clazzName));
    }

    @ParameterizedTest
    @MethodSource(value = "BIOGRAFIE")
    @Order(101)
    @DisplayName("101 - estraeMappa")
        //--wikiTitle
        //--numero parametri
    void estraeMappa(String wikiTitleVoce, int numParametri) {
        System.out.println(("101 - estraeMappa"));
        sorgente = wikiTitleVoce;
        previstoIntero = numParametri;

        bioServerEntity = bioServerModulo.findByWikiTitle(sorgente);
        if (bioServerEntity != null) {
            assertNotNull(bioServerEntity);
            sorgente2 = bioServerEntity.tmplBio;
            mappaBio = service.estraeMappa(sorgente2);
            assertNotNull(mappaBio);
            printMappa(mappaBio);
            assertEquals(previstoIntero, mappaBio.size());
        }
    }


    //    @ParameterizedTest
    @MethodSource(value = "BIOGRAFIE")
    @Order(102)
    @DisplayName("102 - creaBeanMongo")
    //--wikiTitle
    //--numero parametri
    void creaBeanMongo(String wikiTitleVoce, int numParagrafi) {
        System.out.println(("102 - creaBeanMongo"));
        sorgente = wikiTitleVoce;
        previstoIntero = numParagrafi;

        bioServerEntity = bioServerModulo.findByWikiTitle(sorgente);
        bioMongoEntity = service.creaBeanMongo(bioServerEntity);
        if (bioMongoEntity != null) {
            printBioMongo(bioMongoEntity);
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LUOGO")
    @Order(503)
    @DisplayName("503 - luogoNato")
        //--wikiTitle
        //--previsto nato
        //--previsto morto
    void luogoNato(String wikiTitleVoce, String luogoNato, String nonUsato) {
        System.out.println(("503 - luogoNato"));
        sorgente = wikiTitleVoce;
        previsto = luogoNato;

        bioServerEntity = bioServerModulo.findByWikiTitle(sorgente);
        assertNotNull(bioServerEntity);
        mappaBio = service.estraeMappa(bioServerEntity);
        sorgente2 = bioServerEntity.wikiTitle;
        sorgente3 = mappaBio.get(KEY_MAPPA_LUOGO_NASCITA);
        sorgente4 = mappaBio.get(KEY_MAPPA_LUOGO_NASCITA_LINK);

        ottenuto = service.fixLuogo(sorgente2, sorgente3, sorgente4);
        previsto = textService.isValid(previsto) ? previsto : NULLO;
        ottenuto = textService.isValid(ottenuto) ? ottenuto : NULLO;
        message = String.format("%s%s%s", previsto, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }

    //    @ParameterizedTest
    @MethodSource(value = "GIORNO")
    @Order(504)
    @DisplayName("504 - giornoNato")
    //--wikiTitle
    //--previsto nato
    //--previsto morto
    void giornoNato(String wikiTitleVoce, String giornoNato, String nonUsato) {
        System.out.println(("504 - giornoNato"));
        sorgente = wikiTitleVoce;
        previsto = giornoNato;

        bioServerEntity = bioServerModulo.findByWikiTitle(sorgente);
        assertNotNull(bioServerEntity);
        mappaBio = service.estraeMappa(bioServerEntity);
        sorgente2 = bioServerEntity.wikiTitle;
        sorgente3 = mappaBio.get(KEY_MAPPA_GIORNO_NASCITA);

        ottenuto = service.fixGiorno(sorgente2, sorgente3);
        previsto = textService.isValid(previsto) ? previsto : NULLO;
        ottenuto = textService.isValid(ottenuto) ? ottenuto : NULLO;
        message = String.format("%s%s%s", previsto, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }


        @ParameterizedTest
    @MethodSource(value = "ANNO")
    @Order(505)
    @DisplayName("505 - annoNato")
    //--wikiTitle
    //--previsto nato
    //--previsto morto
    void annoNato(String wikiTitleVoce, String annoNato, String nonUsato) {
        System.out.println(("505 - annoNato"));
        sorgente = wikiTitleVoce;
        previsto = annoNato;

        bioServerEntity = bioServerModulo.findByWikiTitle(sorgente);
        assertNotNull(bioServerEntity);
        mappaBio = service.estraeMappa(bioServerEntity);
        sorgente2 = bioServerEntity.wikiTitle;
        sorgente3 = mappaBio.get(KEY_MAPPA_ANNO_NASCITA);

        ottenuto = service.fixAnno(sorgente2, sorgente3);
        previsto = textService.isValid(previsto) ? previsto : NULLO;
        ottenuto = textService.isValid(ottenuto) ? ottenuto : NULLO;
        message = String.format("%s%s%s", previsto, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "LUOGO")
    @Order(506)
    @DisplayName("506 - luogoMorto")
        //--wikiTitle
        //--previsto nato
        //--previsto morto
    void luogoMorto(String wikiTitleVoce, String nonUsato, String luogoMorto) {
        System.out.println(("506 - giornoMorto"));
        sorgente = wikiTitleVoce;
        previsto = luogoMorto;

        bioServerEntity = bioServerModulo.findByWikiTitle(sorgente);
        assertNotNull(bioServerEntity);
        mappaBio = service.estraeMappa(bioServerEntity);
        sorgente2 = bioServerEntity.wikiTitle;
        sorgente3 = mappaBio.get(KEY_MAPPA_LUOGO_MORTE);
        sorgente4 = mappaBio.get(KEY_MAPPA_LUOGO_MORTE_LINK);

        ottenuto = service.fixLuogo(sorgente2, sorgente3, sorgente4);
        previsto = textService.isValid(previsto) ? previsto : NULLO;
        ottenuto = textService.isValid(ottenuto) ? ottenuto : NULLO;
        message = String.format("%s%s%s", previsto, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }


    //    @ParameterizedTest
    @MethodSource(value = "GIORNO")
    @Order(507)
    @DisplayName("507 - giornoMorto")
    //--wikiTitle
    //--previsto nato
    //--previsto morto
    void giornoMorto(String wikiTitleVoce, String nonUsato, String giornoMorto) {
        System.out.println(("507 - giornoMorto"));
        sorgente = wikiTitleVoce;
        previsto = giornoMorto;

        bioServerEntity = bioServerModulo.findByWikiTitle(sorgente);
        assertNotNull(bioServerEntity);
        mappaBio = service.estraeMappa(bioServerEntity);
        sorgente2 = bioServerEntity.wikiTitle;
        sorgente3 = mappaBio.get(KEY_MAPPA_GIORNO_MORTE);

        ottenuto = service.fixGiorno(sorgente2, sorgente3);
        previsto = textService.isValid(previsto) ? previsto : NULLO;
        ottenuto = textService.isValid(ottenuto) ? ottenuto : NULLO;
        message = String.format("%s%s%s", previsto, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }


    //    @ParameterizedTest
    @MethodSource(value = "ANNO")
    @Order(508)
    @DisplayName("508 - annoMorto")
    //--wikiTitle
    //--previsto nato
    //--previsto morto
    void annoMorto(String wikiTitleVoce, String nonUsato, String annoMorto) {
        System.out.println(("508 - annoMorto"));
        sorgente = wikiTitleVoce;
        previsto = annoMorto;

        bioServerEntity = bioServerModulo.findByWikiTitle(sorgente);
        assertNotNull(bioServerEntity);
        mappaBio = service.estraeMappa(bioServerEntity);
        sorgente2 = bioServerEntity.wikiTitle;
        sorgente3 = mappaBio.get(KEY_MAPPA_ANNO_MORTE);

        ottenuto = service.fixAnno(sorgente2, sorgente3);
        previsto = textService.isValid(previsto) ? previsto : NULLO;
        ottenuto = textService.isValid(ottenuto) ? ottenuto : NULLO;
        message = String.format("%s%s%s", previsto, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }


}
