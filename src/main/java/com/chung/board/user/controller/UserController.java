package com.chung.board.user.controller;

import com.chung.board.user.UserVO;
import com.chung.board.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/new")
    public String createForm() {
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(UserForm userForm) {
        UserVO userVO = new UserVO();
        userVO.setName(userForm.getName());

        userService.join(userVO);
        List<UserVO> allUsers = userService.findAllUsers();

        return "redirect:/";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<UserVO> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users/userList";
    }
}
