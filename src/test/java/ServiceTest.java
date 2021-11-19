import com.mthree.vendingmachine.Coins;
import com.mthree.vendingmachine.Payment;
import com.mthree.vendingmachine.VendingItem;
import com.mthree.vendingmachine.VendingServiceLayer;
import com.mthree.vendingmachine.exceptions.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ServiceTest {
    HashMap<String, VendingItem> purchaseItems = new HashMap<>();
    HashMap<Coins, Integer> amountEntered = new HashMap<>();
    VendingServiceLayer vs = new VendingServiceLayer();

    public ServiceTest() {
        purchaseItems.put("A1", new VendingItem("A1", new BigDecimal("1.34"), 5));
        vs.setServiceItems(purchaseItems);

        // ENTERED 5.23
        amountEntered.put(Coins.ONE_POUND, 5);
        amountEntered.put(Coins.FIFTY_PENCE, 0);
        amountEntered.put(Coins.FIVE_PENCE, 0);
        amountEntered.put(Coins.ONE_PENCE, 1);
        amountEntered.put(Coins.TWO_PENCE, 1);
        amountEntered.put(Coins.TEN_PENCE, 0);
        amountEntered.put(Coins.TWENTY_PENCE, 1);
    }

    @Test
    public void getCoinChangeDue() {
        HashMap<Coins, Integer> coinChange = new HashMap<>();
        coinChange.put(Coins.ONE_POUND, 1);
        coinChange.put(Coins.FIFTY_PENCE, 1);
        coinChange.put(Coins.TWENTY_PENCE, 0);
        coinChange.put(Coins.TEN_PENCE, 0);
        coinChange.put(Coins.FIVE_PENCE, 1);
        coinChange.put(Coins.TWO_PENCE, 0);
        coinChange.put(Coins.ONE_PENCE, 1);

        // Tests that the BigDecimal change value is converted into coins properly.
        BigDecimal testVal = new BigDecimal("1.56");
        assertEquals(coinChange, Payment.getCoinSet(testVal));
    }

    @Test
    public void getUserPaymentValue(){
        // Checking that the method to convert a HashMap of coins into decimal works.
        BigDecimal value = new BigDecimal("5.23");
        assertEquals(Payment.getUserPaymentValue(amountEntered), value);
    }

    @Test
    public void purchaseItemQuantityUpdate() {
        try {
            // Testing that the item quantity is updated correctly.
            int originalQuantity = vs.getServiceItems().get("A1").getNumberItems();
            vs.purchaseItem("A1", amountEntered);

            int newQuantity = vs.getServiceItems().get("A1").getNumberItems();
            assertEquals(originalQuantity - 1, newQuantity);

        } catch (DAOException | NoItemInventoryException | InsufficientFundsException | ItemNotFoundException ignored){}
    }

    @Test
    public void purchaseItemNoItemFound(){
        // Testing that the item quantity is updated correctly.
        assertThrows(ItemNotFoundException.class, () -> vs.purchaseItem("C1", amountEntered));

    }

    @Test
    public void validatePurchaseNotEnoughMoney() {
        // Testing that the correct com.mthree.vendingmachine.exceptions are thrown when not enough money
        Payment pay = new Payment(amountEntered, new BigDecimal("6.40"), 1);
        assertThrows(InsufficientFundsException.class, pay::validatePurchase);
    }

    @Test
    public void validatePurchaseNoItemInStock() {
        // Testing that the correct com.mthree.vendingmachine.exceptions are thrown when the item is not in stock
        Payment pay = new Payment(amountEntered, new BigDecimal("3.40"), 0);
        assertThrows(NoItemInventoryException.class, pay::validatePurchase);
    }

}
