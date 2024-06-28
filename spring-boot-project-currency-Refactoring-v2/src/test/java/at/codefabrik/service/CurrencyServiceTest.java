package at.codefabrik.service;

import at.codefabrik.controller.CurrencyController;
import at.codefabrik.entity.CurrencyBot;
import at.codefabrik.entity.CurrencyData;
import at.codefabrik.utils.AppConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyServiceTest {

    private CurrencyService currencyService;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        restTemplate = Mockito.mock(RestTemplate.class);
        objectMapper = new ObjectMapper();
        currencyService = new CurrencyService(restTemplate);
    }
    @Test
    void testGetCurrencies() throws IOException {
        String jsonResponse = "{ \"Update_Date\": \"2024-06-25 21:15:02\", \"USD\": { \"Buying\": \"32.9584\", \"Selling\": \"32.9628\", \"Type\": \"Currency\", \"Change\": \"%0,20\" }, \"EUR\": { \"Buying\": \"35.1234\", \"Selling\": \"35.5678\", \"Type\": \"Currency\", \"Change\": \"%0,15\" } }";
        Mockito.when(restTemplate.getForObject("https://finans.truncgil.com/v4/today.json", String.class)).thenReturn(jsonResponse);

        Map<String, CurrencyBot> expectedCurrencies = new HashMap<>();
        expectedCurrencies.put("USD", new CurrencyBot(new BigDecimal("32.9584"), new BigDecimal("32.9628"), "Currency", "%0,20"));
        expectedCurrencies.put("EUR", new CurrencyBot(new BigDecimal("35.1234"), new BigDecimal("35.5678"), "Currency", "%0,15"));

        Map<String, CurrencyBot> actualCurrencies = currencyService.getCurrencies();

        System.out.println("Expected currencies: " + expectedCurrencies);
        System.out.println("Actual currencies: " + actualCurrencies);

        assertEquals(expectedCurrencies.get("USD").getBuying(), actualCurrencies.get("USD").getBuying());
        assertEquals(expectedCurrencies.get("USD").getSelling(), actualCurrencies.get("USD").getSelling());
        // usw. für andere Felder


        assertEquals(expectedCurrencies, actualCurrencies);
    }

    @Test
    void getCurrencies() {
    }

    @Test
    void getUpdateDate() {
    }
}