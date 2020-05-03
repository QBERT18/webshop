package de.webshop.controller;

import de.webshop.constants.UserPermission;
import de.webshop.entities.User;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.UserDbServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class SuccessController extends BaseController {

    private final UserDbService userDbService;

    public SuccessController(UserDbService userDbService) {
        this.userDbService = userDbService;
    }

    @GetMapping("/registrationSuccess")
    public String registrationSuccess() {
        return "success/registrationSuccess";
    }

    @GetMapping("/verificationSuccess")
    public String verificationSuccess(@RequestParam(value = "token") String token) throws UserDbServiceException {
        Optional<User> oldUser = userDbService.getUserByToken(token);
        if (oldUser.isPresent()) {
            User newUser = oldUser.get().deepCopy();
            newUser.setEnabled(true);
            newUser.setUserPermission(UserPermission.STANDARD);
            userDbService.updateUser(oldUser.get(), newUser);
        } else {
            return redirect("/error");
        }


        return "success/verificationSuccess";
    }
}
