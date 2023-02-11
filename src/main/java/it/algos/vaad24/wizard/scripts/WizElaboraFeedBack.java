package it.algos.vaad24.wizard.scripts;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import static it.algos.vaad24.wizard.scripts.WizCost.*;
import static it.algos.vaad24.wizard.scripts.WizElaboraNewProject.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project sette
 * Created by Algos
 * User: gac
 * Date: lun, 11-apr-2022
 * Time: 17:18
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizElaboraFeedBack extends WizElabora {

    public void esegue() {
        String message;
        boolean esisteSrc;
        boolean esisteDest;
        AResult result;
        String srcWizardProject = System.getProperty("user.dir");
        String currentProject = fileService.estraeClasseFinaleSenzaJava(srcWizardProject).toLowerCase();
        String destBaseVaad24 = textService.levaCoda(srcWizardProject, currentProject);
        if (destBaseVaad24.contains("tutorial")) {
            destBaseVaad24 = "/Users/gac/Documents/IdeaProjects/operativi/";
        }
        destBaseVaad24 += VAADIN_PROJECT + SLASH;

        String srcWizard = String.format("%s%s%s%s%s%s", srcWizardProject, SLASH, SOURCE_PREFIX, VAADIN_MODULE, SLASH, DIR_WIZARD);
        String destWizard = String.format("%s%s%s%s%s", destBaseVaad24, SOURCE_PREFIX, VAADIN_MODULE, SLASH, DIR_WIZARD);

        esisteSrc = fileService.isEsisteDirectory(srcWizard);
        esisteDest = fileService.isEsisteDirectory(destWizard);

        if (esisteSrc && esisteDest) {
            result = fileService.copyDirectory(AECopy.dirFilesModifica, srcWizard, destWizard);
            if (result.isValido()) {
                if (result.getTagCode().equals(AEKeyDir.integrata.name())) {
                    mostraRisultato(result, AECopy.dirFilesModifica, destWizard, "Rollback");
                    message = String.format("La directory 'wizard' su [%s] è stata aggiornata partendo da quella di [%s]", VaadVar.frameworkVaadin24, VaadVar.projectCurrent);
                    logger.info(new WrapLog().message(message).type(AETypeLog.wizard));
                    Avviso.message("Feedback di wizard").success().open();
                }
                if (result.getTagCode().equals(AEKeyDir.esistente.name())) {
                    message = String.format("La directory 'wizard' su [%s] non è stata modificata", VaadVar.frameworkVaadin24);
                    logger.info(new WrapLog().message(message).type(AETypeLog.wizard));
                    Avviso.message("Feedback di wizard").primary().open();
                }
            }
            else {
                message = "La directory 'wizard' ha dei problemi";
                logger.warn(new WrapLog().type(AETypeLog.wizard).exception(new AlgosException(message)));
                Avviso.message("Feedback non riuscito").error().open();
            }
        }
        else {
            if (!esisteSrc) {
                message = String.format("Il path sorgente %s è errato", srcWizard);
                logger.warn(new WrapLog().type(AETypeLog.wizard).message(message));
            }
            if (!esisteDest) {
                message = String.format("Il path destinazione %s è errato", destWizard);
                logger.warn(new WrapLog().type(AETypeLog.wizard).message(message));
            }
            Avviso.message("Feedback non riuscito").primary().open();
        }

        message = String.format("La directory 'wizard' su [%s] è stata aggiornata partendo da quella di [%s]", VaadVar.frameworkVaadin24, VaadVar.projectCurrent);
        logger.info(new WrapLog().message(message).type(AETypeLog.wizard));

    }



}