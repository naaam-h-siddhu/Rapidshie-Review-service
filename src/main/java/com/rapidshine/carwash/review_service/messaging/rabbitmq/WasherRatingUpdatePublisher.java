package com.rapidshine.carwash.review_service.messaging.rabbitmq;


import com.rapidshine.carwash.review_service.dto.WasherRatingUpdateDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WasherRatingUpdatePublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void updateWasherRating(String email, double rating){
        WasherRatingUpdateDto washerRatingUpdateDto = new WasherRatingUpdateDto();
        washerRatingUpdateDto.setWasherEmail(email);
        washerRatingUpdateDto.setRating(rating);

        rabbitTemplate.convertAndSend(
                "review.washer.rating.update.queue",
                "review.washer.rating.update",
                washerRatingUpdateDto
        );
    }
}
