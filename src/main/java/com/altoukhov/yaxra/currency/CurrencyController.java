// SPDX-License-Identifier: Unlicense

package com.altoukhov.yaxra.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyRepository repository;

    @Autowired
    public CurrencyController(CurrencyRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Map<String, String> all() {
        return repository.findAll()
                .stream()
                .collect(toMap(Currency::getFormattedNumericCode, Currency::getName));
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrencyGetDTO> byId(@PathVariable String id) {
        try {
            short numericCode = Short.parseShort(id);
            Optional<Currency> entity = repository.findById(numericCode);

            Optional<CurrencyGetDTO> dto = entity.map(e -> new CurrencyGetDTO(e.getAlphabeticCode(), e.getName()));
            return ResponseEntity.of(dto);
        } catch (NumberFormatException nfe) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
    }
}
