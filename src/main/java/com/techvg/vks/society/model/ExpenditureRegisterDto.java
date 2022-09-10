package com.techvg.vks.society.model;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.base.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenditureRegisterDto extends BaseEntityDto implements Serializable {

    private String name;
    private  Date expenditureDate;
    private String expenditureTypeName;
    private Double expenditureAmt;
    private String expenditureNote;
    private Long expenditureTypeId;
    private String debitFrom;
    private String creditTo;
    private String mode; // CASH or account
    private String voucherType;
    private VouchersDto vouchersDto;

    @Builder
    public ExpenditureRegisterDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
        @Null String lastModifiedBy, Boolean isDeleted,Date expenditureDate, String name,String expenditureTypeName,
        Double expenditureAmt,String expenditureNote,Long expenditureTypeId,String debitFrom,String creditTo,
        String mode, String voucherType) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.name = name;
        this.expenditureTypeName=expenditureTypeName;
        this.expenditureDate = expenditureDate;
        this.expenditureAmt = expenditureAmt;
        this.expenditureNote = expenditureNote;
        this.expenditureTypeId=expenditureTypeId;
        this.debitFrom=debitFrom;
        this.creditTo=creditTo;
        this.mode = mode;
        this.voucherType = voucherType;
    }
	
}
