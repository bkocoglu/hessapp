package com.hessapp.api.controller;

import com.hessapp.api.dto.request.CreateSpendRequest;
import com.hessapp.api.model.Spend;
import com.hessapp.api.service.SpendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = SpendController.END_POINT)
public class SpendController {
    public static final String END_POINT = "/spend";
    private static final Logger log = LoggerFactory.getLogger(SpendController.class);

    private SpendService spendService;

    public SpendController(SpendService spendService){
        this.spendService = spendService;
    }

    @PostMapping()
    public ResponseEntity createSpend(@Valid @RequestBody CreateSpendRequest createSpendRequest) throws IllegalAccessException {
        Spend spend = spendService.createSpend(createSpendRequest);

        spendService.createActivities(spend);

        return ResponseEntity.ok().body(spend);
    }
}
