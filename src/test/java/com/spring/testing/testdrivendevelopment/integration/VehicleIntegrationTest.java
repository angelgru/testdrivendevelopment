package com.spring.testing.testdrivendevelopment.integration;

import com.spring.testing.testdrivendevelopment.domain.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles(value = "test")
public class VehicleIntegrationTest {

    private TestRestTemplate testRestTemplate;

    @Before
    public void setup() {
        testRestTemplate = new TestRestTemplate();
    }

    @Test
    public void testGetVehicleList() {
//        Given
        String url = "http://localhost:8080/api/";
        String username = "admin@gmail.com";
        String password = "admin";

//        Then

        ResponseEntity<Vehicle[]> vehiclesResponse = testRestTemplate
                .withBasicAuth(username, password)
                .getForEntity(url, Vehicle[].class);
        List<Vehicle> vehicleList = Arrays.asList(vehiclesResponse.getBody());
        Assert.assertEquals(HttpStatus.OK, vehiclesResponse.getStatusCode());
        Assert.assertNotNull(vehicleList);
        Assert.assertEquals(2, vehicleList.size());
    }

    @Test
    public void getVehicle() {
//        Given
        String url = "http://localhost:8080/api/1";
        String username = "admin@gmail.com";
        String password = "admin";
        Vehicle vehicleEquals = new Vehicle(1L, "Skoda", "Felicia");

//        Then
        ResponseEntity<Vehicle> vehicle = testRestTemplate
                .withBasicAuth(username, password)
                .getForEntity(url, Vehicle.class);
        Assert.assertNotNull(vehicle.getBody());
        Assert.assertEquals(HttpStatus.OK, vehicle.getStatusCode());
        Assert.assertEquals(vehicleEquals, vehicle.getBody());
    }

    @Test
    public void addVehicle() {
//        Given
        String url = "http://localhost:8080/api/";
        String username = "admin@gmail.com";
        String password = "admin";
        Vehicle vehicleRequest = new Vehicle("Honda", "Civic");
        Vehicle vehicleResponse = new Vehicle(2L, "Honda", "Civic");

//        Then
        ResponseEntity<Vehicle> vehicleResponseEntity = testRestTemplate
                .withBasicAuth(username, password)
                .postForEntity(url, vehicleRequest, Vehicle.class);
        Assert.assertEquals(HttpStatus.OK, vehicleResponseEntity.getStatusCode());
        Assert.assertNotNull(vehicleResponseEntity.getBody());
        Assert.assertEquals(vehicleResponse, vehicleResponseEntity.getBody());
    }
}
