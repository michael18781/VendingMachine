package com.mthree.vendingmachine;

import com.mthree.vendingmachine.exceptions.*;
import com.mthree.vendingmachine.interfaces.VendingControllerInterface;
import com.mthree.vendingmachine.interfaces.VendingServiceLayerInterface;
import com.mthree.vendingmachine.interfaces.VendingViewInterface;

import java.util.HashMap;

public class VendingController implements VendingControllerInterface {
    private final VendingViewInterface viewInterface;
    private final VendingServiceLayerInterface serviceInterface;

    public VendingController(){
        this.serviceInterface = new VendingServiceLayer();
        this.viewInterface = new VendingView();
    }

    public void mainControlLoop() {
        boolean exitStatus = false;
        startUpRoutine();

        do {

            MenuOptions menuOption = viewInterface.getMenuOption();

            if (menuOption == MenuOptions.EXIT) {
                exitStatus = true;
            }

            else if (menuOption == MenuOptions.PURCHASE_ITEM) {
                HashMap<Coins, Integer> userMoney = viewInterface.getMoneyIn();
                String itemName = viewInterface.getItemNameFromUser();

                try {
                    // Purchases the item. purchaseItem returns the HashMap of coins as change.
                    viewInterface.displayCoinsReturned(serviceInterface.purchaseItem(itemName, userMoney));
                } catch (InsufficientFundsException | ItemNotFoundException | NoItemInventoryException | DAOException e){
                    viewInterface.displayExceptionFound(e);
                }
            }

            else if (menuOption == MenuOptions.VIEW_ITEMS) {
                viewInterface.displayVendingItems(serviceInterface.getServiceItems());
            }

        } while (!exitStatus);
        exitRoutine();
    }

    public void startUpRoutine(){
        try {

            // Use service layer to get the items
            String filenameToOpen = viewInterface.getFileNameToOpen();

            // Only called on start up. Loads items into service.items hashmap.
            serviceInterface.retrieveItemsUsingDAO(filenameToOpen);

            // Forward onto the view to display
            viewInterface.displayVendingItems(serviceInterface.getServiceItems());

        } catch (DAOException ex){
            // In case anything went wrong earlier
            viewInterface.displayExceptionFound(ex);
            startUpRoutine();
        }
    }

    public void exitRoutine(){
        try {
            String filenameToClose = viewInterface.getFileNameToSaveTo();
            serviceInterface.writeItemsUsingDAO(filenameToClose);
        } catch (DAOException e){
            viewInterface.displayExceptionFound(e);
        }
        System.exit(0);
    }
}