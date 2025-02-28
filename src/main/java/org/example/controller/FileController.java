package org.example.controller;

import org.example.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileController {
    private final FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    @GetMapping("/file/upload")
    public String uploadPage() {
        return "/auth/upload";
    }

    @PostMapping("/file/upload")
    public ModelAndView upload(@RequestParam(name = "file") MultipartFile file) {
        String genUrl = service.upload(file);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("file/upload");
        modelAndView.addObject("gen_url", genUrl);
        return modelAndView;
    }

    @GetMapping("/file/download/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> download(@PathVariable(name = "fileName") String fileName) {
        return service.download(fileName);
    }
}
