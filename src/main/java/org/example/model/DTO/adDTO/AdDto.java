package org.example.model.DTO.adDTO;

import lombok.*;
import org.example.model.entity.Category;
import org.example.model.entity.base.BaseEntity;
import org.example.model.enums.Currency;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdDto extends BaseEntity {
    private String id;
    private String name;
    private String description;
    private Double price;
    private Currency currency;
    private String category;
    private Double stars;
    private Long addOrder;
    private Boolean isActive;
    private String imageURL;
}
