package it.algos.base24.backend.annotation;

import com.vaadin.flow.component.icon.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;

import java.lang.annotation.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Tue, 08-Aug-2023
 * Time: 15:59
 * Annotation per i fields (property) delle Entity Class <br>
 * Annotation to add some property for a single field <br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) //--Field declaration (includes enum constants)
public @interface AField {


    /**
     * (Required) The type of the field.
     * Defaults to the text type.
     *
     * @return the field type
     */
    TypeField type() default TypeField.text;

    /**
     * (Optional) The width of the field.
     * Expressed in double, to be converted in String ending with "rem"
     * Defaults to 0.0
     *
     * @return the double
     */
    int widthRem() default 0;

    /**
     * (Optional) The visible text header of the column.
     * Defaults to the property or field name.
     *
     * @return the string
     */
    String headerText() default VUOTA;

    /**
     * (Optional) The visible icon header of the column
     * Defaults to YOUTUBE (che non uso di sicuro).
     *
     * @return the vaadin icon
     */
    VaadinIcon headerIcon() default VaadinIcon.YOUTUBE;

    /**
     * (Optional) The visible name of the field.
     * Defaults to the property or field name.
     *
     * @return the string
     */
    String caption() default VUOTA;


    /**
     * (Optional) Classe della property.
     * Utilizzato nei Link.
     *
     * @return the class
     */
    Class<?> linkClazz() default Object.class;

    /**
     * (Optional) Classe della property.
     * Utilizzato nei Link.
     *
     * @return the class
     */
    Class<?> enumClazz() default Object.class;

    /**
     * (Required) The type of the field.
     * Defaults to the TypeBoolCol.checkIcon.
     *
     * @return the field type
     */
    TypeBool typeBool() default TypeBool.checkIcon;


    /**
     * (Required) The type of the date.
     * Defaults to the TypeDate.iso8601.
     *
     * @return the ae field type
     */
    TypeDate typeDate() default TypeDate.iso8601;

    /**
     * (Optional) Prefisso web del Anchor.
     * Utilizzato nei Link.
     *
     * @return the prefix
     */
    String anchorPrefix() default VUOTA;

}
