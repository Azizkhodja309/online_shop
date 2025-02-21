package org.example.controller;

import jakarta.servlet.http.HttpSession;
import org.example.config.SessionUser;
import org.example.mapper.AuthUserMapper;
import org.example.model.DTO.userDTO.AuthUserCreateDto;
import org.example.model.DTO.userDTO.AuthUserDto;
import org.example.model.DTO.userDTO.AuthUserUpdateDto;
import org.example.model.entity.AuthUser;
import org.example.service.AuthUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AuthUserController {
    private final AuthUserService service;
    private final AuthUserMapper mapper;
    private final SessionUser sessionUser;

    public AuthUserController(AuthUserService service, AuthUserMapper mapper, SessionUser sessionUser) {
        this.service = service;
        this.mapper = mapper;
        this.sessionUser = sessionUser;
    }

    @GetMapping("/auth/admin")
    @ResponseBody
    @PreAuthorize(value = "hasRole('ADMIN') || hasAuthority('get_user')")
    public String adminApi(@AuthenticationPrincipal User user) {
        System.out.print(user.getAuthorities());
        return "This is admin api";
    }

    @GetMapping("/auth/user")
    @ResponseBody
    @PreAuthorize(value = "hasRole('USER')")
    public String userApi() {
        return "This is user api";
    }

    @GetMapping("/auth/login")
    public String login(){
        return "/auth/login";
    }

    @GetMapping("/auth/set_authentication")
    public String setAuthentication(){
//        sessionUser.get().getAuthUser().setIsLocked(!sessionUser.get().getAuthUser().getIsLocked());
        return "redirect:/advertisement/ads";
    }

    @GetMapping("/auth/logout")
    public String logout(){
        return "/auth/logout";
    }

    @GetMapping("/auth/create-page")
    public String createPage() {
        return "/auth/create";
    }

    @GetMapping("/auth/users")
    public ModelAndView usersPage() {
        List<AuthUserDto> users = service.getAll();
        ModelAndView view = new ModelAndView();
        view.setViewName("auth/users");
        view.addObject("users", users);
        return view;
    }

    @GetMapping("/auth/update-page")
    public ModelAndView updatePage(@RequestParam("id") String id) {
        ModelAndView view = new ModelAndView("/auth/update");
        AuthUserDto user = service.get(id);
        if(user != null) {
            view.addObject("user", user);
        } else {
            view.addObject("error", "user not found");
        }
        return view;
    }

    @PostMapping("/auth/create")
    public String create(@ModelAttribute AuthUserCreateDto dto, HttpSession session) {
        AuthUser authUser = mapper.fromCreateDto(dto);
        session.setAttribute("userId", authUser.getId());
        service.create(authUser, null);
        return "redirect:users";
    }

    @PostMapping("/auth/update")
    public String update(@ModelAttribute AuthUserUpdateDto dto, HttpSession session) {
        service.update(dto, /*session.getAttribute("userId").toString()*/"123");
        return "redirect:users";
    }

    @PostMapping("/auth/delete")
    public String delete(@RequestParam("id") String id) {
        service.delete(id);
        return "redirect:/auth/users";
    }
}
