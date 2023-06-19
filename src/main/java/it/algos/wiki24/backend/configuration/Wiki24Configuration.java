package it.algos.wiki24.backend.configuration;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.backend.service.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 19-Jun-2023
 * Time: 10:06
 * <p>
 * Normally Configuration class gets scanned and instantiated first.
 * This has to be the starting point to know about other configurations and beans.
 */
@Component
public class Wiki24Configuration {

    public Wiki24Configuration() {
    }



    public static void START() {

        LogService.debugBean(new VaadCost());
        LogService.debugBean(new VaadBoot());
        LogService.debugBean(new PreferenceService());
        LogService.debugBean(new VaadData());
        LogService.debugBean(new VaadPref());
        LogService.debugBean(new VaadVar());
        LogService.debugBean(new VaadVers());
        LogService.debugBean(new AnnotationService());
        LogService.debugBean(new ArrayService());
        LogService.debugBean(new ClassService());
        LogService.debugBean(new ColumnService());
        LogService.debugBean(new FileService());
        LogService.debugBean(new HtmlService());
        LogService.debugBean(new MailService());
        LogService.debugBean(new MathService());
        LogService.debugBean(new ReflectionService());
        LogService.debugBean(new RegexService());
        LogService.debugBean(new ResourceService());
        LogService.debugBean(new RouteService());
        LogService.debugBean(new TextService());
        LogService.debugBean(new UtilityService());
        LogService.debugBean(new WikiBotService());
        LogService.debugBean(new WikiApiService());
        LogService.debugBean(new NazSingolareBackend());
        LogService.debugBean(new NazPluraleBackend());
        LogService.debugBean(new AttSingolareBackend());
        LogService.debugBean(new AttPluraleBackend());
    }

}