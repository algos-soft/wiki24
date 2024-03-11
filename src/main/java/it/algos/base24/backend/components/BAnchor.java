package it.algos.base24.backend.components;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 30-Jun-2023
 * Time: 14:15
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BAnchor extends Anchor {

    private static final boolean BOLD = false;

    public BAnchor() {
    }

    public BAnchor(String href, String text) {
        super(href, text);
    }

    public static BAnchor build(String href, String text) {
        BAnchor wAnchor;

        if (!href.startsWith(TAG_INIZIALE)) {
            href = TAG_INIZIALE + href;
        }

        wAnchor = new BAnchor(href, text);
        wAnchor.setTarget(AnchorTarget.BLANK);
        if (BOLD) {
            wAnchor.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        }
        wAnchor.getElement().getStyle().set("color", "blue");
        return wAnchor;
    }

}