package it.algos.wiki24.backend.list;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.list.*;
import it.algos.base24.backend.logic.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 28-Nov-2023
 * Time: 18:32
 */
public abstract class WikiList extends CrudList {
//    protected VerticalLayout infoPlaceHolder;

    public WikiList(final CrudModulo crudModulo) {
        super(crudModulo);
    }


//    @Override
//    public void fixAlert() {
//
////        alertPlaceHolder.add(layout);
//    }


//    /**
//     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
//     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
//     */
//    public void fixInfo() {
//        infoPlaceHolder.removeAll();
//        if (usaInfoDownload) {
//            if (lastReset != null && lastReset.get() instanceof LocalDateTime reset) {
//                if (reset.equals(ROOT_DATA_TIME)) {
//                    message = "Reset non ancora effettuato";
//                }
//                else {
//                    message = String.format("Ultimo reset effettuato il %s", dateService.get(reset));
//                    if (durataReset != null && durataDownload.get() instanceof Integer durata) {
//                        message += String.format(" in circa %d %s.", durata, "minuti");
//                    }
//                }
//                addSpan(ASpan.text(message).verde().small());
//            }
//            if (lastDownload != null && lastDownload.get() instanceof LocalDateTime download) {
//                if (download.equals(ROOT_DATA_TIME)) {
//                    message = "Download non ancora effettuato";
//                }
//                else {
//                    message = String.format("Ultimo download effettuato il %s", dateService.get(download));
//                    if (durataDownload != null && durataDownload.get() instanceof Integer durata) {
//                        if (durata > 0) {
//                            message += String.format(" in circa %d %s.", durata, unitaMisuraDownload);
//                        }
//                    }
//                    if (nextDownload != null && nextDownload.get() instanceof LocalDateTime next) {
//                        if (!next.equals(ROOT_DATA_TIME)) {
//                            message += String.format(" Prossimo download previsto %s.", DateTimeFormatter.ofPattern("EEE, d MMM yyy 'alle' HH:mm").format(next));
//                        }
//                    }
//                }
//                addSpan(ASpan.text(message).verde().small());
//            }
//            if (lastElaborazione != null && lastElaborazione.get() instanceof LocalDateTime elaborazione) {
//                if (elaborazione.equals(ROOT_DATA_TIME)) {
//                    message = "Elaborazione non ancora effettuata";
//                }
//                else {
//                    message = String.format("Ultimo elaborazione effettuata il %s", dateService.get(elaborazione));
//                    if (durataElaborazione != null && durataElaborazione.get() instanceof Integer durata) {
//                        if (durata > 0) {
//                            message += String.format(" in circa %d %s.", durata, unitaMisuraElaborazione);
//                        }
//                    }
//                }
//
//                addSpan(ASpan.text(message).verde().small());
//            }
//
//            if (lastStatistica != null && lastStatistica.get() instanceof LocalDateTime statistica) {
//                if (statistica.equals(ROOT_DATA_TIME)) {
//                    message = "Statistiche non ancora registrate sul server";
//                }
//                else {
//                    message = String.format("Ultime statistiche registrate il %s", DateTimeFormatter.ofPattern("EEE, d MMM yyy 'alle' HH:mm").format(statistica));
//                }
//                addSpan(ASpan.text(message).verde().small());
//            }
//
//            if (lastUpload != null && lastUpload.get() instanceof LocalDateTime upload) {
//                if (upload.equals(ROOT_DATA_TIME)) {
//                    message = "Upload non ancora effettuato";
//                }
//                else {
//                    message = String.format("Ultimo upload effettuato il %s", dateService.get(upload));
//                    if (durataUpload != null && durataUpload.get() instanceof Integer durata) {
//                        message += String.format(" in circa %d %s.", durata, unitaMisuraUpload);
//                    }
//                    if (nextUpload != null && nextUpload.get() instanceof LocalDateTime next) {
//                        message += String.format(" Prossimo upload previsto %s.", DateTimeFormatter.ofPattern("EEE, d MMM yyy 'alle' HH:mm").format(next));
//                    }
//                }
//                addSpan(ASpan.text(message).verde().small());
//            }
//        }
//    }

}
