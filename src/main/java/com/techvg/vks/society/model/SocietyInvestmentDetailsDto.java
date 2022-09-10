package com.techvg.vks.society.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class SocietyInvestmentDetailsDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 7093018506209899082L;

    private Date depositDate;
    private String particulars;
    private Double debitAmount;
    private Double creditAmount;
    private Double balanceAmount;
    private Double interestAmount;
    private Date interestDate;
    private Long societyInvestmentId;

    public SocietyInvestmentDetailsDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy,
                                       @Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,
                                       Date depositDate, String particulars, Double debitAmount, Double creditAmount,
                                       Double balanceAmount, Double interestAmount, Date interestDate,Long societyInvestmentId) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.depositDate = depositDate;
        this.particulars = particulars;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
        this.balanceAmount = balanceAmount;
        this.interestAmount = interestAmount;
        this.interestDate = interestDate;
        this.societyInvestmentId=societyInvestmentId;
    }
}
