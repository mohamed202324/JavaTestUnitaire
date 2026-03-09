package org.example;


public class BankAccount {

    private double balance = 0;

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Montant invalide");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Montant invalide");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Solde insuffisant");
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}