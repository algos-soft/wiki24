package it.algos.wiki24.backend.service;

import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.nomi.nomebio.*;
import it.algos.wiki24.backend.packages.tabelle.anni.*;
import it.algos.wiki24.backend.packages.tabelle.attplurale.*;
import it.algos.wiki24.backend.packages.tabelle.giorni.*;
import it.algos.wiki24.backend.packages.tabelle.nazplurale.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

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

    public void giornoNato(final GiornoWikiEntity giornoBean) {
        WResult risultato;
        Upload istanzaUpload;
        List<String> listaStr;
        Lista istanzaLista;

        // pagina principale
        istanzaUpload = appContext.getBean(Upload.class, giornoBean.nome, TypeLista.giornoNascita);
        risultato = istanzaUpload.uploadOnly();

        // sottopagine
        listaStr = istanzaUpload.getListaSottoPagine();
        if (listaStr != null && listaStr.size() > 0) {
            istanzaLista = istanzaUpload.getIstanzaLista();
            for (String keySottopagina : listaStr) {
                risultato = appContext.getBean(Upload.class, giornoBean.nome, TypeLista.giornoNascita, keySottopagina, istanzaLista).uploadOnly();
            }
        }
    }

    public WResult giornoMortoTest(final GiornoWikiEntity giornoBean) {
        return appContext.getBean(Upload.class, giornoBean.nome, TypeLista.giornoMorte).test().uploadOnly();
    }

    public void giornoMorto(final GiornoWikiEntity giornoBean) {
        WResult risultato;
        Upload istanzaUpload;
        List<String> listaStr;
        Lista istanzaLista;

        // pagina principale
        istanzaUpload = appContext.getBean(Upload.class, giornoBean.nome, TypeLista.giornoMorte);
        risultato = istanzaUpload.uploadOnly();

        // sottopagine
        listaStr = istanzaUpload.getListaSottoPagine();
        if (listaStr != null && listaStr.size() > 0) {
            istanzaLista = istanzaUpload.getIstanzaLista();
            for (String keySottopagina : listaStr) {
                risultato = appContext.getBean(Upload.class, giornoBean.nome, TypeLista.giornoMorte, keySottopagina, istanzaLista).uploadOnly();
            }
        }
    }

    public WResult annoNatoTest(final AnnoWikiEntity annoBean) {
        return appContext.getBean(Upload.class, annoBean.nome, TypeLista.annoNascita).test().uploadOnly();
    }

    public void annoNato(final AnnoWikiEntity annoBean) {
        WResult risultato;
        Upload istanzaUpload;
        List<String> listaStr;
        Lista istanzaLista;

        // pagina principale
        istanzaUpload = appContext.getBean(Upload.class, annoBean.nome, TypeLista.annoNascita);
        risultato = istanzaUpload.uploadOnly();

        // sottopagine
        listaStr = istanzaUpload.getListaSottoPagine();
        if (listaStr != null && listaStr.size() > 0) {
            istanzaLista = istanzaUpload.getIstanzaLista();
            for (String keySottopagina : listaStr) {
                risultato = appContext.getBean(Upload.class, annoBean.nome, TypeLista.annoNascita, keySottopagina, istanzaLista).uploadOnly();
            }
        }
    }

    public WResult annoMortoTest(final AnnoWikiEntity annoBean) {
        return appContext.getBean(Upload.class, annoBean.nome, TypeLista.annoMorte).test().uploadOnly();
    }

    public void annoMorto(final AnnoWikiEntity annoBean) {
        WResult risultato;
        Upload istanzaUpload;
        List<String> listaStr;
        Lista istanzaLista;

        // pagina principale
        istanzaUpload = appContext.getBean(Upload.class, annoBean.nome, TypeLista.annoMorte);
        risultato = istanzaUpload.uploadOnly();

        // sottopagine
        listaStr = istanzaUpload.getListaSottoPagine();
        if (listaStr != null && listaStr.size() > 0) {
            istanzaLista = istanzaUpload.getIstanzaLista();
            for (String keySottopagina : listaStr) {
                risultato = appContext.getBean(Upload.class, annoBean.nome, TypeLista.annoMorte, keySottopagina, istanzaLista).uploadOnly();
            }
        }
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

    public WResult attivitaOnly(final AttPluraleEntity attivitaBean) {
        return appContext.getBean(Upload.class, attivitaBean.plurale, TypeLista.attivitaPlurale).uploadOnly();
    }

    public void attivita(final AttPluraleEntity attivitaBean) {
        WResult risultato;
        Upload istanzaUpload;
        List<String> listaStrSotto;
        List<String> listaStrSottoSotto;
        Lista istanzaLista;

        // pagina principale
        istanzaUpload = appContext.getBean(Upload.class, attivitaBean.plurale, TypeLista.attivitaPlurale);
        risultato = istanzaUpload.uploadOnly();

        // sottopagine
        listaStrSotto = istanzaUpload.getListaSottoPagine();
        if (listaStrSotto != null && listaStrSotto.size() > 0) {
            istanzaLista = istanzaUpload.getIstanzaLista();
            for (String keySottopagina : listaStrSotto) {
                risultato = appContext.getBean(Upload.class, attivitaBean.plurale, TypeLista.attivitaPlurale, keySottopagina, istanzaLista).uploadOnly();
            }
        }

        // sottosottopagine
        listaStrSottoSotto = istanzaUpload.getListaSottoSottoPagine();
        if (listaStrSottoSotto != null && listaStrSottoSotto.size() > 0) {
            istanzaLista = istanzaUpload.getIstanzaLista();
            for (String keySottoSottopagina : listaStrSottoSotto) {
                risultato = appContext.getBean(Upload.class, attivitaBean.plurale, TypeLista.attivitaPlurale, keySottoSottopagina, istanzaLista).uploadOnly();
            }
        }
    }

    public WResult nazionalitaTest(final NazPluraleEntity nazionalitaBean) {
        return appContext.getBean(Upload.class, nazionalitaBean.plurale, TypeLista.nazionalitaPlurale).test().uploadOnly();
    }

    public WResult nazionalitaOnly(final NazPluraleEntity nazionalitaBean) {
        return appContext.getBean(Upload.class, nazionalitaBean.plurale, TypeLista.nazionalitaPlurale).uploadOnly();
    }
    public WResult nomeTest(final NomeBioEntity nomeBean) {
        return appContext.getBean(Upload.class, nomeBean.nome, TypeLista.nomi).test().uploadOnly();
    }
    public WResult nomeOnly(final NomeBioEntity nomeBean) {
        return appContext.getBean(Upload.class, nomeBean.nome, TypeLista.nomi).uploadOnly();
    }

    public void nazionalita(final NazPluraleEntity nazionalitaBean) {
        WResult risultato;
        Upload istanzaUpload;
        List<String> listaStrSotto;
        List<String> listaStrSottoSotto;
        Lista istanzaLista;

        // pagina principale
        istanzaUpload = appContext.getBean(Upload.class, nazionalitaBean.plurale, TypeLista.nazionalitaPlurale);
        risultato = istanzaUpload.uploadOnly();

        // sottopagine
        listaStrSotto = istanzaUpload.getListaSottoPagine();
        if (listaStrSotto != null && listaStrSotto.size() > 0) {
            istanzaLista = istanzaUpload.getIstanzaLista();
            for (String keySottopagina : listaStrSotto) {
                risultato = appContext.getBean(Upload.class, nazionalitaBean.plurale, TypeLista.nazionalitaPlurale, keySottopagina, istanzaLista).uploadOnly();
            }
        }

        // sottosottopagine
        listaStrSottoSotto = istanzaUpload.getListaSottoSottoPagine();
        if (listaStrSottoSotto != null && listaStrSottoSotto.size() > 0) {
            istanzaLista = istanzaUpload.getIstanzaLista();
            for (String keySottoSottopagina : listaStrSottoSotto) {
                risultato = appContext.getBean(Upload.class, nazionalitaBean.plurale, TypeLista.nazionalitaPlurale, keySottoSottopagina, istanzaLista).uploadOnly();
            }
        }
    }

}
