package ru.kata.spring.boot_security.demo.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.configs.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.configs.UserService;
import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.Valid;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/admin")
    public String userList( Model model) {

        model.addAttribute("list", userService.getAll());
        return "admin";
    }

    @PostMapping (value = "/admin/delete")
    public String deleteUser(@RequestParam(name = "del") long id) {

        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/edit")
    public String edit (@RequestParam(name = "edit") long id, Model model) {

        model.addAttribute("user", userService.findUserById(id));
        return "edit";
    }

    @PostMapping(value = "/admin/edit")
    public String saveEdit (@ModelAttribute("user") @Valid User user, BindingResult result, Long id) {

        if (result.hasErrors()) {
            return "edit";
        }

        if (!user.getPassword().equals(user.getPasConfirm())){
            result.addError(new ObjectError("globalPassword", "Пароли не совпадают"));
            return "edit";
        }

        User user1 = userService.findUserById(id);
        user1.setLastName(user.getLastName());
        user1.setFirstName(user.getFirstName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setPasConfirm(user.getPasConfirm());
        userService.saveEditUser(user1);
        return "redirect:/admin";
    }
}