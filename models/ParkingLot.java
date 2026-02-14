package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import exceptions.RateMismatchException;

public class ParkingLot {
    private final List<Floor> floors = new ArrayList<>();
    private final List<FloorNameMapping> floorNameMappings = new ArrayList<>();
    private final List<VehicleType> vehicleTypes = new ArrayList<>();
    private int nextFloorNumber = 1;

    private static class FloorNameMapping {
        private final String key;
        private final int number;
        private final String displayName;

        private FloorNameMapping(String key, int number, String displayName) {
            this.key = key;
            this.number = number;
            this.displayName = displayName;
        }
    }

    public void addFloor(int floor) {
        if (findFloorByNumber(floor) != null) {
            return;
        }
        floors.add(new Floor(floor));
    }
    
    public void addFloor(String floorName) {
        String key = normalizeFloorName(floorName);
        int existingNum = getFloorNumberByKey(key);
        if (existingNum != -1) {
            return;
        }

        int floorNumber = nextFloorNumber++;
        upsertFloor(floorNumber);
        floorNameMappings.add(new FloorNameMapping(key, floorNumber, floorName.trim()));
    }
    
    public void addFloor(String floorName, int floorNumber) {
        if (findFloorByNumber(floorNumber) != null) {
            return;
        }

        floors.add(new Floor(floorNumber));
        floorNameMappings.add(new FloorNameMapping(normalizeFloorName(floorName), floorNumber, floorName.trim()));
        if (floorNumber >= nextFloorNumber) {
            nextFloorNumber = floorNumber + 1;
        }
    }

    public Floor getFloor(int floor) { 
        return findFloorByNumber(floor); 
    }
    
    public Floor getFloor(String floorName) {
        int floorNum = getFloorNumberByKey(normalizeFloorName(floorName));
        return floorNum != -1 ? findFloorByNumber(floorNum) : null;
    }
    
    public int getFloorNumber(String floorName) {
        return getFloorNumberByKey(normalizeFloorName(floorName));
    }
    
    public String getFloorName(int floorNumber) {
        return getFloorNameByNumber(floorNumber);
    }

    public Collection<Floor> getFloors() { return floors; }

    public VehicleType getOrCreateType(String name, double rate) {
        String key = normalizeVehicleTypeName(name);
        VehicleType existing = findVehicleTypeByName(key);
        if (existing != null) {
            if (existing.getRatePerHour() != rate)
                throw new RateMismatchException("Rate mismatch for same vehicle type");
            return existing;
        }
        VehicleType t = new VehicleType(key, rate);
        vehicleTypes.add(t);
        return t;
    }

    public VehicleType getType(String name) {
        return findVehicleTypeByName(normalizeVehicleTypeName(name));
    }

    public boolean hasFloor(int floor) { return findFloorByNumber(floor) != null; }

    public boolean hasFloor(String floorName) {
        return getFloorNumberByKey(normalizeFloorName(floorName)) != -1;
    }

    public int getFloorCount() { return floors.size(); }

    public String getFloorDisplayName(int floorNumber) {
        String name = getFloorNameByNumber(floorNumber);
        return name != null ? name : String.valueOf(floorNumber);
    }

    public Collection<VehicleType> getVehicleTypes() { return vehicleTypes; }

    private Floor findFloorByNumber(int floorNumber) {
        for (Floor floor : floors) {
            if (floor.getFloorNumber() == floorNumber) {
                return floor;
            }
        }
        return null;
    }

    private void upsertFloor(int floorNumber) {
        for (int i = 0; i < floors.size(); i++) {
            if (floors.get(i).getFloorNumber() == floorNumber) {
                floors.set(i, new Floor(floorNumber));
                return;
            }
        }
        floors.add(new Floor(floorNumber));
    }

    private int getFloorNumberByKey(String key) {
        for (int i = floorNameMappings.size() - 1; i >= 0; i--) {
            FloorNameMapping mapping = floorNameMappings.get(i);
            if (mapping.key.equals(key)) {
                return mapping.number;
            }
        }
        return -1;
    }

    private String getFloorNameByNumber(int floorNumber) {
        for (int i = floorNameMappings.size() - 1; i >= 0; i--) {
            FloorNameMapping mapping = floorNameMappings.get(i);
            if (mapping.number == floorNumber) {
                return mapping.displayName;
            }
        }
        return null;
    }

    private VehicleType findVehicleTypeByName(String name) {
        for (VehicleType type : vehicleTypes) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    private static String normalizeFloorName(String floorName) {
        if (floorName == null || floorName.trim().isEmpty()) {
            throw new IllegalArgumentException("Floor name is required");
        }
        return floorName.trim().toLowerCase();
    }

    private static String normalizeVehicleTypeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle type name is required");
        }
        return name.trim().toUpperCase();
    }
}
