package it.algos.vaad24.backend.enumeration;

import it.algos.vaad24.backend.interfaces.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sat, 14-Jan-2023
 * Time: 15:46
 */
public enum AETypeResult implements AIType {

    indeterminato("Indeterminato"),
    noAECopy("Manca il type AECopy"),
    typeNonCompatibile("Il type previsto non è compatibile col metodo"),
    noFileName("Manca il nome del file"),
    noSourceFile("Manca il file sorgente"),
    noSrcDir("Manca la directory sorgente"),
    noDestDir("Manca la directory destinazione"),


    fileEsistente("Il file esisteva già"), //dopo fileCreaOnlyNotExist
    fileCreato("Il file è stato creato"), //dopo fileCreaOnlyNotExist, fileModifyEver, fileModifyToken
    fileEsistenteUguale("Il file esisteva già e non è stato modificato"), //dopo fileModifyEver
    fileEsistenteModificato("Il file esisteva già ma è stato modificato"), //dopo fileModifyEver

    fileTokenCreatoUguale("Il file è stato creato senza modifiche al token"), //dopo fileModifyToken
    fileTokenCreatoDiverso("Il file è stato creato e il token adeguato"), //dopo fileModifyToken
    fileTokenModificatoNoToken("Il file è stato modificato ma il token no"), //dopo fileModifyToken
    fileTokenModificatoSiToken("Il file è stato modificato e anche il token"), //dopo fileModifyToken
    fileTokenUgualeNoToken("Il file non è stato modificato e neanche il token"), //dopo fileModifyToken
    fileTokenUgualeSiToken("Il file non è stato modificato ma il token si"), //dopo fileModifyToken

    fileSovrascritto("Il file è stato sovrascritto"),


    dirEsistente("La directory esisteva già e non è stata toccata"),
    dirCreata("La directory è stata creata"),


    error("Errore"),
    mancaResult("Manca il Result"),
    mancaTypeLog("Manca il typeLog");

    private String tag;

    AETypeResult(String tag) {
        this.tag = tag;
    }

    public static List<AETypeResult> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    public static List<String> getAllTags() {
        List<String> listaTags = new ArrayList<>();

        getAllEnums().forEach(type -> listaTags.add(type.getTag()));
        return listaTags;
    }

    @Override
    public List<AETypeResult> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public String getTag() {
        return tag;
    }

}