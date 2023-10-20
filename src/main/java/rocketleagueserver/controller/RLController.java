package rocketleagueserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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
	// CREATE/POST
	@PostMapping("/player")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PlayerData createPlayer(@RequestBody PlayerData playerData) {
		log.info("THIS IS ROCKET LEAGUE! Creating new player {}", playerData);
		return playerService.savePlayer(playerData); // method in service
		
	}
}
