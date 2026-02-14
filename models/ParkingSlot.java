package models;

public class ParkingSlot {
    private final int slotNumber;
    private final VehicleType supportedType;
    private Vehicle parkedVehicle;

    public ParkingSlot(int slotNumber, VehicleType type) {
        if (type == null) {
            throw new IllegalArgumentException("Supported vehicle type is required");
        }
        this.slotNumber = slotNumber;
        this.supportedType = type;
    }

    public boolean isFree() { return parkedVehicle == null; }
    public boolean isOccupied() { return parkedVehicle != null; }

    public boolean supports(VehicleType type) {
        if (type == null) {
            return false;
        }
        return supportedType.getName().equals(type.getName());
    }

    public void park(Vehicle v) {
        if (v == null) {
            throw new IllegalArgumentException("Vehicle is required");
        }
        if (!isFree()) {
            throw new IllegalStateException("Slot is already occupied");
        }
        if (!supports(v.getType())) {
            throw new IllegalArgumentException("Vehicle type not supported by this slot");
        }
        this.parkedVehicle = v;
    }

    public void unpark() {
        if (isFree()) {
            throw new IllegalStateException("Slot is already free");
        }
        this.parkedVehicle = null;
    }

    public int getSlotNumber() { return slotNumber; }
    public VehicleType getSupportedType() { return supportedType; }
    public Vehicle getParkedVehicle() { return parkedVehicle; }
}
