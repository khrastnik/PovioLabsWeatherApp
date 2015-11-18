package com.weatherapp.managers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by Klemen on 27.2.2015.
 */
public class RoundNumberManager {

    // global
    private static int DECIMAL_PLACES = 1;

    /**
     * Round number and return double value
     *
     * @param notRoundedValue notRoundedValue
     * @return
     */
    public double round(double notRoundedValue, int places) {

        if (places < 0) throw new IllegalArgumentException();

        return new BigDecimal(notRoundedValue).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * Round to one decimal values
     *
     * @param notRoundedValue notRoundedValue
     * @return rounded value
     */
    public double roundToOneDecimal(double notRoundedValue) {
        return new BigDecimal(notRoundedValue).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * Round to one decimal values
     *
     * @param notRoundedValue notRoundedValue
     * @return rounded value
     */
    public double roundToTwoDecimal(double notRoundedValue) {
        return new BigDecimal(notRoundedValue).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * round number and return double value
     *
     * @param notRoundedValue notRoundedValue
     * @return
     */
    public double round(double notRoundedValue) {
        return new BigDecimal(notRoundedValue).setScale(DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * round number and return string
     *
     * @param value
     * @return
     */
    public String format(double value) {
        return String.format("%." + DECIMAL_PLACES + "f", value);
    }

    public String format(double value, int decDot) {
        return String.format("%." + decDot + "f", value);
    }
}
