package org.example.service;

import jakarta.servlet.http.HttpSession;
import org.example.dao.AdDao;
import org.example.mapper.AdMapper;
import org.example.model.DTO.adDTO.AdCreateDto;
import org.example.model.DTO.adDTO.AdDto;
import org.example.model.DTO.adDTO.AdUpdateDto;
import org.example.model.entity.Advertisement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementService implements AbstractCrudService<AdDto, AdCreateDto, AdUpdateDto, String, String>{
    private final AdDao dao;
    private final AdMapper mapper;

    public AdvertisementService(AdDao dao, AdMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public void create(AdCreateDto dto, String userId) {
        Advertisement ad = mapper.fromCreateDto(dto);
        dao.save(ad, userId);
    }

    @Override
    public void update(AdUpdateDto dto, String userId) {
        Advertisement ad = mapper.fromUpdateDto(dto);
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

    @Override
    public void delete(String id) {
        dao.delete(id);
    }

    @Override
    public List<AdDto> getById(String id) {
        List<Advertisement> ads = dao.findById(id);
        return mapper.toDto(ads);
    }
}
