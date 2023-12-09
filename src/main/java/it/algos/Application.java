package it.algos;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import it.algos.base24.backend.boot.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.boot.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.context.event.*;
import org.springframework.scheduling.annotation.*;

import javax.inject.*;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"it.algos"}, exclude = {SecurityAutoConfiguration.class})
@Theme(value = "wiki24")
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
public class Application implements AppShellConfigurator {

    @Inject
    ApplicationContext applicationContext;

    @Inject
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
        BaseBoot currentBoot = null;
        String message ;

        try {
            currentBoot = (BaseBoot) applicationContext.getBean(BaseVar.bootClazzQualifier);
        } catch (Exception unErrore) {
        }

        if (currentBoot != null) {
            currentBoot.inizia();
        }
        else {
            if (BaseVar.bootClazz==null) {
                message = String.format("La variabile generale %s non può essere nulla", "BaseVar.bootClazz");
            }
            else {
                message = String.format("Non ho trovato nessuna classe di Boot con 'qualifier'=[%s]", BaseVar.bootClazz.getSimpleName());
            }
            logger.error(new WrapLog().exception(new Exception(message)).type(TypeLog.startup));
        }
    }


}
