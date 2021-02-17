package com.gabia.auth.client;

import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientRegistrationService clientRegistrationService;

    public ClientController(ClientRegistrationService clientRegistrationService) {
        this.clientRegistrationService = clientRegistrationService;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView mv) {
        mv.setViewName("client/register");
        mv.addObject("registry", new BasicClientInfo());

        return mv;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(ModelAndView mv) {
        mv.addObject("applications",
                clientRegistrationService.listClientDetails());
        return mv;
    }

    @GetMapping("/remove")
    public ModelAndView remove(
            @RequestParam(value = "client_id", required = false) String clientId) {

        clientRegistrationService.removeClientDetails(clientId);

        ModelAndView mv = new ModelAndView("redirect:/client/dashboard");
        mv.addObject("applications",
                clientRegistrationService.listClientDetails());
        return mv;
    }

    @PostMapping("/save")
    public ModelAndView save(@Validated BasicClientInfo clientDetails,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("client/register");
        }

        Application app = new Application();
        app.setName(clientDetails.getName());
        app.addRedirectUri(clientDetails.getRedirectUri());
        app.setClientType(ClientType.valueOf(clientDetails.getClientType()));
        app.setClientId(UUID.randomUUID().toString());
        app.setClientSecret("{noop}"+UUID.randomUUID().toString());
        app.setAccessTokenValidity(3000);
        app.addScope("read_profile");
        app.addScope("read_contacts");

        clientRegistrationService.addClientDetails(app);

        // create client details in database
        ModelAndView mv = new ModelAndView("redirect:/client/dashboard");
        mv.addObject("applications",
                clientRegistrationService.listClientDetails());

        return mv;
    }
}
