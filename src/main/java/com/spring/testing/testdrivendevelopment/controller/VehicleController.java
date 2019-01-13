package com.spring.testing.testdrivendevelopment.controller;

import com.spring.testing.testdrivendevelopment.domain.Vehicle;
import com.spring.testing.testdrivendevelopment.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<List<Vehicle>> getVehicleList() {
        List<Vehicle> vehicleList = vehicleService.getVehicleList();
        return new ResponseEntity<>(vehicleList, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<Vehicle> getVehicle(@PathVariable("id") Long id) {
        Optional<Vehicle> vehicle = vehicleService.getVehicle(id);
        if(vehicle.isPresent()){
            log.error("PRESENT " + vehicle.get().getMake());
            return ResponseEntity.ok(vehicle.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/")
    ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok().body(vehicleService.addVehicle(vehicle));
    }
}
