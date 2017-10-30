package si.faks.airbnb.model;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;

@Entity(name = "real_estate")
@NamedQueries(value = {
    @NamedQuery(name = "RealEstate.getAll", query = "SELECT re FROM real_estate re")
})
@UuidGenerator(name = "idGenerator")
public class RealEstate {

    @Id
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Id
    private String userId;

    @Column(name = "name")
    private String name;

    //@Column(name = "locationId")
    @ManyToOne
    @JoinColumn(name="locationId", nullable=false)
    private Location location;

    @Column(name = "availableBeds")
    private int availableBeds;

    @Column(name = "price")
    private Double price;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

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
