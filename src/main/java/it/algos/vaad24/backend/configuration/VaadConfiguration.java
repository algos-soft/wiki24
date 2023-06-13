package it.algos.vaad24.backend.configuration;

import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.backend.service.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.doppionome.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Mon, 02-Jan-2023
 * Time: 11:37
 * Normally Configuration class gets scanned and instantiated first.
 * This has to be the starting point to know about other configurations and beans.
 */
@Component
public class VaadConfiguration {

    //    @Autowired
    //    TextService textService;
    //    @Autowired
    //    AnnotationService annotationService;
    //
    public VaadConfiguration() {
    }

    //    @Bean
    //    public LogService logServiceBean() {
    //        return new LogService();
    //    }
    @Bean
    public VaadBoot vaadBootBean() {
        return new VaadBoot();
    }

//    @Autowired
//    TextService textService;
//
//    @Autowired
//    AnnotationService annotationService;


//    @Bean
//    public  TextService getTextServiceBean() {
//        return textService;
//    }

//    @Bean
//    public  AnnotationService getAnnotationServiceBean() {
//        return annotationService;
//    }

    //    @Autowired
    //    Pref.PreferenzaServiceInjector preferenzaServiceInjector;
    //    @Bean
    //    public Pref.PreferenzaServiceInjector preferenzaServiceInjector() {
    //        return new Pref.PreferenzaServiceInjector();
    //    }

    //        @Autowired
    //        ArrayService arrayService;

    //    @Bean
    //    public ArrayService arrayService() {
    //        return new ArrayService();
    //    }

    public static void start() {
            LogService.debugBean(new VaadCost());
            //        new Pref.PreferenzaServiceInjector();
    //        for (Pref pref : Pref.getAllEnums()) {
    //
    //            LogService.debugBean(pref);
    //        }

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
            LogService.debugBean(new WebService());
            LogService.debugBean(new QueryService());
            LogService.debugBean(new WikiApiService());
            LogService.debugBean(new WikiBotService());
            LogService.debugBean(new ElaboraService());
            LogService.debugBean(new DoppioNomeBackend());
            LogService.debugBean(new AttSingolareBackend());
            LogService.debugBean(new AttPluraleBackend());
            LogService.debugBean(new NazSingolareBackend());
            LogService.debugBean(new NazPluraleBackend());
        }

}