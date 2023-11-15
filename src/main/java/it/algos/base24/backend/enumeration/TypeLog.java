package it.algos.base24.backend.enumeration;

import it.algos.base24.backend.interfaces.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sat, 21-Oct-2023
 * Time: 14:23
 * Enumeration type: con interfaccia [type]
 */
public enum TypeLog implements Type {
    system("system"),
    setup("setup"),
    login("login"),
    startup("startup"),
    startupreset("startupReset"),
    sviluppo("sviluppo"),
    checkMenu("checkMenu"),
    checkData("checkData"),
    preferenze("preferenze"),
    nuovo("newEntity"),
    edit("edit"),
    modifica("modifica"),
    delete("delete"),
    deleteAll("deleteAll"),
    mongo("mongoDB"),
    file("file"),
    resources("resources"),
    debug("debug"),
    info("info"),
    warn("warn"),
    error("error"),
    wizard("wizard"),
    wizardDoc("wizardDoc"),
    importo("import"),
    export("export"),
    download("download"),
    upload("upload"),
    update("update"),
    elabora("elabora"),
    statistiche("statistiche"),
    task("task"),
    reset("reset"),
    resetOnlyEmpty("resetOnlyEmpty"),
    resetForcing("resetForcing"),
    utente("utente"),
    password("password"),
    bio("cicloBio"),
    test("test"),
    spazio("spazio"),
    schedule("schedule"),
    flow("flow"),
    form("form"),
    utility("utility"),
    ideJar("ideJar"),
    ;

    private String tag;


    TypeLog(String tag) {
        this.tag = tag;
    }


    public static List<TypeLog> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<TypeLog> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<String> getAllTags() {
        List<String> listaTags = new ArrayList<>();

        getAllEnums().forEach(type -> listaTags.add(type.getTag()));
        return listaTags;
    }

    @Override
    public String getTag() {
        return tag;
    }

}
