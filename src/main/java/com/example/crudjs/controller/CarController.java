package com.example.crudjs.controller;

import com.example.crudjs.DTO.CarDTO;
import com.example.crudjs.model.Car;
import com.example.crudjs.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/crudJS/car")
@Slf4j
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping()
    public List<Car> getCars(){
        log.info("Rest api to get all cars");
        return carService.getCars();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id){
        return carService.getCarById(id);
    }

    @PostMapping()
    public void addCar(@Valid @RequestBody Car car){
        this.carService.addCar(car);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id){
        this.carService.deleteCarById(id);
    }

    @PutMapping("/{id}")
    public void updateCar(@RequestBody CarDTO carDTO, @PathVariable Long id){
        this.carService.updateCar(carDTO, id);
    }

    @PutMapping("/update-car-by-brand-model")
    public void updateCarByBrandAndModel(@RequestParam(value = "brand")String brand, @RequestParam(value = "model")String model, @RequestBody CarDTO carDTO){
        this.carService.updateCarByBrandAndModel(carDTO, brand, model);
    }

    @GetMapping("/get-car-by-brand-model")
    public Car getCarByBrandAndModel(@RequestParam(value = "brand")String brand, @RequestParam(value = "model")String model){
        return this.carService.getCarByBrandAndModel(brand, model);
    }

    @DeleteMapping("/delete-car/{brand}/{model}")
    public void deleteCar(@PathVariable String brand, @PathVariable String model){
        this.carService.deleteCar(brand, model);
    }
}
