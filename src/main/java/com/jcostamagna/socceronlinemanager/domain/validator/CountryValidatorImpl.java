package com.jcostamagna.socceronlinemanager.domain.validator;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class CountryValidatorImpl implements CountryValidator {

    public boolean validate(String countryToValidate) {
        Locale.setDefault(Locale.ENGLISH);

        List<String> list = Arrays.stream(Locale.getISOCountries())
                .map(country -> new Locale("en", country))
                .map(Locale::getDisplayCountry)
                .collect(Collectors.toList());

        return list.contains(countryToValidate);
    }
}
