package at.codefabrik.controller;
import at.codefabrik.entity.CurrencyBot;
import at.codefabrik.exception.InvalidCurrencyException;
import at.codefabrik.exception.SpecialCurrencyException;
import at.codefabrik.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@RestController
public class CurrencyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyController.class);
    @Autowired
    private CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/currencies")
    public ResponseEntity<Map<String, CurrencyBot>> getAllCurrencies() throws IOException {
        Map<String, CurrencyBot> currencies = currencyService.getCurrencies();
        LOGGER.info("Currencies: {}", currencies);
        //return currencies;
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping("/update-date")
    public ResponseEntity<String> getUpdateDate() throws IOException {
        String updateDate = currencyService.getUpdateDate();
        LOGGER.info("Update Date: {}", updateDate);
        //return updateDate;
        return new ResponseEntity<>(updateDate, HttpStatus.OK);
    }

    @GetMapping("/convert")
    public ResponseEntity<BigDecimal> convertCurrency(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam BigDecimal amount) throws IOException, InvalidCurrencyException {

        Map<String, CurrencyBot> currencies = currencyService.getCurrencies();
        CurrencyBot fromCurrency = currencies.get(from);
        CurrencyBot toCurrency = currencies.get(to);

        if (fromCurrency == null || toCurrency == null) {
            throw new InvalidCurrencyException("Invalid currency code: " + (fromCurrency == null ? from : to));
        }

        // Weitere spezielle Exception unter bestimmten Bedingungen werfen
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new SpecialCurrencyException("Amount must be greater than zero");
        }

        BigDecimal fromRate = fromCurrency.getSelling();
        BigDecimal toRate = toCurrency.getBuying();

        BigDecimal calculation =  amount.multiply(fromRate).divide(toRate, 2, BigDecimal.ROUND_HALF_UP);
        return new ResponseEntity<>(calculation, HttpStatus.OK);
    }
}
