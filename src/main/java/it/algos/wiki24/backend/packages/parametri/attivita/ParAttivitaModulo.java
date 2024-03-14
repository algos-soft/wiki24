package it.algos.wiki24.backend.packages.parametri.attivita;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.packages.parametri.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 02-Jan-2024
 * Time: 19:01
 */
@Service
public class ParAttivitaModulo extends ParModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ParAttivitaModulo() {
        super(ParAttivitaEntity.class, ParAttivitaList.class, ParAttivitaForm.class);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastElabora = WPref.lastElaboraParAttivita;
        super.durataElabora = WPref.elaboraParAttivitaTime;
        super.unitaMisuraElabora = TypeDurata.minuti;

        super.keyMapName = KEY_MAPPA_ATTIVITA;
    }

    /**
     * Regola le property di una ModelClazz <br>
     * Di default prende tutti i fields della ModelClazz specifica <br>
     */
    @Override
    public List<String> getPropertyNames() {
        return Arrays.asList("pageId", "wikiTitle", "grezzo", "elaborato", "grezzo2", "elaborato2", "grezzo3", "elaborato3", "grezzoVuoto", "elaboratoVuoto", "uguale");
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public ParAttivitaEntity newEntity() {
        return newEntity(0, VUOTA, VUOTA, VUOTA, VUOTA, VUOTA, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param pageId     (obbligatorio)
     * @param wikiTitle  (obbligatorio)
     * @param grezzo     (obbligatorio)
     * @param elaborato  (obbligatorio)
     * @param grezzo2    (obbligatorio)
     * @param elaborato2 (obbligatorio)
     * @param grezzo3    (obbligatorio)
     * @param elaborato3 (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ParAttivitaEntity newEntity(long pageId, String wikiTitle, String grezzo, String elaborato, String grezzo2, String elaborato2, String grezzo3, String elaborato3) {
        ParAttivitaEntity newEntityBean = ParAttivitaEntity.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .grezzo(textService.isValid(grezzo) ? grezzo : null)
                .elaborato(textService.isValid(elaborato) ? elaborato : null)
                .grezzo2(textService.isValid(grezzo2) ? grezzo2 : null)
                .elaborato2(textService.isValid(elaborato2) ? elaborato2 : null)
                .grezzo3(textService.isValid(grezzo3) ? grezzo3 : null)
                .elaborato3(textService.isValid(elaborato3) ? elaborato3 : null)
                .build();

        return (ParAttivitaEntity) fixKey(newEntityBean);
    }

    public AbstractEntity elabora(BioServerEntity bioServerBean) {
        AbstractEntity parametroEntity;
        Map<String, String> mappa;
        long pageId;
        String wikiTitle;
        String grezzo;
        String grezzo2;
        String grezzo3;
        String elaborato;
        String elaborato2;
        String elaborato3;

        mappa = elaboraService.estraeMappa(bioServerBean);
        pageId = bioServerBean.getPageId();
        wikiTitle = bioServerBean.getWikiTitle();
        grezzo = mappa.get(keyMapName);
        grezzo2 = mappa.get(KEY_MAPPA_ATTIVITA_DUE);
        grezzo3 = mappa.get(KEY_MAPPA_ATTIVITA_TRE);
        elaborato = getElaborato(wikiTitle, grezzo);
        elaborato2 = getElaborato(wikiTitle, grezzo);
        elaborato3 = getElaborato(wikiTitle, grezzo);
        parametroEntity = newEntity(pageId, wikiTitle, grezzo, elaborato, grezzo2, elaborato2, grezzo3, elaborato3);

        parametroEntity = fixParametri(parametroEntity, grezzo, elaborato);
        return insertSave(parametroEntity);
    }

    public String getElaborato(String wikiTitle, String grezzo) {
        return elaboraService.fixAttivita(wikiTitle, grezzo);
    }

}// end of CrudModulo class
