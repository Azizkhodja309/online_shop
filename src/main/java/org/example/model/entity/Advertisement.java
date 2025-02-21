package org.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.entity.base.BaseEntity;
import org.example.model.enums.Currency;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Advertisement extends BaseEntity {
    private String name;
    private String description;
    private Double price;
    private Currency currency;
    private Integer stars;
    private Boolean isActive = Boolean.FALSE;
    private Long addOrder;
}
