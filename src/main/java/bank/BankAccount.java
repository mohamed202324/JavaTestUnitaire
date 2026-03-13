// BankAccount.java — version mise à jour avec AuditLogger
package bank;

public class BankAccount {

    private String accountId;
    private double balance;
    private AuditLogger logger;   // nouvelle dépendance

    public BankAccount(String accountId, double initialBalance,
                       AuditLogger logger) {
        this.accountId = accountId;
        this.balance   = initialBalance;
        this.logger    = logger;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException(
                "Le montant doit être positif");
        balance += amount;
        logger.log("DEPOSIT", amount, accountId);
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException(
                "Le montant doit être positif");
        if (amount > balance) throw new IllegalStateException(
                "Solde insuffisant");
        balance -= amount;
        logger.log("WITHDRAW", amount, accountId);
    }

    public double getBalance() { return balance; }
    public String getAccountId() { return accountId; }
}
