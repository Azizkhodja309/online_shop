package org.example.service;

import org.example.dao.AdDao;
import org.example.mapper.AdMapper;
import org.example.model.DTO.adDTO.AdCreateDto;
import org.example.model.DTO.adDTO.AdDto;
import org.example.model.DTO.adDTO.AdUpdateDto;
import org.example.model.entity.Advertisement;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class AdvertisementService implements AbstractCrudService<AdDto, AdCreateDto, AdUpdateDto, String, String> {
    private final AdDao dao;
    private final AdMapper mapper;
    private final FileService fileService;

    public AdvertisementService(AdDao dao, AdMapper mapper, FileService fileService) {
        this.dao = dao;
        this.mapper = mapper;
        this.fileService = fileService;
    }

    @Override
    public void create(AdCreateDto dto, String userId) {
//        String imageURL = fileService.getImageURL(dto);
        String imageURL = null;
        Advertisement ad = mapper.fromCreateDto(imageURL, dto, userId);
        dao.save(ad, userId);
    }

    @Override
    public void update(AdUpdateDto dto, String userId) {
        Advertisement ad = mapper.fromUpdateDto(dto, userId);
        dao.update(ad, userId);
    }

    @Override
    public AdDto get(String id) {
        return dao.get(id);
    }

    @Override
    public List<AdDto> getAll() {
        List<Advertisement> ads = dao.findAll();
        return mapper.toDto(ads);
    }

    public List<AdDto> getAllByCategory(String category) {
        List<Advertisement> ads = dao.findAllByCategory(category);
        return mapper.toDto(ads);
    }

    @Override
    public void delete(String id) {
        dao.delete(id);
    }

    @Override
    public List<AdDto> getByUserId(String id) {
        List<Advertisement> ads = dao.findByUser(id);
        return mapper.toDto(ads);
    }

    @Override
    public void changeActivity(String id, String userId) {
        dao.changeActivity(id, userId);
    }
}
