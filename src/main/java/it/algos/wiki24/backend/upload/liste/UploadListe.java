package it.algos.wiki24.backend.upload.liste;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaad24.backend.logic.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.backend.wrapper.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 23-Jul-2023
 * Time: 19:53
 */
public  abstract class UploadListe extends Upload implements AlgosBuilderPattern {


    public UploadListe(String nomeLista) {
        super(nomeLista);
    }// end of constructor

//    public WResult esegue() {
//        if (false) {
////            return super.esegue();
//            return null;
//
//        }
//        else {
////            return super.esegue();
//            return null;
//        }
//    }

}
