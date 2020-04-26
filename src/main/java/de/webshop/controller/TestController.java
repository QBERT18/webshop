package de.webshop.controller;

import de.webshop.dataTransferObjects.RegistrationData;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.UserDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    private final UserDbService userDbService;

    @Autowired
    public TestController(final UserDbService userDbService) {
        this.userDbService = userDbService;
    }

    @RequestMapping(value = "/registerTest", method = RequestMethod.GET)
    public ModelAndView registerNewUser() throws UserDbServiceException {
        RegistrationData data = new RegistrationData();
        data.setEmail("testmail@host.de");
        data.setPassword("testPassword");
        userDbService.registerNewUser(data);

        return new ModelAndView("helloworld");
    }
}
