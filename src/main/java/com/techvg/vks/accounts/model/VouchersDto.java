package com.techvg.vks.accounts.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class VouchersDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -5751334850646227743L;

    private Long voucherNo;
    private Date voucherDate;
    private String narration;
    private String costCenter;
    private String preparedBy;
    private String authorisedBy;
    private String voucherTypeDesc;
    private Long voucherTypeId;
    private String mode;
    private String appCode;
    private Set<VoucherDetailsDto> voucherDetails;

    @Builder
    public VouchersDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                       @Null String lastModifiedBy, Boolean isDeleted, Long voucherNo, Date voucherDate, String narration,
                       String costCenter, String preparedBy, String authorisedBy, String voucherTypeDesc, Long voucherTypeId,
                       String mode, String appCode, Set<VoucherDetailsDto> voucherDetails) {

        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.voucherNo = voucherNo;
        this.voucherDate = voucherDate;
        this.narration = narration;
        this.costCenter = costCenter;
        this.preparedBy = preparedBy;
        this.authorisedBy = authorisedBy;
        this.voucherTypeDesc = voucherTypeDesc;
        this.voucherTypeId = voucherTypeId;
        this.mode = mode;
        this.appCode = appCode;
        this.voucherDetails = voucherDetails;
    }
}
