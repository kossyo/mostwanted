package mostwanted.service.impl;

import mostwanted.repository.CarRepository;
import mostwanted.service.CarService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Boolean carsAreImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return null;
    }

    @Override
    public String importCars(String carsFileContent) {
        return null;
    }
}
