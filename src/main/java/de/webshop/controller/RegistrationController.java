package de.webshop.controller;

import de.webshop.dataTransferObjects.AddressData;
import de.webshop.dataTransferObjects.RegistrationData;
import de.webshop.db.dataAccessObjects.UserRepository;
import de.webshop.entities.User;
import de.webshop.entities.VerificationToken;
import de.webshop.eventlisteners.RegistrationEmailListener;
import de.webshop.eventpublishers.RegistrationEmailEventPublisher;
import de.webshop.services.MailService;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.MailServiceException;
import de.webshop.services.exceptions.UserDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class RegistrationController extends BaseController {
    private final String TEMPLATE_REGISTRATION = "registration/registration";
    private final String ROUTE_REGISTRATION = "/registration";

    private final UserDbService userDbService;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final RegistrationEmailListener emailListener;

    @Autowired
    public RegistrationController(final UserDbService userDbService, UserRepository userRepository, MailService mailService, RegistrationEmailListener emailListener) {
        this.userDbService = userDbService;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.emailListener = emailListener;
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
        if (bindingResultRegistrationData.hasErrors() || bindingResultAddressData.hasErrors()) {
            return TEMPLATE_REGISTRATION;
        } else {
            try {
                registrationData.setAddressData(addressData);
                if (userRepository.getUserByEmail(registrationData.getEmail()) != null) {
                    model.addAttribute("message", "User with this Email Address already exists!");
                    return TEMPLATE_REGISTRATION;
                } else {
                    User newUser = userDbService.registerNewUser(registrationData);
                    new RegistrationEmailEventPublisher(newUser, "asdf");
                    //mailService.sendVerificationMail(registrationData.getEmail());
                }
            } catch (UserDbServiceException ex) {
                ex.printStackTrace();
            }
        }
        return redirect("home");
    }

}
