package com.smartwaste.controller;

import com.smartwaste.model.Bin;
import com.smartwaste.service.BinService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bins")
public class BinController {

    private final BinService binService;

    public BinController(BinService binService) {
        this.binService = binService;
    }

    // Get all bins
    @GetMapping
    public List<Bin> getAllBins() {
        return binService.getAllBins();
    }

    // Get bin by ID
    @GetMapping("/{id}")
    public Bin getBinById(@PathVariable int id) {
        return binService.getBinById(id);
    }

    // Add a new bin
    @PostMapping
    public Bin addBin(@RequestBody Bin bin) {
        return binService.saveBin(bin);
    }

    // Delete a bin
    @DeleteMapping("/{id}")
    public String deleteBin(@PathVariable int id) {
        binService.deleteBin(id);
        return "Bin deleted successfully";
    }
}