package it.algos.wiki24.backend.enumeration;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 08-Jan-2024
 * Time: 15:11
 */
public enum TypeInesistente {


    giorno("Senza giorno specificato"),
    anno("Senza anno specificato"),
    secolo("Secolo inesistente"),
    mese("Mese inesistente"),
    attivita("Senza attività specificata"),
    nazionalita("Senza nazionalità specificata"),
    nomi("Senza attività specificata");

    private String tag;


    TypeInesistente(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
