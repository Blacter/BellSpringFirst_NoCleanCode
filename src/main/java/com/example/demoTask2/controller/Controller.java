package com.example.demoTask2.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.example.demoTask2.model.User;

@RestController
public class Controller {
    @GetMapping(value = "/api", produces="application/json")
    public String api_get(){
        doRandomDelay();
        return "{\"login\": \"Login1\", \"status\": \"ok\" }";
    }

    @PostMapping(value = "/api", produces="application/json")
    public User api_post(@Valid @RequestBody User loginPasswordData){
        doRandomDelay();
        loginPasswordData.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        return loginPasswordData;
    }

    public static void doRandomDelay() {
        Random random = new Random();
        long delayTime = 1000 + random.nextLong(1000);
        try {
            TimeUnit.MILLISECONDS.sleep(delayTime);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
