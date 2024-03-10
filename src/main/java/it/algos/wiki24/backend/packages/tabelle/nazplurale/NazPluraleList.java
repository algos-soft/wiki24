package it.algos.wiki24.backend.packages.tabelle.nazplurale;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.components.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.ui.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.list.*;
import it.algos.wiki24.backend.packages.tabelle.attplurale.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NazPluraleList extends WikiList {

    private IndeterminateCheckbox checkSoglia;

    private IndeterminateCheckbox checkLista;

    private IndeterminateCheckbox checkPagina;


    public NazPluraleList(final NazPluraleModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaInfoDownload = true;
        super.usaInfoElabora = true;
        super.usaInfoUpload = true;

        this.usaBottoneDownload = true;
        this.usaBottoneElabora = true;
        this.usaBottoneUploadAll = true;

        super.usaBottoneWikiView = false;
        super.usaBottoneTest = true;
        super.usaBottoneTest1 = false;
        super.usaBottoneTest2 = false;
        super.usaBottoneUpload = true;
        super.usaBottoneUpload1 = false;
        super.usaBottoneUpload2 = false;
    }

    @Override
    protected void fixHeader() {
        Anchor anchor1;
        Anchor anchor2;
        String categoria = CAT + "Bio nazionalità";

        anchor1 = WAnchor.build(categoria, textService.setQuadre(CATEGORIA));
        anchor2 = WAnchor.build(PATH_STATISTICHE_NAZIONALITA, textService.setQuadre(STATISTICHE));

        message = "Tavola di base. Vedi pagine wiki: ";
        Span testo = new Span(message);
        testo.getStyle().set(FontWeight.HTML, FontWeight.bold.getTag());
        testo.getStyle().set(TAG_HTML_COLOR, TypeColor.verde.getTag());

        headerPlaceHolder.add(new Span(testo, anchor1, new Text(SEP), anchor2));

        message = "Indipendentemente da come sono scritte nei moduli, tutte le nazionalità plurali sono convertite in minuscolo.";
        headerPlaceHolder.add(ASpan.text(message).size(FontSize.em8).rosso());

        message = String.format("Download%sCancella tutto. Esegue un download di NazSingolare.", FORWARD);
        headerPlaceHolder.add(ASpan.text(message).rosso());
        message = String.format("Download%sCrea una nuova tavola dai plurali (DISTINCT) di NazSingolare.", FORWARD);
        headerPlaceHolder.add(ASpan.text(message).rosso());
        message = String.format("Download%sCrea un link alla PaginaLista. Crea un link alla pagina della Nazione.", FORWARD);
        headerPlaceHolder.add(ASpan.text(message).rosso());
        message = String.format("Elabora%sCalcola il numero di voci biografiche che usano ogni singola nazionalità plurale.", FORWARD);
        headerPlaceHolder.add(ASpan.text(message).rosso());
        message = "Gestisce l'elaborazione delle liste biografiche e gli upload delle liste di nazionalità.";
        headerPlaceHolder.add(ASpan.text(message).rosso().small());

        super.fixHeader();
        message = "Lista: blue=normale, rosso=esiste ma non dovrebbe, verde=andrebbe creata";
        headerPlaceHolder.add(ASpan.text(message).blue().small());
    }


    /**
     * Regola numero, ordine e visibilità delle colonne della grid <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixColumns() {
        Grid.Column newLista = grid.addColumn(new ComponentRenderer<>(entity -> {
            String wikiTitle = textService.primaMaiuscola(((NazPluraleEntity) entity).lista);
            String anchorPrefix = TAG_WIKI + annotationService.getAnchorPrefix(NazPluraleEntity.class, "lista");
            Anchor anchor = new Anchor(anchorPrefix + wikiTitle, wikiTitle);
            boolean superaSoglia = ((NazPluraleEntity) entity).superaSoglia;
            if (((NazPluraleEntity) entity).isEsisteLista()) {
                if (superaSoglia) {
                    anchor.getElement().getStyle().set("color", "blue");
                }
                else {
                    anchor.getElement().getStyle().set("color", "red");
                }
            }
            else {
                if (superaSoglia) {
                    anchor.getElement().getStyle().set("color", "green");
                }
                else {
                    return new Span();
                }
            }
            return new Span(anchor);
        })).setHeader("Lista").setKey("newLista").setWidth("10rem").setFlexGrow(0);

        Grid.Column plurale = grid.getColumnByKey("plurale");
        Grid.Column txtSingolari = grid.getColumnByKey("txtSingolari");

        Grid.Column oldLista = grid.getColumnByKey("lista");

        Grid.Column pagina = grid.getColumnByKey("pagina");
        Grid.Column numBio = grid.getColumnByKey("numBio");
        Grid.Column numSingolari = grid.getColumnByKey("numSingolari");
        Grid.Column superaSoglia = grid.getColumnByKey("superaSoglia");
        Grid.Column esisteLista = grid.getColumnByKey("esisteLista");
        Grid.Column esistePagina = grid.getColumnByKey("esistePagina");

        grid.removeColumn(oldLista);
        grid.setColumnOrder(plurale, txtSingolari, newLista, pagina, numBio, numSingolari, superaSoglia, esisteLista, esistePagina);
    }

    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        checkSoglia = super.creaFiltroCheckBox(checkSoglia, "Soglia");
        checkLista = super.creaFiltroCheckBox(checkLista, "Lista");
        checkPagina = super.creaFiltroCheckBox(checkPagina, "Pagina");
    }

    @Override
    protected void fixFiltri() {
        super.fixFiltri();

        super.fixFiltroCheckBox(checkSoglia, "superaSoglia");
        super.fixFiltroCheckBox(checkLista, "esisteLista");
        super.fixFiltroCheckBox(checkPagina, "esistePagina");
    }

}// end of CrudList class
