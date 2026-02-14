package services;

import interfaces.*;
import models.*;
import exceptions.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingManagerImple implements ParkingManager {

    private final ParkingLot lot;
    private final SlotAllocator allocator;
    private final FeeCalculator calculator;
    private final List<Ticket> active = new ArrayList<>();
    private int counter = 1;

    public ParkingManagerImple(ParkingLot lot, SlotAllocator a, FeeCalculator c) {
        this.lot = lot;
        this.allocator = a;
        this.calculator = c;
    }

    public void addFloor(int floor) {
        lot.addFloor(floor);
    }
    
    public void addFloor(String floorName) {
        lot.addFloor(floorName);
    }

    public void addSlots(int floor, String type, int count, double rate) {
        Floor f = lot.getFloor(floor);
        if (f == null) throw new InvalidFloorException("Floor not found");

        VehicleType vt = lot.getOrCreateType(type, rate);
        f.addSlots(vt, count);
    }
    
    public void addSlots(String floorName, String type, int count, double rate) {
        Floor f = lot.getFloor(floorName);
        if (f == null) throw new InvalidFloorException("Floor '" + floorName + "' not found");

        VehicleType vt = lot.getOrCreateType(type, rate);
        f.addSlots(vt, count);
    }

    public Ticket park(String vehicleNo, String typeName) {
        VehicleType type = lot.getType(typeName);
        if (type == null) throw new InvalidVehicleTypeException("Invalid vehicle type");

        ParkingSlot slot = allocator.allocate(lot, type);
        if (slot == null) throw new ParkingFullException("Parking Full");

        Vehicle v = new Vehicle(vehicleNo, type);
        slot.park(v);

        Floor floor = null;
        for (Floor f : lot.getFloors())
            if (f.getSlot(slot.getSlotNumber()) != null) floor = f;

        String id = "T" + counter++;
        Ticket t = new Ticket(id, vehicleNo, type.getName(),
                floor.getFloorNumber(), slot.getSlotNumber(),
                type.getRatePerHour(), System.currentTimeMillis());

        active.add(t);
        return t;
    }

    public Ticket unpark(String id) {
        int index = findActiveTicketIndex(id);
        if (index == -1) throw new InvalidTicketException("Invalid ticket");
        Ticket t = active.get(index);

        Floor f = lot.getFloor(t.getFloor());
        ParkingSlot s = f.getSlot(t.getSlot());
        s.unpark();

        long now = System.currentTimeMillis();
        double amount = calculator.calculate(t.getInTime(), now, t.getRate());
        t.close(now, amount);

        active.remove(index);
        return t;
    }

    private int findActiveTicketIndex(String id) {
        for (int i = 0; i < active.size(); i++) {
            if (active.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
