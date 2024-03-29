package com.example.crudjs.service;

import com.example.crudjs.DTO.CarDTO;
import com.example.crudjs.exceptions.AlreadyExistsCarWithSameBrandAndModelException;
import com.example.crudjs.exceptions.CarNotFoundByBrandAndModel;
import com.example.crudjs.exceptions.CarNotFoundByIdException;
import com.example.crudjs.exceptions.CarsListEmptyException;
import com.example.crudjs.model.Car;
import com.example.crudjs.repo.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ThemeResolver;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepo carRepo;

    public List<Car> getCars(){
        List<Car> cars = carRepo.findAll();
        if (cars.isEmpty()){
            throw new CarsListEmptyException();
        }
        return cars;
    }

    public Car getCarById(Long id){
        return carRepo.findById(id)
                .orElseThrow(() -> new CarNotFoundByIdException(id));
    }

    public void deleteCarById(Long id){
        Car car = carRepo.findById(id)
                .orElseThrow(() -> new CarNotFoundByIdException(id));
        carRepo.deleteById(id);
    }

    public Car addCar(Car newCar){

        Optional<Car> car = carRepo.getCarByBrandAndModel(newCar.getBrand(), newCar.getModel());
        if(car.isPresent()){
            throw new AlreadyExistsCarWithSameBrandAndModelException(newCar.getBrand(), newCar.getModel());
        }
        Car car1 = carRepo.saveAndFlush(new Car(newCar.getBrand(), newCar.getModel(), newCar.getWeight(), newCar.getIsAvailable()));
        return car1;
    }

    public void updateCar(CarDTO carDTO, Long id){
        Car car = carRepo.findById(id)
                .orElseThrow(() -> new CarNotFoundByIdException(id));
        carRepo.updateCarById(id, carDTO.getBrand(), carDTO.getModel(), carDTO.getWeight(), carDTO.getIsAvailable());
    }

    public void updateCarByBrandAndModel(CarDTO carDTO, String oldBrand, String oldModel){
        Car car = carRepo.getCarByBrandAndModel(oldBrand, oldModel)
                .orElseThrow(CarNotFoundByBrandAndModel::new);
        carRepo.updateCarByBrandAndModel(oldBrand, oldModel, carDTO);
    }

    public Car getCarByBrandAndModel(String brand, String model){
        return this.carRepo.getCarByBrandAndModel(brand, model)
                .orElseThrow(() -> new CarNotFoundByBrandAndModel());
    }

    public void deleteCar(String brand, String model){
        if(this.carRepo.getCarByBrandAndModel(brand, model).isPresent()){
            this.carRepo.deleteCarByBrandAndModel(brand, model);

        }else{
            throw new CarNotFoundByBrandAndModel();

        }
    }
}
