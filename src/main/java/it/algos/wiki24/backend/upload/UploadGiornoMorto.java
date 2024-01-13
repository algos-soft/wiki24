package it.algos.wiki24.backend.upload;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 13-Jan-2024
 * Time: 06:30
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UploadGiornoMorto extends UploadGiorni {

    /**
     * Costruttore base con 1 parametro (obbligatorio) <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: getBean(UploadGiornoNato.class, nomeLista) <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public UploadGiornoMorto(String nomeLista) {
        super(nomeLista);
    }// end of constructor not @Autowired and used

    protected void fixPreferenze() {
        super.fixPreferenze();

        super.type = TypeLista.giornoMorte;
        super.clazzLista = ListaGiornoMorto.class;
        super.titoloPagina = wikiUtilityService.wikiTitleMortiGiorno(nomeLista);
    }

}
