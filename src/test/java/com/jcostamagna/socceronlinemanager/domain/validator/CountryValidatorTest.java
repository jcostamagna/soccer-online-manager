package com.jcostamagna.socceronlinemanager.domain.validator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryValidatorTest {

    @Test
    public void test_validate_countries() {
        // GIVEN
        CountryValidatorImpl countryValidator = new CountryValidatorImpl();

        // THEN
        assertThat(countryValidator.validate("Argentina"))
                .isEqualTo(true);

        assertThat(countryValidator.validate("United States"))
                .isEqualTo(true);

        assertThat(countryValidator.validate("Central African Republic"))
                .isEqualTo(true);

        assertThat(countryValidator.validate("India"))
                .isEqualTo(true);
    }
}