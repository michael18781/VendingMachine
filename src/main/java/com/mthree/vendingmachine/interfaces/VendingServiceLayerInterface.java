package com.mthree.vendingmachine.interfaces;

import com.mthree.vendingmachine.Coins;
import com.mthree.vendingmachine.VendingItem;
import com.mthree.vendingmachine.exceptions.DAOException;
import com.mthree.vendingmachine.exceptions.InsufficientFundsException;
import com.mthree.vendingmachine.exceptions.ItemNotFoundException;
import com.mthree.vendingmachine.exceptions.NoItemInventoryException;

import java.util.HashMap;

public interface VendingServiceLayerInterface {

    void retrieveItemsUsingDAO(String filename) throws DAOException;
    void writeItemsUsingDAO(String filename) throws DAOException;

    HashMap<String, VendingItem> getServiceItems();
    void setServiceItems(HashMap<String, VendingItem> items);

    HashMap<Coins, Integer> purchaseItem(String itemName, HashMap<Coins, Integer> userPayment)
            throws ItemNotFoundException, NoItemInventoryException, InsufficientFundsException, DAOException;
}
