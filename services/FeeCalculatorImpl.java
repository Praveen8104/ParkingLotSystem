package services;

import interfaces.FeeCalculator;

public class FeeCalculatorImpl implements FeeCalculator {

    public double calculate(long inTime, long outTime, double rate) {
        long hours = (long) Math.ceil((outTime - inTime) / 3600000.0);
        if (hours <= 0) hours = 1;
        return hours * rate;
    }
}
