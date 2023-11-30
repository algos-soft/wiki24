package it.algos.wiki24.backend.boot;

import it.algos.base24.backend.boot.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import static it.algos.base24.backend.boot.BaseVar.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.base24.ui.view.*;
import it.algos.wiki24.backend.enumeration.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 16-Nov-2023
 * Time: 13:55
 */
@Service
public class WikiBoot extends BaseBoot {

    /**
     * Regola le variabili generali dell' applicazione con il loro valore iniziale di default <br>
     * Le variabili (static) sono uniche per tutta l' applicazione <br>
     * Alcuni valori sono hardcoded, altri sono 'letti' da [application.properties] <br>
     * Il loro valore può essere modificato SOLO in questa classe o in una sua sottoclasse <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixVariabili() {
        super.fixVariabili();
    }


    /**
     * Injection di SpringBoot <br>
     * Usa la injection di SpringBoot per ogni Enumeration della lista globale <br>
     * NON crea le preferenze su mondoDB <br>
     * Non deve essere sovrascritto <br>
     */
    public void fixEnumerationPreferenze() {
        for (WPref pref : WPref.values()) {
            pref.preferenzaModulo = this.preferenzaModulo;
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
    }
}



