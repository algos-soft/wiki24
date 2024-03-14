package it.algos.wiki24.backend.login;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: gio, 08-lug-2021
 * Time: 18:31
 */
@Service
public class BotLogin {

    private boolean valido;

    private boolean bot = false;


    /**
     * Property ricevuta da QueryLogin e indispensabile per ulteriori query (POST) come Bot <br>
     */
    private long lguserid;

    /**
     * Property ricevuta da QueryLogin e indispensabile per ulteriori query (POST) come Bot <br>
     */
    private String lgusername;

    private TypeUser userType = TypeUser.anon;

    private String urlResponse;

    /**
     * Property ricevuta da QueryLogin e indispensabile per ulteriori query (POST) come Bot <br>
     */
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
        return urlResponse;
    }

    public void setUrlResponse(String urlResponse) {
        this.urlResponse = urlResponse;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setQuery(boolean bot, long lguserid, String lgusername, TypeUser type, String urlResponse, Map<String, String> cookies) {
        this.valido = true;
        this.bot = bot;
        this.lguserid = lguserid;
        this.lgusername = lgusername;
        this.userType = type;
        this.urlResponse = urlResponse;
        this.cookies = cookies;
    }

    public void reset() {
        this.valido = false;
        this.bot = false;
        this.lguserid = 0;
        this.lgusername = VUOTA;
        this.userType = TypeUser.anon;
        this.urlResponse = VUOTA;
        this.cookies = null;
    }

}
