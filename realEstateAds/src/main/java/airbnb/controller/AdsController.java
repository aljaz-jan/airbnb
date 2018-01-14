package airbnb.controller;

import airbnb.model.RealEstateAd;
import com.kumuluz.ee.logs.cdi.Log;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Log
@RequestScoped
@Path("/realEstateAds")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdsController {

	private List<RealEstateAd> adsList = new ArrayList<>();

	{
		adsList.add(new RealEstateAd("ad1", "realEstate1"));
		adsList.add(new RealEstateAd("ad2", "realEstate2"));
	}

	@GET
	public List<RealEstateAd> getRecommendationForAll(){
		return adsList;
	}

	@GET
	@Path("/{adId}")
	public RealEstateAd getRecommendationForUser(@PathParam("adId") String adId) {
		return adsList.stream()
				.filter(ad -> ad.getAdId().equals(adId))
				.findFirst()
				.orElse(null);
	}

}
