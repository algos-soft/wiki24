package it.algos.wiki24.backend.boot;

import it.algos.base24.backend.boot.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.view.*;
import org.springframework.stereotype.*;

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

        if (Pref.usaMenuAutomatici.is()) {
            for (Class clazz : reflectionService.getSubClazz(CrudView.class, "it.algos.wiki24")) {
                if (annotationService.usaMenuAutomatico(clazz)) {
                    BaseVar.menuRouteListProject.add(clazz);
                }
            }
        }

        Object alfa=BaseVar.menuRouteListVaadin;
        Object beta=BaseVar.menuRouteListProject;
        Object gamma=BaseVar.menuRouteListProject;
    }

}
