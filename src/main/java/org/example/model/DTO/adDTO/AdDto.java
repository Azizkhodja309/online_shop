package org.example.model.DTO.adDTO;

import lombok.*;
import org.example.model.entity.base.BaseEntity;
import org.example.model.enums.Currency;

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
    private Integer stars;
    private Long addOrder;
}
