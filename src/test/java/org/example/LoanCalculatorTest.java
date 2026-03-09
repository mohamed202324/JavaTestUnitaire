package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoanCalculatorTest {

    @Test
    void calculMensualiteDoitRetournerUneValeurPositive() {
        // Arrange
        LoanCalculator calc = new LoanCalculator();

        // Act
        double resultat = calc.calculMensualite(10000, 0.05, 10);

        // Assert
        assertTrue(resultat > 0);
    }


    //capital null ou négatif
    @Test
    void capitalNegatifDoitLeverException() {
        LoanCalculator calc = new LoanCalculator();
        assertThrows(IllegalArgumentException.class,
                () -> calc.calculMensualite(-1000, 0.05, 10));
    }

    @Test
    void capitalNulDoitLeverException() {
        LoanCalculator calc = new LoanCalculator();
        assertThrows(IllegalArgumentException.class,
                () -> calc.calculMensualite(0, 0.05, 10));
    }

    // taux négatif

    @Test
    void tauxNegatifDoitLeverException() {
        LoanCalculator calc = new LoanCalculator();
        assertThrows(IllegalArgumentException.class,
                () -> calc.calculMensualite(10000, -0.05, 10));
    }

    // Durée null ou négatif

    @Test
    void dureeNulleDoitLeverException() {
        LoanCalculator calc = new LoanCalculator();
        assertThrows(IllegalArgumentException.class,
                () -> calc.calculMensualite(10000, 0.05, 0));
    }

    @Test
    void dureeNegativeDoitLeverException() {
        LoanCalculator calc = new LoanCalculator();
        assertThrows(IllegalArgumentException.class,
                () -> calc.calculMensualite(10000, 0.05, -5));
    }

    //taux 0
    @Test
    void tauxZeroDoitRetournerCapitalDiviseParNombreDeMois() {
        LoanCalculator calc = new LoanCalculator();
        double resultat = calc.calculMensualite(1200, 0.0, 1);
        assertEquals(100.0, resultat);
    }

    //Capital faible
    @Test
    void capitalTresFaibleDoitRetournerUneValeurPositive() {
        LoanCalculator calc = new LoanCalculator();
        double resultat = calc.calculMensualite(0.01, 0.05, 1);
        assertTrue(resultat > 0);
    }

    //duree courte
    @Test
    void dureeTresCourteDoitRetournerUneValeurPositive() {
        LoanCalculator calc = new LoanCalculator();
        double resultat = calc.calculMensualite(10000, 0.05, 1);
        assertTrue(resultat > 0);
    }
}
