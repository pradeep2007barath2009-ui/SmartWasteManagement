package com.smartwaste.service;

import com.smartwaste.model.Bin;
import com.smartwaste.repository.BinRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BinService {

    private final BinRepository binRepository;

    public BinService(BinRepository binRepository) {
        this.binRepository = binRepository;
    }

    public List<Bin> getAllBins() {
        return binRepository.findAll();
    }

    public Bin getBinById(int id) {
        return binRepository.findById(id).orElse(null);
    }

    public Bin saveBin(Bin bin) {
        return binRepository.save(bin);
    }

    public void deleteBin(int id) {
        binRepository.deleteById(id);
    }
}
