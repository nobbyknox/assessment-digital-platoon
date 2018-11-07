package com.nobbyknox.platoon.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalWrapper {
    private BigDecimal decimal;

    public BigDecimalWrapper(double value) {
        this.decimal = new BigDecimal(value, new MathContext(8, RoundingMode.HALF_UP));
    }

    public BigDecimal getValue() {
        return this.decimal.setScale(2, RoundingMode.HALF_UP);
    }
}
