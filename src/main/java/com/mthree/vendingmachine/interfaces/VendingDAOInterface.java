package com.mthree.vendingmachine.interfaces;

import com.mthree.vendingmachine.VendingItem;
import com.mthree.vendingmachine.exceptions.DAOException;

import java.util.HashMap;

public interface VendingDAOInterface {
    HashMap<String, VendingItem> getVendingItemsFromStorage(String filename) throws DAOException;
    void writeVendingItemsToStorage(String filename, HashMap<String, VendingItem> vendingItems) throws DAOException;
}
