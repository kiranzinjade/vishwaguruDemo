package com.techvg.vks.loan.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class KMPCropDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 7638704163433669783L;

    private String cropName;
    private Long cropRegistrationId;
    private String season;
    private  Integer year;
    private String tharavNo;
    private Long kmpId;

    public KMPCropDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                      @Null String lastModifiedBy, Boolean isDeleted, String cropName, Long cropRegistrationId, String season,
                      Integer year, String tharavNo, Long kmpId) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.cropName = cropName;
        this.cropRegistrationId = cropRegistrationId;
        this.season = season;
        this.year = year;
        this.tharavNo = tharavNo;
        this.kmpId = kmpId;
    }
}
