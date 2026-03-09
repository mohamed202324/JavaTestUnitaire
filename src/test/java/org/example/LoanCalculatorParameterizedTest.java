package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class LoanCalculatorParameterizedTest {

    @ParameterizedTest
    @CsvSource({
            "1000,  0.05, 5",
            "5000,  0.03, 10",
            "10000, 0.04, 20"
    })
    void calculMensualiteDoitRetournerUneValeurPositive(
            double capital, double taux, int duree) {
        LoanCalculator calc = new LoanCalculator();
        double resultat = calc.calculMensualite(capital, taux, duree);
        assertTrue(resultat > 0);
    }

    @ParameterizedTest
    @CsvSource({
            "-1000, 0.05, 10",
            "0,     0.05, 10",
            "10000, -0.05, 10",
            "10000, 0.05, 0",
            "10000, 0.05, -5"
    })
    void parametresInvalidesDoiventLeverException(
            double capital, double taux, int duree) {
        LoanCalculator calc = new LoanCalculator();
        assertThrows(IllegalArgumentException.class,
                () -> calc.calculMensualite(capital, taux, duree));
    }

    @ParameterizedTest
    @CsvSource({
            "1200, 0.0, 1, 100.0",
            "2400, 0.0, 2, 100.0",
            "3600, 0.0, 1, 300.0"
    })
    void tauxZeroDoitRetournerCapitalDiviseParNombreDeMois(
            double capital, double taux, int duree, double attendu) {
        LoanCalculator calc = new LoanCalculator();
        double resultat = calc.calculMensualite(capital, taux, duree);
        assertEquals(attendu, resultat);
    }
}