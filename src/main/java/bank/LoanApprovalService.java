package bank;

// LoanApprovalService.java — classe à tester



public class LoanApprovalService {

    private final CreditScoringAPI scoringAPI;
    private final double maxLoanAmount;

    // Injection par constructeur — favorise la testabilité
    public LoanApprovalService(CreditScoringAPI scoringAPI,
                               double maxLoanAmount) {
        this.scoringAPI    = scoringAPI;
        this.maxLoanAmount = maxLoanAmount;
    }



    public String getLoanDecision(String borrowerId, double amount) {
        if (scoringAPI.isBlacklisted(borrowerId))
            return "REJECTED_BLACKLIST";
        int score = scoringAPI.getScore(borrowerId);
        if (score < 600)  return "REJECTED_SCORE";
        if (amount > maxLoanAmount) return "REJECTED_AMOUNT";
        return "APPROVED";
    }

    public boolean approveLoan(String borrowerId, double amount) {
        try {
            if (scoringAPI.isBlacklisted(borrowerId)) return false;
            int score = scoringAPI.getScore(borrowerId);
            if (score < 600)  return false;
            if (amount > maxLoanAmount) return false;
            return true;
        } catch (RuntimeException e) {
            // API indisponible → refus par sécurité
            return false;
        }
    }

}
