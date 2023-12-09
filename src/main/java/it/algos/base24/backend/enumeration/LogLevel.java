package it.algos.base24.backend.enumeration;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.interfaces.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 11:45
 * Enumeration type: con interfaccia [type]
 */
public enum LogLevel implements Type {
    debug(VERDE),
    info(BLUE),
    warn(GIALLO),
    error(ROSSO),
    mail(VIOLA),
    ;

    public String tag;


    LogLevel(String tag) {
        this.tag = tag;
    }

    public static List<LogLevel> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<LogLevel> getAll() {
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