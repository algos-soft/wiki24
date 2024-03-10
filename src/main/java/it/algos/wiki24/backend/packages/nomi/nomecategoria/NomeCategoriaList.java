package it.algos.wiki24.backend.packages.nomi.nomecategoria;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.list.*;
import it.algos.base24.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.list.*;
import static it.algos.wiki24.backend.packages.nomi.nomecategoria.NomeCategoriaModulo.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NomeCategoriaList extends WikiList {


    public NomeCategoriaList(final NomeCategoriaModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }

    @Override
    protected void fixHeader() {
        Anchor anchor1;
        Anchor anchor2;
        Anchor anchor3;
        String link;
        String message;
        String plurale = "Plurale attività";
        String ex = "Ex attività";
        String pagina = "Link attività";

         anchor1 = WAnchor.build(CAT + CAT_MASCHILE , "Maschili");
         anchor2 = WAnchor.build(CAT + CAT_FEMMINILE , "Femminili");
         anchor3 = WAnchor.build(CAT + CAT_ENTRAMBI, "Entrambi");

        message = "Tavola di base. Costruita dalle categorie wiki: ";
        Span testo = new Span(message);
        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());
        headerPlaceHolder.add(new Span(testo, anchor1, new Text(SEP), anchor2, new Text(SEP), anchor3));

//        message = "Nomi doppi (esempio: 'Maria Teresa') elencati nella pagina di progetto";
//        headerPlaceHolder.add(ASpan.text(message).verde());
//
//        message = "I nomi mantengono spazi, maiuscole, minuscole e caratteri accentati come in originale";
//        headerPlaceHolder.add(ASpan.text(message).verde());
//
//        message = String.format("Download%sCancella tutto e scarica la pagina wiki", FORWARD);
//        headerPlaceHolder.add(ASpan.text(message).rosso());
//
//        message = "L'elaborazione delle liste biografiche e gli upload delle liste di nomi sono gestiti dalla task Nome.";
//        headerPlaceHolder.add(ASpan.text(message).rosso().small());

        super.fixHeader();
    }

}// end of CrudList class
