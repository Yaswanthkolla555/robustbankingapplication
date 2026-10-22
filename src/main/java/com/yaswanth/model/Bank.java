package com.yaswanth.model;


import com.yaswanth.exception.AccountNotFoundException;
import com.yaswanth.exception.InsufficientFundsException;
import com.yaswanth.exception.InvalidAmountException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Bank {

    private final Map<String, BankAccount> accounts = new HashMap<>();

    private long nextAccountNumber = 1000001;

    public BankAccount createAccount() {
        String accountNumber = String.valueOf(nextAccountNumber++);
        BankAccount account = new BankAccount(accountNumber);
        accounts.put(accountNumber, account);

        return account;
    }

    public BankAccount getAccount(String accountNumber) throws AccountNotFoundException {

        BankAccount account = accounts.get(accountNumber);

        if(account == null) {
            throw new AccountNotFoundException("Account " + accountNumber + " not found");
        }

        return account;
    }

    public void transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount) throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException {

        if(amount == null || amount.signum() <= 0){
            throw new InvalidAmountException( "Amount must be greater than zero.");
        }
        if(amount.scale() > 2){
            throw new InvalidAmountException("Amount cannot have more than two decimal places.");
        }

        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new AccountNotFoundException("Source and destination accounts must be different.");
        }

        BankAccount source = getAccount(fromAccountNumber);
        BankAccount destination = getAccount(toAccountNumber);

        source.withdraw(amount);
        destination.deposit(amount);
    }

}
