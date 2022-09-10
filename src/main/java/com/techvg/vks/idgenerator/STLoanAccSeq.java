package com.techvg.vks.idgenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "seq_st_loan_acc")
public class STLoanAccSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "st_loan_acc_seq")
    private Long stLoanAccNo;
}
