package com.spring.testing.testdrivendevelopment.unit;

import com.spring.testing.testdrivendevelopment.domain.Vehicle;
import com.spring.testing.testdrivendevelopment.repository.VehicleRepository;
import com.spring.testing.testdrivendevelopment.service.VehicleServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VehicleServiceTest {

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddVehicle() {
//        Given
        String make = "Honda";
        String model = "Civic";
        Vehicle vehicleRequest = new Vehicle(make, model);
        Vehicle vehicleResponse = new Vehicle(1L, make, model);

//        When
        Mockito.when(vehicleRepository.save(vehicleRequest)).thenReturn(vehicleResponse);
        Vehicle vehicle = vehicleService.addVehicle(vehicleRequest);

//        Then
        Assert.assertEquals(vehicleResponse, vehicle);
        Mockito.verify(vehicleRepository).save(vehicleRequest);
    }

    @Test
    public void getVehicle() {
//        Given
        Long id = 1L;
        String make = "Honda";
        String model = "Civic";
        Optional<Vehicle> vehicleResponse = Optional.of(new Vehicle(1L, make, model));

//        When
        Mockito.when(vehicleRepository.findById(id)).thenReturn(vehicleResponse);
        Optional<Vehicle> vehicle = vehicleService.getVehicle(id);

//        Then
        Assert.assertEquals(vehicleResponse.get(), vehicle.get());
        Mockito.verify(vehicleRepository).findById(id);
    }

    @Test
    public void getVehicleList() {
//        Given
        Long id = 1L;
        String make = "Honda";
        String model = "Civic";
        Vehicle vehicle = new Vehicle(id, make, model);
        List<Vehicle> vehicleResponse = Collections.singletonList(vehicle);

//        When
        Mockito.when(vehicleRepository.findAll()).thenReturn(vehicleResponse);
        List<Vehicle> vehicleList = vehicleService.getVehicleList();

//        Then
        Assert.assertEquals(vehicleResponse, vehicleList);
        Assert.assertEquals(1, vehicleList.size());
        Mockito.verify(vehicleRepository).findAll();
    }
}
