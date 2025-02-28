package org.example.model.DTO.adDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.enums.Currency;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdCreateDto {
    private MultipartFile image;
    private String name;
    private String description;
    private Double price;
    private String category;
    private Currency currency;
    private Double stars;
    private String imageURL;
}
