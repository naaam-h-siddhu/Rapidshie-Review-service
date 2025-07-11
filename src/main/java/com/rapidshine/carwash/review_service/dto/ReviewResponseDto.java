package com.rapidshine.carwash.review_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {
    private Long bookingId;
    private String customerEmail;
    private String washerEmail;
    private double rating;
    private String comment;
    private LocalDateTime createdAt;
    private  String reply;

}
