package com.techvg.vks.society.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MeasuringUnitDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -5922330330135038255L;

    @NotBlank
    private String name;

    private String description;

    @Builder
    public MeasuringUnitDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                            @Null String lastModifiedBy, Boolean isDeleted, @NotBlank String name, String description) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.name = name;
        this.description = description;
    }
}
