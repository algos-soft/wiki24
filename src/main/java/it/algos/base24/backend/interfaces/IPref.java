package it.algos.base24.backend.interfaces;

import java.time.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Wed, 29-Nov-2023
 * Time: 21:02
 */
public interface IPref {
     void setValue(Object javaValue) ;

    String getStr();

    boolean is();

    int getInt();

    LocalDateTime getDateTime();

}// end of interface

