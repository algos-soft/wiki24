package it.algos.base24.backend.wrapper;

import it.algos.base24.backend.enumeration.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 25-mar-2022
 * Time: 13:41
 * Wrapper di informazioni (Fluent API) per costruire un elemento Span <br>
 * Può contenere:
 * -weight (AETypeWeight) grassetto
 * -color (AETypeColor) colore
 */
public class WrapSpan {

    private String message;

    private FontWeight weight;

    private TypeColor color;

    private FontSize fontHeight;

    private LineHeight lineHeight;

    private FontStyle style;

    /**
     * Fluent pattern Builder <br>
     */
    public WrapSpan(String message) {
        this.message = message;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapSpan message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapSpan weight(FontWeight weight) {
        this.weight = weight;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapSpan color(TypeColor color) {
        this.color = color;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapSpan lineHeight(LineHeight lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapSpan fontHeight(FontSize fontHeight) {
        this.fontHeight = fontHeight;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapSpan style(FontStyle style) {
        this.style = style;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public FontWeight getWeight() {
        return weight;
    }

    public TypeColor getColor() {
        return color;
    }

    public FontSize getFontHeight() {
        return fontHeight;
    }

    public LineHeight getLineHeight() {
        return lineHeight;
    }

    public FontStyle getStyle() {
        return style;
    }

}