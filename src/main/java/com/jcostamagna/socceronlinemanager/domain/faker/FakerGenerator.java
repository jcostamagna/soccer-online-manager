package com.jcostamagna.socceronlinemanager.domain.faker;

public interface FakerGenerator {
    String generateFirstName();

    String generateLastName();

    int generateAge();

    String generateCountry();

    String footballClubName();
}
