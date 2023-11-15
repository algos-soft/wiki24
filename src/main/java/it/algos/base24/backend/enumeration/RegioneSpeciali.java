package it.algos.base24.backend.enumeration;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Fri, 10-Nov-2023
 * Time: 14:56
 */
public enum RegioneSpeciali {
    friuli("IT-36", "Friuli-Venezia Giulia"),
    sardegna("IT-88", "Sardegna"),
    sicilia("IT-82", "Sicilia"),
    trentino("IT-32", "Trentino-Alto Adige,"),
    aosta("IT-23", "Valle d'Aosta"),
    ;

    String sigla;

    String nome;

    /**
     * Costruttore interno della Enumeration <br>
     */
    RegioneSpeciali(final String sigla, final String nome) {
        this.sigla = sigla;
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public String getNome() {
        return nome;
    }
}
