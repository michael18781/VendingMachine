package com.mthree.vendingmachine.interfaces;

import com.mthree.vendingmachine.Coins;
import com.mthree.vendingmachine.MenuOptions;
import com.mthree.vendingmachine.VendingItem;
import java.util.HashMap;

public interface VendingViewInterface {
    void displayMenuOptions();
    MenuOptions getMenuOption();

    void displayMessage(String msg);
    void displayExceptionFound(Exception ex);

    String getItemNameFromUser();

    HashMap<Coins, Integer> getMoneyIn();

    void displayCoinsReturned(HashMap<Coins, Integer> returned);

    String getFileNameToOpen();
    String getFileNameToSaveTo();

    void displayVendingItems(HashMap<String, VendingItem> vendingCollection);
}
