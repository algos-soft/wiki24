package it.algos.base24.basetest;

import it.algos.base24.backend.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: ven, 27-ott-2023
 * Time: 07:16
 */
public abstract class AlgosTest {

    @Autowired
    public ApplicationContext appContext;

    @Autowired
    protected AnnotationService annotationService;

    @Autowired
    protected DateService dateService;

    public static final String GIUSTO = "(giusto)";

    public static final String SBAGLIATO = "(sbagliato)";

    protected Class clazz;

    protected Object object;

    protected String clazzName;

    protected String message;

    protected String sorgente;

    protected String sorgente2;

    protected String sorgente3;

    protected String previsto;

    protected String previsto2;

    protected String ottenuto;
    protected long  sorgenteLong;
    protected boolean previstoBooleano;

    protected boolean ottenutoBooleano;

    protected int sorgenteIntero;

    protected int previstoIntero;

    protected int ottenutoIntero;

    protected TypeField typeField;

    protected long inizio;

    protected List<String> sorgenteArray;

    protected List<String> previstoArray;

    protected List<String> ottenutoArray;

    protected List<String> listaStr;

    protected List<List<String>> listaTable;

    protected Map<String, List<String>> mappa;

    protected List<AbstractEntity> listaEntity;

    protected List<Field> fieldsArray;

    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    protected void setUpEach() {
        sorgente = VUOTA;
        sorgente2 = VUOTA;
        sorgente3 = VUOTA;
        previsto = VUOTA;
        previsto2 = VUOTA;
        ottenuto = VUOTA;
        sorgenteIntero = 0;
        previstoIntero = 0;
        ottenutoIntero = 0;
        clazz = null;
        sorgenteArray = null;
        previstoArray = null;
        ottenutoArray = null;
        listaStr = null;
        mappa = null;
        inizio = System.currentTimeMillis();
        message = VUOTA;
        fieldsArray = null;
        listaTable = null;
    }

    protected void printClazz(List<Class> listaClazz) {
        System.out.println(VUOTA);
        int k = 0;

        for (Class clazz : listaClazz) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(clazz.getSimpleName());
        }
    }

    protected void printField(List<Field> listaField) {
        AField annotation;
        System.out.println(VUOTA);
        int k = 0;

        for (Field field : listaField) {
            annotation = field.getAnnotation(AField.class);
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.print(field.getName());
            System.out.print(SPAZIO);
            System.out.print(QUADRA_INI);
            System.out.print(field.getType().getSimpleName());
            System.out.print(QUADRA_END);
            System.out.println(VUOTA);

            if (annotation != null) {
                message = String.format("%s%s%s%s", TAB_SPAZIO, "type", FORWARD, annotation.type());
                System.out.println(message);
                message = String.format("%s%s%s%d", TAB_SPAZIO, "widthRem", FORWARD, annotation.widthRem());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "headerText", FORWARD, annotation.headerText());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "headerIcon", FORWARD, annotation.headerIcon());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "caption", FORWARD, annotation.caption());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "linkClazz", FORWARD, annotation.linkClazz());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "enumClazz", FORWARD, annotation.enumClazz());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "typeBool", FORWARD, annotation.typeBool());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "typeDate", FORWARD, annotation.typeDate());
                System.out.println(message);
            }
        }
    }

}
