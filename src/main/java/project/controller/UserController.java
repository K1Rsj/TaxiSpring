package project.controller;

import static project.constant.GlobalConstants.INFORMATION_MESSAGE;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.constant.Messages;
import project.model.domain.User;
import project.model.dto.UserDto;
import project.model.exception.EntityAlreadyExists;
import project.model.service.UserService;
import project.validator.UserValidator;

@Controller
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
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
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "user/user_registration_page";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") UserDto userDto, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", userDto);
            return "redirect:/registration";
        }
        try {
            userService.registerUser(userDto);
            redirectAttributes.addFlashAttribute(INFORMATION_MESSAGE, Messages.SUCCESSFUL_REGISTRATION);
        } catch (EntityAlreadyExists e) {
            redirectAttributes.addFlashAttribute(INFORMATION_MESSAGE, e.getMessage());
            redirectAttributes.addFlashAttribute("user", userDto);
            LOG.info("User trying to register with already used login or email", e);
        }
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
