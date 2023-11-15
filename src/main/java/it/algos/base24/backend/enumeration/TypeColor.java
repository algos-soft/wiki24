package it.algos.base24.backend.enumeration;

import it.algos.base24.backend.interfaces.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 15-feb-2021
 * Time: 20:42
 * Enumeration type: con interfaccia [type]
 */
public enum TypeColor implements Type {
    normale("black"),
    nero("black"),
    blue("blue"),
    verde("green"),
    rosso("red"),
    ;

    public static final String HTML = "color";

    private String tag;


    TypeColor(String tag) {
        this.tag = tag;
    }

    public static List<TypeColor> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<TypeColor> getAll() {
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