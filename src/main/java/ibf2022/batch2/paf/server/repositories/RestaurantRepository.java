package ibf2022.batch2.paf.server.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.paf.server.models.Address;
import ibf2022.batch2.paf.server.models.Comment;
import ibf2022.batch2.paf.server.models.Restaurant;

@Repository
public class RestaurantRepository {


	@Autowired
	private MongoTemplate mongoTemplate;

	// prevent typo
	public static final String C_RESTAURANTS = "restaurants";

	public static final String C_COMMENTS = "comments";


	// TODO: Task 2 
	// Do not change the method's signature
	// Write the MongoDB query for this method in the comments below
	//
	// native Mongo query
	// db.restaurants.distinct("cuisine").sort();
	public List<String> getCuisines() {

		Query query = new Query();
		query.fields().include("cuisine").exclude("_id");
		// cuisines sorted in ascending order
		query.with(Sort.by(Sort.Direction.ASC, "cuisine"));

		List<String> cuisines = mongoTemplate.findDistinct(query, "cuisine", C_RESTAURANTS, String.class);


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
	// native mongo query
	//  db.restaurants.find({ cuisine: "Afghan" }, {name: 1, _id:0}).sort({ name: 1 })
	public List<Restaurant> getRestaurantsByCuisine(String cuisine) {

		Criteria criteria = Criteria.where("cuisine").is(cuisine);
		Query query = new Query(criteria);

		List<Restaurant> restaurants = mongoTemplate.find(query, Restaurant.class, C_RESTAURANTS);

		System.out.println(restaurants);

		return restaurants;


	}
	


	// TODO: Task 4 
	// Do not change the method's signature
	// Write the MongoDB query for this method in the comments below
	//
	// native mongo query
	// 
	public Optional<Restaurant> getRestaurantById(String id) {

		MatchOperation match = Aggregation.match(Criteria.where("restaurant_id").is(id));

		ProjectionOperation project = Aggregation.project()
				.and(id).as("restaurant_id")
				.and("name").as("name")
				.and("cuisine").as("cuisine")
				.and("address").as("address")
				.and("comments").as("comments");


		Aggregation pipeline = Aggregation.newAggregation(match, project);

		Restaurant restaurant = mongoTemplate.aggregate(pipeline, C_RESTAURANTS, Restaurant.class).getUniqueMappedResult();

		restaurant.setAddress(getAddressById(id).toString());

		List<Comment> comments = getCommentsById(id);
		restaurant.setComments(comments);


		return Optional.ofNullable(restaurant);

	}


	public Address getAddressById(String id) {

		MatchOperation match = Aggregation.match(Criteria.where("restaurant_id").is(id));

		ProjectionOperation project = Aggregation.project().and("address.building").as("building")
				.and("address.street").as("street")
				.and("address.zipcode").as("zipcode")
				.and("borough").as("borough");

		Aggregation pipeline = Aggregation.newAggregation(match, project);
	
		Document result = mongoTemplate.aggregate(pipeline, C_RESTAURANTS, Document.class).getUniqueMappedResult();
	
		String building = result.getString("building");
		String street = result.getString("street");
		String zipcode = result.getString("zipcode");
		String borough = result.getString("borough");
	

		return new Address(building, street, zipcode, borough);

	}



	public List<Comment> getCommentsById(String id) {

		MatchOperation match = Aggregation.match(Criteria.where("restaurant_id").is(id));

		UnwindOperation unwind = Aggregation.unwind("grades");

		ProjectionOperation project = Aggregation.project()
				.and("restaurant_id").as("restaurantId")
				.and("grades.date").as("date")
				.and("grades.comment").as("comment")
				.and("grades.score").as("rating")
				.and("grades.name").as("name");


		Aggregation pipeline = Aggregation.newAggregation(match, unwind, project);
	
		AggregationResults<Comment> results = mongoTemplate.aggregate(pipeline, C_RESTAURANTS, Comment.class);

		List<Comment> comments = results.getMappedResults();
	


		return comments;

	}




	// TODO: Task 5 
	// Do not change the method's signature
	// Write the MongoDB query for this method in the comments below
	//
	public void insertRestaurantComment(Comment comment) {

		Document commentDoc = new Document()
		.append("restaurantId", comment.getRestaurantId())
		.append("name", comment.getName())
		.append("date", comment.getDate())
		.append("rating", comment.getRating())
		.append("comment", comment.getComment());

		mongoTemplate.insert(commentDoc, C_COMMENTS);

	}



}
