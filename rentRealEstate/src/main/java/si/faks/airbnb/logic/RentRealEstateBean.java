package si.faks.airbnb.logic;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.faks.airbnb.model.RentRealEstate;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
public class RentRealEstateBean {

    @PersistenceContext(unitName = "rent-real-estate-jpa")
    private EntityManager em;


    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }

    public List<RentRealEstate> getRentedRealEstates(){

        Query query = em.createNamedQuery("RentRealEstate.getAll", RentRealEstate.class);

        return query.getResultList();

    }

    public RentRealEstate getRentedRealEstate(String rentRealEstateId) {

        RentRealEstate rentRealEstate = em.find(RentRealEstate.class, rentRealEstateId);

        if (rentRealEstate == null) {
            throw new NotFoundException();
        }

        return rentRealEstate;
    }

    public RentRealEstate createRentRealEstate(RentRealEstate rentRealEstate) {

        try {
            beginTx();
            em.persist(rentRealEstate);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return rentRealEstate;
    }

    public RentRealEstate putRentRealEstate(String rentRealEstateId, RentRealEstate rentRealEstate) {

        RentRealEstate r = em.find(RentRealEstate.class, rentRealEstateId);

        if (r == null) {
            return null;
        }

        try {
            beginTx();
            rentRealEstate.setId(r.getId());
            rentRealEstate = em.merge(rentRealEstate);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return rentRealEstate;
    }

    public boolean deleteRentRealEstate(String rentRealEstateId) {

        RentRealEstate rentRealEstate = em.find(RentRealEstate.class, rentRealEstateId);

        if (rentRealEstate != null) {
            try {
                beginTx();
                em.remove(rentRealEstate);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }

    public List<RentRealEstate> getRentRealEstateFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, RentRealEstate.class, queryParameters);
    }

    public List<RentRealEstate> getRentedRealEstateListForUserId(final String userId) {
        final List<RentRealEstate> rentRealEstateList = em.createNamedQuery("RentRealEstate.getAllForUserId")
                .setParameter("userId", userId)
                .getResultList();
        return rentRealEstateList;
    }

}