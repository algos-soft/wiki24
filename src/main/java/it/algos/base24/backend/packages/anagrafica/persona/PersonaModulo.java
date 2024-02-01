package it.algos.base24.backend.packages.anagrafica.persona;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.anagrafica.address.*;
import org.springframework.stereotype.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Thu, 01-Feb-2024
 * Time: 12:28
 */
@Service
public class PersonaModulo extends CrudModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public PersonaModulo() {
        super(PersonaEntity.class, PersonaList.class, PersonaForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public PersonaEntity newEntity() {
        return newEntity(VUOTA, VUOTA, VUOTA, VUOTA, VUOTA, null);
    }

    public PersonaEntity newEntity(String nome, String cognome) {
        return newEntity(VUOTA, nome, cognome, VUOTA, VUOTA, null);
    }

    public PersonaEntity newEntity(String nome, String cognome, String telefono, String mail) {
        return newEntity(VUOTA, nome, cognome, telefono, mail, null);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param titolo   (facoltativo)
     * @param nome     (obbligatorio)
     * @param cognome  (obbligatorio)
     * @param telefono (facoltativo)
     * @param mail     (facoltativo)
     * @param address  (facoltativo)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public PersonaEntity newEntity(String titolo, String nome, String cognome, String telefono, String mail, AddressEntity address) {
        PersonaEntity newEntityBean = PersonaEntity.builder()
                .titolo(textService.isValid(titolo) ? titolo : null)
                .nome(textService.isValid(nome) ? nome : null)
                .cognome(textService.isValid(cognome) ? cognome : null)
                .telefono(textService.isValid(telefono) ? telefono : null)
                .mail(textService.isValid(mail) ? mail : null)
                .address(address)
                .build();

        return newEntityBean;
    }

    @Override
    public PersonaEntity findByKey(final Object keyPropertyValue) {
        return (PersonaEntity) super.findByKey(keyPropertyValue);
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();

        insert(newEntity("Mario", "Rossi"));
        insert(newEntity("Maria Giovanna", "Brambilla","339 254377","giovanna.brambilla.libero.it"));
        insert(newEntity("Odoardo Luigi", "Passerini","","passerini@win.com"));

        return null;
    }

}// end of CrudModulo class
