package com.rapidshine.carwash.review_service.controller;

import com.netflix.discovery.converters.Auto;
import com.rapidshine.carwash.review_service.dto.ReviewRequestDto;
import com.rapidshine.carwash.review_service.dto.ReviewResponseDto;
import com.rapidshine.carwash.review_service.model.Review;
import com.rapidshine.carwash.review_service.service.ReviewService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<ReviewResponseDto> addReview(Authentication authentication,@RequestBody ReviewRequestDto reviewRequestDto){
        return reviewService.addReview(authentication.getName(),reviewRequestDto);
    }

    @GetMapping("/by-booking/{id}")
    public ResponseEntity<ReviewResponseDto> getReviewByBooking(@PathVariable("id") Long id){
        return reviewService.getReviewByBooking(id);
    }

    @GetMapping("/by-washer/{email}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewByWasher(@PathVariable("email") String email){
        return reviewService.getReviewByWasher(email);
    }
    @GetMapping("/by-customer")
    public ResponseEntity<List<ReviewResponseDto>> getReviewByCustomer(Authentication authentication) {
        return reviewService.getReviewByCustomer(authentication.getName());
    }

    @PutMapping("/reply/{id}")
    public ResponseEntity<ReviewResponseDto> reply(
            @PathVariable String id,
            @RequestBody String replyText) {
        return reviewService.replyToReview(id, replyText);
    }


    public void add(){
        List<Integer> arr = Arrays.asList(2,2,2,2,3,4,5,5);
        Map<Integer,Integer> mp = new HashMap<>();
        for(Integer it: arr){
            if(mp.containsKey(it)){
                mp.put(it,mp.get(it)+1);
            }
            else{
                mp.put(it,1);
            }
            mp.put(it,mp.getOrDefault(it,0)+1);
        }

        for(Map.Entry<Integer,Integer> entry: mp.entrySet()){
            if(entry.getValue() > 1 && entry.getValue() %2 ==1){
                System.out.println(entry.getKey());
            }
        }
    }
}
