// SPDX-License-Identifier: Unlicense

package com.altoukhov.yaxra.exchange;

import com.altoukhov.yaxra.currency.Currency;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RestController
@RequestMapping("/rates")
public class ExchangeRateController {

    private final ExchangeRateRepository repository;

    public ExchangeRateController(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{baseId}/{dateFormat}")
    public ResponseEntity<Map<String, BigDecimal>> get(@PathVariable String baseId, @PathVariable String dateFormat) {
        try {
            short baseNumericCode = Short.parseShort(baseId);
            LocalDate date = LocalDate.parse(dateFormat);
            List<ExchangeRate> rates = repository.findByBaseAndDate(baseNumericCode, date);

            Map<String, BigDecimal> dto = rates.stream()
                    .collect(toMap(rate -> {
                        Exchange exchange = rate.getExchange();
                        Currency targetCurrency = exchange.getTargetCurrency();
                        return targetCurrency.getFormattedNumericCode();
                    }, ExchangeRate::getValue));
            return ResponseEntity.ok(dto);
        } catch (NumberFormatException | DateTimeParseException parseException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
