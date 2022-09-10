package com.techvg.vks.accounts.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class VoucherDetailsDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -873568122820686284L;

    private Long debitAcc;
    private Long creditAcc;
    private String accountHead;
    private Double transAmount;
    private Double creditAmt;
    private Double debitAmt;

    public VoucherDetailsDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy,
                             @Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,
                             Long debitAcc, Long creditAcc,String accountHead, Double transAmount,
                             Double creditAmt, Double debitAmt) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.debitAcc = debitAcc;
        this.creditAcc = creditAcc;
        this.accountHead = accountHead;
        this.transAmount = transAmount;
        this.debitAmt = debitAmt;
        this.creditAmt = creditAmt;
    }
}
