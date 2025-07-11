package com.rapidshine.carwash.review_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reviews") // ✅ Correct
public class Review {
    @Id
    private String id;

    private Long bookingId;
    private String customerEmail;
    private String washerEmail;
    private double rating;
    private String comment;
    private LocalDateTime createdAt;
    private String reply; // for washer use\
    public Review(Long bookingId,String customerEmail,String washerEmail,double rating,String comment){
        this.bookingId = bookingId;
        this.customerEmail = customerEmail;
        this.washerEmail = washerEmail;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }
}
