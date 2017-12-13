package si.faks.airbnb.controller;

import com.kumuluz.ee.logs.cdi.Log;
import si.faks.airbnb.logic.RealEstateHistoryBean;
import si.faks.airbnb.model.RealEstateUserHistory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Log
@RequestScoped
@Path("/realEstateHistory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RealEstateHistoryController {

    @Inject
    private RealEstateHistoryBean realEstateHistoryBean;

    @GET
    @Path("/{userId}")
    public Response getRealEstate(@PathParam("userId") String userId) {
        final RealEstateUserHistory realEstateUserHistory = realEstateHistoryBean.getRentedRealEstateList(userId);

        if (realEstateUserHistory == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(realEstateUserHistory).build();
    }

}
