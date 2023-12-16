package it.algos.wiki24.backend.login;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: gio, 08-lug-2021
 * Time: 18:31
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BotLogin {

    private boolean bot = false;

    private WResult result;

    /**
     * Property indispensabile ricevuta da QueryLogin <br>
     */
    private long lguserid;

    /**
     * Property indispensabile ricevuta da QueryLogin <br>
     */
    private String lgusername;

    private TypeUser userType = TypeUser.anonymous;

    private boolean valido;

    private Map<String, String> cookies;

    public BotLogin() {
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public boolean nonCollegato() {
        return !isBot();
    }

    public TypeUser getUserType() {
        return userType;
    }

    public Map<String, String> getMappa() {
        return result != null ? result.getMappa() : null;
    }

    public WResult getResult() {
        return result;
    }

    public void setResult(WResult result) {
        this.result = result;
    }

    public long getUserid() {
        return lguserid;
    }

    public void setLguserid(long lguserid) {
        this.lguserid = lguserid;
    }

    public String getUsername() {
        return lgusername;
    }

    public void setLgusername(String lgusername) {
        this.lgusername = lgusername;
    }

    public void setUserType(TypeUser userType) {
        this.userType = userType;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public String getUrlResponse() {
        return result != null ? result.getResponse() : TAG_VUOTA;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void reset() {
        this.valido = false;
        this.bot = false;
        this.result = null;
        this.lguserid = 0;
        this.lgusername = VUOTA;
        this.userType = TypeUser.anonymous;
    }

}
