package mostwanted.service.impl;

import mostwanted.repository.RaceEntryRepository;
import mostwanted.service.RaceEntryService;
import org.springframework.stereotype.Service;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {

    private final RaceEntryRepository raceEntryRepository;

    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository) {
        this.raceEntryRepository = raceEntryRepository;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return this.raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() {
        return null;
    }

    @Override
    public String importRaceEntries() {
        return null;
    }
}
