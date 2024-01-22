package it.algos.wiki24.backend.boot;

import it.algos.base24.backend.boot.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import static it.algos.base24.backend.boot.BaseVar.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.interfaces.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.login.*;
import it.algos.wiki24.backend.packages.tabelle.attsingolare.*;
import it.algos.wiki24.backend.schedule.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 16-Nov-2023
 * Time: 13:55
 */
@Service
@Component("wikiBoot")
public class WikiBoot extends BaseBoot {

    @Inject
    QueryService queryService;
    @Inject
    BotLogin botLogin;

    public WikiBoot() {
        //        System.out.println(String.format("Costruttore (%s) mentre sono nella classe [%s]", "WikiBoot", getClass().getSimpleName()));
        //        BaseVar.bootClazz = this.getClass();
        //        BaseVar.bootClazzQualifier=this.getClass().getSimpleName();
        //        System.out.println(String.format("Regolata bootClazz=[%s]", BaseVar.bootClazz.getSimpleName()));
    }

    //    @PostConstruct
    //    protected void postConstruct() {
    //        System.out.println(String.format("postConstruct (%s) mentre sono nella classe [%s]", "WikiBoot", getClass().getSimpleName()));
    //        System.out.println(String.format("Adesso bootClazz=[%s]", BaseVar.bootClazz.getSimpleName()));
    //        //        try {
    //        //            BaseVar.bootClazz = resourceService.getClazzBoot(projectModulo, projectPrefix);
    //        //            System.out.println(String.format("Regolata bootClazz=[%s]",BaseVar.bootClazz.getSimpleName()));
    //        //        } catch (Exception unErrore) {
    //        //            String message = String.format("Non ho trovato una delle due property %s o %s nelle risorse", projectModulo, projectPrefix);
    //        //            System.out.println(message);
    //        ////        }
    ////        this.fixVariabili();
    //    }

    public void inizia() {
        super.inizia();
    }


    /**
     * Aggiunta delle preferenze (Enumeration) alla lista BaseVar.prefList <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void addPreferenze() {
        super.addPreferenze();
        for (IPref pref : WPref.values()) {
            prefList.add(pref);
        }
    }

    /**
     * Aggiunge al menu le @Route (view) standard e specifiche <br>
     * Questa classe viene invocata PRIMA della chiamata del browser <br>
     * <p>
     * Nella sottoclasse che invoca questo metodo, aggiunge le @Route (view) specifiche dell' applicazione <br>
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in BaseVar <br>
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixMenuRoutes() {
        super.fixMenuRoutes();
        List<Class> listaViewsProject;
        String message;
        String viewName;

        if (Pref.usaMenuAutomatici.is()) {
            listaViewsProject = reflectionService.getSubClazzViewProgetto();
            if (listaViewsProject != null) {
                for (Class clazz : listaViewsProject) {
                    if (annotationService.usaMenuAutomatico(clazz)) {
                        menuRouteListProject.add(clazz);
                        viewName = clazz.getSimpleName();
                        viewName = textService.levaCoda(viewName, SUFFIX_VIEW);
                        nameViewListProject.add(viewName);
                    }
                }
            }
            else {
                message = String.format("Non esiste nessuna view/route nel progetto [%s]", projectCurrent);
                logger.warn(new WrapLog().exception(new Exception(message)));
            }
        }
        else {
            menuRouteListProject.add(AttSingolareView.class);
        }
    }

    @Override
    protected void fixTask() {
        BaseVar.taskList.add(appContext.getBean(TaskDownloadBioServer.class));
        BaseVar.taskList.add(appContext.getBean(TaskElaboraBioMongo.class));
        BaseVar.taskList.add(appContext.getBean(TaskUploadGiorni.class));
        BaseVar.taskList.add(appContext.getBean(TaskUploadAnni.class));
        super.fixTask();
    }

    @Override
    protected void fixLogin() {
        String message;

        if (!queryService.logAsBot()) {
            message = String.format("Non sono riuscito a collegarmi come [%s]", TypeUser.bot);
            logger.warn(new WrapLog().message(message).type(TypeLog.setup));
        }
    }

}



