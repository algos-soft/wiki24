package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 21-Jun-2023
 * Time: 08:14
 * <p>
 * La lista è una mappa di WrapLista suddivisa in paragrafi, che contiene tutte le informazioni per scrivere le righe della pagina <br>
 * Usata fondamentalmente da UploadNomi con appContext.getBean(ListaNomi.class).mappaWrap() <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ListaNomi extends Lista {


    /**
     * Costruttore base con 1 parametro (obbligatorio) <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(ListaNomi.class, nomeLista) <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public ListaNomi(String nomeLista) {
        super(nomeLista);
    }// end of constructor not @Autowired and used


    protected void fixPreferenze() {
        super.fixPreferenze();

        super.backend = super.nomeBackend;
        super.nomeLista = textService.primaMaiuscola(nomeLista);
        super.titoloPagina = wikiUtility.wikiTitleNomi(nomeLista);
        super.typeLista = AETypeLista.nomi;
        super.typeLinkParagrafi = (AETypeLink) WPref.linkParagrafiNomi.getEnumCurrentObj();
        super.paragrafoAltre = TAG_LISTA_NO_ATTIVITA;
        super.patternCompleto = true;
    }


    /**
     * Pattern Builder <br>
     */
    public ListaNomi typeLista(AETypeLista typeLista) {
        super.patternCompleto = false;
        return switch (typeLista) {
            case nomi -> {
                super.patternCompleto = true;
                yield (ListaNomi) super.typeLista(typeLista);
            }
            default -> this;
        };
    }

}
