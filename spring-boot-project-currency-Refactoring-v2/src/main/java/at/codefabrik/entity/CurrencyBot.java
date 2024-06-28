package at.codefabrik.entity;
import at.codefabrik.utils.CustomBigDecimalDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyBot {
    @JsonProperty("Buying")
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private BigDecimal buying;

    @JsonProperty("Selling")
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private BigDecimal selling;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Change")
    private String change;


    // Standardkonstruktor
    public CurrencyBot() {
    }

    // Konstruktor mit Argumenten
    @JsonCreator
    public CurrencyBot(@JsonProperty("Buying") BigDecimal buying,
                       @JsonProperty("Selling") BigDecimal selling,
                       @JsonProperty("Type") String type,
                       @JsonProperty("Change") String change) {
        if (buying == null || selling == null) {
            throw new IllegalArgumentException("Buying and Selling rates must not be null");
        }
        this.buying = buying;
        this.selling = selling;
        this.type = type;
        this.change = change;
    }

    // Getter und Setter
    public BigDecimal getBuying() {
        return buying;
    }

    public void setBuying(BigDecimal buying) {
        this.buying = buying;
    }

    public BigDecimal getSelling() {
        return selling;
    }

    public void setSelling(BigDecimal selling) {
        this.selling = selling;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyBot that = (CurrencyBot) o;
        return Objects.equals(buying, that.buying) &&
                Objects.equals(selling, that.selling) &&
                Objects.equals(type, that.type) &&
                Objects.equals(change, that.change);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buying, selling, type, change);
    }

}
