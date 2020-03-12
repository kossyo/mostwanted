package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.domain.dtos.import_.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.service.CarService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static mostwanted.common.Constants.INCORRECT_DATA_MESSAGE;
import static mostwanted.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
public class CarServiceImpl implements CarService {

    private static final String CARS_IMPORT_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/cars.json";

    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, RacerRepository racerRepository, ValidationUtil validationUtil, FileUtil fileUtil, ModelMapper modelMapper, Gson gson) {
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean carsAreImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return this.fileUtil.readFile(CARS_IMPORT_FILE_PATH);
    }

    @Override
    public String importCars(String carsFileContent) {

        StringBuilder sb = new StringBuilder();
        CarImportDto[] carImportDtos = this.gson.fromJson(carsFileContent, CarImportDto[].class);

        for (CarImportDto carImportDto : carImportDtos) {
            Racer racer = this.racerRepository.findByName(carImportDto.getRacerName()).orElse(null);

            if (!this.validationUtil.isValid(carImportDto) || racer == null) {
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            Car car = this.modelMapper.map(carImportDto, Car.class);
            car.setRacer(racer);

            this.carRepository.saveAndFlush(car);

            String importedMssg = String.format("%s %s @ %d", car.getBrand(), car.getModel(), car.getYearOfProduction());
            sb
                    .append(String.format(SUCCESSFUL_IMPORT_MESSAGE, racer.getClass().getSimpleName(), importedMssg))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
