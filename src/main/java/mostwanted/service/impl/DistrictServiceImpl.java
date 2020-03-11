package mostwanted.service.impl;

import mostwanted.repository.DistrictRepository;
import mostwanted.service.DistrictService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    public DistrictServiceImpl(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.count() > 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return null;
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        return null;
    }
}
