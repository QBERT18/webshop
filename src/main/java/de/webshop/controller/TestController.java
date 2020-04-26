package de.webshop.controller;

import de.webshop.dataTransferObjects.AddressData;
import de.webshop.dataTransferObjects.RegistrationData;
import de.webshop.entities.User;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.UserDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class TestController {

    private final UserDbService userDbService;

    @Autowired
    public TestController(final UserDbService userDbService) {
        this.userDbService = userDbService;
    }

    @GetMapping("/getEmailTest")
    public ModelAndView getByEmail() throws UserDbServiceException {
        Optional<User> optionalUser = userDbService.getUserByEmail("testmail@host.de");
        throw new UserDbServiceException("get by email" + optionalUser.toString());
    }

    @RequestMapping(value = "/registerTest", method = RequestMethod.GET)
    public ModelAndView registerNewUser() throws UserDbServiceException {
        final RegistrationData data = new RegistrationData();
        final AddressData addressData = new AddressData();
        data.setEmail("testmail@host.de");
        data.setPassword("testPassword");
        data.setFirstName("Benjamin");
        data.setLastName("Blümchen");
        addressData.setCity("Bielefeld");
        addressData.setCountryCode("DE");
        addressData.setZipCode("33689");
        addressData.setStreet("Hinterm Bahnhof 23");
        data.setAddressData(addressData);
        userDbService.registerNewUser(data);

        return new ModelAndView("home");
    }
}
