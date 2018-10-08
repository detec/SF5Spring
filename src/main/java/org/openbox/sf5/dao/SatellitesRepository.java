package org.openbox.sf5.dao;

import java.util.Optional;

import org.openbox.sf5.model.Satellites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SatellitesRepository extends JpaRepository<Satellites, Long> {

    Optional<Satellites> findByName(String name);
}
