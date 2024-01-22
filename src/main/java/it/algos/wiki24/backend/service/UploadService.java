package it.algos.wiki24.backend.service;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.entity.*;
import it.algos.wiki24.backend.packages.tabelle.anni.*;
import it.algos.wiki24.backend.packages.tabelle.giorni.*;
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
        return appContext.getBean(UploadGiornoNato.class, giornoBean.nome).upload().isValido();
    }

    public boolean giornoNato(final String nomeGiorno) {
        return appContext.getBean(UploadGiornoNato.class, nomeGiorno).upload().isValido();
    }

    public boolean giornoMorto(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(UploadGiornoMorto.class, giornoBean.nome).upload().isValido();
    }

    public boolean giornoMorto(final String nomeGiorno) {
        return appContext.getBean(UploadGiornoMorto.class, nomeGiorno).upload().isValido();
    }

    public boolean annoNato(final AnnoWikiEntity annoBean) {
        return appContext.getBean(UploadAnnoNato.class, annoBean.nome).upload().isValido();
    }

    public boolean annoNato(final String nomeAnno) {
        return appContext.getBean(UploadAnnoNato.class, nomeAnno).upload().isValido();
    }

    public boolean annoMorto(final AnnoWikiEntity annoBean) {
        return appContext.getBean(UploadAnnoMorto.class, annoBean.nome).upload().isValido();
    }

    public boolean annoMorto(final String nomeAnno) {
        return appContext.getBean(UploadAnnoMorto.class, nomeAnno).upload().isValido();
    }


    public boolean giornoNatoTest(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(UploadGiornoNato.class, giornoBean.nome).test().upload().isValido();
    }

    public boolean giornoNatoTest(final String nomeGiorno) {
        return appContext.getBean(UploadGiornoNato.class, nomeGiorno).test().upload().isValido();
    }

    public boolean giornoMortoTest(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(UploadGiornoMorto.class, giornoBean.nome).test().upload().isValido();
    }

    public boolean giornoMortoTest(final String nomeGiorno) {
        return appContext.getBean(UploadGiornoMorto.class, nomeGiorno).test().upload().isValido();
    }

    public boolean annoNatoTest(final AnnoWikiEntity annoBean) {
        return appContext.getBean(UploadAnnoNato.class, annoBean.nome).test().upload().isValido();
    }

    public boolean annoNatoTest(final String nomeAnno) {
        return appContext.getBean(UploadAnnoNato.class, nomeAnno).test().upload().isValido();
    }

    public boolean annoMortoTest(final AnnoWikiEntity annoBean) {
        return appContext.getBean(UploadAnnoMorto.class, annoBean.nome).test().upload().isValido();
    }

    public boolean annoMortoTest(final String nomeAnno) {
        return appContext.getBean(UploadAnnoMorto.class, nomeAnno).test().upload().isValido();
    }

}
