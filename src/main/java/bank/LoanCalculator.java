package bank;

public class LoanCalculator {

    public double getInterestRate() {
        return 0.035;
    }

    public double calculateMonthlyPayment(double principal, int months) {
        double rate = getInterestRate() / 12;
        if (rate == 0) return principal / months;
        return (principal * rate) / (1 - Math.pow(1 + rate, -months));
    }
}