package it.algos.wiki24.basetest;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import org.junit.jupiter.params.provider.*;
import java.util.stream.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: ven, 29-apr-2022
 * Time: 20:57
 * Classe astratta che contiene le regolazioni essenziali <br>
 */
public abstract class WikiStreamTest extends WikiTest {


    //--nome lista
    //--typeLista per il test
    //--esiste pagina lista
    //--esistono sottoPagine
    //--esistono sottoSottoPagine
    protected static Stream<Arguments> LISTA_LIVELLO_PAGINA() {
        return Stream.of(
//                Arguments.of(VUOTA, TypeLista.giornoNascita, false, false, false),
//                Arguments.of("34 marzo", TypeLista.giornoMorte, false, false, false),
//                Arguments.of("34 febbraio", TypeLista.giornoMorte, false, false, false),
//                Arguments.of("38 A.C.", TypeLista.annoNascita, false, false, false),
//                Arguments.of("38 a.C.", TypeLista.annoNascita, true, false, false),
//                Arguments.of("29 febbraio", TypeLista.giornoNascita, true, false, false),
//                Arguments.of("406 a.C.", TypeLista.annoMorte, true, false, false),
//                Arguments.of("1567", TypeLista.annoNascita, true, false, false),
//                Arguments.of("1610", TypeLista.annoMorte, true, false, false),
//                Arguments.of("560", TypeLista.annoMorte, true, false, false),
//                Arguments.of("1968", TypeLista.annoMorte, true, true, false),
//                Arguments.of("dominicani", TypeLista.nazionalitaPlurale, true, true, false),
//                Arguments.of("abati e badesse", TypeLista.attivitaPlurale, true, true, false),
//                Arguments.of("agronomi", TypeLista.attivitaPlurale, true, true, false),
//                Arguments.of("brasiliani", TypeLista.nazionalitaPlurale, true, true, true),
//                Arguments.of("allenatori di calcio", TypeLista.attivitaPlurale, true, true, true),
//                Arguments.of("2023", TypeLista.annoMorte, true, true, false),
//                Arguments.of("birmani", TypeLista.nazionalitaPlurale, true, true, false),
//                Arguments.of("antiguo-barbudani", TypeLista.nazionalitaPlurale, true, true, false),
//                Arguments.of("1968", TypeLista.annoNascita, true, true, false),
                Arguments.of("Aaron", TypeLista.nomi, true, false, false)
                );
    }

    //--nome lista
    //--typeLista per il test
    protected static Stream<Arguments> LISTA_MAPPA() {
        return Stream.of(
                Arguments.of("38 a.C.", TypeLista.annoNascita),
                Arguments.of("1431", TypeLista.annoMorte),
                Arguments.of("29 febbraio", TypeLista.giornoNascita),
                Arguments.of("dominicani", TypeLista.nazionalitaPlurale)
                //                Arguments.of("brasiliani", TypeLista.nazionalitaPlurale),

        );
    }

    //--nome lista
    //--typeLista per il test
    protected static Stream<Arguments> LISTA() {
        return Stream.of(
                Arguments.of(VUOTA, TypeLista.giornoNascita),
                //                Arguments.of(VUOTA, TypeLista.giornoMorte),
                //                Arguments.of("ciclista", TypeLista.attivitaSingolare),
                //                Arguments.of("1857", null),
                //                Arguments.of("34 marzo", TypeLista.giornoMorte),
                //                Arguments.of("2024", TypeLista.annoNascita),
                //                Arguments.of("2026", TypeLista.annoMorte),
                //                Arguments.of("1857", TypeLista.giornoNascita),
                Arguments.of("29 febbraio", TypeLista.giornoNascita),
                Arguments.of("29 febbraio", TypeLista.giornoMorte),
                //                Arguments.of("8 aprile", TypeLista.attivitaPlurale),
                //                Arguments.of("20 marzo", TypeLista.giornoNascita),
                //                Arguments.of("21 febbraio", TypeLista.giornoMorte),
                //                Arguments.of("34 febbraio", TypeLista.giornoMorte),
                //                Arguments.of("1º gennaio", TypeLista.giornoNascita),
                //                Arguments.of("23 marzo", TypeLista.annoMorte),
                //                Arguments.of("2024", TypeLista.annoMorte),
                //                Arguments.of("38 A.C.", TypeLista.annoNascita),
                //                Arguments.of("4 gennaio", TypeLista.annoNascita),
                //                Arguments.of("1985", TypeLista.nazionalitaSingolare),
                //                Arguments.of("1º gennaio", TypeLista.annoMorte),
                //                Arguments.of("1467", TypeLista.giornoNascita),
                //                Arguments.of("406 a.C.", TypeLista.annoMorte),
                //                Arguments.of("1567", TypeLista.annoNascita),
                //                Arguments.of("560", TypeLista.annoMorte),
                Arguments.of("2023", TypeLista.nessunaLista),
                Arguments.of("3125", TypeLista.annoMorte),
                Arguments.of("agricoltori", TypeLista.attivitaPlurale),
                Arguments.of("direttore di scena", TypeLista.nessunaLista),
                Arguments.of("direttore di scena", TypeLista.attivitaSingolare),
                Arguments.of("agronomi", TypeLista.attivitaPlurale),
                Arguments.of("direttori d'orchestra", TypeLista.attivitaPlurale),

                Arguments.of("dominicani", TypeLista.nazionalitaPlurale),
                Arguments.of("afghani", TypeLista.nessunaLista),
                Arguments.of("afghano", TypeLista.nazionalitaSingolare),
                Arguments.of("afghani", TypeLista.nazionalitaPlurale),
                Arguments.of("afghani", TypeLista.nazionalitaSingolare),

                Arguments.of("2025", TypeLista.annoNascita),
                Arguments.of("43 a.C.", TypeLista.annoNascita),
                Arguments.of("138", TypeLista.annoMorte),
                Arguments.of("43 a.C.", TypeLista.annoMorte),
                Arguments.of("1493", TypeLista.annoNascita),
                Arguments.of("2023", TypeLista.annoMorte)
        );
    }

    //--nome lista
    //--typeLista per il test
    //--key sottoPagina
    protected static Stream<Arguments> LISTA_TEST() {
        return Stream.of(
                //                Arguments.of("29 febbraio", TypeLista.giornoNascita),
                //                Arguments.of("20 marzo", TypeLista.giornoNascita),
                //                Arguments.of("29 febbraio", TypeLista.giornoMorte),
                //                Arguments.of("21 febbraio", TypeLista.giornoMorte),
                //                Arguments.of("38 a.C.", TypeLista.annoNascita),
                //                Arguments.of("1567", TypeLista.annoNascita),
                //                Arguments.of("406 a.C.", TypeLista.annoMorte),
                //                Arguments.of("42 luglio", TypeLista.giornoNascita),
                //                Arguments.of("2024", TypeLista.annoNascita),
                //                Arguments.of("2026", TypeLista.annoMorte),
                Arguments.of("allenatori di calcio", TypeLista.attivitaPlurale, "belgi"),
                Arguments.of("allenatori di calcio", TypeLista.attivitaPlurale, "bosniaci"),
                Arguments.of("allenatori di calcio", TypeLista.attivitaPlurale, "brasiliani")
        );
    }

    //--nome giorno
    //--typeCrono per il test
    protected static Stream<Arguments> GIORNI() {
        return Stream.of(
                Arguments.of(VUOTA, TypeLista.giornoNascita),
                Arguments.of(VUOTA, TypeLista.giornoMorte),
                Arguments.of("1857", TypeLista.giornoNascita),
                Arguments.of("29 febbraio", TypeLista.giornoNascita),
                Arguments.of("29 febbraio", TypeLista.giornoMorte),
                Arguments.of("8 aprile", TypeLista.attivitaPlurale),
                Arguments.of("20 marzo", TypeLista.giornoNascita),
                Arguments.of("21 febbraio", TypeLista.giornoMorte),
                Arguments.of("34 febbraio", TypeLista.giornoMorte),
                Arguments.of("1º gennaio", TypeLista.giornoNascita),
                Arguments.of("23 marzo", TypeLista.annoMorte)
        );
    }

    //--nome anno
    //--typeCrono per il test
    protected static Stream<Arguments> ANNI() {
        return Stream.of(
                Arguments.of(VUOTA, TypeLista.annoNascita),
                Arguments.of(VUOTA, TypeLista.annoMorte),
                Arguments.of("2024", TypeLista.annoMorte),
                Arguments.of("2023", TypeLista.annoMorte),
                Arguments.of("38 a.C.", TypeLista.annoNascita),
                Arguments.of("38 a.C.", TypeLista.annoMorte),
                Arguments.of("38 A.C.", TypeLista.annoNascita),
                Arguments.of("4 gennaio", TypeLista.annoNascita),
                Arguments.of("1985", TypeLista.nazionalitaSingolare),
                Arguments.of("1º gennaio", TypeLista.annoMorte),
                Arguments.of("1467", TypeLista.giornoNascita),
                Arguments.of("406 a.C.", TypeLista.annoMorte),
                Arguments.of("1567", TypeLista.annoNascita),
                Arguments.of("560", TypeLista.annoMorte)
        );
    }


    //--nome della pagina
    //--esiste sul server wiki
    public static Stream<Arguments> VOCE_BIOGRAFICA() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("paginaInesistente", false),
                Arguments.of("Matteo Salvini", true),
                Arguments.of("Matteo Renzi", true),
                Arguments.of("Rocco Commisso", true),
                Arguments.of("Kenny Adeleke", true),
                Arguments.of("Charles Collins", true),
                Arguments.of("Elsie Inglis", true),
                Arguments.of("Charles Dupaty", true),
                Arguments.of("Bernadette Soubirous", true)

        );
    }

    //--valore grezzo
    //--valore valido
    protected static Stream<Arguments> NOMI() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("Marcello<ref>Da levare</ref>", "Marcello"),
                Arguments.of("Marcello <ref>Da levare</ref>", "Marcello"),
                Arguments.of("Marcello {{#tag:ref", "Marcello"),
                Arguments.of("Marcello{{#tag:ref", "Marcello"),
                Arguments.of("Marcello <!--", "Marcello"),
                Arguments.of("Marcello<!--", "Marcello"),
                Arguments.of("Marcello{{graffe iniziali", "Marcello"),
                Arguments.of("Marcello {{graffe iniziali", "Marcello"),
                Arguments.of("Marcello <nowiki>", "Marcello"),
                Arguments.of("Marcello<nowiki>", "Marcello"),
                Arguments.of("Marcello=", VUOTA),
                Arguments.of("Marcello =", VUOTA),
                Arguments.of("Marcello ?", VUOTA),
                Arguments.of("Marcello?", VUOTA),
                Arguments.of("Marcello ecc.", VUOTA),
                Arguments.of("Marcelloecc.", VUOTA),
                Arguments.of("Antonio [html:pippoz]", "Antonio"),
                Arguments.of("Roberto Marco Maria", "Roberto"),
                Arguments.of("Colin Campbell (generale)", "Colin"),
                Arguments.of("Giovan Battista", "Giovan Battista"),
                Arguments.of("Anna Maria", "Anna Maria"),
                Arguments.of("testo errato", "Testo"),
                Arguments.of("antonio", "Antonio"),
                Arguments.of("(antonio)", "Antonio"),
                Arguments.of("[antonio]", "Antonio"),
                Arguments.of("[[Roberto]]", "Roberto")
        );
    }

    //--valore grezzo
    //--valore valido
    protected static Stream<Arguments> COGNOMI() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("Brambilla<ref>Da levare</ref>", "Brambilla"),
                Arguments.of("Brambilla <ref>Da levare</ref>", "Brambilla"),
                Arguments.of("Brambilla {{#tag:ref", "Brambilla"),
                Arguments.of("Brambilla{{#tag:ref", "Brambilla"),
                Arguments.of("Brambilla <!--", "Brambilla"),
                Arguments.of("Brambilla<!--", "Brambilla"),
                Arguments.of("Brambilla{{graffe iniziali", "Brambilla"),
                Arguments.of("Brambilla {{graffe iniziali", "Brambilla"),
                Arguments.of("Brambilla <nowiki>", "Brambilla"),
                Arguments.of("Brambilla<nowiki>", "Brambilla"),
                Arguments.of("Brambilla=", VUOTA),
                Arguments.of("Brambilla =", VUOTA),
                Arguments.of("Brambilla ?", VUOTA),
                Arguments.of("Brambilla?", VUOTA),
                Arguments.of("Brambilla ecc.", VUOTA),
                Arguments.of("Brambillaecc.", VUOTA),
                Arguments.of("Brambilla [html:pippoz]", "Brambilla"),
                Arguments.of("Brambilla Generale", "Brambilla Generale"),
                Arguments.of("Brambilla (generale)", "Brambilla"),
                Arguments.of("Bayley", "Bayley"),
                Arguments.of("Mora Porras", "Mora Porras"),
                Arguments.of("Ørsted", "Ørsted"),
                Arguments.of("bruillard", "Bruillard"),
                Arguments.of("de Bruillard", "de Bruillard"),
                Arguments.of("(Brambilla)", "Brambilla"),
                Arguments.of("[Brambilla]", "Brambilla"),
                Arguments.of("[[Brambilla]]", "Brambilla")
        );
    }


    //--valore grezzo
    //--valore valido
    protected static Stream<Arguments> GIORNI_FIELD() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("3 dicembre<ref>Da levare</ref>", "3 dicembre"),
                Arguments.of("3 dicembre <ref>Da levare</ref>", "3 dicembre"),
                Arguments.of("3 dicembre {{#tag:ref", "3 dicembre"),
                Arguments.of("3 dicembre{{#tag:ref", "3 dicembre"),
                Arguments.of("3 dicembre <!--", "3 dicembre"),
                Arguments.of("3 dicembre<!--", "3 dicembre"),
                Arguments.of("3 dicembre{{graffe iniziali", "3 dicembre"),
                Arguments.of("3 dicembre {{graffe iniziali", "3 dicembre"),
                Arguments.of("3 dicembre <nowiki>", "3 dicembre"),
                Arguments.of("3 dicembre<nowiki>", "3 dicembre"),
                Arguments.of("3 luglio=", VUOTA),
                Arguments.of("27  ottobre=", VUOTA),
                Arguments.of("17 marzo ecc.", VUOTA),
                Arguments.of("17 marzoecc..", VUOTA),
                Arguments.of("31 febbraio", VUOTA),
                Arguments.of("4 termidoro", VUOTA),
                Arguments.of("17 marzo [html:pippoz]", "17 marzo"),
                Arguments.of("17 marzo", "17 marzo"),
                Arguments.of("testo errato", VUOTA),
                Arguments.of("4 termidoro", VUOTA),
                Arguments.of("12 [[Luglio]] <ref>Da levare</ref>", "12 luglio"),
                Arguments.of("24aprile", "24 aprile"),
                Arguments.of("2 Novembre", "2 novembre"),
                Arguments.of("2Novembre", "2 novembre"),
                Arguments.of("2novembre", "2 novembre"),
                Arguments.of("3 dicembre?", VUOTA),
                Arguments.of("3 dicembre ?", VUOTA),
                Arguments.of("?", VUOTA),
                Arguments.of("(?)", VUOTA),
                Arguments.of("3 dicembre circa", VUOTA),
                Arguments.of("[[3 dicembre]]ca", VUOTA),
                Arguments.of("(8 agosto)", "8 agosto"),
                Arguments.of("[8 agosto]", "8 agosto"),
                Arguments.of("[[8 agosto]]", "8 agosto"),
                Arguments.of("21[Maggio]", "21 maggio"),
                Arguments.of("21 [Maggio]", "21 maggio"),
                Arguments.of("21 [[Maggio]]", "21 maggio"),
                Arguments.of("[4 febbraio]", "4 febbraio"),
                Arguments.of("settembre 5", VUOTA),
                Arguments.of("27 ottobre <!--eh eh eh-->", "27 ottobre"),
                Arguments.of("29 giugno <nowiki> levare", "29 giugno"),
                Arguments.of("dicembre", VUOTA),
                Arguments.of("12/5", VUOTA),
                Arguments.of("12-5", VUOTA),
                Arguments.of("9 pianepsione", VUOTA),
                Arguments.of("9 [[pianepsione]]", VUOTA)
        );
    }


    //--valore grezzo
    //--valore valido
    protected static Stream<Arguments> ANNI_FIELD() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("3145", VUOTA),
                Arguments.of("1874", "1874"),
                Arguments.of("(1954)", "1954"),
                Arguments.of("[1954]", "1954"),
                Arguments.of("[[1954]]", "1954"),
                Arguments.of("1649 circa", VUOTA),
                Arguments.of("[[1649]]ca", VUOTA),
                Arguments.of("[[1649]]ca.", VUOTA),
                Arguments.of("[[1649]]circa", VUOTA),
                Arguments.of("1649ca", VUOTA),
                Arguments.of("1649 ca", VUOTA),
                Arguments.of("1649 ca.", VUOTA),
                Arguments.of("1649ca.", VUOTA),
                Arguments.of("1649<ref>Da levare</ref>", "1649"),
                Arguments.of("1649 <ref>Da levare</ref>", "1649"),
                Arguments.of("879{{#tag:ref", "879"),
                Arguments.of("879 {{#tag:ref", "879"),
                Arguments.of("1250<!--", "1250"),
                Arguments.of("1250 <!--", "1250"),
                Arguments.of("1817{{graffe iniziali", "1817"),
                Arguments.of("1817 {{graffe iniziali", "1817"),
                Arguments.of("1940<nowiki>", "1940"),
                Arguments.of("1940 <nowiki>", "1940"),
                Arguments.of("1936=", VUOTA),
                Arguments.of("1936 =", VUOTA),
                Arguments.of("1512 ?", VUOTA),
                Arguments.of("1512?", VUOTA),
                Arguments.of("1512 (?)", VUOTA),
                Arguments.of("1512(?)", VUOTA),
                Arguments.of("1880ecc.", VUOTA),
                Arguments.of("1880 ecc.", VUOTA),
                Arguments.of("novecento", VUOTA),
                Arguments.of("secolo", VUOTA),
                Arguments.of("3 secolo", VUOTA),
                Arguments.of("1532/1537", VUOTA),
                Arguments.of("368 a.C. circa", VUOTA),
                Arguments.of("testo errato", VUOTA),
                Arguments.of("424 [html:pippoz]", "424"),
                Arguments.of("754 a.C.", "754 a.C."),
                Arguments.of("754 a.c.", "754 a.C."),
                Arguments.of("754a.c.", "754 a.C."),
                Arguments.of("754a.C.", "754 a.C."),
                Arguments.of("754 A.C.", "754 a.C."),
                Arguments.of("754A.C.", "754 a.C."),
                Arguments.of("754 AC", "754 a.C."),
                Arguments.of("754AC", "754 a.C."),
                Arguments.of("754 ac", "754 a.C."),
                Arguments.of("754ac", "754 a.C."),
                Arguments.of("[[390 a.C.|390]]/[[389 a.C.|389]] o 389/[[388 a.C.]]", VUOTA)
        );
    }

    //--nome anno
    //--type nato/morto
    //--previsto
    protected static Stream<Arguments> TITOLO_ANNI() {
        return Stream.of(
                Arguments.of(null, TypeLista.annoNascita, VUOTA),
                Arguments.of(VUOTA, TypeLista.annoMorte, VUOTA),
                Arguments.of("214 a.C.", TypeLista.annoNascita, "Nati nel 214 a.C."),
                Arguments.of("214", TypeLista.annoMorte, "Morti nel 214"),
                Arguments.of("735", TypeLista.annoNascita, "Nati nel 735"),
                Arguments.of("735", TypeLista.annoMorte, "Morti nel 735"),
                Arguments.of("18 a.C.", TypeLista.annoNascita, "Nati nel 18 a.C."),
                Arguments.of("18 a.C.", TypeLista.annoMorte, "Morti nel 18 a.C."),
                Arguments.of("123", TypeLista.annoNascita, "Nati nel 123"),
                Arguments.of("123", TypeLista.annoMorte, "Morti nel 123"),
                Arguments.of("1", TypeLista.annoNascita, "Nati nell'1"),
                Arguments.of("1", TypeLista.annoMorte, "Morti nell'1"),
                Arguments.of("11", TypeLista.annoNascita, "Nati nell'11"),
                Arguments.of("11", TypeLista.annoMorte, "Morti nell'11"),
                Arguments.of("11 a.C.", TypeLista.annoNascita, "Nati nell'11 a.C."),
                Arguments.of("11 a.C.", TypeLista.annoMorte, "Morti nell'11 a.C."),
                Arguments.of("24 a.C.", TypeLista.annoNascita, "Nati nel 24 a.C."),
                Arguments.of("24 a.C.", TypeLista.annoMorte, "Morti nel 24 a.C."),
                Arguments.of("865 a.C.", TypeLista.annoNascita, "Nati nell'865 a.C."),
                Arguments.of("865 a.C.", TypeLista.annoMorte, "Morti nell'865 a.C."),
                Arguments.of("1 a.C.", TypeLista.annoNascita, "Nati nell'1 a.C."),
                Arguments.of("1 a.C.", TypeLista.annoMorte, "Morti nell'1 a.C.")
        );
    }


    //--nome giorno
    //--type nato/morto
    //--previsto
    protected static Stream<Arguments> TITOLO_GIORNI() {
        return Stream.of(
                Arguments.of(null, TypeLista.giornoNascita, VUOTA),
                Arguments.of(VUOTA, TypeLista.giornoMorte, VUOTA),
                Arguments.of("7 aprile", TypeLista.giornoNascita, "Nati il 7 aprile"),
                Arguments.of("7 aprile", TypeLista.giornoMorte, "Morti il 7 aprile"),
                Arguments.of("1º ottobre", TypeLista.giornoNascita, "Nati il 1º ottobre"),
                Arguments.of("1º ottobre", TypeLista.giornoMorte, "Morti il 1º ottobre"),
                Arguments.of("7 agosto", TypeLista.giornoNascita, "Nati il 7 agosto"),
                Arguments.of("7 agosto", TypeLista.giornoMorte, "Morti il 7 agosto"),
                Arguments.of("8 dicembre", TypeLista.giornoNascita, "Nati l'8 dicembre"),
                Arguments.of("8 dicembre", TypeLista.giornoMorte, "Morti l'8 dicembre"),
                Arguments.of("11 maggio", TypeLista.giornoNascita, "Nati l'11 maggio"),
                Arguments.of("11 maggio", TypeLista.giornoMorte, "Morti l'11 maggio")
        );
    }

    //--titolo
    //--pagina valida
    //--bio valida
    protected static Stream<Arguments> PAGINE_BIO() {
        return Stream.of(
                Arguments.of(null, false, false),
                Arguments.of(VUOTA, false, false),
                Arguments.of("Roberto il Forte", true, true),
                Arguments.of("Filareto Bracamio", true, true),
                Arguments.of("Claude de Chastellux", true, true),
                Arguments.of("John Murphy (politico statunitense)", true, true),
                Arguments.of("Meena Keshwar Kamal", true, true),
                Arguments.of("Werburga", true, true),
                Arguments.of("Roman Protasevič", true, true),
                Arguments.of("Aldelmo di Malmesbury", true, true),
                Arguments.of("Aelfric il grammatico", true, true),
                Arguments.of("Bernart Arnaut d'Armagnac", true, true),
                Arguments.of("Elfleda di Whitby", true, true),
                Arguments.of("Gaetano Anzalone", true, true),
                Arguments.of("Colin Campbell (generale)", true, true),
                Arguments.of("Louis Winslow Austin", true, true),
                Arguments.of("San Nicanore", true, true),
                Arguments.of("Regno di Napoli (1908-1745)", false, false),
                Arguments.of("Regno di Napoli (1806-1815)", true, false),
                Arguments.of("Rossi", true, false),
                Arguments.of("Bartolomeo Giuseppe Amico di Castell'Alfero", true, true),
                Arguments.of("Lucio Anneo Seneca", true, true),
                Arguments.of("Bodhidharma", true, true),
                Arguments.of("Aloisio Gonzaga", true, true),
                Arguments.of("Alex Bagnoli", true, true),
                Arguments.of("Ashur-uballit I", true, true)
        );
    }


    //--wikiTitle
    //--numero parametri
    protected static Stream<Arguments> BIOGRAFIE() {
        return Stream.of(
                Arguments.of(VUOTA, 0),
                Arguments.of("Jacques de Molay", 17),
                Arguments.of("Roberto il Forte", 16),
                Arguments.of("Agnese di Borgogna", 17),
                Arguments.of("Matteo Renzi", 14),
                Arguments.of("Hunter King", 10),
                Arguments.of("Laura Mancinelli", 17),
                Arguments.of("Johann Georg Kastner", 14),
                Arguments.of("Meirchion Gul", 15),
                Arguments.of("Vincenzo Vacirca", 15),
                Arguments.of("Ashur-uballit I", 15),
                Arguments.of("Albia Dominica", 15),
                Arguments.of("Angelo Inganni", 13),
                Arguments.of("Andrey Guryev", 17),
                Arguments.of("Ingen Ryūki", 17),
                Arguments.of("Giorgio Merula", 16),
                Arguments.of("Rob Paulsen", 16),
                Arguments.of("Aleksandr Isaevič Solženicyn", 22),
                Arguments.of("Aloisio Gonzaga", 19),
                Arguments.of("Alex Bagnoli", 18),
                Arguments.of("Harry Fielder", 15),
                Arguments.of("Yehudai Gaon", 16),
                Arguments.of("Kaku Takagawa", 13),
                Arguments.of("Filippo Tornielli", 12),
                Arguments.of("Mario Tosi (fotografo)", 13),
                Arguments.of("Giuseppe Trombone de Mier", 12),
                Arguments.of("Herlindis di Maaseik", 17),
                Arguments.of("Rinaldo II di Bar", 15),
                Arguments.of("Harald II di Norvegia", 18)
        );
    }

    //--titolo
    //--pagina esistente
    //--biografia esistente
    public Stream<Arguments> PAGINE_E_CATEGORIE() {
        return Stream.of(
                Arguments.of(null, false, false),
                Arguments.of(VUOTA, false, false),
                Arguments.of("Roman Protasevič", true, true),
                Arguments.of("Louis Winslow Austin", true, true),
                Arguments.of("4935359", true, false),
                Arguments.of("Categoria:Nati nel 1435", true, false),
                Arguments.of("2741616|27416167", false, false),
                Arguments.of("Categoria:Nati nel 2387", false, false),
                Arguments.of("Categoria:BioBot", true, false),
                Arguments.of("Categoria:Supercalifragilistichespiralidoso", false, false),
                Arguments.of("Supercalifragilistichespiralidoso", true, false),
                Arguments.of("Regno di Napoli (1908-1745)", false, false),
                Arguments.of("Rossi", true, false)
        );
    }

    //--pageIds
    //--pagina esistente
    protected static Stream<Arguments> PAGINE() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(8978579, true),
                Arguments.of(4935359, true),
                Arguments.of(2116435, true),
                Arguments.of(1, false),
                Arguments.of(480562, true),
                Arguments.of(5605544, true),
                Arguments.of(272727, false),
                Arguments.of(621881, true)
        );
    }

    //--categoria
    //--esiste come anonymous
    //--esiste come user, admin
    //--esiste come bot
    protected static Stream<Arguments> CATEGORIE() {
        return Stream.of(
                Arguments.of(null, false, false, false),
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("Inesistente", false, false, false),
                Arguments.of("Nati nel 1435", true, true, true),
                Arguments.of("2741616|27416167", false, false, false),
                Arguments.of("Ambasciatori britannici in Brasile", true, true, true),
                Arguments.of("Nati nel 1935", false, true, true)
        );
    }


    //--nome singolarePlurale
    //--esiste
    protected static Stream<Arguments> ATTIVITA_TRUE() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("politico", true),
                Arguments.of("politici", true),
                Arguments.of("errata", false),
                Arguments.of("fantasmi", false),
                Arguments.of("attrice", true),
                Arguments.of("attore", true),
                Arguments.of("attori", true),
                Arguments.of("nessuna", false),
                Arguments.of("direttore di scena", false),
                Arguments.of("accademici", false),
                Arguments.of("vescovo ariano", true)
        );
    }

    //--nome singolare
    //--esiste
    public static Stream<Arguments> NAZIONALITA_SINGOLARE() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("turco", true),
                Arguments.of("errata", false),
                Arguments.of("tedesca", true),
                Arguments.of("direttore di scena", false),
                Arguments.of("brasiliano", true),
                Arguments.of("vescovo ariano", false),
                Arguments.of("errata", false),
                Arguments.of("britannici", false),
                Arguments.of("tedesco", true),
                Arguments.of("tedeschi", false)
        );
    }


    //--nome nazionalità plurale (solo minuscola)
    //--esiste
    public static Stream<Arguments> NAZIONALITA_PLURALE() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("turco", false),
                Arguments.of("afghani", true),
                Arguments.of("Afghani", false),
                Arguments.of("andorrani", true),
                Arguments.of("tedesca", false),
                Arguments.of("arabi", true),
                Arguments.of("tedesco", false)
        );
    }

    //--nome singolarePlurale
    //--esiste
    public static Stream<Arguments> NAZIONALITA_TRUE() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("turco", true),
                Arguments.of("errata", false),
                Arguments.of("tedesca", true),
                Arguments.of("direttore di scena", false),
                Arguments.of("brasiliano", true),
                Arguments.of("vescovo ariano", false),
                Arguments.of("errata", false),
                Arguments.of("britannici", true),
                Arguments.of("tedesco", true),
                Arguments.of("tedeschi", true)
        );
    }

    //--nome singolare
    //--esiste
    public static Stream<Arguments> ATTIVITA_SINGOLARE() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("politico", true),
                Arguments.of("politici", false),
                Arguments.of("errata", false),
                Arguments.of("attrice", true),
                Arguments.of("attrici", false),
                Arguments.of("direttore di scena", false),
                Arguments.of("vescovo ariano", true)

        );
    }


    //--cognome
    //--flag diacritico
    protected static Stream<Arguments> DIACRITICI() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
                Arguments.of("Díaz", true),
                Arguments.of("Diaz", false),
                Arguments.of("Fernández", true),
                Arguments.of("Fernandez", false),
                Arguments.of("García", true),
                Arguments.of("Garcia", false),
                Arguments.of("González", true),
                Arguments.of("Gonzalez", false),
                Arguments.of("Gómez", true),
                Arguments.of("Gomez", false),
                Arguments.of("Hernández", true),
                Arguments.of("Hernandez", false),
                Arguments.of("Itō", true),
                Arguments.of("Ito", false),
                Arguments.of("López", true),
                Arguments.of("Lopez", false),
                Arguments.of("Martínez", true),
                Arguments.of("Martinez", false),
                Arguments.of("Müller", true),
                Arguments.of("Muller", false),
                Arguments.of("Rodríguez", true),
                Arguments.of("Rodriguez", false),
                Arguments.of("Sánchez", true),
                Arguments.of("Sanchez", false)
        );
    }

}