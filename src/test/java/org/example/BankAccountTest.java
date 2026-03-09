package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {



    //Depot valide
    @Test
    void depositDoitAugmenterLeSolde() {
        BankAccount account = new BankAccount();
        account.deposit(100);
        assertEquals(100, account.getBalance());
    }
    //Depot invalide
    @Test
    void depositInvalideDoitLeverException() {
        BankAccount account = new BankAccount();
        assertThrows(IllegalArgumentException.class,
                () -> account.deposit(-50));
    }
    //retrait valide
    @Test
    void withdrawDoitReduireLeSolde() {
        BankAccount account = new BankAccount();
        account.deposit(200);
        account.withdraw(50);
        assertEquals(150, account.getBalance());
    }


    //retrait supérieur au solde
    @Test
    void withdrawSuperieurAuSoldeDoitLeverException() {
        BankAccount account = new BankAccount();
        account.deposit(100);
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(200));
    }
}