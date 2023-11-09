package rocketleagueserver.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class RankEarned {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rankId; // PK

// ranks???
// should this just be a singular String, rankNames? if so, fix ERD
	// enums -- ranks never changed
	@Column(unique = true)
	private String rankLevel;
	
// Relation between ranksEarned and players
// Use Join Column a.k.a. FK = playerId to build Entity relationship (1-Many; from the perspective of Players)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "player_id")
	private Player player;
	

// Relation between cars and ranksEarned, NOT SURE IF CORRECT
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "ranksEarned", cascade = CascadeType.PERSIST)
	 private Set<Car> cars = new HashSet<>();
}
