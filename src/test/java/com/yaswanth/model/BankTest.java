package com.yaswanth.model;

import com.yaswanth.exception.AccountNotFoundException;
import com.yaswanth.exception.InsufficientFundsException;
import com.yaswanth.exception.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {


    private Bank bank;
    private BankAccount source;
    private BankAccount destination;

    @BeforeEach
    void setUp() throws Exception {
        bank = new Bank();

        source = bank.createAccount();
        destination = bank.createAccount();

        source.deposit(new BigDecimal("1000.00"));
        destination.deposit(new BigDecimal("500.00"));
    }

    @Test
    void accountsShouldHaveUniqueNumbers() {
        assertNotEquals(source.getAccountNumber(),destination.getAccountNumber());
    }

    @Test
    void existingAccountShouldBeFound() throws Exception {
        BankAccount found =bank.getAccount(source.getAccountNumber());

        assertSame(source, found);
    }

    @Test
    void nonexistentAccountShouldThrowException() {
        assertThrows(AccountNotFoundException.class,() -> bank.getAccount("9999999"));
    }

    @Test
    void transferShouldUpdateBothBalances() throws Exception {
        bank.transferMoney(source.getAccountNumber(),destination.getAccountNumber(),new BigDecimal("200.00"));

        assertEquals(new BigDecimal("800.00"),source.getBalance());

        assertEquals(new BigDecimal("700.00"),destination.getBalance());
    }

    @Test
    void insufficientFundsShouldLeaveBothBalancesUnchanged() {
        assertThrows(
                InsufficientFundsException.class,
                () -> bank.transferMoney(source.getAccountNumber(),destination.getAccountNumber(),new BigDecimal("2000.00"))
        );

        assertEquals(0, new BigDecimal("1000.00").compareTo(source.getBalance()));
        assertEquals(0, new BigDecimal("500.00").compareTo(destination.getBalance()));
    }

    @Test
    void nonexistentDestinationShouldNotDebitSource() {

        assertThrows(AccountNotFoundException.class, () -> bank.transferMoney(source.getAccountNumber(), "9999999", new BigDecimal("100.00")));
        assertEquals(0, new BigDecimal("1000.00").compareTo(source.getBalance()));
    }

    @Test
    void transferToSameAccountShouldBeRejected() {

        assertThrows(AccountNotFoundException.class, () -> bank.transferMoney(source.getAccountNumber(), source.getAccountNumber(), new BigDecimal("100.00")));
        assertEquals(0, new BigDecimal("1000.00").compareTo(source.getBalance()));
    }

    @Test
    void negativeTransferShouldBeRejected() {
        assertThrows(InvalidAmountException.class, () -> bank.transferMoney(source.getAccountNumber(), destination.getAccountNumber(), new BigDecimal("-100.00")));
        assertEquals(0, new BigDecimal("1000.00").compareTo(source.getBalance()));
        assertEquals(0, new BigDecimal("500.00").compareTo(destination.getBalance()));
    }

    @Test
    void zeroTransferShouldBeRejected() {
        assertThrows(InvalidAmountException.class, () -> bank.transferMoney(source.getAccountNumber(), destination.getAccountNumber(), BigDecimal.ZERO));
    }

    @Test
    void transferWithExcessPrecisionShouldBeRejected() {
        assertThrows(InvalidAmountException.class, () -> bank.transferMoney(source.getAccountNumber(), destination.getAccountNumber(), new BigDecimal("10.123")));
    }
}
