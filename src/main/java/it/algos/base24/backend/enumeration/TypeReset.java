package it.algos.base24.backend.enumeration;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: mar, 31-ott-2023
 * Time: 08:35
 */
public enum TypeReset {
    nessuno(false, false, false, false, false, false, false, false, true, false),
    standard(true, false, false, true, true, false, true, true, true, false),
    soloShow(true, true, false, false, false, true, false, true, true, false),
    resetAdd(true, false, true, true, true, false, true, true, true, true),
    preferenze(false, false, false, false, false, false, false, false, true, false),
    additivo(false, false, false, false, false, false, false, false, true, false),
    ;

    private boolean usaBottoneDeleteAll;


    private boolean usaBottoneResetDelete;

    private boolean usaBottoneResetAdd;

    private boolean usaBottoneNew;

    private boolean usaBottoneEdit;

    private boolean usaBottoneShows;

    private boolean usaBottoneDeleteEntity;

    private boolean usaBottoneExport;

    private boolean usaBottoneSearch;

    private boolean usaStartupReset;

    TypeReset(boolean usaBottoneDeleteAll, boolean usaBottoneResetDelete, boolean usaBottoneResetAdd, boolean usaBottoneNew, boolean usaBottoneEdit, boolean usaBottoneShows, boolean usaBottoneDeleteEntity, boolean usaBottoneExport, boolean usaBottoneSearch, boolean usaStartupReset) {
        this.usaBottoneDeleteAll = usaBottoneDeleteAll;
        this.usaBottoneResetDelete = usaBottoneResetDelete;
        this.usaBottoneResetAdd = usaBottoneResetAdd;
        this.usaBottoneNew = usaBottoneNew;
        this.usaBottoneEdit = usaBottoneEdit;
        this.usaBottoneShows = usaBottoneShows;
        this.usaBottoneDeleteEntity = usaBottoneDeleteEntity;
        this.usaBottoneExport = usaBottoneExport;
        this.usaBottoneSearch = usaBottoneSearch;
        this.usaStartupReset = usaStartupReset;
    }

    public boolean isUsaBottoneDeleteAll() {
        return usaBottoneDeleteAll;
    }

    public boolean isUsaBottoneResetAdd() {
        return usaBottoneResetAdd;
    }

    public boolean isUsaBottoneResetDelete() {
        return usaBottoneResetDelete;
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

    public boolean isUsaStartupReset() {
        return usaStartupReset;
    }
}
