package ibf2022.batch2.paf.server.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.paf.server.models.Comment;
import ibf2022.batch2.paf.server.models.Restaurant;

@Repository
public class RestaurantRepository {


	@Autowired
	private MongoTemplate mongoTemplate;



	// TODO: Task 2 
	// Do not change the method's signature
	// Write the MongoDB query for this method in the comments below
	//
	// native Mongo query
	//db.restaurants.find({}, {name:1, _id:0})
	public List<String> getCuisines() {

		Query query = new Query();
		query.fields().include("cuisine").exclude("_id");
		// cuisines sorted in ascending order
		query.with(Sort.by(Sort.Direction.ASC, "cuisine"));

		List<String> cuisines = mongoTemplate.findDistinct(query, "cuisine", "restaurant", String.class);

		
		List<String> newCuisines = new ArrayList<>();
		
		for (String s : cuisines) {

			String u ="";
			u = s.replace("/", "_");
			newCuisines.add(u);

		}
		return newCuisines;
	}

















	// TODO: Task 3 
	// Do not change the method's signature
	// Write the MongoDB query for this method in the comments below
	//
	public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
		return null;
	}
	
	// TODO: Task 4 
	// Do not change the method's signature
	// Write the MongoDB query for this method in the comments below
	//
	public Optional<Restaurant> getRestaurantById(String id) {
		return null;
	}

	// TODO: Task 5 
	// Do not change the method's signature
	// Write the MongoDB query for this method in the comments below
	//
	public void insertRestaurantComment(Comment comment) {
	}
}
