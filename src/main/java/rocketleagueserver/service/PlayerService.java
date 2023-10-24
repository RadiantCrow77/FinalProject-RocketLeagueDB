package rocketleagueserver.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rocketleagueserver.controller.model.PlayerData;
import rocketleagueserver.dao.PlayerDao;
import rocketleagueserver.entity.Player;

@Service
public class PlayerService {

	
	//Note: need a Dao for each Entity
	@Autowired
	private PlayerDao playerDao;
	
	// creates a player
	@Transactional(readOnly = false)
	public PlayerData savePlayer(PlayerData playerData) {
		Long playerId = playerData.getPlayerId();
		Player player = findOrCreatePlayer(playerId);
		
		// set all instances variables in player obj to what got passed in JSON data (destination, source)
		setFieldsInPlayer(player, playerData);
		
		// save player
		return new PlayerData(playerDao.save(player)); // Note: in vids, created constructor and put it in Data around 31:00, but Eclipse isn't yelling at me???
	}
	
	
	private void setFieldsInPlayer(Player player, 
			PlayerData playerData) {
		// all the fields in the player data class
		// no need to set ID
		player.setPlayerName(playerData.getPlayerName());
		player.setHoursPlayed(playerData.getHoursPlayed());
		player.setPlayerBanner(playerData.getPlayerBanner());
		player.setPlayerBorder(playerData.getPlayerBorder());
		player.setPlayerAnthem(playerData.getPlayerAnthem());
	}


	//helper method for savePlayer
	private Player findOrCreatePlayer(Long playerId) {
		Player player;

		if (Objects.isNull(playerId)) { 
			player = new Player(); 
		} else {
			player = findPlayerById(playerId);
		}
		
		return player;
	}

	private Player findPlayerById(Long playerId) {
	return playerDao.findById(playerId)
			.orElseThrow(() -> new NoSuchElementException(
			"Player with ID = " + playerId + "was not found.")); 
	}

// retreive ALL players
	@Transactional(readOnly = true)
	public List<PlayerData> retrieveAllPlayers() {
		List<Player> players = playerDao.findAll();
		
		// turn list of player objects into list of player data objects
		List<PlayerData> response = new LinkedList<>();
		
		for (Player player : players) {
			response.add(new PlayerData(player));
		}
		
		return response;
	}

// retrieve ONE player by ID
	@Transactional(readOnly = true)
	public PlayerData retrievePlayerById(Long playerId) {
		Player player =  playerDao.findById(playerId).orElseThrow(
				() -> new NoSuchElementException("The Player with Id = " + playerId + " was not found. "));
		
		PlayerData pd = new PlayerData(player);
		return pd;
	}
}
