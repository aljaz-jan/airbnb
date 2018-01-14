package airbnb.controller;

import airbnb.model.Comment;
import com.kumuluz.ee.logs.cdi.Log;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log
@RequestScoped
@Path("/realEstateComment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentController {

	private List<Comment> commentList = new ArrayList<>();

	{
		commentList.add(new Comment("user1", "realEstate1", "Prvi komentar !!!", new Date()));
		Date d = new Date();
		d.setTime(123123);
		commentList.add(new Comment("user2", "realEstate1", "Drugi komentar !!!", d));
	}

	@GET
	public List<Comment> getComments(){
		return commentList;
	}

	@GET
	@Path("/{realEstateId}")
	public List<Comment> getCommentsForRealEstateId(@PathParam("realEstateId") String realEstateId) {
		return commentList.stream()
				.filter(comment -> comment.getRealEstateId().equals(realEstateId))
				.collect(Collectors.toList());
	}

}
