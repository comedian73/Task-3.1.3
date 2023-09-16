package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping
    public String userList(Authentication auth, Model model) {
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());
        model.addAttribute("list", userService.getAllUsers());
        model.addAttribute("user", user);
        return "admin";
    }

    @PostMapping (value = "/delete")
    public String deleteUser(@RequestParam(name = "del") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit")
    public String edit (@RequestParam(name = "edit") String username, Model model) {
        User user = (User) userService.loadUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("listRole", userService.getAllRoles());
        return "edit";
    }

    @PostMapping(value = "/edit")
    public String saveEdit (@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "edit";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/addUser")
    public String addUser(Model model) {
        model.addAttribute("listRole", userService.getAllRoles());
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping(value = "/addUser")
    public String saveAddUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "edit";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }
}