package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.domain.dtos.import_.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.service.TownService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import static mostwanted.common.Constants.*;

@Service
public class TownServiceImpl implements TownService {

    private static final String TOWNS_IMPORT_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/towns.json";

    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ValidationUtil validationUtil, FileUtil fileUtil, ModelMapper modelMapper, Gson gson) {
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.findAll().size() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return this.fileUtil.readFile(TOWNS_IMPORT_FILE_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) throws IOException {

        StringBuilder sb = new StringBuilder();
        TownImportDto[] townImportDtos = this.gson.fromJson(townsFileContent, TownImportDto[].class);
        for (TownImportDto townImportDto : townImportDtos) {

            // validate dto
            if (!this.validationUtil.isValid(townImportDto)) {
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            Town town = this.townRepository.findByName(townImportDto.getName()).orElse(null);

            //check if duplicate record
            if (town != null){
                sb.append(DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            town = this.modelMapper.map(townImportDto, Town.class);
            this.townRepository.saveAndFlush(town);

            sb
                    .append(String.format(SUCCESSFUL_IMPORT_MESSAGE, town.getClass().getSimpleName(), townImportDto.getName()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();

    }

    @Override
    public String exportRacingTowns() {
        return null;
    }
}
