package it.algos;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import it.algos.base24.backend.boot.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.*;
import org.springframework.context.*;
import org.springframework.context.event.*;
import org.springframework.scheduling.annotation.*;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"it.algos"}, exclude = {SecurityAutoConfiguration.class})
@Theme(value = "wiki24")
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
public class Application implements AppShellConfigurator {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    protected LogService logger;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Primo ingresso nel programma <br>
     * <p>
     * 1) Prima SpringBoot crea tutte le classi SINGLETON, metodo constructor() <br>
     * 2) Dopo SpringBoot esegue tutti i metodi con l'annotation @PostConstruct delle classi SINGLETON appena costruite <br>
     * 3) Infine SpringBoot arriva qui <br>
     */
    @EventListener(ContextRefreshedEvent.class)
    private void doSomethingAfterStartup() {
        try {
            BaseBoot currentBoot = (BaseBoot) applicationContext.getBean(BaseVar.bootClazz);
            currentBoot.inizia();
        } catch (Exception unErrore) {
            String message = String.format("La variabile generale %s non può essere nulla", "BaseVar.bootClazz");
            logger.error(new WrapLog().exception(unErrore).message(message).type(TypeLog.startup));
        }
    }

//    public static void main(String[] args) {
////        VaadBoot.start();
////        Wiki24Boot.start();
//        SpringApplication.run(Wiki24App.class, args);
//    }

}
