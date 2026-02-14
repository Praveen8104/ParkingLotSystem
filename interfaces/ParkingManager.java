package interfaces;

import models.Ticket;

public interface ParkingManager {
    void addFloor(int floor);
    void addFloor(String floorName);
    void addSlots(int floor, String type, int count, double rate);
    void addSlots(String floorName, String type, int count, double rate);
    Ticket park(String vehicleNo, String type);
    Ticket unpark(String ticketId);
}
