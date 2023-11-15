package it.algos.base24.ui.dialog;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.confirmdialog.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.ui.view.*;
import it.algos.base24.ui.wrapper.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Fri, 23-Dec-2022
 * Time: 18:09
 */
public class DialogConfirm extends ConfirmDialog {

    protected Runnable confermaHandler;

    protected Runnable annullaHandler;


    public DialogConfirm() {
        this.setConfirmText(BUTTON_CONFERMA);
        this.setCancelText(BUTTON_CANCELLA);
        this.setRejectText(BUTTON_REJECT);
    }

    public static void html(String html) {
        DialogConfirm dialog = new DialogConfirm();
        dialog.setText(HtmlService.getPar(html));
        dialog.open();
    }

    public static void show(String message) {
        DialogConfirm dialog = new DialogConfirm();
        dialog.setText(HtmlService.getPar(message));
        dialog.open();
    }

    public static DialogConfirm title(String title) {
        DialogConfirm dialog = new DialogConfirm();
        dialog.setHeader(new Html(ASpan.text(title).blue().bold().big().getPar()));
        return dialog;
    }

    public DialogConfirm message(String message) {
        this.setText(HtmlService.getPar(message));
        return this;
    }

    public DialogConfirm confirm(String confirmButtonText) {
        this.setConfirmText(confirmButtonText);
        return this;
    }

    public DialogConfirm confirmError() {
        this.setConfirmButtonTheme("error primary");
        return this;
    }

    public DialogConfirm confirmError(String confirmButtonText) {
        this.setConfirmButtonTheme("error primary");
        this.setConfirmText(confirmButtonText);
        return this;
    }

    public DialogConfirm annulla() {
        this.setCancelable(true);
        return this;
    }

    public DialogConfirm annullaPrimary() {
        this.setCancelButtonTheme("primary");
        return annulla();
    }


    public DialogConfirm rifiuta() {
        this.setCancelable(true);
        this.setRejectable(true);
        return this;
    }

    public DialogConfirm open(final Runnable confermaHandler) {
        this.confermaHandler = confermaHandler;
        this.addConfirmListener(event -> confermaHandler());
        this.addCancelListener(event -> Notifica.message("Annullato").primary().open());
        super.open();
        return this;
    }

    public DialogConfirm open(final Runnable confermaHandler, final Runnable annullaHandler) {
        this.confermaHandler = confermaHandler;
        this.annullaHandler = annullaHandler;
        this.addConfirmListener(event -> confermaHandler());
        this.addCancelListener(event -> annullaHandler());
        super.open();
        return this;
    }

    public void confermaHandler() {
        if (confermaHandler != null) {
            confermaHandler.run();
        }
        close();
    }


    public void annullaHandler() {
        if (annullaHandler != null) {
            annullaHandler.run();
        }
        close();
        Notifica.message("Annullato").open();
    }

}
