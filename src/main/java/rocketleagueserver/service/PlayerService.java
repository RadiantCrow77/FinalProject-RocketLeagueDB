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
			return findCarById(playerId, carId); // FIGURE THIS OUT NEXT, LOOKING AT CUSTOMER AS A REF***
		}
	}

	private Car findCarById(Long playerId, Long carId) {
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

	public RankEarnedData saveRank(Long playerId, RankEarnedData rankData) {
	Player player = findPlayerById(playerId);
	
	Long rankId = rankData.getRankId();
	RankEarned rankEarned = findOrCreateRank(playerId, rankId);

	copyRankFields(rankEarned, rankData);
//	findRankByPlayerId( playerId, rankId);
	// ^ duplicate problem
	
	// set rank in car
	rankEarned.setPlayer(player);


	player.getRanksEarned().add(rankEarned);

	RankEarned dbRank = rankEarnedDao.save(rankEarned);

	return new RankEarnedData(dbRank);
	
	}

	private RankEarned findOrCreateRank(Long playerId, Long rankId) {
		if (Objects.isNull(rankId)) {
			return new RankEarned();
		} else {
			return findRankById(playerId, rankId); // FIGURE THIS OUT NEXT, LOOKING AT CUSTOMER AS A REF***
		}
	}

	private RankEarned findRankById(Long playerId, Long rankId) {
		RankEarned rank = rankEarnedDao.findById(rankId)
				.orElseThrow(() -> new NoSuchElementException("The rank with Id = " + rankId + " was not found."));

		// if car with Id is not found for that specific player, throw exception
		if (rank.getPlayer().getPlayerId() != playerId) {
			throw new IllegalStateException(
					"Rank with ID = " + rankId + " does not belong to player containing ID = " + playerId);
		}
		return rank;
	}

	private void copyRankFields(RankEarned rank, RankEarnedData rankData) {
	rank.setRankId(rankData.getRankId());
	rank.setRankLevel(rankData.getRankLevel());
	}
	
	// figuring out the duplicate problem:
	// find rank by name
	// compare to player rank lv
	private RankEarned findRankByPlayerId(Long playerId, Long rankId) {
//		RankEarned rank = rankEarnedDao.findById(rankId)
//				.orElseThrow(() -> new NoSuchElementException("The rank with Id = " + rankId + " was not found."));
		
		RankEarned rank = new RankEarned();
		
		// if car with Id is not found for that specific player, throw exception
		if (rank.getPlayer().getPlayerId() == rankId) {
			throw new IllegalStateException(
					"Player with ID = " + playerId + " already contains the rank with = " + rankId);
		}
		return rank;
	}
	
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
	
	// findCarById
//		private Car findCarById(Long rankId, Long carId) {
//			Car car = carDao.findById(carId).orElseThrow(
//					() -> new NoSuchElementException("The Car with Id = " + carId + " was not found. "));
//
//			boolean storeFound = false;
//
//			// ... have to loop through petstore because it's a set
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
