package it.algos.wiki24.backend.wrapper;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.base24.backend.boot.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.time.format.*;

import java.time.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 19-Dec-2023
 * Time: 07:08
 */
public class WrapPage {

    private TypePage type;

    private long nameSpace;

    private long pageid;

    private String title;

    private LocalDateTime timeStamp;

    private String content;

    public WrapPage() {
    }

    public WrapPage(TypePage type) {
        this.type(type);
    }

    public WrapPage(TypePage type, long nameSpace, long pageid, String title, LocalDateTime timeStamp, String content) {
        this.type(type).nameSpace(nameSpace).pageid(pageid).title(title).timeStamp(timeStamp).content(content);
    }

    public WrapPage(TypePage type, long nameSpace, long pageid, String title, String timeStamp, String content) {
        this.type(type).nameSpace(nameSpace).pageid(pageid).title(title).timeStamp(timeStamp).content(content);
    }

    public static WrapPage nonValida() {
        return new WrapPage(TypePage.indeterminata).content(VUOTA);
    }

    public WrapPage type(final TypePage type) {
        this.type = type;
        return this;
    }

    public WrapPage nameSpace(final long nameSpace) {
        this.nameSpace = nameSpace;
        return this;
    }

    public WrapPage pageid(final long pageid) {
        this.pageid = pageid;
        return this;
    }

    public WrapPage title(final String title) {
        this.title = title;
        return this;
    }

    public WrapPage timeStamp(final LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public WrapPage timeStamp(final String stringTimestamp) {
        this.timeStamp = (stringTimestamp != null && stringTimestamp.length() > 0) ? LocalDateTime.parse(stringTimestamp, DateTimeFormatter.ISO_DATE_TIME) : BaseCost.ROOT_DATA_TIME;
        return this;
    }

    public WrapPage content(final String content) {
        this.content = content;
        return this;
    }


    public TypePage getType() {
        return type;
    }

    public long getNameSpace() {
        return nameSpace;
    }

    public long getPageid() {
        return pageid;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getContent() {
        return content;
    }

    public boolean isValida() {
        return content != null && content.length() > 0;
    }

}
