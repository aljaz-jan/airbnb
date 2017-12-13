package si.faks.airbnb.controller;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/info")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectInfoController {

	@GET
	public String getProjectInfo() {
		return "{\n" +
				"    \"clani\": [\"ak2748\", \"jz2491\"],\n" +
				"    \"opis_projekta\": \"Projekt implementira airbnb storitev.\",\n" +
				"    \"mikrostoritve\": [\"http://35.198.179.68:8080/realEstate\", \"http://35.198.118.211:8081/rentRealEstate\", \"http://35.198.155.120:8082/realEstateHistory\"],\n" +
				"    \"github\": [\"https://github.com/aljaz-jan/airbnb\"],\n" +
				"    \"travis\": [\"https://travis-ci.org/aljaz-jan/airbnb\"],\n" +
				"    \"dockerhub\": [\"https://hub.docker.com/r/ak2748/real_estate/\", \"https://hub.docker.com/r/ak2748/rent-real-estate-api/\", \"https://hub.docker.com/r/ak2748/real-estate-history/\"]\n" +
				"}";
	}

}
