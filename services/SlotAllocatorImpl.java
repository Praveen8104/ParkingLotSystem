package services;

import interfaces.SlotAllocator;
import models.*;

public class SlotAllocatorImpl implements SlotAllocator {

    public ParkingSlot allocate(ParkingLot lot, VehicleType type) {
        for (Floor f : lot.getFloors()) {
            for (ParkingSlot s : f.getSlots()) {
                if (s.isFree() && s.supports(type))
                    return s;
            }
        }
        return null;
    }
}
