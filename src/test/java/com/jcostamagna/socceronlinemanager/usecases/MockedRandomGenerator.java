package com.jcostamagna.socceronlinemanager.usecases;

import com.jcostamagna.socceronlinemanager.domain.random.RandomNumberGenerator;

public class MockedRandomGenerator implements RandomNumberGenerator {

    private int number = 0;

    @Override
    public int generateNumberBetween(int min, int bound) {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
