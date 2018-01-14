package si.faks.airbnb.controller;

import si.faks.airbnb.model.RealEstateDistance;
import com.kumuluz.ee.logs.cdi.Log;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log
@RequestScoped
@Path("/realEstateDistance")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DistanceController {

	private List<RealEstateDistance> realEstateDistanceList = new ArrayList<>();

	{
		realEstateDistanceList.add(new RealEstateDistance("realEstate1", "Vecna pot 113", 13.2));
		realEstateDistanceList.add(new RealEstateDistance("realEstate2", "Vecna pot 113", 33.1));
		realEstateDistanceList.add(new RealEstateDistance("realEstate1", "Stanezice 47", 30.2));
		realEstateDistanceList.add(new RealEstateDistance("realEstate2", "Stanezice 47", 71.1));
	}

	@GET
	public String dummy(){
		return "dummy";
	}

	@GET
	@Path("/{fromLocation}")
	public List<RealEstateDistance> getDistanceToAll(@PathParam("fromLocation") String fromLocation) {
		return realEstateDistanceList.stream()
				.filter(distance -> distance.getFromLocation().equals(fromLocation))
				.collect(Collectors.toList());
	}

	@GET
	@Path("/{fromLocation}/{realEstateId}")
	public RealEstateDistance getDistanceToRealEstate(@PathParam("fromLocation") String fromLocation, @PathParam("realEstateId") String realEstateId) {
		return realEstateDistanceList.stream()
				.filter(distance -> distance.getFromLocation().equals(fromLocation) && distance.getRealEstateId().equals(realEstateId))
				.findFirst()
				.orElse(null);
	}

}
