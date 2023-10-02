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
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Car {
// Instance vars based on ERD
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long carId; // PK
	
	private String carBodyName;
	private String carColor;
	private String carPaintFinish;
	
	// - start player - car relationship --
	// Use FK = player_id to create Entity Relationship (Many-1)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.PERSIST, optional = true)
	@JoinColumn(name = "player_id", nullable = true)
	private Player player; // points back to player as a JPA requirement
	// - end player - car relationship --
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	
	// Joins Table car_rank with car_id
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "car_rank", joinColumns = @JoinColumn(name = "car_id"), inverseJoinColumns = @JoinColumn(name = "rank_id"))
    private Set<RankEarned> ranksEarned = new HashSet<>();
	// for reference:
//	private Set<Customer> customers = new HashSet<>();
	
//	//  set employee annotations and relationship
//	@EqualsAndHashCode.Exclude
//	@ToString.Exclude
//	@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
//	private Set<Employee> employees = new HashSet<>();
	
//	@ManyToMany(cascade = CascadeType.PERSIST)
//	@JoinTable(name = "pet_store_customer", joinColumns = @JoinColumn(name = "pet_store_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
//	private Set<Customer> customers = new HashSet<>();
	
}
