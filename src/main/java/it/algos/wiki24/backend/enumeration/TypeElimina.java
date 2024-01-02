package it.algos.wiki24.backend.enumeration;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.base24.backend.boot.BaseCost.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 01-Jan-2024
 * Time: 13:28
 */
public enum TypeElimina {
    tondaIni("(", TypeParse.startsWith),
    senzaFonte("Senza fonte", TypeParse.contains),
    //    calendario("calendario", TypeParse.contains),
    probabile("probabile", TypeParse.contains),
    presunto("presunto", TypeParse.contains),
    incerta("data incerta", TypeParse.contains),
    altreFonti("altre fonti", TypeParse.contains),
    dubbioO(" o ", TypeParse.contains),
    parentesi("(o ", TypeParse.contains),
    oppure(" oppure ", TypeParse.contains),
    floruit("Floruit", TypeParse.contains),
    floruit2("floruit", TypeParse.contains),
    ecc("ecc.", TypeParse.endsWith),
    interrogativo("?", TypeParse.endsWith),
    uguale("=", TypeParse.endsWith),
    intTonda("?)", TypeParse.endsWith),

    ;

    private String tag;

    private TypeParse typeParse;

    TypeElimina(String tag, TypeParse typeParse) {
        this.tag = tag;
        this.typeParse = typeParse;
    }

    public String get(String valoreGrezzo) {
        return switch (typeParse) {
            case startsWith -> valoreGrezzo.startsWith(tag) ? VUOTA : valoreGrezzo;
            case contains -> valoreGrezzo.contains(tag) ? VUOTA : valoreGrezzo;
            case endsWith -> valoreGrezzo.endsWith(tag) ? VUOTA : valoreGrezzo;
        };
    }

    public String getTag() {
        return tag;
    }
}
