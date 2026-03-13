package bank;

// CreditScoringAPI.java — interface (dépendance externe)
public interface CreditScoringAPI {
    int getScore(String borrowerId);   // retourne 0-850
    boolean isBlacklisted(String borrowerId);
}
