package com.example.crudjs.service;

import com.example.crudjs.DTO.CarDTO;
import com.example.crudjs.exceptions.AlreadyExistsCarWithSameBrandAndModelException;
import com.example.crudjs.exceptions.CarNotFoundByIdException;
import com.example.crudjs.exceptions.CarsListEmptyException;
import com.example.crudjs.model.Car;
import com.example.crudjs.repo.CarRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepo carRepo;

    @InjectMocks
    private CarService carService;

    @Captor
    private ArgumentCaptor<Car> argumentCaptor;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldGetCars(){
        List<Car> carList = new ArrayList<>();
        carList.add(new Car("dacia", "logan", 1100D, true));
        carList.add(new Car("BMW", "M4", 1630D, false));

        when(carRepo.findAll()).thenReturn(carList);
        assertThat(carService.getCars().size()).isEqualTo(carList.size());
    }
    
    
    @Test
    void shouldThrowExceptionGetCars(){
        when(carRepo.findAll()).thenReturn(new ArrayList<Car>());
        assertThrows(CarsListEmptyException.class, () -> this.carService.getCars());
    }

    @Test
    void shouldGetCarById(){

        Car car = new Car("dacia", "logan", 1100D, true);
        car.setId(1L);
        when(carRepo.findById(car.getId())).thenReturn(Optional.of(car));
        assertThat(this.carService.getCarById(car.getId())).isEqualTo(car);
    }

    @Test
    void shouldThrowExceptionGetCarById(){

        when(carRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundByIdException.class, () -> this.carService.getCarById(1L));
    }

    @Test
    void shouldDeleteCarById(){
        Car car = new Car("dacia", "logan", 1100D, true);
        car.setId(1L);

        when(carRepo.findById(car.getId())).thenReturn(Optional.of(car));
        carService.deleteCarById(car.getId());
        then(carRepo).should().deleteById(car.getId());
    }

    @Test
    void shouldAddCar(){
        Car car = new Car("dacia", "logan", 1100D, true);

        when(carRepo.getCarByBrandAndModel(car.getBrand(), car.getModel())).thenReturn(Optional.empty());
        carService.addCar(car);
        then(carRepo).should().saveAndFlush(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(new Car(car.getBrand(), car.getModel(), car.getWeight(), car.getIsAvailable()));
    }

    @Test
    void shouldThrowExceptionAddCar(){
        Car car = new Car("dacia", "logan", 1100D, true);

        when(carRepo.getCarByBrandAndModel(car.getBrand(), car.getModel())).thenReturn(Optional.of(car));
        assertThrows(AlreadyExistsCarWithSameBrandAndModelException.class, () -> carService.addCar(car));
    }

    @Test
    void shouldUpdateCar(){
        Car car = new Car("dacia", "logan", 1100D, true);
        car.setId(1L);

        CarDTO carDTO = new CarDTO("DACIA", "LOGAN 3", 1000D, false);

        when(carRepo.findById(car.getId())).thenReturn(Optional.of(car));
        carService.updateCar(carDTO, car.getId());
        then(carRepo).should().updateCarById(car.getId(), carDTO.getBrand(), carDTO.getModel(), carDTO.getWeight(), carDTO.getIsAvailable());
    }
}