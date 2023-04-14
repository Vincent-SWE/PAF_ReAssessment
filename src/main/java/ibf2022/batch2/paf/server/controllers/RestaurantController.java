package ibf2022.batch2.paf.server.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.model.Model;
import ibf2022.batch2.paf.server.models.Comment;
import ibf2022.batch2.paf.server.models.Restaurant;
import ibf2022.batch2.paf.server.services.RestaurantService;

@RestController
@RequestMapping({"/", "/api"})
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantSvc;



	// TODO: Task 2 - request handler
	@GetMapping(path = "/cuisines")
	public ResponseEntity<List<String>> getCuisines(Model model) {

		List<String> listOfCuisines = restaurantSvc.getCuisines();
		
		if (listOfCuisines.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(listOfCuisines, HttpStatus.OK);
		}

	}



	// TODO: Task 3 - request handler
	@GetMapping(path = "/restaurants/{cuisine}")
	public ResponseEntity<List<Restaurant>> getRestaurants(@PathVariable String cuisine, Model model) {

		List<Restaurant> restaurantsByCuisine = restaurantSvc.getRestaurantsByCuisine(cuisine);

		if (restaurantsByCuisine.size() == 0 ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(restaurantsByCuisine, HttpStatus.OK);
		}

	}



	// TODO: Task 4 - request handler
		@GetMapping(path = "/restaurant/{restaurantId}")
		public ResponseEntity<?> getRestaurantDetails(@PathVariable String restaurantId, Model model) {

			Optional<Restaurant> restaurantById = restaurantSvc.getRestaurantById(restaurantId);

			if (restaurantById.isEmpty()) {
				Map<String, String> response = new HashMap<>();
				response.put("error : ", "Missing " + restaurantId);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			else {
				return new ResponseEntity<>(restaurantById, HttpStatus.OK);
			}

		}



	// TODO: Task 5 - request handler
	@PostMapping("/restaurant/comment")
	public ResponseEntity<Object> postRestaurantComment(@RequestParam MultiValueMap<String, String> form) {

		String restaurantId = form.getFirst("restaurantId");
		String name = form.getFirst("name");
		int rating = Integer.parseInt(form.getFirst("rating"));
		String commentText = form.getFirst("comment");
		Date date = new Date();
	
		long epoch = date.getTime();

		Comment comment = new Comment();
		
		comment.setRestaurantId(restaurantId);
		comment.setName(name);
		comment.setDate(epoch);
		comment.setRating(rating);
		comment.setComment(commentText);

		restaurantSvc.postRestaurantComment(comment);


		return ResponseEntity.status(HttpStatus.CREATED).body("Comment added");


	}


}
