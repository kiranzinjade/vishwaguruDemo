package com.techvg.vks.accounts.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class LedgerAccountsDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 4224620599460506791L;

    private Long accountNo;
    private String accountName;
    private Double accBalance;
    private String accHeadCode;
    private String ledgerCode;
    private String appCode;
    private String classification;
    private String category;
    private Integer level;
    private Long parentId;
    private Long accTypeId;
    private String parentAccHead;
    private String accTypeName;
    private Boolean hasParent;
    private LedgerAccountsDto parentLedger;
    private Set<LedgerAccountsDto> childLedgers;

    public LedgerAccountsDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                             @Null String lastModifiedBy, Boolean isDeleted, Long accountNo, String accountName,
                             Double accBalance, String accHeadCode, String ledgerCode, String appCode, String classification,
                             Integer level, String category, LedgerAccountsDto parentLedger, String parentAccHead,
                             Long parentId, Long accTypeId, Boolean hasParent, String accTypeName,
                             Set<LedgerAccountsDto> childLedgers) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.accBalance = accBalance;
        this.accHeadCode = accHeadCode;
        this.ledgerCode = ledgerCode;
        this.appCode = appCode;
        this.classification = classification;
        this.level = level;
        this.category = category;
        this.parentId = parentId;
        this.parentLedger = parentLedger;
        this.childLedgers = childLedgers;
        this.accTypeId = accTypeId;
        this.hasParent = hasParent;
        this.parentAccHead = parentAccHead;
        this.accTypeName = accTypeName;
    }
}
