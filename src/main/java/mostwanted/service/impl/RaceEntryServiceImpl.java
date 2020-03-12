package mostwanted.service.impl;

import mostwanted.domain.dtos.import_.RaceEntryImportDto;
import mostwanted.domain.dtos.import_.RaceEntryImportRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.service.RaceEntryService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static mostwanted.common.Constants.*;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {

    private static final String RACE_ENTRIES_IMPORT_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/race-entries.xml";

    private final RaceEntryRepository raceEntryRepository;
    private final RacerRepository racerRepository;
    private final RaceRepository raceRepository;
    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, RacerRepository racerRepository, RaceRepository raceRepository, CarRepository carRepository, ValidationUtil validationUtil, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper) {
        this.raceEntryRepository = raceEntryRepository;
        this.racerRepository = racerRepository;
        this.raceRepository = raceRepository;
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return this.raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACE_ENTRIES_IMPORT_FILE_PATH);
    }

    @Override
    public String importRaceEntries() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();
        RaceEntryImportRootDto raceEntryImportRootDto =
                this.xmlParser.parseXml(RaceEntryImportRootDto.class, RACE_ENTRIES_IMPORT_FILE_PATH);

        for (RaceEntryImportDto raceEntryDto : raceEntryImportRootDto.getRaceEntryImportDtos()) {

            Car car = this.carRepository.findById(raceEntryDto.getCarId()).orElse(null);
            Racer racer = this.racerRepository.findByName(raceEntryDto.getRacerName()).orElse(null);

            if (!this.validationUtil.isValid(raceEntryDto) || car == null || racer == null) {
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            RaceEntry raceEntry = this.modelMapper.map(raceEntryDto, RaceEntry.class);
            raceEntry.setCar(car);
            raceEntry.setRacer(racer);
            raceEntry.setRace(null);
            raceEntry = this.raceEntryRepository.saveAndFlush(raceEntry);

            sb
                    .append(String.format(SUCCESSFUL_IMPORT_MESSAGE, raceEntry.getClass().getSimpleName(), raceEntry.getId()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
