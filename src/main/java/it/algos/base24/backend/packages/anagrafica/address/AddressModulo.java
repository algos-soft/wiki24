package it.algos.base24.backend.packages.anagrafica.address;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.anagrafica.via.*;
import org.springframework.stereotype.*;

import javax.inject.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Thu, 01-Feb-2024
 * Time: 11:53
 */
@Service
public class AddressModulo extends CrudModulo {

    @Inject
    ViaModulo viaModulo;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public AddressModulo() {
        super(AddressEntity.class, AddressList.class, AddressForm.class);
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
    public AddressEntity newEntity() {
        return newEntity(null, VUOTA, VUOTA, VUOTA);
    }

    public AddressEntity newEntity(String indirizzo, String localita) {
        return newEntity(null, indirizzo, localita, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param typeVia   (obbligatorio)
     * @param indirizzo (obbligatorio)
     * @param localita  (obbligatorio)
     * @param cap       (facoltativo)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public AddressEntity newEntity(ViaEntity typeVia, String indirizzo, String localita, String cap) {
        AddressEntity newEntityBean = AddressEntity.builder()
                .typeVia(typeVia)
                .indirizzo(textService.isValid(indirizzo) ? indirizzo : null)
                .localita(textService.isValid(localita) ? localita : null)
                .cap(textService.isValid(cap) ? cap : null)
                .build();

        return newEntityBean;
    }

    @Override
    public AddressEntity findByKey(final Object keyPropertyValue) {
        return (AddressEntity) super.findByKey(keyPropertyValue);
    }

    @Override
    public RisultatoReset resetDelete() {
        RisultatoReset typeReset = super.resetDelete();
        ViaEntity viaBean0 = viaModulo.findByKey("via");
        ViaEntity viaBean1 = viaModulo.findByKey("viale");
        ViaEntity viaBean2 = viaModulo.findByKey("piazza");

        insert(newEntity("Romagna, 17", "Milano"));
        insert(newEntity(viaBean1, "Romagna, 17", "Milano", "20121"));
        insert(newEntity(null, "Trinità de' Monti, 2", "Roma", "60102"));
        insert(newEntity(viaBean2, "Trinità de' Monti, 2", "Roma", "60102"));
        insert(newEntity(viaBean0, "Roma, 4", "San Valentino in Abruzzo Citeriore", "37234"));

        return null;
    }

}// end of CrudModulo class
