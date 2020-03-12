package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.domain.dtos.import_.RacerImportDto;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.service.RacerService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static mostwanted.common.Constants.*;

@Service
public class RacerServiceImpl implements RacerService {

    private static final String RACERS_IMPORT_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/racers.json";

    private final RacerRepository racerRepository;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;


    @Autowired
    public RacerServiceImpl(RacerRepository racerRepository, ValidationUtil validationUtil, FileUtil fileUtil,
                            TownRepository townRepository, ModelMapper modelMapper, Gson gson) {
        this.racerRepository = racerRepository;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean racersAreImported() {
        return this.racerRepository.count() > 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return this.fileUtil.readFile(RACERS_IMPORT_FILE_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) {

        StringBuilder sb = new StringBuilder();
        RacerImportDto[] racerImportDtos = this.gson.fromJson(racersFileContent, RacerImportDto[].class);

        for (RacerImportDto racerImportDto : racerImportDtos) {
            if (!this.validationUtil.isValid(racerImportDto)) {
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            Racer racer = this.racerRepository.findByName(racerImportDto.getName()).orElse(null);
            if (racer != null) {
                sb.append(DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            Town town = this.townRepository.findByName(racerImportDto.getHomeTown()).orElse(null);
            if (town == null) {
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            racer = this.modelMapper.map(racerImportDto, Racer.class);
            racer.setHomeTown(town);

            this.racerRepository.saveAndFlush(racer);

            sb
                    .append(String.format(SUCCESSFUL_IMPORT_MESSAGE, racer.getClass().getSimpleName(), racerImportDto.getName()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public String exportRacingCars() {
        return null;
    }
}
