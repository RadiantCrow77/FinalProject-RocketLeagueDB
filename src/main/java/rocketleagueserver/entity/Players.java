package rocketleagueserver.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pet.store.entity.Customer;
import pet.store.entity.Employee;

@Entity
@Data
public class Players {
// Instance vars based on ERD
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long playerId; // PK

	private String playerName;
	private Long hoursPlayed;
	private String playerBanner;
	private String playerBorder;
	private String playerAnthem;
	
	// set relationship w/cars, 1-many
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Car> cars = new HashSet<>();
	
	// set relationship w/ranks_earned
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<rankEarned> ranksEarned = new HashSet<>();
	
	// for reference
	// set customer annotations and relationship
//			@ManyToMany(cascade = CascadeType.PERSIST)
//			@JoinTable(name = "pet_store_customer", joinColumns = @JoinColumn(name = "pet_store_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
//			private Set<Customer> customers = new HashSet<>();
//			
//			//  set employee annotations and relationship
//			@EqualsAndHashCode.Exclude
//			@ToString.Exclude
	// petStore = player
	// Employee = rankEarned
//			@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
//			private Set<Employee> employees = new HashSet<>();
}

