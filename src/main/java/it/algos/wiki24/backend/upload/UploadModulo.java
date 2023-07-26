package it.algos.wiki24.backend.upload;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 10-Apr-2023
 * Time: 09:04
 * Upload sempre su pagina Test e poi copiare sul Modulo protetto solo per gli Admin
 */
public abstract class UploadModulo extends Upload {

    /**
     * Costruttore base senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAnni.class).nascita/morte().upload(nomeAnno) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     */
    public UploadModulo() {
        super.summary = "Fix in ordine alfabetico";
        super.uploadTest = true;
    }// end of constructor

    public String leggeTestoPagina() {
        return queryService.legge(wikiTitleModulo);
    }

    public String leggeTestoModulo() {
        String testoPagina = leggeTestoPagina();
        return wikiUtility.estraeTestoModulo(testoPagina);
    }

    public Map<String, String> leggeMappa() {
        String testoModulo = leggeTestoModulo();
        return wikiApiService.getMappaModulo(testoModulo);
    }

    public Map<String, String> getMappaOrdinataKey() {
        Map<String, String> mappa = leggeMappa();
        return arrayService.sort(mappa);
    }

    public Map<String, String> getMappaOrdinataValue() {
        Map<String, String> mappa = leggeMappa();
        return arrayService.sortValue(mappa);
    }

    public String getWikiTitleUpload() {
        return wikiTitleUpload;
    }

    public String getWikiTitleModulo() {
        return wikiTitleModulo;
    }

    public Map<String, String> leggeMappaMongo() {
        return wikiBackend.findMappa();
    }

    public Upload esegue() {
         this.uploadOrdinatoSenzaModifiche();
        return null;
    }

    /**
     * Esegue la scrittura della pagina di test ordinata per singolari <br>
     */
    public WResult uploadOrdinatoSenzaModifiche() {
        String testoPagina = leggeTestoPagina();
        String testoModuloOld = leggeTestoModulo();
        String testoModuloNew = fixTestoModulo(getMappaOrdinataKey());
        String textDaRegistrare = textService.sostituisce(testoPagina, testoModuloOld, testoModuloNew);

        return wikiApiService.scrive(wikiTitleUpload, textDaRegistrare, summary).typeResult(AETypeResult.uploadValido);
    }

    /**
     * Esegue la scrittura della pagina di test ordinata per plurali <br>
     */
    public WResult uploadOrdinatoValoreSenzaModifiche() {
        String testoPagina = leggeTestoPagina();
        String testoModuloOld = leggeTestoModulo();
        String testoModuloNew = fixTestoModulo(getMappaOrdinataValue());
        String textDaRegistrare = textService.sostituisce(testoPagina, testoModuloOld, testoModuloNew);

        return wikiApiService.scrive(wikiTitleUpload, textDaRegistrare, summary).typeResult(AETypeResult.uploadValido);

    }

    public String fixTestoModulo(Map<String, String> mappa) {
        StringBuffer buffer = new StringBuffer();
        String key;
        String value;

        if (mappa != null && mappa.size() > 0) {
            for (Map.Entry<String, String> entry : mappa.entrySet()) {
                key = textService.setApicetti(entry.getKey());
                value = entry.getValue() != null ? textService.setApicetti(entry.getValue()) : APICETTI+APICETTI;

                buffer.append(textService.setQuadre(key));
                buffer.append(UGUALE_SPAZIATO);
                buffer.append(value);
                buffer.append(VIRGOLA);
                buffer.append(CAPO);
            }
        }

        return fixVirgolaFinale(buffer.toString());
    }

    public String fixVirgolaFinale(String testoModulo) {
        testoModulo = textService.levaCoda(testoModulo, CAPO);
        testoModulo = textService.levaCoda(testoModulo, VIRGOLA);
        testoModulo += CAPO;

        return testoModulo;
    }


}
