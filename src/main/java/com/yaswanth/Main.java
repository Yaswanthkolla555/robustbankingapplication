package com.yaswanth;

import com.yaswanth.controller.BankingController;
import com.yaswanth.model.Bank;
import com.yaswanth.view.BankingView;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        BankingView bankingView = new BankingView();
        BankingController bankingController = new BankingController(bank,bankingView);

        bankingController.start();
    }
}