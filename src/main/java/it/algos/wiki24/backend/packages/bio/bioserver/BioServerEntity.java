package it.algos.wiki24.backend.packages.bio.bioserver;

import it.algos.base24.backend.annotation.*;
import it.algos.base24.backend.entity.*;
import it.algos.base24.backend.enumeration.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Indexed;

import java.time.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "bioserver", keyPropertyName = "pageId", typeList = TypeList.softWiki)
public class BioServerEntity extends AbstractEntity {


    @AField(type = TypeField.lungo, widthRem = 7)
    public long pageId;


    //    @NotBlank()
    @AField(type = TypeField.text, widthRem = 16)
    public String wikiTitle;

    //    @Lob
    @AField(type = TypeField.textArea, widthRem = 48)
    public String tmplBio;


    @AField(type = TypeField.localDateTime, typeDate = TypeDate.normaleOrario)
    public LocalDateTime lastServer;


    @AField(type = TypeField.localDateTime, typeDate = TypeDate.normaleOrario)
    public LocalDateTime lastMongo;


    @Override
    public String toString() {
        return wikiTitle;
    }

}// end of crud entity class