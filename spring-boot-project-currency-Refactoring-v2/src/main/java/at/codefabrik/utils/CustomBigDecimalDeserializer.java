package at.codefabrik.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class CustomBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        //String value = p.getText();
        String value = p.getValueAsString().trim();

        /*
        // Define decimal format with comma as decimal separator
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.0#", symbols);
         */

        /*
        // Entfernen Sie alle nicht numerischen Zeichen außer Punkt und Komma
        value = value.replaceAll("[^0-9,]", "").replace(',', '.');

        // Entfernen Sie alle Tausendertrennzeichen
        value = value.replace(".", "");
         */

        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            // Wenn die Umwandlung fehlschlägt, geben Sie null zurück oder werfen eine geeignete Ausnahme
            throw new IOException("Invalid number format: " + value, e);
        }
    }
}

