package it.algos.base24.backend.packages.utility.role;

import it.algos.base24.backend.importexport.*;
import it.algos.base24.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@Scope(value = SCOPE_PROTOTYPE)
public class RoleList extends CrudList {


    public RoleList(final RoleModulo crudModulo) {
        super(crudModulo);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }


    /**
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixHeader() {
        super.infoScopo = String.format(typeList.getInfoScopo(), "Role", "Role"); ;
        super.fixHeader();
    }


    public ExcelExporter creaExcelExporter() {
        ExcelExporter exporter = new ExcelExporter(RoleEntity.class, filtri, Arrays.asList("ordine", "code"), mongoService);

        exporter.setTitle("Tabella dei ruoli");
        return exporter;
    }

}// end of CrudList class
