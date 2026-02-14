import interfaces.*;
import models.*;
import services.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        clearConsole();
        
        ParkingLot lot = new ParkingLot();
        ParkingManager manager = new ParkingManagerImple(
                lot,
                new SlotAllocatorImpl(),
                new FeeCalculatorImpl()
        );

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("         PARKING LOT MANAGEMENT SYSTEM");
                System.out.println("=".repeat(50));
                System.out.println("1. Add Floor");
                System.out.println("2. Add Slots");
                System.out.println("3. Park Vehicle");
                System.out.println("4. Unpark Vehicle");
                System.out.println("0. Exit");
                System.out.println("=".repeat(50));
                System.out.print("Enter your choice: ");

                try {
                    int c = sc.nextInt();
                    sc.nextLine();
                    
                    switch (c) {
                        case 1 -> {
                            System.out.print("Enter floor number or name (e.g., 1, ground, first, basement): ");
                            String floorInput = sc.nextLine().trim();
                            try {
                                int floorNum = Integer.parseInt(floorInput);
                                manager.addFloor(floorNum);
                                System.out.println("SUCCESS: Floor " + floorNum + " added successfully!");
                            } catch (NumberFormatException e) {
                                manager.addFloor(floorInput);
                                System.out.println("SUCCESS: Floor '" + floorInput + "' added successfully!");
                            }
                        }
                        case 2 -> {
                            System.out.print("Enter floor number or name: ");
                            String floorInput = sc.nextLine().trim();
                            System.out.print("Enter vehicle type: ");
                            String vehicleType = sc.nextLine();
                            System.out.print("Enter number of slots: ");
                            int count = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Enter rate per hour: ");
                            double rate = sc.nextDouble();
                            sc.nextLine();
                            
                            try {
                                int floorNum = Integer.parseInt(floorInput);
                                manager.addSlots(floorNum, vehicleType, count, rate);
                                System.out.println("SUCCESS: Added " + count + " " + vehicleType + " slots to floor " + floorNum);
                            } catch (NumberFormatException e) {
                                manager.addSlots(floorInput, vehicleType, count, rate);
                                System.out.println("SUCCESS: Added " + count + " " + vehicleType + " slots to floor '" + floorInput + "'");
                            }
                        }
                        case 3 -> {
                            System.out.print("Enter vehicle number: ");
                            String vehicleNo = sc.nextLine();
                            System.out.print("Enter vehicle type: ");
                            String vehicleType = sc.nextLine();
                            Ticket ticket = manager.park(vehicleNo, vehicleType);
                            ticket.displayParkingDetails();
                        }
                        case 4 -> {
                            System.out.print("Enter ticket ID: ");
                            String ticketId = sc.nextLine();
                            Ticket ticket = manager.unpark(ticketId);
                            ticket.displayUnparkingDetails();
                        }
                        case 0 -> {
                            System.out.println("Thank you for using Parking Lot System!");
                            System.exit(0);
                        }
                        default -> System.out.println("ERROR: Invalid choice! Please try again.");
                    }
                } catch (Exception e) {
                    System.out.println("ERROR: " + e.getMessage());
                    sc.nextLine();
                }
                
                System.out.println("\nPress Enter to continue...");
                sc.nextLine();
                clearConsole();
            }
        }
    }
    
    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ignored) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }
}
