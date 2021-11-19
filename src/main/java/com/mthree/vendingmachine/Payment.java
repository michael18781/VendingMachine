package com.mthree.vendingmachine;

import com.mthree.vendingmachine.exceptions.InsufficientFundsException;
import com.mthree.vendingmachine.exceptions.NoItemInventoryException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Payment {
    private final HashMap<Coins, Integer> userPayment;
    private final BigDecimal userPaymentValue;
    private final BigDecimal price;
    private final int number_available;

    public Payment(HashMap<Coins, Integer> userPayment, BigDecimal price, int number_available){
        this.userPayment = userPayment;
        this.userPaymentValue = getUserPaymentValue(userPayment);
        this.price = price;
        this.number_available = number_available;
    }

    public void validatePurchase() throws NoItemInventoryException, InsufficientFundsException {
        if (number_available < 1) {
            throw new NoItemInventoryException("No more items of this type left.");
        }

        if (getUserPaymentValue(userPayment).compareTo(price) < 0){
            // i.e. user did not enter enough money.
            throw new InsufficientFundsException("You did not enter enough money. It will be returned.", userPayment);
        }
    }

    public HashMap<Coins, Integer> makePayment() throws NoItemInventoryException, InsufficientFundsException {
        validatePurchase();
        BigDecimal changeDue = userPaymentValue.subtract(price);
        return getCoinSet(changeDue);
    }

    public static HashMap<Coins, Integer> getCoinSet(BigDecimal balanceToPay) {
        // Code to deduce the number of each coin type to return to the user.
        HashMap<Coins, Integer> change = new HashMap<>();

        // Want to make sure the coin values are sorted from largest to smallest
        ArrayList<Coins> coins = new ArrayList<>();
        Collections.addAll(coins, Coins.values());
        coins.sort(Coins.coinComparator);

        for (Coins coinType: coins){
            // Find out the number of whole coins of this type that we can return
            int numberDue = balanceToPay.divide(coinType.getValue(), RoundingMode.DOWN).intValue();
            change.put(coinType, numberDue);

            // Update the remaining balance we need to give back.
            balanceToPay = balanceToPay.subtract(BigDecimal.valueOf(numberDue).multiply(coinType.getValue()));
        }

        return change;
    }

    public static BigDecimal getUserPaymentValue(HashMap<Coins, Integer> userPayment){
        // Converting the set of coins into a value.
        BigDecimal userPaymentValue = new BigDecimal(0);

        for(Coins coin: userPayment.keySet()){
            // Takes the set of coins and finds how many of each we have. Then updates the counter.
            BigDecimal numberCoins = BigDecimal.valueOf(userPayment.get(coin));
            userPaymentValue = userPaymentValue.add(coin.getValue().multiply(numberCoins));
        }

        return userPaymentValue;
    }
}
