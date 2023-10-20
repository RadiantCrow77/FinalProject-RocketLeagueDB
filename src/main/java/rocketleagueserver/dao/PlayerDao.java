package rocketleagueserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import rocketleagueserver.entity.Player;

public interface PlayerDao extends JpaRepository<Player, Long> {

}
