package bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanApprovalServiceTest {

    @Mock
    private CreditScoringAPI scoringAPI;

    private LoanApprovalService approvalService;

    @BeforeEach
    void setUp() {
        approvalService = new LoanApprovalService(scoringAPI, 50000.0);
    }

    // tests ici
    @Test
    void approveLoan_doitRetournerTrue_quandScoreSuffisantEtMontantOk() {
        // ARRANGE — configurer le mock
        when(scoringAPI.isBlacklisted("alice")).thenReturn(false);
        when(scoringAPI.getScore("alice")).thenReturn(720);

        // ACT
        boolean result = approvalService.approveLoan("alice", 20000.0);

        // ASSERT
        assertTrue(result);
    }

    @Test
    void approveLoan_doitRetournerFalse_quandScoreInsuffisant() {
        // ARRANGE
        when(scoringAPI.isBlacklisted("alice")).thenReturn(false);
        when(scoringAPI.getScore("alice")).thenReturn(550);
        // ACT
        boolean result = approvalService.approveLoan("alice", 20000.0);
        // ASSERT
        assertFalse(result);
    }

    @Test
    void getScore_doitRetourner0_quandMockNonConfigure() {
        int score = scoringAPI.getScore("inconnu");
        assertEquals(0, score);
    }
    @Test
    void getLoanDecision_doitRetournerRejectedScore_quandScore580() {
        // ARRANGE
        when(scoringAPI.isBlacklisted("alice")).thenReturn(false);
        when(scoringAPI.getScore("alice")).thenReturn(580);
        // ACT
        String decision = approvalService.getLoanDecision("alice", 20000.0);
        // ASSERT
        assertEquals("REJECTED_SCORE", decision);
    }

    @Test
    void approveLoan_doitRetournerFalse_quandClientBlackliste() {

        when(scoringAPI.isBlacklisted("alice")).thenReturn(true);

        boolean result = approvalService.approveLoan("alice", 20000.0);

        assertFalse(result);
        verify(scoringAPI, never()).getScore(any());
    }

    @Test
    void approveLoan_doitRetournerFalse_quandMontantTropEleve() {

        when(scoringAPI.isBlacklisted("alice")).thenReturn(false);
        when(scoringAPI.getScore("alice")).thenReturn(700);

        boolean result = approvalService.approveLoan("alice", 60000.0);

        assertFalse(result);
    }

    @Test
    void approveLoan_doitRetournerFalse_quandAPIIndisponible() {
        // Configurer le mock pour lever une exception
        when(scoringAPI.isBlacklisted(anyString()))
                .thenThrow(new RuntimeException("API scoring indisponible"));

        boolean result = approvalService.approveLoan("alice", 20000.0);

        assertFalse(result, "Un refus par défaut est attendu si l'API est down");
    }

    @Test
    void getScore_doitLeverException_quandScoreNegatif() {
        when(scoringAPI.isBlacklisted(anyString())).thenReturn(false);
        when(scoringAPI.getScore(anyString()))
                .thenThrow(new IllegalStateException("Score invalide"));

        // getLoanDecision() ne gère pas l'exception → elle se propage
        assertThrows(IllegalStateException.class,
                () -> approvalService.getLoanDecision("alice", 20000.0));
    }

    @Test
    void getScore_doitReussirPuisLeverException_auSecondAppel() {
        when(scoringAPI.isBlacklisted(anyString())).thenReturn(false);
        when(scoringAPI.getScore(anyString()))
                .thenReturn(700)
                .thenThrow(new RuntimeException("API indisponible"));

        // Premier appel → score 700 → approuvé
        assertTrue(approvalService.approveLoan("alice", 20000.0));

        // Second appel → exception → refus par sécurité
        assertFalse(approvalService.approveLoan("alice", 20000.0));
    }


}
