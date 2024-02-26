package rocketleagueserver.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rocketleagueserver.controller.model.PlayerData;
import rocketleagueserver.controller.model.PlayerData.CarData;
import rocketleagueserver.controller.model.PlayerData.RankEarnedData;
import rocketleagueserver.dao.CarDao;
import rocketleagueserver.dao.PlayerDao;
import rocketleagueserver.dao.RankEarnedDao;
import rocketleagueserver.entity.Car;
import rocketleagueserver.entity.Player;
import rocketleagueserver.entity.RankEarned;

@Service
public class PlayerService {

	// Note: need a Dao for each Entity
	@Autowired
	private PlayerDao playerDao;

	@Autowired
	private CarDao carDao;
	
	@Autowired
	private RankEarnedDao rankEarnedDao;

	// RANK DAO HERE

	// creates a player
	@Transactional(readOnly = false)
	public PlayerData savePlayer(PlayerData playerData) {
		Long playerId = playerData.getPlayerId();
		Player player = findOrCreatePlayer(playerId, playerData.getPlayerName());

		// set all instances variables in player obj to what got passed in JSON data
		// (destination, source)
		setFieldsInPlayer(player, playerData);

		// save player
		return new PlayerData(playerDao.save(player)); // Note: in vids, created constructor and put it in Data around
														// 31:00, but Eclipse isn't yelling at me???
	}

	private void setFieldsInPlayer(Player player, PlayerData playerData) {
		// all the fields in the player data class
		// no need to set ID
		player.setPlayerName(playerData.getPlayerName());
		player.setHoursPlayed(playerData.getHoursPlayed());
		player.setPlayerBanner(playerData.getPlayerBanner());
		player.setPlayerBorder(playerData.getPlayerBorder());
		player.setPlayerAnthem(playerData.getPlayerAnthem());
	}

	// helper method for savePlayer
	// recall that player names need to be unique
	private Player findOrCreatePlayer(Long playerId, String playerName) {
		Player player;

		if (Objects.isNull(playerId)) {
			Optional<Player> opPlayerNm = playerDao.findByPlayerName(playerName);

			if (opPlayerNm.isPresent()) {
				throw new DuplicateKeyException("Player with Name: " + playerName + " already exists.");
			}

			player = new Player();
		} else {
			player = findPlayerById(playerId);
		}

		return player;
	}

	private Player findPlayerById(Long playerId) {
		return playerDao.findById(playerId)
				.orElseThrow(() -> new NoSuchElementException("Player with ID = " + playerId + "was not found."));
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
		Player player = playerDao.findById(playerId)
				.orElseThrow(() -> new NoSuchElementException("The Player with Id = " + playerId + " was not found. "));

		PlayerData pd = new PlayerData(player);
		return pd;
	}

// create a new car for a specific player ID
	// this is like saveEmployee in PetStoreService
	public CarData saveCar(Long playerId, CarData carData) {
		Player player = findPlayerById(playerId);

		Long carId = carData.getCarId();
		Car car = findOrCreateCar(playerId, carId);

		copyCarFields(car, carData);

		// set player in car
		car.setPlayer(player);

		// add car to player list of cars
		player.getCars().add(car);

		// save car by calling save method in car DAO
		Car dbCar = carDao.save(car);

		// convert car obj returned by save method to PetStoreEmployee obj and
		// return
		return new CarData(dbCar);
	}

	private void copyCarFields(Car car, CarData playerCar) {
		// fields from car entity
//		private Long carId; // PK
//		private String carBodyName;
//		private String carColor;
//		private String carPaintFinish;

		car.setCarId(playerCar.getCarId());
		car.setCarBodyName(playerCar.getCarBodyName());
		car.setCarColor(playerCar.getCarColor());
		car.setCarPaintFinish(playerCar.getCarPaintFinish());

	}

	private Car findOrCreateCar(Long playerId, Long carId) {
		if (Objects.isNull(carId)) {
			return new Car();
		} else {
			return findPlayerCarById(playerId, carId); // FIGURE THIS OUT NEXT, LOOKING AT CUSTOMER AS A REF***
		}
	}

	private Car findPlayerCarById(Long playerId, Long carId) {
		Car car = carDao.findById(carId)
				.orElseThrow(() -> new NoSuchElementException("The car with Id = " + carId + " was not found."));

		// if car with Id is not found for that specific player, throw exception
		if (car.getPlayer().getPlayerId() != playerId) {
			throw new IllegalStateException(
					"Car with ID = " + carId + " does not belong to player containing ID = " + playerId);
		}
		return car;
	}

	// Retrieve ALL cars for a certain player
	@Transactional(readOnly = true)
	public List<CarData> retrieveAllCars() {
		List<Car> cars = carDao.findAll();

		List<CarData> response = new LinkedList<>();

		for (Car car : cars) {
			response.add(new CarData(car));
		}
		return response;
	}

	// Get ONE car by ID for a specific player
	@Transactional(readOnly = true)
	public CarData retrieveCarById(Long carId) {
		Car car = carDao.findById(carId)
				.orElseThrow(() -> new NoSuchElementException("The Car with Id = " + carId + " was not found. "));

		CarData cd = new CarData(car);
		return cd;
	}

	// Delete ONE car by ID for a specific player
	@Transactional(readOnly = false)
	public void deleteCarById(Long carId) {
		Car car = carDao.findById(carId)
				.orElseThrow(() -> new NoSuchElementException("The car with Id = " + carId + " was not found."));

		carDao.delete(car);
	}
 // PLAYER STUFF
	public RankEarnedData savePlayerRank(Long playerId, RankEarnedData rankData) {
	Player player = findPlayerById(playerId);
	
	Long rankId = rankData.getRankId();
	RankEarned rankEarned = findOrCreatePlayerRank(playerId, rankId);

	copyRankFields(rankEarned, rankData);
//	findRankByPlayerId( playerId, rankId);
	// ^ duplicate problem
	
	// set rank in car
//	rankEarned.setPlayer(player); // NOT SURE IF NEEDED


	player.getRanksEarned().add(rankEarned);
	rankEarned.setPlayer(player); // set player inside rank earned

	RankEarned dbRank = rankEarnedDao.save(rankEarned);

	return new RankEarnedData(dbRank);
	
	}

	private RankEarned findOrCreatePlayerRank(Long playerId, Long rankId) {
		RankEarned rankEarned;
		
		if (Objects.isNull(rankId)) { // if player has no rank Id
			rankEarned = new RankEarned(); // ... create one
			// du start
			if(rankEarned != null) {
				throw new DuplicateKeyException("Player with ID = " + playerId + " already contains that rank. Only new ranks can be added.");
			} // du end
		} else {
			rankEarned = findPlayerRankById(playerId, rankId); // find the rank ID that already exists for that player
		}
		return rankEarned;
	}
	
//	// NEW** Duplicate error handler - handles if a player already has specific rank **NOT WORKING
//	if(rank != null) { //  if rank already exists ..
//		throw new DuplicateKeyException("Player with ID = " + playerId + "already contains rank with ID = " + rank + ". Only new ranks can be added.");
//		
//	}

	private RankEarned findPlayerRankById(Long playerId, Long rankId) {
		RankEarned rank = rankEarnedDao.findById(rankId)
				.orElseThrow(() -> new NoSuchElementException("The rank with Id = " + rankId + " was not found."));

		// if car with Id is not found for that specific player, throw exception
		if (rank.getPlayer().getPlayerId() != playerId) {
			throw new IllegalStateException(
					"Rank with ID = " + rankId + " does not belong to player containing ID = " + playerId);
		}
		
	
		return rank;
	}

	// CAR STUFF
	@Transactional(readOnly=false)
	public RankEarnedData saveCarRank(Long carId, Long rankId) {
	Car car = findCarById(carId);

	RankEarned rankEarned = findRankById(rankId);

//	findRankByPlayerId( playerId, rankId);
	// ^ duplicate problem
	
	// set rank in car
	rankEarned.getCars().add(car);

	car.getRanksEarned().add(rankEarned);

	RankEarned dbRank = rankEarnedDao.save(rankEarned);

	return new RankEarnedData(dbRank);
	
	}

	private Car findCarById(Long carId) {
		return carDao.findById(carId).orElseThrow(() -> new NoSuchElementException("The car with Id = " + carId + " was not found."));
	}

	private RankEarned findRankById(Long rankId) {
		return rankEarnedDao.findById(rankId).orElseThrow(() -> new NoSuchElementException("The rank with Id = " + rankId + " was not found."));
	}
	
	private RankEarned findOrCreateCarRank(Long carId, Long rankId) {
		if (Objects.isNull(rankId)) {
			return new RankEarned();
		} else {
			return findCarRankById(carId, rankId); // FIGURE THIS OUT NEXT, LOOKING AT CUSTOMER AS A REF***
		}
	}

	private RankEarned findCarRankById(Long carId, Long rankId) {
		RankEarned rank = rankEarnedDao.findById(rankId)
				.orElseThrow(() -> new NoSuchElementException("The rank with Id = " + rankId + " was not found."));

		boolean found = false;
		for (Car car: rank.getCars()) {
			if (car.getCarId() == carId) {
				found = true;
				break;
				
			}
		}
		
		if(!found) {
			throw new IllegalArgumentException(
					"Rank with ID = " + rankId + " does not belong to car containing ID = " + carId);	
		}
	
		return rank;
	}
	// END CAR STUFF
	
	private void copyRankFields(RankEarned rank, RankEarnedData rankData) {
	rank.setRankId(rankData.getRankId());
	rank.setRankLevel(rankData.getRankLevel());
	}
	
	// figuring out the duplicate problem:
	// find rank by name
	// compare to player rank lv
//	private RankEarned findRankByPlayerId(Long playerId, Long rankId) {
////		RankEarned rank = rankEarnedDao.findById(rankId)
////				.orElseThrow(() -> new NoSuchElementException("The rank with Id = " + rankId + " was not found."));
//		
//		RankEarned rank = new RankEarned();
//		
//		// if car with Id is not found for that specific player, throw exception
//		if (rank.getPlayer().getPlayerId() == rankId) {
//			throw new IllegalStateException(
//					"Player with ID = " + playerId + " already contains the rank with = " + rankId);
//		}
//		return rank;
//	}
	
	@Transactional(readOnly = true)
	public List<RankEarnedData> retrieveAllRanks() {
	List<RankEarned> ranks = rankEarnedDao.findAll();
	
	List<RankEarnedData> response = new LinkedList<>();
	
	for(RankEarned rank : ranks) {
		response.add(new RankEarnedData(rank));
	}
	return response;
	}

	// trying to  fill up car_rank join table
	// could insert into SQL and use ARC to see join table
	// or create join with code
	
	// PSEUDOCODE:
	// 1 - see if player has rank for a car
	
	// 2 - see if rank level (string) already exists in rank tbl
	// see if car (ID / pk) has rank AKA does rank level (string) already exist?
		// yes - check if current car rank = new car rank
			// fetch that ID # for bronze/silver etc (the highest rank lv)
		// no - null = car rank? then take rank ID (pk) and all values assoc w/that rank ID

		
	
	// findCarById
//		private Car findCarById(Long rankId, Long carId) {
//			Car car = carDao.findById(carId).orElseThrow(
//					() -> new NoSuchElementException("The Car with Id = " + carId + " was not found. "));
//
//			boolean carFound = false;
//
//			// ... have to loop through player? because it's a set
//				// ***copied this from petstore 
//			for (PetStore petStore : car.getPetStores()) {
//				if (petStore.getPetStoreId() == petStoreId) {
//					storeFound = true;
//					break;
//				} else {
//					if (!storeFound) {
//						throw new IllegalStateException("Car with ID = " + carId
//								+ " does not work at pet store containing ID = " + petStoreId);
//					}
//				}
//
//			}
//			return car;
//		}

}
