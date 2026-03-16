package com.coworking.coworking_technical_test.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coworking.coworking_technical_test.services.interfaces.ICuponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cupones")
@RequiredArgsConstructor
public class CuponController {
    
    private final ICuponService cuponService;

    @PostMapping("/redimir/{cuponId}")
    public void redimirCupon(Integer cuponId) {
        cuponService.redimirCupon(cuponId);
    }

}
