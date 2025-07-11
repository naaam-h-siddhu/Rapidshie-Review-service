package com.rapidshine.carwash.review_service.repository;

import com.rapidshine.carwash.review_service.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review,String> {

    // find by customer
    List<Review> findByCustomerEmail(String email);
    // find by washer
    List<Review> findByWasherEmail(String email);
    // find by booking id
    Optional<Review> findByBookingId(Long bookingId);

}
