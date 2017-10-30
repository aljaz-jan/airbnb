package si.faks.airbnb.logic;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.faks.airbnb.model.RealEstate;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
public class RealEstateBean {

    @PersistenceContext(unitName = "realEstate-jpa")
    private EntityManager entityManager;

    public List<RealEstate> getRealEstateList(){
        Query query = entityManager.createNamedQuery("RealEstate.getAll", RealEstate.class);
        return query.getResultList();
    }

    public RealEstate getRealEstate(final String realEstateId) {
        RealEstate customer = entityManager.find(RealEstate.class, realEstateId);

        if (customer == null) {
            throw new NotFoundException();
        }

        return customer;
    }

    public RealEstate addRealEstate(RealEstate realEstate) {
        try {
            beginTx();
            entityManager.persist(realEstate);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return realEstate;
    }

    public RealEstate updateRealEstate(final String realEstateId, RealEstate realEstate) {
        RealEstate realEstateToUpdate = entityManager.find(RealEstate.class, realEstateId);

        if (realEstateToUpdate == null) {
            return null;
        }

        try {
            beginTx();
            realEstate.setId(realEstateToUpdate.getId());
            realEstate = entityManager.merge(realEstate);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return realEstate;
    }

    public boolean deleteRealEstate(final String realEstateId) {
        RealEstate realEstate = entityManager.find(RealEstate.class, realEstateId);

        if (realEstate != null) {
            try {
                beginTx();
                entityManager.remove(realEstate);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else {
            return false;
        }
        return true;
    }

    public List<RealEstate> getRealEstateListFiltered(UriInfo uriInfo) {
        final QueryParameters queryParameters = QueryParameters.query(
                uriInfo.getRequestUri().getQuery()
        ).defaultOffset(0).build();

        final List<RealEstate> reatEstateList = JPAUtils.queryEntities(entityManager, RealEstate.class, queryParameters);

        return reatEstateList;
    }

    private void beginTx() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

}
