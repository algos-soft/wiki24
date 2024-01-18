package it.algos.base24.backend.service;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.wrapper.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: lun, 19-lug-2021
 * Time: 11:58
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L'istanza viene utilizzata con: <br>
 * 1) @Autowired public JSonService jSonService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class JSonService {

    @Inject
    private TextService textService;

    @Inject
    LogService logger;

    public Map<String, Object> getMappa(AbstractEntity entityBean) {
        Map<String, Object> mappa = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());

        try {
            mappa = objectMapper.convertValue(entityBean, new TypeReference<Map<String, Object>>() {});
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }

        return mappa;
    }

    //    public CrudModel getPojo(Map<String, Object> mappa) {
    //        CrudModel entityBean;
    //        ObjectMapper mapper = new ObjectMapper();
    //
    //
    //        MeseModel entityBean2 = mapper.convertValue(mappa, new TypeReference<MeseModel>() {});
    //
    //        return entityBean;
    //    }


    public Map<String, Object> getMappaModifiche(Map<String, Object> mappaOld, Map<String, Object> mappaNew) {
        Map<String, Object> mappaModifiche = new HashMap<>();
        String message;
        Object oldValue;
        Object newValue;

        if (mappaOld.size() == mappaNew.size()) {
            for (String key : mappaOld.keySet()) {
                oldValue = mappaOld.get(key) != null ? mappaOld.get(key) : NULLO;
                newValue = mappaNew.get(key) != null ? mappaNew.get(key) : NULLO;
                if (!oldValue.equals(newValue)) {
                    message = String.format("%s[%s%s%s]", key, oldValue, FORWARD, newValue);
                    mappaModifiche.put(key, message);
                }
            }

            return mappaModifiche;
        }

        return mappaModifiche;
    }

    public Map<String, Object> getMappaModifiche(AbstractEntity entityBeanOld, AbstractEntity entityBeanNew) {
        Map<String, Object> mappaOld = getMappa(entityBeanOld);
        Map<String, Object> mappaNew = getMappa(entityBeanNew);
        return getMappaModifiche(mappaOld, mappaNew);
    }

    public String getModifiche(AbstractEntity entityBeanOld, AbstractEntity entityBeanNew) {
        String modifiche = VUOTA;
        StringBuffer buffer = new StringBuffer();

        Map<String, Object> mappaModifiche = getMappaModifiche(entityBeanOld, entityBeanNew);

        if (mappaModifiche != null && mappaModifiche.size() > 0) {
            buffer.append(GRAFFA_INI);
            for (String key : mappaModifiche.keySet()) {
                buffer.append(mappaModifiche.get(key));
                buffer.append(VIRGOLA_SPAZIO);
            }
            modifiche = buffer.toString().trim();
            modifiche = textService.levaCoda(modifiche, VIRGOLA);
            modifiche += GRAFFA_END;
        }

        return modifiche;
    }


    public String getProperties(AbstractEntity entityBean) {
        String modifiche = VUOTA;
        StringBuffer buffer = new StringBuffer();

        Map<String, Object> mappa = getMappa(entityBean);

        if (mappa != null && mappa.size() > 0) {
            buffer.append(GRAFFA_INI);
            for (String key : mappa.keySet()) {
                buffer.append(mappa.get(key));
                buffer.append(VIRGOLA_SPAZIO);
            }
            modifiche = buffer.toString().trim();
            modifiche = textService.levaCoda(modifiche, VIRGOLA);
            modifiche += GRAFFA_END;
        }

        return modifiche;
    }

}