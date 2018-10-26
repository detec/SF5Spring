package org.openbox.sf5.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openbox.sf5.dao.DVBRangeValuesRepository;
import org.openbox.sf5.dao.SatellitesRepository;
import org.openbox.sf5.dao.TranspondersRepository;
import org.openbox.sf5.dao.ValueOfTheCarrierFrequencyRepository;
import org.openbox.sf5.model.CarrierFrequency;
import org.openbox.sf5.model.DVBStandards;
import org.openbox.sf5.model.KindsOfPolarization;
import org.openbox.sf5.model.Polarization;
import org.openbox.sf5.model.RangesOfDVB;
import org.openbox.sf5.model.Satellites;
import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.model.TypesOfFEC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Component to parse transponder files.
 *
 * @author duplyk.a
 *
 */
@Service
public class IniReader {

    private Satellites currentSatellite;

    @Autowired
    private DVBRangeValuesRepository dvbRangeValuesRepository;

    @Autowired
    private ValueOfTheCarrierFrequencyRepository valueOfTheCarrierFrequencyRepository;

    @Autowired
    private TranspondersRepository transpondersRepository;

    @Autowired
    private SatellitesRepository satellitesRepository;

	private static final String REGEX = "(\\d{1,3})=(\\d{5}),(H|V|L|R),(\\d{4,5}),(\\d{2}),(DVB-S|S2),(QPSK|8PSK)(\\sACM)?";
	private Pattern pattern;
	private Matcher matcher;

	private boolean result = false;

	private String filePath;

	/**
	 * Accepts {@link MultipartFile} from controller
	 *
	 * @param file
	 *            {@link MultipartFile}
	 * @throws IOException
	 */
	public void readMultiPartFile(MultipartFile file) throws IOException {

		// create a temp file
		File temp = File.createTempFile("transponders", ".xml");
        Path path = Paths.get(temp.getAbsolutePath());
        Files.write(path, file.getBytes());

		// calling reader class
        setFilePath(path.toString());
		readData(); // doing import
	}

	/**
	 * Method used for unit test.
	 *
	 * @throws IOException
	 */
	public void readData() throws IOException {
		// Open the file

        Iterator<String> linesIterator = Files.readAllLines(Paths.get(filePath)).iterator();
		String strLine;

		// (\d{1,3})=(\d{5}),(H|V|L|R),(\d{4,5}),(\d{2,3}),(DVB-S|S2),(QPSK|8PSK)(\sACM)?

		// Read File Line By Line
        while (linesIterator.hasNext()) {
            strLine = linesIterator.next();
			if ("[SATTYPE]".equals(strLine)) {
                readSatData(linesIterator);
			}

			if ("[DVB]".equals(strLine)) {
                readTransponderData(linesIterator);
			}

		}
		result = true;

	}

    private void readSatData(Iterator<String> linesIterator) throws IOException {
        linesIterator.next(); // 1=0130
        String satline = linesIterator.next();

		String satName = satline.substring(2); // 2 characters

        Optional<Satellites> satOpional = this.satellitesRepository.findByName(satName);

        if (satOpional.isPresent()) {
            this.currentSatellite = satOpional.get();
		} else {
            currentSatellite = new Satellites(satName);
            currentSatellite = this.satellitesRepository.save(currentSatellite);
		}
	}

    private void readTransponderData(Iterator<String> linesIterator) throws IOException {

		// replace with Java core

        String transCountString = linesIterator.next().substring(2);

		int transCount = Integer.parseInt(transCountString);

		pattern = Pattern.compile(REGEX);

		for (int i = 1; i <= transCount; i++) {
            String transDataString = linesIterator.next();

			// Initialize

			matcher = pattern.matcher(transDataString);

			while (matcher.find()) {
				processTransponedrMatch();
			}
		}
	}

	private void processTransponedrMatch() {

		Transponders selectedTrans;

		// let's check, that it isn't Multistream
		String multistream = matcher.group(8);
		if (multistream != null) {
			return;
		}

		// frequency
		String frequencyString = matcher.group(2);

		Long frequency = Long.parseLong(frequencyString);

		// polarization
		Polarization aPolarization = Polarization.valueOf(matcher.group(3));

		// speed
		Long speed = Long.parseLong(matcher.group(4));

		// FEC
		TypesOfFEC fec = TypesOfFEC.valueOf("_" + matcher.group(5));

		// DVB standard
		DVBStandards dvbStandard = resolveDVBStandard();

		// define range
        RangesOfDVB rangeEnum = resolveTheDVBRangeValue(frequency);

        CarrierFrequency carrierEnum = resolveCarrierFrequency(frequency,
                Polarization.getPolarizationKind(aPolarization));

		Transponders newTrans = new Transponders(frequency, aPolarization, fec, carrierEnum, speed, dvbStandard,
                rangeEnum, currentSatellite);

		// let's check if such frequency already exists on the given
		// satellite
        List<Transponders> transList = getListOfTranspondersWithSameFrequency(frequency);

        if (transList.isEmpty()) {
            newTrans = this.transpondersRepository.save(newTrans);
        } else {
            selectedTrans = transList.get(0);

			// check if this trans changed to newly read trans
			if (!selectedTrans.equals(newTrans)) {
				// we should update all properties of the selected
				// trans.
				updateTransponderData(selectedTrans, carrierEnum, fec, frequency, aPolarization, rangeEnum, speed,
						dvbStandard);
			}
		}
	}

    private List<Transponders> getListOfTranspondersWithSameFrequency(Long frequency) {
        return this.transpondersRepository.findAllByFrequencyAndSatellite(frequency, currentSatellite);
	}

    private RangesOfDVB resolveTheDVBRangeValue(Long frequency) {
        return this.dvbRangeValuesRepository.findFrequencyBetweenThresholds(frequency).orElse(null);
	}

	private DVBStandards resolveDVBStandard() {
        Map<String, DVBStandards> dvbMap = DVBStandards.getConversionMap();
		String standard = matcher.group(6);
        return dvbMap.get(standard);
	}

    private CarrierFrequency resolveCarrierFrequency(Long frequency, KindsOfPolarization kindOfPolarization) {
        return this.valueOfTheCarrierFrequencyRepository
                .resolveByFrequencyAndPolarization(frequency, kindOfPolarization)
                .orElse(null);
	}

	private void updateTransponderData(Transponders selectedTrans, CarrierFrequency carrierEnum, TypesOfFEC fec,
			Long frequency, Polarization aPolarization, RangesOfDVB rangeEnum, Long speed, DVBStandards dvbStandard) {

		selectedTrans.setCarrier(carrierEnum);
		selectedTrans.setFEC(fec);
		selectedTrans.setFrequency(frequency);
		selectedTrans.setPolarization(aPolarization);
		selectedTrans.setRangeOfDVB(rangeEnum);
        selectedTrans.setSatellite(currentSatellite);
		selectedTrans.setSpeed(speed);
		selectedTrans.setVersionOfTheDVB(dvbStandard);
        this.transpondersRepository.save(selectedTrans);
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filepath) {
		this.filePath = filepath;
	}

}
