package it.algos.wiki24.ui.dialog;

import com.vaadin.flow.spring.annotation.SpringComponent;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.ui.dialog.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 02-Apr-2023
 * Time: 14:07
 */

public class AUpload extends AConfirm {

    public static void upload(final Runnable confirmHandler) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Upload sul server wiki di TUTTE le pagine sovrascrivendo quelle esistenti.");
        buffer.append(CAPO_HTML);
        buffer.append(ASpan.text("Sei sicuro di volerle sovrascrivere tutte ?").rosso().bold().get());
        buffer.append(CAPO_HTML);
        buffer.append(ASpan.text("L'operazione è irreversibile").blue().bold().get());

        AConfirm.title("Upload").message(buffer.toString()).confirmError("Upload").annullaPrimary().open(confirmHandler);
    }
    public static void uploadSelezione(final Runnable confirmHandler) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Upload sul server wiki delle pagine selezionate. Vengono sovrascritte quelle esistenti.");
        buffer.append(CAPO_HTML);
        buffer.append(ASpan.text("Sei sicuro di volerle sovrascrivere le pagine selezionate ?").rosso().bold().get());
        buffer.append(CAPO_HTML);
        buffer.append(ASpan.text("L'operazione è irreversibile").blue().bold().get());

        AConfirm.title("Upload").message(buffer.toString()).confirmError("Upload").annullaPrimary().open(confirmHandler);
    }

}