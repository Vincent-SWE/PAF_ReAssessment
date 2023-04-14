package ibf2022.batch2.paf.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.model.Model;
import ibf2022.batch2.paf.server.services.RestaurantService;

@RestController
@RequestMapping("/api")
public class RestaurantController {

	@Autowired
	RestaurantService restaurantSvc;



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


	// TODO: Task 4 - request handler
	

	// TODO: Task 5 - request handler

}
