package com.techvg.vks.deposit.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DepositAccrualDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -1832223762082636553L;

    private LocalDate accrualDate;
    private LocalDate periodFrom;
    private LocalDate periodTo;
    private Double interestAccrued;
    private Long accountNo;
    private String parentAccHead;
    private Double debit;
    private Double credit;
    private Boolean isPosted;

    private Long depositId;
    private Long savingAccountId;

    @Builder
    public DepositAccrualDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                             @Null String lastModifiedBy, Boolean isDeleted, LocalDate accrualDate, LocalDate periodFrom, LocalDate periodTo,
                             Double interestAccrued, Long accountNo, String parentAccHead, Long depositId, Long savingAccountId, Double debit,
                             Double credit, Boolean isPosted) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.accrualDate = accrualDate;
        this.periodFrom = periodFrom;
        this.periodTo = periodTo;
        this.interestAccrued = interestAccrued;
        this.accountNo = accountNo;
        this.parentAccHead = parentAccHead;
        this.debit = debit;
        this.credit = credit;
        this.isPosted = isPosted;
        this.depositId = depositId;
        this.savingAccountId = savingAccountId;
    }
}
