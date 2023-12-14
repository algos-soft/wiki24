package it.algos.base24.backend.enumeration;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.interfaces.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Fri, 01-Dec-2023
 * Time: 06:44
 */
public enum TypeList implements Type {
    standard(VUOTA, TEXT_STANDARD, TEXT_DELETE, false, true, false, false, false, true, true, true, false, true, true, true),
    hardEnum(TEXT_TAVOLA + SPAZIO + TEXT_ENUM, TEXT_HARD, TEXT_RESET_DELETE, true, false, true, false, false, false, false, false, true, false, true, true),
    hardCsv(TEXT_TAVOLA + SPAZIO + TEXT_CSV, TEXT_HARD, TEXT_RESET_DELETE, true, false, true, false, false, false, false, false, true, false, true, true),
    softCsv(TEXT_TAVOLA + SPAZIO + TEXT_CSV, TEXT_NEWS, TEXT_RESET_ADD, true, true, false, true, false, false, true, true, false, true, true, true),
    hardCode(TEXT_TAVOLA + SPAZIO + TEXT_CODE, TEXT_HARD, TEXT_RESET_DELETE, true, false, true, false, false, false, false, false, true, false, true, true),
    hardWiki(TEXT_WIKI, TEXT_HARD, TEXT_RESET_DELETE, true, false, false, false, false, true, false, false, true, false, true, true),
    softWiki(TEXT_WIKI, TEXT_NEWS, TEXT_RESET_ADD, true, true, false, false, false, true, true, true, false, true, true, true),
    pref(TEXT_BASE + SPAZIO + TEXT_ENUM_PREF, TEXT_PREF, TEXT_RESET_PREF, true, false, false, false, true, false, false, true, false, false, false, true),
    ;

    private String infoScopo;

    private String infoCreazione;

    private String infoReset;

    private boolean usaStartupReset;

    private boolean usaBottoneDeleteAll;

    private boolean usaBottoneResetDelete;

    private boolean usaBottoneResetAdd;

    private boolean usaBottoneResetPref;

    private boolean usaBottoneDownload;

    private boolean usaBottoneNew;

    private boolean usaBottoneEdit;

    private boolean usaBottoneShows;

    private boolean usaBottoneDeleteEntity;

    private boolean usaBottoneExport;

    private boolean usaBottoneSearch;


    TypeList(String infoScopo, String infoCreazione, String infoReset, boolean usaStartupReset, boolean usaBottoneDeleteAll, boolean usaBottoneResetDelete, boolean usaBottoneResetAdd, boolean usaBottoneResetPref, boolean usaBottoneDownload, boolean usaBottoneNew, boolean usaBottoneEdit, boolean usaBottoneShows, boolean usaBottoneDeleteEntity, boolean usaBottoneExport, boolean usaBottoneSearch) {
        this.infoScopo = infoScopo;
        this.infoCreazione = infoCreazione;
        this.infoReset = infoReset;
        this.usaStartupReset = usaStartupReset;
        this.usaBottoneDeleteAll = usaBottoneDeleteAll;
        this.usaBottoneResetDelete = usaBottoneResetDelete;
        this.usaBottoneResetAdd = usaBottoneResetAdd;
        this.usaBottoneDownload = usaBottoneDownload;
        this.usaBottoneResetPref = usaBottoneResetPref;
        this.usaBottoneNew = usaBottoneNew;
        this.usaBottoneEdit = usaBottoneEdit;
        this.usaBottoneShows = usaBottoneShows;
        this.usaBottoneDeleteEntity = usaBottoneDeleteEntity;
        this.usaBottoneExport = usaBottoneExport;
        this.usaBottoneSearch = usaBottoneSearch;
    }


    public static List<TypeList> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<TypeList> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<String> getAllTags() {
        return getAllEnums()
                .stream()
                .map(type->type.getTag())
                .collect(Collectors.toList());
    }

    @Override
    public String getTag() {
        return infoScopo;
    }


    public String getInfoScopo() {
        return infoScopo;
    }

    public String getInfoCreazione() {
        return infoCreazione;
    }

    public String getInfoReset() {
        return infoReset;
    }

    public boolean isUsaStartupReset() {
        return usaStartupReset;
    }

    public boolean isUsaBottoneDeleteAll() {
        return usaBottoneDeleteAll;
    }

    public boolean isUsaBottoneResetDelete() {
        return usaBottoneResetDelete;
    }

    public boolean isUsaBottoneResetAdd() {
        return usaBottoneResetAdd;
    }

    public boolean isUsaBottoneResetPref() {
        return usaBottoneResetPref;
    }

    public boolean isUsaBottoneDownload() {
        return usaBottoneDownload;
    }

    public boolean isUsaBottoneNew() {
        return usaBottoneNew;
    }

    public boolean isUsaBottoneEdit() {
        return usaBottoneEdit;
    }

    public boolean isUsaBottoneShows() {
        return usaBottoneShows;
    }

    public boolean isUsaBottoneDeleteEntity() {
        return usaBottoneDeleteEntity;
    }

    public boolean isUsaBottoneExport() {
        return usaBottoneExport;
    }

    public boolean isUsaBottoneSearch() {
        return usaBottoneSearch;
    }
}
