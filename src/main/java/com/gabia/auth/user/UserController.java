package com.gabia.auth.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @RequestMapping("/api/profile")
    public ResponseEntity<List<UserProfile>> profile() {
        return ResponseEntity.ok(getUsers());
    }

    private List<UserProfile> getUsers() {
        List<UserProfile> users = new ArrayList<>();
        users.add(new UserProfile("abc", "abc@google.com"));
        users.add(new UserProfile("dfd", "dfdf@google.com"));
        users.add(new UserProfile("zcjdf2", "zcjdf2@google.com"));
        return users;
    }

}
