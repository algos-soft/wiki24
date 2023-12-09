package it.algos.base24.backend.enumeration;


import it.algos.base24.backend.interfaces.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: lun, 15-feb-2021
 * Time: 20:46
 * Enumeration type: con interfaccia [type]
 */
public enum FontWeight implements Type {

    normal("normal"),
    bold("bold"),
    bolder("bolder"),
    lighter("lighter"),
    w100("100"),
    w200("200"),
    w300("300"),
    w400("400"),
    w500("500"),
    w600("600"),
    w700("700"),
    w800("800"),
    w900("900");

    public static final String HTML = "font-weight";

    private String tag;


    FontWeight(String tag) {
        this.tag = tag;
    }

    public static List<FontWeight> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<FontWeight> getAll() {
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