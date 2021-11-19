package com.mthree.vendingmachine;

import com.mthree.vendingmachine.exceptions.*;
import com.mthree.vendingmachine.interfaces.VendingViewInterface;

import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class VendingView implements VendingViewInterface {
    private final HashMap<Integer, MenuOptions> options = new HashMap<>();

    public VendingView() {
        // Create menu options to use when displaying
        for(MenuOptions option: MenuOptions.values()){
            options.put(option.getValue(), option);
        }
    }

    public void displayMenuOptions(){
        displayMessage("------");
        // Displays the options menu to the user
        for (Integer key : options.keySet()) {
            if (!(options.get(key) == MenuOptions.INVALID)) displayMessage(key + ": " + options.get(key));
        }
    }

    public MenuOptions getMenuOption() {
        // Display the menu and take in the user input for their selection.
        Scanner inputReader = new Scanner(System.in);
        displayMenuOptions();

        try {
            // Tries to get users input for the menu
            displayMessage("Enter an option: ");
            int intChoice = Integer.parseInt(inputReader.nextLine());

            // Validating the user input
            if ((intChoice < Collections.min(options.keySet())) || (intChoice > Collections.max(options.keySet())) || intChoice == 0) {
                throw new NumberFormatException();
            } else {
                return options.get(intChoice);
            }

        } catch (NumberFormatException ex) {
            displayExceptionFound(new MenuChoiceException("Invalid menu choice. Try again!"));
        }
        return getMenuOption();
    }

    public void displayMessage(String msg){
        System.out.println(msg);
    }

    public void displayExceptionFound(Exception ex) {
        displayMessage(ex.getMessage());
        if(ex instanceof InsufficientFundsException){
            // This part ensures that we can return coins if the user wasn't able to purchase anything.
            displayCoinsReturned(((InsufficientFundsException) ex).getReturnedCoins());
        }
    }

    public String getItemNameFromUser(){
        Scanner inputReader = new Scanner(System.in);
        displayMessage("Enter the product name: ");
        return inputReader.nextLine();
    }

    public HashMap<Coins, Integer> getMoneyIn(){
        displayMessage("------");
        HashMap<Coins, Integer> coinsEntered = new HashMap<>();

        // Will get coin input for every type of coin and return the set of coins the user has deposited.
        for (Coins coin: Coins.values()){
            coinsEntered.put(coin, tryToGetCoinIn(coin));
        }

        return coinsEntered;
    }

    public int tryToGetCoinIn(Coins coin){
        // Code to repeatedly ask the user to enter a quantity of Coins value until they enter
        // an integer number.
        Scanner inputReader = new Scanner(System.in);
        displayMessage("How many " + coin + " ?");
        String line = inputReader.nextLine();

        int number = 0;

        try {
            number = Integer.parseInt(line);
        } catch (NumberFormatException ex){
            displayMessage("Incorrect number format.");
            tryToGetCoinIn(coin);
        }

        return number;
    }

    public void displayCoinsReturned(HashMap<Coins, Integer> returned){
        displayMessage("----- Your change / returned coins -----");
        for(Coins coin: returned.keySet()){
            displayMessage(coin.toString() + ": " + returned.get(coin));
        }
    }

    public String getFileNameToOpen(){
        Scanner inputReader = new Scanner(System.in);
        displayMessage("Enter file name to open: ");
        return inputReader.nextLine();
    }

    public String getFileNameToSaveTo(){
        Scanner inputReader = new Scanner(System.in);
        displayMessage("Enter file name to save to: ");
        return inputReader.nextLine();
    }

    public void displayVendingItems(HashMap<String, VendingItem> vendingCollection) {
        displayMessage("------");
        for(VendingItem item: vendingCollection.values()){
            displayMessage(item.getItemName() + ": " + item.getNumberItems() + " in stock, price = £" + item.getItemCost());
        }
    }
}
