package si.faks.airbnb.model;

public class RealEstate {

    private String id;
    private String userId;
    private String name;
    //private Location location;
    private int availableBeds;
    private Double price;

    public RealEstate(String id, String userId, String name, int availableBeds, Double price) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.availableBeds = availableBeds;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }*/

    public int getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(int availableBeds) {
        this.availableBeds = availableBeds;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}

