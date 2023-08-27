package it.algos.vaad24.backend.enumeration;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import org.springframework.data.mongodb.core.query.*;

import java.util.regex.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 28-apr-2021
 * Time: 21:06
 */
public enum AETypeFilter {
    uguale("$eq", "Matches values that are equal to a specified value.") {
        @Override
        public Criteria getCriteria(final String fieldName, final Object value) {
            return Criteria.where(fieldName).is(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            String nota = VUOTA;
            if (value.startsWith(SPAZIO)) {
                nota = "(spazio iniziale)";
            }
            if (value.endsWith(SPAZIO)) {
                nota = "(spazio finale)";
            }
            return String.format("%s%s[%s %s %s] %s", this, FORWARD, fieldName, "(uguale)", value, nota);
        }
    },
    maggiore("$gt", "Matches values that are greater than a specified value.") {
        @Override
        public Criteria getCriteria(final String fieldName, final Object value) {
            return Criteria.where(fieldName).gt(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return String.format("%s%s[%s %s %s]", this, FORWARD, fieldName, "(maggiore di)", value);
        }
    },
    maggioreUguale("$gte", "Matches values that are greater than or equal to a specified value.") {
        @Override
        public Criteria getCriteria(final String fieldName, final Object value) {
            return Criteria.where(fieldName).gte(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return String.format("%s%s[%s %s %s]", this, FORWARD, fieldName, "(maggiore o uguale a)", value);
        }
    },
    minore("$lt", "Matches values that are less than a specified value.") {
        @Override
        public Criteria getCriteria(final String fieldName, final Object value) {
            return Criteria.where(fieldName).lt(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return String.format("%s%s[%s %s %s]", this, FORWARD, fieldName, "(minore di)", value);
        }
    },
    minoreUguale("$lte", "Matches values that are less than or equal to a specified value.") {
        @Override
        public Criteria getCriteria(final String fieldName, final Object value) {
            return Criteria.where(fieldName).lte(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return String.format("%s%s[%s %s %s]", this, FORWARD, fieldName, "(minore o uguale a)", value);
        }
    },
    regex("$regex", "Selects documents where values match a specified regular expression.") {
        @Override
        public Criteria getCriteria(final String fieldName, final Object value) {
            return Criteria.where(fieldName).regex((String) value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return VUOTA;
        }
    },
    diverso("$ne", "Matches all values that are not equal to a specified value.") {
        @Override
        public Criteria getCriteria(final String fieldName, final Object value) {
            return Criteria.where(fieldName).ne(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return String.format("%s%s[%s %s %s]", this, FORWARD, fieldName, "(diverso da)", value);
        }
    },
    lista("$in", "Matches any of the values specified in an array.") {
        @Override
        public Criteria getCriteria(final String fieldName, final Object value) {
            return Criteria.where(fieldName).in(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return String.format("%s%s[%s %s %s]", this, FORWARD, fieldName, "(nella lista)", value);
        }
    },
    contiene("$regex", "Seleziona i documenti che contengono il valore indicato.") {
        @Override
        public Criteria getCriteria(final String fieldName, final Object value) {
            String questionPattern = ".*" + Pattern.quote((String) value) + ".*";
            return Criteria.where(fieldName).regex(questionPattern, "i");
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            String nota = VUOTA;
            if (value.startsWith(SPAZIO)) {
                nota = "(spazio iniziale)";
            }
            if (value.endsWith(SPAZIO)) {
                nota = "(spazio finale)";
            }
            return String.format("%s%s[%s %s %s] %s", this, FORWARD, fieldName, "(contiene)", value, nota);
        }
    },
    inizia("$regex", "Seleziona i documenti che iniziano col valore indicato.") {
        @Override
        public Criteria getCriteria(final String fieldName, final Object value) {
            String questionPattern = "^" + Pattern.quote((String) value) + ".*";
            return Criteria.where(fieldName).regex(questionPattern, "i");
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return String.format("%s%s[%s %s %s]", this, FORWARD, fieldName, "(inizia con)", value);
        }
    },
    link("$eq", "Seleziona i documenti che hanno un link (DBRef) alla collezione indicata.") {
        @Override
        public Criteria getCriteria(final String fieldName, final Object value) {
            return Criteria.where(fieldName).is(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            return String.format("%s%s[(DBRef) %s %s %s]", this, FORWARD, fieldName, "(linkato a)", value);
        }
    },
    checkBox3Vie("$eq", "CheckBox a 3 stati.") {
        @Override
        public Criteria getCriteria(final String fieldName, final Object value) {
            return Criteria.where(fieldName).is(value);
        }

        @Override
        public String getOperazione(final String fieldName, final String value) {
            String nota = VUOTA;
            if (value.startsWith(SPAZIO)) {
                nota = "(spazio iniziale)";
            }
            if (value.endsWith(SPAZIO)) {
                nota = "(spazio finale)";
            }
            return String.format("%s%s[%s %s %s] %s", this, FORWARD, fieldName, "(uguale)", value, nota);
        }
    },
    ;

    private String tag;

    private String descrizione;


    AETypeFilter(String tag, String descrizione) {
        this.tag = tag;
        this.descrizione = descrizione;
    }

    public Criteria getCriteria(final String fieldName, final Object value) {
        return null;
    }

    public String getOperazione(final String fieldName, final String value) {
        return null;
    }
}
