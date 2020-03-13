package mostwanted.service.impl;

import mostwanted.domain.dtos.import_.EntryImportDto;
import mostwanted.domain.dtos.import_.RaceImportDto;
import mostwanted.domain.dtos.import_.RaceImportRootDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.service.RaceService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;

import static mostwanted.common.Constants.INCORRECT_DATA_MESSAGE;
import static mostwanted.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
public class RaceServiceImpl implements RaceService {

    private static final String RACES_IMPORT_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/races.xml";

    private final RaceRepository raceRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final DistrictRepository districtRepository;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public RaceServiceImpl(RaceRepository raceRepository, RaceEntryRepository raceEntryRepository, DistrictRepository districtRepository, ValidationUtil validationUtil, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper) {
        this.raceRepository = raceRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.districtRepository = districtRepository;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean racesAreImported() {
        return this.raceRepository.count() > 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACES_IMPORT_FILE_PATH);
    }

    @Override
    public String importRaces() throws JAXBException {

        StringBuilder sb = new StringBuilder();
        RaceImportRootDto raceImportRootDto = this.xmlParser.parseXml(RaceImportRootDto.class, RACES_IMPORT_FILE_PATH);

        for (RaceImportDto raceDto : raceImportRootDto.getRaceImportDtos()) {
            if (!this.validationUtil.isValid(raceDto)){
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            District district = this.districtRepository.findByName(raceDto.getDistrictName()).orElse(null);

            if (district == null || raceDto.getLaps() == null){
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            Race race = this.modelMapper.map(raceDto, Race.class);
            race.setDistrict(district);
            for (EntryImportDto entryImportDto : raceDto.getEntryImportRootDto().getEntryImportDtos()) {
//                raceEntry.setRace(null);
                RaceEntry raceEntry = this.raceEntryRepository.findById(entryImportDto.getId()).orElse(null);
                if (raceEntry != null){
                    raceEntry.setRace(race);
                    this.raceEntryRepository.saveAndFlush(raceEntry); //zakomentirai ako ima cascade
                }
            }
            this.raceRepository.saveAndFlush(race);
            sb
                    .append(String.format(SUCCESSFUL_IMPORT_MESSAGE, race.getClass().getSimpleName(), race.getId()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim() ;
    }
}
