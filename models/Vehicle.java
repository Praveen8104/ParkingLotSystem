package models;

public class Vehicle {
    private final String number;
    private final VehicleType type;

    public Vehicle(String number, VehicleType type) {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle number is required");
        }
        if (type == null) {
            throw new IllegalArgumentException("Vehicle type is required");
        }
        this.number = number.trim();
        this.type = type;
    }

    public String getNumber() { return number; }
    public VehicleType getType() { return type; }
}

