package org.example.service;

import org.example.model.DTO.adDTO.AdCreateDto;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {

    private final Environment environment;

    public FileService(Environment environment) {
        this.environment = environment;
    }

    public String getImageURL(AdCreateDto dto) {
        return upload(dto.getImage());
    }

    public String upload(MultipartFile file) {
        String myMeType = file.getContentType();
        double size = file.getSize();
        try {
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID() + "_" + originalFilename;
            Path path = Paths.get(environment.getRequiredProperty("project.image.store") + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//            advImageDao.save(id, originalFilename, fileName, myMeType, size );
            return environment.getRequiredProperty("project.image.store")+fileName;
        } catch (
                Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Resource> download(String fileName) {
        Path path = Paths.get(environment.getRequiredProperty("project.image.store"), fileName);
        FileSystemResource resource = new FileSystemResource(path);
        HttpHeaders headers = new HttpHeaders();
        ContentDisposition contentDisposition = ContentDisposition.builder("inline")
                .filename(fileName)
                .build();
        headers.set("Content-Type", "application/jpeg");
        headers.setContentDisposition(contentDisposition);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
