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
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pet.store.entity.Customer;
import pet.store.entity.Employee;

@Entity
@Data
public class Cars {
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
	
	// Many-to-Many Relationship with ranks earned => set
	// Not sure if correct???? vvvv
	private Set<RankEarned> ranksEarned = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	
	// Joins Table car_rank with car_id
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "car_rank", joinColumns = @JoinColumn(name = "car_id"), inverseJoinColumns = @JoinColumn(name = "rank_id"))
	

	// for reference:
//	private Set<Customer> customers = new HashSet<>();
	
//	//  set employee annotations and relationship
//	@EqualsAndHashCode.Exclude
//	@ToString.Exclude
//	@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
//	private Set<Employee> employees = new HashSet<>();
	
}
