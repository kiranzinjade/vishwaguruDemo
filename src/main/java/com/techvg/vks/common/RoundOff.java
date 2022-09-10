package com.techvg.vks.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundOff {

    public static double roundDouble(double d, int places) {

        BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static float roundFloat(float f, int places) {

        BigDecimal bigDecimal = new BigDecimal(Float.toString(f));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }

    public static double roundDouble(double d) {
        return RoundOff.roundDouble( d, 2);
    }

    public static float roundFloat(float f) {
        return RoundOff.roundFloat( f, 2);
    }
}

