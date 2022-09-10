package com.techvg.vks.accounts.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class VoucherTransDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 6146459049180074520L;

    private Double debitAmt;
    private Double creditAmt;
    private Double balance;
    private Long voucherNo;
    private Date transDate;
    private String remark;
    private String mode;
    private String transType;

    @Builder
    public VoucherTransDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                            @Null String lastModifiedBy, Boolean isDeleted, Double debitAmt, Double creditAmt, Double balance,
                            Long voucherNo, Date transDate, String remark, String mode, String transType) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.debitAmt = debitAmt;
        this.creditAmt = creditAmt;
        this.balance = balance;
        this.voucherNo = voucherNo;
        this.transDate = transDate;
        this.remark = remark;
        this.mode = mode;
        this.transType = transType;
    }
}
