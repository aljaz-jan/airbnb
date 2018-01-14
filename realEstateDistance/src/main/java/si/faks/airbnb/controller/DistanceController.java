package si.faks.airbnb.controller;

import si.faks.airbnb.model.RealEstateDistance;
import com.kumuluz.ee.logs.cdi.Log;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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

	private Map<String, RealEstateDistance> realEstateDistanceList = new HashMap<>();

	{
		realEstateDistanceList.put("Vecna pot 113", new RealEstateDistance("realEstate1", "Vecna pot 113", 13.2));
		realEstateDistanceList.put("Vecna pot 113", new RealEstateDistance("realEstate2", "Vecna pot 113", 33.1));
		realEstateDistanceList.put("Stanezice 47", new RealEstateDistance("realEstate1", "Stanezice 47", 30.2));
		realEstateDistanceList.put("Stanezice 47", new RealEstateDistance("realEstate2", "Stanezice 47", 71.1));
	}

	@GET
	@Path("/{fromLocation}")
	public List<RealEstateDistance> getDistanceToAll(@PathParam("fromLocation") String fromLocation) {
		return realEstateDistanceList.entrySet().stream()
				.filter(distanceEntry -> distanceEntry.getKey().equals(fromLocation))
				.map(distanceEntry -> distanceEntry.getValue())
				.collect(Collectors.toList());
	}

	@GET
	@Path("/{fromLocation}/{realEstateId}")
	public RealEstateDistance getDistanceToRealEstate(@PathParam("fromLocation") String fromLocation, @PathParam("realEstateId") String realEstateId) {
		return realEstateDistanceList.entrySet().stream()
				.filter(distanceEntry -> distanceEntry.getKey().equals(fromLocation) && distanceEntry.getValue().getRealEstateId().equals(realEstateId))
				.map(distanceEntry -> distanceEntry.getValue())
				.findFirst()
				.orElse(null);
	}

}
