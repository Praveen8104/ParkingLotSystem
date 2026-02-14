package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Floor {
    private final int floorNumber;
    private final List<ParkingSlot> slots = new ArrayList<>();
    private int nextSlot = 1;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void addSlots(VehicleType type, int count) {
        if (type == null) {
            throw new IllegalArgumentException("Vehicle type is required");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Slot count must be positive");
        }
        for (int i = 0; i < count; i++) {
            slots.add(new ParkingSlot(nextSlot, type));
            nextSlot++;
        }
    }

    public Collection<ParkingSlot> getSlots() { return slots; }

    public ParkingSlot getSlot(int slot) {
        if (slot <= 0 || slot > slots.size()) {
            return null;
        }
        return slots.get(slot - 1);
    }

    public int getFloorNumber() { return floorNumber; }

    public int getSlotCount() { return slots.size(); }

    public int getAvailableSlotCount(VehicleType type) {
        int count = 0;
        for (ParkingSlot slot : slots) {
            if (slot.isFree() && slot.supports(type)) {
                count++;
            }
        }
        return count;
    }
}
