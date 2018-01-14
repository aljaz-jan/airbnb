package airbnb.model;

public class RealEstateAd {

	private String adId;
	private String realEstateId;

	public RealEstateAd(String adId, String realEstateId) {
		this.adId = adId;
		this.realEstateId = realEstateId;
	}

	public String getAdId() {
		return adId;
	}

	public String getRealEstateId() {
		return realEstateId;
	}

}
