package rocketleagueserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import rocketleagueserver.entity.RankEarned;

public interface CarRankDao extends JpaRepository<RankEarned, Long>{

}
