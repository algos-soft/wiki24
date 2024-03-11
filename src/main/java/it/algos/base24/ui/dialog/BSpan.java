package it.algos.base24.ui.dialog;

import com.vaadin.flow.component.html.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Thu, 22-Dec-2022
 * Time: 17:47
 */
public class BSpan extends Span {

    private String text;

    private TypeColor color;

    private FontWeight weight;

    private FontSize fontSize;

    private FontStyle style;

    private LineHeight lineHeight;


    public static BSpan text(String text) {
        BSpan span = new BSpan();
        span.setText(text);
        span.getElement().setProperty(TAG_INNER_HTML, text);
        span.getElement().getStyle().set(TAG_HTML_FONT_SIZE, FontSize.normal.getTag());
        span.getElement().getStyle().set(TAG_HTML_LINE_HEIGHT, LineHeight.em12.getTag());
        return span;
    }

    /**
     * Pattern Builder <br>
     */
    public BSpan rosso() {
        this.color = TypeColor.rosso;
        return color(color);
    }

    /**
     * Pattern Builder <br>
     */
    public BSpan verde() {
        this.color = TypeColor.verde;
        return color(color);
    }

    /**
     * Pattern Builder <br>
     */
    public BSpan blue() {
        this.color = TypeColor.blue;
        return color(color);
    }

    /**
     * Pattern Builder <br>
     */
    public BSpan color(TypeColor typeColor) {
        this.color = typeColor;
        this.getElement().getStyle().set(TAG_HTML_COLOR, color.getTag());
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public BSpan bold() {
        this.weight = FontWeight.bold;
        return weight(weight);
    }

    /**
     * Pattern Builder <br>
     */
    public BSpan weight(FontWeight typeWeight) {
        this.weight = typeWeight;
        this.getElement().getStyle().set(TAG_HTML_FONT_WEIGHT, weight.getTag());
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public BSpan small() {
        this.fontSize = FontSize.em7;
        this.getElement().getStyle().set(TAG_HTML_LINE_HEIGHT, LineHeight.em12.getTag());
        return size(fontSize);
    }

    /**
     * Pattern Builder <br>
     */
    public BSpan big() {
        this.fontSize = FontSize.em9;
        return size(fontSize);
    }

    /**
     * Pattern Builder <br>
     */
    public BSpan size(FontSize typeSize) {
        this.fontSize = typeSize;
        this.getElement().getStyle().set(TAG_HTML_FONT_SIZE, fontSize.getTag());
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public BSpan italic() {
        this.style = FontStyle.italic;
        return stile(style);
    }

    /**
     * Pattern Builder <br>
     */
    public BSpan stile(FontStyle typeStyle) {
        this.style = typeStyle;
        this.getElement().getStyle().set(TAG_HTML_FONT_STYLE, style.getTag());
        return this;
    }

    public String get() {
        return this.getElement().toString();
    }

    public String getPar() {
        return "<p>" + this.get() + "</p>";
    }

}