package com.mthree.vendingmachine;

import com.mthree.vendingmachine.exceptions.*;
import com.mthree.vendingmachine.interfaces.VendingServiceLayerInterface;

import java.math.BigDecimal;
import java.util.HashMap;

public class VendingServiceLayer implements VendingServiceLayerInterface {
    private final VendingDAO vendingDao;
    private final AuditDAO auditDAO;
    private HashMap<String, VendingItem> items;

    public VendingServiceLayer(){
        this.vendingDao = new VendingDAO();
        this.auditDAO = new AuditDAO("src/main/resources/vendingAudit.txt");
    }

    public void retrieveItemsUsingDAO(String filename) throws DAOException {
        this.items = vendingDao.getVendingItemsFromStorage(filename);
        auditDAO.fileHasBeenRead(filename);
    }

    public void writeItemsUsingDAO(String filename) throws DAOException {
        vendingDao.writeVendingItemsToStorage(filename, items);
        auditDAO.fileHasBeenWrittenTo(filename);
    }

    public HashMap<String, VendingItem> getServiceItems(){
        return items;
    }

    public void setServiceItems(HashMap<String, VendingItem> items){
        this.items = items;
    }

    public HashMap<Coins, Integer> purchaseItem(String itemName, HashMap<Coins, Integer> userPayment)
            throws DAOException, ItemNotFoundException, NoItemInventoryException, InsufficientFundsException {
        // Finding the item we want to purchase and its details

        VendingItem itemToBeBought = items.get(itemName);

        if (itemToBeBought == null){
            throw new ItemNotFoundException("Item cannot be found in the vending machine.");
        }

        BigDecimal price = itemToBeBought.getItemCost();
        int number_available = itemToBeBought.getNumberItems();

        // Counting the money the user entered and verifying if there is enough and there is item in stock.
        Payment payment = new Payment(userPayment, price, number_available);
        HashMap<Coins, Integer> returnedCoins = payment.makePayment();

        // Set item to be bought i.e. transaction completed.
        itemToBeBought.setNumberItems(number_available - 1);

        // Update Audit and return difference in price so change can be given.
        auditDAO.itemVended(itemToBeBought);
        return returnedCoins;
    }
}