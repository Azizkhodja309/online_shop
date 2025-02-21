package org.example.model.entity.base;

import lombok.Getter;
import lombok.Setter;
import org.example.model.entity.base.IdEntity;

import java.time.LocalDateTime;


@Getter
@Setter
public class BaseEntity extends IdEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean deleted = false;
}
