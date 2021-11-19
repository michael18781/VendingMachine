package com.mthree.vendingmachine.exceptions;

import java.io.IOException;

public class ItemNotFoundException extends IOException {
    public ItemNotFoundException(String msg){
        super(msg);
    }
}
