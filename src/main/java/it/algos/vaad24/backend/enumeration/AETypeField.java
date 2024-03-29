package it.algos.vaad24.backend.enumeration;

import java.util.*;

public enum AETypeField {

    text(10, 14),
    phone(9, 14),
    password(10, 14),
    email(20, 24),
    cap(6, 6),
    textArea(8, 18),
    integer(5, 8),
    lungo(5, 8),
    doppio(5, 8),
    booleano(5, 4),
    localDateTime(15, 15),
    localDate(8, 8),
    localTime(6, 6),
    timestamp(6, 6),
    mappa(0, 14),

    //    meseShort(6, 0, AETypeData.meseShort),
    //
    //    meseNormal(6, 0, AETypeData.meseNormal),
    //
    //    meseLong(6, 0, AETypeData.meseLong),
    //
    //    weekShort(6, 0, AETypeData.weekShort),
    //
    //    weekShortMese(6, 0, AETypeData.weekShortMese),
    //
    //    weekLong(6, 0, AETypeData.weekShort),

    preferenza(6, 6),

    //    noBinder,
    //    calculatedTxt,
    //    calculatedInt,
    //    integernotzero,
    //    onedecimal,

    combo(8, 10),
    //    stringLinkClassCombo(10, 14),

    //    multicombo,
    listaH(10, 10),
    listaV(10, 10),
    linkDinamico(8, 10),
    linkStatico(8, 8),

    enumString(8, 8),

    enumType(8, 8),
    gridShowOnly(0, 20),

    //    dateNotEnabled,
    //    decimal,
    image(3, 9),
    //    resource,

    vaadinIcon(8, 8),

    //    json,

    ugualeAlForm(0, 0),
    anchor(10, 10),

    //    noone,
    //    color,
    //    custom
    ;

    private double widthColumn;

    private double widthField;

    private boolean customField = false;

    AETypeField(double widthColumn, double widthField) {
        this(false, widthColumn, widthField);
    }

    AETypeField(boolean customField, double widthColumn, double widthField) {
        this.customField = customField;
        this.widthColumn = widthColumn;
        this.widthField = widthField;
    }

    public static List<AETypeField> getAllEnums() {
        return Arrays.stream(values()).toList();
    }


    public double getWidthColumn() {
        return widthColumn;
    }


    public double getWidthField() {
        return widthField;
    }

    public boolean isCustomField() {
        return customField;
    }
}