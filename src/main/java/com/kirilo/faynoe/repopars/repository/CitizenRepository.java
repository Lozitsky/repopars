package com.kirilo.faynoe.repopars.repository;

import com.kirilo.faynoe.repopars.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Integer> {

    @Query("SELECT c FROM Citizen c WHERE c.idGame=?1")
    Citizen findByGameId(int gameId);
}
