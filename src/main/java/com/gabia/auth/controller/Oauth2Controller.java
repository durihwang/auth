package com.gabia.auth.controller;

import com.gabia.auth.dto.Oauth;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Oauth2Controller {

    @RequestMapping("/oauth/validation")
    public ResponseEntity<Oauth> Validation() {
        Oauth oauth = new Oauth();
        oauth.setMsg("Success");
        return new ResponseEntity<Oauth>(oauth, HttpStatus.OK);
    }
}
