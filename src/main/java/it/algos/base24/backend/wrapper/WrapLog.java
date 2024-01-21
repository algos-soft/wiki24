package it.algos.base24.backend.wrapper;


import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 21-mar-2022
 * Time: 14:08
 * Wrapper di informazioni (Fluent API) per accedere al logger <br>
 * Può contenere:
 * -type (AETypeLog) merceologico per il log
 * -message (String)
 * -errore (AlgosException) con StackTrace per recuperare classe, metodo e riga di partenza
 * -wrapCompany (WrapLogCompany) per recuperare companySigla, userName e addressIP se l'applicazione è multiCompany
 */
public class WrapLog {

    private TypeLog type;

    private LogLevel livello = LogLevel.info;

    private String message;

    private TypeNotifica notifica = TypeNotifica.nessuna;

    private String companySigla;

    private String userName;

    private String addressIP;
    private String mailObject;

    private AlgosException exception;

    private boolean multiCompany = false;

    private boolean usaDB = false;

    private boolean usaMail = false;

    private boolean usaStackTrace = false;

    public WrapLog() {
    }


    public static WrapLog crea() {
        return new WrapLog();
    }

    public static WrapLog crea(String message) {
        return WrapLog.crea().message(message);
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLog message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLog type(TypeLog type) {
        this.type = type;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLog livello(LogLevel livello) {
        this.livello = livello;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLog info() {
        return this.livello(LogLevel.info);
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLog warn() {
        return this.livello(LogLevel.warn);
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLog debug() {
        return this.livello(LogLevel.debug);
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLog error() {
        return this.livello(LogLevel.error);
    }


    /**
     * Fluent pattern Builder <br>
     */
    public WrapLog primary() {
        this.notifica = TypeNotifica.primary;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLog success() {
        this.notifica = TypeNotifica.success;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLog contrast() {
        this.notifica = TypeNotifica.contrast;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLog errorNotifica() {
        this.notifica = TypeNotifica.error;
        return this;
    }

    /**
     * Fluent pattern Builder <br>
     */
    public WrapLog senzaNotifica() {
        this.notifica = TypeNotifica.nessuna;
        return this;
    }

    public WrapLog exception(Exception exception) {
        this.exception = new AlgosException(exception);
        this.usaStackTrace = true;
        return this;
    }

    //    public WrapLog exception(AlgosException exception) {
    //        this.exception = exception;
    //        this.usaStackTrace = true;
    //        return this;
    //    }

    //    public WrapLog company(String companySigla) {
    //        this.companySigla = companySigla;
    //        this.multiCompany = true;
    //        return this;
    //    }

    public WrapLog user(String userName) {
        this.userName = userName;
        this.multiCompany = true;
        return this;
    }

    //    public WrapLog address(String addressIP) {
    //        this.addressIP = addressIP;
    //        this.multiCompany = true;
    //        return this;
    //    }

    public WrapLog usaDb() {
        this.usaDB = true;
        return this;
    }

    public WrapLog usaMail(String mailObject) {
        this.usaMail = true;
        this.mailObject = mailObject;
        this.livello(LogLevel.mail);
        return this;
    }

    public TypeLog getType() {
        return type;
    }

    public String get() {
        return (message != null && message.length() > 0) ? message : VUOTA;
    }

    //    /**
    //     * Per il database registra entrambi i messaggi, se esistono <br>
    //     */
    //    public String getMessageDB() {
    //        StringBuffer buffer = new StringBuffer();
    //        String messageErrore = exception != null ? exception.getMessage() : VUOTA;
    //        String messageSpecifico = (message != null && message.length() > 0) ? message : VUOTA;
    //
    //        buffer.append(messageErrore);
    //        if (messageErrore != null && messageErrore.length() > 0 && messageSpecifico.length() > 0) {
    //            buffer.append(SEP);
    //        }
    //        buffer.append(messageSpecifico);
    //
    //        return buffer.toString();
    //    }

    public AlgosException getException() {
        return exception;
    }

    public LogLevel getLivello() {
        return livello;
    }

    public String getCompanySigla() {
        return companySigla;
    }

    public String getUserName() {
        return userName;
    }

    public String getAddressIP() {
        return addressIP;
    }

    public boolean isMultiCompany() {
        return multiCompany;
    }

    public boolean isUsaDB() {
        return usaDB;
    }

    public boolean isUsaMail() {
        return usaMail;
    }

    public boolean isUsaStackTrace() {
        return usaStackTrace;
    }

    public void setUsaStackTrace(boolean usaStackTrace) {
        this.usaStackTrace = usaStackTrace;
    }

    public String toString() {
        return get();
    }

    public String getMessage() {
        return get();
    }

    public TypeNotifica getNotifica() {
        return notifica;
    }

    public String getTag() {
        return type != null ? type.getTag() : TypeLog.system.getTag();
    }

    public String getMailObject() {
        return mailObject;
    }

}