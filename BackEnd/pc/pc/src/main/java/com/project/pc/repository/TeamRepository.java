package com.project.pc.repository;

import com.project.pc.model.Task;
import com.project.pc.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByTeamLeader(long id);
    Optional<Team> findByTeamName(String name);
    List<Team> findByActivityId(Long id);
}
