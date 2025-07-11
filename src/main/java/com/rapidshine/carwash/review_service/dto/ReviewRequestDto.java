package com.rapidshine.carwash.review_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    private Long bookingId;
    private String washerEmail;
    private String customerEmail;
    private double rating;
    private String comment;

}
