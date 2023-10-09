package rocketleagueserver.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rocketleagueserver.controller.model.PlayerData;

@RestController
// URI Mapping:
@RequestMapping("/rocketleagueserver")
public class RLController {

	// controller methods
	
	public PlayerData createPlayer(@RequestBody PlayerData playerData) {
		
	}
}
