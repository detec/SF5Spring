package org.openbox.sf5.dao;

import java.util.Optional;

import org.openbox.sf5.model.RangesOfDVB;
import org.openbox.sf5.model.TheDVBRangeValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DVBRangeValuesRepository extends JpaRepository<TheDVBRangeValues, RangesOfDVB> {

    @Query("SELECT rangeOfDVB FROM TheDVBRangeValues where :Frequency between lowerThreshold and upperThreshold")
    Optional<RangesOfDVB> findFrequencyBetweenThresholds(@Param("Frequency") Long frequency);

}
