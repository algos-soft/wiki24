package it.algos.base24.backend.service;

import com.opencsv.bean.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Tue, 15-Aug-2023
 * Time: 21:46
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Autowired public ResourceService resourceService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class ResourceService {

    @Autowired
    private TextService textService;

    @Autowired
    private FileService fileService;

    @Autowired
    private WebService webService;

    @Autowired
    public LogService logger;

    /**
     * Legge un file di risorse da {project directory}/config/ <br>
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return testo completo grezzo del file
     */
    public String leggeConfig(final String simpleNameFileToBeRead) {
        return fileService.legge(CONFIG + File.separator + simpleNameFileToBeRead);
    }


    /**
     * Legge un file dal server <br>
     *
     * @param nameFileToBeRead nome del file
     *
     * @return testo grezzo del file
     */
    public String leggeServer(final String nameFileToBeRead) {
        return webService.legge(WebService.URL_BASE_VAADIN24_CONFIG + nameFileToBeRead);
    }


    /**
     * Legge una lista di righe di risorse da {project directory}/config/ <br>
     * La prima riga contiene i titoli
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return lista di righe grezze
     */
    public List<String> leggeListaConfig(final String simpleNameFileToBeRead) {
        String rawText = leggeConfig(simpleNameFileToBeRead);
        return leggeLista(rawText);
    }


    /**
     * Legge una lista di righe di risorse dal server <br>
     * La prima riga contiene i titoli
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return lista di righe grezze
     */
    public List<String> leggeListaServer(final String simpleNameFileToBeRead) {
        String rawText = leggeServer(simpleNameFileToBeRead);
        return leggeLista(rawText);
    }


    /**
     * Legge una lista di righe di risorse <br>
     * La prima riga contiene i titoli
     *
     * @param rawText testo grezzo del file
     *
     * @return lista di righe grezze
     */
    private List<String> leggeLista(final String rawText) {
        List<String> listaRighe = null;
        String[] righe;

        if (textService.isValid(rawText)) {
            listaRighe = new ArrayList<>();
            righe = rawText.split(CAPO);
            if (righe != null && righe.length > 0) {
                for (String riga : righe) {
                    riga = textService.estraeCon(riga, DOPPIE_GRAFFE_INI, DOPPIE_GRAFFE_END);
                    riga = textService.levaCoda(riga, VIRGOLA).trim();
                    if (textService.isValid(riga)) {
                        listaRighe.add(riga);
                    }
                }
            }
        }

        return listaRighe;
    }


    /**
     * Legge una mappa di risorse da {project directory}/config/ oppure dal server URL_BASE_VAADIN23_CONFIG <br>
     * La mappa NON contiene i titoli <br>
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return mappa dei titoli più le righe grezze
     */
    public Map<String, List<String>> leggeMappa(final String simpleNameFileToBeRead) {
        Map<String, List<String>> mappa;

        mappa = leggeMappaServer(simpleNameFileToBeRead);
        if (mappa == null) {
            mappa = leggeMappaConfig(simpleNameFileToBeRead);
        }

        return mappa;
    }

    /**
     * Legge una mappa di risorse da {project directory}/config/ <br>
     * La mappa NON contiene i titoli <br>
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return mappa dei titoli più le righe grezze
     */
    public Map<String, List<String>> leggeMappaConfig(final String simpleNameFileToBeRead) {
        String rawText = leggeConfig(simpleNameFileToBeRead);
        String message;

        if (textService.isEmpty(rawText)) {
            message = String.format("Non ho trovato il file [%s] nella cartella '%s'", simpleNameFileToBeRead, "config");
            logger.info(new WrapLog().message(message).type(TypeLog.startup));
        }

        return elaboraMappa(rawText);
    }


    /**
     * Legge una mappa di risorse dal server URL_BASE_VAADIN23_CONFIG <br>
     * La mappa NON contiene i titoli <br>
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return mappa dei titoli più le righe grezze
     */
    public Map<String, List<String>> leggeMappaServer(final String simpleNameFileToBeRead) {
        String rawText = leggeServer(simpleNameFileToBeRead);
        String message;

        if (textService.isEmpty(rawText)) {
            message = String.format("Non sono riuscito a leggere il file [%s] dal server '%s'", simpleNameFileToBeRead, "algos");
            logger.info(new WrapLog().message(message).type(TypeLog.startup));
        }

        return elaboraMappa(rawText);
    }


    /**
     * Legge una mappa di risorse <br>
     * La mappa NON contiene i titoli <br>
     *
     * @param rawText testo grezzo del file
     *
     * @return mappa delle righe grezze con eventualmente i titoli
     */
    public Map<String, List<String>> elaboraMappa(String rawText) {
        Map<String, List<String>> mappa = null;
        List<String> listaParti;
        String[] righe;
        String[] parti;
        boolean usaId = rawText.startsWith("id,") || rawText.startsWith("ID,");
        int prima = usaId ? 1 : 0;
        int numRiga = 0;
        String value;
        //        String sep = APICETTI + VIRGOLA + APICETTI;
        String sep = VIRGOLA;

        if (textService.isValid(rawText)) {
            righe = rawText.split(CAPO);
            if (righe != null && righe.length > 0) {
                mappa = new LinkedHashMap<>();
                for (String riga : Arrays.stream(righe).toList().subList(1,righe.length)) {
                    listaParti = new ArrayList<>();
                    parti = riga.split(sep);

                    if (parti != null && parti.length > 0) {
                        for (int k = prima; k < parti.length; k++) {
                            value = textService.levaCoda(parti[k], VIRGOLA).trim();
                            if (value.startsWith(APICETTI)) {
                                value = value.substring(1);
                            }
                            if (value.endsWith(APICETTI)) {
                                value = textService.levaCoda(value, APICETTI);
                            }
                            listaParti.add(value);
                        }
                    }
                    if (usaId) {
                        mappa.put(parti[0], listaParti);
                    }
                    else {
                        mappa.put(++numRiga + VUOTA, listaParti);
                    }
                }
            }
        }

        if (mappa != null) {
            mappa.remove("id");
            mappa.remove("0");
        }

        return mappa;
    }

    public List<AbstractEntity> beanCsvBuilder(Path path, Class clazz) throws Exception {
        CsvToBean<AbstractEntity> bean;

        try (Reader reader = Files.newBufferedReader(path)) {
            bean = new CsvToBeanBuilder<AbstractEntity>(reader)
                    .withType(clazz)
                    .build();
        }

        return bean.parse().stream().collect(Collectors.toList());
    }

    public List<List<String>> leggeListaCSV(final String pathFileToBeRead) {
        return leggeListaCSV(pathFileToBeRead, VIRGOLA, CAPO);
    }

    public List<List<String>> leggeListaCSV(final String pathFileToBeRead, final String sepColonna, final String sepRiga) {
        List<List<String>> lista = new ArrayList<>();
        List<String> riga = null;
        String[] righe;
        String[] colonne;

        String testo = leggeFile(pathFileToBeRead);

        if (textService.isValid(testo)) {
            righe = testo.split(sepRiga);
            if (righe != null && righe.length > 0) {
                for (String rigaTxt : righe) {
                    riga = null;
                    colonne = rigaTxt.split(sepColonna);
                    if (colonne != null && colonne.length > 0) {
                        riga = new ArrayList<>();
                        for (String colonna : colonne) {
                            riga.add(colonna);
                        }
                    }
                    if (riga != null) {
                        lista.add(riga);
                    }
                }
            }
        }

        return lista.subList(1, lista.size());
    }


    /**
     * Legge un file
     *
     * @param pathFileToBeRead nome completo del file
     */
    public String leggeFile(String pathFileToBeRead) {
        String testo = VUOTA;
        String aCapo = CAPO;
        String currentLine;

        //-- non va, perché se arriva it/algos/Alfa.java becca anche il .java
        //        nameFileToBeRead=  nameFileToBeRead.replaceAll("\\.","/");

        try (BufferedReader br = new BufferedReader(new FileReader(pathFileToBeRead))) {
            while ((currentLine = br.readLine()) != null) {
                testo += currentLine;
                testo += "\n";
            }

            testo = textService.levaCoda(testo, aCapo);
        } catch (Exception unErrore) {
        }

        return testo;
    }


    /**
     * Classe corrente di Boot in base al nome del modulo/programma
     *
     * @param projectModulo nome del modulo
     * @param projectName   nome del programma
     */
    public Class getClazzBoot(String projectModulo, String projectName) {
        Class clazz = null;
        String clazzName = VUOTA;
        if (textService.isValid(projectName)) {
            clazzName = PATH_ALGOS + PUNTO + projectModulo + PUNTO + PATH_BOOT + PUNTO + textService.primaMaiuscola(projectName) + SUFFIX_BOOT;
        }

        if (textService.isValid(clazzName)) {
            try {
                clazz = Class.forName(clazzName);
            } catch (Exception unErrore) {
                logger.error(new WrapLog().message(unErrore.getMessage()).usaDb());
            }
        }

        return clazz;
    }


}// end of Service class