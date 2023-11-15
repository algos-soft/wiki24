package it.algos.base24.backend.enumeration;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: lun, 30-ott-2023
 * Time: 07:00
 */
public enum RisultatoReset {
    esistenteNonModificato(RisultatoDelete.errore),
    vuotoMaCostruito(RisultatoDelete.empty),
    esistenteMaCostruito(null),


    vuotoIntegrato(RisultatoDelete.eseguito),
    esistenteIntegrato(RisultatoDelete.empty),
    nonCostruito(null),
    nonIntegrato(null),
    nessuno(RisultatoDelete.nessuno);

    private RisultatoDelete delete;


    RisultatoReset(RisultatoDelete delete) {
        this.delete = delete;
    }

    public static RisultatoReset getDelete(final RisultatoDelete delete) {

        return switch (delete) {
            case nessuno -> RisultatoReset.nessuno;
            case errore -> RisultatoReset.esistenteNonModificato;
            case eseguito -> RisultatoReset.vuotoIntegrato;
            case empty -> RisultatoReset.vuotoMaCostruito;
        };
    }
    public static RisultatoReset getAdd(final RisultatoDelete delete) {

        return switch (delete) {
            case nessuno -> RisultatoReset.nessuno;
            case errore -> RisultatoReset.esistenteNonModificato;
            case eseguito -> RisultatoReset.vuotoIntegrato;
            case empty -> RisultatoReset.esistenteIntegrato;
        };
    }

}
