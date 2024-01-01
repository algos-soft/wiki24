package it.algos.wiki24.backend.packages.parametri.sesso;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.packages.parametri.*;
import it.algos.wiki24.backend.service.*;
import org.springframework.stereotype.*;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 27-Dec-2023
 * Time: 07:43
 */
@Service
public class ParSessoModulo extends ParModulo {

    @Inject
    ElaboraService elaboraService;

    @Inject
    BioServerModulo bioServerModulo;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ParSessoModulo() {
        super(ParSessoEntity.class, ParSessoList.class, ParSessoForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastElabora = WPref.lastElaboraParSesso;
        super.durataElabora = WPref.elaboraParSessoTime;
        super.unitaMisuraElabora = TypeDurata.minuti;

        super.keyMapName = KEY_MAPPA_SESSO;
    }


    @Override
    public ParSessoEntity newEntity() {
        return newEntity(0, VUOTA, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param pageId    (obbligatorio)
     * @param wikiTitle (obbligatorio)
     * @param grezzo    (obbligatorio)
     * @param elaborato (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ParSessoEntity newEntity(long pageId, String wikiTitle, String grezzo, String elaborato) {
        ParSessoEntity newEntityBean = ParSessoEntity.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .grezzo(textService.isValid(grezzo) ? grezzo : null)
                .elaborato(textService.isValid(elaborato) ? elaborato : null)
                .build();

        return (ParSessoEntity) fixKey(newEntityBean);
    }

    public String getElaborato(String grezzo) {
        return elaboraService.fixSesso(grezzo);
    }

//    public AbstractEntity fixParametri(AbstractEntity parametroEntity, String grezzo, String elaborato) {
//        ((ParSessoEntity) parametroEntity).grezzoVuoto = textService.isEmpty(grezzo);
//        ((ParSessoEntity) parametroEntity).elaboratoVuoto = textService.isEmpty(elaborato);
//        ((ParSessoEntity) parametroEntity).uguale = elaborato != null ? elaborato.equals(grezzo) : grezzo != null ? true : false;
//
//        return parametroEntity;
//    }


}// end of CrudModulo class
