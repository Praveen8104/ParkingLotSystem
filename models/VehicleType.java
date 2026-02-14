package models;

public class VehicleType {
    private final String name;
    private final double ratePerHour;

    public VehicleType(String name, double ratePerHour) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle type name is required");
        }
        if (ratePerHour <= 0) {
            throw new IllegalArgumentException("Rate per hour must be positive");
        }
        this.name = name.trim().toUpperCase();
        this.ratePerHour = ratePerHour;
    }

    public String getName() { return name; }
    public double getRatePerHour() { return ratePerHour; }
}
