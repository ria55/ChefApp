package application.controllers;

import application.models.ChefUser;
import application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = {"/", "/home"})
    public String getHello() {
        return "hello";
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    // legyen bejelentkezve
    @GetMapping("/user")
    public ChefUser getLoggedInUser() {
        return service.getLoggedInUser();
    }

    // csak admin
    @GetMapping("/users")
    public List<ChefUser> getAllUsers() {
        return service.getAllUsers();
    }

    // akár user, akár admin
    // ezt is csak admin
    @GetMapping(value = {"/users/", "/users/{username}"})
    public ChefUser getOneUser(
            @PathVariable("username") String username
    ) {
        if (username != null) {
            return service.getOneUser(username);
        }
        return null;
    }

    @GetMapping("/register")
    public String registerUser() {
        boolean registered = service.registerUsers();
        if (registered) {
            return "ok";
        }
        return "not ok";
    }

}
