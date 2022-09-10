package com.techvg.vks.accounts.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AccountMappingDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -5146664216659684328L;

    private String mappingName;
    private String ledgerAccHeadCode;
    private String mappingType;
    private Long ledgerAccId;

    public AccountMappingDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                             @Null String lastModifiedBy, Boolean isDeleted, String mappingName,
                             String ledgerAccHeadCode, String mappingType, Long ledgerAccId) {

        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.mappingName = mappingName;
        this.ledgerAccHeadCode = ledgerAccHeadCode;
        this.mappingType = mappingType;
        this.ledgerAccId = ledgerAccId;
    }
}
