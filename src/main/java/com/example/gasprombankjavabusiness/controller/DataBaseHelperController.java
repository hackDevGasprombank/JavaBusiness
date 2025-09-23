package com.example.gasprombankjavabusiness.controller;

import com.example.gasprombankjavabusiness.anotions.WebController;
import com.example.gasprombankjavabusiness.dto.databaseHelper.DataBaseParamsRequestDto;
import com.example.gasprombankjavabusiness.dto.databaseHelper.ReviewResponse;
import com.example.gasprombankjavabusiness.serivce.DataBaseHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@WebController
@RequiredArgsConstructor
public class DataBaseHelperController {

    private final DataBaseHelperService helperService;

    @PostMapping("/backup")
    public ResponseEntity<Void> startGettingBackup(
            @RequestBody DataBaseParamsRequestDto dto) {

        helperService.getBackup(dto.pageCount(), dto.url());
        return ResponseEntity.ok().build();

    }


    @GetMapping("/reviews")
    public ResponseEntity<ReviewResponse> getReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size
    ) {

        return ResponseEntity.ok(helperService.getReviews(page, size));


    }

}
