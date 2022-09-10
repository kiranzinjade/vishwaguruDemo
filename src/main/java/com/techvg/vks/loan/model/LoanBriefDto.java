 package com.techvg.vks.loan.model;

 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;

 import java.io.Serializable;
 import java.util.Date;

 @Data
 @NoArgsConstructor

 public class LoanBriefDto implements Serializable {
     private static final long serialVersionUID = -2382285810952315086L;

     private Long id;
     private Long loanAccountNo;
     private double loanAmt;
     private String loanType;
     private String loanStatus;
     private Date loanEffectiveDate;
     private String loanClassification;
     private Date loanPlannedClosureDate;
     private String loanProductName;
     private Long memberId;
     private String fullName;
     private String disbursementStatus;//full,partial
     private Double disbursementAmt;
     private Double paidAmt;
     private Double balanceAmt;
     private Double interest;
     private Integer duration;

     public LoanBriefDto(Long id, Long loanAccountNo, double loanAmt, String loanType, String loanStatus,
                         Date loanEffectiveDate, String loanClassification, Date loanPlannedClosureDate,
                         String loanProductName, Long memberId, Double disbursementAmt, String disbursementStatus,
                         Double paidAmt, Double balanceAmt, String fullName, Double interest, Integer duration) {

         this.id = id;
         this.loanAccountNo = loanAccountNo;
         this.loanAmt = loanAmt;
         this.loanType = loanType;
         this.loanStatus = loanStatus;
         this.loanEffectiveDate = loanEffectiveDate;
         this.loanClassification = loanClassification;
         this.loanPlannedClosureDate = loanPlannedClosureDate;
         this.loanProductName = loanProductName;
         this.memberId = memberId;
         this.disbursementAmt = disbursementAmt;
         this.disbursementStatus = disbursementStatus;
         this.paidAmt = paidAmt;
         this.balanceAmt = balanceAmt;
         this.fullName = fullName;
         this.interest=interest;
         this.duration=duration;
     }






 }
