package com.rapidshine.carwash.review_service.service;

import com.rapidshine.carwash.review_service.dto.ReviewRequestDto;
import com.rapidshine.carwash.review_service.dto.ReviewResponseDto;
import com.rapidshine.carwash.review_service.exceptions.ReviewNotFoundException;
import com.rapidshine.carwash.review_service.messaging.rabbitmq.WasherRatingUpdatePublisher;
import com.rapidshine.carwash.review_service.model.Review;
import com.rapidshine.carwash.review_service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    private WasherRatingUpdatePublisher washerRatingUpdatePublisher;
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ResponseEntity<ReviewResponseDto> addReview(String email,ReviewRequestDto reviewRequestDto) {
        Review review = new Review(reviewRequestDto.getBookingId(), email, reviewRequestDto.getWasherEmail(), reviewRequestDto.getRating(),reviewRequestDto.getComment());
        Review savedReview = reviewRepository.save(review);
        washerRatingUpdatePublisher.updateWasherRating(reviewRequestDto.getWasherEmail(), reviewRequestDto.getRating());
        System.out.println("REview Request REcieved");
        return ResponseEntity.ok(mapToDto(savedReview));
    }

    public ResponseEntity<ReviewResponseDto> getReviewByBooking(Long id) {
        Review review = reviewRepository.findByBookingId(id)
                .orElseThrow(() -> new ReviewNotFoundException("No review found for booking ID: " + id));
        return ResponseEntity.ok(mapToDto(review));
    }

    public ResponseEntity<List<ReviewResponseDto>> getReviewByWasher(String email) {
        List<Review> reviews = reviewRepository.findByWasherEmail(email);
        List<ReviewResponseDto> responseList = reviews.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
    public ResponseEntity<List<ReviewResponseDto>> getReviewByCustomer(String email) {
        List<Review> reviews = reviewRepository.findByCustomerEmail(email);
        if (reviews.isEmpty()) {
            throw new ReviewNotFoundException("No reviews found for customer: " + email);
        }
        List<ReviewResponseDto> responseList = reviews.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<ReviewResponseDto> replyToReview(String id, String replyText) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found for ID: " + id));
        review.setReply(replyText);
        reviewRepository.save(review);
        return ResponseEntity.ok(mapToDto(review));
    }

    // Helper functions
    private Review mapToModel(ReviewRequestDto dto) {
        Review review = new Review();
        review.setBookingId(dto.getBookingId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setReply(null); // washer reply can be null at creation
        review.setCustomerEmail(dto.getCustomerEmail());
        review.setWasherEmail(dto.getWasherEmail());
        review.setCreatedAt(LocalDateTime.now());
        return review;
    }

    private ReviewResponseDto mapToDto(Review review) {
        return new ReviewResponseDto(
                review.getBookingId(),
                review.getCustomerEmail(),
                review.getWasherEmail(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt(),
                review.getReply()
        );
    }
}
