package de.webshop.controller;

import de.webshop.dataTransferObjects.AddressData;
import de.webshop.dataTransferObjects.RegistrationData;
import de.webshop.db.dataAccessObjects.UserRepository;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.UserDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class RegistrationController extends BaseController{
    private final String TEMPLATE_REGISTRATION = "registration/registration";
    private final String ROUTE_REGISTRATION = "/registration";
    private final String ROUTE_SUCCESS = "/success";

    private final UserDbService userDbService;
    private final UserRepository userRepository;

    @Autowired
    public RegistrationController(final UserDbService userDbService, UserRepository userRepository) {
        this.userDbService = userDbService;
        this.userRepository = userRepository;
    }

    @GetMapping(ROUTE_REGISTRATION)
    public String registrationPage(Model model) {
        model.addAttribute("registrationData", new RegistrationData());
        model.addAttribute("addressData", new AddressData());
        return TEMPLATE_REGISTRATION;
    }

    @PostMapping(ROUTE_REGISTRATION)
    public String registerNewUser(Model model, @Valid @ModelAttribute("registrationData") RegistrationData registrationData, BindingResult bindingResultRegistrationData,
                                  @Valid @ModelAttribute("addressData") AddressData addressData, BindingResult bindingResultAddressData) {
        if(bindingResultRegistrationData.hasErrors() || bindingResultAddressData.hasErrors()) {
            return TEMPLATE_REGISTRATION;
        } else {
            try {
                registrationData.setAddressData(addressData);
                if (userRepository.getUserByEmail(registrationData.getEmail()) == null) {
                    userDbService.registerNewUser(registrationData);
                } else {
                    model.addAttribute("message", "User with this Email Adress already exists!");
                    return TEMPLATE_REGISTRATION;
                }
            } catch (UserDbServiceException ex) {
                ex.printStackTrace();
            }
        }
        return redirect(ROUTE_SUCCESS);
    }
}
