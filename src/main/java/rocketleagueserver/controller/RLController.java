package rocketleagueserver.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import rocketleagueserver.controller.model.PlayerData;
import rocketleagueserver.controller.model.PlayerData.CarData;
import rocketleagueserver.entity.Car;
import rocketleagueserver.service.PlayerService;

@RestController
// URI Mapping:
@RequestMapping("/rocketleagueserver")
@Slf4j
public class RLController {
	// injects class
	@Autowired
	private PlayerService playerService;

	// controller methods

	// Player
	// CREATE / POST
	@PostMapping("/player")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PlayerData createPlayer(@RequestBody PlayerData playerData) {
		log.info("THIS IS ROCKET LEAGUE! Creating new player {}", playerData);
		return playerService.savePlayer(playerData); // method in service

	}

	// ALL Players,
	// similar to petStore
	// READ / GET
	@GetMapping("/player")
	public List<PlayerData> retrieveAllPlayers() {
		log.info("Retrieving all players.");
		List<PlayerData> players = playerService.retrieveAllPlayers();
		return players;
	}

	// Player
	// READ / GET one Player by ID
	@GetMapping("/player/{playerId}")
	public PlayerData retrievePlayerById(@PathVariable Long playerId) {
		log.info("Retrieving player with ID: {}", playerId);
		PlayerData player = playerService.retrievePlayerById(playerId);
		return player;
	}

	// Player
	// UPDATE / POST by Id
	@PutMapping("/player/{playerId}")
	public PlayerData updatePlayer(@PathVariable Long playerId, 
			@RequestBody PlayerData playerData) {
		playerData.setPlayerId(playerId);
		log.info("Updating Player {}", playerId);
		return playerService.savePlayer(playerData);
	}
	
	// unsure from here down:
	// Car
	// CREATE / POST
	@PostMapping("/player/{playerId}/car")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CarData addCar(
			@PathVariable Long playerId,
			@RequestBody CarData carData){ // chang Car to PlayerCar? compare employee and player 
		log.info("Added a new car: {}", carData);
		// Fix this vvv*****
		return playerService.saveCar(playerId, carData);
	}
	
	// Car
	// All cars for ONE player
	// READ / GET by player ID
	// **MIGHT NEED TO LOGIC THROUGH TIHS
	@GetMapping("/player/{playerId}/car")
	public List<CarData> retrieveAllCars(){
		log.info("Retrieving all cars in the specified player's garage.");
		List<CarData> cars = playerService.retrieveAllCars();
		return cars;
	}
	
	// Car
	// READ / GET Car by ID
	@GetMapping("/player/{playerId}/car/{carId}")
	public CarData retrieveCarById(@PathVariable Long carId) {
		log.info("Retrieving player with ID: {}",carId);
		CarData car = playerService.retrieveCarById(carId);
		return car;
	}
	
	// Car
	// Update ONE car by ID for a specific player
	@PutMapping("/player/{playerId}/car/{carId}") // the specific URL I'm using: /player/1/car/2
	public CarData updateCar(@PathVariable Long playerId, @PathVariable Long carId,
			@RequestBody CarData carData) {
		carData.setCarId(carId);
		log.info("Updating Car = {} for player with ID = {}", carId, playerId); // Output: Updating Car = 2 for player with ID = 1
		return playerService.saveCar(playerId, carData); // changed carId to playerId
		// when sending req, it's claiming that the playerId is the same as the carId = 2, playerId is 1
	}
	
//	// Car
//	// Remove ALL cars from an inventory -- this is not allowed
//	@DeleteMapping()
//	public void deleteAllCars("/player/{playerId}/car/{carId}") {
//		
//	}

	// Car
	// Remove ONE car by ID from inventory for a specific player
	@DeleteMapping("/player/{playerId}/car/{carId}")
	public Map<String, String> deleteCarById(@PathVariable Long playerId, @PathVariable Long carId){
		log.info("Deleting car = {} for player with ID = {}", carId, playerId);
		playerService.deleteCarById(carId);
		
		return Map.of("message", "Deleted car with ID = "+carId+ " succesfully.");
	}
	
}
