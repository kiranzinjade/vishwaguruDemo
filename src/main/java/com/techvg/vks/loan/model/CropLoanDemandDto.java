package com.techvg.vks.loan.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CropLoanDemandDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -4067460774728860891L;

    private Integer year;
    private Boolean kmpStatus;
    private Boolean societyKmpStatus;
    private Boolean kmpApprovalStatus;
    private Boolean loanRegistrationStatus;
    private Long cropRegistrationId;
    private String cropName;
    private String season;
    private String tharavNo;
    private Long kmpId;

    public CropLoanDemandDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy,
                             @Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,
                             Integer year, Boolean kmpStatus, Boolean kmpApprovalStatus, Boolean loanRegistrationStatus,
                             Long cropRegistrationId, String cropName, String season, Long kmpId) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.year = year;
        this.kmpStatus = kmpStatus;
        this.kmpApprovalStatus = kmpApprovalStatus;
        this.loanRegistrationStatus = loanRegistrationStatus;
        this.cropRegistrationId = cropRegistrationId;
        this.cropName = cropName;
        this.season = season;
        this.kmpId = kmpId;
    }
}
