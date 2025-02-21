package org.example.model.DTO.adDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.enums.Currency;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdUpdateDto {
    private String id;
    private String name;
    private String description;
    private Double price;
    private Currency currency;
    private Integer stars;
}
