package com.mthree.vendingmachine;

public enum MenuOptions {
    PURCHASE_ITEM(1), VIEW_ITEMS(2), EXIT(3), INVALID(0);

    private final int value;

    MenuOptions(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
