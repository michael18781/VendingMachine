package com.mthree.vendingmachine.exceptions;

import com.mthree.vendingmachine.Coins;
import java.io.IOException;
import java.util.HashMap;

public class InsufficientFundsException extends IOException {
    private final HashMap<Coins, Integer> returnedCoins;

    public InsufficientFundsException(String msg, HashMap<Coins, Integer> returnedCoins){
        super(msg);
        this.returnedCoins = returnedCoins;
    }

    public HashMap<Coins, Integer> getReturnedCoins(){
        return returnedCoins;
    }
}
