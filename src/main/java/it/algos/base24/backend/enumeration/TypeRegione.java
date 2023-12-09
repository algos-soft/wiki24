package it.algos.base24.backend.enumeration;

import it.algos.base24.backend.interfaces.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Tue, 07-Nov-2023
 * Time: 17:32
 */
public enum TypeRegione implements Type {
    cantone("Cantone"),
    capitale("Capitale"),
    contea("Contea"),
    cittaComitale("Città di rilevanza comitale"),
    dipartimentoOltremare("Dipartimento d'oltremare"),
    collettivita("Collettività"),
    collettivitaOltremare("Collettività d'oltremare"),
    collettivitaEuropea("Collettività europea"),
    collettivitaSpeciale("Collettività a statuto speciale"),
    comune("Comune"),
    comunitaAutonoma("Comunità autonoma"),
    dipartimento("Dipartimento"),
    dipartimentoMetropolitano("Dipartimento metropolitano"),
    distretto("Distretto"),
    land("Land"),
    oblast("Oblast'"),
    periferia("Periferia greca"),
    prefettura("Prefettura"),
    provincia("Provincia"),
    cittaAutonoma("Città autonoma"),
    cittaMetropolitana("Città metropolitana"),
    quartiere("Quartiere"),
    nazione("Nazione costitutiva"),
    municipalita("Municipalità speciale"),
    regione("Regione"),
    regioneMetropolitana("Regione metropolitana"),
    entita("Entità"),
    regioneAutonoma("Regione autonoma"),
    regioneOltremare("Regione d'oltremare"),
    regioneOrdinaria("Regione ordinaria"),
    regioneSpeciale("Regione a statuto speciale"),
    voivodato("Voivodato"),
    governatorato("Governatorato"),
    parrocchia("Parrocchia"),
    ;

    private String tag;


    TypeRegione(String tag) {
        this.tag = tag;
    }


    public static List<TypeRegione> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<TypeRegione> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<String> getAllTags() {
        return getAllEnums()
                .stream()
                .map(type->type.getTag())
                .collect(Collectors.toList());
    }


    @Override
    public String getTag() {
        return tag;
    }
    @Override
    public String toString() {
        return tag;
    }
}
