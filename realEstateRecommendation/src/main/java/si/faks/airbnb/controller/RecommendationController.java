package si.faks.airbnb.controller;

import com.kumuluz.ee.logs.cdi.Log;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Log
@RequestScoped
@Path("/realEstateUsers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecommendationController {

	private List<String> recommendationList = new ArrayList<>();

	{
		recommendationList.add("realEstate1");
		recommendationList.add("realEstate2");
		recommendationList.add("realEstate3");
		recommendationList.add("realEstate4");
		recommendationList.add("realEstate5");
	}

	@GET
	public List<String> getRecommendationForAll(){
		return recommendationList;
	}

	@GET
	@Path("/{userId}")
	public List<String> getRecommendationForUser(@PathParam("userId") String userId) {
		return recommendationList.subList(0, 2);
	}

}
