package si.faks.airbnb.controller;

import com.kumuluz.ee.logs.cdi.Log;
import si.faks.airbnb.model.User;

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
public class UsersController {

	private List<User> userList = new ArrayList<>();

	{
		userList.add(new User("user1", "Jan", "Zivkovic", "jan@zivkovic.si", "000000000"));
		userList.add(new User("user2", "Aljaz", "Kosir", "aljaz@kosir.si", "100000000"));
	}

	@GET
	public List<User> getUsers(){
		return userList;
	}

	@GET
	@Path("/{userId}")
	public User getUser(@PathParam("userId") String userId) {
		return userList.stream()
				.filter(user -> user.getUserId().equals(userId))
				.findFirst()
				.orElse(null);
	}

}
