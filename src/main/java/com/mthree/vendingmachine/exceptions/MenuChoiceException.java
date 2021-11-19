package com.mthree.vendingmachine.exceptions;

import java.io.IOException;

public class MenuChoiceException extends IOException {
    public MenuChoiceException(String msg){
        super(msg);
    }
}