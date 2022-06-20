package com.jcostamagna.socceronlinemanager.usecases;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TransferMarketServiceTest extends TransferMarketService {

    @Test
    public void test_calculate_new_player_value() {
        test_generator(10, 1000, 1100);
        test_generator(11, 1000, 1110);
        test_generator(13, 1000, 1130);
        test_generator(58, 1000, 1580);
        test_generator(99, 1000, 1990);
        test_generator(100, 1000, 2000);
        test_generator(99, 1111, 2210);
        test_generator(17, 1111, 1299);
        test_generator(17, 1, 1);
    }

    public void test_generator(int randomNumer, int initialPrice, int expected) {
        // GIVEN
        MockedRandomGenerator mockedRandomGenerator = new MockedRandomGenerator();
        this.randomNumberGenerator = mockedRandomGenerator;

        mockedRandomGenerator.setNumber(randomNumer);

        int result = this.calculateNewPlayerValue(initialPrice);

        // THEN
        assertThat(result)
                .isEqualTo(expected);
    }


}