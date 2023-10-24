package rocketleagueserver.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rocketleagueserver.entity.Player;

public interface PlayerDao extends JpaRepository<Player, Long> {

	Optional<Player> findByPlayerName(String playerName);

}
