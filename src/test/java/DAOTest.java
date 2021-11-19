import com.mthree.vendingmachine.VendingDAO;
import com.mthree.vendingmachine.VendingItem;
import com.mthree.vendingmachine.exceptions.*;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DAOTest {
    HashMap<String, VendingItem> purchaseItems = new HashMap<>();

    public DAOTest(){
        purchaseItems.put("A1", new VendingItem("A1", new BigDecimal("1.34"), 5));
        purchaseItems.put("A2", new VendingItem("A2", new BigDecimal("1.11"), 2));
        purchaseItems.put("A3", new VendingItem("A3", new BigDecimal("0.90"), 4));
        purchaseItems.put("B1", new VendingItem("B1", new BigDecimal("2.10"), 2));
        purchaseItems.put("B2", new VendingItem("B2", new BigDecimal("45.0"), 1));
        purchaseItems.put("B3", new VendingItem("B3", new BigDecimal("1.55"), 0));
    }

    @Test
    public void testWriting(){
        VendingDAO dao = new VendingDAO();

        HashSet<String> expectedLines = new HashSet<>();
        for(VendingItem item: purchaseItems.values()){
            expectedLines.add(item.getItemName() + "," + item.getItemCost() + "," + item.getNumberItems());
        }

        try {
            dao.writeVendingItemsToStorage("src/main/resources/vendingTestWrite.csv", purchaseItems);
        } catch (DAOException ignored) {}

        String line;
        HashSet<String> lines = new HashSet<>();

        try {
            FileReader fr = new FileReader("src/main/resources/vendingTestWrite.csv");
            BufferedReader br = new BufferedReader(fr);

            while ((line = br.readLine()) != null)   // Returns a Boolean value
            {
                // Split each line with delimiter and then create a DVD object
                lines.add(line);
            }
        } catch (IOException ignored){}

        assertEquals(expectedLines, lines);
    }

    @Test
    public void testReading(){
        VendingDAO dao = new VendingDAO();
        try {
            HashMap<String, VendingItem> readItems = dao.getVendingItemsFromStorage("src/main/resources/vendingTestRead.csv");
            assertEquals(purchaseItems, readItems);
        } catch (DAOException ignored) {}
    }
}
