package model;

import java.io.Serializable;

public class ShelfLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String locationId;
    private String rackNumber;
    private String shelfNumber;
    private String floor;

    // Constructor
    public ShelfLocation(String locationId, String rackNumber,
                         String shelfNumber, String floor) {

        this.locationId = locationId;
        this.rackNumber = rackNumber;
        this.shelfNumber = shelfNumber;
        this.floor = floor;
    }

    // Getters and Setters
    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getRackNumber() {
        return rackNumber;
    }

    public void setRackNumber(String rackNumber) {
        this.rackNumber = rackNumber;
    }

    public String getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(String shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "Floor " + floor
                + ", Rack " + rackNumber
                + ", Shelf " + shelfNumber;
    }
}
