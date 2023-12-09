package it.algos.base24.backend.enumeration;


import it.algos.base24.backend.interfaces.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 26-mar-2022
 * Time: 10:44
 * Enumeration type: con interfaccia [type]
 */
public enum LineHeight implements Type {

    normal("1"),
    px0("0px"),
    px2("2px"),
    px4("4px"),
    px6("6px"),
    px8("8px"),
    px10("10px"),
    px12("12px"),
    cm05("0.5cm"),
    cm08("0.5cm"),
    em2("0.2em"),
    em3("0.3em"),
    em4("0.4em"),
    em6("0.6em"),
    em8("0.8em"),
    em12("1.2em"),
    number03("0.3"),
    number05("0.5"),
    number16("1.6"),
    cento120("120%"),
    cento80("80%"),
    ;

    public static final String HTML = "line-height";

    private String tag;


    LineHeight(String tag) {
        this.tag = tag;
    }

    public static List<LineHeight> getAllEnums() {
        return Arrays.stream(values()).toList();
    }


    @Override
    public List<LineHeight> getAll() {
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