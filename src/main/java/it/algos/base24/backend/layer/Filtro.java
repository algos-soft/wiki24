package it.algos.base24.backend.layer;

import it.algos.base24.backend.enumeration.*;
import org.springframework.data.domain.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 27-Aug-2023
 * Time: 14:04
 */
public class Filtro {


    private String propertyField;

    private Object propertyValue;

    private TypeFiltro type;

    private Sort sort;


    public Filtro(String propertyField, Object propertyValue) {
        this(propertyField, propertyValue, TypeFiltro.uguale, null);
    }

    public Filtro(String propertyField, Object propertyValue, TypeFiltro type) {
        this(propertyField, propertyValue, type, null);
    }

    public Filtro(String propertyField, Object propertyValue, TypeFiltro type, Sort sort) {
        this.propertyField = propertyField;
        this.propertyValue = propertyValue;
        this.type = type;
        this.sort = sort;
    }

    public String getPropertyField() {
        return propertyField;
    }

    public Object getPropertyValue() {
        return propertyValue;
    }

    public TypeFiltro getType() {
        return type;
    }

    public Sort getSort() {
        return sort;
    }

}
