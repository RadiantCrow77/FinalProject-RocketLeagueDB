package rocketleagueserver.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Player {
// Instance vars based on ERD
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long playerId; // PK

	// should be no duplicates on names, this is handled in the service **Might need to change this later
	@Column(unique = true)
	private String playerName;
	
	private Long hoursPlayed;
	private String playerBanner;
	private String playerBorder;
	private String playerAnthem;
	
	// set relationship w/cars, 1-many
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true) // not sure if PERSIST or ALL cascade type
	private Set<Car> cars = new HashSet<>();
	
	// set relationship w/ranks_earned, 1-many
//	@EqualsAndHashCode.Exclude
//	@ToString.Exclude
//	@OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true) // same
//	@Column(unique = true) // make sure no duplicate ranks
//	private Set<RankEarned> ranksEarned = new HashSet<>();

}

