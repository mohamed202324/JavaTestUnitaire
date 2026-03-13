// AuditLogger.java — interface de journalisation
package bank;

public interface AuditLogger {
    void log(String operation, double amount, String accountId);
}
