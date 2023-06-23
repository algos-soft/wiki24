package it.algos.vaad24.backend.components;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 23-Jun-2023
 * Time: 05:41
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WAnchor extends Anchor {

    public WAnchor() {
    }

    public WAnchor(String href) {
        super(PATH_WIKI + href, href.substring(href.indexOf(SLASH) + 1));
    }

    public WAnchor(String href, String text) {
        super(PATH_WIKI + href, text);
    }

    //    public WAnchor(String href, String text, AnchorTarget target) {
    //        super(PATH_WIKI + href, text, target);
    //    }

    public static WAnchor build(String href) {
        WAnchor anchor = new WAnchor(href);
        anchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        anchor.setTarget(AnchorTarget.BLANK);

        return anchor.bold();
    }

    public static WAnchor build(String href, String text) {
        WAnchor anchor = new WAnchor(href, text);
        anchor.setTarget(AnchorTarget.BLANK);

        return anchor.bold();
    }

    public WAnchor normal() {
        this.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.normal.getTag());
        return this;
    }

    public WAnchor bold() {
        this.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        return this;
    }

    public WAnchor blue() {
        this.getElement().getStyle().set(TAG_HTML_COLOR, AETypeColor.blue.getTag());
        return this;
    }

    public WAnchor verde() {
        this.getElement().getStyle().set(TAG_HTML_COLOR, AETypeColor.verde.getTag());
        return this;
    }

    public WAnchor rosso() {
        this.getElement().getStyle().set(TAG_HTML_COLOR, AETypeColor.rosso.getTag());
        return this;
    }

    public WAnchor italic() {
        this.getElement().getStyle().set(TAG_HTML_FONT_STYLE, AEFontStyle.italic.getTag());
        return this;
    }

}
