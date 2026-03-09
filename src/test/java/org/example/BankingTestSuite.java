package org.example;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        LoanCalculatorTest.class,
        LoanCalculatorParameterizedTest.class,
        BankAccountTest.class
})
class BankingTestSuite {
}