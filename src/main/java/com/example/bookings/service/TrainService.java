package com.example.bookings.service;

import com.example.bookings.model.Train;
import com.example.bookings.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainService {
    private final TrainRepository trainRepository;

    @Autowired
    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }


    public Train getTrainById(Long id) {
        return trainRepository.findById(id).orElse(null);
    }
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public Train saveTrain(Train train) {
        return trainRepository.save(train);
    }

    public void deleteTrain(Long id) {
        trainRepository.deleteById(id);
    }


    }
