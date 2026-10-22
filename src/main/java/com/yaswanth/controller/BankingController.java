package com.yaswanth.controller;

import com.yaswanth.exception.AccountNotFoundException;
import com.yaswanth.exception.InsufficientFundsException;
import com.yaswanth.exception.InvalidAmountException;
import com.yaswanth.model.Bank;
import com.yaswanth.model.BankAccount;
import com.yaswanth.view.BankingView;

import java.math.BigDecimal;

public class BankingController {

    private final Bank bank;
    private final BankingView view;

    public BankingController(Bank bank, BankingView view){
        this.bank = bank;
        this.view = view;
    }

    public void start(){
        boolean running = true;

        while(running){

            view.displayMenu();
            String choice = view.readInput();

            try{
                switch(choice){
                    case "1":
                        createAccount();
                        break;

                    case "2":
                        checkBalance();
                        break;

                    case "3":
                        depositMoney();
                        break;

                    case "4":
                        withdrawMoney();
                        break;

                    case "5":
                        transferMoney();
                        break;

                    case "6":
                        running = false;
                        view.displayMessage( "Thank you for using our banking system!");
                        break;

                    default:
                        view.displayError("Invalid menu choice. Please enter 1-6." );
                }
            }catch (AccountNotFoundException | InvalidAmountException | InsufficientFundsException e){
                view.displayError(e.getMessage());
            }catch (NumberFormatException e) {
                view.displayError("Invalid amount. Enter a valid number, such as 500 or 500.50.");
            } catch (Exception e) {
                view.displayError("An unexpected error occurred. Please try again.");
            }
        }


        view.close();
    }

    private void createAccount(){

        BankAccount account = bank.createAccount();
        view.displayMessage("Account created successfully! Account number: " + account.getAccountNumber());
    }

    private void checkBalance() throws AccountNotFoundException{
        view.displayMessage("Enter account number ");
        String accountNumber = view.readInput();

        BankAccount account = bank.getAccount(accountNumber);
        view.displayMessage("Account balance is " + account.getBalance());
    }

    private void depositMoney() throws AccountNotFoundException,InvalidAmountException{
        view.displayMessage("Enter account number ");
        String accountNumber = view.readInput();

        BankAccount account = bank.getAccount(accountNumber);

        view.displayMessage("Enter amount to be deposited");
        BigDecimal amount = new BigDecimal(view.readInput());

        account.deposit(amount);

        view.displayMessage("Account deposited successfully, Updated balance:" + account.getBalance());
    }

    private void withdrawMoney() throws AccountNotFoundException, InvalidAmountException,InsufficientFundsException{
        view.displayMessage("Enter account number ");
        String accountNumber = view.readInput();

        BankAccount account = bank.getAccount(accountNumber);
        view.displayMessage("Enter amount to be withdrawn");
        BigDecimal amount = new BigDecimal(view.readInput());

        account.withdraw(amount);

        view.displayMessage("Account withdrawn successfully, Updated balance:" + account.getBalance());

    }

    private void transferMoney() throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException{

        view.displayMessage("Enter source account number:");
        String source = view.readInput();

        view.displayMessage("Enter destination account number:");
        String destination = view.readInput();

        view.displayMessage("Enter transfer amount:");
        BigDecimal amount = new BigDecimal(view.readInput());

        bank.transferMoney(source, destination, amount);

        view.displayMessage("Transfer successful");
        view.displayMessage( "Transferred ₹" + amount + " from "+ source + " to " + destination);
    }



}
