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
public class DepositAccountDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -3538943648789120512L;

    
    private String name;
    private Double interest;
    private Integer fdDurationDays;
    private Integer rdDurationMonths;
    private Integer lockInPeriod;
    private Boolean reinvestInterest;
    private String depositAccType;
    private Long depositAccTypeId;

    @Builder
    public DepositAccountDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                             @Null String lastModifiedBy, Boolean isDeleted, String name, Double interest,
                             Integer fdDurationDays, Integer lockInPeriod, Boolean reinvestInterest,
                             Long depositAccTypeId, String depositAccType,Integer rdDurationMonths) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.name = name;
        this.interest = interest;
        this.fdDurationDays = fdDurationDays;
        this.lockInPeriod = lockInPeriod;
        this.reinvestInterest = reinvestInterest;
        this.depositAccTypeId = depositAccTypeId;
        this.depositAccType = depositAccType;
        this.rdDurationMonths=rdDurationMonths;
    }
}
