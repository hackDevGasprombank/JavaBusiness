package com.example.gasprombankjavabusiness.dto.databaseHelper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private List<ReviewDto> reviews;
    private boolean hasMore; // зависит от API
}
