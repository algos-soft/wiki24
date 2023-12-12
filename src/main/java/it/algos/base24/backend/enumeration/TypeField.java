package it.algos.base24.backend.enumeration;

import it.algos.base24.backend.boot.*;
import it.algos.base24.backend.interfaces.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Tue, 08-Aug-2023
 * Time: 16:02
 * Enumeration type: senza schema fisso
 */
public enum TypeField implements Type {

    text(10, 14),
    phone(9, 14),
    password(10, 14),
    email(20, 24),
    cap(6, 6),
    textArea(8, 18),
    integer(6, 8),
    ordine(4, 8),
    lungo(5, 8),
    doppio(5, 8),
    booleano(5, 4),
    localDateTime(15, 15),
    localDate(8, 8),
    localTime(6, 6),
    linkStatico(8, 8),
    linkDBRef(8, 10),
    linkWiki(12, 16),
    linkWikiCheck(12, 16),
    image(3, 9),
    vaadinIcon(8, 8),
    anchor(10, 10),
    enumType(8, 8),

    preferenza(12, 12),
    ;

    private int widthColumnInt;

    private int widthFieldInt;

    private String widthColumn;

    private String widthField;

    TypeField(final int intWidthColumn, final int intWidthField) {
        this.widthColumnInt = intWidthColumn;
        this.widthFieldInt = intWidthField;
        this.widthColumn = intWidthColumn + BaseCost.HTML_WIDTH_UNIT;
        this.widthField = intWidthField + BaseCost.HTML_WIDTH_UNIT;
    }

    public static List<TypeField> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<TypeField> getAll() {
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
        return name();
    }


    public int getWidthColumnInt() {
        return widthColumnInt;
    }

    public int getWidthFieldInt() {
        return widthFieldInt;
    }

    public String getWidthColumn() {
        return widthColumn;
    }

    public String getWidthField() {
        return widthField;
    }

}
