package si.faks.airbnb.controller;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;
import org.apache.commons.lang3.StringUtils;
import si.faks.airbnb.logic.RealEstateBean;
import si.faks.airbnb.model.RealEstate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Log
@RequestScoped
@Path("/realEstate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ConfigBundle("real-estate-config")
public class RealEstateController {

    @Inject
    private RealEstateBean realEstateBean;

    @Context
    protected UriInfo uriInfo;

    @ConfigValue(value = "dynamic", watch = true)
    private String dynamic;

    @GET
    public Response getRealEstateList() {
        final List<RealEstate> realEstateList = realEstateBean.getRealEstateList();
        return Response.ok(realEstateList).build();
    }

    @GET
    @Path("/{realEstateId}")
    public Response getRealEstate(@PathParam("realEstateId") String realEstateId) {
        final RealEstate realEstate = realEstateBean.getRealEstate(realEstateId);

        if (realEstate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(realEstate).build();
    }

    @POST
    public Response addRealEstate(RealEstate realEstate) {
        if(StringUtils.isBlank(realEstate.getName()) ||
                StringUtils.isBlank(realEstate.getUserId()) //||
                //realEstate.getLocation() == null
                ){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        realEstate = realEstateBean.addRealEstate(realEstate);

        if (realEstate.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(realEstate).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(realEstate).build();
        }
    }

    @GET
    @Path("/getRealEstateListFiltered")
    public Response getRealEstatesFiltered() {
        final List<RealEstate> realEstateList;

        realEstateList = realEstateBean.getRealEstateListFiltered(uriInfo);

        return Response.status(Response.Status.OK).entity(realEstateList).build();
    }

    @GET
    @Path("/config")
    public Response getConfig() {
        return Response.status(Response.Status.OK).entity(dynamic).build();
    }

}
