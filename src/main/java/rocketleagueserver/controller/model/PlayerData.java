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
	
	private Set<CarData> cars = new HashSet<>();
	private Set<RankEarnedData> ranksEarned = new HashSet<>();
	
	// constructor, takes Player in as param, set all matching fields in PlayerData class
	public PlayerData(Player player) {
		playerId = player.getPlayerId();
		playerName = player.getPlayerName();
		hoursPlayed = player.getHoursPlayed();
		playerBanner = player.getPlayerBanner();
		playerBorder = player.getPlayerBorder();
		playerAnthem = player.getPlayerAnthem();
		
// for loop needed for "main" class
		for(Car car: player.getCars()) {
		 CarData carData = new CarData();
		 // *************** was = new CarData(car);
		 cars.add(carData);
		}
		
		for(RankEarned rankEarned: player.getRanksEarned()) {
			RankEarnedData rankEarnedData = new RankEarnedData();
			ranksEarned.add(rankEarnedData);
		}
	}
	
	// Inner Classes for other entities
	
	// CAR CLASS
	@Data
	@NoArgsConstructor
	public static class CarData{
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
	public static class RankEarnedData{
		private Long rankId; 
		// ****took final off to fix NoArgsConstruct err
			private String BRONZE;
			private String SILVER;
			private String GOLD;
			private String PLATINUM;
			
			// Rank constructor and getters
			public RankEarnedData(RankEarned rankEarned) {
				rankId = rankEarned.getRankId();
				BRONZE = rankEarned.getBRONZE();
				SILVER = rankEarned.getSILVER();
				GOLD = rankEarned.getGOLD();
				PLATINUM = rankEarned.getPLATINUM();
			}
	}


} // end player class
