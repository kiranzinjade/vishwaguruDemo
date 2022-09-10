package com.techvg.vks.accounts.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class JournalDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -3969316766082737913L;

    private String name;
    private String description;

    public JournalDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                      @Null String lastModifiedBy, Boolean isDeleted, String name, String description) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.name = name;
        this.description = description;
    }
}
