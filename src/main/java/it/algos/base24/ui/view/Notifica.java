package it.algos.base24.ui.view;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.server.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.base24.backend.enumeration.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 30-mar-2022
 * Time: 08:24
 */
@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class Notifica {


    public static final Notification.Position POSIZIONE = Notification.Position.BOTTOM_START;

    private String message;

    private Notification.Position posizione = POSIZIONE;

    private NotificationVariant themeVariant = null;

    private int durata;


    public static void show(String messaggioImmediatamenteVisibile) {
        Notification.show(messaggioImmediatamenteVisibile, Pref.durataNotification.getInt(), POSIZIONE);
    }


    /**
     * Need open() to show or use show(String messaggioImmediatamenteVisibile) instead <br>
     */
    public static Notifica message(String message) {
        Notifica avviso = new Notifica();
        avviso.message = message;
        avviso.durata = Pref.durataNotification.getInt();
        return avviso;
    }

    /**
     * Fluent pattern Builder <br>
     * Need open() to show <br>
     */
    public Notifica primary() {
        this.themeVariant = TypeNotifica.primary.get();
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     * Need open() to show <br>
     */
    public Notifica success() {
        this.themeVariant = TypeNotifica.success.get();
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     * Need open() to show <br>
     */
    public Notifica contrast() {
        this.themeVariant = TypeNotifica.contrast.get();
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     * Need open() to show <br>
     */
    public Notifica error() {
        this.themeVariant = TypeNotifica.error.get();
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     * Need open() to show <br>
     */
    public Notifica durata(int secondi) {
        this.durata = secondi;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     * Need open() to show <br>
     */
    public Notifica middle() {
        this.posizione = Notification.Position.MIDDLE;
        return this;
    }

    public void open() {
        if (VaadinSession.getCurrent() != null) {
            Notification notification = null;
            message = String.format("%s%s%s", "<div>", message, "</div>");

            notification = new Notification(new Html(message));
            notification.setPosition(posizione);
            notification.setDuration(durata());
            if (themeVariant != null) {
                notification.addThemeVariants(themeVariant);
            }
            notification.open();
        }
    }


    public int durata() {
        if (durata == 0) {
            durata = Pref.durataNotification.getInt();
        }

        return durata * 1000;
    }

}