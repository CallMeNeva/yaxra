--  SPDX-License-Identifier: Unlicense

CREATE SCHEMA IF NOT EXISTS yaxra_data;
COMMENT ON SCHEMA yaxra_data IS 'Primary data for the Yet Another eXchange Rates API';


CREATE TABLE IF NOT EXISTS yaxra_data.currencies
(
    -- Reference: https://www.iso.org/iso-4217-currency-codes.html
    iso_4217_num_code   SMALLINT    NOT NULL,
    iso_4217_alpha_code CHAR(3)     NOT NULL,
    name                VARCHAR(30) NOT NULL,

    CONSTRAINT currencies_pk PRIMARY KEY (iso_4217_num_code),
    CONSTRAINT num_code_digit_count CHECK (iso_4217_num_code BETWEEN 0 AND 999),
    CONSTRAINT alpha_code_chars CHECK (iso_4217_alpha_code SIMILAR TO '[A-Z]{3}'),
    CONSTRAINT alpha_code_uniqueness UNIQUE (iso_4217_alpha_code)
);


CREATE TABLE IF NOT EXISTS yaxra_data.exchange_rates
(
    base_num_code   SMALLINT NOT NULL,
    target_num_code SMALLINT NOT NULL,
    rate_value      NUMERIC  NOT NULL, -- https://wiki.postgresql.org/wiki/Don%27t_Do_This#Don%27t_use_money
    date            DATE     NOT NULL DEFAULT CURRENT_DATE,

    CONSTRAINT exchange_rates_pk PRIMARY KEY (base_num_code, target_num_code, date),
    CONSTRAINT base_num_code_fk FOREIGN KEY (base_num_code) REFERENCES yaxra_data.currencies (iso_4217_num_code),
    CONSTRAINT target_num_code_fk FOREIGN KEY (target_num_code) REFERENCES yaxra_data.currencies (iso_4217_num_code),
    -- There isn't anything wrong with a record of a currency against itself per
    -- se, but it's completely redundant as it's always 1:1 at any point in time.
    CONSTRAINT base_not_equal_target CHECK (base_num_code != target_num_code)

    -- TODO:
    --  Does it make sense for exchange rates to be negative? What about zero?
    --  If yes, implement a constraint for that. Potentially useful resources:
    --  https://www.quora.com/Can-exchange-rates-between-currencies-be-negative
);
