package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {
    private final String id;
    private final String vehicleNumber;
    private final String vehicleType;
    private final int floor;
    private final int slot;
    private final double rate;
    private final long inTime;
    private long outTime;
    private double amount;

    public Ticket(String id, String vehicleNumber, String vehicleType,
                  int floor, int slot, double rate, long inTime) {
        this.id = id;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.floor = floor;
        this.slot = slot;
        this.rate = rate;
        this.inTime = inTime;
    }

    public void close(long outTime, double amount) {
        this.outTime = outTime;
        this.amount = amount;
    }

    public String getId() { return id; }
    public int getFloor() { return floor; }
    public int getSlot() { return slot; }
    public double getRate() { return rate; }
    public long getInTime() { return inTime; }
    public long getOutTime() { return outTime; }
    public double getAmount() { return amount; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getVehicleType() { return vehicleType; }
    public boolean isClosed() { return outTime > 0; }
    public long getDurationMillis() { return isClosed() ? (outTime - inTime) : 0L; }
    public long getDurationHoursRoundedUp() {
        if (!isClosed()) {
            return 0L;
        }
        long duration = outTime - inTime;
        return (long) Math.ceil(duration / 3600000.0);
    }
    
    public void displayParkingDetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           PARKING TICKET");
        System.out.println("=".repeat(40));
        System.out.println("Ticket ID: " + id);
        System.out.println("Vehicle Number: " + vehicleNumber);
        System.out.println("Vehicle Type: " + vehicleType);
        System.out.println("Location: Floor " + floor + ", Slot " + slot);
        System.out.println("Rate: $" + rate + "/hour");
        System.out.println("Entry Time: " + sdf.format(new Date(inTime)));
        System.out.println("=".repeat(40));
        System.out.println("     Vehicle Parked Successfully!");
        System.out.println("=".repeat(40) + "\n");
    }
    
    public void displayUnparkingDetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        long duration = outTime - inTime;
        long hours = (long) Math.ceil(duration / 3600000.0);
        
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           PARKING RECEIPT");
        System.out.println("=".repeat(40));
        System.out.println("Ticket ID: " + id);
        System.out.println("Vehicle Number: " + vehicleNumber);
        System.out.println("Vehicle Type: " + vehicleType);
        System.out.println("Location: Floor " + floor + ", Slot " + slot);
        System.out.println("Entry Time: " + sdf.format(new Date(inTime)));
        System.out.println("Exit Time: " + sdf.format(new Date(outTime)));
        System.out.println("Duration: " + hours + " hour(s)");
        System.out.println("Rate: $" + rate + "/hour");
        System.out.println("Total Amount: $" + amount);
        System.out.println("=".repeat(40));
        System.out.println("     Vehicle Unparked Successfully!");
        System.out.println("=".repeat(40) + "\n");
    }
}
