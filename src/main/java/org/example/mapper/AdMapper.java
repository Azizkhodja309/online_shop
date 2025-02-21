package org.example.mapper;

import org.example.config.SessionUser;
import org.example.model.DTO.adDTO.AdCreateDto;
import org.example.model.DTO.adDTO.AdDto;
import org.example.model.DTO.adDTO.AdUpdateDto;
import org.example.model.entity.Advertisement;
import org.example.model.enums.Currency;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AdMapper {
    private SessionUser sessionUser;

    public Advertisement fromCreateDto(AdCreateDto dto) {
        Advertisement ad = new Advertisement();

        ad.setId(UUID.randomUUID().toString().replace("-", ""));
        ad.setName(dto.getName());
        ad.setDescription(dto.getDescription());
        ad.setPrice(dto.getPrice());
        ad.setStars(dto.getStars());
        ad.setCurrency(dto.getCurrency());
        ad.setCreatedBy(sessionUser.get().getAuthUser().getId());
        return ad;
    }

    public Advertisement fromUpdateDto(AdUpdateDto dto) {
        Advertisement ad = new Advertisement();

        ad.setId(dto.getId());
        ad.setName(dto.getName());
        ad.setDescription(dto.getDescription());
        ad.setPrice(dto.getPrice());
        ad.setStars(dto.getStars());
        ad.setCurrency(dto.getCurrency());
        ad.setCreatedBy(sessionUser.get().getAuthUser().getId());
        return ad;
    }

    public List<AdDto> toDto(List<Advertisement> ads) {
        return ads.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public AdDto toDto(Advertisement ad) {
        return AdDto.builder()
                .id(ad.getId())
                .name(ad.getName())
                .description(ad.getDescription())
                .price(ad.getPrice())
                .currency(ad.getCurrency())
                .stars(ad.getStars())
                .addOrder(ad.getAddOrder())
                .build();
    }
}
