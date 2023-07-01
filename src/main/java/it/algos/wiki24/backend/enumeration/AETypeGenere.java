package it.algos.wiki24.backend.enumeration;

import it.algos.vaad24.backend.interfaces.*;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Sun, 10-Jul-2022
 * Time: 08:34
 */
public enum AETypeGenere  {
    maschile, femminile, entrambi, nessuno;


    public static List<AETypeGenere> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    }
