package com.spring.testing.testdrivendevelopment.unit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.testing.testdrivendevelopment.config.TestSecurityConfig;
import com.spring.testing.testdrivendevelopment.controller.VehicleController;
import com.spring.testing.testdrivendevelopment.domain.Vehicle;
import com.spring.testing.testdrivendevelopment.service.VehicleService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(VehicleController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleService vehicleService;

    @Test
    public void testAddVehicle() throws Exception {
//        Given
        String username = "admin@gmail.com";
        String password = "admin";
        Long vehicleId = 1L;
        String vehicleMake = "Skoda";
        String vehicleModel = "Felicia";
        Vehicle addVehicleRequest = new Vehicle(vehicleMake, vehicleModel);
        Vehicle addVehicleResponse = new Vehicle(vehicleId, vehicleMake, vehicleModel);

//        When
        Mockito.when(vehicleService.addVehicle(ArgumentMatchers.any(Vehicle.class))).thenReturn(addVehicleResponse);

//        Then
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addVehicleRequest))
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(username,password)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make", CoreMatchers.is(vehicleMake)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", CoreMatchers.is(vehicleModel)))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Vehicle v = objectMapper.readValue(jsonResponse, Vehicle.class);

//        JUnit assertions
        Assert.assertNotNull(v);
        Assert.assertEquals(v.getMake(), vehicleMake);
        Assert.assertEquals(v.getModel(), vehicleModel);
    }

    @Test
    public void testGetVehicle() throws Exception {
//        Given
        Long vehicleId = 1L;
        String vehicleMake = "Skoda";
        String vehicleModel = "Felicia";
        Vehicle getVehicleResponse = new Vehicle(vehicleId, vehicleMake, vehicleModel);
        String adminUsername = "admin@gmail.com";
        String adminPassword = "admin";

//        When
        Mockito.when(vehicleService.getVehicle(1L)).thenReturn(Optional.of(getVehicleResponse));

//        Then
        MvcResult mvcResult =
                mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/{id}", 1)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.httpBasic(
                                        adminUsername,
                                        adminPassword)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make", CoreMatchers.is(vehicleMake)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", CoreMatchers.is(vehicleModel)))
                        .andReturn();

    }

    @Test
    public void testGetVehicleList() throws Exception {
//        Given
        String username = "admin@gmail.com";
        String password = "admin";
        Vehicle vehicle1 = new Vehicle(1L, "Skoda", "Felicia");
        Vehicle vehicle2 = new Vehicle(2L, "Honda", "Civic");
        List<Vehicle> getVehicleListResponse = Arrays.asList(vehicle1, vehicle2);

//        When
        Mockito.when(vehicleService.getVehicleList()).thenReturn(getVehicleListResponse);

//        Then
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/")
                .accept(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        String resultString = mvcResult.getResponse().getContentAsString();
        List<Vehicle> resultList = objectMapper.readValue(resultString, new TypeReference<List<Vehicle>>(){});
        Assert.assertEquals(resultList, getVehicleListResponse);
    }

    @Test
    public void testGetVehicleListUnathorized() throws Exception {
//        Given
        String username = "asd@gmail.com";
        String password = "admin";
        Vehicle vehicle1 = new Vehicle(1L, "Skoda", "Felicia");
        Vehicle vehicle2 = new Vehicle(2L, "Honda", "Civic");
        List<Vehicle> getVehicleListResponse = Arrays.asList(vehicle1, vehicle2);

//        When
        Mockito.when(vehicleService.getVehicleList()).thenReturn(getVehicleListResponse);

//        Then
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, password)))
                        .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                        .andReturn();
    }
}
