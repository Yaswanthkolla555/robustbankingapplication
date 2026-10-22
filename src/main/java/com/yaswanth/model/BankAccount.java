package com.yaswanth.model;

import com.yaswanth.exception.InsufficientFundsException;
import com.yaswanth.exception.InvalidAmountException;

import java.math.BigDecimal;

public class BankAccount {
    private final String  accountNumber;
    private BigDecimal  balance;

    public BankAccount(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = BigDecimal.ZERO;
    }


    public void deposit(BigDecimal amount) throws InvalidAmountException{
        validateAmount(amount);
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) throws InvalidAmountException, InsufficientFundsException {
        validateAmount(amount);
        if(amount.compareTo(balance) > 0){
            throw new InsufficientFundsException("Insufficient Funds. Available balance: " + balance);
        }

        balance = balance.subtract(amount);
    }

    private void validateAmount(BigDecimal amount)throws InvalidAmountException {
        if(amount == null || amount.signum() <= 0){
            throw new InvalidAmountException( "Amount must be greater than zero.");
        }
        if(amount.scale() > 2){
            throw new InvalidAmountException("Amount cannot have more than two decimal places.");
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
