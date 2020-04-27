package de.webshop.controller;

import de.webshop.dataTransferObjects.AddressData;
import de.webshop.dataTransferObjects.RegistrationData;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.UserDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RegistrationController {

    private final UserDbService userDbService;

    @Autowired
    public RegistrationController(final UserDbService userDbService) {
        this.userDbService = userDbService;
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new RegistrationData());
        model.addAttribute("userAdressData", new AddressData());
        return "registration/registration";
    }

    @PostMapping("/registration")
    public ModelAndView registerNewUser(@ModelAttribute RegistrationData registrationData, @ModelAttribute AddressData addressData) throws UserDbServiceException {
        registrationData.setAddressData(addressData);
        userDbService.registerNewUser(registrationData);
        return new ModelAndView("home");
    }
}
