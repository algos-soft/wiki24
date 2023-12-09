package it.algos.base24.backend.enumeration;

import it.algos.base24.backend.interfaces.*;

import java.util.*;
import java.util.stream.*;

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
        return getAllEnums()
                .stream()
                .map(type->type.getTag())
                .collect(Collectors.toList());
    }


    @Override
    public String getTag() {
        return tag;
    }

}