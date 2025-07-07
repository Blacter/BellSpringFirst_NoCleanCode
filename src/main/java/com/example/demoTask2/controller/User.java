package com.example.demoTask2.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@RestController
@RequestMapping(path="/", produces="application/json")
public class User {
    @GetMapping("/api")
    public String api_get(){
        String prefix = "GET /api" + " ";
        ResponseDelay.doRandomDelay(prefix);
        return "{\"login\": \"Login1\", \"status\": \"okk\" }";
    }

    @PostMapping("/api")
    public LoginPasswordDateBean api_post(@Valid @RequestBody LoginPasswordBean loginPasswordData){
        String prefix = "POST /api" + " ";
        ResponseDelay.doRandomDelay(prefix);
        return new LoginPasswordDateBean(loginPasswordData.getLogin(), loginPasswordData.getPassword());
    }
}

@Data
class LoginPasswordBean {
    @NotNull
    @Length(min = 5, max = 128)
    public String login;

    @NotNull
    @Length(min = 5, max = 128)
    public String password;
}

@Data
class LoginPasswordDateBean {
    private String login;
    private String password;
    private String date;

    public LoginPasswordDateBean(String login, String password){
        this.login = login;
        this.password = password;
        this.date = getCurrentDateTime();
    }

    private String getCurrentDateTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}

@Data
class LoginStatusBean {
    String login;
    String status;
}

class ResponseDelay {
    public static void doRandomDelay(String prefix) {
        long formMilliseconds = 1000;
        long toMilliseconds = 2000;
        Random random = new Random();
        long delayTime = formMilliseconds + random.nextLong(toMilliseconds - formMilliseconds);
        try {
            TimeUnit.MILLISECONDS.sleep(delayTime);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.printf(prefix + "DELAY_TIME: %d milliseconds\n", delayTime);
    }
}

@ControllerAdvice
class ErrorHandlingControllerAdvice  {
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e
    ){
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }
}

@Getter
@RequiredArgsConstructor
class ValidationErrorResponse {
    private final List<Violation> violations;
}

@Getter
@RequiredArgsConstructor
class Violation {
    private final String fieldName;
    private final String message;
}