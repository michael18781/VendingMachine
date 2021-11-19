package com.mthree.vendingmachine.exceptions;

import java.io.IOException;

public class DAOException extends IOException {
    public DAOException(String msg) {
        super(msg);
    }
}