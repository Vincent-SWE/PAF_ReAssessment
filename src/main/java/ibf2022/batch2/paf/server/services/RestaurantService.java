package ibf2022.batch2.paf.server.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.batch2.paf.server.models.Comment;
import ibf2022.batch2.paf.server.models.Restaurant;
import ibf2022.batch2.paf.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {


	@Autowired
	RestaurantRepository restaurantRepo;


	// TODO: Task 2
	// Do not change the method's signature
	public List<String> getCuisines() {

		return restaurantRepo.getCuisines();
	}





	// TODO: Task 3 
	// Do not change the method's signature
	public List<Restaurant> getRestaurantsByCuisine(String cuisine) {

		cuisine.replace("_", "/");
		
		List<Restaurant> restaurantsByCuisine = restaurantRepo.getRestaurantsByCuisine(cuisine);

		return restaurantsByCuisine;
	}




	// TODO: Task 4 
	// Do not change the method's signature
	public Optional<Restaurant> getRestaurantById(String id) {

		return restaurantRepo.getRestaurantById(id);
	}




	// TODO: Task 5 
	// Do not change the method's signature
	public void postRestaurantComment(Comment comment) {

		restaurantRepo.insertRestaurantComment(comment);

	}
}
