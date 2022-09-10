package com.techvg.vks.share.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberShareAccDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 253465342673399922L;

    private Long shareAccNumber;
    private String shareAccName;
    private String parentAccHeadCode;
    private Long parentAccId;

    public MemberShareAccDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                             @Null String lastModifiedBy, Boolean isDeleted, Long shareAccNumber,
                             String shareAccName, String parentAccHeadCode, Long parentAccId) {

        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.shareAccNumber = shareAccNumber;
        this.shareAccName = shareAccName;
        this.parentAccHeadCode = parentAccHeadCode;
        this.parentAccId = parentAccId;
    }
}
