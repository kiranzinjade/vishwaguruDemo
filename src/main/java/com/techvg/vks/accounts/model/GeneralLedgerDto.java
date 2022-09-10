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
public class GeneralLedgerDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -3626758232196358902L;

    private Double debitAmt;
    private Double creditAmt;
    private Double balance;
    private String voucherNo;
    private Date transDate;
    private String remark;
    private String mode;
    private String transType;
    private Double openingBalance;
    private Long ledgerAccountId;
    private String accHeadCode;

    @Builder
    public GeneralLedgerDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                            @Null String lastModifiedBy, Boolean isDeleted, Double debitAmt, Double creditAmt, Long ledgerAccountId, Double balance,
                            String voucherNo, Date transDate, String remark, String mode,Double openingBalance, String transType,String accHeadCode) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.debitAmt = debitAmt;
        this.creditAmt = creditAmt;
        this.balance = balance;
        this.voucherNo = voucherNo;
        this.transDate = transDate;
        this.remark = remark;
        this.mode = mode;
        this.openingBalance=openingBalance;
        this.transType = transType;
        this.ledgerAccountId=ledgerAccountId;
        this.accHeadCode=accHeadCode;
    }
}
