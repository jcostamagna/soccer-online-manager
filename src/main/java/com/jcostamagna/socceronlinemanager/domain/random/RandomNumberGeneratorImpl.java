package com.jcostamagna.socceronlinemanager.domain.random;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGeneratorImpl implements RandomNumberGenerator {

    public int generateNumberBetween(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }
}
