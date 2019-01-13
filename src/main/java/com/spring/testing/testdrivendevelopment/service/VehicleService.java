package com.spring.testing.testdrivendevelopment.service;

import com.spring.testing.testdrivendevelopment.domain.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface VehicleService {

    Vehicle addVehicle(Vehicle vehicle);

    List<Vehicle> getVehicleList();

    Optional<Vehicle> getVehicle(Long id);

}
