package si.faks.airbnb.model;

import java.util.List;
import java.util.Map;

public class RealEstateUserHistory {

	private List<RentRealEstate> rentRealEstateList;
	private Map<String, RealEstate> realEstateMap;

	public RealEstateUserHistory(List<RentRealEstate> rentRealEstateList, Map<String, RealEstate> realEstateMap) {
		this.rentRealEstateList = rentRealEstateList;
		this.realEstateMap = realEstateMap;
	}

	public List<RentRealEstate> getRentRealEstateList() {
		return rentRealEstateList;
	}

	public Map<String, RealEstate> getRealEstateMap() {
		return realEstateMap;
	}

}
