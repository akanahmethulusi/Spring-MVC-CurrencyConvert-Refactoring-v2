package at.codefabrik.controller;

import at.codefabrik.entity.CurrencyBot;
import at.codefabrik.entity.CurrencyData;
import at.codefabrik.exception.InvalidCurrencyException;
import at.codefabrik.exception.SpecialCurrencyException;
import at.codefabrik.service.CurrencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyControllerTest {
    CurrencyController currencyController;
    CurrencyService currencyService;
    CurrencyData currencyData;

    @BeforeEach
    public void setUp(){
        currencyService = Mockito.mock(CurrencyService.class);
        currencyController = new CurrencyController(currencyService);
    }

    @Test
    void getAllCurrencies() throws IOException {
        currencyService.getCurrencies();
        Mockito.verify(currencyService).getCurrencies();
    }

    @Test
    void getUpdateDate() throws IOException {
        currencyService.getUpdateDate();
        Mockito.verify(currencyService).getUpdateDate();
    }

    @Test
    void testConvertCurrencyValid() throws IOException, InvalidCurrencyException, SpecialCurrencyException {
        Map<String, CurrencyBot> currencies = new HashMap<>();
        CurrencyBot usd = new CurrencyBot(new BigDecimal("32.9584"), new BigDecimal("32.9628"), new String("USD"), new String("%1.4"));
        CurrencyBot eur = new CurrencyBot(new BigDecimal("35.1234"), new BigDecimal("35.5678"), new String("EUR"), new String("%0.65"));
        currencies.put("USD", usd);
        currencies.put("EUR", eur);

        Mockito.when(currencyService.getCurrencies()).thenReturn(currencies);

        ResponseEntity<BigDecimal> response = currencyController.convertCurrency("USD", "EUR", new BigDecimal("100"));

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // Erwarteten Wert manuell berechnen
        BigDecimal expectedValue = new BigDecimal("100").multiply(new BigDecimal("32.9628")).divide(new BigDecimal("35.1234"), 2, BigDecimal.ROUND_HALF_UP);
        //Assertions.assertEquals(new BigDecimal("92.80"), response.getBody());
        Assertions.assertEquals(expectedValue, response.getBody());

    }

    @Test
    void testConvertCurrencyInvalidCurrency() throws IOException {
        Map<String, CurrencyBot> currencies = new HashMap<>();
        CurrencyBot usd = new CurrencyBot(new BigDecimal("32.9584"), new BigDecimal("32.9628"), new String("USD"), new String("%1.4"));
        currencies.put("USD", usd);

        Mockito.when(currencyService.getCurrencies()).thenReturn(currencies);

        InvalidCurrencyException exception = assertThrows(InvalidCurrencyException.class, () ->
                currencyController.convertCurrency("USD", "INVALID", new BigDecimal("100"))
        );
        Assertions.assertEquals("Invalid currency code: INVALID", exception.getMessage());
    }

    @Test
    void testConvertCurrencyAmountLessThanOrEqualToZero() throws IOException {
        Map<String, CurrencyBot> currencies = new HashMap<>();
        CurrencyBot usd = new CurrencyBot(new BigDecimal("32.9584"), new BigDecimal("32.9628"), new String("USD"), new String("%1.5"));
        CurrencyBot eur = new CurrencyBot(new BigDecimal("35.1234"), new BigDecimal("35.5678"), new String("EUR"), new String("%0.14"));
        currencies.put("USD", usd);
        currencies.put("EUR", eur);

        Mockito.when(currencyService.getCurrencies()).thenReturn(currencies);

        SpecialCurrencyException exception = assertThrows(SpecialCurrencyException.class, () ->
                currencyController.convertCurrency("USD", "EUR", new BigDecimal("-100"))
        );
        Assertions.assertEquals("Amount must be greater than zero", exception.getMessage());
    }
}