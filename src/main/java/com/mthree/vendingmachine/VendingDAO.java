package com.mthree.vendingmachine;

import com.mthree.vendingmachine.exceptions.DAOException;
import com.mthree.vendingmachine.interfaces.VendingDAOInterface;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;

public class VendingDAO implements VendingDAOInterface {

    public HashMap<String, VendingItem> getVendingItemsFromStorage(String filename) throws DAOException {
        HashMap<String, VendingItem> newCollection = new HashMap<>();
        String line;

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            while ((line = br.readLine()) != null)   // Returns a Boolean value
            {
                // Split each line with delimiter and then create a VendingItem object
                String[] itemData = line.split(",");

                VendingItem itemRead = new VendingItem(itemData[0], new BigDecimal(itemData[1]), Integer.parseInt(itemData[2]));
                newCollection.put(itemRead.getItemName(), itemRead);
            }
        } catch (IOException e){
            throw new DAOException("There has been an error reading the file.");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            throw new DAOException("The file format is incorrect.");
        }

        return newCollection;
    }

    public void writeVendingItemsToStorage(String filename, HashMap<String, VendingItem> vendingItems) throws DAOException {
        // Append false means that we re-write the file each time
        try {
            FileWriter fw = new FileWriter(filename, false);
            BufferedWriter bw = new BufferedWriter(fw);

            for (VendingItem item : vendingItems.values()) {
                bw.write(item.getItemName() + "," +
                        item.getItemCost() + "," +
                        item.getNumberItems() + "\n");
            }

            bw.close();
            fw.close();
        } catch (IOException e){
            throw new DAOException("There has been an error writing to the file.");
        }
    }

}
