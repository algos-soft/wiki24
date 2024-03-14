package it.algos.wiki24.backend.wrapper;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 16-Feb-2024
 * Time: 21:51
 */
public class MappaWrap {

    private String key;

    private List<WrapDidascalia> lista;

    public MappaWrap(String key, List<WrapDidascalia> lista) {
        this.key = key;
        this.lista = lista;
    }

    public String getKey() {
        return key;
    }

    public List<WrapDidascalia> getLista() {
        return lista;
    }

}
