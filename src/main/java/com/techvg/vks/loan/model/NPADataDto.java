package com.techvg.vks.loan.model;

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
public class NPADataDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -5786140261135485708L;

    private Date npaDate;
    private String loanType;

    private Double standardAmt=0.0;
    private Integer standardCnt=0;
    private Double standardProvision;

    private Double substandardAmt=0.0;
    private Integer substandardCnt=0;
    private Double substandardProvision;

    private Double doubtful1Amt=0.0;
    private Integer doubtful1Cnt=0;
    private Double doubtful1Provision;

    private Double doubtful2Amt=0.0;
    private Integer doubtful2Cnt=0;
    private Double doubtful2Provision;

    private Double doubtful3Amt=0.0;
    private Integer doubtful3Cnt=0;
    private Double doubtful3Provision;

    private Double lossAmt=0.0;
    private Integer lossCnt=0;

    @Builder
    public NPADataDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted, Date npaDate, String loanType, Double standardAmt, Integer standardCnt, Double standardProvision, Double substandardAmt, Integer substandardCnt, Double substandardProvision, Double doubtful1Amt, Integer doubtful1Cnt, Double doubtful1Provision, Double doubtful2Amt, Integer doubtful2Cnt, Double doubtful2Provision, Double doubtful3Amt, Integer doubtful3Cnt, Double doubtful3Provision, Double lossAmt, Integer lossCnt) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.npaDate = npaDate;
        this.loanType = loanType;
        this.standardAmt = standardAmt;
        this.standardCnt = standardCnt;
        this.standardProvision = standardProvision;
        this.substandardAmt = substandardAmt;
        this.substandardCnt = substandardCnt;
        this.substandardProvision = substandardProvision;
        this.doubtful1Amt = doubtful1Amt;
        this.doubtful1Cnt = doubtful1Cnt;
        this.doubtful1Provision = doubtful1Provision;
        this.doubtful2Amt = doubtful2Amt;
        this.doubtful2Cnt = doubtful2Cnt;
        this.doubtful2Provision = doubtful2Provision;
        this.doubtful3Amt = doubtful3Amt;
        this.doubtful3Cnt = doubtful3Cnt;
        this.doubtful3Provision = doubtful3Provision;
        this.lossAmt = lossAmt;
        this.lossCnt = lossCnt;
    }
}
