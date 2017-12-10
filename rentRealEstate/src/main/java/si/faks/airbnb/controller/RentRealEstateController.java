package si.faks.airbnb.controller;

import si.faks.airbnb.logic.RentRealEstateBean;
import si.faks.airbnb.model.RentRealEstate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("/rentRealEstate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RentRealEstateController {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private RentRealEstateBean rentRealEstateBean;

    @GET
    public Response getRealEstates() {
        List<RentRealEstate> rentRealEstates = rentRealEstateBean.getRentedRealEstates();

        return Response.ok(rentRealEstates).build();
    }

    @GET
    @Path("/{rentRealEstateId}")
    public Response getRealEstate(@PathParam("rentRealEstateId") String rentRealEstateId) {
        RentRealEstate rentRealEstate = rentRealEstateBean.getRentedRealEstate(rentRealEstateId);

        if (rentRealEstate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(rentRealEstate).build();
    }

    @GET
    @Path("/user/{userId}")
    public Response getRealEstateForUserId(@PathParam("userId") String userId) {
        List<RentRealEstate> rentRealEstateList = rentRealEstateBean.getRentedRealEstateListForUserId(userId);

        if (rentRealEstateList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(rentRealEstateList).build();
    }

    @GET
    @Path("/filtered")
    public Response getCustomersFiltered() {
        List<RentRealEstate> customers;

        customers = rentRealEstateBean.getRentRealEstateFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(customers).build();
    }

    @POST
    public Response createRealEstates(RentRealEstate rentRealEstate) {
        if ((rentRealEstate.getRealEstateId() == null || rentRealEstate.getRealEstateId().isEmpty()) || (rentRealEstate.getUserId() == null
                || rentRealEstate.getUserId().isEmpty())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            rentRealEstate = rentRealEstateBean.createRentRealEstate(rentRealEstate);
        }

        if (rentRealEstate.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(rentRealEstate).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(rentRealEstate).build();
        }
    }

    @PUT
    @Path("{rentRealEstateId}")
    public Response putRentRealEstate(@PathParam("rentRealEstateId") String rentRealEstateId, RentRealEstate rentRealEstate) {
        rentRealEstate = rentRealEstateBean.putRentRealEstate(rentRealEstateId, rentRealEstate);

        if (rentRealEstate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (rentRealEstate.getId() != null)
                return Response.status(Response.Status.OK).entity(rentRealEstate).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{rentRealEstateId}")
    public Response deleteRentRealEstate(@PathParam("rentRealEstateId") String customerId) {
        boolean deleted = rentRealEstateBean.deleteRentRealEstate(customerId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
