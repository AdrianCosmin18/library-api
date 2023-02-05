package com.example.crudjs.service;

import com.example.crudjs.DTO.CarDTO;
import com.example.crudjs.exceptions.AlreadyExistsCarWithSameBrandAndModelException;
import com.example.crudjs.exceptions.CarNotFoundByIdException;
import com.example.crudjs.exceptions.CarsListEmptyException;
import com.example.crudjs.model.Car;
import com.example.crudjs.repo.CarRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ThemeResolver;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private CarRepo carRepo;

    public CarService(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    public List<Car> getCars(){
        List<Car> cars = carRepo.findAll();
        if (cars.isEmpty()){
            throw new CarsListEmptyException();
        }
        return cars;
    }

    public Car getCarById(Long id){
        Car car = carRepo.findById(id)
                .orElseThrow(() -> new CarNotFoundByIdException(id));
        return car;
    }

    public void deleteCarById(Long id){
        Car car = carRepo.findById(id)
                .orElseThrow(() -> new CarNotFoundByIdException(id));
        carRepo.deleteById(id);
    }

    public void addCar(Car newCar){

        Optional<Car> car = carRepo.getCarByBrandAndModel(newCar.getBrand(), newCar.getModel());
        if(car.isPresent()){
            throw new AlreadyExistsCarWithSameBrandAndModelException(newCar.getBrand(), newCar.getModel());
        }
        carRepo.saveAndFlush(new Car(newCar.getBrand(), newCar.getModel(), newCar.getWeight(), newCar.getIsAvailable()));
    }

    public void updateCar(CarDTO carDTO, Long id){
        Car car = carRepo.findById(id)
                .orElseThrow(() -> new CarNotFoundByIdException(id));
        carRepo.updateCarById(id, carDTO.getBrand(), carDTO.getModel(), carDTO.getWeight(), carDTO.getIsAvailable());
    }
}
