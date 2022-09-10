package com.techvg.vks.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntityDto {

    @Null
    protected Long id;
    @Null
    protected LocalDateTime created;
    @Null
    protected String createdBy;
    @Null
    protected LocalDateTime lastModified;
    @Null
    protected String lastModifiedBy;

    protected Boolean isDeleted=false;

}
