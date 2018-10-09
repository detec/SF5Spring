package org.openbox.sf5.dao;

import java.util.Optional;

import org.openbox.sf5.model.CarrierFrequency;
import org.openbox.sf5.model.KindsOfPolarization;
import org.openbox.sf5.model.Polarization;
import org.openbox.sf5.model.ValueOfTheCarrierFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueOfTheCarrierFrequencyRepository
        extends JpaRepository<ValueOfTheCarrierFrequency, ValueOfTheCarrierFrequency.ValueOfTheCarrierFrequencyId> {

    @Query("SELECT typeOfCarrierFrequency FROM ValueOfTheCarrierFrequency " +
        "where (:Frequency between lowerThreshold and upperThreshold) " +
        "and (polarization = :KindOfPolarization)")
    Optional<CarrierFrequency> resolveByFrequencyAndPolarization(@Param("Frequency") Long frequency,
            @Param("KindOfPolarization") Polarization aPolarization);

    Optional<ValueOfTheCarrierFrequency> findByTypeOfCarrierFrequencyAndPolarization(
            CarrierFrequency typeOfCarrierFrequency, KindsOfPolarization polarization);
}
