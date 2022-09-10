package com.techvg.vks.idgenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "seq_accrued_acc")
public class AccruedAccSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accrued_acc_seq")
    private Long accruedAccNo;
}
