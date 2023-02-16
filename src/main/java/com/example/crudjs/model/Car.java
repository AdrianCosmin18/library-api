package com.example.crudjs.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity(name = "Car")
@Table(name = "car")
@Data
@NoArgsConstructor
public class Car {

    @Id
    @SequenceGenerator(name = "car_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_seq")
    private Long id;

    @Column(name = "brand", nullable = false)
    @Size(min = 2, message = "The brand length has to have minim 2 characters")
    @NotNull(message = "The brand is missing")
    private String brand;

    @Column(name = "model", nullable = false)
    @Size(min = 2, message = "Model's length must have minim 2 characters !")
    @NotNull(message = "The model is missing")
    private String model;

    @Column(name = "weight", nullable = false)
    @DecimalMin(value = "0.1" , message = "Weight value is missing !")
    private double weight;

    @Column(name = "isAvailable")
    private Boolean isAvailable;


    public Car(String brand, String model, Double weight, Boolean isAvailable) {
        this.brand = brand;
        this.model = model;
        this.weight = weight;
        this.isAvailable = isAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return Objects.equals(getId(), car.getId()) && Objects.equals(getBrand(), car.getBrand()) && Objects.equals(getModel(), car.getModel()) && Objects.equals(getWeight(), car.getWeight()) && Objects.equals(getIsAvailable(), car.getIsAvailable());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBrand(), getModel(), getWeight(), getIsAvailable());
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", weight=" + weight +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
