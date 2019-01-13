package com.spring.testing.testdrivendevelopment.service;

import com.spring.testing.testdrivendevelopment.domain.Vehicle;
import com.spring.testing.testdrivendevelopment.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle addVehicle(Vehicle vehicle) {
        Vehicle saveObject = new Vehicle(vehicle.getMake(), vehicle.getModel());
        return vehicleRepository.save(saveObject);
    }

    @Override
    public List<Vehicle> getVehicleList() {
        return (List<Vehicle>) vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> getVehicle(Long id) {
        return vehicleRepository.findById(id);
    }
}
