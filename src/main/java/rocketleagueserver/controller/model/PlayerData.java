package rocketleagueserver.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import rocketleagueserver.entity.Car;
import rocketleagueserver.entity.Player;
import rocketleagueserver.entity.RankEarned;

@Data
@NoArgsConstructor
public class PlayerData {
	// Data Copied from Player Entity
	private Long playerId;

	private String playerName;
	private Long hoursPlayed;
	private String playerBanner;
	private String playerBorder;
	private String playerAnthem;

	Set<CarData> cars = new HashSet<>();
	Set<RankEarnedData> ranksEarned = new HashSet<>();

	// constructor, takes Player in as param, set all matching fields in PlayerData
	// class
	public PlayerData(Player player) {
		playerId = player.getPlayerId();
		playerName = player.getPlayerName();
		hoursPlayed = player.getHoursPlayed();
		playerBanner = player.getPlayerBanner();
		playerBorder = player.getPlayerBorder();
		playerAnthem = player.getPlayerAnthem();

// for loop needed for "main" class
		for (Car car : player.getCars()) {
			CarData carData = new CarData(car);
			// *************** was = new CarData(car);
			cars.add(carData);
		}
	}

	// Inner Classes for other entities

	// CAR CLASS
	@Data
	@NoArgsConstructor
	public static class CarData {
		private Long carId;

		private String carBodyName;
		private String carColor;
		private String carPaintFinish;

		// Car constructor and getters
		public CarData(Car car) {
			carId = car.getCarId();
			carBodyName = car.getCarBodyName();
			carColor = car.getCarColor();
			carPaintFinish = car.getCarPaintFinish();
		}
		// - end player - car relationship --
	}

	// RANKS CLASS
	@Data
	@NoArgsConstructor
	public static class RankEarnedData {
		private Long rankId;
		public String rankLevel;

		// Rank constructor and getters
		public RankEarnedData(RankEarned rankEarned) {
			rankId = rankEarned.getRankId();
			rankLevel = rankEarned.getRankLevel();
		}
	}

	// Car Ranks Class, not sure if correct***
	@Data
	@NoArgsConstructor
	public static class CarRankData {
		private long rankId;
		private long carId;

		// constructor + getters
		public CarRankData(CarRankData carRankData) {
			// create
			carId = carRankData.getCarId();
			rankId = carRankData.getRankId();

		}
	}

} // end player class
