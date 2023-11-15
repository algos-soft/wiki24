package it.algos.base24.backend.enumeration;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.interfaces.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Sun, 08-Oct-2023
 * Time: 13:40
 * Enumeration type: con interfaccia [type]
 */
public enum MenuGroup implements Type {
    nessuno(VUOTA, 999),
    anagrafe("anagrafica", 1),
    crono("crono", 3),
    geografia("geografia", 2),
    utility("utility", 4),
    ;

    public String tag;

    private int ordine;


    MenuGroup(String tag, int ordine) {
        this.tag = tag;
        this.ordine = ordine;
    }

    public static List<MenuGroup> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<MenuGroup> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<String> getAllTags() {
        List<String> listaTags = new ArrayList<>();

        getAllEnums().forEach(group -> listaTags.add(group.getTag()));
        return listaTags;
    }

    public static List<MenuGroup> getAllOrderedEnums() {
        return getAllEnums()
                .stream()
                .sorted(Comparator.comparingInt(MenuGroup::getOrdine))
                .collect(Collectors.toList());
    }

    public static List<String> getAllOrderedTags() {
        return getAllOrderedEnums().stream().map(group -> group.getTag()).collect(Collectors.toList());
    }



    public int getOrdine() {
        return ordine;
    }

    @Override
    public String getTag() {
        return tag != null && tag.length() > 0 ? tag.substring(0, 1).toUpperCase() + tag.substring(1) : VUOTA;
    }

}
