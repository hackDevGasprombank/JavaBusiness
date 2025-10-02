package com.example.gasprombankjavabusiness.controller;

import com.example.gasprombankjavabusiness.anotions.WebController;
import com.example.gasprombankjavabusiness.dto.databaseHelper.DataBaseParamsRequestDto;
import com.example.gasprombankjavabusiness.dto.databaseHelper.ReviewPushingDto;
import com.example.gasprombankjavabusiness.dto.databaseHelper.TopicPushingDto;
import com.example.gasprombankjavabusiness.serivce.DataBaseHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@WebController(value = "/database-helper")
@RequiredArgsConstructor
public class DataBaseHelperController {

    private final DataBaseHelperService helperService;

    @PostMapping("/push-backup")
    public ResponseEntity<Void> startPushingBackup(
            @RequestBody DataBaseParamsRequestDto dto) {

        helperService.startPushingBackup(dto.pageSize(), dto.url());
        return ResponseEntity.ok().build();

    }

    @PostMapping("/reviews")
    public ResponseEntity<Void> saveReviews(
            @RequestBody List<ReviewPushingDto> dto) {

        helperService.saveLocalReviews(dto);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/topics")
    public ResponseEntity<Void> saveTopics(
            @RequestBody List<TopicPushingDto> dto) {

        helperService.saveLocalTopics(dto);
        return ResponseEntity.ok().build();

    }

}
