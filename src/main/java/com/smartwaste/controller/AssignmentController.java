package com.smartwaste.controller;

import com.smartwaste.model.Bin;
import com.smartwaste.model.Vehicle;
import com.smartwaste.repository.BinRepository;
import com.smartwaste.repository.VehicleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignment")
@CrossOrigin
public class AssignmentController {

    private final BinRepository binRepository;
    private final VehicleRepository vehicleRepository;

    public AssignmentController(
            BinRepository binRepository,
            VehicleRepository vehicleRepository) {

        this.binRepository = binRepository;
        this.vehicleRepository = vehicleRepository;
    }

    // Automatically find nearest vehicle for a bin
    @GetMapping("/auto/{binId}")
    public String autoAssignVehicle(@PathVariable int binId) {

        // Find bin
        Bin bin = binRepository.findById(binId)
                .orElseThrow(() -> new RuntimeException("Bin not found"));

        // Check fill level
        if (bin.getFillLevel() < 80) {
            return "Bin " + binId +
                    " does not need collection yet. Fill level: " +
                    bin.getFillLevel() + "%";
        }

        // Get all vehicles
        List<Vehicle> vehicles = vehicleRepository.findAll();

        Vehicle nearestVehicle = null;
        double shortestDistance = Double.MAX_VALUE;

        // Find nearest AVAILABLE vehicle
        for (Vehicle vehicle : vehicles) {

            if (vehicle.getStatus() != null &&
                    vehicle.getStatus().equalsIgnoreCase("AVAILABLE")) {

                double distance = calculateDistance(
                        bin.getLatitude(),
                        bin.getLongitude(),
                        vehicle.getLatitude(),
                        vehicle.getLongitude()
                );

                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearestVehicle = vehicle;
                }
            }
        }

        // No vehicle available
        if (nearestVehicle == null) {
            return "⚠️ No available vehicle found for Bin " + binId;
        }

        // Result
        return "🚛 Vehicle " +
                nearestVehicle.getVehicleNumber() +
                " assigned to Bin " +
                binId +
                " | Driver: " +
                nearestVehicle.getDriver() +
                " | Distance: " +
                String.format("%.2f", shortestDistance) +
                " km";
    }


    // Calculate distance between two GPS locations
    private double calculateDistance(
            double lat1,
            double lon1,
            double lat2,
            double lon2) {

        final double EARTH_RADIUS = 6371.0;

        double latDistance =
                Math.toRadians(lat2 - lat1);

        double lonDistance =
                Math.toRadians(lon2 - lon1);

        double a =
                Math.sin(latDistance / 2) *
                        Math.sin(latDistance / 2)
                        +
                        Math.cos(Math.toRadians(lat1)) *
                                Math.cos(Math.toRadians(lat2)) *
                                Math.sin(lonDistance / 2) *
                                Math.sin(lonDistance / 2);

        double c =
                2 * Math.atan2(
                        Math.sqrt(a),
                        Math.sqrt(1 - a)
                );

        return EARTH_RADIUS * c;
    }
}