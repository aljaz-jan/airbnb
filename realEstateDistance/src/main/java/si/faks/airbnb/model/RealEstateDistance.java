package si.faks.airbnb.model;

public class RealEstateDistance {

	private String realEstateId;
	private String fromLocation;
	private double distance;

	public RealEstateDistance(String realEstateId, String fromLocation, double distance) {
		this.realEstateId = realEstateId;
		this.fromLocation = fromLocation;
		this.distance = distance;
	}

	public String getRealEstateId() {
		return realEstateId;
	}

	public String getFromLocation() {
		return fromLocation;
	}

	public double getDistance() {
		return distance;
	}
}
