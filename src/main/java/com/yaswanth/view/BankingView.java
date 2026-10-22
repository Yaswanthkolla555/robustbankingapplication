package com.yaswanth.view;

import java.util.Scanner;

public class BankingView {

    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu(){
        System.out.println("\n===== ROBUST BANKING SYSTEM =====");
        System.out.println("1. Create Account");
        System.out.println("2. Check Balance");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Transfer Money");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    public String readInput(){
        return scanner.nextLine().trim();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String message) {
        System.out.println("ERROR: " + message);
    }

    public void close() {
        scanner.close();
    }
}
