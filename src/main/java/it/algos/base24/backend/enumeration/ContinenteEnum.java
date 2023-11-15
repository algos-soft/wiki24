package it.algos.base24.backend.enumeration;

import it.algos.base24.backend.interfaces.*;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 29-set-2020
 * Time: 14:34
 */
public enum ContinenteEnum implements Type {

    europa("Europa", "Europa",true),
    asia("Asia", "Asia",true),
    africa("Africa", "Africa",true),
    nordcentroamerica("NordCentroAmerica", "America settentrionale e centrale",true),
    sudamerica("SudAmerica", "America meridionale",true),
    oceania("Oceania", "Oceania",true),
    antartide("Antartide", "Antartide",false),
    ;

    private String tag;
    private String template;

    boolean abitato;


    ContinenteEnum(String tag, String template,boolean abitato) {
        this.tag = tag;
        this.template = template;
        this.abitato = abitato;
    }

    public static List<ContinenteEnum> getAllEnums() {
        return Arrays.stream(values()).toList();
    }


    public boolean isAbitato() {
        return abitato;
    }


    @Override
    public List<ContinenteEnum> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<String> getAllTags() {
        return null;
    }

    @Override
    public String getTag() {
        return tag;
    }

    public String getTemplate() {
        return template;
    }

    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return getTag();
    }
}
