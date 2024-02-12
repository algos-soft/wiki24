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

    public boolean giornoNato(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(Upload.class, giornoBean.nome).type(TypeLista.giornoNascita).uploadAll().isValido();
    }

    public boolean giornoNato(final String nomeGiorno) {
        return appContext.getBean(Upload.class, nomeGiorno).type(TypeLista.giornoNascita).uploadAll().isValido();
    }

    public boolean giornoMorto(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(Upload.class, giornoBean.nome).type(TypeLista.giornoMorte).uploadAll().isValido();
    }

    public boolean giornoMorto(final String nomeGiorno) {
        return appContext.getBean(Upload.class, nomeGiorno).type(TypeLista.giornoMorte).uploadAll().isValido();
    }

    public boolean annoNato(final AnnoWikiEntity annoBean) {
        return appContext.getBean(Upload.class, annoBean.nome).type(TypeLista.annoNascita).uploadAll().isValido();
    }

    public boolean annoNato(final String nomeAnno) {
        return appContext.getBean(Upload.class, nomeAnno).type(TypeLista.annoNascita).uploadAll().isValido();
    }

    public boolean annoMorto(final AnnoWikiEntity annoBean) {
        return appContext.getBean(Upload.class, annoBean.nome).type(TypeLista.annoMorte).uploadAll().isValido();
    }

    public boolean annoMorto(final String nomeAnno) {
        return appContext.getBean(Upload.class, nomeAnno).type(TypeLista.annoMorte).uploadAll().isValido();
    }


    public boolean giornoNatoTest(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(Upload.class, giornoBean.nome).test().type(TypeLista.giornoNascita).uploadAll().isValido();
    }

    public boolean giornoNatoTest(final String nomeGiorno) {
        return appContext.getBean(Upload.class, nomeGiorno).test().type(TypeLista.giornoNascita).uploadAll().isValido();
    }

    public boolean giornoMortoTest(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(Upload.class, giornoBean.nome).test().type(TypeLista.giornoMorte).uploadAll().isValido();
    }

    public boolean giornoMortoTest(final String nomeGiorno) {
        return appContext.getBean(Upload.class, nomeGiorno).test().type(TypeLista.giornoMorte).uploadAll().isValido();
    }

    public boolean annoNatoTest(final AnnoWikiEntity annoBean) {
        return appContext.getBean(Upload.class, annoBean.nome).test().type(TypeLista.annoNascita).uploadAll().isValido();
    }

    public boolean annoNatoTest(final String nomeAnno) {
        return appContext.getBean(Upload.class, nomeAnno).test().type(TypeLista.annoNascita).uploadAll().isValido();
    }

    public boolean annoMortoTest(final AnnoWikiEntity annoBean) {
        return appContext.getBean(Upload.class, annoBean.nome).test().type(TypeLista.annoMorte).uploadAll().isValido();
    }

    public boolean annoMortoTest(final String nomeAnno) {
        return appContext.getBean(Upload.class, nomeAnno).test().type(TypeLista.annoMorte).uploadAll().isValido();
    }

    public int numMortiAnno(final String nomeAnno) {
        return appContext.getBean(Upload.class, nomeAnno).type(TypeLista.annoMorte).numBio();
    }
    public boolean attivitaTest(final AttPluraleEntity attivitaBean) {
        return appContext.getBean(Upload.class, attivitaBean.plurale).test().type(TypeLista.attivitaPlurale).uploadAll().isValido();
    }
    public boolean attivita(final AttPluraleEntity attivitaBean) {
        return appContext.getBean(Upload.class, attivitaBean.plurale).type(TypeLista.attivitaPlurale).uploadAll().isValido();
    }

    public boolean nazionalitaTest(final NazPluraleEntity nazionalitaBean) {
        return appContext.getBean(Upload.class, nazionalitaBean.plurale).test().type(TypeLista.nazionalitaPlurale).uploadAll().isValido();
    }
    public boolean nazionalita(final NazPluraleEntity nazionalitaBean) {
        return appContext.getBean(Upload.class, nazionalitaBean.plurale).type(TypeLista.nazionalitaPlurale).uploadAll().isValido();
    }

}
