package com.techvg.vks.accounts.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Null;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountTypeDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -6626422753832011689L;

    private String name;
    private String description;

    @Builder
    public AccountTypeDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                          @Null String lastModifiedBy, Boolean isDeleted, String name, String description) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.name = name;
        this.description = description;
    }
}
