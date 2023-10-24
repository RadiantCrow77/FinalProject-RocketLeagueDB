package rocketleagueserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
}
