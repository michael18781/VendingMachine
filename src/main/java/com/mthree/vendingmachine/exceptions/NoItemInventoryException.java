package com.mthree.vendingmachine.exceptions;

import java.io.IOException;

public class NoItemInventoryException extends IOException {
    public NoItemInventoryException(String msg){super(msg);}
}