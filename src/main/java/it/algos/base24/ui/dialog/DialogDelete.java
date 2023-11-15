package it.algos.base24.ui.dialog;


import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.ui.wrapper.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sat, 24-Dec-2022
 * Time: 10:00
 */
public class DialogDelete extends DialogConfirm {

    public static final String IRREVERSIBILE = ASpan.text("L'operazione è irreversibile").blue().bold().get();

    public static void delete(final Runnable confirmHandler) {
        DialogDelete.delete(VUOTA, confirmHandler);
    }

    public static void delete(final String entityName, final Runnable confirmHandler) {
        StringBuffer buffer = new StringBuffer();

        if (entityName == null || entityName.length() < 1) {
            buffer.append("Cancella dal database la singola entity");
        }
        else {
            buffer.append("Cancella dal database ");
            buffer.append(ASpan.text(entityName).verde().bold().get());
        }

        buffer.append(CAPO_HTML);
        buffer.append(ASpan.text("Vuoi veramente cancellare questo elemento ?").rosso().bold().get());
        buffer.append(CAPO_HTML);
        buffer.append(IRREVERSIBILE);

        DialogDelete.title("Delete").message(buffer.toString()).confirmError("Delete").annullaPrimary().open(confirmHandler);
    }

    public static void deleteAll(final Runnable confirmHandler) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Cancella dal database tutti i valori della collezione");
        buffer.append(CAPO_HTML);
        buffer.append(ASpan.text("Sei sicuro di volerli cancellare tutti ?").rosso().bold().get());
        buffer.append(CAPO_HTML);
        buffer.append(IRREVERSIBILE);

        DialogDelete.title("Delete all").message(buffer.toString()).confirmError("Delete all").annullaPrimary().open(confirmHandler);
    }


}
