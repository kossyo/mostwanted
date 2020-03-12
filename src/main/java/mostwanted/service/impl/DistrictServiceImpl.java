package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.domain.dtos.import_.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.service.DistrictService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static mostwanted.common.Constants.*;

@Service
public class DistrictServiceImpl implements DistrictService {

    private static final String DISTRICTS_IMPORT_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/districts.json";

    private final DistrictRepository districtRepository;
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, TownRepository townRepository, ValidationUtil validationUtil, FileUtil fileUtil, ModelMapper modelMapper, Gson gson) {
        this.districtRepository = districtRepository;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.count() > 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return this.fileUtil.readFile(DISTRICTS_IMPORT_FILE_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        StringBuilder sb = new StringBuilder();
        DistrictImportDto[] districtImportDtos = this.gson.fromJson(districtsFileContent, DistrictImportDto[].class);

        for (DistrictImportDto districtImportDto : districtImportDtos) {
            if (!this.validationUtil.isValid(districtImportDto)){
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            District district = this.districtRepository.findByName(districtImportDto.getName()).orElse(null);
            //check if duplicate record
            if (district != null){
                sb.append(DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Town town = this.townRepository.findByName(districtImportDto.getTownName()).orElse(null);
            if (town == null){
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            district = this.modelMapper.map(districtImportDto, District.class);
            district.setTown(town);

            this.districtRepository.saveAndFlush(district);
            sb
                    .append(String.format(SUCCESSFUL_IMPORT_MESSAGE, district.getClass().getSimpleName(), districtImportDto.getName()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
