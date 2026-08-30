package com.smartwaste.controller;

import com.smartwaste.model.Vehicle;
import com.smartwaste.repository.VehicleRepository;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin
public class VehicleController {

    private final VehicleRepository vehicleRepository;


    // Constructor
    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }


    // GET ALL VEHICLES
    @GetMapping
    public List<Vehicle> getAllVehicles() {

        return vehicleRepository.findAll();

    }


    // ADD VEHICLE
    @PostMapping
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {

        vehicle.setLastUpdated(
                LocalDateTime.now().toString()
        );

        return vehicleRepository.save(vehicle);

    }


    // GET VEHICLE BY ID
    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable int id) {

        return vehicleRepository
                .findById(id)
                .orElse(null);

    }


    // UPDATE VEHICLE
    @PutMapping("/{id}")
    public Vehicle updateVehicle(
            @PathVariable int id,
            @RequestBody Vehicle vehicle) {

        Vehicle existingVehicle =
                vehicleRepository
                        .findById(id)
                        .orElse(null);

        if (existingVehicle == null) {
            return null;
        }


        existingVehicle.setVehicleNumber(
                vehicle.getVehicleNumber()
        );

        existingVehicle.setDriver(
                vehicle.getDriver()
        );

        existingVehicle.setVehicleType(
                vehicle.getVehicleType()
        );

        existingVehicle.setLatitude(
                vehicle.getLatitude()
        );

        existingVehicle.setLongitude(
                vehicle.getLongitude()
        );

        existingVehicle.setStatus(
                vehicle.getStatus()
        );

        existingVehicle.setLastUpdated(
                LocalDateTime.now().toString()
        );


        return vehicleRepository.save(
                existingVehicle
        );
    }


    // DELETE VEHICLE
    @DeleteMapping("/{id}")
    public String deleteVehicle(
            @PathVariable int id) {

        vehicleRepository.deleteById(id);

        return "Vehicle deleted successfully";
    }
}