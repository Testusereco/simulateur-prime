package be.fodeco.simulateurprime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PrimeCalculatorTest {

    private final PrimeCalculator calculator = new PrimeCalculator();

    @Test
    void simuler_leveUneException_quandLeDenominateurEstNul() {
        // (F x G) + (B - A) = (0 x 0) + (1000 - 1000) = 0
        assertThrows(DenominateurNulException.class, () ->
            calculator.simuler(25, 1000, 1000, 0, 0, 1, 0, 0, 1)
        );
    }
}