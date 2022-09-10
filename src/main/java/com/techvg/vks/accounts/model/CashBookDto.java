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
public class CashBookDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 4891822959765806149L;

    private Double debitAmt;
    private Double creditAmt;
    private Double balance;
    private String voucherNo;
    private Date transDate;
    private String particulars;
    private String authorisedBy;
    private String transType;

    @Builder
    public CashBookDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy,
                       @Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,
                       Double debitAmt, Double creditAmt, Double balance, String voucherNo, Date transDate,
                       String particulars, String authorisedBy, String transType) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.debitAmt = debitAmt;
        this.creditAmt = creditAmt;
        this.balance = balance;
        this.voucherNo = voucherNo;
        this.transDate = transDate;
        this.particulars = particulars;
        this.authorisedBy = authorisedBy;
        this.transType = transType;
    }
}
