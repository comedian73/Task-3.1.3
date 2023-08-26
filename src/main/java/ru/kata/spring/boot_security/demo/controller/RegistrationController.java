package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.configs.UserService;
import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.Valid;


@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/registration")
    public String registration( Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping(value = "/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult result) {

        if (result.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasConfirm())){
            result.addError(new ObjectError("globalPassword", "Пароли не совпадают"));
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            result.addError(new ObjectError("globalUser", "Пользователь с таким именем уже существует. Войдите или зарегистрируйтесь под другим именем."));
            return "registration";
        }

        return "redirect:/";
    }
}