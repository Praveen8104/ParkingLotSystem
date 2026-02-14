package interfaces;

public interface FeeCalculator {
    double calculate(long inTime, long outTime, double rate);
}
