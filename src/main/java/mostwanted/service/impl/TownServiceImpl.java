package mostwanted.service.impl;

import mostwanted.repository.TownRepository;
import mostwanted.service.TownService;
import org.springframework.stereotype.Service;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;

    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.findAll().size() > 0;
    }

    @Override
    public String readTownsJsonFile() {
        return null;
    }

    @Override
    public String importTowns(String townsFileContent) {
        return null;
    }

    @Override
    public String exportRacingTowns() {
        return null;
    }
}
