package rocketleagueserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import rocketleagueserver.entity.Car;

public interface CarDao extends JpaRepository<Car, String> {

}
