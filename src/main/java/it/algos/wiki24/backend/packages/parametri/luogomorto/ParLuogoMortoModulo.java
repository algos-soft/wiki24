package it.algos.wiki24.backend.packages.parametri.luogomorto;

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
 * Date: Mon, 01-Jan-2024
 * Time: 18:07
 */
@Service
public class ParLuogoMortoModulo extends ParModulo {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la formClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ParLuogoMortoModulo() {
        super(ParLuogoMortoEntity.class, ParLuogoMortoList.class, ParLuogoMortoForm.class);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.lastElabora = WPref.lastElaboraParLuogoMorto;
        super.durataElabora = WPref.elaboraParLuogoMortoTime;
        super.unitaMisuraElabora = TypeDurata.minuti;

        super.keyMapName = KEY_MAPPA_LUOGO_MORTE;
    }

    /**
     * Regola le property di una ModelClazz <br>
     * Di default prende tutti i fields della ModelClazz specifica <br>
     */
    @Override
    public List<String> getPropertyNames() {
        return Arrays.asList("pageId", "wikiTitle", "grezzo", "linkLuogo", "elaborato", "grezzoVuoto", "elaboratoVuoto", "uguale");
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public ParLuogoMortoEntity newEntity() {
        return newEntity(0, VUOTA, VUOTA, VUOTA, VUOTA);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param pageId    (obbligatorio)
     * @param wikiTitle (obbligatorio)
     * @param grezzo    (obbligatorio)
     * @param linkLuogo (obbligatorio)
     * @param elaborato (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ParLuogoMortoEntity newEntity(long pageId, String wikiTitle, String grezzo, String linkLuogo, String elaborato) {
        ParLuogoMortoEntity newEntityBean = ParLuogoMortoEntity.builder()
                .pageId(pageId)
                .wikiTitle(textService.isValid(wikiTitle) ? wikiTitle : null)
                .grezzo(textService.isValid(grezzo) ? grezzo : null)
                .linkLuogo(textService.isValid(linkLuogo) ? linkLuogo : null)
                .elaborato(textService.isValid(elaborato) ? elaborato : null)
                .build();

        return (ParLuogoMortoEntity) fixKey(newEntityBean);
    }

    public AbstractEntity elabora(BioServerEntity bioServerBean) {
        AbstractEntity parametroEntity;
        Map<String, String> mappa;
        long pageId;
        String wikiTitle;
        String grezzo;
        String linkLuogo;
        String elaborato;

        mappa = elaboraService.estraeMappa(bioServerBean);
        pageId = bioServerBean.getPageId();
        wikiTitle = bioServerBean.getWikiTitle();
        grezzo = mappa.get(keyMapName);
        linkLuogo = mappa.get(KEY_MAPPA_LUOGO_MORTE_LINK);
        elaborato = getElaborato(wikiTitle, grezzo, linkLuogo);
        parametroEntity = newEntity(pageId, wikiTitle, grezzo, linkLuogo, elaborato);

        parametroEntity = fixParametri(parametroEntity, grezzo, elaborato);
        return insertSave(parametroEntity);
    }

    public String getElaborato(String wikiTitle, String grezzo, String linkLuogo) {
        return elaboraService.fixLuogo(wikiTitle, grezzo, linkLuogo);
    }

}// end of CrudModulo class
