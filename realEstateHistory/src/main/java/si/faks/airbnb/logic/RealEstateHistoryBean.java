package si.faks.airbnb.logic;

import org.apache.commons.collections.CollectionUtils;
import si.faks.airbnb.model.RealEstate;
import si.faks.airbnb.model.RealEstateUserHistory;
import si.faks.airbnb.model.RentRealEstate;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequestScoped
public class RealEstateHistoryBean {

	private Client httpClient;

	public RealEstateHistoryBean() {
		httpClient = ClientBuilder.newClient();
	}

	public RealEstateUserHistory getRentedRealEstateList(final String userId) {
		final List<RentRealEstate> rentRealEstateList = getRentRealEstateForUserId(userId);
		final Map<String, RealEstate> realEstateMap = getRealEstateMapForIds(
				rentRealEstateList.stream()
						.map(RentRealEstate::getRealEstateId)
						.distinct()
						.collect(Collectors.toList())
		);
		if(CollectionUtils.isEmpty(rentRealEstateList)){
			return null;
		}
		return new RealEstateUserHistory(rentRealEstateList, realEstateMap);
	}

	private Optional<RealEstate> getRealEstateForId(final String id) {
		final Optional<String> baseUrl = Optional.of("172.18.0.5/realEstate");

		if (baseUrl.isPresent()) {
			try {
				RealEstate realEstate = httpClient.target(baseUrl.get() + "/" + id)
						.request().get(new GenericType<RealEstate>() {
									   }
						);
				return Optional.ofNullable(realEstate);
			} catch (WebApplicationException | ProcessingException e) {
				System.out.println(e.getMessage());
				throw new InternalServerErrorException(e);
			}
		}
		return Optional.empty();
	}

	private List<RealEstate> getRealEstateListForIds(final List<String> realEstateIdsList){
		if(CollectionUtils.isEmpty(realEstateIdsList)) {
			return new ArrayList<>();
		}
		final Optional<String> baseUrl = Optional.of("");
		final Set<String> realEstateIds = new HashSet<>(realEstateIdsList);

		return realEstateIds.stream()
				.map(this::getRealEstateForId)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

	private List<RentRealEstate> getRentRealEstateForUserId(String userId) {
		final Optional<String> baseUrl = Optional.of("172.18.0.4/rentRealEstate");

		if (baseUrl.isPresent()) {
			try {
				return httpClient.target(baseUrl.get() + "/user/" + userId)
						.request().get(new GenericType<List<RentRealEstate>>() {}
						);
			} catch (WebApplicationException | ProcessingException e) {
				System.out.println(e.getMessage());
				throw new InternalServerErrorException(e);
			}
		}
		return new ArrayList<>();
	}

	private Map<String, RealEstate> getRealEstateMapForIds(final List<String> realEstateIds) {
		final List<RealEstate> realEstateList = getRealEstateListForIds(realEstateIds);

		return realEstateList.stream()
				.collect(Collectors.toMap(RealEstate::getId, Function.identity()));
	}

}
