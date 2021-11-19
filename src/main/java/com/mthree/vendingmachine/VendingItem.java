package com.mthree.vendingmachine;

import java.math.BigDecimal;

public class VendingItem {
    String itemName;
    BigDecimal itemCost;
    int numberItems;

    public VendingItem(String itemName, BigDecimal itemCost, int numberItems) {
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.numberItems = numberItems;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public int getNumberItems() {
        return numberItems;
    }

    public void setNumberItems(int numberItems) {
        this.numberItems = numberItems;
    }

    @Override
    public String toString() {
        return "VendingItem{" +
                "itemName='" + itemName + '\'' +
                ", itemCost=" + itemCost +
                ", numberItems=" + numberItems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) return true;

        if (!(o instanceof VendingItem)) return false;

        VendingItem v = (VendingItem) o;

        // Compare the data members and return accordingly
        return itemName.equals(v.getItemName())
                && itemCost.equals(v.getItemCost())
                && numberItems == v.getNumberItems();
    }
}
