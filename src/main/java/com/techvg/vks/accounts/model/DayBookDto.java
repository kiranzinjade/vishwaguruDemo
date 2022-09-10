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
public class DayBookDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 923479697327322458L;

    private Double debitBalance;
    private Double debitCashAmt;
    private Double debitTransferAmt;
    private Double debitTotalAmt;
    private Double creditBalance;
    private Double creditCashAmt;
    private Double creditTransferAmt;
    private Double creditTotalAmt;
    private Integer voucherCount;
    private String mode;
    private String transType;
    private Date transDate;
    private String particulars;
    private String authorisedBy;
    private Double balance;

    @Builder
    public DayBookDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                      @Null String lastModifiedBy, Boolean isDeleted, Double debitBalance, Double debitCashAmt,
                      Double debitTransferAmt, Double debitTotalAmt, Double creditBalance, Double creditCashAmt,
                      Double creditTransferAmt, Double creditTotalAmt, Integer voucherCount, String mode,
                      String transType, Date transDate, String particulars, String authorisedBy, Double balance) {

        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.debitBalance = debitBalance;
        this.debitCashAmt = debitCashAmt;
        this.debitTransferAmt = debitTransferAmt;
        this.debitTotalAmt = debitTotalAmt;
        this.creditBalance = creditBalance;
        this.creditCashAmt = creditCashAmt;
        this.creditTransferAmt = creditTransferAmt;
        this.creditTotalAmt = creditTotalAmt;
        this.voucherCount = voucherCount;
        this.mode = mode;
        this.transType = transType;
        this.transDate = transDate;
        this.particulars = particulars;
        this.authorisedBy = authorisedBy;
        this.balance = balance;
    }
}
