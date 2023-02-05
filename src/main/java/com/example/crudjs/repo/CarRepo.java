package com.example.crudjs.repo;

import com.example.crudjs.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface CarRepo extends JpaRepository<Car, Long> {

    Optional<Car> getCarByBrandAndModel(String brand, String model);

    @Modifying
    @Transactional
    @Query("update Car c set c.brand = ?2, c.model = ?3, c.weight = ?4, c.isAvailable = ?5 where c.id = ?1")
    void updateCarById(Long id, String brand, String model, Double weight, Boolean isAvailable);
}
