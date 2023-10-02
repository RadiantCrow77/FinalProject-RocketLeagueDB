package rocketleagueserver.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
	private String bronze;
	private String silver;
	private String gold;
	private String platinum;
	
//	private String rankName;

// Use Join Column a.k.a. FK = playerId to build Entity relationship (1-Many; from the perspective of Players)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "ranksEarned", cascade = CascadeType.PERSIST)
	private Set<Player> players = new HashSet<>();
	
	// for reference
//	@EqualsAndHashCode.Exclude
//	@ToString.Exclude
//	@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST) 
//	private Set<PetStore> petStores = new HashSet<>();

}
