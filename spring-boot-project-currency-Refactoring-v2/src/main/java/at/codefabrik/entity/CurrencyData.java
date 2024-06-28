package at.codefabrik.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyData {

    @JsonProperty("Update_Date")
    private String updateDate;

    private Map<String, CurrencyBot> currencies = new HashMap<>();

    // Verwenden Sie eine Map, um die verschiedenen Währungen zu speichern
    /*@JsonProperty("Currencies")
    private Map<String, CurrencyBot> currencies;

     */

    @JsonAnySetter
    public void setCurrency(String key, CurrencyBot  value) {
        if (!key.equals("Update_Date")) {
            this.currencies.put(key, value);
        }
    }

    // Getter und Setter
    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Map<String, CurrencyBot> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, CurrencyBot> currencies) {
        this.currencies = currencies;
    }
    @Override
    public String toString() {
        return "CurrencyData{" +
                "updateDate='" + updateDate + '\'' +
                ", currencies=" + currencies +
                '}';
    }
}

