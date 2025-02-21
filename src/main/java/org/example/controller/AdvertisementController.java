package org.example.controller;

import jakarta.servlet.http.HttpSession;
import org.example.config.CustomUserDetails;
import org.example.config.SessionUser;
import org.example.model.DTO.adDTO.AdCreateDto;
import org.example.model.DTO.adDTO.AdDto;
import org.example.model.DTO.adDTO.AdUpdateDto;
import org.example.model.entity.Advertisement;
import org.example.service.AdvertisementService;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;

@Controller
public class AdvertisementController {
    private AdvertisementService service;
    private SessionUser sessionUser;

    public AdvertisementController(AdvertisementService service) {
        this.service = service;
    }

    @GetMapping("/advertisement/create-page")
    public String createPage() {
        return "/advertisement/create";
    }

    @PostMapping("/advertisement/create")
    public String create(@ModelAttribute AdCreateDto ad) {
        service.create(ad, sessionUser.get().getAuthUser().getId());
        return "redirect:ads";
    }

    @GetMapping("/advertisement/my_ads")
    public ModelAndView myAds(){
        List<AdDto> ads = service.getById(sessionUser.get().getAuthUser().getId());
        ads.sort(Comparator.comparing(AdDto::getAddOrder));
        ModelAndView view = new ModelAndView("advertisement/ads");
        view.addObject("ads", ads);
        view.addObject("isLoggedIn", sessionUser.get().isAccountNonLocked());
        return view;
    }

    @GetMapping("/advertisement/info-page")
    public String infoPage(@RequestParam("id") String id) {
        return "/advertisement/infoPage";
    }

    @GetMapping("/advertisement/ads")
    public ModelAndView ads(){
        List<AdDto> ads = service.getAll();
        ads.sort(Comparator.comparing(AdDto::getAddOrder));
        ModelAndView view = new ModelAndView("advertisement/ads");
        view.addObject("ads", ads);
        view.addObject("isLoggedIn", true);
        return view;
    }

    @GetMapping("/advertisement/update-page")
    public ModelAndView updatePage(@RequestParam("id") String id) {
        ModelAndView view = new ModelAndView("advertisement/update");
        AdDto adDto = service.get(id);
        if (adDto != null) {
            view.addObject("ad", adDto);
        } else {
            view.addObject("error", "ad not found");
        }
        return view;
    }

    @PostMapping("/advertisement/update")
    public String update(@ModelAttribute AdUpdateDto ad) {
        service.update(ad, sessionUser.get().getAuthUser().getId());
        return "redirect:ads";
    }

    @PostMapping("/advertisement/delete")
    public String delete(@RequestParam("id") String id) {
        service.delete(id);
        return "redirect:ads";
    }
}
