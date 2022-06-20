package com.jcostamagna.socceronlinemanager.domain.faker;

import com.github.javafaker.Faker;
import com.jcostamagna.socceronlinemanager.domain.random.RandomNumberGenerator;
import com.jcostamagna.socceronlinemanager.domain.random.RandomNumberGeneratorImpl;
import org.springframework.stereotype.Component;

@Component
public class FakerGeneratorImpl implements FakerGenerator {

    Faker faker = new Faker();
    RandomNumberGenerator randomNumberGenerator = new RandomNumberGeneratorImpl();

    @Override
    public String generateFirstName() {
        return faker.name().firstName();
    }

    @Override
    public String generateLastName() {
        return faker.name().lastName();
    }

    @Override
    public int generateAge() {
        return randomNumberGenerator.generateNumberBetween(18, 41);
    }

    @Override
    public String generateCountry() {
        return faker.country().name();
    }

    @Override
    public String footballClubName() {
        return faker.team().name();
    }
}
