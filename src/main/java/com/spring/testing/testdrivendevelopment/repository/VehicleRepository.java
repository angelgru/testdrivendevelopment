package com.spring.testing.testdrivendevelopment.repository;

import com.spring.testing.testdrivendevelopment.domain.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
}
