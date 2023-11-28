package it.algos.wiki24.backend.schedule;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.schedule.*;
import it.algos.base24.backend.service.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.*;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 28-Nov-2023
 * Time: 17:18
 */
@Service
public class Wiki24Schedule extends ScheduleTaskUsingCronExpression {

    @Inject
    public NazSingolareModulo nazSingolareModulo;

    protected void ogniMinuto() {
        nazSingolareModulo.resetDelete();
    }

}
