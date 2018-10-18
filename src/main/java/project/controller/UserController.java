package project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.domain.User;
import project.model.service.UserService;
import project.validator.UserValidator;

import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;
    private UserValidator userValidator;

    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());

        return "admin/all_users";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("userForm", new User());

        return "user/user_registration_page";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("userForm") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()) {
            return "user/user_registration_page";
        }
        userService.registerUser(user);

        return "redirect:/login";
    }

    @RequestMapping("/users/remove/{id}")
    public String removeUser(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);

        return "redirect:/users";
    }

    @PostMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            model.addAttribute("message", "User with this id is doesn't exist");
            model.addAttribute("user", new User());
        }
        model.addAttribute("users", userService.getAllUsers());

        return "admin/all_users";
    }

    @GetMapping("/my_discount")
    public String getUserDiscount(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("discount", userService.getUserDiscount(auth.getName()));

        return "user/my_discount";
    }
}
