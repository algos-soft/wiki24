package it.algos.base24.backend.packages.geografia.provincia;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class ProvinciaList extends CrudList {


    public ProvinciaList(final ProvinciaModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    public void fixHeader() {
        Anchor anchor;
        String link;
        String caption;
        String alfa = "Province d'Italia";

        link = String.format("%s%s", TAG_WIKI, alfa);
        caption = String.format("%s%s%s", QUADRA_INI, alfa, QUADRA_END);
        anchor = new Anchor(link, caption);
        anchor.getElement().getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());

        Span testo = new Span(typeList.getInfoScopo());
        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());
        headerPlaceHolder.add(new Span(testo, anchor));

        super.fixHeader();
    }

}// end of CrudList class
