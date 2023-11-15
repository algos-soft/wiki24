package it.algos.base24.ui.wrapper;

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
public class ASpan extends Span {

    private String text;

    private TypeColor color;

    private FontWeight weight;

    private FontSize fontSize;

    private FontStyle style;

    private LineHeight lineHeight;


    public static ASpan text(String text) {
        ASpan span = new ASpan();
        span.setText(text);
        span.getElement().setProperty(TAG_INNER_HTML, text);
        span.getElement().getStyle().set(TAG_HTML_FONT_SIZE, FontSize.normal.getTag());
        span.getElement().getStyle().set(TAG_HTML_LINE_HEIGHT, LineHeight.em12.getTag());
        return span;
    }

    public ASpan rosso() {
        this.color = TypeColor.rosso;
        return color(color);
    }

    public ASpan verde() {
        this.color = TypeColor.verde;
        return color(color);
    }

    public ASpan blue() {
        this.color = TypeColor.blue;
        return color(color);
    }

    public ASpan color(TypeColor typeColor) {
        this.color = typeColor;
        this.getElement().getStyle().set(TAG_HTML_COLOR, color.getTag());
        return this;
    }

    public ASpan bold() {
        this.weight = FontWeight.bold;
        return weight(weight);
    }

    public ASpan weight(FontWeight typeWeight) {
        this.weight = typeWeight;
        this.getElement().getStyle().set(TAG_HTML_FONT_WEIGHT, weight.getTag());
        return this;
    }

    public ASpan small() {
        this.fontSize = FontSize.em7;
        this.getElement().getStyle().set(TAG_HTML_LINE_HEIGHT, LineHeight.em12.getTag());
        return size(fontSize);
    }

    public ASpan big() {
        this.fontSize = FontSize.em9;
        return size(fontSize);
    }

    public ASpan size(FontSize typeSize) {
        this.fontSize = typeSize;
        this.getElement().getStyle().set(TAG_HTML_FONT_SIZE, fontSize.getTag());
        return this;
    }

    public ASpan italic() {
        this.style = FontStyle.italic;
        return stile(style);
    }

    public ASpan stile(FontStyle typeStyle) {
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