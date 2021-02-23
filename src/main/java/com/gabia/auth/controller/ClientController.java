package com.gabia.auth.controller;

import com.gabia.auth.dto.BasicClientInfo;
import com.gabia.auth.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/register")
    public ModelAndView register(ModelAndView mv) {
        mv.setViewName("client/register");
        mv.addObject("registry", new BasicClientInfo());
        return mv;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(ModelAndView mv) throws Exception {
        List<ClientDetails> clientDetails = clientService.find();
        mv.addObject("applications", clientDetails);
        return mv;
    }

    @GetMapping("/remove")
    public ModelAndView remove(@RequestParam(value = "client_id", required = false) String clientId) throws Exception {

        clientService.remove(clientId);
        List<ClientDetails> clientDetails = clientService.find();
        ModelAndView mv = new ModelAndView("redirect:/client/dashboard");
        mv.addObject("applications", clientDetails);
        return mv;
    }

    @PostMapping("/save")
    public ModelAndView save(@Validated BasicClientInfo basicClientInfo, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("client/register");
        }

        clientService.save(basicClientInfo);
        List<ClientDetails> clientDetails = clientService.find();

        // create client details in database
        ModelAndView mv = new ModelAndView("redirect:/client/dashboard");
        mv.addObject("applications", clientDetails);

        return mv;
    }
}
