package com.yaswanth.model;

import com.yaswanth.exception.InsufficientFundsException;
import com.yaswanth.exception.InvalidAmountException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setup() throws  Exception {
        account = new BankAccount("1000001");
        account.deposit(new BigDecimal("1000.00"));
    }

    @Test
    void depositShouldIncreaseBalance() throws Exception {
        account.deposit(new BigDecimal("500.00"));

        assertEquals(new BigDecimal("1500.00"),account.getBalance());
    }

    @Test
    void withdrawShouldDecreaseBalance() throws Exception {
        account.withdraw(new BigDecimal("200.00"));

        assertEquals(new BigDecimal("800.00"),account.getBalance());
    }


    @Test
    void withdrawingMoreThanBalanceShouldThrowException() {
        assertThrows(InsufficientFundsException.class,() -> account.withdraw(new BigDecimal("1500.00")));

        assertEquals(new BigDecimal("1000.00"),account.getBalance());
    }

    @Test
    void depositingNegativeAmountShouldThrowException() {
        assertThrows(InvalidAmountException.class,() -> account.deposit(new BigDecimal("-100.00")));

        assertEquals(new BigDecimal("1000.00"),account.getBalance());
    }

    @Test
    void depositingZeroShouldThrowException() {
        assertThrows(InvalidAmountException.class,() -> account.deposit(BigDecimal.ZERO));
    }

    @Test
    void withdrawingEntireBalanceShouldSucceed() throws Exception {
        account.withdraw(new BigDecimal("1000.00"));

        assertEquals(0, BigDecimal.ZERO.compareTo(account.getBalance()));
    }

    @Test
    void withdrawingNegativeAmountShouldThrowException() {
        assertThrows(InvalidAmountException.class,() -> account.withdraw(new BigDecimal("-50.00")));

        assertEquals(new BigDecimal("1000.00"),account.getBalance() );
    }

    @Test
    void amountWithMoreThanTwoDecimalPlacesShouldBeRejected() {
        assertThrows(InvalidAmountException.class,() -> account.deposit(new BigDecimal("10.123")));

        assertEquals(new BigDecimal("1000.00"),account.getBalance());
    }
}
