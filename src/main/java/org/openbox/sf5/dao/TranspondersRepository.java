package org.openbox.sf5.dao;

import java.util.List;

import org.openbox.sf5.model.Satellites;
import org.openbox.sf5.model.Transponders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranspondersRepository extends JpaRepository<Transponders, Long> {

    List<Transponders> findAllByFrequencyAndSatellite(Long frequency, Satellites sat);
}
