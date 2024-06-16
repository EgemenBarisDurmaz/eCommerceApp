package com.example.ecommerceportal.service;

import com.example.ecommerceportal.model.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingService {
    List<Rating> findAll();
    Optional<Rating> findById(Long id);
    Rating save(Rating rating);
    void deleteById(Long id);
}
