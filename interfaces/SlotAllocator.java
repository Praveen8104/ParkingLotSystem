package interfaces;

import models.*;

public interface SlotAllocator {
    ParkingSlot allocate(ParkingLot lot, VehicleType type);
}
