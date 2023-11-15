package it.algos.base24.backend.enumeration;

import it.algos.base24.backend.interfaces.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 08-apr-2022
 * Time: 20:13
 * Enumeration type: con interfaccia [type]
 */
public enum FontStyle implements Type {

    normal("normal"),
    italic("italic"),
    ;

    public static final String HTML = "font-style";

    private String tag;

    FontStyle(String tag) {
        this.tag = tag;
    }

    public static List<FontStyle> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<FontStyle> getAll() {
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