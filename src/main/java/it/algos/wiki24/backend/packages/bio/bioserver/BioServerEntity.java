package it.algos.wiki24.backend.packages.bio.bioserver;

import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import lombok.*;
import org.springframework.stereotype.*;

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


    @AField(type = TypeField.wikiAnchor, widthRem = 16)
    public String wikiTitle;

    //    @Lob
    @AField(type = TypeField.textArea, widthRem = 48)
    public String tmplBio;


    @AField(type = TypeField.localDateTime, typeDate = TypeDate.normaleOrario)
    public LocalDateTime timestamp;


    @Override
    public String toString() {
        return wikiTitle;
    }

}// end of crud entity class