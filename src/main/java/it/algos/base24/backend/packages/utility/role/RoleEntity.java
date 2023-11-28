package it.algos.base24.backend.packages.utility.role;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import lombok.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "role", usaStartupReset = true, typeReset = TypeReset.soloList)
public class RoleEntity extends AbstractEntity {

    @AField(type = TypeField.ordine, headerText = "#")
    public int ordine;


    @AField(type = TypeField.text, widthRem = 12)
    public String code;


    @Override
    public String toString() {
        return code;
    }


}// end of CrudModel class
