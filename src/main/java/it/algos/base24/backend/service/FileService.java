package it.algos.base24.backend.service;

import static it.algos.base24.backend.boot.BaseCost.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Wed, 16-Aug-2023
 * Time: 14:10
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Autowired public FileService FileService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class FileService {

    @Autowired
    private TextService textService;

    /**
     * Legge un file
     *
     * @param pathFileToBeRead nome completo del file
     */
    public String legge(String pathFileToBeRead) {
        String testo = VUOTA;
        String aCapo = CAPO;
        String currentLine;

        try (BufferedReader br = new BufferedReader(new FileReader(pathFileToBeRead))) {
            while ((currentLine = br.readLine()) != null) {
                testo += currentLine;
                testo += CAPO;
            }

            testo = textService.levaCoda(testo, aCapo);
        } catch (Exception unErrore) {
//            logService.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb().type(TypeLog.file));
        }

        return testo;
    }

    /**
     * Recupera l'ultima classe da un path <br>
     * <p>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param pathIn in ingresso
     *
     * @return classe finale del path
     */
    public String estraeClasseFinale(final String pathIn) {
        String pathOut = pathIn.trim();

        if (textService.isValid(pathOut)) {
            pathOut = textService.levaCoda(pathOut, SLASH);
            if (pathOut.contains(SLASH)) {
                pathOut = pathOut.substring(pathOut.lastIndexOf(SLASH) + SLASH.length());
            }
            if (pathOut.contains(PUNTO)) {
                pathOut = pathOut.substring(pathOut.lastIndexOf(PUNTO) + PUNTO.length());
            }
        }

        return pathOut.trim();
    }

}// end of Service class