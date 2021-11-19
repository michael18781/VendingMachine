package com.mthree.vendingmachine;

import java.math.BigDecimal;
import java.util.*;

public enum Coins {
    ONE_POUND(new BigDecimal("1.00")),
    FIFTY_PENCE(new BigDecimal("0.50")),
    TWENTY_PENCE(new BigDecimal("0.20")),
    TEN_PENCE(new BigDecimal("0.10")),
    FIVE_PENCE(new BigDecimal("0.05")),
    TWO_PENCE(new BigDecimal("0.02")),
    ONE_PENCE(new BigDecimal("0.01"));

    private final BigDecimal value;

    Coins(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public static Comparator<Coins> coinComparator = (s1, s2) -> (s1.value.subtract(s2.value)).intValue();
}
