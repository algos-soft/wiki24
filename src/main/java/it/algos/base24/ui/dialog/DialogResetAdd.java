package it.algos.base24.ui.dialog;


import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.ui.wrapper.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sat, 24-Dec-2022
 * Time: 09:20
 */
public class DialogResetAdd extends DialogConfirm {

    public static void reset(final Runnable confirmHandler) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Ripristina nel database i valori di default annullando le eventuali aggiunte e/o modifiche apportate successivamente");
        buffer.append(CAPO_HTML);
        buffer.append(ASpan.text("Sei sicuro di voler ripristinare i valori originali ?").rosso().bold().get());
        buffer.append(CAPO_HTML);
        buffer.append(ASpan.text("L'operazione è irreversibile").blue().bold().get());

        DialogConfirm.title("ResetAdd").message(buffer.toString()).confirmError(BUTTON_RESET).annullaPrimary().open(confirmHandler);
    }

}
