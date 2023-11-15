package it.algos.base24.backend.enumeration;

import com.vaadin.flow.component.notification.*;
import it.algos.base24.backend.interfaces.*;

import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Wed, 04-Oct-2023
 * Time: 16:34
 * Enumeration type: con interfaccia [type]
 */
public enum TypeNotifica implements Type {
    primary("primario", NotificationVariant.LUMO_PRIMARY),
    success("successo", NotificationVariant.LUMO_SUCCESS),
    contrast("contrasto", NotificationVariant.LUMO_CONTRAST),

    error("errore", NotificationVariant.LUMO_ERROR),
    nessuna("nessuna", null);

    private String tag;

    private NotificationVariant variant;


    TypeNotifica(String tag, NotificationVariant variant) {
        this.variant = variant;
    }

    public static List<TypeNotifica> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<TypeNotifica> getAll() {
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

    public NotificationVariant get() {
        return variant;
    }
}
