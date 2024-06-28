package at.codefabrik.service;
import at.codefabrik.entity.CurrencyData;
import at.codefabrik.entity.CurrencyBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Service
public class CurrencyService {
    private static final String URL = "https://finans.truncgil.com/v4/today.json";
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyService.class);


    public CurrencyService(RestTemplate restTemplate) {
    }

    public CurrencyData getCurrencyData() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        String jsonString = response.getBody();

        LOGGER.info("Response Json: {}", jsonString);

        ObjectMapper objectMapper = new ObjectMapper();

        // Deserialize JSON into CurrencyData
        CurrencyData currencyData = objectMapper.readValue(jsonString, CurrencyData.class);

        LOGGER.info("CurrencyData: {}", currencyData);
        return currencyData;
    }

    public Map<String, CurrencyBot> getCurrencies() throws IOException {
        return getCurrencyData().getCurrencies();
    }

    public String getUpdateDate() throws IOException {
        return getCurrencyData().getUpdateDate();
    }
}
