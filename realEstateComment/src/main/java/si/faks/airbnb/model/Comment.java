package si.faks.airbnb.model;

import java.util.Date;

public class Comment {

	private String userId;
	private String realEstateId;
	private String message;
	private Date dateCreated;

	public Comment(String userId, String realEstateId, String message, Date dateCreated) {
		this.userId = userId;
		this.realEstateId = realEstateId;
		this.message = message;
		this.dateCreated = dateCreated;
	}

	public String getUserId() {
		return userId;
	}

	public String getRealEstateId() {
		return realEstateId;
	}

	public String getMessage() {
		return message;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

}
