package it.algos.base24.backend.service;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.wrapper.*;
import jakarta.annotation.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.core.env.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Service;

import javax.inject.*;
import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Wed, 13-Dec-2023
 * Time: 14:30
 */
@Service
public class MailService {

    @Inject
    public Environment environment;

    //    @Inject
    private JavaMailSenderImpl mailSender;

    @Inject
    TextService textService;

    @Inject
    LogService logger;

    String mailHost;

    String mailPort;

    String mailUser;

    String mailPass;

    /**
     * Spring constructor
     */
    public MailService() {
        this.mailSender = new JavaMailSenderImpl();
    }// end of constructor

    @PostConstruct
    public void postConstruct() {
        Properties properties = new Properties();
        String property = VUOTA;

        try {
            property = "spring.mail.host";
            mailHost = Objects.requireNonNull(environment.getProperty(property));
            properties.put("mail.smtp.host", mailHost);
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        try {
            property = "spring.mail.port";
            mailPort = Objects.requireNonNull(environment.getProperty(property));
            properties.put("mail.smtp.port", mailPort);
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        try {
            property = "spring.mail.username";
            mailUser = Objects.requireNonNull(environment.getProperty(property));
            mailSender.setUsername(mailUser);
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        try {
            property = "spring.mail.password";
            mailPass = Objects.requireNonNull(environment.getProperty(property));
            mailSender.setPassword(mailPass);
        } catch (Exception unErrore) {
            logError(unErrore, property);
        }

        mailSender.setJavaMailProperties(properties);
    }

    /**
     * Spedizione ridotta
     *
     * @param title soggetto
     * @param body  testo della mail
     */
    public boolean send( String title, String body) {
        return send(mailUser, MAIL_TO, title, body);
    }

    /**
     * Spedizione semplice
     *
     * @param to    destinatario
     * @param title soggetto
     * @param body  testo della mail
     */
    public boolean send(String to, String title, String body) {
        return send(mailUser, to, title, body);
    }

    /**
     * Spedizione completa con mittente e destinatario
     *
     * @param from  mittente
     * @param to    destinatario
     * @param title soggetto
     * @param body  testo della mail
     */
    public boolean send(String from, String to, String title, String body) {
        boolean status;
        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        try {
            if (textService.isValid(from)) {
                mimeMessageHelper.setFrom(from);
            }
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(body);
            mimeMessageHelper.setTo(to);
            this.mailSender.send(message);
            status = true;
        } catch (MessagingException messageException) {
            // You could also 'throw' this exception. I am not a fan of checked exceptions.
            // If you want to go that route, then just update this method and the interface.
            throw new RuntimeException(messageException);
            //            throw AlgosException.stack(messageException, "Pippoz", getClass(), "send");
        }
        return status;
    }

    private void logError(Exception unErrore, String property) {
        String message = String.format("Non ho trovato la property %s nelle risorse", property);
        logger.warn(new WrapLog().exception(unErrore).message(message).usaDb());
    }

}
