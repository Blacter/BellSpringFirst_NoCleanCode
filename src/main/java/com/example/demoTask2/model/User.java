package com.example.demoTask2.model;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;

@Data
public class User {
    @NotNull
    @Length(min = 5, max = 128)
    private String login;
    @NotNull
    @Length(min = 5, max = 128)
    private String password;
    private String date;

    public User(String login, String password){
        this.login = login;
        this.password = password;
//        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}