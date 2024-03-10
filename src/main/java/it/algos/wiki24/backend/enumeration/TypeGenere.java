package it.algos.wiki24.backend.enumeration;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Sun, 10-Jul-2022
 * Time: 08:34
 */
public enum TypeGenere {
    maschile, femminile, entrambi, nessuno;


    public static List<TypeGenere> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    public static TypeGenere getType(String nome) {
        return getAllEnums()
                .stream()
                .filter(type -> type.name().equals(nome))
                .findAny()
                .orElse(null);
    }

}
