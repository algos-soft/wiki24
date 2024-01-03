package it.algos.wiki24.backend.wrapper;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.service.*;
import jakarta.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 03-Jan-2024
 * Time: 12:18
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WrapDidascalia {

    @Inject
    private DidascaliaService didascaliaService;

    private String primoLivello;

    private String secondoLivello;

    private String terzoLivello;

    private String quartoLivello;

    private String didascalia;

    private BioMongoEntity bio;

    public WrapDidascalia() {
    }

    public WrapDidascalia(BioMongoEntity bio) {
        this.bio = bio;
    }

    /**
     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una sottoclasse, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
     * Se esistono delle sottoclassi, passa di qui per ognuna di esse (oltre a questa classe madre) <br>
     */
    @PostConstruct
    private void postConstruct() {
        didascalia = didascaliaService.didascaliaGiornoNato(bio);
    }

    public String getPrimoLivello() {
        return primoLivello;
    }

    public String getSecondoLivello() {
        return secondoLivello;
    }

    public String getTerzoLivello() {
        return terzoLivello;
    }

    public String getQuartoLivello() {
        return quartoLivello;
    }

    public String getDidascalia() {
        return didascalia;
    }

}
