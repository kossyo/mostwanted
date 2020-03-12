package mostwanted.service.impl;

import mostwanted.repository.RaceEntryRepository;
import mostwanted.service.RaceEntryService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {

    private static final String RACE_ENTRIES_IMPORT_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/race-entries.xml";

    private final RaceEntryRepository raceEntryRepository;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;

    @Autowired
    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, ValidationUtil validationUtil, FileUtil fileUtil) {
        this.raceEntryRepository = raceEntryRepository;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
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
    public String importRaceEntries() {
        return null;
    }
}
