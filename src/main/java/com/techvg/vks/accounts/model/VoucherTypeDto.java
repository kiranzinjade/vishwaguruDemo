package com.techvg.vks.accounts.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class VoucherTypeDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -5751334850646227743L;

    private String name;
    private String description;
    private Boolean isGeneral;

    @Builder
    public VoucherTypeDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                          @Null String lastModifiedBy, Boolean isDeleted, String name, String description, Boolean isGeneral) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.name = name;
        this.description = description;
        this.isGeneral = isGeneral;
    }
}
