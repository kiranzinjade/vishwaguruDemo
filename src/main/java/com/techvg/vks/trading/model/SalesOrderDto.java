package com.techvg.vks.trading.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class SalesOrderDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -8038556398356509588L;

    private Long orderNo;
    private Date salesDate;
    private Integer billNo;
    private String department;
    private String salesMode;
    private Double billAmount;
    private Double balanceAmount;
    private Double paidAmount;
    private String particulars;

    private Long memberId;
    private String fullName;
    private Set<SalesRegisterDto> salesRegisters;
}
