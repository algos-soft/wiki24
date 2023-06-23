package it.algos.vaad24.backend.components;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaad24.backend.enumeration.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 22-Jun-2023
 * Time: 17:39
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AAnchor extends Anchor {

    public AAnchor() {
    }

    public AAnchor(String href, String text) {
        super(href, text);
    }

    public AAnchor(String href, String text, AnchorTarget target) {
        super(href, text, target);
    }

    public static AAnchor build(String href, String text) {
        AAnchor anchor = new AAnchor(href, text);
        anchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        anchor.setTarget(AnchorTarget.BLANK);

        return anchor;
    }


}
