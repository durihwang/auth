package com.gabia.auth.controller;

import com.gabia.auth.dto.Oauth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Oauth2Controller {

    @RequestMapping(value = "/oauth/validation", method = RequestMethod.POST)
    public ResponseEntity<Oauth> Validation() {
        Oauth oauth = new Oauth();
        oauth.setMsg("Success");
        return new ResponseEntity<Oauth>(oauth, HttpStatus.OK);
    }
}
