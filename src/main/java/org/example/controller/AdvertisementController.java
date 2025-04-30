package org.example.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.example.config.CustomUserDetails;
import org.example.config.SessionUser;
import org.example.dao.AuthUserDao;
import org.example.model.DTO.adDTO.AdCreateDto;
import org.example.model.DTO.adDTO.AdDto;
import org.example.model.DTO.adDTO.AdUpdateDto;
import org.example.model.entity.AuthUser;
import org.example.model.enums.Currency;
import org.example.service.AdvertisementService;
import org.example.service.FileService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;

@MultipartConfig
@Controller
public class AdvertisementController {
    private final AdvertisementService service;
    private final FileService fileService;
    private final SessionUser sessionUser;
    private final AuthUserDao authUserDao;

    public AdvertisementController(AdvertisementService service, FileService fileService, SessionUser sessionUser, AuthUserDao authUserDao) {
        this.service = service;
        this.fileService = fileService;
        this.sessionUser = sessionUser;
        this.authUserDao = authUserDao;
    }

    @GetMapping("/advertisement/create-page")
    public String createPage() {
        return "/advertisement/create";
    }

    @PostMapping("/advertisement/create")
    public String create(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("price") Double price,
            @RequestParam("currency") String currency,
            @RequestParam("image") MultipartFile image
    ) {
        AdCreateDto adDto = new AdCreateDto();
        adDto.setName(name);
        adDto.setDescription(description);
        adDto.setCategory(category);
        adDto.setPrice(price);
        adDto.setCurrency(Currency.valueOf(currency));
        adDto.setImage(image);

        service.create(adDto, sessionUser.get().getAuthUser().getId());
        return "redirect:/advertisement/my_ads";
    }


    @GetMapping("/advertisement/my_ads")
    public ModelAndView myAds(){
        List<AdDto> myAds = service.getByUserId(sessionUser.get().getAuthUser().getId());

        myAds.sort(Comparator.comparing(AdDto::getAddOrder));
        ModelAndView view = new ModelAndView("advertisement/myAds");
        view.addObject("myAds", myAds);
        return view;
    }

    @GetMapping("/advertisement/info-page")
    public ModelAndView infoPage(@RequestParam("id") String id) {
        AdDto ad = service.get(id);
        ModelAndView modelAndView = new ModelAndView("advertisement/infoPage");
        modelAndView.addObject("adInfo", ad);
        return modelAndView;
    }

    @GetMapping("/advertisement/ads")
    public ModelAndView ads(@RequestParam(value = "category", required = false, defaultValue = "all") String category) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String fullName = "Guest";
        boolean admin = false;
        boolean isLoggedIn = !(principal instanceof String && principal.equals("anonymousUser"));

        if (principal instanceof CustomUserDetails customUserDetails) {
            AuthUser authUser = customUserDetails.getAuthUser();
            fullName = authUser.getFullName();
        }

        List<AdDto> ads;
        if (category.equals("all")) {
            ads = service.getAll();
        } else {
            ads = service.getAllByCategory(category);
        }
        ads.sort(Comparator.comparing(AdDto::getAddOrder));

        ModelAndView view = new ModelAndView("advertisement/ads");
        view.addObject("ads", ads);
        view.addObject("userFullName", fullName);
        view.addObject("selectedCategory", category);

        try {
            if (authUserDao.findRoleById(sessionUser.get().getAuthUser().getRoleId()).equals("admin"))
                admin = true;
        } catch (Exception ignored) {}

        view.addObject("isAdmin", admin);
        view.addObject("isLoggedIn", isLoggedIn);
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
    public String update(@RequestParam("id") String id,
                        @RequestParam("name") String name,
                         @RequestParam("description") String description,
                         @RequestParam("category") String category,
                         @RequestParam("price") Double price,
                         @RequestParam("currency") String currency,
                         @RequestParam(value = "image", required = false) MultipartFile file) {
        AdUpdateDto ad = new AdUpdateDto();
        ad.setId(id);
        ad.setName(name);
        ad.setDescription(description);
        ad.setCategory(category);
        ad.setPrice(price);
        ad.setCurrency(Currency.valueOf(currency));
//        ad.setImageURL(fileService.getImageURL(file));
        service.update(ad, sessionUser.get().getAuthUser().getId());
        return "redirect:/advertisement/my_ads";
    }

    @PostMapping("/advertisement/delete")
    public String delete(@RequestParam("id") String id) {
        service.delete(id);
        return "redirect:/advertisement/my_ads";
    }

    @PostMapping("/advertisement/change-activity")
    public String toggle(@RequestParam("id") String id) {
        service.changeActivity(id, sessionUser.get().getAuthUser().getId());
        return "redirect:/advertisement/my_ads";
    }
}
