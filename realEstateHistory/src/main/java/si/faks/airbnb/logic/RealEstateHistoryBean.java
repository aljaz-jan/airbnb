package si.faks.airbnb.logic;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.fault.tolerance.annotations.CommandKey;
import com.kumuluz.ee.fault.tolerance.annotations.GroupKey;
import org.apache.commons.collections.CollectionUtils;
import org.eclipse.microprofile.faulttolerance.*;
import si.faks.airbnb.config.ConfigProperties;
import si.faks.airbnb.model.RealEstate;
import si.faks.airbnb.model.RealEstateUserHistory;
import si.faks.airbnb.model.RentRealEstate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
@Bulkhead
@GroupKey("real-estate-history")
public class RealEstateHistoryBean {

	private Client httpClient;

	@Inject
	private ConfigProperties properties;

	@Inject
	@DiscoverService(value = "realEstate")
	private Optional<String> realEstateServiceUrl;

	@Inject
	@DiscoverService(value = "rentRealEstateService")
	private Optional<String> rentRealEstateServiceUrl;

	public RealEstateHistoryBean() {
		httpClient = ClientBuilder.newClient();
	}

	@CircuitBreaker
	@Fallback(fallbackMethod = "getRentedRealEstateListFallback")
	@CommandKey("http-get-real-estate")
	@Timeout
	@Asynchronous
	public RealEstateUserHistory getRentedRealEstateList(final String userId) {
		if (properties.getIsDown() != null && properties.getIsDown()) {
			throw new InternalServerErrorException("Known error occurred");
		}

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

	public RealEstateUserHistory getRentedRealEstateListFallback(final String userId){
		final List<RentRealEstate> rentRealEstateList = new ArrayList<>();
		final Map<String, RealEstate> realEstateMap = new HashMap<>();
		RentRealEstate rre = new RentRealEstate();
		rre.setId("NA");
		rre.setRealEstateId("NA");
		rre.setUserId("NA");


		RealEstate re = new RealEstate();
		re.setId("NA");
		re.setName("NA");
		re.setAvailableBeds(0);
		re.setPrice(0.0d);
		re.setUserId("NA");

		rentRealEstateList.add(rre);
		realEstateMap.put("NA", re);
		return new RealEstateUserHistory(rentRealEstateList, realEstateMap);
	}

	private Optional<RealEstate> getRealEstateForId(final String id) {
		if (realEstateServiceUrl.isPresent()) {
			try {
				RealEstate realEstate = httpClient.target(realEstateServiceUrl.get() + "/realEstate/" + id)
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
		final Set<String> realEstateIds = new HashSet<>(realEstateIdsList);

		return realEstateIds.stream()
				.map(this::getRealEstateForId)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

	private List<RentRealEstate> getRentRealEstateForUserId(String userId) {
		if (rentRealEstateServiceUrl.isPresent()) {
			try {
				return httpClient.target(rentRealEstateServiceUrl.get() + "/rentRealEstate/user/" + userId)
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
