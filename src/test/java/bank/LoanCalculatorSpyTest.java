package bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanCalculatorSpyTest {

    @Spy
    private LoanCalculator calculator = new LoanCalculator();

    @Test
    void calculateMonthlyPayment_avecSpySurGetInterestRate() {
        // Stubber seulement getInterestRate()
        // La vraie méthode calculateMonthlyPayment() est exécutée
        doReturn(0.05).when(calculator).getInterestRate();

        double payment = calculator.calculateMonthlyPayment(12000.0, 12);

        // Le vrai calcul est utilisé, mais avec le taux stubé
        assertTrue(payment > 0);
        verify(calculator).calculateMonthlyPayment(12000.0, 12);
        verify(calculator).getInterestRate();
    }

    //5.3
    @Test
    void calculateMonthlyPayment_doitEtreExact_quandTauxZero() {
        doReturn(0.0).when(calculator).getInterestRate();

        double payment = calculator.calculateMonthlyPayment(12000.0, 12);

        assertEquals(1000.0, payment, 0.001); // 12000 / 12 = 1000
    }

}
