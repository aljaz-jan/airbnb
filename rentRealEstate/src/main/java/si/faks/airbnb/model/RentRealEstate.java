package si.faks.airbnb.model;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "rent_real_estate")
@NamedQueries(value =
    {
        @NamedQuery(name = "RentRealEstate.getAll", query = "SELECT r FROM rent_real_estate r")
    })
@UuidGenerator(name = "idGenerator")
public class RentRealEstate {

    @Id
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "real_estate_id")
    private String realEstateId;

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

    public String getRealEstateId() {
        return realEstateId;
    }

    public void setRealEstateId(String realEstateId) {
        this.realEstateId = realEstateId;
    }
}
