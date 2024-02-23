package it.algos.wiki24.backend.service;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.entity.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.tabelle.anni.*;
import it.algos.wiki24.backend.packages.tabelle.attplurale.*;
import it.algos.wiki24.backend.packages.tabelle.giorni.*;
import it.algos.wiki24.backend.packages.tabelle.nazplurale.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.*;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 22-Jan-2024
 * Time: 21:22
 */
@Service
public class UploadService {

    @Inject
    public ApplicationContext appContext;

    public WResult giornoNatoTest(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(Upload.class, giornoBean.nome, TypeLista.giornoNascita).test().uploadOnly();
    }

    public WResult giornoNato(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(Upload.class, giornoBean.nome, TypeLista.giornoNascita).uploadOnly();
    }

    public WResult giornoMortoTest(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(Upload.class, giornoBean.nome, TypeLista.giornoMorte).test().uploadOnly();
    }

    public WResult giornoMorto(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(Upload.class, giornoBean.nome, TypeLista.giornoMorte).uploadOnly();
    }

    public WResult annoNatoTest(final AnnoWikiEntity annoBean) {
        return appContext.getBean(Upload.class, annoBean.nome, TypeLista.annoNascita).test().uploadOnly();
    }

    public WResult annoNato(final AnnoWikiEntity annoBean) {
        return appContext.getBean(Upload.class, annoBean.nome, TypeLista.annoNascita).uploadOnly();
    }

    public WResult annoMortoTest(final AnnoWikiEntity annoBean) {
        return appContext.getBean(Upload.class, annoBean.nome, TypeLista.annoMorte).test().uploadOnly();
    }

    public WResult annoMorto(final AnnoWikiEntity annoBean) {
        return appContext.getBean(Upload.class, annoBean.nome, TypeLista.annoMorte).uploadOnly();
    }


    public int numNatiGiorno(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(Upload.class, giornoBean.nome, TypeLista.giornoNascita).numBio();
    }
    public int numMortiGiorno(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(Upload.class, giornoBean.nome, TypeLista.giornoMorte).numBio();
    }


    public int numNatiAnno(final AnnoWikiEntity annoBean) {
        return appContext.getBean(Upload.class, annoBean.nome, TypeLista.annoNascita).numBio();
    }
    public int numMortiAnno(final AnnoWikiEntity annoBean) {
        return appContext.getBean(Upload.class, annoBean.nome, TypeLista.annoMorte).numBio();
    }

    public WResult attivitaTest(final AttPluraleEntity attivitaBean) {
        return appContext.getBean(Upload.class, attivitaBean.plurale, TypeLista.attivitaPlurale).test().uploadOnly();
    }

    public WResult attivita(final AttPluraleEntity attivitaBean) {
        return appContext.getBean(Upload.class, attivitaBean.plurale, TypeLista.attivitaPlurale).uploadOnly();
    }

    public WResult nazionalitaTest(final NazPluraleEntity nazionalitaBean) {
        return appContext.getBean(Upload.class, nazionalitaBean.plurale, TypeLista.nazionalitaPlurale).test().uploadOnly();
    }

    public WResult nazionalita(final NazPluraleEntity nazionalitaBean) {
        return appContext.getBean(Upload.class, nazionalitaBean.plurale, TypeLista.nazionalitaPlurale).uploadOnly();
    }

}
