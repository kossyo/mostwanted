package mostwanted.service.impl;

import mostwanted.repository.RaceRepository;
import mostwanted.service.RaceService;
import org.springframework.stereotype.Service;

@Service
public class RaceServiceImpl implements RaceService {

    private final RaceRepository raceRepository;

    public RaceServiceImpl(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @Override
    public Boolean racesAreImported() {
        return this.raceRepository.count() > 0;
    }

    @Override
    public String readRacesXmlFile() {
        return null;
    }

    @Override
    public String importRaces() {
        return null;
    }
}
